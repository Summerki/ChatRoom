import java.io.*;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

// Channel类代表从ChatRoom类里面accept()出来的Socket对象所具有的多线程收发msg的类
// 准确说是给accept()出来的Socket对象做了一层包装
public class Channel implements Runnable {

    private Socket client;
    private BufferedReader br;
    private String rcvMsg = "";
    private BufferedWriter bw;
    private String userName;
    private boolean isRunning = true; // 默认为true



    // 构造函数
    public Channel(Socket client) throws IOException {
        this.client = client;
        this.userName = receive();
        this.send("欢迎来到CSU聊天室");
        this.sendMsgToOthers("欢迎" + this.userName + "来到CSU聊天室", true);
    }

    public void run() {
        while (isRunning){
            try {
                rcvMsg = receive();
                sendMsgToOthers(rcvMsg, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // 某一个CLient发送的消息也让其他Client可以看见
    // 发送消息给其他Client
    // msg: 某一个Client发送的msg，再转发给其他Client
    // isSys: 指示是否是系统消息
    private void sendMsgToOthers(String msg, boolean isSys) throws IOException {
        for (Channel channel : ChatRoom.clientList){
            if(channel.equals(this)){ // 自己
                continue;
            }else if(isSys){  // 系统消息
                channel.send(msg);
            }else if(!msg.equals("")){ // 发送消息不为空时才发出去
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
        if(rcvMsg.equals("byeCSU")){
            this.sendMsgToOthers(rcvMsg, false);
            release();
            rcvMsg = "";
        }else if(rcvMsg.startsWith("@")){  // 以@开始，代表该用户想要私聊某一个人
            String[] msgs = rcvMsg.split(" ");
            String toUser = msgs[0].substring(1); // 获取要发给的用户名
            // msgs[1]代表要私聊的消息
            boolean isFind = false;  // 默认开始未找到该用户
            for (Channel c : ChatRoom.clientList){
                if(c.userName.equals(toUser)){  //  代表确有其人
                    c.send(this.userName + "私聊你说: " + msgs[1]);
                    isFind = true;
                    rcvMsg = "";
                }
            }
            if (!isFind){
                this.send("未找到该用户！请重新发送消息！");
                rcvMsg = "";
            }
        }
        return rcvMsg;
    }


    // 释放资源
    private void release() throws IOException {
        this.sendMsgToOthers(this.userName + "退出了聊天室", true);
        ChatRoom.clientList.remove(this);
        CloseStream.close(client, br, bw);
        this.isRunning = false;
    }
}
