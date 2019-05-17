import java.io.*;
import java.net.Socket;

// 用户类
public class Client {

    // 首先实现一个可以单人连接的聊天室
    // 用户可以不停发送消息，同时聊天室会打印该消息

    public static void main(String[] args) throws IOException {
        System.out.println("----Client----");
        Socket client = new Socket("localhost", 8888);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        BufferedReader br;
        byte[] buffer = new byte[1024];
        int len = -1;
        while (true){
            br = new BufferedReader(new InputStreamReader(System.in));
            String msg = br.readLine();
            bw.write(msg + "\r\n"); // 这个\r\n卡了我好久……
            bw.flush();
        }


    }

}
