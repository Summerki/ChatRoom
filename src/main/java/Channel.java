import java.io.*;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

// Channel类代表从ChatRoom类里面accept()出来的Socket对象所具有的多线程收发msg的类
public class Channel implements Runnable {

    private Socket client;
    private BufferedReader br;
    private String rcvMsg;
    private BufferedWriter bw;
    private String userName;



    // 构造函数
    public Channel(Socket client) throws IOException {
        this.client = client;
        this.userName = receive();
        
    }

    public void run() {
        while (true){
            try {
                receive();
                send();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // 发
    private void send() throws IOException {
        bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        bw.write("ChatRoom传回的消息: " + rcvMsg + "\r\n");
        bw.flush();
    }

    // 收
    private String receive() throws IOException {
        br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        rcvMsg = br.readLine();
        System.out.println("收到消息: " + rcvMsg);
        return rcvMsg;
    }
}
