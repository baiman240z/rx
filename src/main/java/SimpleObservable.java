import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SimpleObservable {
    public static void main(String[] args) throws Exception {
        Observable<String> observable = Observable.create(emitter -> {
            System.out.println(
                Thread.currentThread().getName() + ":start sending"
            );

            String[] strings = {"o001", "o002", "o003", "o004", "o005"};

            for (String string : strings) {
                if (emitter.isDisposed()) {
                    return;
                }
                emitter.onNext(string);
            }

            emitter.onComplete();
        });

        observable.observeOn(Schedulers.computation()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println(
                    Thread.currentThread().getName() + ": Ovservale start"
                );
            }

            @Override
            public void onNext(String s) {
                System.out.println(
                    Thread.currentThread().getName() + ": " + s
                );
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
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
