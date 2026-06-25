# 📄 DocuTrack — Document Tracking & Management System

> A console-based Java application that demonstrates the practical application of **Data Structures & Algorithms (DSA)** to a real-world document management problem. Documents can be indexed, searched, networked, routed, sorted, and storage-optimised — all through a role-based, menu-driven CLI.

---

## 📑 Table of Contents

1. [Project Overview](#project-overview)
2. [Technology Stack](#technology-stack)
3. [Project Structure](#project-structure)
4. [Data Model](#data-model)
5. [Authentication & Roles](#authentication--roles)
6. [Module Breakdown (CO1 – CO6)](#module-breakdown-co1--co6)
   - [CO1 — Document Indexing (BST & AVL)](#co1--document-indexing-bst--avl)
   - [CO2 — Analytics (Fenwick, Segment, B-Tree, B+ Tree)](#co2--analytics-fenwick-segment-b-tree-b-tree-1)
   - [CO3 — Document Network (Graph, BFS, DFS, MST)](#co3--document-network-graph-bfs-dfs-mst)
   - [CO4 — Path Optimization (Dijkstra, Bellman-Ford, Floyd-Warshall, Topological Sort)](#co4--path-optimization-dijkstra-bellman-ford-floyd-warshall-topological-sort)
   - [CO5 — Sorting & Ranking (Merge, Quick, Heap, Radix)](#co5--sorting--ranking-merge-quick-heap-radix)
   - [CO6 — Storage Optimization (Activity Selection, Knapsack, LIS)](#co6--storage-optimization-activity-selection-knapsack-lis)
7. [Execution Flow](#execution-flow)
8. [Shared Data Architecture](#shared-data-architecture)
9. [How to Compile & Run](#how-to-compile--run)
10. [Class Diagram](#class-diagram)
11. [Complexity Analysis](#complexity-analysis)

---

## Project Overview

**DocuTrack** is a Java SE 21 console application built to showcase how various data structures and algorithms can be applied to a *Document Management System*. The project is organised into **six Course Outcome (CO) modules**, each targeting a distinct DSA category:

| CO   | Focus Area            | DSA Demonstrated                                      |
|------|-----------------------|-------------------------------------------------------|
| CO1  | Document Indexing     | Binary Search Tree (BST), AVL Tree                    |
| CO2  | Analytics             | Fenwick Tree, Segment Tree, B-Tree, B+ Tree           |
| CO3  | Document Network      | Adjacency-List Graph, BFS, DFS, MST                   |
| CO4  | Path Optimization     | Dijkstra, Bellman-Ford, Floyd-Warshall, Topological Sort |
| CO5  | Sorting & Ranking     | Merge Sort, Quick Sort, Heap Sort, Radix Sort         |
| CO6  | Storage Optimization  | Activity Selection (Greedy), Fractional Knapsack, 0/1 Knapsack (DP), LIS (DP) |

All modules share a **single, unified document pool** — inserting a document in the BST menu simultaneously makes it available to the graph module, path module, analytics module, and every other CO.

---

## Technology Stack

| Component     | Technology         |
|---------------|--------------------|
| Language      | Java SE 21         |
| Build         | Manual `javac` / IntelliJ IDEA |
| IDE           | Eclipse / IntelliJ IDEA |
| Version Control | Git              |
| UI            | Console (Scanner)  |

---

## Project Structure

```
DocuTrack/
├── src/
│   ├── app/
│   │   └── Main.java                          ← Entry point, all menus & orchestration
│   ├── models/
│   │   └── Document.java                      ← Core data model
│   └── modules/
│       ├── indexing/
│       │   ├── bst/
│       │   │   ├── BSTNode.java               ← BST node (holds Document)
│       │   │   └── BST.java                   ← Insert, Search, Delete, Traversals
│       │   └── avl/
│       │       ├── AVLNode.java               ← AVL node (height-tracked)
│       │       └── AVLTree.java               ← Self-balancing Insert, Search, Traversals
│       ├── analytics/
│       │   ├── FenwickTree.java               ← Point update + Prefix sum queries
│       │   ├── SegmentTree.java               ← Range sum queries
│       │   ├── BTree.java                     ← Key-based insertion + Inorder display
│       │   └── BPlusTree.java                 ← Range search over document IDs
│       ├── graph/
│       │   ├── Graph.java                     ← Adjacency list + Resize + Remove vertex
│       │   ├── BFS.java                       ← Breadth-First Search traversal
│       │   ├── DFS.java                       ← Depth-First Search traversal
│       │   └── MST.java                       ← Minimum Spanning Tree (placeholder)
│       ├── path/
│       │   ├── Dijkstra.java                  ← Single-source shortest path
│       │   ├── BellmanFord.java               ← Shortest path (handles negative edges)
│       │   ├── FloydWarshall.java             ← All-pairs shortest path
│       │   └── TopologicalSort.java           ← DAG linear ordering
│       ├── sorting/
│       │   ├── MergeSort.java                 ← Sort documents by ID (stable)
│       │   ├── QuickSort.java                 ← Sort documents by Title (alphabetical)
│       │   ├── HeapSort.java                  ← Sort documents by Author (alphabetical)
│       │   └── RadixSort.java                 ← Sort documents by ID (non-comparative)
│       ├── optimization/
│       │   ├── ActivitySelection.java         ← Greedy activity scheduling
│       │   ├── FractionalKnapsack.java        ← Greedy fractional storage
│       │   ├── Knapsack01.java                ← 0/1 Knapsack (Dynamic Programming)
│       │   └── LIS.java                       ← Longest Increasing Subsequence (DP)
│       └── storage/                           ← (Reserved for future use)
└── bin/                                       ← Compiled .class files
```

---

## Data Model

### `models.Document`

The fundamental data entity shared across every module.

| Field         | Type     | Description                           |
|---------------|----------|---------------------------------------|
| `documentId`  | `int`    | Unique integer identifier             |
| `title`       | `String` | Human-readable title of the document  |
| `author`      | `String` | Author / creator name                 |

**`toString()` output:**
```
ID: 101, Title: Report Q1, Author: Alice
```

---

## Authentication & Roles

DocuTrack implements a simple hardcoded login system:

| Role    | Username | Password   | Capabilities                            |
|---------|----------|------------|-----------------------------------------|
| **Admin** | `admin`  | `admin123` | Full access — all 6 CO modules + document CRUD |
| **User**  | `user`   | `user123`  | Read-only — Search & View documents     |

Invalid credentials loop back to the login prompt.

---

## Module Breakdown (CO1 – CO6)

### CO1 — Document Indexing (BST & AVL)

**Purpose:** Efficiently index, search, and retrieve documents using tree-based data structures.

#### BST (Binary Search Tree) — `modules.indexing.bst`

| Class      | Description |
|------------|-------------|
| `BSTNode`  | Node wrapper holding a `Document` reference and `left`/`right` child pointers. |
| `BST`      | Full BST implementation with iterative insert, iterative search, 3-case delete (leaf / one child / two children using in-order predecessor), and recursive traversals (inorder, preorder, postorder). |

**BST Menu Options:**
1. **Insert Document** — Prompts for ID, Title, Author → inserts into BST *and* the shared pool.
2. **Search Document** — Prompts for ID → iterative lookup → prints document or "Not Found".
3. **Delete Document** — Prompts for ID → handles all 3 deletion cases → removes from shared pool.
4. **Display Inorder** — Prints all documents in ascending ID order (left → root → right).
5. **Minimum** — Traverses to the leftmost node → prints the document with the smallest ID.
6. **Maximum** — Traverses to the rightmost node → prints the document with the largest ID.

**Key Algorithms:**
- **Insert:** O(h) iterative — walks down the tree comparing IDs, attaches at the leaf position.
- **Search:** O(h) iterative — standard BST search.
- **Delete:** O(h) — finds the node, then applies: leaf removal, single-child promotion, or in-order predecessor replacement.

---

#### AVL Tree — `modules.indexing.avl`

| Class      | Description |
|------------|-------------|
| `AVLNode`  | Node with `Document`, `height` field, `left`/`right` children. |
| `AVLTree`  | Self-balancing BST using rotations. Supports recursive insert, recursive search, and traversals. |

**AVL Menu Options:**
1. **Insert Document** — Inserts with automatic rebalancing (LL, RR, LR, RL rotations).
2. **Search Document** — Recursive search by ID.
3. **Display Inorder** — Sorted traversal.

**Rotation Cases:**
- **LL (Left-Left):** Right rotate.
- **RR (Right-Right):** Left rotate.
- **LR (Left-Right):** Left rotate child, then right rotate.
- **RL (Right-Left):** Right rotate child, then left rotate.

**Why AVL over BST?** AVL guarantees O(log n) operations even in worst-case ordered insertions, whereas a plain BST can degrade to O(n).

---

### CO2 — Analytics (Fenwick, Segment, B-Tree, B+ Tree)

**Purpose:** Perform aggregate queries and range-based analytics on document data.

#### Fenwick Tree (Binary Indexed Tree) — `modules.analytics.FenwickTree`

| Operation      | Description                                                |
|----------------|------------------------------------------------------------|
| `update(i, v)` | Adds value `v` to position `i` (1-indexed).               |
| `sum(i)`       | Returns prefix sum from index 1 to `i`.                    |
| `rangeSum(l,r)` | Returns sum over range `[l, r]` using `sum(r) - sum(l-1)`. |

**Use Case:** Track cumulative document access counts. Each document is mapped to an index; updating the access count at that position and querying prefix sums gives cumulative access statistics.

**Menu Flow:**
1. Enter document number (1-indexed).
2. Enter access count to add.
3. Displays total accesses up to that document.

---

#### Segment Tree — `modules.analytics.SegmentTree`

| Operation                  | Description                                     |
|----------------------------|-------------------------------------------------|
| `build(arr, node, s, e)`   | Recursively builds the tree from document IDs.  |
| `query(node, s, e, l, r)`  | Returns range sum of document IDs in `[l, r]`.  |

**Use Case:** Answer range sum queries over document IDs. For example, "What is the total sum of document IDs from index 2 to index 5?"

**Menu Flow:**
1. Enter left index and right index.
2. Displays the sum of document IDs in that range.

---

#### B-Tree — `modules.analytics.BTree`

Simplified BST-like tree that stores document IDs as integer keys.

| Operation         | Description                              |
|-------------------|------------------------------------------|
| `insert(key)`     | Inserts a document ID into the tree.     |
| `display()`       | Prints all keys in sorted (inorder) order. |

**Use Case:** Demonstrates multi-level indexing for document IDs.

---

#### B+ Tree — `modules.analytics.BPlusTree`

Simplified B+ Tree using an `ArrayList` to store document IDs.

| Operation              | Description                                    |
|------------------------|------------------------------------------------|
| `insert(key)`          | Adds a document ID.                            |
| `rangeSearch(s, e)`    | Prints all document IDs in range `[s, e]`.     |

**Use Case:** Range queries — "Find all document IDs between 100 and 200."

---

### CO3 — Document Network (Graph, BFS, DFS, MST)

**Purpose:** Model relationships between documents as a graph and perform network analysis.

#### Graph — `modules.graph.Graph`

An **undirected adjacency-list graph** where each vertex represents a document.

| Operation                    | Description                                              |
|------------------------------|----------------------------------------------------------|
| `addEdge(src, dst)`          | Adds an undirected edge between two document vertices.   |
| `resize(newVertices)`        | Grows/shrinks the graph when documents are added/removed.|
| `removeVertex(v)`            | Removes a vertex and re-indexes all edges.               |
| `display()`                  | Prints adjacency list with vertex numbers.               |
| `displayDocuments(docs)`     | Prints adjacency list with document titles.              |

**CO3 Menu Options:**
1. **View Document Numbers** — Lists all documents with their vertex indices.
2. **Add Relationship** — Creates an undirected edge between two document vertices.
3. **Display Graph** — Shows the full adjacency list with document titles.
4. **BFS Traversal** — Breadth-first traversal from a chosen start vertex.
5. **DFS Traversal** — Depth-first traversal from a chosen start vertex.
6. **MST** — Displays Minimum Spanning Tree (currently a placeholder demonstration).

#### BFS — `modules.graph.BFS`

Standard level-order traversal using a `Queue<Integer>`. Visits all reachable vertices from a source, printing them in BFS order.

#### DFS — `modules.graph.DFS`

Recursive depth-first traversal. Takes a `boolean[] visited` array and explores as deep as possible before backtracking.

#### MST — `modules.graph.MST`

Currently a placeholder that prints a demonstration message. The infrastructure (graph adjacency list) is in place for a full Prim's/Kruskal's implementation.

---

### CO4 — Path Optimization (Dijkstra, Bellman-Ford, Floyd-Warshall, Topological Sort)

**Purpose:** Find optimal routes between documents using weighted cost matrices.

The path module operates on a **2D cost matrix** (`routeCosts[][]`) where `routeCosts[i][j]` is the cost to go from document `i` to document `j`. Costs default to `INF` (999999) and are set to `0` on the diagonal.

#### Dijkstra — `modules.path.Dijkstra`

| Feature            | Detail                                                    |
|--------------------|-----------------------------------------------------------|
| **Algorithm**      | Greedy single-source shortest path                        |
| **Input**          | Cost matrix, source vertex, destination vertex            |
| **Output**         | Minimum cost + optimized path (with document names)       |
| **Complexity**     | O(V²) with adjacency matrix                              |

Tracks `parent[]` array to reconstruct the full path.

---

#### Bellman-Ford — `modules.path.BellmanFord`

| Feature            | Detail                                                    |
|--------------------|-----------------------------------------------------------|
| **Algorithm**      | Dynamic programming single-source shortest path           |
| **Input**          | Cost matrix, source vertex                                |
| **Output**         | Distances from source to all other documents              |
| **Complexity**     | O(V³) with adjacency matrix                              |

Can handle negative edge weights (unlike Dijkstra).

---

#### Floyd-Warshall — `modules.path.FloydWarshall`

| Feature            | Detail                                                    |
|--------------------|-----------------------------------------------------------|
| **Algorithm**      | All-pairs shortest path using DP                          |
| **Input**          | Cost matrix                                               |
| **Output**         | Complete distance matrix between all document pairs       |
| **Complexity**     | O(V³)                                                     |

Produces a full matrix showing shortest distances between every pair of documents.

---

#### Topological Sort — `modules.path.TopologicalSort`

| Feature            | Detail                                                    |
|--------------------|-----------------------------------------------------------|
| **Algorithm**      | DFS-based topological ordering                            |
| **Input**          | Cost matrix (treated as a directed graph)                 |
| **Output**         | Linear ordering of documents respecting dependencies      |
| **Complexity**     | O(V²) with adjacency matrix                              |

Useful for determining document processing order when dependencies exist.

---

### CO5 — Sorting & Ranking (Merge, Quick, Heap, Radix)

**Purpose:** Sort the document collection by different attributes using different algorithms.

All sorting algorithms operate on a **copy** of the documents list (non-destructive).

| Algorithm   | Sorts By    | Stability | Complexity       | Class             |
|-------------|-------------|-----------|------------------|-------------------|
| Merge Sort  | Document ID | ✅ Stable  | O(n log n)       | `MergeSort`       |
| Quick Sort  | Title       | ❌ Unstable | O(n log n) avg   | `QuickSort`       |
| Heap Sort   | Author      | ❌ Unstable | O(n log n)       | `HeapSort`        |
| Radix Sort  | Document ID | ✅ Stable  | O(d × n)         | `RadixSort`       |

**Menu Options:**
1. **Merge Sort by Document ID** — Divide-and-conquer, compares `documentId`.
2. **Quick Sort by Title** — Partitions using `title.toLowerCase().compareTo()`.
3. **Heap Sort by Author** — Builds max-heap using `author.compareToIgnoreCase()`.
4. **Radix Sort by Document ID** — LSD radix sort using counting sort per digit.

---

### CO6 — Storage Optimization (Activity Selection, Knapsack, LIS)

**Purpose:** Optimize document scheduling and storage allocation using greedy and dynamic programming techniques.

#### Activity Selection (Greedy) — `modules.optimization.ActivitySelection`

| Input                        | Description                                            |
|------------------------------|--------------------------------------------------------|
| `documents[]`                | Array of documents                                     |
| `start[]`, `finish[]`        | Start and finish times per document (entered by user)  |

**Algorithm:** Greedy selection — selects non-overlapping activities sorted by finish time. Useful for scheduling document review sessions without conflicts.

---

#### Fractional Knapsack (Greedy) — `modules.optimization.FractionalKnapsack`

| Input                        | Description                                |
|------------------------------|--------------------------------------------|
| `documents[]`                | Array of documents                         |
| `value[]`                    | Priority value for each document           |
| `weight[]`                   | Storage size for each document             |
| `capacity`                   | Total available storage capacity           |

**Algorithm:** Picks items by value-to-weight ratio. Can take fractional portions of a document's allocation.

---

#### 0/1 Knapsack (Dynamic Programming) — `modules.optimization.Knapsack01`

Same inputs as Fractional Knapsack, but uses a 2D DP table to determine the optimal subset of documents to store (no fractions allowed).

**DP Table:** `dp[i][w]` = max value achievable using the first `i` documents with capacity `w`.

**Backtracking:** Traces through the DP table to identify which specific documents were selected.

---

#### LIS — Longest Increasing Subsequence — `modules.optimization.LIS`

| Input                        | Description                                |
|------------------------------|--------------------------------------------|
| `values[]` (document IDs)    | Array of document IDs in insertion order   |

**Algorithm:** Classic O(n²) DP. Finds the longest strictly increasing subsequence of document IDs. Tracks `parent[]` to reconstruct and display the actual subsequence.

---

## Execution Flow

```
┌────────────────────────────────────────┐
│              LOGIN SCREEN              │
│  ┌──────────┐      ┌───────────┐      │
│  │  admin /  │      │  user /   │      │
│  │ admin123  │      │ user123   │      │
│  └────┬─────┘      └─────┬─────┘      │
│       │                  │             │
│       ▼                  ▼             │
│  ┌─────────┐      ┌──────────┐        │
│  │  ADMIN  │      │   USER   │        │
│  │  MENU   │      │   MENU   │        │
│  └────┬────┘      └────┬─────┘        │
│       │                │              │
│  ┌────┴────────┐  ┌────┴──────┐       │
│  │ CO1–CO6     │  │ Search /  │       │
│  │ Sub-menus   │  │ View Docs │       │
│  └─────────────┘  └───────────┘       │
└────────────────────────────────────────┘
```

### Admin Menu Flow

```
ADMIN MENU
├── 1. CO1 — Document Indexing
│   ├── BST Operations
│   │   ├── Insert Document ──► addSharedDocument() ──► rebuildIndexes() + graph.resize() + resizeRouteCosts()
│   │   ├── Search Document
│   │   ├── Delete Document ──► deleteSharedDocument() ──► rebuildIndexes() + graph.removeVertex() + removeRouteCost()
│   │   ├── Display Inorder
│   │   ├── Minimum
│   │   └── Maximum
│   └── AVL Operations
│       ├── Insert Document ──► addSharedDocument() (same shared pool)
│       ├── Search Document
│       └── Display Inorder
│
├── 2. CO2 — Analytics
│   ├── Fenwick Tree (update access count + prefix sum query)
│   ├── Segment Tree (range sum query on document IDs)
│   ├── B-Tree (display sorted keys)
│   └── B+ Tree (range search by document ID)
│
├── 3. CO3 — Document Network
│   ├── View Document Numbers
│   ├── Add Relationship (undirected edge)
│   ├── Display Graph (adjacency list with titles)
│   ├── BFS Traversal
│   ├── DFS Traversal
│   └── MST
│
├── 4. CO4 — Path Optimization
│   ├── View Document Numbers
│   ├── Add Route Cost (weighted directed edge)
│   ├── Display Route Costs (cost matrix)
│   ├── Dijkstra Shortest Path (source → destination)
│   ├── Bellman-Ford (source → all)
│   ├── Floyd-Warshall (all → all)
│   └── Topological Sort
│
├── 5. CO5 — Sorting & Ranking
│   ├── Merge Sort by Document ID
│   ├── Quick Sort by Title
│   ├── Heap Sort by Author
│   └── Radix Sort by Document ID
│
├── 6. CO6 — Storage Optimization
│   ├── Activity Selection (input start/finish times)
│   ├── Fractional Knapsack (input values, weights, capacity)
│   ├── 0/1 Knapsack (input values, weights, capacity)
│   └── LIS on Document IDs
│
└── 7. Logout ──► Back to Login Screen
```

### User Menu Flow

```
USER MENU
├── 1. Search Document ──► BST search by ID
├── 2. View Documents ──► Lists all documents
└── 3. Logout ──► Back to Login Screen
```

---

## Shared Data Architecture

A critical design decision: **all modules share the same data.** The following static fields in `Main.java` serve as the single source of truth:

| Field                  | Type                    | Purpose                                      |
|------------------------|-------------------------|----------------------------------------------|
| `documents`            | `ArrayList<Document>`   | Master list of all documents                 |
| `bst`                  | `BST`                   | BST index (rebuilt on every insert/delete)    |
| `avl`                  | `AVLTree`               | AVL index (rebuilt on every insert/delete)    |
| `graph`                | `Graph`                 | Document relationship graph                   |
| `routeCosts`           | `int[][]`               | Weighted cost matrix for path algorithms      |

### Synchronization Flow

When a document is **added** (`addSharedDocument()`):
1. Duplicate ID check → if unique, add to `documents` list.
2. `rebuildIndexes()` → Reconstructs *both* BST and AVL from scratch.
3. `graph.resize()` → Expands the adjacency list to accommodate the new vertex.
4. `resizeRouteCosts()` → Expands the cost matrix (new row/column initialized to `INF`).

When a document is **deleted** (`deleteSharedDocument()`):
1. Find document in `documents` list → get its index.
2. Remove from `documents` list.
3. `rebuildIndexes()` → Reconstructs BST and AVL.
4. `graph.removeVertex()` → Removes the vertex and re-indexes all edges.
5. `removeRouteCost()` → Removes the corresponding row/column from the cost matrix.

---

## How to Compile & Run

### Prerequisites

- **Java Development Kit (JDK) 21** or higher installed.
- `javac` and `java` available on your system PATH.

### Command-Line Compilation

```bash
cd "E:\Apps\Java Projects\DocuTrack"

# Compile all Java files into the bin directory
javac -d bin src/app/Main.java src/models/Document.java src/modules/indexing/bst/BSTNode.java src/modules/indexing/bst/BST.java src/modules/indexing/avl/AVLNode.java src/modules/indexing/avl/AVLTree.java src/modules/analytics/FenwickTree.java src/modules/analytics/SegmentTree.java src/modules/analytics/BTree.java src/modules/analytics/BPlusTree.java src/modules/graph/Graph.java src/modules/graph/BFS.java src/modules/graph/DFS.java src/modules/graph/MST.java src/modules/path/Dijkstra.java src/modules/path/BellmanFord.java src/modules/path/FloydWarshall.java src/modules/path/TopologicalSort.java src/modules/sorting/MergeSort.java src/modules/sorting/QuickSort.java src/modules/sorting/HeapSort.java src/modules/sorting/RadixSort.java src/modules/optimization/ActivitySelection.java src/modules/optimization/FractionalKnapsack.java src/modules/optimization/Knapsack01.java src/modules/optimization/LIS.java

# Run the application
java -cp bin app.Main
```

### IntelliJ IDEA

1. Open the project folder `E:\Apps\Java Projects` in IntelliJ.
2. Ensure `DocuTrack/src` is marked as a **Source Root**.
3. Right-click `Main.java` → **Run 'Main.main()'**.

### Sample Session

```
=================================
      DOCUTRACK LOGIN
=================================
Username: admin
Password: admin123

Welcome Admin

=================================
       DOCUTRACK SYSTEM
=================================
1. CO1 - Document Indexing
2. CO2 - Analytics
3. CO3 - Document Network
4. CO4 - Path Optimization
5. CO5 - Sorting & Ranking
6. CO6 - Storage Optimization
7. Logout
Enter Choice: 1

========== CO1 ==========
1. BST Operations
2. AVL Operations
3. Back
```

---

## Class Diagram

```
┌──────────────────────────────────────────────────────────────┐
│                        app.Main                              │
│──────────────────────────────────────────────────────────────│
│ - sc: Scanner                                                │
│ - documents: ArrayList<Document>                             │
│ - bst: BST                                                   │
│ - avl: AVLTree                                               │
│ - graph: Graph                                               │
│ - routeCosts: int[][]                                        │
│──────────────────────────────────────────────────────────────│
│ + main(args)                                                 │
│ - adminMenu() / userMenu()                                   │
│ - co1Menu() / co2Menu() / co3Menu()                          │
│ - co4Menu() / co5Menu() / co6Menu()                          │
│ - bstMenu() / avlMenu()                                     │
│ - addSharedDocument(doc) / deleteSharedDocument(id)          │
│ - rebuildIndexes() / resizeRouteCosts(size)                  │
│ - buildFenwickTree() / buildBTree() / buildBPlusTree()       │
│ - getDocumentIds() / getDocumentTitles()                     │
│ - displayDocuments() / displayRouteCosts()                   │
│ - runActivitySelection() / runFractionalKnapsack()           │
│ - runKnapsack01() / readValues() / readWeights()             │
└──────────────────────────┬───────────────────────────────────┘
                           │ uses
           ┌───────────────┼───────────────────────────┐
           ▼               ▼                           ▼
┌─────────────────┐ ┌──────────────┐      ┌─────────────────────┐
│ models.Document │ │  BST / AVL   │      │   Graph / BFS / DFS │
│─────────────────│ │──────────────│      │─────────────────────│
│ + documentId    │ │ + root       │      │ + vertices          │
│ + title         │ │ + insert()   │      │ + adj[]             │
│ + author        │ │ + search()   │      │ + addEdge()         │
│ + toString()    │ │ + delete()   │      │ + resize()          │
└─────────────────┘ │ + inorder()  │      │ + removeVertex()    │
                    └──────────────┘      └─────────────────────┘
```

*(See the dedicated class diagram image for the full UML representation.)*

---

## Complexity Analysis

| Algorithm             | Time Complexity (Average)  | Time Complexity (Worst) | Space  |
|-----------------------|---------------------------|-------------------------|--------|
| BST Insert/Search     | O(log n)                  | O(n)                    | O(n)   |
| AVL Insert/Search     | O(log n)                  | O(log n)                | O(n)   |
| Fenwick Update/Query  | O(log n)                  | O(log n)                | O(n)   |
| Segment Tree Query    | O(log n)                  | O(log n)                | O(4n)  |
| BFS / DFS             | O(V + E)                  | O(V + E)                | O(V)   |
| Dijkstra (matrix)     | O(V²)                     | O(V²)                  | O(V)   |
| Bellman-Ford (matrix) | O(V³)                     | O(V³)                  | O(V)   |
| Floyd-Warshall        | O(V³)                     | O(V³)                  | O(V²)  |
| Topological Sort      | O(V + E)                  | O(V + E)                | O(V)   |
| Merge Sort            | O(n log n)                | O(n log n)              | O(n)   |
| Quick Sort            | O(n log n)                | O(n²)                  | O(log n)|
| Heap Sort             | O(n log n)                | O(n log n)              | O(1)   |
| Radix Sort            | O(d × n)                  | O(d × n)               | O(n)   |
| Activity Selection    | O(n²)                     | O(n²)                  | O(n)   |
| Fractional Knapsack   | O(n²)                     | O(n²)                  | O(n)   |
| 0/1 Knapsack          | O(n × W)                  | O(n × W)               | O(n×W) |
| LIS                   | O(n²)                     | O(n²)                  | O(n)   |

---

> **DocuTrack** — Where Data Structures Meet Document Management. 📄🌳📊
