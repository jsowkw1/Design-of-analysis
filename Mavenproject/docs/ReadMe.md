# Assignment 2 – MinHeap & MaxHeap Analysis

## 📌 Overview
This repository contains the implementation and analysis of MinHeap (with decrease-key and merge operations)  
as well as the peer review analysis of the MaxHeap implementation (partner’s code).  
The project includes algorithmic implementation, unit testing, and performance benchmarking.

---

## 📂 Project Structure
- src/main/java/algorithms/MinHeap.java → MinHeap implementation
- src/main/java/metrics/PerformanceTracker.java → performance counters
- src/main/java/cli/BenchmarkRunner.java → CLI interface for benchmarks
- src/test/java/algorithms/MinHeapTest.java → JUnit 5 test suite
- docs/performance-plots/ → Benchmark plots and raw CSV data

---

## 📑 Reports

| Report Type       | File                       | Link                                                                                                                                             |
|-------------------|----------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------|
| 📝 My Report (MinHeap) | docs/Min-Heap-Report.pdf   | [![MinHeap Report](https://img.shields.io/badge/View_Report-blue?style=for-the-badge&logo=adobeacrobatreader)](docs/minheap-report-template.pdf) |
| 🔎 Peer Review (MaxHeap) | docs/Max-Heap_Report.pdf   | [![MaxHeap Report](https://img.shields.io/badge/View_Report-green?style=for-the-badge&logo=adobeacrobatreader)](docs/Lab2_MaxHeap_Report.pdf)    |
| ⚖️ Comparison (Min vs Max) | docs/Comparison_Report.pdf | [![Comparison Report](https://img.shields.io/badge/View_Report-orange?style=for-the-badge&logo=adobeacrobatreader)](docs/Comparison_Report.pdf)  |

---

## ⚙️ Build & Run
```bash
# Compile project
mvn clean install

# Run tests
mvn test

# Run benchmark CLI
mvn exec:java -Dexec.mainClass="cli.BenchmarkRunner"