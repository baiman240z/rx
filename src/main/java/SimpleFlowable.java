import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class SimpleFlowable {
    public static void main(String[] args) throws Exception {
        Flowable<String> flow = Flowable.create(emitter -> {
            System.out.println(
                Thread.currentThread().getName() + ":start sending"
            );

            String[] strings = {"f001", "f002", "f003", "f004", "f005"};

            for (String string : strings) {
                if (emitter.isCancelled()) {
                    return;
                }
                emitter.onNext(string);
            }

            emitter.onComplete();
        }, BackpressureStrategy.BUFFER);

        flow.observeOn(Schedulers.computation()).subscribe(new Subscriber<String>() {
            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription s) {
                System.out.println(
                    Thread.currentThread().getName() + ": Flowable subscribe"
                );
                subscription = s;
                subscription.request(1L);
            }

            @Override
            public void onNext(String s) {
                System.out.println(
                    Thread.currentThread().getName() + ": " + s
                );
                subscription.request(1L);
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println(
                    Thread.currentThread().getName() + ": onComplete"
                );
            }
        });

        Thread.sleep(500L);
    }
}
