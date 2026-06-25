package app;

import java.util.ArrayList;
import java.util.Scanner;

import models.Document;
import modules.analytics.BPlusTree;
import modules.analytics.BTree;
import modules.analytics.FenwickTree;
import modules.analytics.SegmentTree;
import modules.graph.BFS;
import modules.graph.DFS;
import modules.graph.Graph;
import modules.graph.MST;
import modules.indexing.avl.AVLTree;
import modules.indexing.bst.BST;
import modules.optimization.ActivitySelection;
import modules.optimization.FractionalKnapsack;
import modules.optimization.Knapsack01;
import modules.optimization.LIS;
import modules.path.BellmanFord;
import modules.path.Dijkstra;
import modules.path.FloydWarshall;
import modules.path.TopologicalSort;
import modules.sorting.HeapSort;
import modules.sorting.MergeSort;
import modules.sorting.QuickSort;
import modules.sorting.RadixSort;

public class Main {

    
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String[] COLORS = {
        "\u001B[31m", "\u001B[32m", "\u001B[33m", "\u001B[34m", "\u001B[35m", "\u001B[36m",
        "\u001B[91m", "\u001B[92m", "\u001B[93m", "\u001B[94m", "\u001B[95m", "\u001B[96m"
    };
    
    public static String logoColor;
    public static String borderColor;
    public static String titleColor;
    public static String optionColor;
    
    static {
        java.util.Random rand = new java.util.Random();
        logoColor = COLORS[rand.nextInt(COLORS.length)];
        do { borderColor = COLORS[rand.nextInt(COLORS.length)]; } while(borderColor.equals(logoColor));
        do { titleColor = COLORS[rand.nextInt(COLORS.length)]; } while(titleColor.equals(borderColor) || titleColor.equals(logoColor));
        do { optionColor = COLORS[rand.nextInt(COLORS.length)]; } while(optionColor.equals(titleColor) || optionColor.equals(borderColor));
    }
    public static void printMenuTop() {
        System.out.println();
        System.out.println(borderColor + "    ╭─────────────────────────────────────────────────────────────────────────────╮" + ANSI_RESET);
    }

    public static void printMenuOption(String text) {
        int totalInnerWidth = 77;
        int textLen = text.length();
        int paddingRight = totalInnerWidth - textLen - 2; 
        if(paddingRight < 0) paddingRight = 0;
        
        System.out.print(borderColor + "    │  " + ANSI_RESET);
        System.out.print(optionColor + text + ANSI_RESET);
        for(int i=0; i<paddingRight; i++) System.out.print(" ");
        System.out.println(borderColor + "│" + ANSI_RESET);
    }

    public static void printMenuBottom() {
        System.out.println(borderColor + "    ╰─────────────────────────────────────────────────────────────────────────────╯" + ANSI_RESET);
        System.out.println();
    }

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Document> documents = new ArrayList<>();
    static BST bst = new BST();
    static AVLTree avl = new AVLTree();
    static Graph graph = new Graph(0);
    static int routeCosts[][] = new int[0][0];

    public static void main(String[] args) {

        while (true) {

            printHeader("DOCUTRACK LOGIN");

            System.out.print(optionColor + "Username: " + ANSI_RESET);
            String username = sc.next();

            System.out.print(optionColor + "Password: " + ANSI_RESET);
            String password = sc.next();

            if (username.equals("admin") &&
                password.equals("admin123")) {

                System.out.println("\nWelcome Admin");
                adminMenu();

            } else if (username.equals("user") &&
                       password.equals("user123")) {

                System.out.println("\nWelcome User");
                userMenu();

            } else {

                System.out.println("Invalid Credentials");
            }
        }
    }

    // ==========================
    // ADMIN MENUS
    // ==========================

    static void adminMenu() {

        while (true) {

            printHeader("DOCUTRACK SYSTEM");

            printMenuTop();
            printMenuOption("  [1] CO1 - Document Indexing");
            printMenuOption("  [2] CO2 - Analytics");
            printMenuOption("  [3] CO3 - Document Network");
            printMenuOption("  [4] CO4 - Path Optimization");
            printMenuOption("  [5] CO5 - Sorting & Ranking");
            printMenuOption("  [6] CO6 - Storage Optimization");
            printMenuOption("  [7] Logout");
            printMenuBottom();

            System.out.print(optionColor + "Enter Choice: " + ANSI_RESET);

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    co1Menu();
                    break;

                case 2:
                    co2Menu();
                    break;

                case 3:
                    co3Menu();
                    break;

                case 4:
                    co4Menu();
                    break;

                case 5:
                    co5Menu();
                    break;

                case 6:
                    co6Menu();
                    break;

                case 7:
                    return;

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    // ==========================
    // USER MENU
    // ==========================

    static void userMenu() {

        while (true) {

            printHeader("USER MENU");

            printMenuTop();
            printMenuOption("  [1] Search Document");
            printMenuOption("  [2] View Documents");
            printMenuOption("  [3] Logout");
            printMenuBottom();

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    searchSharedDocument();
                    break;

                case 2:
                    displayDocuments();
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    // ==========================
    // CO1 MENU
    // ==========================

    static void co1Menu() {

        while(true) {

            printHeader("CO1");
            printMenuTop();
            printMenuOption("  [1] BST Operations");
            printMenuOption("  [2] AVL Operations");
            printMenuOption("  [3] Back");
            printMenuBottom();

            int choice = sc.nextInt();

            switch(choice) {

                case 1:
                    bstMenu();
                    break;

                case 2:
                    avlMenu();
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
    // ==========================
    // CO2 MENU
    // ==========================

    static void co2Menu() {

        FenwickTree ft = buildFenwickTree();

        int arr[] = getDocumentIds();
        SegmentTree st = null;

        if(arr.length > 0) {

            st = new SegmentTree(arr);
        }

        BTree bt = buildBTree();
        BPlusTree bpt = buildBPlusTree();

        while(true) {

            printHeader("CO2");
            printMenuTop();
            printMenuOption("  [1] Fenwick Tree");
            printMenuOption("  [2] Segment Tree");
            printMenuOption("  [3] B Tree");
            printMenuOption("  [4] B+ Tree");
            printMenuOption("  [5] Back");
            printMenuBottom();

            int choice = sc.nextInt();

            switch(choice) {

                case 1:

                    if(documents.isEmpty()) {

                        System.out.println("No documents available");
                        break;
                    }

                    System.out.print(optionColor + "Enter Document Number: " + ANSI_RESET);
                    int day = sc.nextInt();

                    if(day < 1 || day > documents.size()) {

                        System.out.println("Invalid document number");
                        break;
                    }

                    System.out.print(optionColor + "Enter Access Count: " + ANSI_RESET);
                    int count = sc.nextInt();

                    ft.update(day, count);

                    System.out.println(
                            "Total Accesses Till Document "
                                    + day + " = "
                                    + ft.sum(day));

                    break;

                case 2:

                    if(arr.length == 0) {

                        System.out.println("No documents available");
                        break;
                    }

                    System.out.print(optionColor + "Enter Left Index: " + ANSI_RESET);
                    int l = sc.nextInt();

                    System.out.print(optionColor + "Enter Right Index: " + ANSI_RESET);
                    int r = sc.nextInt();

                    if(l < 0 || r >= arr.length || l > r) {

                        System.out.println("Invalid range");
                        break;
                    }

                    System.out.println(
                    	    "Range Sum = " +
                    	    st.query(
                    	        1,
                    	        0,
                    	        arr.length - 1,
                    	        l,
                    	        r
                    	    )
                    	);

                    break;

                case 3:

                    System.out.println("B Tree Contents:");
                    bt.display();

                    break;

                case 4:

                    System.out.print(optionColor + "Range Start: " + ANSI_RESET);
                    int start = sc.nextInt();

                    System.out.print(optionColor + "Range End: " + ANSI_RESET);
                    int end = sc.nextInt();

                    bpt.rangeSearch(start, end);

                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
    static void co3Menu() {

        while(true) {

            System.out.println(
                    "\n===== CO3 =====");

            System.out.println(
                    "1. View Document Numbers");

            System.out.println(
                    "2. Add Relationship");

            System.out.println(
                    "3. Display Graph");

            System.out.println(
                    "4. BFS Traversal");

            System.out.println(
                    "5. DFS Traversal");

            System.out.println(
                    "6. MST");

            System.out.println(
                    "7. Back");

            int choice =
                    sc.nextInt();

            switch(choice) {

                case 1:

                    displayDocumentsWithNumbers();

                    break;

                case 2:

                    if(documents.isEmpty()) {

                        System.out.println("Add documents first");
                        break;
                    }

                    System.out.print(
                            "Source: ");

                    int s =
                            sc.nextInt();

                    System.out.print(
                            "Destination: ");

                    int d =
                            sc.nextInt();

                    graph.addEdge(s,d);

                    break;

                case 3:

                    graph.displayDocuments(documents);

                    break;

                case 4:

                    if(documents.isEmpty()) {

                        System.out.println("Add documents first");
                        break;
                    }

                    System.out.print(
                            "Start Vertex: ");

                    int start =
                            sc.nextInt();

                    if(start < 0 || start >= documents.size()) {

                        System.out.println("Invalid document number");
                        break;
                    }

                    BFS.bfs(
                            graph,
                            start);

                    break;

                case 5:

                    if(documents.isEmpty()) {

                        System.out.println("Add documents first");
                        break;
                    }

                    System.out.print(
                            "Start Vertex: ");

                    start =
                            sc.nextInt();

                    if(start < 0 || start >= documents.size()) {

                        System.out.println("Invalid document number");
                        break;
                    }

                    boolean visited[] =
                            new boolean[documents.size()];

                    DFS.dfs(
                            graph,
                            start,
                            visited);

                    System.out.println();

                    break;

                case 6:

                    MST.displayMST();

                    break;

                case 7:

                    return;
            }
        }
    }

    static void co4Menu() {

        while(true) {

            printHeader("CO4");
            printMenuTop();
            printMenuOption("  [1] View Document Numbers");
            printMenuOption("  [2] Add Route Cost");
            printMenuOption("  [3] Display Route Costs");
            printMenuOption("  [4] Dijkstra Shortest Path");
            printMenuOption("  [5] Bellman-Ford From Source");
            printMenuOption("  [6] Floyd-Warshall All Pairs");
            printMenuOption("  [7] Topological Sort");
            printMenuOption("  [8] Back");
            printMenuBottom();

            int choice =
                    sc.nextInt();

            switch(choice) {

                case 1:

                    displayDocumentsWithNumbers();
                    break;

                case 2:

                    if(documents.size() < 2) {

                        System.out.println("Add at least two documents first");
                        break;
                    }

                    System.out.print(optionColor + "Source Document Number: " + ANSI_RESET);
                    int source =
                            sc.nextInt();

                    System.out.print(optionColor + "Destination Document Number: " + ANSI_RESET);
                    int destination =
                            sc.nextInt();

                    if(!validDocumentNumber(source) ||
                       !validDocumentNumber(destination)) {

                        System.out.println("Invalid document number");
                        break;
                    }

                    System.out.print(optionColor + "Cost: " + ANSI_RESET);
                    int cost =
                            sc.nextInt();

                    if(cost <= 0) {

                        System.out.println("Cost must be positive");
                        break;
                    }

                    routeCosts[source][destination] = cost;

                    System.out.println("Route cost added");
                    break;

                case 3:

                    displayRouteCosts();
                    break;

                case 4:

                    if(documents.size() < 2) {

                        System.out.println("Add at least two documents first");
                        break;
                    }

                    System.out.print(optionColor + "Source Document Number: " + ANSI_RESET);
                    source =
                            sc.nextInt();

                    System.out.print(optionColor + "Destination Document Number: " + ANSI_RESET);
                    destination =
                            sc.nextInt();

                    if(!validDocumentNumber(source) ||
                       !validDocumentNumber(destination)) {

                        System.out.println("Invalid document number");
                        break;
                    }

                    Dijkstra.shortestPath(
                            routeCosts,
                            source,
                            destination,
                            getDocumentTitles());

                    break;

                case 5:

                    if(documents.size() < 2) {

                        System.out.println("Add at least two documents first");
                        break;
                    }

                    System.out.print(optionColor + "Source Document Number: " + ANSI_RESET);
                    source =
                            sc.nextInt();

                    if(!validDocumentNumber(source)) {

                        System.out.println("Invalid document number");
                        break;
                    }

                    BellmanFord.shortestPath(
                            routeCosts,
                            source,
                            getDocumentTitles());

                    break;

                case 6:

                    if(documents.size() < 2) {

                        System.out.println("Add at least two documents first");
                        break;
                    }

                    FloydWarshall.allPairsShortestPath(
                            routeCosts,
                            getDocumentTitles());

                    break;

                case 7:

                    if(documents.isEmpty()) {

                        System.out.println("No documents available");
                        break;
                    }

                    TopologicalSort.sort(
                            routeCosts,
                            getDocumentTitles());

                    break;

                case 8:

                    return;

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    static void co5Menu() {

        while(true) {

            printHeader("CO5");
            printMenuTop();
            printMenuOption("  [1] Merge Sort by Document ID");
            printMenuOption("  [2] Quick Sort by Title");
            printMenuOption("  [3] Heap Sort by Author");
            printMenuOption("  [4] Radix Sort by Document ID");
            printMenuOption("  [5] Back");
            printMenuBottom();

            int choice =
                    sc.nextInt();

            switch(choice) {

                case 1:

                    displayDocumentList(
                            MergeSort.sortById(documents));
                    break;

                case 2:

                    displayDocumentList(
                            QuickSort.sortByTitle(documents));
                    break;

                case 3:

                    displayDocumentList(
                            HeapSort.sortByAuthor(documents));
                    break;

                case 4:

                    displayDocumentList(
                            RadixSort.sortById(documents));
                    break;

                case 5:

                    return;

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    static void co6Menu() {

        while(true) {

            printHeader("CO6");
            printMenuTop();
            printMenuOption("  [1] Activity Selection");
            printMenuOption("  [2] Fractional Knapsack");
            printMenuOption("  [3] 0/1 Knapsack");
            printMenuOption("  [4] LIS on Document IDs");
            printMenuOption("  [5] Back");
            printMenuBottom();

            int choice =
                    sc.nextInt();

            switch(choice) {

                case 1:

                    runActivitySelection();
                    break;

                case 2:

                    runFractionalKnapsack();
                    break;

                case 3:

                    runKnapsack01();
                    break;

                case 4:

                    LIS.display(getDocumentIds());
                    break;

                case 5:

                    return;

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    static void bstMenu() {

        while(true) {

            printHeader("BST");
            printMenuTop();
            printMenuOption("  [1] Insert Document");
            printMenuOption("  [2] Search Document");
            printMenuOption("  [3] Delete Document");
            printMenuOption("  [4] Display Inorder");
            printMenuOption("  [5] Minimum");
            printMenuOption("  [6] Maximum");
            printMenuOption("  [7] Back");
            printMenuBottom();

            int ch = sc.nextInt();

            switch(ch) {

                case 1:

                    System.out.print(optionColor + "Document ID: " + ANSI_RESET);
                    int id = sc.nextInt();

                    sc.nextLine();

                    System.out.print(optionColor + "Title: " + ANSI_RESET);
                    String title = sc.nextLine();

                    System.out.print(optionColor + "Author: " + ANSI_RESET);
                    String author = sc.nextLine();

                    Document doc =
                            new Document(
                                    id,
                                    title,
                                    author);

                    addSharedDocument(doc);

                    break;

                case 2:

                    System.out.print(optionColor + "Enter ID: " + ANSI_RESET);
                    id = sc.nextInt();

                    Document result =
                            bst.search(id);

                    if(result != null)
                        System.out.println(result);
                    else
                        System.out.println("Not Found");

                    break;

                case 3:

                    System.out.print(optionColor + "Enter ID: " + ANSI_RESET);
                    id = sc.nextInt();

                    deleteSharedDocument(id);

                    break;

                case 4:

                    bst.inorder(bst.root);

                    break;

                case 5:

                    bst.minimum();

                    break;

                case 6:

                    bst.maximum();

                    break;

                case 7:

                    return;
            }
        }
    }
    static void avlMenu() {

        while(true) {

            printHeader("AVL");
            printMenuTop();
            printMenuOption("  [1] Insert Document");
            printMenuOption("  [2] Search Document");
            printMenuOption("  [3] Display Inorder");
            printMenuOption("  [4] Back");
            printMenuBottom();

            int ch = sc.nextInt();

            switch(ch) {

                case 1:

                    System.out.print(optionColor + "Document ID: " + ANSI_RESET);
                    int id = sc.nextInt();

                    sc.nextLine();

                    System.out.print(optionColor + "Title: " + ANSI_RESET);
                    String title = sc.nextLine();

                    System.out.print(optionColor + "Author: " + ANSI_RESET);
                    String author = sc.nextLine();

                    Document doc =
                            new Document(
                                    id,
                                    title,
                                    author);

                    addSharedDocument(doc);

                    break;

                case 2:

                    System.out.print(optionColor + "Enter ID: " + ANSI_RESET);
                    id = sc.nextInt();

                    Document result =
                            avl.search(
                                    avl.root,
                                    id);

                    if(result != null)
                        System.out.println(result);
                    else
                        System.out.println("Not Found");

                    break;

                case 3:

                    avl.inorder(avl.root);

                    break;

                case 4:

                    return;
            }
        }
    }

    static void addSharedDocument(Document doc) {

        if(findDocument(doc.documentId) != null) {

            System.out.println("Document ID already exists");
            return;
        }

        documents.add(doc);
        rebuildIndexes();
        graph.resize(documents.size());
        resizeRouteCosts(documents.size());

        System.out.println("Inserted Successfully");
    }

    static void deleteSharedDocument(int id) {

        Document found =
                findDocument(id);

        if(found == null) {

            System.out.println("Document not found");
            return;
        }

        int removedIndex =
                documents.indexOf(found);

        documents.remove(found);
        rebuildIndexes();
        graph.removeVertex(removedIndex);
        removeRouteCost(removedIndex);

        System.out.println("Document deleted successfully");
    }

    static Document findDocument(int id) {

        for(Document doc : documents) {

            if(doc.documentId == id)
                return doc;
        }

        return null;
    }

    static void rebuildIndexes() {

        bst = new BST();
        avl = new AVLTree();

        for(Document doc : documents) {

            bst.insert(doc);
            avl.root = avl.insert(avl.root, doc);
        }
    }

    static int[] getDocumentIds() {

        int arr[] =
                new int[documents.size()];

        for(int i=0;i<documents.size();i++) {

            arr[i] =
                    documents.get(i).documentId;
        }

        return arr;
    }

    static FenwickTree buildFenwickTree() {

        FenwickTree ft =
                new FenwickTree(Math.max(1, documents.size()));

        for(int i=0;i<documents.size();i++) {

            ft.update(i + 1, 1);
        }

        return ft;
    }

    static BTree buildBTree() {

        BTree bt =
                new BTree();

        for(Document doc : documents) {

            bt.insert(doc.documentId);
        }

        return bt;
    }

    static BPlusTree buildBPlusTree() {

        BPlusTree bpt =
                new BPlusTree();

        for(Document doc : documents) {

            bpt.insert(doc.documentId);
        }

        return bpt;
    }

    static void searchSharedDocument() {

        System.out.print(optionColor + "Enter ID: " + ANSI_RESET);
        int id =
                sc.nextInt();

        Document result =
                bst.search(id);

        if(result != null)
            System.out.println(result);
        else
            System.out.println("Not Found");
    }

    static void displayDocuments() {

        if(documents.isEmpty()) {

            System.out.println("No documents available");
            return;
        }

        for(Document doc : documents) {

            System.out.println(doc);
        }
    }

    static void displayDocumentsWithNumbers() {

        if(documents.isEmpty()) {

            System.out.println("No documents available");
            return;
        }

        for(int i=0;i<documents.size();i++) {

            System.out.println(i + ": " +
                    documents.get(i));
        }
    }

    static boolean validDocumentNumber(int number) {

        return number >= 0 &&
               number < documents.size();
    }

    static void resizeRouteCosts(int newSize) {

        int newCosts[][] =
                new int[newSize][newSize];

        for(int i=0;i<newSize;i++) {

            for(int j=0;j<newSize;j++) {

                if(i == j)
                    newCosts[i][j] = 0;
                else
                    newCosts[i][j] = Dijkstra.INF;
            }
        }

        int limit =
                Math.min(routeCosts.length, newSize);

        for(int i=0;i<limit;i++) {

            for(int j=0;j<limit;j++) {

                newCosts[i][j] = routeCosts[i][j];
            }
        }

        routeCosts = newCosts;
    }

    static void removeRouteCost(int removedIndex) {

        int newSize =
                documents.size();

        int newCosts[][] =
                new int[newSize][newSize];

        for(int i=0;i<newSize;i++) {

            for(int j=0;j<newSize;j++) {

                if(i == j)
                    newCosts[i][j] = 0;
                else
                    newCosts[i][j] = Dijkstra.INF;
            }
        }

        for(int oldI=0;oldI<routeCosts.length;oldI++) {

            if(oldI == removedIndex)
                continue;

            for(int oldJ=0;oldJ<routeCosts.length;oldJ++) {

                if(oldJ == removedIndex)
                    continue;

                int newI =
                        oldI > removedIndex ?
                        oldI - 1 :
                        oldI;

                int newJ =
                        oldJ > removedIndex ?
                        oldJ - 1 :
                        oldJ;

                newCosts[newI][newJ] =
                        routeCosts[oldI][oldJ];
            }
        }

        routeCosts = newCosts;
    }

    static String[] getDocumentTitles() {

        String titles[] =
                new String[documents.size()];

        for(int i=0;i<documents.size();i++) {

            titles[i] =
                    documents.get(i).title;
        }

        return titles;
    }

    static void displayRouteCosts() {

        if(documents.isEmpty()) {

            System.out.println("No documents available");
            return;
        }

        System.out.println("Route Cost Matrix:");

        for(int i=0;i<routeCosts.length;i++) {

            System.out.print(i + " -> ");

            for(int j=0;j<routeCosts.length;j++) {

                if(routeCosts[i][j] == Dijkstra.INF)
                    System.out.print("INF ");
                else
                    System.out.print(routeCosts[i][j] + " ");
            }

            System.out.println();
        }
    }

    static void displayDocumentList(
            ArrayList<Document> list) {

        if(list.isEmpty()) {

            System.out.println("No documents available");
            return;
        }

        for(Document doc : list) {

            System.out.println(doc);
        }
    }

    static Document[] getDocumentArray() {

        Document arr[] =
                new Document[documents.size()];

        for(int i=0;i<documents.size();i++) {

            arr[i] = documents.get(i);
        }

        return arr;
    }

    static void runActivitySelection() {

        if(documents.isEmpty()) {

            System.out.println("No documents available");
            return;
        }

        displayDocumentsWithNumbers();

        int start[] =
                new int[documents.size()];

        int finish[] =
                new int[documents.size()];

        for(int i=0;i<documents.size();i++) {

            System.out.print(optionColor + "Start time for document " + i + ": " + ANSI_RESET);
            start[i] = sc.nextInt();

            System.out.print(optionColor + "Finish time for document " + i + ": " + ANSI_RESET);
            finish[i] = sc.nextInt();

            if(finish[i] < start[i]) {

                System.out.println("Finish time cannot be before start time");
                i--;
            }
        }

        ActivitySelection.select(
                getDocumentArray(),
                start,
                finish);
    }

    static void runFractionalKnapsack() {

        if(documents.isEmpty()) {

            System.out.println("No documents available");
            return;
        }

        int value[] =
                readValues();

        int weight[] =
                readWeights();

        System.out.print(optionColor + "Storage Capacity: " + ANSI_RESET);
        int capacity =
                sc.nextInt();

        FractionalKnapsack.optimize(
                getDocumentArray(),
                value,
                weight,
                capacity);
    }

    static void runKnapsack01() {

        if(documents.isEmpty()) {

            System.out.println("No documents available");
            return;
        }

        int value[] =
                readValues();

        int weight[] =
                readWeights();

        System.out.print(optionColor + "Storage Capacity: " + ANSI_RESET);
        int capacity =
                sc.nextInt();

        Knapsack01.optimize(
                getDocumentArray(),
                value,
                weight,
                capacity);
    }

    static int[] readValues() {

        displayDocumentsWithNumbers();

        int value[] =
                new int[documents.size()];

        for(int i=0;i<documents.size();i++) {

            System.out.print(optionColor + "Priority value for document " + i + ": " + ANSI_RESET);
            value[i] = sc.nextInt();
        }

        return value;
    }

    static int[] readWeights() {

        int weight[] =
                new int[documents.size()];

        for(int i=0;i<documents.size();i++) {

            System.out.print(optionColor + "Storage size for document " + i + ": " + ANSI_RESET);
            weight[i] = sc.nextInt();

            if(weight[i] <= 0) {

                System.out.println("Storage size must be positive");
                i--;
            }
        }

        return weight;
    }
    

    public static void printHeader(String title) {
        System.out.println();
        System.out.println(borderColor + "╔═══════════════════════════════════════════════════════════════════════════════════════╗" + ANSI_RESET);
        System.out.println(borderColor + "║ " + logoColor + "██████╗  ██████╗  ██████╗██╗   ██╗████████╗██████╗  █████╗  ██████╗██╗  ██╗" + borderColor + "           ║" + ANSI_RESET);
        System.out.println(borderColor + "║ " + logoColor + "██╔══██╗██╔═══██╗██╔════╝██║   ██║╚══██╔══╝██╔══██╗██╔══██╗██╔════╝██║ ██╔╝" + borderColor + "           ║" + ANSI_RESET);
        System.out.println(borderColor + "║ " + logoColor + "██║  ██║██║   ██║██║     ██║   ██║   ██║   ██████╔╝███████║██║     █████╔╝" + borderColor + "            ║" + ANSI_RESET);
        System.out.println(borderColor + "║ " + logoColor + "██║  ██║██║   ██║██║     ██║   ██║   ██║   ██╔══██╗██╔══██║██║     ██╔═██╗" + borderColor + "            ║" + ANSI_RESET);
        System.out.println(borderColor + "║ " + logoColor + "██████╔╝╚██████╔╝╚██████╗╚██████╔╝   ██║   ██║  ██║██║  ██║╚██████╗██║  ██╗" + borderColor + "           ║" + ANSI_RESET);
        System.out.println(borderColor + "║ " + logoColor + "╚═════╝  ╚═════╝  ╚═════╝ ╚═════╝    ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝" + borderColor + "           ║" + ANSI_RESET);
        System.out.println(borderColor + "╠═══════════════════════════════════════════════════════════════════════════════════════╣" + ANSI_RESET);
        int totalWidth = 87;
        int padding = (totalWidth - title.length()) / 2;
        int paddingRight = totalWidth - title.length() - padding;
        String format = borderColor + "║" + ANSI_RESET + "%" + (padding == 0 ? "" : padding) + "s" + titleColor + "%s" + ANSI_RESET + "%" + (paddingRight == 0 ? "" : paddingRight) + "s" + borderColor + "║%n" + ANSI_RESET;
        System.out.printf(format, "", title, "");
        System.out.println(borderColor + "╚═══════════════════════════════════════════════════════════════════════════════════════╝" + ANSI_RESET);
    }
}