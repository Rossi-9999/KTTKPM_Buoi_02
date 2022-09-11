package code;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Client2 extends JFrame {

	private JPanel pn1;

	private JTextField txtMss;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Client2() {
		this.setTitle("Client 2");
		this.setSize(1000, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		buildUI();
	}
	
	private void buildUI() {
		
		pn1 = new JPanel();
		pn1.setPreferredSize(new Dimension(0,600));
		pn1.setLayout(null);
		txtMss = new JTextField();
		txtMss.setBounds(50, 100, 270, 30);
		
		pn1.add(txtMss);
		this.add(pn1);
		
	}
	
	public static void main(String[] args) {
		new Client2().setVisible(true);
	}

}
