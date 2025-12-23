package com.exxlexxlee.puzzle.crypto

import java.math.BigInteger

class Base58 {
    companion object {
        private const val BASE58_ALPHABET =
            "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz"

    }

    private val sha256 = Digest.sha256()

    fun addressToPubKeyHash(address: String): ByteArray {
        val decoded = base58Decode(address)
        require(decoded.size == 25) { "Invalid address length" }

        val payload = decoded.copyOfRange(0, 21)
        val checksum = decoded.copyOfRange(21, 25)

        val hash = sha256.hash(sha256.hash(payload)).copyOfRange(0, 4)
        require(hash.contentEquals(checksum)) { "Bad checksum" }
        // payload = [version(1) | pubKeyHash(20)]
        return payload.copyOfRange(1, 21)
    }


    private fun base58Decode(input: String): ByteArray {
        var num = BigInteger.ZERO
        for (char in input) {
            val index = BASE58_ALPHABET.indexOf(char)
            require(index >= 0) { "Invalid Base58 char" }
            num = num.multiply(BigInteger.valueOf(58)).add(BigInteger.valueOf(index.toLong()))
        }
        val bytes = num.toByteArray().let {
            if (it[0] == 0.toByte()) it.drop(1).toByteArray() else it
        }
        val leadingZeros = input.takeWhile { it == '1' }.count()
        return ByteArray(leadingZeros) + bytes
    }
}