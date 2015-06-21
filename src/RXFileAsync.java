import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

import rx.Observable;
import rx.Subscriber;
import rx.functions.*;
import rx.schedulers.Schedulers;

public class RXFileAsync {

	public static void main(String[] args) {
		System.out.println("\n\n******************ASYNC********************\n");
		Thread.currentThread().setName("Main Thread");
		getFileObs(new File("c:\\obs1.txt"))
		    .subscribeOn(Schedulers.newThread())
		    //.toBlocking()
		    .forEach(files -> {
		      System.out.println("ForEach Thread = " + Thread.currentThread().getName());
		      System.out.println("files " + files);
		    });
		
		getFileObs(new File("c:\\obs2.txt"))
	    .subscribeOn(Schedulers.newThread())
	    //.toBlocking()
	    .forEach(files -> {
	      System.out.println("ForEach Thread = " + Thread.currentThread().getName());
	      System.out.println("files " + files);
	    });
	
		try {
			Thread.currentThread().sleep(100000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("***");
		
	}

	private static Observable<Long> getFileObs(File f) {
		System.out.println("Function getFileObs Thread = "
				+ Thread.currentThread().getName());
		return Observable.create(subscriber -> {
			System.out.println("Observable.create Thread = " + Thread.currentThread().getName());
			Long size;
			try {
				size = Long.valueOf(writeFile(f));
				subscriber.onNext(size);
				subscriber.onCompleted();
			} catch (Exception e) {
				subscriber.onError(e);
			}
		});
	}
	
	private static long writeFile(File f) throws Exception {
		System.out.println("WriteFile "+f+" in Thread = " + Thread.currentThread().getName());
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(f);
			bw = new BufferedWriter(fw);
			for (int i = 0; i < 35000; i++) {
				for (int j = 0; j < 500; j++) {
					bw.write("-");
				}
				bw.newLine();
				bw.flush();
				Thread.currentThread().yield();
			}
			bw.flush();
			return new File(f.getAbsolutePath()).length();
		} catch (Exception e) {
			throw e;
		} finally {
			try {bw.close();} catch (Throwable e2) {}
			try {fw.close();} catch (Throwable e2) {}
		}
	}
}
