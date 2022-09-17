package code;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.BasicConfigurator;

public class Client extends JFrame implements ActionListener {

	private JPanel pn1;

	private JTextField txtMss;

	private JButton btnSend;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Client() {
		this.setTitle("Client 1");
		this.setSize(500, 300);
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
				sendmss(mess);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	private void sendmss(String mess) throws Exception {
		//thiết lập môi trường cho JMS logging
				BasicConfigurator.configure();
		//thiết lập môi trường cho JJNDI
				Properties settings = new Properties();
				settings.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
				settings.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
		//tạo context
				Context ctx = new InitialContext(settings);
		//lookup JMS connection factory
				Object obj = ctx.lookup("TopicConnectionFactory");
				ConnectionFactory factory = (ConnectionFactory) obj;
		//tạo connection
				Connection con = factory.createConnection("admin", "admin");
		//nối đến MOM
				con.start();
		//tạo session
				Session session = con.createSession(/* transaction */false, /* ACK */Session.AUTO_ACKNOWLEDGE);
				Destination destination = (Destination) ctx.lookup("dynamicTopics/thanthidet");
		//tạo producer
				MessageProducer producer = session.createProducer(destination);
		//Tạo 1 message
				Message msg = session.createTextMessage(mess);
		//gửi
				producer.send(msg);
		//shutdown connection
				session.close();
				con.close();
				System.out.println("Finished...");
	}
	
	public static void main(String[] args) {
		new Client().setVisible(true);
	}
	
}