import rx.Observable;
import rx.Subscriber;
import rx.functions.*;

public class RXSampleOperations {

	public static void main(String[] args) {
		
		// Just con conversion(map) y filtrado...
		Observable.just("First Message", "Second Message", "This is a large message", "Short message")
			.map(String::length)//.map(msg -> msg.length())
			.filter(length -> length < 15)
			.subscribe(/* OnNext */ System.out::println); 
		
		// similar a Just pero con un array...
		Observable.from(new Integer[]{1, 2, 3, 4, 5})
			.subscribe(number -> System.out.println("Number " +  number));
		
		// All: Aplicar inferencia a todos los elementos del observable (observadores)..
		Observable.just("type", "content", "author")
	        .all(field -> field != null)
	        .filter(areFieldNotNull-> areFieldNotNull)
	        .subscribe(result -> System.out.println("All fields are not null"));
		
		// groupBy...
		Observable.from(new Integer[]{1, 2, 3, 4, 5})
		    .groupBy(number -> number % 2 == 0 ? "Even" : "Odd")
		    .subscribe(group -> {
               group.count().subscribe(count ->
                  System.out.println(String.format("There are %d %s numbers", count, group.getKey()))
               );
		    });
		
		/*
		 * Al contrario que filter tanto contains como exits emiten siempre un observable, 
		 * filter en caso de ser falso el predicado no emite ningún observable. Además contains 
		 * y exists son bloqueantes al igual que all hasta que no les llegan todos los ítems no emiten.
		 */
		
		Observable.from(new Integer[]{1, 2, 3, 4, 5, 6})
	        .contains(5)
	        .subscribe(result -> System.out.println("Is 5 in numbers? " + result));
		
		Observable.just("Fabio", "Marcos", "Pedro", "Elena")
	        .exists(name -> name.length() > 5)
	        .subscribe(result -> System.out.println("Exists name with more than four chars? " + result));
		
		Observable.from(new Integer[]{1, 2, 3, 4, 5, 6})
		    .limit(3)
		    .subscribe(number -> System.out.println("Number " + number));
		
		/*
		 * IsEmpty
		 * 
		 * Con isEmpty podemos saber si no se emiten ítems, por ejemplo, en el siguiente fragmento de código 
		 * from “no emite” item debido a que numbers no tiene valores. Pongo “no emite” entre comillas porque 
		 * en realidad si emite un ítem pero vacío.
		 */
		Observable.from(new Integer[]{})
	        .isEmpty()
	        .subscribe(result -> System.out.println("Is array empty? " + result));
		
		// Test
		Observable obs = Observable.from(new Integer[]{1, 2, 3, 4, 5, 6})
	    .filter(val -> val % 2 == 0);
		obs.subscribe(result -> System.out.println("Testing: " + result));
		obs.toList().subscribe(result -> System.out.println("Testing List: " + result));
		
	}
	
	public static void main2(String[] args) {
		Observable<String> obs = Observable.just("First Message", "Second Message", "This is a large message", "Short message");
//        obs.subscribe( // OnNext
//           new Action1<String>() {
//             @Override
//             public void call(String s) {
//               System.out.println(s);
//             }
//        });
		obs = obs.filter(msg -> msg.length() < 15);
		obs.subscribe(/* OnNext */ System.out::println); 
	}

	
}
