package com.ium.easyreps.task

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class LoginTask: CoroutineScope {
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun cancel() {
        job.cancel()
    }

    fun execute() = launch {
        onPreExecute()
        val result = doInBackground()
        onPostExecute(result)
    }

    private suspend fun doInBackground(): Any = withContext(Dispatchers.IO) {
        return@withContext
    }

    private fun onPreExecute() {

    }

    private fun onPostExecute(result: Any) {

    }
}