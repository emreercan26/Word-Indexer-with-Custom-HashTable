# Word Indexer with Custom Hash Table

This high-performance Java application indexes unique words from text files, tracking their frequencies and exact positions. The project is built entirely from scratch, implementing a **manual Hash Table** to ensure maximum control over memory and algorithmic efficiency.

---

### 🚀 Key Technical Features

* **Custom Hash Engine:** Utilizes a **Polynomial Rolling Hash** algorithm with a prime multiplier (31) to ensure uniform word distribution and minimize collisions.
* **Collision Management:** Implements **Chaining** via a custom-built singly linked list (`WordList`) to handle multiple words mapping to the same bucket.
* **Dynamic Rehashing:** Automatically doubles the table size and re-indexes all elements when the **Load Factor** exceeds 0.75, maintaining O(1) average time complexity.
* **Zero-Library Logic:** Features manual string comparison and case-insensitivity logic (ASCII-based) without relying on Java's built-in `String.equals()` or `toLowerCase()` methods.

---

### 🛠️ Implementation Details

* **Word Tracking:** Each word is stored with its total count and an `ArrayList` of its 1-based token positions in the text.
* **Efficient Search:** The `find` method performs linear searches within buckets, utilizing a custom character-by-character comparison.
* **Optimized Distribution:** The system ensures that increasing the initial table size significantly reduces collisions.
* **Ordered Output:** Results are sorted primarily by frequency (descending) and secondarily by lexicographical order before being exported to a CSV file.

---

### 📊 Performance Analysis

The project includes a collision tracking mechanism to validate the hash function's efficiency. Based on test results:

| Initial Table Size | Total Collision Count |
| :--- | :--- |
| 500 | 7 |
| 1000 | 3 |
| 10,000 | 0 |
