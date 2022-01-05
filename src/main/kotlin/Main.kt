import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    runBlocking {
        val parentJob = launch {
            val childJob = launch {
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