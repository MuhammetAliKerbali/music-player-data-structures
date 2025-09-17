import java.util.Objects;
import java.util.Scanner;

public class Album {

    public Song head;
    private final String albumName;
    private final int releaseYear;
    private final String singer;


    public Album(String albumName, int releaseYear, String singer) {
        this.albumName = Objects.requireNonNull(albumName, "Album name cannot be null");
        this.singer = Objects.requireNonNull(singer, "Singer cannot be null");
        this.releaseYear = releaseYear;
        this.head = null;
    }

    // Getter methods
    public String getAlbumName() {
        return albumName;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getSinger() {
        return singer;
    }

    public Song getHead() {
        return head;
    }

    // Add a song to the album
    public void addSong(String songName, String singer){
        Song newNode = new Song(songName, singer);
        if (head == null) {
            head = newNode;
        } else {
            Song last = getLastSong();
            last.next = newNode;
            newNode.prev = last;
        }
    }
    private Song getLastSong() {
        if (head == null) return null;

        Song current = head;
        while (current.next != null) {
            current = current.next;
        }
        return current;
    }

    // Print all songs in the album
    public void printSongs(){
        System.out.println("Album: "+albumName+" ("+releaseYear+")");
        System.out.println("Singer: "+ singer);
        Song current =head;

        while(current!=null){
            System.out.println("- "+ current.songName);
            current=current.next;
        }
    }

    // Start playing album with navigation

    public void startAlbum(){
        if (head == null) {
            System.out.println("Album is empty!");
            return;
        }

        Song current=this.head;
        System.out.println("First song is playing: "+ current.songName);

        Scanner scanner=new Scanner(System.in);
        int userInput = -1;

        while (userInput != 0) {
            System.out.println("\nSelect the operation:");
            System.out.println("\t1-Play next song.");
            System.out.println("\t2-Play previous song.");
            System.out.println("\t0-Quit.");
            System.out.print("Select an option:");
            try {
                userInput = scanner.nextInt();
                scanner.nextLine(); // Clear buffer

                switch (userInput) {
                    case 1:
                        if (current.next != null) {
                            current = current.next;
                            System.out.println("Next song is playing: " + current.songName);
                        } else {
                            System.out.println("End of album reached!");
                        }
                        break;
                    case 2:
                        if (current.prev != null) {
                            current = current.prev;
                            System.out.println("Previous song is playing: " + current.songName);
                        } else {
                            System.out.println("Beginning of album reached!");
                        }
                        break;
                    case 0:
                        System.out.println("Exiting album playback.");
                        break;
                    default:
                        System.out.println("Invalid option!");
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid number!");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
}
