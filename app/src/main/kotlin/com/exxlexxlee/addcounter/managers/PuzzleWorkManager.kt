package com.exxlexxlee.addcounter.managers

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.exxlexxlee.puzzle.PuzzleWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit

class PuzzleWorkManager(context: Context) {
    companion object {
        private const val WORK_TAG = "PUZZLE"
        private const val WORK_NAME = "puzzle_work"
    }

    private val workManager = WorkManager.getInstance(context)

    fun isWorkRunning(): Boolean {
        val existingWork = workManager.getWorkInfosByTag(WORK_TAG).get()
        return existingWork.any { !it.state.isFinished }
    }

    fun startWork() {
        if (isWorkRunning()) return
        val request = OneTimeWorkRequestBuilder<PuzzleWorker>()
            .addTag(WORK_TAG)
            .setInitialDelay(5, TimeUnit.MINUTES)
            .build()
        workManager.enqueueUniqueWork(WORK_NAME, ExistingWorkPolicy.KEEP, request)
    }

    fun cancelWork() {
        workManager.cancelUniqueWork(WORK_NAME)
    }

    fun observeProgress(): Flow<String> {
        return workManager.getWorkInfosForUniqueWorkFlow(WORK_NAME)
            .map { workInfoList ->
                val workInfo = workInfoList.firstOrNull()
                if (workInfo?.state == WorkInfo.State.SUCCEEDED) {
                    workInfo.outputData.getString("progress") ?: ""
                } else ""
            }
    }
}