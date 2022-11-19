import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Member {
	private String id;
	private String username;
	private String password;

	public Member() {
	}

	public boolean login(String username, String password) {
		try {
			if (username.equals("") || password.equals("")) {
				JOptionPane.showMessageDialog(null, "The username or password can't be null!", "Notification",
						JOptionPane.WARNING_MESSAGE);
				return false;
			} else if (findUser(username) == true && this.password.equals(password)) {
				JOptionPane.showMessageDialog(null, "Login successfully!", "Notification", JOptionPane.WARNING_MESSAGE);
				return true;
			}
		} catch (Exception e) {

		}
		JOptionPane.showMessageDialog(null, "The username or password is incorrect, please try again!", "Notification",
				JOptionPane.WARNING_MESSAGE);
		return false;
	}

	public boolean verify(String id, String username, String password) throws FileNotFoundException {
		try {
			if (findUser(username) == false) {
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "The username has existed, please try another one.", "Notification",
						JOptionPane.WARNING_MESSAGE);
			}
		} catch (NoSuchElementException e1) {
			JOptionPane.showMessageDialog(null, "The username or password can't be null!", "Notification",
					JOptionPane.WARNING_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return false;
	}

	public boolean create(String id, String username, String password) {
		String path = assignPath(id);
		PrintWriter writer = null;
		try {
			if (username.equals("") || password.equals("")) {
				JOptionPane.showMessageDialog(null, "The username or password can't be null!", "Notification",
						JOptionPane.WARNING_MESSAGE);
				return false;
			} else {
				writer = new PrintWriter(new BufferedWriter(new FileWriter(path, true)));
				writer.println(username + " " + password + "$");
				JOptionPane.showMessageDialog(null, "Sign up successfully!", "Notification",
						JOptionPane.WARNING_MESSAGE);
				return true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Fail to sign up!", "Notification", JOptionPane.WARNING_MESSAGE);
			return false;
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	public String assignPath(String id) {
		String path = "";
		switch (id) {
		case ("Please select your identity"):
			JOptionPane.showMessageDialog(null, "Please select your identity!", "Notification",
					JOptionPane.WARNING_MESSAGE);
			break;
		case ("Customer"):
			path = "Customer.txt";
			break;
		case ("Staff"):
			path = "Staff.txt";
			break;
		case ("Owner"):
			path = "Owner.txt";
			break;
		case ("Supplier"):
			path = "Supplier.txt";
			break;
		}
		return path;
	}

	public boolean findUser(String username) throws FileNotFoundException {
		String[] allPath = { "Customer.txt", "Staff.txt", "Owner.txt", "Supplier.txt" };
		Scanner scanner = new Scanner("");
		for (String path : allPath) {
			File file = new File(path);
			scanner = new Scanner(file);
			try {
				while (scanner.hasNextLine()) {
					String text = scanner.next();
					if (text.equals(username)) {
						this.username = username;
						this.password = scanner.next().replace("$", "");
						this.id = path.replace(".txt", "");
						scanner.close();
						return true;
					}
				}
			} catch (Exception e) {

			}
		}
		scanner.close();
		return false;
	}

	public String getID() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
