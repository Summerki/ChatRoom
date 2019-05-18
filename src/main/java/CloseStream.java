import java.io.Closeable;
import java.io.IOException;

// 专门用来关闭流的一个类
public class CloseStream {
    // 关闭流
    public static void close(Closeable... targets){  // ...代表可变参数，所有流都继承了Closeable接口
        for(Closeable target : targets){
            if(null != target){
                try {
                    target.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
