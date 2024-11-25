import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class Server extends Thread {
    ServerGui gui;
    static int closeflag = 0;
    List<Socket> listSocket = new ArrayList<>();

    Server(ServerGui gui) {
        super();
        this.gui = gui;
    }

    public void run() {
        ServerSocket svs = null;

        try {
            svs = new ServerSocket(4000);
            int i = 0;
            while (true) {
                Socket s = svs.accept();
                listSocket.add(s);
                ClientHandler clientHandler = new ClientHandler(s, i);
                clientHandler.start();
                gui.taBoard.append("Client " + i + "已經連線\n");
                sendMessageToAllClients("Client " + i + "已經連線\n");
                i++;
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        try {
        if (closeflag == 1) {
            svs.close();
            
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
        
        
    }

    private class ClientHandler extends Thread {
        private Socket socket;
        private int clientNumber;

        public ClientHandler(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
        }

        public void run() {
        	byte[] buf = new byte[1024];
            int n = 0;
            try {
                InputStream in = socket.getInputStream();
                while (true) {
                    try {
                        while (in.available() == 0) {
                        	synchronized(gui.taBoard) {
                            if (gui.send_flag == 1) {
                                gui.send_flag = 0;
                            	String message =gui.tfMessage.getText();
                                gui.taBoard.append("Server: " + message + "\n");
                                sendMessageToAllClients("Server: " + message + "\n");
                                gui.tfMessage.setText("");
                            }
                            }
                        }
                        n = in.read(buf);
                        String returnedMessage = new String(buf, 0, n);
                        gui.taBoard.append("Client " + clientNumber + ": " + returnedMessage + "\n");
                        sendMessageToAllClients("Client " + clientNumber + ": " + returnedMessage + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

	private void sendMessageToAllClients(String message) throws IOException {
		for (Socket clientSocket : listSocket) {
			try {
				OutputStream out = clientSocket.getOutputStream();
				out.write(message.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
    
}
