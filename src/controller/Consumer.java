package controller;

import model.Buffer;

public class Consumer implements Runnable {
	Buffer buffer;
	boolean isRunning = true;
	int speed = 0;

	public Consumer(Buffer buffer, int speed) {
		this.buffer = buffer;
		this.speed = speed;
		System.out.println("Consumer speed: " + speed);
	}

	@Override
	public void run() {
		while (isRunning) {
			try {
				Thread.sleep(speed * 1000);
				buffer.remove();
				// System.out.println("Consumed: " + buffer.remove());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
