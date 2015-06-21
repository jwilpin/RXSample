import rx.Observable;
import rx.Subscriber;
import rx.functions.*;
import rx.schedulers.Schedulers;

public class RXSampleAsync {

	public static void main(String[] args) {
		// Sync
		Thread.currentThread().setName("Main Thread");
		getNumbers()
				.flatMap(
						numbers -> {
							System.out.println("FlatMap Thread = "
									+ Thread.currentThread().getName());
							return Observable.from(numbers);
						})
				.map(number -> {
					System.out.println("Map Thread = "
							+ Thread.currentThread().getName());
					return number % 2;
				})
				.forEach(
						number -> {
							System.out.println("ForEach Thread = "
									+ Thread.currentThread().getName());
							System.out.println("Is Odd " + (number == 0));
						});
		
		// Async
		System.out.println("\n\n******************ASYNC********************\n");
		Thread.currentThread().setName("Main Thread");
		getNumbers()
		    .subscribeOn(Schedulers.newThread())
		    .flatMap(numbers -> {
		      System.out.println("FlatMap Thread = " + Thread.currentThread().getName());
		      return Observable.from(numbers);
		    })
		    .map(number -> {
		      System.out.println("Map Thread = " + Thread.currentThread().getName());
		      return number % 2;
		    })
		    .toBlocking()
		    .forEach(number -> {
		      System.out.println("ForEach Thread = " + Thread.currentThread().getName());
		      System.out.println("Is Odd " + (number != 0));
		    });
		
		
	}

	private static Observable<Integer[]> getNumbers() {
		System.out.println("Function getNumbers Thread = "
				+ Thread.currentThread().getName());
		return Observable.create(subscriber -> {
			System.out.println("Observable.create Thread = "
					+ Thread.currentThread().getName());
			Integer[] numbers = { 3, 6, 2 };
			subscriber.onNext(numbers);
			subscriber.onCompleted();
		});
	}
}
