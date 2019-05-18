import io.reactivex.*
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import kotlin.system.exitProcess

fun main() {
    val flow = Flowable.create<String>({ emitter ->
        println(
            Thread.currentThread().name + ":start sending"
        )

        val strings = arrayOf("f001", "f002", "f003", "f004", "f005")

        for (string in strings) {
            if (emitter.isCancelled) {
                exitProcess(0)
            }
            emitter.onNext(string)
        }

        emitter.onComplete()
    }, BackpressureStrategy.BUFFER)

    flow.observeOn(Schedulers.computation()).subscribe(object : Subscriber<String> {
        private var subscription: Subscription? = null

        override fun onSubscribe(s: Subscription) {
            println(
                Thread.currentThread().name + ": Flowable subscribe"
            )
            subscription = s
            subscription!!.request(1L)
        }

        override fun onNext(s: String) {
            println(
                Thread.currentThread().name + ": " + s
            )
            subscription!!.request(1L)
        }

        override fun onError(t: Throwable) {
            t.printStackTrace()
        }

        override fun onComplete() {
            println(
                Thread.currentThread().name + ": onComplete"
            )
        }
    })

    Thread.sleep(500L)
}
