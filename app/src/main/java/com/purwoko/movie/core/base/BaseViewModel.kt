package com.purwoko.movie.core.base

import androidx.lifecycle.ViewModel
import com.purwoko.movie.utils.errorMessage
import com.purwoko.movie.utils.runOnUi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import timber.log.Timber.Forest.w

abstract class BaseViewModel : ViewModel() {

    fun launchOnUi(
        onRequest: suspend CoroutineScope.() -> Unit,
        onError: (String) -> Unit,
        onFinally: suspend CoroutineScope.() -> Unit = {},
        handleCancellationExceptionManually: Boolean = true
    ): Job {
        return runOnUi {
            tryCatch(onRequest, onError, onFinally, handleCancellationExceptionManually)
        }
    }

    private suspend fun tryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: (String) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit,
        handleCancellationExceptionManually: Boolean = false
    ) {
        coroutineScope {
            try {
                tryBlock()
            } catch (e: Exception) {
                if (e !is CancellationException || handleCancellationExceptionManually) {
                    val error = e.errorMessage()
                    catchBlock(error)
                } else {
                    w("Close exception")
                }
            } finally {
                finallyBlock()
            }
        }
    }

}