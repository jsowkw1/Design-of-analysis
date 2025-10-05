# 🧠 Algorithm Project — Pair 4: Heap Data Structures

**Student A:** MinHeap Implementation (decrease-key, merge)  
**Student B:** MaxHeap Implementation (increase-key, extract-max)

---

## 📄 Peer Analysis Report — MinHeap (by Student A)
**Reviewer:** Student B  
**Analyzed file:** `MinHeap.java`  
**Supporting class:** `PerformanceTracker.java`

---

### 1️⃣ Algorithm Overview
The implementation is a **Min-Heap** data structure that maintains a complete binary tree, ensuring that each parent node is smaller than its children.  
The smallest element is always stored at the root (`heap[0]`).

**Implemented operations:**
- `insert(int key)`
- `extractMin()`
- `decreaseKey(int i, int newVal)`
- `merge(MinHeap other)`

Helper methods: `heapifyUp`, `heapifyDown`, `swap`, `printHeap()`.

---

### 2️⃣ Complexity Analysis

| Operation | Best | Average | Worst | Description |
|------------|------|----------|--------|--------------|
| `insert()` | Θ(1) | O(log n) | O(log n) | Percolate up if needed. |
| `extractMin()` | Θ(1) | O(log n) | O(log n) | Percolate down. |
| `decreaseKey()` | Θ(1) | O(log n) | O(log n) | Bubble up the decreased key. |
| `merge()` | O(n + m) | O(n + m) | O(n + m) | Sequential insertion. |

**Space Complexity:** O(n) total, with optional O(log n) recursion depth.

---

### 3️⃣ Code Quality Evaluation

✅ **Strengths:**
- Well-documented and modular code.  
- Proper error handling (`IllegalArgumentException`, `IllegalStateException`).  
- Integration with `PerformanceTracker` for metrics collection.  
- Readable structure and naming conventions.

⚠️ **Weaknesses / Suggestions:**
1. **Inefficient merge()** — sequential insertions instead of a single heapify.  
2. **Memory tracking** — `addMemoryAllocation()` called too often.  
3. **Recursive heapifyDown()** — can be replaced with iterative.  
4. **No array access tracking** — tracker method `addArrayAccess()` unused.  
5. **Missing tests for edge cases.**

---

### 4️⃣ Empirical Results (Expected Behavior)

| n | Avg Time (ms) | Comparisons | Swaps | Allocations |
|---|----------------|-------------|--------|--------------|
| 100 | ~0 | ~350 | ~180 | ~100 |
| 1,000 | ~1 | ~4,000 | ~2,100 | ~1,000 |
| 10,000 | ~3 | ~47,000 | ~24,500 | ~10,000 |

- Time grows logarithmically (O(n log n)).  
- Swaps and comparisons scale predictably.  
- Memory usage grows linearly with n.

---

### 5️⃣ Comparison with MaxHeap

| Feature | MinHeap (Student A) | MaxHeap (Student B) |
|----------|---------------------|---------------------|
| Property | Parent < Child | Parent > Child |
| Key Update | decreaseKey() | increaseKey() |
| Merge | Implemented | Not required |
| Main Operation | extractMin() | extractMax() |

Both have the same structure and asymptotic complexity.  
MinHeap includes an extra `merge()` feature with room for optimization.

---

### 6️⃣ Conclusion

The `MinHeap` code is **functionally correct, clear, and well-instrumented**.  
It fully meets assignment requirements and demonstrates solid understanding of heap operations.

**Ratings:**
- Correctness: ⭐ 10/10  
- Readability: ⭐ 9/10  
- Performance: ⚙️ 8/10  
- Testing: 🧪 7.5/10  
- Overall: **8.6 / 10**

---

📘 *Prepared by Student B — Peer Review for Student A’s MinHeap Implementation*  
📅 *Algorithm Analysis Report — Pair 4 (Heap Structures)*  
