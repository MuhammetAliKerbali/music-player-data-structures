# Music Player Data Structures

A music player simulator implemented in Java core. The project showcases the application of key data structures: Albums are implemented as Doubly Linked Lists, and Playlists are managed using a Queue (FIFO) structure. It features interactive console navigation.

## Features

*   **Album Management:** Load albums and songs from a text file.
*   **Interactive Playback:** Navigate through albums (next/previous song).
*   **Playlist Management:** Create and manage a queue-based playlist.
*   **Console UI:** User-friendly text-based interface.

## Data Structures Used

*   **Doubly Linked List:** Used within the `Album` class to manage the list of songs.
*   **Queue:** Used in the `Playlist` class to manage the song queue (First-In-First-Out).
*   **Stack:** Implemented in the `Stack` class (Last-In-First-Out).

## How to Run

1.  Ensure you have Java JDK installed on your system.
2.  Clone this repository:
    ```bash
    git clone https://github.com/your-username/music-player-data-structures.git
    ```
3.  Navigate to the project directory:
    ```bash
    cd music-player-data-structures
    ```
4.  Compile the Java files:
    ```bash
    javac *.java
    ```
5.  Run the main class:
    ```bash
    java Operations
    ```
6.  Follow the on-screen instructions in the console.

## Project Structure

*   `Operations.java` - The main class and entry point of the application.
*   `Album.java` - Represents a music album (uses Doubly Linked List).
*   `Song.java` - Represents a single song node.
*   `Playlist.java` - Manages a queue of songs.
*   `Stack.java` - A stack implementation for songs.
*   `albumList.txt` - Sample data file.

## License

This project is licensed under the MIT License - see the LICENSE file for details.