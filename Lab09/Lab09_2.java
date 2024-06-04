import java.io.*;
import java.util.Scanner;

public class Lab09_2 {
    public static void main(String[] args) throws IOException{
        Scanner scan = new Scanner(System.in);
        FileOutputStream fos = new FileOutputStream("c:/test/test3.txt");
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
        while(true){
            String line = scan.nextLine();
            if(line.length() == 0)
                break;
            osw.write(line);
            osw.write('\n');
        }
        osw.close();
        fos.close();
        scan.close();

        FileInputStream fis = new FileInputStream("c:/test/test3.txt");
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        char[] c = new char[10];
        int num;
        while((num = isr.read(c)) != -1)
            for(int i = 0; i<num; i++)
                System.out.print(c[i]);
        isr.close();
        fis.close();
    }
}

