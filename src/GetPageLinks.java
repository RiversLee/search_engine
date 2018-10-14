import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//import javax.swing.text.Document;
import java.io.IOException;

public class GetPageLinks {
    public static void main(String[]args) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = "https://www.zhihu.com/";
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

        int statusCode = httpResponse.getStatusLine().getStatusCode();

        if(statusCode == 200){
            String entity = EntityUtils.toString(httpResponse.getEntity());

            Document doc = Jsoup.parse(entity);

            Elements links = doc.select("a[href]");
            //System.out.println(links);

        }

    }
}
