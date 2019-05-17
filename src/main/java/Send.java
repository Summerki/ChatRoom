import java.io.*;
import java.net.Socket;

// Client对应的发送数据类
public class Send implements Runnable{
    private Socket client;
    private BufferedReader br;
    private BufferedWriter bw;
    private String sendMsg;

    private String userName;

    // 构造函数
    public Send(Socket client, String userName) throws IOException {
        this.client = client;
        this.userName = userName;  // 相当于是唯一标识
        send(this.userName);  // 构造函数期间就直接将userName发送到ChatRoom之中存储起来
    }


//    private void getUserName() throws IOException {
//        System.out.println("请输入准备进入聊天室的昵称: ");
//        br = new BufferedReader(new InputStreamReader(System.in));
//        userName = br.readLine();
//        bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
//        bw.write(userName + "\r\n");
//        bw.flush();
//
//    }

    public void run() {
        // 具体要循环干的事情
        while (true){
            br = new BufferedReader(new InputStreamReader(System.in));
            try {
                sendMsg = br.readLine();
                send(sendMsg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void send(String msg) throws IOException {
        bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        bw.write(msg + "\r\n");
        bw.flush();
    }
}
