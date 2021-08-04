package app.el_even.priceupdater.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

/**
 * @author el_even
 * @version 1.0
 */
class UpdateWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result = try {
        Result.success()
    } catch (e: Exception) {
        Result.failure()
    }
}