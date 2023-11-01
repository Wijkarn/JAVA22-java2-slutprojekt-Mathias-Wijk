package controller;

import model.Buffer;
import model.Item;

public class Producer implements Runnable {
	Buffer buffer = null;
	boolean isRunning = true;
	int speed = 0;

	public Producer(Buffer buffer, int speed) {
		this.buffer = buffer;
		this.speed = speed;
		System.out.println("Producer speed: " + speed);
	}

	public void stopProducing() {
		isRunning = false;
	}

	public void start() {
		this.start();
	}

	@Override
	public void run() {
		while (isRunning) {
			try {
				Thread.sleep(speed * 1000);

				buffer.add(new Item("" + (int) (Math.random() * 10)));
			} catch (InterruptedException e) {
				isRunning = false;
				Thread.currentThread().interrupt();
			}
		}
	}
}
