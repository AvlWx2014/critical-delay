package viewmodel

import kotlinx.coroutines.*
import model.ProgressCallback
import model.determinateProgress
import java.util.concurrent.atomic.AtomicInteger

class MainScreenViewModel(applicationScope: CoroutineScope) {
    private val viewModelScope = applicationScope +
            Dispatchers.Default +
            SupervisorJob(parent = applicationScope.coroutineContext[Job]).apply {
                invokeOnCompletion { println("Completing view model supervisor job...") }
            }

    fun doSomethingWithProgress(progressCallback: ProgressCallback, onComplete: () -> Unit = {}) {
        viewModelScope.launch(Dispatchers.Default) {
            val count = AtomicInteger(0)
            while (count.getAndIncrement() <= 100) {
                launch(Dispatchers.Main) {
                    progressCallback(determinateProgress(count.get(), 100))
                }

                delay(50L)
            }

            withContext(Dispatchers.Main) {
                onComplete()
            }
        }
    }
}