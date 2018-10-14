import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BookCrawler {
    static final Logger logger = LoggerFactory.getLogger(BookCrawler.class);
    public static void main(String[]args) throws IOException, SQLException,ClassNotFoundException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = "https://search.jd.com/Search?keyword=python&enc=utf-8&wq=python&pvid=5509b4aa6e6b475d91b35d7c0d83009a";
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

        List<Book> books = new ArrayList<>();

        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if(statusCode == 200){
            String enity = EntityUtils.toString(httpResponse.getEntity(),"utf-8");

            Document doc = Jsoup.parse(enity);

            Elements elements = doc.select("ul[class=gl-warp clearfix]").select("li[class=gl-item]");
            for(Element element : elements){
                String bookID = element.attr("data-sku");
                String bookPrice = element.select("div[class=p-price]").select("strong").select("i").text();
                String bookName = element.select("div[class=p-name]").select("em").text();

                Book book = new Book();
                book.setBookId(bookID);
                book.setBookPrice(bookPrice);
                book.setBookName(bookName);

                books.add(book);
            }
            EntityUtils.consume(httpResponse.getEntity());

        }
        if(exeInsertData(books)){
            logger.info("Insert success");
        }else{
            logger.info("Insert fail");
        }
        httpResponse.close();

    }
    public static Boolean exeInsertData(List<Book> books) throws ClassNotFoundException,SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/bookmessage?useUnicode=true&characterEncoding=utf8&useSSL=false";
        Connection conn = DriverManager.getConnection(url,"root","lyj5232698");
        String sql = "insert into book (bookID, bookName, bookPrice) values (?, ?, ?)";
        PreparedStatement exeUpdate = conn.prepareStatement(sql);

        for(Book book:books){
            exeUpdate.setString(1,book.getBookId());

            exeUpdate.setString(2,book.getBookName());
            System.out.println(book.getBookName());
            exeUpdate.setString(3,book.getBookPrice());
            exeUpdate.executeUpdate();

        }
        exeUpdate.close();
        conn.close();
        return true;
    }
}
