import io.reactivex.Flowable
import io.reactivex.Observable

fun main() {
    Flowable.just("001", "002", "003").subscribe { println(it) }
    Observable.fromArray("101", "102", "103").subscribe { println(it) }

    val flow = Flowable.range(1, 5)
        .map { v -> v * v }
        .filter { v -> v % 3 == 0 }

    flow.subscribe { println(it) }
}
