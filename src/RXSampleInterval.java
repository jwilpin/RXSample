import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.*;
import rx.schedulers.Schedulers;

public class RXSampleInterval {

	public static void main(String[] args) {
		final CountDownLatch latch = new CountDownLatch(5);
		latch.countDown();
		Observable
        .interval(1, TimeUnit.SECONDS)
        .take(10)
        .subscribe(new Action1<Long>() {
            @Override
            public void call(Long counter) {
                System.out.println("Got: " + counter);
            }
        });
		
		Observable
        .interval(2, TimeUnit.SECONDS)
        .take(10)
        .subscribe(new Action1<Long>() {
            @Override
            public void call(Long counter) {
                System.out.println("Got2: " + counter);
            }
        });
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
