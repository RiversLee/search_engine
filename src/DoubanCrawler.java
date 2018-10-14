import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoubanCrawler {
    private static List<Movie> movies = new ArrayList<>();
    private static String url = "https://movie.douban.com/top250";
    public static void main(String[]args) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

        int statusCode = httpResponse.getStatusLine().getStatusCode();

        if(statusCode == 200){
            String entity = EntityUtils.toString(httpResponse.getEntity());
            Document document = Jsoup.parse(entity);
            Elements elements = document.select("div[class=item]");

            for(Element element: elements){
                String ranking = element.select("div[class=pic]").select("em").text();
                String name = element.select("div[class=hd]").select("span").text();

                Movie movie = new Movie();
                movie.setMovieName(name);
                movie.setRanking(ranking);

                movies.add(movie);
            }
            EntityUtils.consume(httpResponse.getEntity());
        }
        httpResponse.close();

        for(Movie movie: movies){
            System.out.println(movie.getMovieName() + " "+movie.getRanking() );
        }
    }
    public boolean addMysql(List<Movie> movies) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/moviemessage?useUnicode=true&" +
                                    "characterEncoding=utf8&useSSL=false";
        Connection conn = DriverManager.getConnection(url,"root","12345678");
        return true;
    }
}
