import java.io.*;
import java.net.Socket;

// Channel类代表从ChatRoom类里面accept()出来的Socket对象所具有的多线程收发msg的类
public class Channel implements Runnable {

    private Socket client;
    private BufferedReader br;
    private String rcvMsg;
    private BufferedWriter bw;


    // 构造函数
    public Channel(Socket client) {
        this.client = client;
    }

    public void run() {
        while (true){
            // 收
            try {
                br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                rcvMsg = br.readLine();
                System.out.println("收到消息: " + rcvMsg);
                // 发
                bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                bw.write("ChatRoom传回的消息: " + rcvMsg + "\r\n");
                bw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
