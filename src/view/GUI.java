package view;

import javax.swing.*;

import controller.Controller;
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
	private JTextArea logTextArea; // Add JTextArea for logging
	private JScrollPane logScrollPane; // Add a scroll pane for the text area
	private Buffer buffer = null;
	private Controller controller;

	public GUI(Controller controller) {
		this.controller = controller;
	}

	public void createAndShowGUI(int maxAmount, Buffer buffer) {
		this.buffer = buffer;
		frame = new JFrame("AvJavaSlutprojekt");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 400); // Increased the height to accommodate the log area

		controlPanel = new JPanel();
		addButton = new JButton("Add Producer");
		removeButton = new JButton("Remove Producer");

		progressBarPanel = new JPanel();
		progressBarPanel.setLayout(new BorderLayout());
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);

		// Create JTextArea for logging
		logTextArea = new JTextArea(10, 1); // Set rows and columns
		logTextArea.setEditable(false); // Make it read-only
		logScrollPane = new JScrollPane(logTextArea);

		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.addProducer();
				updateProductIndicator();
				//logMessage("Producer added.");
			}
		});

		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.removeProducer();
				updateProductIndicator();
				//logMessage("Producer removed.");
			}
		});

		controlPanel.add(addButton);
		controlPanel.add(removeButton);

		progressBarPanel.add(progressBar, BorderLayout.CENTER);

		frame.add(controlPanel, BorderLayout.NORTH);
		frame.add(progressBarPanel, BorderLayout.CENTER);
		frame.add(logScrollPane, BorderLayout.SOUTH);

		updateProductIndicator();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void updateProductIndicator() {
		progressBar.setValue(buffer.getSize());
		progressBar.setForeground(getColorForPercentage(buffer.getSize()));
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

	private void logMessage(String message) {
		logTextArea.setText("[" + new Date() + "] " + message + "\n");
	}
}
