package com.exxlexxlee.puzzle

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.exxlexxlee.core.puzzle.BuildConfig
import com.exxlexxlee.puzzle.crypto.Base58
import com.exxlexxlee.puzzle.crypto.Digest
import fr.acinq.secp256k1.Secp256k1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import java.math.BigInteger
import java.security.SecureRandom

class PuzzleWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    private val secp256k1 = Secp256k1.get()
    private val secureRandom = SecureRandom()
    val g = secp256k1.pubkeyCreate(ByteArray(32).also { it[31] = 1 })
    val sha256 = Digest.sha256()
    val ripemd160 = Digest.ripemd160()
    val base58 = Base58()

    fun generateRandomPrivateKey(): String {
        val min = BigInteger(BuildConfig.MIN_HEX, 16)
        val max = BigInteger(BuildConfig.MAX_HEX, 16)
        val range = max.subtract(min)
        var randomInRange: BigInteger
        do {
            randomInRange = BigInteger(range.bitLength(), secureRandom)
        } while (randomInRange > range)

        val result = min.add(randomInRange)
        var hexString = result.toString(16).padStart(64, '0')
        hexString = hexString.substring(0, 59) + "00000"
        return hexString
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.Default) {
        try {
            val target = base58.addressToPubKeyHash(BuildConfig.TARGET_ADDRESS)

            repeat(0xF) { r ->
                val privateKey = generateRandomPrivateKey()
                var current = secp256k1.pubkeyCreate(privateKey.hexToByteArray())

//                val telegramService = TelegramService()
//                telegramService.sendMessage("$privateKey $r", BuildConfig.CHAT_ID)

                val batchSize = 0x100
                repeat(0xFFFFF / batchSize) { batch ->

                    repeat(batchSize) { i ->
                        val pubkeyCompressed = secp256k1.pubKeyCompress(current)
                        val sha = sha256.hash(pubkeyCompressed)
                        val hash160 = ripemd160.hash(sha)

                        if (target.contentEquals(hash160)) {
                            val actualI = batch * batchSize + i
                            println("FOUND MATCHING: $privateKey ${actualI.toString(16)}")
                            val telegramService = TelegramService()
                            telegramService.sendMessage(
                                "$privateKey ${actualI.toString(16)}",
                                BuildConfig.CHAT_ID
                            )
                            return@withContext Result.success()
                        }

                        current = secp256k1.pubKeyCombine(arrayOf(current, g))
                    }

                    ensureActive()
                    yield()
                    if (batch % 0x1FF == 0) delay(5_000)
                }

            }

            val outputData = workDataOf("progress" to "done")
//            setProgress(workDataOf("progress" to "done"))
            Result.success(outputData)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }

}