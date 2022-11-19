import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class StaffFrame extends CustomerFrame {
	final int FRAME_WIDTH = 600;
	final int FRAME_HEIGHT = 500;
	private JButton checkInventory, checkOrder;
	private String imagePath;
	private BufferedImage image;
	private JPanel titlePanel, buttonPanel, operatePanel, overallPanel;
	private JLabel titleLabel;

	public StaffFrame() {
		createComp();
		creteButton();
//		setResizable(false);
//		pack();
		createPanel();
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setTitle("Staff");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private void createComp() {
		titleLabel = new JLabel("Staff Page");
		titleLabel.setFont(new Font("Dialog", 1, 60));

		imagePath = "/Staff.png";
		LoadFile();
		JScrollPane scrollPane = new JScrollPane(new JLabel(new ImageIcon(image)));
		// getContentPane().add(scrollPane);
	}

	private void LoadFile() {
		try {
			URL imgURL = RegisterFrame.class.getResource(imagePath);
			image = ImageIO.read(imgURL);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Fail to load: " + imagePath);
			image = null;
		}
	}

	private void creteButton() {
		checkInventory = new JButton("Check Inventory");
		checkInventory.setActionCommand("Check Inventory");
		checkOrder = new JButton("Check Order");
		checkOrder.setActionCommand("Check Order");
		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				String command = event.getActionCommand();

				switch (command) {
				case ("Check Inventory"):
					close();
					new InventoryFrame().open();
					break;
				case ("Check Order"):
					close();
					new OrderListFrame().open();
					break;
				}
			}
		}
		ActionListener listener = new ButtonListener();
		checkInventory.addActionListener(listener);
		checkOrder.addActionListener(listener);
	}

	private void createPanel() {
		titlePanel = new JPanel();
		titlePanel.add(Box.createVerticalStrut(220));
		titlePanel.add(titleLabel);
		buttonPanel = new JPanel();
		buttonPanel.add(checkInventory);
		buttonPanel.add(Box.createRigidArea(new Dimension(80, 0)));
		buttonPanel.add(checkOrder);
		operatePanel = new JPanel();
		operatePanel.add(Box.createVerticalStrut(150));
		operatePanel.add(buttonPanel);
		overallPanel = new JPanel(new BorderLayout());
		overallPanel.add(titlePanel, BorderLayout.NORTH);
		overallPanel.add(operatePanel, BorderLayout.CENTER);
		add(overallPanel);
	}
}
