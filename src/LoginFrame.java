import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class LoginFrame extends RegisterFrame {
	final int FIELD_WIDTH = 20;
	private Member member;
	private JLabel titleLabel, usernameLabel, passwordLabel;
	private JTextField enterUsername, enterPassword;
	private JButton login, register;
	private String imagePath;
	private BufferedImage image;
	private JPanel titlePanel, usernamePanel, passwordPanel, buttonPanel, operatePanel, overallPanel;

	public LoginFrame() {
		createComp();
		creteButton();
//		setResizable(false);
//		pack();
		createPanel();
		setSize(600, 500);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void createComp() {
		titleLabel = new JLabel("Cake Order");
		titleLabel.setFont(new java.awt.Font("Dialog", 1, 80));

		usernameLabel = new JLabel("Username   ");
		usernameLabel.setFont(new java.awt.Font("Dialog", 1, 30));
		passwordLabel = new JLabel("Password   ");
		passwordLabel.setFont(new java.awt.Font("Dialog", 1, 30));
		enterUsername = new JTextField(FIELD_WIDTH);
		
		focus();
		
		enterPassword = new JPasswordField(FIELD_WIDTH);
		enterPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String username = enterUsername.getText();
				String password = enterPassword.getText();
				try {
					if (member.login(username, password) == true) {
						close();
						switch (member.getID()) {
						case "Customer":
							new CustomerFrame().open();
							break;
						case "Staff":
							new StaffFrame().open();
							break;
						case "Owner":
							new OwnerFrame().open();
							break;
						case "Supplier":
							new SupplierFrame().open();
							break;
						}
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
				enterUsername.setText("");
				enterPassword.setText("");
				focus();
			}
		});

		imagePath = "/Login.png";
		LoadFile();
		JScrollPane scrollPane = new JScrollPane(new JLabel(new ImageIcon(image)));
		// getContentPane().add(scrollPane);
	}
	
	private void focus() {
		this.addWindowListener( new WindowAdapter() {
		    public void windowOpened( WindowEvent e ){
		    	enterUsername.requestFocus();
		    }
		}); 
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
		member = new Member();
		login = new JButton("Login");
		login.setActionCommand("Login");
		register = new JButton("Register");
		register.setActionCommand("Register");
		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				String username = enterUsername.getText();
				String password = enterPassword.getText();
				String command = event.getActionCommand();

				switch (command) {
				case ("Login"):
					try {
						if (member.login(username, password) == true) {
							close();
							switch (member.getID()) {
							case "Customer":
								new CustomerFrame().open();
								break;
							case "Staff":
								new StaffFrame().open();
								break;
							case "Owner":
								new OwnerFrame().open();
								break;
							case "Supplier":
								new SupplierFrame().open();
								break;
							}
							break;
						}
					} catch (Exception e) {
						System.out.println(e.getMessage());
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
					enterUsername.setText("");
					enterPassword.setText("");
					break;
				case ("Register"):
					new RegisterFrame().open();
					break;
				}
			}
		}
		ActionListener listener = new ButtonListener();
		login.addActionListener(listener);
		register.addActionListener(listener);
	}

	private void createPanel() {
		titlePanel = new JPanel();
		titlePanel.add(Box.createRigidArea(new Dimension(0, 180)));
		titlePanel.add(titleLabel);
		usernamePanel = new JPanel();
		usernamePanel.add(usernameLabel);
		usernamePanel.add(enterUsername);
		passwordPanel = new JPanel();
		passwordPanel.add(passwordLabel);
		passwordPanel.add(enterPassword);
		buttonPanel = new JPanel();
		buttonPanel.add(register);
		buttonPanel.add(Box.createRigidArea(new Dimension(80, 0)));
		buttonPanel.add(login);
		operatePanel = new JPanel();
		operatePanel.add(usernamePanel);
		operatePanel.add(passwordPanel);
		operatePanel.add(Box.createRigidArea(new Dimension(0, 100)));
		operatePanel.add(buttonPanel);
		overallPanel = new JPanel(new BorderLayout());
		overallPanel.add(titlePanel, BorderLayout.NORTH);
		overallPanel.add(operatePanel, BorderLayout.CENTER);
		add(overallPanel);
	}
}
