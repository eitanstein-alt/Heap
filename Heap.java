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
        heap2.last = start2;
        heap2.min = start2item;
        heap2.sz = 1;
        heap2.szT =1;
        meld(heap2);
        return start2item;
    }

    public HeapNode Link(HeapNode a,HeapNode b){
        if(b.item.key < a.item.key){
            HeapNode c = a;
            a=b;
            b =c;
        } 
        b.prev = null;
        b.next  = a.child;
        b.parent = a;
        a.rank = a.rank+1;
        if(a.child != null){
            a.child.prev = b;
        }
        a.child = b;
        a.next = null;
        a.prev = null;
        return a;
    }
    /**
     * 
     * Return the minimal HeapNode, null if empty.
     *
     */
    public void SuccessiveLinking(){
        int maxrank = 2*(int)Math.log(sz) +10;
        HeapNode[] split =  new HeapNode[maxrank];
        /* makes list of copy of heads without connection to each other */
        HeapNode[] heads = new HeapNode[szT+1];
        HeapNode now = start;
        int ihead =0;
        while(now != null){
            heads[ihead++] = now.copy();
            heads[ihead-1].prev = null;
            heads[ihead-1].next = null;
            now =  now.next;
        }
        /*doing acual succsive linking by Linking - we use copy all the time */
        ihead = 0;
        while(heads[ihead] != null){
            now = heads[ihead];
            int i = now.rank;
            HeapNode nowgo =  now.copy();
            while(split[i] != null){
                nowgo = Link(split[i].copy(),nowgo);
                split[i] =  null;
                i++;
            }
            split[i] = nowgo;
            ihead++;
        }
        /* making the heap */
        HeapNode lastHeapnode = null;
        szT=0;
        HeapItem a = new HeapItem(Integer.MAX_VALUE, null);
        for(int i=0;i<maxrank;i++){
            if(split[i] != null){
                if(a.key > split[i].item.key){
                    a.key  = split[i].item.key;
                    a.node = split[i];
                }
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
        min = a;
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
        HeapNode minnode = min.node;
        HeapNode child =  minnode.child;
        if(child == null){
            if(minnode.next != null){
                minnode.next.prev = minnode.prev;
            }
            if(minnode.prev != null){
                minnode.prev.next = minnode.next;
            }
        }
        else{
            HeapNode child2 = child;
            while(child2 != null){
                if(child2.next == null){
                    break;
                }
                child2 = child2.next;
                child2.parent  =null;
                szT++;
            }
            if(minnode.next != null){
                minnode.next.prev = child2;
                child2.next = minnode.next;
            }
            if(minnode.prev != null){
                minnode.prev.next = child;
                child.prev = minnode.prev;
            }
            if(minnode.child != null){
                minnode.child.parent  =  null;
            }
        }
        if(last ==  minnode){
            last = minnode.prev;
            if(last == null){
                last =  child;
            }
            if(last == null){
                last = minnode.next;
            }
            while(last != null && last.next != null){
                last = last.next;
            }
        }
        if(start ==  minnode){
            start = minnode.next;
            if(start == null){
                start =  child;
            }
            while(start != null && start.prev != null){
                start = start.prev;
            }
        }
        minnode.child  =  null;
        minnode.next = null;
        minnode.prev = null;
        sz -=1;
        SuccessiveLinking();
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
        if(heap2.sz  == 0){
            if(lazyMelds == false){
                SuccessiveLinking();
            }
            return;
        }
        if(sz ==0 ){
            min= heap2.min;
            start = heap2.start;
            sz = heap2.sz;
            szT = heap2.szT;
            last =  heap2.last;
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
        HeapItem a = this.min;
        HeapItem b = heap2.min;
        if (a.key > b.key){
            this.min = b;
        }
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
    public void printHeap() {
        System.out.println("=== Fibonacci Heap Tree View ===");
        if (start == null) {
            System.out.println("Empty Heap");
            return;
        }

        HeapNode currentRoot = start;
        int treeCount = 1;
        while (currentRoot != null) {
            System.out.println("Tree #" + treeCount + " (Rank: " + currentRoot.rank + ")");
            // Pass " " as the initial prefix for the root
            displayTree(currentRoot, "", true);
            currentRoot = currentRoot.next;
            treeCount++;
        }
    }

    private void displayTree(HeapNode node, String prefix, boolean isLast) {
        if (node == null) return;

        // Determine the branch visual
        System.out.print(prefix);
        System.out.print(isLast ? "└── " : "├── ");
        System.out.println("Key: " + node.item.key);

        // Prepare prefix for children
        String newPrefix = prefix + (isLast ? "    " : "│   ");

        // Print all children by traversing the child's .next chain
        if (node.child != null) {
            HeapNode currentChild = node.child;
            while (currentChild != null) {
                // A child is the "last" sibling if its .next is null
                boolean childIsLast = (currentChild.next == null);
                displayTree(currentChild, newPrefix, childIsLast);
                currentChild = currentChild.next;
            }
        }
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
        /*
            return a copy of a HeapNode;
        */
        public HeapNode copy(){
            HeapNode nodenew =  new HeapNode(this.item,this.child,this.prev,this.next,this.parent,this.rank);
            if(nodenew.item != null){
                nodenew.item.node = nodenew;
            }
            return nodenew;
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
