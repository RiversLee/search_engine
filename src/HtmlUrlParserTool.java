import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import struct.Queue;

import java.io.IOException;

public class HtmlUrlParserTool {
    public static String getUrlIndex(String url)throws IOException {
        //String url = "http://www.fudan.edu.cn/2016/index.html";
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpClient httpClient = HttpClients.createDefault();

        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        String entity = "";
        if(statusCode == 200){
           entity = EntityUtils.toString(httpResponse.getEntity());

        }
        return entity;
    }
    public static Queue htmlParser(String html){

        Queue queue = new Queue();
        Document doc  = Jsoup.parse(html);
        Elements elements = doc.select("a");
        for(Element element:elements){
            String url = element.attr("href");
            if(!url.contains("http")) continue;
           // System.out.println(url);
            if(!queue.contains(url)){
                queue.enQueue(url);
            }
        }
        return queue;
    }

}
