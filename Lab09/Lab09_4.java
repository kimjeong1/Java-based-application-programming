import java.io.FileInputStream;
import java.io.BufferedInputStream;

public class Lab09_4 {
    public static void main(String[] args) {
        long start, end;
        try{
            FileInputStream fis = new FileInputStream("c:/test/dlwlrma.jpg");
            start = System.currentTimeMillis();
            while(fis.read() != -1) {}
            end = System.currentTimeMillis();
            System.out.printf("FileInputStream: %dms\n", end - start);
            fis.close();

            fis = new FileInputStream("c:/test/dlwlrma.jpg");
            BufferedInputStream bis = new BufferedInputStream(fis);
            start = System.currentTimeMillis();
            while(bis.read() != -1){}
            end = System.currentTimeMillis();
            System.out.printf("BufferedInputStream: %dms\n", end - start);
            bis.close();
            fis.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
