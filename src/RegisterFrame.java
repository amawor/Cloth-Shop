import java.awt.BorderLayout;
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class RegisterFrame extends JFrame {
	final int FIELD_WIDTH = 20;
	private Member member;
	private JComboBox<String> idCombo;
	private JLabel titleLabel, usernameLabel, passwordLabel;
	private JTextField enterUsername, enterPassword;
	private JButton confirm, cancel;
	private String imagePath;
	private BufferedImage image;
	private JPanel titlePanel, usernamePanel, passwordPanel, buttonPanel, operatePanel, overallPanel;

	public RegisterFrame() {
		createComp();
		creteButton();
//		setResizable(false);
//		pack();
		createPanel();
		setSize(600, 500);
		setTitle("Register");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public void open() {
		this.setVisible(true);
	}

	public void close() {
		this.setVisible(false);
	}

	private void createComp() {
		idCombo = new JComboBox<String>();
		idCombo.addItem("Please select your identity");
		idCombo.addItem("Customer");
		idCombo.addItem("Staff");
		idCombo.addItem("Owner");
		idCombo.addItem("Supplier");
		idCombo.setPreferredSize(new Dimension(400, 25));

		titleLabel = new JLabel("Create an account");
		titleLabel.setFont(new java.awt.Font("Dialog", 1, 60));

		usernameLabel = new JLabel("Username   ");
		usernameLabel.setFont(new java.awt.Font("Dialog", 1, 30));
		passwordLabel = new JLabel("Password   ");
		passwordLabel.setFont(new java.awt.Font("Dialog", 1, 30));
		enterUsername = new JTextField(FIELD_WIDTH);
		focus();
		
		enterPassword = new JTextField(FIELD_WIDTH);
		
		enterPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String id = idCombo.getSelectedItem().toString();
				String username = enterUsername.getText();
				String password = enterPassword.getText();
				try {
					if (member.verify(id, username, password) == true) {
						if (member.create(id, username, password) == true) {
							close();
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

		imagePath = "/Register.png";
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
		confirm = new JButton("Confirm");
		confirm.setActionCommand("Confirm");
		cancel = new JButton("Cancel");
		cancel.setActionCommand("Cancel");
		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				String id = idCombo.getSelectedItem().toString();
				String username = enterUsername.getText();
				String password = enterPassword.getText();
				String command = event.getActionCommand();

				switch (command) {
				case ("Confirm"):
					try {
						if (member.verify(id, username, password) == true) {
							if (member.create(id, username, password) == true) {
								close();
							}
						}
					} catch (Exception e) {
						System.out.println(e.getMessage());
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
					enterUsername.setText("");
					enterPassword.setText("");
					break;
				case ("Cancel"):
					close();
					break;
				}
			}
		}
		ActionListener listener = new ButtonListener();
		confirm.addActionListener(listener);
		cancel.addActionListener(listener);
	}

	private void createPanel() {
		titlePanel = new JPanel();
		titlePanel.add(Box.createVerticalStrut(120));
		titlePanel.add(titleLabel);
		usernamePanel = new JPanel();
		usernamePanel.add(usernameLabel);
		usernamePanel.add(enterUsername);
		passwordPanel = new JPanel();
		passwordPanel.add(passwordLabel);
		passwordPanel.add(enterPassword);
		buttonPanel = new JPanel();
		buttonPanel.add(cancel);
		buttonPanel.add(Box.createRigidArea(new Dimension(80, 0)));
		buttonPanel.add(confirm);
		operatePanel = new JPanel();
		operatePanel.add(idCombo);
		operatePanel.add(Box.createVerticalStrut(50));
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