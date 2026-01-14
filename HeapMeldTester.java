public class HeapMeldTester {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public static void main(String[] args) {
        System.out.println(ANSI_YELLOW + "--- Starting MELD Tests ---" + ANSI_RESET + "\n");

        try {
            testBasicMeld();            // 1. איחוד פשוט של שתי ערימות מלאות
            testMeldUpdatesMin();       // 2. בדיקה שהמינימום מתעדכן נכון
            testMeldWithEmpty();        // 3. איחוד עם ערימה ריקה (משני הצדדים)
            testLazyMeldStructure();    // 4. בדיקת מבנה כש-lazyMelds=true (חיבור פשוט)
            testNonLazyMeldStructure(); // 5. בדיקת מבנה כש-lazyMelds=false (Consolidate)

            System.out.println(ANSI_GREEN + "\n>>> ALL MELD TESTS PASSED! <<<" + ANSI_RESET);
        } catch (Exception e) {
            System.out.println(ANSI_RED + "\n>>> TEST FAILED <<<" + ANSI_RESET);
            e.printStackTrace();
        }
    }

    /**
     * בדיקה 1: איחוד בסיסי
     * בודקים שגודל הערימה (Size) מתעדכן לסכום הגדלים.
     */
    public static void testBasicMeld() {
        System.out.print("Test 1: Basic Meld (Size Check)... ");
        
        // ערימה 1 (lazyMelds=true)
        Heap h1 = new Heap(true, true); 
        h1.insert(10, "A");
        h1.insert(20, "B"); // גודל 2
        // ערימה 2
        Heap h2 = new Heap(true, true);
        h2.insert(30, "C");
        h2.insert(40, "D");
        h2.insert(50, "E"); // גודל 3

        // ביצוע האיחוד
        h1.meld(h2);

        // בדיקות
        assertCondition(h1.size() == 5, "Size should be 2 + 3 = 5");
        assertCondition(h1.size() > 0, "Heap should not be empty");
        
        // בדיקה שכל האיברים נגישים (על ידי מחיקת כולם)
       

    }

    /**
     * בדיקה 2: עדכון המינימום
     * אם ל-h2 יש מינימום קטן יותר מ-h1, ה-Min של h1 צריך להתעדכן.
     */
    public static void testMeldUpdatesMin() {
        System.out.print("Test 2: Min Pointer Update... ");
        
        Heap h1 = new Heap(true, true);
        h1.insert(100, "Big");
        
        Heap h2 = new Heap(true, true);
        Heap.HeapItem small = h2.insert(10, "Small"); // זה המינימום האמיתי

        // המינימום נמצא ב-h2. אחרי המלד, h1 צריך להצביע עליו.
        h1.meld(h2);

        assertCondition(h1.findMin() == small, "Min pointer should point to the smaller element (10)");
        assertCondition(h1.findMin().key == 10, "Min value should be 10");

        System.out.println(ANSI_GREEN + "OK" + ANSI_RESET);
    }

    /**
     * בדיקה 3: מקרי קצה עם ערימות ריקות
     */
    public static void testMeldWithEmpty() {
        System.out.print("Test 3: Meld with Empty Heaps... ");
        
        // מקרה א: איחוד של ערימה מלאה עם ריקה
        Heap h1 = new Heap(true, true);
        h1.insert(5, "Val");
        Heap hEmpty = new Heap(true, true);
        
        h1.meld(hEmpty);
        assertCondition(h1.size() == 1, "Meld with empty should change nothing");
        assertCondition(h1.findMin().key == 5, "Min remains same");

        // מקרה ב: איחוד של ערימה ריקה עם מלאה
        Heap hEmpty2 = new Heap(true, true);
        Heap h2 = new Heap(true, true);
        h2.insert(3, "Val");
        
        hEmpty2.meld(h2);
        assertCondition(hEmpty2.size() == 1, "Empty heap should take items from other heap");
        assertCondition(hEmpty2.findMin().key == 3, "Min should update");

        System.out.println(ANSI_GREEN + "OK" + ANSI_RESET);
    }

    /**
     * בדיקה 4: מבנה Lazy (ללא איחוד עצים)
     * כאשר lazyMelds=true, מספר העצים צריך להיות פשוט סכום העצים בשתי הערימות.
     */
    public static void testLazyMeldStructure() {
        System.out.print("Test 4: Lazy Meld Structure... ");
        
        // תזכורת: בנאי(lazyMelds, lazyDecreaseKeys)
        // נוודא ש-lazyMelds = true
        // שים לב: אם הבנאי שלך הפוך, תחליפי כאן את הסדר!
        boolean lazyMelds = true;
        Heap h1 = new Heap(lazyMelds, true); 
        h1.insert(1, "A"); 
        h1.insert(2, "B"); 
        // בפיבונאצ'י, insert יוצר עץ חדש. אז ב-h1 יש 2 עצים.

        Heap h2 = new Heap(lazyMelds, true);
        h2.insert(3, "C");
        h2.insert(4, "D");
        h2.insert(5, "E");
        // ב-h2 יש 3 עצים.

        assertCondition(h1.numTrees() == 2, "Setup h1: should have 2 trees");
        assertCondition(h2.numTrees() == 3, "Setup h2: should have 3 trees");

        h1.meld(h2);

        // בגלל שזה lazy, אנחנו מצפים ל-2 + 3 = 5 עצים (ללא consolidate)
        assertCondition(h1.numTrees() == 5, "Lazy meld should simply concatenate roots (2+3=5 trees)");

        System.out.println(ANSI_GREEN + "OK" + ANSI_RESET);
    }

    /**
     * בדיקה 5: מבנה Non-Lazy (עם איחוד עצים)
     * כאשר lazyMelds=false, הפעולה צריכה לבצע consolidate.
     */
    public static void testNonLazyMeldStructure() {
        System.out.print("Test 5: Non-Lazy Meld (Consolidation)... ");
        
        // תזכורת: אנחנו רוצים lazyMelds = false
        // נא לשים לב לסדר הפרמטרים בבנאי שלך!
        boolean lazyMelds = false; 
        Heap h1 = new Heap(lazyMelds, true);
        h1.insert(10, "A"); // עץ מדרגה 0

        Heap h2 = new Heap(lazyMelds, true);
        h2.insert(20, "B"); // עץ מדרגה 0

        // יש לנו שני עצים מדרגה 0.
        // אם lazyMelds = false, המלד יקרא ל-consolidate.
        // consolidate יאחד את שני העצים לעץ אחד מדרגה 1.
        
        h1.meld(h2);

        // ציפייה: עץ אחד בלבד (שבו 10 הוא אבא של 20)
        assertCondition(h1.numTrees() == 1, "Non-lazy meld should consolidate trees. Expected 1 tree, got " + h1.numTrees());
        assertCondition(h1.size() == 2, "Size is correct");

        System.out.println(ANSI_GREEN + "OK" + ANSI_RESET);
    }

    private static void assertCondition(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Assertion Failed: " + message);
        }
    }
}