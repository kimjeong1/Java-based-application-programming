import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
public class Lab09_5 {
    public static void main(String[] args) throws Exception{
        File src = new File("c:/test/dlwlrma.jpg");
        File dst = new File("c:/test/dlwlrma_copy.jpg");

        byte[] b = new byte[1024*64];
        FileInputStream fis = new FileInputStream(src);
        FileOutputStream fos = new FileOutputStream(dst);
        long start = System.currentTimeMillis();
        while(true){
            int num = fis.read(b);
            fos.write(b, 0, num);
            if(num < b.length)
                break;
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start + "ms");
        fos.close();
        fis.close();
    }
}
