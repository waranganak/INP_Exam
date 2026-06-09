# INP Exam

A high-performance, multithreaded desktop chat application built as part of the **ITS 1141 ? Introduction to Network Programming** practical curriculum. This project demonstrates socket-level TCP/IP networking, dynamic thread allocation, non-blocking UI handling in JavaFX, and a customized byte-streaming packet delivery framework.

---

## ? Key Features

* **Multi-Client Server Engine:** Supports multiple parallel client instances connecting concurrently to a single background routing server.
* **Custom Byte-Streaming Protocol:** Implements an integer-header command parsing pipeline (`TYPE_TEXT = 0`, `TYPE_FILE = 1`) over data streams to prevent packet collisions.
* **Dynamic Sender Identity Tracking:** Pre-appends user metadata to text transmissions and broadcasts so clients know precisely who sent each message or asset.
* **Thread-Safe UI Operations:** Leverages `Platform.runLater()` to safely bridge asynchronous background socket reader loops with the JavaFX Application Thread.
* **Native OS Integration:** Features local file system downloads and native Windows emoji picker rendering via UTF-8 string data compliance.

---

## ?? Technology Stack

* **Language:** Java (JDK 17)
* **GUI Framework:** 
* **Build System:** Apache Maven


---


