import rx.Observable;
import rx.Subscriber;
import rx.functions.*;

public class RXSampleCombinators {

	public static void main(String[] args) {
		
		Observable.concat(Observable.just(-1, 0, 1), Observable.just(3, 6, 2, 8, 5))
        	.subscribe(number -> System.out.println("Number " + number));
		
		/*
		 * Si queremos saber si dos observables emiten secuencialmente los mismo items utilizamos sequenceEqual.
		 */
		Observable.sequenceEqual(Observable.from(new Integer[]{1, 2, 3, 4, 5, 6}), Observable.just(1, 2, 3, 4, 5, 6))
        	.subscribe(result -> System.out.println("Is sequence equal? " + result));
		
		/*
		 * La siguiente operación nos sirve para combinar el ítem más reciente emitido por el primer observable 
		 * con otro item emitido por otro observable.
		 */
		Observable.combineLatest(Observable.from(new Integer[]{1, 2, 3}), Observable.from(new Integer[]{10, 20, 30, 40}), 
            (latestNumberInFirst, numberInSecond) -> latestNumberInFirst + numberInSecond)
            .subscribe(result -> System.out.println("Sum " + result));
		
		Observable.zip(
			Observable.from(new Integer[]{1, 2, 3, 4}), 
			Observable.from(new String[]{"First Number = "}),// , "Second Number = ", "Third Number = "}), 
	        (number, message) -> message + number
	        ).subscribe(System.out::println);
		
		
		
	}

	
}
