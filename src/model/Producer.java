package model;

public class Producer implements Runnable {
	private Buffer buffer;
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
				if (buffer.getSize() < 100) {
					buffer.add(new Item("" + (int) (Math.random() * 10)));
				}
			} catch (InterruptedException e) {
				stopProducing();
				Thread.currentThread().interrupt();
			}
		}
	}
}
