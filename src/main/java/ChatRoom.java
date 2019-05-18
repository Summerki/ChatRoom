import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

// ChatRoom类，聊天室类
public class ChatRoom {

    public static CopyOnWriteArrayList<Channel> clientList = new CopyOnWriteArrayList<Channel>();  // 存放所有的Client集合

    public static void main(String[] args) throws IOException {
        System.out.println("----ChatRoom----");
        ServerSocket chatRoom = new ServerSocket(8888);



        while (true){
            Socket client = chatRoom.accept();
            System.out.println("一个用户已经连接");
            Channel channel = new Channel(client);
            clientList.add(channel);
            new Thread(channel).start();
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
