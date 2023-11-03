package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import model.Buffer;
import model.Consumer;
import model.Producer;
import view.GUI;

public class Controller {
	private LinkedList<Thread> producerList = new LinkedList<Thread>();
	private LinkedList<Thread> consumerList = new LinkedList<Thread>();
	private Buffer buffer;
	private final int maxAmount = 15;
	private GUI gui;

	public void start() {
		buffer = new Buffer();
		gui = new GUI(this, buffer);
		SwingUtilities.invokeLater(() -> gui.createAndShowGUI(maxAmount));

		createConsumersAndProducers();

		timerUpdate();
	}

	private void createConsumersAndProducers() {
		for (int i = 0; i < getRandom(3, maxAmount - 1); i++) {
			addProducer();
		}

		for (int i = 0; i < getRandom(3, maxAmount - 1); i++) {
			addConsumer();
		}
	}

	private int getRandom(int minValue, int maxValue) {
		return (int) (Math.random() * (maxValue - minValue + 1) + minValue);
	}

	private void addConsumer() {
		Thread consumer = new Thread(new Consumer(buffer, getRandom(1, 10)));
		consumer.start();
		consumerList.add(consumer);
	}

	public void addProducer() {
		int speed = getRandom(1, 10);
		Thread producer = new Thread(new Producer(buffer, speed));
		producer.start();
		producerList.add(producer);
		gui.logMessage("Added producers! Total: " + producerList.size() + ". Producer speed = " + speed);
	}

	public void removeProducer() {
		if (!producerList.isEmpty()) {
			Thread producerThread = producerList.removeFirst();
			producerThread.interrupt();
			gui.logMessage("Removed producers! Total: " + producerList.size());
		} else {
			gui.logMessage("No producers to remove!");
		}
	}

	private void timerUpdate() {
		// Update the progressbar every second
		Timer timer = new Timer(1000, (ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gui.updateProgressBar();
			}
		});
		timer.start();

		// Log amount of items every 10 seconds
		Timer timer2 = new Timer(10000, (ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gui.logMessage("Amount of products: " + buffer.getSize());
			}
		});
		timer2.start();
	}

	public int getProducerListSize() {
		return producerList.size();
	}
}
