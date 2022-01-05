import kotlinx.coroutines.*

fun main() {
    cancelParentAndLetChildContinueExecution()
}

private fun cancelParentJobAndRecursivelyChildJobIsCancelled() {
    runBlocking {
        val parentJob = launch(Dispatchers.Default) {
            launch(Dispatchers.Default) {
                var count = 1
                while (count <= 5) {
                    println("Count: $count")
                    delay(100)
                    count++
                }
            }
        }

        delay(250)
        println("Cancelling parent job")
        parentJob.cancel()
    }
}

private fun jobContinuesExecutionEvenAfterCancelIsCalled() {
    runBlocking{
        val job = launch(Dispatchers.Default) {
            var count = 1
            val startTime = System.currentTimeMillis()
            var nextPrintTime = startTime
            while (count <= 5) {
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("Count: $count")
                    nextPrintTime += 100L
                    count++
                }
            }
        }

        delay(250)
        println("Cancelling job")
        job.cancelAndJoin() // job will cancel but will wait till its execution is completed.
        println("Job completed")
    }
}

private fun cancelParentAndLetChildContinueExecution() {
    runBlocking {
        val parentJob = launch(Dispatchers.Default) {
            val childJob = launch(Dispatchers.Default) {
                withContext(NonCancellable) {
                    var count = 1
                    while (count <= 5) {
                        println("Count: $count")
                        delay(100)
                        count++
                    }
                }
            }
        }

        delay(250)
        println("Cancelling parent job")
        parentJob.cancel() // parent job will cancel but child job will continue execution
        println("Parent job cancelled")

        parentJob.join()
        println("Parent job completed")
    }
}