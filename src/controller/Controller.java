package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import logger.Logger;
import model.Buffer;
import view.GUI;

public class Controller {
	private LinkedList<Thread> producerList = new LinkedList<Thread>();
	private LinkedList<Thread> consumerList = new LinkedList<Thread>();
	private Buffer buffer;
	private int maxAmount = 15;
	private GUI gui;
	private Timer timer;
	Logger logger;

	public void start() {
		gui = new GUI(this);
		buffer = new Buffer();
		SwingUtilities.invokeLater(() -> gui.createAndShowGUI(maxAmount, buffer));
		
		logger = Logger.getInstance();

		for (int i = 0; i < getRandom(1, maxAmount); i++) {
			addProducer();
		}

		for (int i = 0; i < getRandom(1, maxAmount); i++) {
			Thread consumer = new Thread(new Consumer(buffer, getRandom(1, 10)));
			consumer.start();
			consumerList.add(consumer);
		}

		timerUpdate();
	}

	private int getRandom(int maxValue, int minValue) {
		return (int) (Math.random() * (maxValue - minValue + 1) + minValue);
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
		}
		gui.logMessage("Removed producers! Total: " + producerList.size());
	}

	private void timerUpdate() {
		int delay = 1000;
		timer = new Timer(delay, (ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gui.updateProgressBar();
			}
		});
		timer.start();
	}

	public int getProducerListSize() {
		return producerList.size();
	}
}
