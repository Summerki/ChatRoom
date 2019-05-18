import java.io.*;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

// Channel类代表从ChatRoom类里面accept()出来的Socket对象所具有的多线程收发msg的类
// 准确说是给accept()出来的Socket对象做了一层包装
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
                rcvMsg = receive();
                sendMsgToOthers(rcvMsg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // 某一个CLient发送的消息也让其他Client可以看见
    // 发送消息给其他Client
    private void sendMsgToOthers(String msg) throws IOException {
        for (Channel channel : ChatRoom.clientList){
            if(channel.equals(this)){ // 自己
                continue;
            }else{
                channel.send(this.userName + "说:" + msg);
            }
        }
    }


    // 发
    // msg: 要发送的消息
    private void send(String msg) throws IOException {
        bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        bw.write(msg + "\r\n");
        bw.flush();
    }

    // 收
    // 返回: 接受到的消息
    private String receive() throws IOException {
        br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        rcvMsg = br.readLine();
        System.out.println("收到消息: " + rcvMsg);  // ChatRoom后台打印确认是否接收到消息
        return rcvMsg;
    }
}
