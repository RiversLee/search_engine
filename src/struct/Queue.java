package struct;

import java.util.LinkedList;

public class Queue {
    private LinkedList queue = new LinkedList();

    public void enQueue(Object o){
        queue.add(o);
    }
    public Object deQueue(){
        return queue.removeFirst();
    }
    public boolean isQueueEmpty(){
        return queue.isEmpty();
    }
    public boolean contains(Object o){
        return queue.contains(o);
    }
    public int getSize(){
        return queue.size();
    }
}
