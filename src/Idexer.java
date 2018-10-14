import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;


import java.io.File;
import java.io.IOException;

public class Idexer {

    public static void main(String[]args){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = "http://www.fudan.edu.cn/";
        HttpGet httpGet = new HttpGet(url);
        System.out.println(httpGet);

        HttpPost httpPost = new HttpPost();
        System.out.println(httpPost);

        try{
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            System.out.println(httpResponse);

            int status = httpResponse.getStatusLine().getStatusCode();
            System.out.println(status);

            if(status == 200){
                String enity = EntityUtils.toString(httpResponse.getEntity());
                System.out.println(enity);
                EntityUtils.consume(httpResponse.getEntity());
            }else{
                EntityUtils.consume(httpResponse.getEntity());
            }
            httpResponse.close();
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
