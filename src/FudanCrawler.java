import struct.LinkQueue;
import struct.Queue;

import java.io.IOException;

public class FudanCrawler {
    public static void main(String[]args) throws IOException {
        String url = "http://www.fudan.edu.cn/2016/index.html";
        Queue queue = HtmlUrlParserTool.htmlParser(HtmlUrlParserTool.getUrlIndex(url));
        LinkQueue.addVisitdUrl("http://www.fudan.edu.cn/2016/index.html");

        while(!queue.isQueueEmpty()){
            String url1 = (String)queue.deQueue();
            LinkQueue.addUnVisitdUrl(url1);
        }

        //int i = 0;
        FudanThread fudanThread = new FudanThread();
        for(int i = 0;i < 100;i++){
            new Thread(fudanThread,"线程"+i).run();
        }
    }


}
class FudanThread implements Runnable{

    @Override
    public void run() {
        while(!LinkQueue.unVisitdUrlIsEmpty()){
           String url = (String)LinkQueue.unVisitdUrlDequeue();
           LinkQueue.addVisitdUrl(url);
            try {
                Queue queue = HtmlUrlParserTool.htmlParser(HtmlUrlParserTool.getUrlIndex(url));
                while (!queue.isQueueEmpty()){
                    String urlNew = (String)queue.deQueue();
                    LinkQueue.addUnVisitdUrl(urlNew);
                }
                System.out.println("线程："+ Thread.currentThread().getName()+" 已经访问的的url数目:"+
                                    LinkQueue.getVisitdNum()+"未访问的url数目："+LinkQueue.getUnVisitNum());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}