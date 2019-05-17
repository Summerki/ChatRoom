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
    public Send(Socket client) {
        this.client = client;
    }


    private void getUserName() throws IOException {
        System.out.println("请输入准备进入聊天室的昵称: ");
        br = new BufferedReader(new InputStreamReader(System.in));
        userName = br.readLine();
        System.out.println("欢迎 " + userName + " 进入聊天室！");

    }

    public void run() {
        try {
            getUserName();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 具体要循环干的事情
        while (true){
            br = new BufferedReader(new InputStreamReader(System.in));
            try {
                sendMsg = br.readLine();
                bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                bw.write(sendMsg + "\r\n");
                bw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
