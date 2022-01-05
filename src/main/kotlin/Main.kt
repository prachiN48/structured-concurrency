import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    cancelParentJobAndRecursivelyChildJobIsCancelled()
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