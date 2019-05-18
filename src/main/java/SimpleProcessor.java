import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class SimpleProcessor {
    public static void main(String[] args) throws Exception {
        PublishProcessor<String> processor = PublishProcessor.create();

        processor
            .observeOn(Schedulers.computation())
            .subscribe(new Subscriber<String>() {
                private Subscription subscription;

                @Override
                public void onSubscribe(Subscription s) {
                    System.out.println(
                        Thread.currentThread().getName() + ": Processor subscribe"
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

        for (int i = 0; i < 5; i++) {
             processor.onNext(String.format("p%03d", i + 1));
        }

        processor.onComplete();

        Thread.sleep(500L);
    }
}
