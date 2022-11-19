import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class CartFrame extends CustomerFrame {
	final int FRAME_WIDTH = 500;
	final int FRAME_HEIGHT = 700;
	final int FIELD_WIDTH = 2;
	final int TITLE_SIZE = 60;
	final int SUBTITLE_SIZE = 30;
	final int PRICE_SIZE = 16;
	private JButton clear, checkout;
	private String imagePath;
	private BufferedImage image;
	private JPanel productPanel, operatePanel, overallPanel;
	private JLabel titleLabel, productLabel, clearLabel, subLabel, subpLabel, feeLabel, feepLabel, totalLabel,
			totalpLabel;
	private ArrayList<JLabel> productPrices;
	private Order order;

	public CartFrame(Order order) {
		this.order = order;

		createComp();
		creteButton();
		setResizable(false);
//		pack();
		createPanel();
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setTitle("Cart");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private void createComp() {
		titleLabel = new JLabel("Cart Page", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Dialog", 1, TITLE_SIZE));
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		productLabel = new JLabel("Products");
		productLabel.setFont(new Font("Dialog", 1, SUBTITLE_SIZE));
		clearLabel = new JLabel("Clear Cart");
		clearLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		subLabel = new JLabel("Subtotal");
		subLabel.setFont(new Font("Dialog", 1, SUBTITLE_SIZE));
		subLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		int subtotal = 0;
		for (Item item : order.getItems()) {
			subtotal += item.getPrice();
		}
		subpLabel = new JLabel("NT$" + subtotal);
		subpLabel.setFont(new Font("Dialog", 1, PRICE_SIZE));
		subpLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		feeLabel = new JLabel("Delivery Fee");
		feeLabel.setFont(new Font("Dialog", 1, SUBTITLE_SIZE));
		feeLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		feepLabel = new JLabel("NT$" + 100);
		feepLabel.setFont(new Font("Dialog", 1, PRICE_SIZE));
		feepLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		totalLabel = new JLabel("Total");
		totalLabel.setFont(new Font("Dialog", 1, SUBTITLE_SIZE));
		totalpLabel = new JLabel("NT$" + (subtotal + 100));
		totalpLabel.setFont(new Font("Dialog", 1, PRICE_SIZE));
		totalpLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

		imagePath = "/Cart Page.png";
		loadFile();
		JScrollPane scrollPane = new JScrollPane(new JLabel(new ImageIcon(image)));
//		 getContentPane().add(scrollPane);
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
		clear = loadButtonImage("/clear.png", 30, 30);
		clear.setAlignmentX(Component.CENTER_ALIGNMENT);
		clear.setActionCommand("Clear");
		checkout = new JButton("Check Out");
		checkout.setActionCommand("Check Out");
		checkout.setAlignmentX(Component.CENTER_ALIGNMENT);
		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				String command = event.getActionCommand();

				switch (command) {
				case ("Clear"):
					productPanel = new JPanel();
					order.clear();
					setVisible(false); 
					new CartFrame(order).open(); 
					break;
				case ("Check Out"):
					System.out.println(order.checkout());
					close();
					 new CheckoutFrame(order);
					break;
				}
			}
		}
		ActionListener listener = new ButtonListener();
		clear.addActionListener(listener);
		checkout.addActionListener(listener);
	}

	private JButton createMinusButton(String name, JTextField amounts, int price) {
		JButton minus = loadButtonImage("/minus.png", 30, 30);
		minus.setMaximumSize(minus.getPreferredSize());
		minus.setActionCommand("Minus");
		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				String command = event.getActionCommand();

				switch (command) {
				case ("Minus"):
					if (Integer.parseInt(amounts.getText()) - 1 > 0) {
						int amount = Integer.parseInt(amounts.getText()) - 1;
						amounts.setText(Integer.toString(amount));
						for (JLabel label : productPrices) {
							
						}
						JLabel priceLabel = new JLabel();
						priceLabel.setText("NT$" + Integer.toString(amount * price));
						order.addItem(order.findItem(name), amount);
					}
					break;
				}
			}
		}
		ActionListener listener = new ButtonListener();
		minus.addActionListener(listener);
		return minus;
	}

	private JButton createPlusButton(String name, JTextField amounts, int price) {
		JButton plus = loadButtonImage("/plus.png", 30, 30);
		plus.setActionCommand("Plus");
		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				String command = event.getActionCommand();

				switch (command) {
				case ("Plus"):
					if (Integer.parseInt(amounts.getText()) + 1 <= 99) {
						int amount = Integer.parseInt(amounts.getText()) + 1;
						int price = Integer.parseInt(priceLabel.getText().replace("NT$", ""));
						amounts.setText(Integer.toString(amount));
						priceLabel.setText("NT$" + Integer.toString(amount * price));
						order.addItem(order.findItem(name), amount);
					}
					break;
				}
			}
		}
		ActionListener listener = new ButtonListener();
		plus.addActionListener(listener);
		return plus;
	}

	private JPanel createProductPanel(String name, int price, String path) {
		JPanel productPanel = new JPanel();
		ImageIcon image = new ImageIcon(loadImage(path));
		image.setImage(image.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
		JLabel icon = new JLabel();
		icon.setIcon(image);

		JLabel nameLabel = new JLabel(name);
		nameLabel.setFont(new Font("Dialog", 1, 20));
		nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JTextField amounts = new JTextField("1");
		amounts.setPreferredSize(new Dimension(30, 30));
		amounts.setHorizontalAlignment(JTextField.CENTER);
		
		JLabel priceLabel = new JLabel("NT$" + price);
		priceLabel.setFont(new Font("Dialog", 1, PRICE_SIZE));
		priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		products.add(priceLabel);
		
		JPanel calPanel = new JPanel();
		calPanel.add(createMinusButton(name, amounts, price));
		calPanel.add(Box.createHorizontalStrut(1));
		calPanel.add(amounts);
		calPanel.add(Box.createHorizontalStrut(1));
		calPanel.add(createPlusButton(name, amounts, price));

		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.add(nameLabel, BorderLayout.NORTH);
		rightPanel.add(Box.createVerticalStrut(5));
		rightPanel.add(calPanel, BorderLayout.SOUTH);

		Box box = new Box(BoxLayout.LINE_AXIS);
		box.add(icon);
		box.add(Box.createHorizontalStrut(10));
		box.add(rightPanel);
		box.add(Box.createHorizontalStrut(90));
		box.add(priceLabel);
		productPanel.add(box);
		return productPanel;
	}

	private void createPanel() {
		Box topBox = new Box(BoxLayout.Y_AXIS);
		topBox.add(titleLabel);
		Box topMiddleBox = new Box(BoxLayout.LINE_AXIS);
		topMiddleBox.add(productLabel);
		topMiddleBox.add(Box.createHorizontalStrut(160));
		Box clearBox = new Box(BoxLayout.Y_AXIS);
		clearBox.add(clear);
		clearBox.add(clearLabel);
		topMiddleBox.add(clearBox);
		topBox.add(Box.createVerticalStrut(30));
		topBox.add(topMiddleBox);

		Box productBox = new Box(BoxLayout.Y_AXIS);
		productPanel = new JPanel();
		for (int i = 0; i < order.getItems().size(); i++) {
			productBox.add(createProductPanel(order.getItems().get(i).getName(), order.getItems().get(i).getPrice(),
					"/cake.png"));
			if (i != order.getItems().size() - 1) {
				productBox.add(Box.createVerticalStrut(10));
			}
		}		
		productPanel.add(productBox);

		Box bottomBox = new Box(BoxLayout.Y_AXIS);
		Box subBox = new Box(BoxLayout.LINE_AXIS);
		subBox.add(subLabel);
		subBox.add(Box.createHorizontalStrut(100));
		subBox.add(subpLabel);
		Box feeBox = new Box(BoxLayout.LINE_AXIS);
		feeBox.add(feeLabel);
		feeBox.add(Box.createHorizontalStrut(100));
		feeBox.add(feepLabel);
		Box totalBox = new Box(BoxLayout.LINE_AXIS);
		totalBox.add(totalLabel);
		totalBox.add(Box.createHorizontalStrut(100));
		totalBox.add(totalpLabel);
		bottomBox.add(subBox);
		bottomBox.add(feeBox);
		bottomBox.add(totalBox);
		bottomBox.add(checkout);
		
		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(Box.createVerticalStrut(20));
		box.add(topBox);
		box.add(productPanel);
		box.add(bottomBox);

		overallPanel = new JPanel();
		overallPanel.add(box);
		add(overallPanel);
	}
}
