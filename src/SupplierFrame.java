import java.awt.BorderLayout;
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
import javax.swing.JTextArea;

public class SupplierFrame extends CustomerFrame{
	final int FRAME_WIDTH = 600;
	final int FRAME_HEIGHT = 500;
	private JButton back;
	private String imagePath;
	private BufferedImage image;
	private JPanel titlePanel, operatePanel, overallPanel;
	private JLabel titleLabel;
	private JTextArea infoArea;

	public SupplierFrame() {
		createComp();
		creteButton();
//		setResizable(false);
//		pack();
		createPanel();
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setTitle("Supplier");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private void createComp() {
		titleLabel = new JLabel("Supplier Page");
		titleLabel.setFont(new java.awt.Font("Dialog", 1, 60));

		infoArea = new JTextArea(35, 90);

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
		back = new JButton("Back");
		back.setActionCommand("Back");
		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				String command = event.getActionCommand();

				switch (command) {
				case ("Back"):
					close();
					new LoginFrame().open();
					break;
				}
			}
		}
		ActionListener listener = new ButtonListener();
		back.addActionListener(listener);
	}

	private void createPanel() {
		titlePanel = new JPanel();
		titlePanel.add(titleLabel);
		operatePanel = new JPanel();
		operatePanel.add(back);
		operatePanel.add(Box.createHorizontalStrut(500));
		operatePanel.add(infoArea);
		overallPanel = new JPanel(new BorderLayout());
		overallPanel.add(titlePanel, BorderLayout.NORTH);
		overallPanel.add(operatePanel, BorderLayout.CENTER);
		add(overallPanel);
	}
}
