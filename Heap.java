/**
 * Heap
 *
 * An implementation of Fibonacci heap over positive integers 
 * with the possibility of not performing lazy melds and 
 * the possibility of not performing lazy decrease keys.
 *
 */
public class Heap
{
    public final boolean lazyMelds;
    public final boolean lazyDecreaseKeys;
    public HeapItem min;
    public HeapNode start;
    public int sz;
    public int szT;
    public HeapNode last;
    /**
     *
     * Constructor to initialize an empty heap.
     *
     */
    public Heap(boolean lazyMelds, boolean lazyDecreaseKeys)
    {
        this.lazyMelds = lazyMelds;
        this.lazyDecreaseKeys = lazyDecreaseKeys;
        // student code can be added here
    }

    /**
     * 
     * pre: key > 0
     *
     * Insert (key,info) into the heap and return the newly generated HeapNode.
     *
     */

    public HeapItem insert(int key, String info) { 
        Heap heap2 = new Heap(lazyMelds, lazyDecreaseKeys);
        HeapItem start2item = new HeapItem(key,info);
        HeapNode start2 =  new HeapNode(start2item , null, null, null, null, 0);
        heap2.start = start2;
        heap2.min = start2item;
        meld(heap2);
        return start2item;
    }

    public HeapNode Link(HeapNode a,HeapNode b){
        if(b.item.key < a.item.key){
            HeapNode c = a;
            a=b;
            b =c;
        } 
        a.rank = a.rank+1;
        b.next  = a.child;
        b.prev = null;
        a.child = b;
        b.parent = a;
        return a;
    }
    /**
     * 
     * Return the minimal HeapNode, null if empty.
     *
     */
    public void SuccessiveLinking(){
        int maxrank = 2*(int)Math.log(sz);
        HeapNode[] split =  new HeapNode[maxrank];
        HeapNode now = start;
        while(now != null){
            int i = now.rank;
            HeapNode nowgo =  now;
            while(split[i] != null){
                nowgo = Link(split[i],nowgo);
                split[i] =  null;
                i++;
            }
            split[i] = nowgo;
            now = now.next;
        }
        HeapNode lastHeapnode = null;
        szT=0;
        for(int i=0;i<maxrank;i++){
            if(split[i] != null){
                szT++;
                if(lastHeapnode  == null){
                        start = split[i];
                        start.prev = null;
                        start.next = null;
                        lastHeapnode = start;
                }
                else{
                    lastHeapnode.next = split[i];
                    split[i].prev = lastHeapnode;
                    split[i].next = null;
                    lastHeapnode = split[i];
                }
            }
        }
        last = lastHeapnode;
    }  
    public HeapItem findMin()
    {
        return min; // should be replaced by student code
    }

    /**
     * 
     * Delete the minimal item.
     *
     */
    public void deleteMin()
    {
        return; // should be replaced by student code
    }

    /**
     * 
     * pre: 0<=diff<=x.key
     * 
     * Decrease the key of x by diff and fix the heap.
     * 
     */
    public void decreaseKey(HeapItem x, int diff) 
    {    
        return; // should be replaced by student code
    }

    /**
     * 
     * Delete the x from the heap.
     *
     */
    public void delete(HeapItem x) 
    {    
        return; // should be replaced by student code
    }


    /**
     * 
     * Meld the heap with heap2
     * pre: heap2.lazyMelds = this.lazyMelds AND heap2.lazyDecreaseKeys = this.lazyDecreaseKeys
     *
     */
    public void meld(Heap heap2)
    {
        if(heap2 == null){
            if(lazyMelds == false){
                SuccessiveLinking();
            }
            return;
        }
        szT += heap2.szT;
        sz += heap2.sz;
        this.last.next = heap2.start;  
        heap2.start.prev = this.last;  
        this.last = heap2.last;
        if(lazyMelds == false){
            SuccessiveLinking();
        }
    }
    
    
    /**
     * 
     * Return the number of elements in the heap
     *   
     */
    public int size()
    {
        return sz; // should be replaced by student code
    }


    /**
     * 
     * Return the number of trees in the heap.
     * 
     */
    public int numTrees()
    {
        return szT; // should be replaced by student code
    }
    
    
    /**
     * 
     * Return the number of marked nodes in the heap.
     * 
     */
    public int numMarkedNodes()
    {
        return 46; // should be replaced by student code
    }
    
    
    /**
     * 
     * Return the total number of links.
     * 
     */
    public int totalLinks()
    {
        return 46; // should be replaced by student code
    }
    
    
    /**
     * 
     * Return the total number of cuts.
     * 
     */
    public int totalCuts()
    {
        return 46; // should be replaced by student code
    }
    

    /**
     * 
     * Return the total heapify costs.
     * 
     */
    public int totalHeapifyCosts()
    {
        return 46; // should be replaced by student code
    }
    
    
    /**
     * Class implementing a node in a Heap.
     *  
     */
    public static class HeapNode{
        public HeapItem item;
        public HeapNode child;
        public HeapNode next;
        public HeapNode prev;
        public HeapNode parent;
        public int rank;
        public HeapNode(HeapItem item,HeapNode child,HeapNode next,HeapNode prev,HeapNode parent,int rank){
            this.item = item;
            this.child = child;
            this.next = next;
            this.prev = prev;
            this.parent = parent;
            this.rank = rank;
            this.item.node = this;
        }
    }
    
    /**
     * Class implementing an item in a Heap.
     *  
     */
    public static class HeapItem{
        public HeapNode node;
        public int key;
        public String info;
        public HeapItem(int key,String info){
            this.key = key;
            this.info = info;
        }
    }
}
