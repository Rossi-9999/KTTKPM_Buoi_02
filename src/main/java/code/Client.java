package code;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Client extends JFrame implements ActionListener {

	private JPanel pn1;

	private JTextField txtMss;

	private JButton btnSend;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Client() {
		this.setTitle("Client");
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
		btnSend = new JButton("Send");
		btnSend.setBounds(350, 100, 80, 30);
		btnSend.addActionListener(this);
		
		pn1.add(txtMss);
		pn1.add(btnSend);
		this.add(pn1);
		
	}

	public void actionPerformed(ActionEvent e) {

		Object obj = e.getSource();
		if(obj.equals(btnSend)) {
			try {
				String mess = txtMss.getText().toString();
				Publisher.main(mess);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) {
		new Client().setVisible(true);
	}
	
}
