package controller;

import model.Buffer;
import model.Item;

public class Producer implements Runnable {
	Buffer buffer;
	private boolean isRunning = true;
	private int speed = 0;

	public Producer(Buffer buffer, int speed) {
		this.buffer = buffer;
		this.speed = speed;
	}

	public void stopProducing() {
		isRunning = false;
	}

	@Override
	public void run() {
		while (isRunning) {
			try {
				Thread.sleep(speed * 1000);

				buffer.add(new Item("" + (int) (Math.random() * 10)));
			} catch (InterruptedException e) {
				stopProducing();
				Thread.currentThread().interrupt();
			}
		}
	}
}
