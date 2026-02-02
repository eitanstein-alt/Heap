import java.util.HashSet;
import java.util.Set;

public class HeapTester {

    // צבעים לזיהוי מהיר של שגיאות
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";

    public static void main(String[] args) {
        System.out.println("--- Starting Comprehensive deleteMin Tests ---\n");

        try {
            testDeleteMinEmpty();           // 1. מחיקה מערימה ריקה
            testDeleteMinSingle();          // 2. מחיקת איבר יחיד
            testDeleteMinOrder();           // 3. האם המינימום הבא נבחר נכון?
            testConsolidationLogic();       // 4. בדיקת Successive Link (איחוד דרגות)
            testDeleteMinWithChildren();    // 5. האם ילדים עולים לרשימת השורשים?
            testStressTest();               // 6. בדיקת עומס קטנה

            System.out.println(ANSI_GREEN + "\n>>> ALL deleteMin TESTS PASSED! <<<" + ANSI_RESET);
        } catch (Exception e) {
            System.out.println(ANSI_RED + "\n>>> TEST FAILED <<<" + ANSI_RESET);
            e.printStackTrace();
        }
    }

    /**
     * בדיקה 1: מחיקה מערימה ריקה
     * ציפייה: לא קורס, לא קורה כלום.
     */
    public static void testDeleteMinEmpty() {
        System.out.print("Test 1: Empty Heap... ");
        Heap h = new Heap(true, true);
        
        try {
            h.deleteMin();
        } catch (Exception e) {
            throw new RuntimeException("deleteMin on empty heap threw exception: " + e.getMessage());
        }

        assertCondition(h.size() == 0, "Size should remain 0");
        assertCondition(h.findMin() == null, "Min should remain null");
        System.out.println(ANSI_GREEN + "OK" + ANSI_RESET);
    }

    /**
     * בדיקה 2: מחיקת איבר יחיד
     * ציפייה: הערימה מתאפסת לחלוטין.
     */
    public static void testDeleteMinSingle() {
        System.out.print("Test 2: Single Node... ");
        Heap h = new Heap(true, true);
        h.insert(10, "A");
        h.deleteMin();

        assertCondition(h.size() == 0, "Size should be 0");
        assertCondition(h.findMin() == null, "Min should be null");
        assertCondition(h.numTrees() == 0, "NumTrees should be 0");
        assertCondition(h.head == null, "Head should be null");
        System.out.println(ANSI_GREEN + "OK" + ANSI_RESET);
    }

    /**
     * בדיקה 3: סדר הוצאת איברים
     * מכניסים בסדר מבולגן, מוציאים אחד אחד.
     * ציפייה: האיברים יוצאים ממויינים (Sort).
     */
    public static void testDeleteMinOrder() {
        System.out.print("Test 3: Order Validity... ");
        Heap h = new Heap(true, true); // Lazy Insert
        
        // הכנסה לא ממויינת
        int[] keys = {50, 10, 40, 20, 30};
        for (int k : keys) h.insert(k, "");

        // מחיקה ראשונה (10)
        assertCondition(h.findMin().key == 10, "First min should be 10");
        h.deleteMin();
        
        // מחיקה שניה (20)
        assertCondition(h.findMin().key == 20, "Second min should be 20");
        h.deleteMin();

        // מחיקה שלישית (30)
        assertCondition(h.findMin().key == 30, "Third min should be 30");
        h.deleteMin();

        assertCondition(h.size() == 2, "Size should be 2 remaining");
        System.out.println(ANSI_GREEN + "OK" + ANSI_RESET);
    }

    /**
     * בדיקה 4: לוגיקת Successive Link (קריטי!)
     * מכניסים הרבה איברים (שורשים בדרגה 0). אחרי deleteMin אחד,
     * הערימה חייבת להתארגן כך שאין שני עצים עם אותה דרגה.
     */
    public static void testConsolidationLogic() {
        System.out.print("Test 4: Consolidation (Duplicate Ranks)... ");
        
        Heap h = new Heap(true, true); // Lazy insertions
        
        // נכניס 8 איברים: 0, 1, 2... 7
        // מצב לפני מחיקה: 8 עצים, כולם דרגה 0.
        int n = 8;
        for (int i = 0; i < n; i++) {
            h.insert(i, "Info" + i);
        }

        // נמחק את המינימום (0). נשארו 7 איברים.
        // deleteMin אמור להפעיל successive_link על ה-7 הנותרים.
        h.deleteMin();

        assertCondition(h.size() == 7, "Size should be 7");
        
        // בדיקת יחידות הדרגות (No duplicate ranks)
        Set<Integer> ranks = new HashSet<>();
        Heap.HeapNode curr = h.head;
        int countNodes = 0;

        if (curr != null) {
            do {
                if (ranks.contains(curr.rank)) {
                    throw new RuntimeException("Found two trees with same rank: " + curr.rank + "! Consolidation failed.");
                }
                ranks.add(curr.rank);
                countNodes++;
                curr = curr.next;
            } while (curr != h.head);
        }

        assertCondition(countNodes == h.numTrees(), "Counted trees via next pointers doesn't match numTrees()");
        
        // עבור 7 איברים (ייצוג בינארי 111), אנחנו מצפים ל-3 עצים: דרגות 0, 1, 2.
        assertCondition(ranks.contains(0), "Should create tree of rank 0");
        assertCondition(ranks.contains(1), "Should create tree of rank 1");
        assertCondition(ranks.contains(2), "Should create tree of rank 2");
        assertCondition(h.totalLinks() > 0, "Links counter should increase after consolidation");

        System.out.println(ANSI_GREEN + "OK" + ANSI_RESET);
    }

    /**
     * בדיקה 5: טיפול בילדים
     * יוצרים עץ עם עומק, מוחקים את האבא, ומוודאים שהילדים הפכו לשורשים.
     */
    public static void testDeleteMinWithChildren() {
        System.out.print("Test 5: Promoting Children... ");
        
        // שלב 1: יצירת עץ מורכב (דרגה 2) ע"י מחיקות
        Heap h = new Heap(true, true);
        h.insert(10, "A"); 
        h.insert(20, "B");
        h.insert(30, "C");
        h.insert(40, "D");
        h.insert(5, "MIN"); // נוסיף מינימום קטן
        
        // כרגע: 5 שורשים. נמחק את 5 -> הארבעה הנותרים יתאחדו.
        // 4 איברים (10,20,30,40) -> עץ אחד בדרגה 2 (מכיל 4 צמתים).
        h.deleteMin(); 
        
        assertCondition(h.numTrees() == 1, "Should have 1 tree after consolidation of 4 items");
        assertCondition(h.findMin().key == 10, "New min should be 10 (root of the tree)");
        assertCondition(h.head.rank == 2, "Root should have rank 2");

        // שלב 2: מחיקת השורש (10).
        // הילדים שלו (שהם שורשי תתי-עצים) צריכים לעלות למעלה ולהפוך לשורשים חדשים.
        h.deleteMin();

        assertCondition(h.size() == 3, "Size should be 3");
        // עבור 3 איברים (20,30,40), הייצוג הבינארי הוא 11 -> שני עצים (דרגות 0, 1).
        assertCondition(h.numTrees() == 2, "Should have 2 trees (ranks 0 and 1) representing 3 nodes");
        assertCondition(h.findMin().key == 20, "New min should be 20");

        System.out.println(ANSI_GREEN + "OK" + ANSI_RESET);
    }
    
    /**
     * בדיקה 6: עומס קל (Stress Test)
     * ודואג ששום דבר לא קורס עם מספרים גדולים יותר
     */
    public static void testStressTest() {
        System.out.print("Test 6: Mini Stress Test (1000 nodes)... ");
        Heap h = new Heap(true, true);
        int n = 1000;
        for (int i = n; i >= 1; i--) {
            h.insert(i, "");
        }
        
        assertCondition(h.findMin().key == 1, "Min should be 1");
        h.deleteMin(); // מוחק את 1, מפעיל איחוד מסיבי על 999 צמתים
        
        assertCondition(h.size() == 999, "Size should be 999");
        assertCondition(h.findMin().key == 2, "New min should be 2");
        
        // 999 בבינארי יש לו הרבה '1', אז יהיו הרבה עצים, אבל לא 999.
        // בערך 8-10 עצים.
        assertCondition(h.numTrees() < 20, "Num trees should be small (logarithmic) after consolidation");
        
        System.out.println(ANSI_GREEN + "OK" + ANSI_RESET);
    }

    private static void assertCondition(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Assertion Failed: " + message);
        }
    }
}