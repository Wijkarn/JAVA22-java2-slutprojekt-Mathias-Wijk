package model;

public class Consumer implements Runnable {
	private Buffer buffer;
	private boolean isRunning = true;
	private int speed = 0;

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
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
