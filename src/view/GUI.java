package view;

import javax.swing.*;

import model.Buffer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
	private JFrame frame;
	private JPanel controlPanel;
	private JButton addButton;
	private JButton removeButton;
	private JPanel progressBarPanel;
	private JProgressBar progressBar;
	private Buffer buffer = null;

	public void createAndShowGUI(int maxAmount, Buffer buffer) {
		this.buffer = buffer;
		frame = new JFrame("AvJavaSlutprojekt");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 300);

		controlPanel = new JPanel();
		addButton = new JButton("Add Producer");
		removeButton = new JButton("Remove Producer");

		progressBarPanel = new JPanel();
		progressBarPanel.setLayout(new BorderLayout());
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);

		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateProductIndicator(maxAmount);
			}
		});

		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateProductIndicator(maxAmount);
			}
		});

		controlPanel.add(addButton);
		controlPanel.add(removeButton);

		progressBarPanel.add(progressBar, BorderLayout.CENTER);

		frame.add(controlPanel, BorderLayout.NORTH);
		frame.add(progressBarPanel, BorderLayout.CENTER);

		updateProductIndicator(maxAmount);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void updateProductIndicator(double maxAmount) {
		// numProducts = (numWorkers / maxAmount) * 100;
		// progressBar.setValue((int) numProducts);
		progressBar.setValue(buffer.getSize());

		// Change the progress bar color
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
}
