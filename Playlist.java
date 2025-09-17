import java.util.Scanner;
import java.util.Objects;
import java.util.NoSuchElementException;

public class Playlist {
    private Song front;
    private Song rear;
    private final String name;
    private int size;

    public Playlist(String name) {
        this.name = Objects.requireNonNull(name, "Playlist name cannot be null");
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return front == null;
    }

    // Add song to the end of playlist
    public void enqueue(Song song) {
        if (song == null) {
            throw new IllegalArgumentException("Cannot enqueue null song");
        }

        Song newNode = new Song(song.getSongName(), song.getSinger());
        if (isEmpty()) {
            front = rear = newNode;
        } else {
            rear.setNext(newNode);
            newNode.setPrev(rear);
            rear = newNode;
        }
        size++;
    }

    // Remove and return the first song
    public Song dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Playlist is empty");
        }

        Song removedSong = front;
        front = front.getNext();

        if (front == null) {
            rear = null;
        } else {
            front.setPrev(null);
        }

        removedSong.setNext(null); // Prevent memory leak
        size--;
        return removedSong;
    }

    // Print all songs in playlist
    public void printPlaylist() {
        System.out.println("\nPlaylist: " + name);
        System.out.println("Total songs: " + size);
        System.out.println("------------------------");

        Song current = front;
        int position = 1;
        while (current != null) {
            System.out.println(position++ + ". " + current);
            current = current.getNext();
        }
    }

    // Start playing the playlist
    public void startPlaylist() {
        if (isEmpty()) {
            System.out.println("Playlist is empty!");
            return;
        }

        Song current = front;
        System.out.println("\nNow playing: " + current);

        try (Scanner scanner = new Scanner(System.in)) {
            int userInput = -1;
            while (userInput != 0) {
                System.out.println("\nCurrent song: " + current);
                System.out.println("1. Play next song");
                System.out.println("2. Remove current song");
                System.out.println("3. Print playlist");
                System.out.println("0. Exit playlist");
                System.out.print("Select option: ");

                try {
                    userInput = scanner.nextInt();
                    scanner.nextLine(); // Clear buffer

                    switch (userInput) {
                        case 1:
                            current = playNextSong(current);
                            break;
                        case 2:
                            current = removeCurrentSong(current);
                            break;
                        case 3:
                            printPlaylist();
                            break;
                        case 0:
                            System.out.println("Exiting playlist...");
                            break;
                        default:
                            System.out.println("Invalid option!");
                    }
                } catch (Exception e) {
                    System.out.println("Please enter a valid number!");
                    scanner.nextLine();
                }
            }
        }
    }

    private Song playNextSong(Song current) {
        Song nextSong = current.getNext();
        if (nextSong != null) {
            System.out.println("Now playing: " + nextSong);
            return nextSong;
        } else {
            System.out.println("Reached end of playlist!");
            return current;
        }
    }

    private Song removeCurrentSong(Song current) {
        if (current == front) {
            dequeue();
            Song newCurrent = front;
            if (newCurrent != null) {
                System.out.println("Now playing: " + newCurrent);
            } else {
                System.out.println("Playlist is now empty");
            }
            return newCurrent;
        } else {
            // Remove from middle/end
            Song prev = current.getPrev();
            Song next = current.getNext();

            prev.setNext(next);
            if (next != null) {
                next.setPrev(prev);
            } else {
                rear = prev; // Update rear if removing last element
            }

            size--;
            System.out.println("Removed: " + current);

            if (next != null) {
                System.out.println("Now playing: " + next);
                return next;
            } else if (prev != null) {
                System.out.println("Now playing: " + prev);
                return prev;
            } else {
                System.out.println("Playlist is now empty");
                return null;
            }
        }
    }
}