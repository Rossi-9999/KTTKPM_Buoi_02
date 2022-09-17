package code;

import java.awt.Dimension;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.BasicConfigurator;

public class Client2 extends JFrame {

	private JPanel pn1;

	private JTextField txtMss;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Client2() {
		this.setTitle("Client 2");
		this.setSize(400, 300);
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
		txtMss.setEditable(false);
		
		try {
			getmss();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		pn1.add(txtMss);
		this.add(pn1);
		
	}
	
	private void getmss() throws Exception {
		//thiết lập môi trường cho JMS
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
				Session session = con.createSession(/* transaction */false, /* ACK */Session.CLIENT_ACKNOWLEDGE);
		//tạo consumer
				Destination destination = (Destination) ctx.lookup("dynamicTopics/thanthidet");
				MessageConsumer receiver = session.createConsumer(destination);
		//receiver.receive();//blocked method
		//Cho receiver lắng nghe trên queue, chừng có message thì notify
				receiver.setMessageListener(new MessageListener() {
//					@Override
		//có message đến queue, phương thức này được thực thi
					public void onMessage(Message msg) {// msg là message nhận được
						try {
							if (msg instanceof TextMessage) {
								TextMessage tm = (TextMessage) msg;
								String txt = tm.getText();
								txtMss.setText(txt);
								msg.acknowledge();// gửi tín hiệu ack
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
	}
	
	public static void main(String[] args) {
		new Client2().setVisible(true);
	}

}
