import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
public class Lab09_3 {
    public static void main(String[] args) throws Exception{
        File dirInfor = new File("c:/tmp");
        for(File f : dirInfor.listFiles()){
            if(f.isFile()) {
                System.out.printf("<%s>\n", f.getName());
                FileInputStream fis = new FileInputStream(f);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);

                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
                System.out.println();
                br.close();
                isr.close();
                fis.close();
            }
        }
    }
}
