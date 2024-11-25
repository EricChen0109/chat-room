import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class Client extends Thread {
    ClientGui gui;

    Client(ClientGui gui) {
        super();
        this.gui = gui;
    }

    public void run() {
        OutputStream out = null;
        InputStream in = null;

        try {
            out = gui.s.getOutputStream();
            in = gui.s.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int n;
        byte[] buf = new byte[1024];
        while (true) {
            try {
                while (in.available() == 0) {
                    if (gui.send_flag == 1) {
                        String message = gui.tfMessage.getText();
                        out.write(message.getBytes());
                        gui.send_flag = 0;
                        gui.tfMessage.setText("");
                    }
                }
                n = in.read(buf);
                String returnedMessage = new String(buf, 0, n);
                gui.taBoard.append(returnedMessage);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}