import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Practice {
    public static void main(String[]args) throws IOException {
        Document doc = Jsoup.connect("http://www.fudan.edu.cn/2016/index.html").get();
        String title = doc.title();
        System.out.println(title);
    }
}
