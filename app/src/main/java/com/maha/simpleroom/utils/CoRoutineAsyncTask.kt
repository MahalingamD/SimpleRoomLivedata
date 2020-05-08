package com.maha.simpleroom.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class CoRoutineAsyncTask<Params, Progress, Result> {

    open fun onPreExecute() {}

    abstract fun doInBackground(vararg values: Params?): Result

    open fun onProgressUpdate(vararg values: Progress?) {}

    open fun onPostExecute(result: Result?) {}

    open fun onCancelled(result: Result?) {}

    protected var isCancelled = false

    protected var isRunning = false

    open fun getstaus(): Boolean {
        return isRunning
    }


    protected fun publishProgress(vararg progress: Progress?) {
        GlobalScope.launch(Dispatchers.Main) {
            onProgressUpdate(*progress)
        }
    }

    fun execute(vararg params: Params?) {
        GlobalScope.launch(Dispatchers.Default) {

            withContext(Dispatchers.Main) {
                isRunning = true
                onPreExecute()
            }

            val result = doInBackground(*params)

            withContext(Dispatchers.Main) {
                isRunning = false
                onPostExecute(result)
            }

        }
    }

    fun cancel(mayInterruptIfRunning: Boolean) {

    }
}