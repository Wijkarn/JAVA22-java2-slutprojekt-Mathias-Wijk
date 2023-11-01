package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import model.Buffer;
import view.GUI;

public class Controller {
	LinkedList<Thread> producerList = new LinkedList<Thread>();
	LinkedList<Thread> consumerList = new LinkedList<Thread>();
	Buffer buffer = new Buffer();
	int maxAmount = 15;
	GUI gui = null;
	private Timer timer;

	public void start() {

		for (int i = 0; i < getRandom(maxAmount, 1); i++) {
			addProducer();
		}

		for (int i = 0; i < getRandom(maxAmount, 1); i++) {
			Thread consumer = new Thread(new Consumer(buffer, getRandom(10, 1)));
			consumer.start();
			consumerList.add(consumer);
		}

		System.out.println("Amount of producers: " + producerList.size());
		System.out.println("Amount of consumers: " + consumerList.size());

		gui = new GUI();

		SwingUtilities.invokeLater(() -> gui.createAndShowGUI(maxAmount, buffer));
		
		int delay = 1000;
	    timer = new Timer(delay, (ActionListener) new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            gui.updateProductIndicator(maxAmount);
	        }
	    });
	    timer.start();
	}

	private int getRandom(int maxValue, int minValue) {
		return (int) (Math.random() * (maxValue - minValue + 1) + minValue);
	}

	public void addProducer() {
		Thread producer = new Thread(new Producer(buffer, getRandom(10, 1)));
		producer.start();
		producerList.add(producer);
	}

	public void removeProducer() {

	}
}
