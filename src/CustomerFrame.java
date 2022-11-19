import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class CustomerFrame extends RegisterFrame {
	final int FRAME_WIDTH = 600;
	final int FRAME_HEIGHT = 500;
	final int FIELD_WIDTH = 10;
	private JButton cart;
	private String imagePath;
	private BufferedImage image;
	private JPanel titlePanel, cartPanel, operatePanel, overallPanel;
	private JLabel titleLabel, cartLabel;
	private Order order;
	private ItemManager itemManager;

	public CustomerFrame() {
		createItems();
		createComp();
		creteButton();
		setResizable(false);
//		pack();
		createPanel();
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setTitle("Customer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private void createItems() {
		order = new Order();
		itemManager = new ItemManager();

		File file = new File("Items.txt");
		Scanner scanner = new Scanner("");
		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String name = scanner.next();
				name = name.replace("_", " ");
				int price = scanner.nextInt();
				itemManager.addNewItem(name, price);
			}
		} catch (Exception e) {

		} finally {
			scanner.close();
		}
	}

	private void createComp() {
		titleLabel = new JLabel("Cake Order");
		titleLabel.setFont(new Font("Dialog", 1, 60));
		cartLabel = new JLabel("Cart", SwingConstants.CENTER);
		cartLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		imagePath = "/Customer.png";
		loadFile();
		JScrollPane scrollPane = new JScrollPane(new JLabel(new ImageIcon(image)));
		// getContentPane().add(scrollPane);
	}

	private void loadFile() {
		try {
			URL imgURL = RegisterFrame.class.getResource(imagePath);
			image = ImageIO.read(imgURL);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Fail to load: " + imagePath);
			image = null;
		}
	}

	private BufferedImage loadImage(String path) {
		URL imgURL;
		try {
			imgURL = CustomerFrame.class.getResource(path);
			BufferedImage img = ImageIO.read(imgURL);
			return img;
		} catch (Exception e) {
			e.getStackTrace();
			return null;
		}
	}

	private JButton loadButtonImage(String path, int width, int height) {
		JButton button = new JButton();
		ImageIcon image = new ImageIcon(loadImage(path));
		image.setImage(image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
		button.setIcon(image);
		button.setContentAreaFilled(false);
		button.setBorder(null);
		return button;
	}

	private void creteButton() {
		cart = loadButtonImage("/cart.png", 40, 40);
		cart.setAlignmentX(Component.CENTER_ALIGNMENT);
		cart.setActionCommand("Cart");
		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				String command = event.getActionCommand();

				switch (command) {
				case ("Cart"):
					new CartFrame(order).open();
					break;
				}
			}
		}
		ActionListener listener = new ButtonListener();
		cart.addActionListener(listener);
	}

	private JButton createAddButton(String name) {
		JButton add = new JButton("Add to Cart");
		add.setActionCommand("Add");
		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				String command = event.getActionCommand();

				switch (command) {
				case ("Add"):
					order.addItem(itemManager.findItem(name), 1);
					break;
				}
			}
		}
		ActionListener listener = new ButtonListener();
		add.addActionListener(listener);
		return add;
	}

	private JPanel createProductPanel(String name, String path) {
		JPanel productPanel = new JPanel();
		ImageIcon image = new ImageIcon(loadImage(path));
		image.setImage(image.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
		JLabel icon = new JLabel();
		icon.setIcon(image);
		JLabel nameLabel = new JLabel(name);
		nameLabel.setFont(new Font("Dialog", 1, 20));
		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.add(nameLabel, BorderLayout.NORTH);
		rightPanel.add(Box.createVerticalStrut(5));
		rightPanel.add(createAddButton(name), BorderLayout.SOUTH);
		productPanel.add(icon);
		productPanel.add(Box.createHorizontalStrut(10));
		productPanel.add(rightPanel);
		return productPanel;
	}

	private void createPanel() {
		titlePanel = new JPanel();
		titlePanel.add(Box.createVerticalStrut(150));
		titlePanel.add(Box.createHorizontalStrut(30));
		titlePanel.add(titleLabel);
		titlePanel.add(Box.createHorizontalStrut(30));
		cartPanel = new JPanel(new BorderLayout());
		cartPanel.add(cart);
		cartPanel.add(cartLabel, BorderLayout.SOUTH);
		titlePanel.add(cartPanel);
		operatePanel = new JPanel();
		for (int i = 0; i < itemManager.getItems().size(); i++) {
			operatePanel.add(createProductPanel(itemManager.getItems().get(i).getName(), "/cake.png"));
			if (i == 0 || i % 2 == 0) {
				operatePanel.add(Box.createHorizontalStrut(30));
			}
		}
		overallPanel = new JPanel(new BorderLayout());
		overallPanel.add(titlePanel, BorderLayout.NORTH);
		overallPanel.add(operatePanel, BorderLayout.CENTER);
		add(overallPanel);
	}
}
