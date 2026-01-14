
public class TestMeld {
	public static void main(String[] args) {
	System.out.println("\n=== TEST 1: meld with empty heap ===");
	Heap h1 = new Heap(false, true);
	Heap h2 = new Heap(false, true);
	h1.insert(10, "a");
	h1.insert(20, "b");
	System.out.println("Before meld:");
	h1.printHeap();
	h1.meld(h2);
	System.out.println("After meld:");
	h1.printHeap();
	
	Heap h3 = new Heap(false, true);
	Heap h4 = new Heap(false, true);

	
	h3.insert(10, "y");
	h3.printHeap();
	h4.printHeap();
	h3.meld(h4);

	System.out.println("After meld:");
	h3.printHeap();
	
	System.out.println("\n=== TEST 3: meld with lazyMelds ===");

	Heap h5 = new Heap(false, true);
	Heap h6 = new Heap(false, true);
	h5.insert(10, "a");
	h5.insert(40, "b");
	h6.insert(20, "c");
	h6.insert(30, "d");
	h5.printHeap();
	h6.printHeap();
	h5.meld(h6);
	System.out.println("After meld:");
	h5.printHeap();
	
	System.out.println("\n=== TEST 4: meld with successive_link ===");
	Heap h7 = new Heap(false, false);
	Heap h8 = new Heap(false, false);
	for (int i = 0; i < 4; i++) {
	    h7.insert(10 + i * 10, "h7_" + i);
	}
	for (int i = 0; i < 4; i++) {
	    h8.insert(15 + i * 10, "h8_" + i);
	}
	h7.printHeap();
	h8.printHeap();
	h7.meld(h8);
	System.out.println("After meld:");
	h7.printHeap();
	
	System.out.println("\n=== TEST 5: meld after deleteMin ===");

	Heap h9 = new Heap(false, true);
	Heap h10 = new Heap(false, true);

	for (int i = 0; i < 6; i++) {
	    h9.insert(50 + i, "a" + i);
	}
	for (int i = 0; i < 6; i++) {
	    h10.insert(10 + i, "b" + i);
	}
	h9.deleteMin();
	h10.deleteMin();
	h9.printHeap();
	h10.printHeap();
	h9.meld(h10);
	System.out.println("After meld:");
	h9.printHeap();
	
	System.out.println("\n=== TEST 6: meld complex heaps ===");

	Heap h11 = new Heap(false, true);
	Heap h12 = new Heap(false, true);

	Heap.HeapItem[] arr = new Heap.HeapItem[10];

	for (int i = 0; i < 10; i++) {
	    arr[i] = h11.insert(100 + i, "x" + i);
	}

	h11.delete(arr[3]);
	h11.delete(arr[5]);

	for (int i = 0; i < 5; i++) {
	    h12.insert(50 + i * 3, "y" + i);
	}

	h11.printHeap();
	h12.printHeap();

	h11.meld(h12);

	System.out.println("After meld:");
	h11.printHeap();

	}
}
