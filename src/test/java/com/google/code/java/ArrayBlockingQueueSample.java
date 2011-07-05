package com.google.code.java;

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueSample {

	public static void main(String[] args) throws InterruptedException {
		final ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);
		Thread ta = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					queue.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		Thread tb = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Integer take = queue.take();
					System.out.println("b take :" + take);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		ta.start();
		tb.start();
		System.out.println("all started");
		Thread.sleep(1000);
		ta.interrupt();
		ta.join();
		System.out.println("ta join");
		tb.join();
		System.out.println("tb join");
	}

}
