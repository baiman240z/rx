import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlin.system.exitProcess

fun main() {
    val observable = Observable.create<String> { emitter ->
        println(
            Thread.currentThread().name + ":start sending"
        )

        val strings = arrayOf("o001", "o002", "o003", "o004", "o005")

        for (string in strings) {
            if (emitter.isDisposed) {
                exitProcess(0)
            }
            emitter.onNext(string)
        }

        emitter.onComplete()
    }

    observable.observeOn(Schedulers.computation()).subscribe(object : Observer<String> {
        override fun onSubscribe(d: Disposable) {
            println(
                Thread.currentThread().name + ": Ovservale start"
            )
        }

        override fun onNext(s: String) {
            println(
                Thread.currentThread().name + ": " + s
            )
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
        }

        override fun onComplete() {
            println(
                Thread.currentThread().name + ": onComplete"
            )
        }
    })

    Thread.sleep(500L)
}
