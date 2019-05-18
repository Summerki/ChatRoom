import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

// CLient对应的接受数据类
public class Receive implements Runnable{

    private Socket client;
    private BufferedReader br;
    private String rcvMsg;
    private boolean isRunning = true;

    // 构造函数
    public Receive(Socket client) {
        this.client = client;
    }

    public void run() {
        // 这里写多线程具体要干的事情
        while (isRunning){
            try {
                br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                rcvMsg = br.readLine();
                if (!(rcvMsg.equals(null) || rcvMsg.equals(""))){
                    System.out.println(rcvMsg);
                }
            } catch (Exception e) {
                System.out.println("断开连接");
                this.isRunning = false;
            }
        }
    }
}
