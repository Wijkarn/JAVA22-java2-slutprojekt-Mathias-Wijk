package view;

import javax.swing.*;

import controller.Controller;
import logger.Logger;
import model.Buffer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class GUI {
	private JFrame frame;
	private JPanel controlPanel;
	private JButton addButton;
	private JButton removeButton;
	private JPanel progressBarPanel;
	private JProgressBar progressBar;
	private JTextArea logTextArea;
	private JScrollPane logScrollPane;
	private Buffer buffer;
	private Controller controller;
	Logger logger;

	public GUI(Controller controller) {
		this.controller = controller;
		logger = Logger.getInstance();

		logTextArea = new JTextArea(10, 20);
		logTextArea.setEditable(false);
		logScrollPane = new JScrollPane(logTextArea);
	}

	public void createAndShowGUI(int maxAmount, Buffer buffer) {
		this.buffer = buffer;
		frame = new JFrame("AvJavaSlutprojekt");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 400);

		controlPanel = new JPanel();
		addButton = new JButton("Add Producer");
		removeButton = new JButton("Remove Producer");

		progressBarPanel = new JPanel();
		progressBarPanel.setLayout(new BorderLayout());
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);

		createButtons();

		controlPanel.add(addButton);
		controlPanel.add(removeButton);

		progressBarPanel.add(progressBar, BorderLayout.CENTER);

		frame.add(controlPanel, BorderLayout.NORTH);
		frame.add(progressBarPanel, BorderLayout.CENTER);
		frame.add(logScrollPane, BorderLayout.SOUTH);

		updateProgressBar();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void updateProgressBar() {
		int size = buffer.getSize();
		progressBar.setValue(size);
		progressBar.setForeground(getColorForPercentage(size));
		if (size <= 10) {
			logMessage("WARNING LOW PRODUCTS!");
		} else if (size >= 90) {
			logMessage("WARNING TO MANY PRODUCTS!");
		}
	}

	private Color getColorForPercentage(double percentage) {
		if (percentage >= 90) {
			return Color.GREEN;
		} else if (percentage <= 10) {
			return Color.RED;
		} else {
			return Color.BLUE;
		}
	}

	public void logMessage(String message) {
		logTextArea.append("[" + new Date() + "] " + message + "\n");
		logger.logText(message);
		System.out.println(message);
	}
	
	private void createButtons() {
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.addProducer();
				updateProgressBar();
			}
		});

		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.removeProducer();
				updateProgressBar();
			}
		});
	}
}
