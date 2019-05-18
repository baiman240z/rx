import io.reactivex.Flowable;
import io.reactivex.Observable;

public class Simple {
    public static void main(String[] args) {
        Flowable.just("001", "002", "003").subscribe(System.out::println);
        Observable.fromArray("101", "102", "103").subscribe(System.out::println);

        Flowable<Integer> flow = Flowable.range(1, 5)
                .map(v -> v * v)
                .filter(v -> v % 3 == 0)
                ;

        flow.subscribe(System.out::println);
    }
}
