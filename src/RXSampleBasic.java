import rx.Observable;
import rx.Subscriber;
import rx.functions.*;

public class RXSampleBasic {

	public static void main(String[] args) {
		// observado u observable...
		Observable<String> userName = Observable
				.create(new Observable.OnSubscribe<String>() {
					@Override
					public void call(Subscriber<? super String> subscriber) {
						try {
							subscriber.onNext("Fabio Santana"); // Enviamos el
																// dato
							subscriber.onCompleted(); // Avisamos que hemos
														// acabado
						} catch (Exception ex) {
							subscriber.onError(ex); // Avisamos de un error
						}
					}
				});
		userName.subscribe(
		// On Next
				new Action1<String>() {
					@Override
					public void call(String userName) {
						System.out.println("On Next: "+userName);
					}
				},

				// On Error
				new Action1<Throwable>() {
					@Override
					public void call(Throwable throwable) {
						System.out.println("On Error: "+throwable);
					}
				},

				// On Complete
				new Action0() {
					@Override
					public void call() {
						System.out.println("On Complete...");
					}
				});

	}

}
