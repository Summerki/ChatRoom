import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

// ChatRoom类，聊天室类
public class ChatRoom {

    public static void main(String[] args) throws IOException {
        System.out.println("----ChatRoom----");
        ServerSocket chatRoom = new ServerSocket(8888);

        while (true){
            Socket client = chatRoom.accept();
            System.out.println("一个用户已经连接");
            new Thread(new Channel(client)).start();
        }




//        // 处理收到的数据
//        BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
//        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
//        int len = -1;
//        byte[] buffer = new byte[1024];
//        while (true){
//            String msg = br.readLine();
//            System.out.println("收到消息: " + msg);
//            bw.write("聊天室+" + msg + "\r\n");
//            bw.flush();
//        }

    }

}
