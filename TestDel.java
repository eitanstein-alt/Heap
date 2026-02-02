
public class TestDel {
	 public static void main(String[] args) {

	        System.out.println("=== TEST 1: delete single element ===");
	        Heap h1 = new Heap(false, true);
	        Heap.HeapItem a = h1.insert(10, "a");
	        h1.printHeap();
	        h1.delete(a);
	        h1.printHeap();

	        System.out.println("\n=== TEST 2: delete leaf ===");
	        Heap h2 = new Heap(false, true);
	        Heap.HeapItem b = h2.insert(10, "b");
	        Heap.HeapItem c = h2.insert(20, "c");
	        Heap.HeapItem d = h2.insert(30, "d");
	        h2.printHeap();
	        h2.delete(b);   // עלה
	        h2.printHeap();

	        System.out.println("\n=== TEST 3: delete root ===");
	        Heap h3 = new Heap(false, true);
	        Heap.HeapItem e = h3.insert(10, "e");
	        Heap.HeapItem f = h3.insert(5, "f");
	        Heap.HeapItem g = h3.insert(20, "g");
	        h3.printHeap();
	        h3.delete(f);   // מינימום
	        h3.printHeap();

	        System.out.println("\n=== TEST 4: delete internal node ===");
	        Heap h4 = new Heap(false, true);
	        Heap.HeapItem h = h4.insert(10, "h");
	        Heap.HeapItem i = h4.insert(20, "i");
	        Heap.HeapItem j = h4.insert(30, "j");
	        Heap.HeapItem k = h4.insert(40, "k");
	        Heap.HeapItem l = h4.insert(50, "l");
	        h4.printHeap();
	        h4.delete(i);   // צומת פנימי
	        h4.printHeap();

	        System.out.println("\n=== TEST 5: delete after decreaseKey ===");
	        Heap h5 = new Heap(false, true);
	        Heap.HeapItem m = h5.insert(100, "m");
	        Heap.HeapItem n = h5.insert(200, "n");
	        Heap.HeapItem o = h5.insert(300, "o");
	        h5.printHeap();
	        h5.decreaseKey(o, 250); // נהיה מינימום
	        h5.printHeap();
	        h5.delete(o);
	        h5.printHeap();

	        System.out.println("\n=== TEST 6: delete many ===");
	        Heap h6 = new Heap(false, true);
	        Heap.HeapItem[] arr = new Heap.HeapItem[10];
	        for (int t = 0; t < 10; t++) {
	            arr[t] = h6.insert(100 + t, "x" + t);
	        }
	        h6.printHeap();
	        for (int t = 0; t < 10; t += 2) {
	            h6.delete(arr[t]);
	            h6.printHeap();
	        }
	    }
}
