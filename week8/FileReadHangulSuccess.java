import java.io.*;

public class FileReadHangulSuccess {
    public static void main(String[] args) {
        InputStreamReader in = null;
        FileInputStream fin = null;
        try {
            fin = new FileInputStream("c:\\Temp\\hangul.txt");
            in = new InputStreamReader(fin, "UTF-8");
            // MS949, US-ASCII, UTF-8 이렇게 있는데 이건 UTF-8로 인코딩 되어 있음
            int c;
            System.out.println("인코딩 문자 집합은 " + in.getEncoding());
            while ((c = in.read()) != -1) {
                System.out.print((char)c);
            }
            in.close();
            fin.close();
        } catch (IOException e) {
            System.out.println("입출력 오류");
        }
    }
}
