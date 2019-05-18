import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

fun main() {
    val processor = PublishProcessor.create<String>()

    processor
        .observeOn(Schedulers.computation())
        .subscribe(object : Subscriber<String> {
            private var subscription: Subscription? = null

            override fun onSubscribe(s: Subscription) {
                println(
                    Thread.currentThread().name + ": Processor subscribe"
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

    for (i in 0..4) {
        processor.onNext(String.format("p%03d", i + 1))
    }

    processor.onComplete()

    Thread.sleep(500L)
}
