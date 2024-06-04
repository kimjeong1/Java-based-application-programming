import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;

public class Lab09_1 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        try{
            FileWriter fw = new FileWriter("c:/test/test2.txt");

            while(true){
                String line = scan.nextLine();
                if(line.length() == 0)
                    break;
                fw.write(line);
                fw.write('\n');
            }
            fw.close();
            scan.close();

            FileReader fr = new FileReader("C:/test/test2.txt");
            int c;
            while((c = fr.read()) != -1)
                System.out.print((char)c);
            fr.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
