import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class ServerGui {
	private JFrame frame;
	private JTextField tfPort;
	JTextField tfMessage;
	JTextArea taBoard;
	ServerGui gui;
	int send_flag = 0;
	int file_send_flag = 0;
	Socket s;
	File file;
	
	public ServerGui() {
       gui = this;
	}

	void setVisible(boolean visiable) {
		frame.setVisible(visiable);
	}

	void initialize() {
		frame = new JFrame();
		frame.setTitle("多人聊天室 : Server");
		frame.setBounds(100, 100, 506, 411);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Server Port");
		lblNewLabel_1.setBounds(30, 21, 65, 15);
		frame.getContentPane().add(lblNewLabel_1);
		tfPort = new JTextField();
		tfPort.setText("4000");
		tfPort.setBounds(110, 18, 105, 21);
		frame.getContentPane().add(tfPort);
		tfPort.setColumns(10);
		JButton btnConnect = new JButton("Wait for Clients");

		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Server(gui).start();;
				gui.taBoard.append("開始聆聽於"+tfPort.getText()+"\n");
			}
		});
		btnConnect.setBounds(320, 17, 130, 23);
		frame.getContentPane().add(btnConnect);
		JButton btnClose = new JButton("Close Server");
		btnClose.setBounds(320, 47, 130, 23);
		frame.getContentPane().add(btnClose);
		
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                Server.closeflag = 1;
				System.exit(0);
			}
		});
		
		JButton btnsend = new JButton("send");
		btnsend.setBounds(380, 342, 90, 21);
		frame.getContentPane().add(btnsend);
		btnsend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					send_flag = 1;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		
		taBoard = new JTextArea();
		taBoard.setBounds(22, 71, 446, 247);
		frame.getContentPane().add(taBoard);
		tfMessage = new JTextField();
		tfMessage.setBounds(22, 342, 340, 21);
		frame.getContentPane().add(tfMessage);
		tfMessage.setColumns(10);
		tfMessage.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					send_flag = 1;
			}
		});
	}
}
