import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class getinfo {
    public static int n = 464646;
    public static int SumOfLinksCutsHeapify(Heap h){
        return h.totalLinks() + h.totalCuts() + h.totalHeapifyCosts();
    }
    public static void first(boolean lazyMelds,boolean lazyDecreaseKeys){
        double totalmilisecondtime =0;
        double minimal =0;
        double links =0;
        double cuts =0;
        double heapify =0;
        double szt =0;
        double costmost =0;
        System.out.println("lazyMelds :" +lazyMelds );
        System.out.println("lazyDecreaseKeys :" +lazyDecreaseKeys +"\n");
        for(int j =0;j<20;j++){
            int cost =0;
            long startTime = System.nanoTime();
            List<Integer> lst = new ArrayList<>();
            for(int i =1;i<=n;i++){
                lst.add(i);
            }
            Collections.shuffle(lst);
            Heap h = new Heap(lazyMelds, lazyDecreaseKeys);
            for(int i =0;i<n;i++){
                int costbefore = SumOfLinksCutsHeapify(h);
                h.insert(lst.get(i),"");
                int costafter = SumOfLinksCutsHeapify(h);
                int costoperation = costafter - costbefore;
                cost =  Math.max(cost,costoperation);
            }
            int costbefore = SumOfLinksCutsHeapify(h);
            h.deleteMin();
            int costafter = SumOfLinksCutsHeapify(h);
            int costoperation = costafter - costbefore;
            cost =  Math.max(cost,costoperation);
            long endTime = System.nanoTime();
            long durationInNano = (endTime - startTime);
            long durationInMillis = durationInNano / 1_000_000;
            totalmilisecondtime += durationInMillis;
            minimal += h.findMin().key;
            links += h.totalLinks();
            cuts += h.totalCuts();
            heapify += h.totalHeapifyCosts();
            szt += h.numTrees();
            costmost += cost;
        }    
        totalmilisecondtime/=20;
        minimal/=20;
        links/=20;
        cuts/=20;
        heapify/=20;
        szt/=20;
        costmost/=20;
        System.out.println("Time: " + totalmilisecondtime);
        System.out.println("Min: " +  minimal);
        System.out.println("Links: " +  links);
        System.out.println("Cuts: " +  cuts);
        System.out.println("Heapify: " +  heapify);
        System.out.println("Trees: " +  szt);
        System.out.println("HeavyOperation: " +  costmost);

    }
    public static void second(boolean lazyMelds,boolean lazyDecreaseKeys){
        double totalmilisecondtime =0;
        double minimal =0;
        double links =0;
        double cuts =0;
        double heapify =0;
        double szt =0;
        double costmost =0;
        System.out.println("lazyMelds :" +lazyMelds );
        System.out.println("lazyDecreaseKeys :" +lazyDecreaseKeys +"\n");
        for(int j =0;j<20;j++){
            int cost =0;
            long startTime = System.nanoTime();
            List<Integer> lst = new ArrayList<>();
            for(int i =1;i<=n;i++){
                lst.add(i);
            }
            Collections.shuffle(lst);
            List<Heap.HeapItem> lstitems = new ArrayList<Heap.HeapItem>();
            for(int i =0;i<n;i++){
                lstitems.add(null);
            }
            Heap h = new Heap(lazyMelds, lazyDecreaseKeys);
            for(int i =0;i<n;i++){
                int costbefore = SumOfLinksCutsHeapify(h);
                Heap.HeapItem nodeitem = h.insert(lst.get(i),"");
                int costafter = SumOfLinksCutsHeapify(h);
                int costoperation = costafter - costbefore;
                lstitems.set(nodeitem.key-1, nodeitem);
                cost =  Math.max(cost,costoperation);
            }
            int costbefore = SumOfLinksCutsHeapify(h);
            h.deleteMin();
            int costafter = SumOfLinksCutsHeapify(h);
            int costoperation = costafter - costbefore;
            cost =  Math.max(cost,costoperation);
            while(h.size() > 46){
                costbefore = SumOfLinksCutsHeapify(h);
                h.delete(lstitems.get(lstitems.size()-1));
                costafter = SumOfLinksCutsHeapify(h);
                costoperation = costafter - costbefore;
                cost =  Math.max(cost,costoperation);
                lstitems.remove(lstitems.size()-1);
            }
            long endTime = System.nanoTime();
            long durationInNano = (endTime - startTime);
            long durationInMillis = durationInNano / 1_000_000;
            totalmilisecondtime += durationInMillis;
            minimal += h.findMin().key;
            links += h.totalLinks();
            cuts += h.totalCuts();
            heapify += h.totalHeapifyCosts();
            szt += h.numTrees();
            costmost +=cost;
        }    
        totalmilisecondtime/=20;
        minimal/=20;
        links/=20;
        cuts/=20;
        heapify/=20;
        szt/=20;
        costmost/=20;
        System.out.println("Time: " + totalmilisecondtime);
        System.out.println("Min: " +  minimal);
        System.out.println("Links: " +  links);
        System.out.println("Cuts: " +  cuts);
        System.out.println("Heapify: " +  heapify);
        System.out.println("Trees: " +  szt);
        System.out.println("HeavyOperation: " +  costmost);

    }
    public static void third(boolean lazyMelds,boolean lazyDecreaseKeys){
        double totalmilisecondtime =0;
        double minimal =0;
        double links =0;
        double cuts =0;
        double heapify =0;
        double szt =0;
        double costmost =0;
        System.out.println("lazyMelds :" +lazyMelds );
        System.out.println("lazyDecreaseKeys :" +lazyDecreaseKeys +"\n");
        for(int j =0;j<20;j++){
            int cost =0;
            long startTime = System.nanoTime();
            List<Integer> lst = new ArrayList<>();
            for(int i =1;i<=n;i++){
                lst.add(i);
            }
            Collections.shuffle(lst);
            List<Heap.HeapItem> lstitems = new ArrayList<Heap.HeapItem>();
            for(int i =0;i<n;i++){
                lstitems.add(null);
            }
            Heap h = new Heap(lazyMelds, lazyDecreaseKeys);
            for(int i =0;i<n;i++){
                int costbefore = SumOfLinksCutsHeapify(h);
                Heap.HeapItem nodeitem = h.insert(lst.get(i),"");
                int costafter = SumOfLinksCutsHeapify(h);
                int costoperation = costafter - costbefore;
                lstitems.set(nodeitem.key-1, nodeitem);
                cost =  Math.max(cost,costoperation);
            }
            int costbefore = SumOfLinksCutsHeapify(h);
            h.deleteMin();
            int costafter = SumOfLinksCutsHeapify(h);
            int costoperation = costafter - costbefore;
            cost =  Math.max(cost,costoperation);
            int n0_1 = n/10;
            n0_1 +=1;
            for(int i =0;i<n0_1;i++){
                costbefore = SumOfLinksCutsHeapify(h);
                h.decreaseKey(lstitems.get(lstitems.size()-1),lstitems.get(lstitems.size()-1).key);
                costafter = SumOfLinksCutsHeapify(h);
                costoperation = costafter - costbefore;
                cost =  Math.max(cost,costoperation);
                lstitems.remove(lstitems.size()-1);
            }
            costbefore = SumOfLinksCutsHeapify(h);
            h.deleteMin();
            costafter = SumOfLinksCutsHeapify(h);
            costoperation = costafter - costbefore;
            cost =  Math.max(cost,costoperation);
            long endTime = System.nanoTime();
            long durationInNano = (endTime - startTime);
            long durationInMillis = durationInNano / 1_000_000;
            totalmilisecondtime += durationInMillis;
            minimal += h.findMin().key;
            links += h.totalLinks();
            cuts += h.totalCuts();
            heapify += h.totalHeapifyCosts();
            szt += h.numTrees();
            costmost += cost;
        }
        totalmilisecondtime/=20;
        minimal/=20;
        links/=20;
        cuts/=20;
        heapify/=20;
        szt/=20;
        costmost/=20;
        System.out.println("Time: " + totalmilisecondtime);
        System.out.println("Min: " +  minimal);
        System.out.println("Links: " +  links);
        System.out.println("Cuts: " +  cuts);
        System.out.println("Heapify: " +  heapify);
        System.out.println("Trees: " +  szt);
        System.out.println("HeavyOperation: " +  costmost);
    }
    public static void main(String[] args) {
        second(true,true);
    }
}
