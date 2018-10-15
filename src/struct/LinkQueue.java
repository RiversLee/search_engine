package struct;

import java.util.HashSet;
import java.util.Set;

public class LinkQueue {
    private static Set visitUrl = new HashSet();

    private static Queue unVisitUrl = new Queue();

    //获得url队列
    public static Queue getUnVisitUrl(){
        return unVisitUrl;
    }

    //添加到访问过的url队列中
    public static void addVisitdUrl(String url){
        visitUrl.add(url);
    }

    //移除访问过的url
    public static void removeVisitdUrl(String url){
        visitUrl.remove(url);
    }

    //未防伪的url出队列
    public static Object unVisitdUrlDequeue(){
        return unVisitUrl.deQueue();
    }

    //保证每个url只被访问一次
    public static void addUnVisitdUrl(String url){
        if(url != null && !url.trim().equals("")
                &&!visitUrl.contains(url)&&!unVisitUrl.contains(url)){
            unVisitUrl.enQueue(url);
        }

    }

    //取得已经被访问的url的数目
    public static int getVisitdNum(){
        return visitUrl.size();
    }
    public static int getUnVisitNum(){
        return unVisitUrl.getSize();
    }
    //判断访问队列是否未空
    public static boolean unVisitdUrlIsEmpty(){
        return unVisitUrl.isQueueEmpty();
    }

}
