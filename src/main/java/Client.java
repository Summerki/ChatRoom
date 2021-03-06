import java.io.*;
import java.net.Socket;
import java.nio.channels.UnresolvedAddressException;

// 用户类
public class Client {

    // 首先实现一个可以单人连接的聊天室
    // 用户可以不停发送消息，同时聊天室会打印该消息
    // 与此同时聊天室还会把消息msg再次转发给用户  转发消息格式为: 聊天室+msg
    // 开始实现多用户模式，首先在Client类中添加用作每个用户的唯一的标识，也就是在进入聊天室的昵称
    // 加入多线程实现多用户登陆

    // 首先是Client类，将收(Receive)发(Send)封装成两个独立的类，各自都继承Runnable接口
    // 其次是ChatRoom类，让它也有能够连接多用户的能力

    // 现在要实现不再是每个Client只与ChatRoom通信，每个Client发送消息后其他Client界面上也能收到消息
    // 所以现在 昵称name 就有作用了

    // 退出群聊功能: 用户输入bye即可退出群聊

    // 私聊功能: 使用[@XXX 消息]这样的格式可以只让XXX收到此条消息

    public static void main(String[] args) throws IOException {
        System.out.println("----Client----");

//        System.out.println("请输入准备进入聊天室的昵称: ");
//        BufferedReader br;
//        br = new BufferedReader(new InputStreamReader(System.in));
//        String uname = br.readLine();
//        System.out.println(uname);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("请输入进入聊天室的昵称:");
        String userName = br.readLine();

        Socket client = new Socket("localhost", 8888);


//        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
//
//
//        while (true){
//            br = new BufferedReader(new InputStreamReader(System.in));
//            String msg = br.readLine();
//            bw.write(msg + "\r\n"); // 这个\r\n卡了我好久……
//            bw.flush();
//            br = new BufferedReader(new InputStreamReader(client.getInputStream()));
//            msg = br.readLine();
//            System.out.println(msg);
//
//        }


        new Thread(new Send(client, userName)).start();
        new Thread(new Receive(client)).start();


    }

}
