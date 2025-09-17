import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Operations {
    private final Map<String, Album> albumMap;
    private int albumCounter;

    public Operations() {
        this.albumMap = new HashMap<>();
        this.albumCounter = 0;
    }

    // Getter methods
    public Map<String, Album> getAlbumMap() {
        return new HashMap<>(albumMap); // Return defensive copy
    }

    public int getAlbumCount() {
        return albumCounter;
    }

    public void loadAlbums(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                String[] parts = line.split(",");
                if (parts.length < 3) {
                    throw new IOException("Invalid album format in file");
                }

                String albumName = parts[0].trim();
                String singer = parts[1].trim();
                int releaseYear = Integer.parseInt(parts[2].trim());

                Album album = new Album(albumName, releaseYear, singer);

                while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                    String[] songParts = line.split(",");
                    if (songParts.length < 2) {
                        throw new IOException("Invalid song format in file");
                    }
                    album.addSong(songParts[0].trim(), songParts[1].trim());
                }

                albumMap.put(album.getAlbumName(), album);
                albumCounter++;
            }
        } catch (NumberFormatException e) {
            throw new IOException("Invalid year format in file", e);
        }
    }

    public void printAlbums() {
        if (albumMap.isEmpty()) {
            System.out.println("No albums loaded");
            return;
        }

        System.out.println("\nAvailable Albums (" + albumCounter + "):");
        System.out.println("=================================");

        for (Album album : albumMap.values()) {
            System.out.println("\nAlbum: " + album.getAlbumName());
            System.out.println("Artist: " + album.getSinger());
            System.out.println("Year: " + album.getReleaseYear());
            System.out.println("Songs:");

            Song current = album.getHead();
            int songNumber = 1;
            while (current != null) {
                System.out.println(songNumber++ + ". " + current.getSongName() + " (" + current.getSinger() + ")");
                current = current.getNext();
            }
        }
    }

    public Song searchForSong(String albumName, String songName) {
        Album album = albumMap.get(albumName);
        if (album == null) {
            System.out.println("Album not found!");
            return null;
        }

        Song current = album.getHead();
        while (current != null) {
            if (current.getSongName().equalsIgnoreCase(songName)) {
                return current;
            }
            current = current.next;
        }

        System.out.println("Song not found in album!");
        return null;
    }

    public void playAlbum(String albumName) {
        Album album = albumMap.get(albumName);
        if (album == null) {
            System.out.println("Album not found!");
            return;
        }


        if (album.getHead() == null) {
            System.out.println("Album '" + albumName + "' is empty!");
            return;
        }
        Song current=album.getHead();
        System.out.println("\nNow playing album: "+album.getAlbumName());
        System.out.println("Now playing: "+ current);

        try (Scanner scanner = new Scanner(System.in)) {
            int userInput = -1;
            while (userInput != 0) {
                System.out.println("\n1. Next song");
                System.out.println("2. Previous song");
                System.out.println("3. Album info");
                System.out.println("0. Exit album");
                System.out.print("Select option: ");

                try {
                    userInput = scanner.nextInt();
                    scanner.nextLine(); // Clear buffer

                    switch (userInput) {
                        case 1:
                            if (current.getNext() != null) {
                                current = current.getNext();
                                System.out.println("Now playing: " + current);
                            } else {
                                System.out.println("End of album reached!");
                            }
                            break;
                        case 2:
                            if (current.getPrev() != null) {
                                current = current.getPrev();
                                System.out.println("Now playing: " + current);
                            } else {
                                System.out.println("Beginning of album reached!");
                            }
                            break;
                        case 3:
                            printAlbumInfo(album);
                            break;
                        case 0:
                            System.out.println("Exiting album...");
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

    private void printAlbumInfo(Album album) {
        System.out.println("\nAlbum: " + album.getAlbumName());
        System.out.println("Artist: " + album.getSinger());
        System.out.println("Year: " + album.getReleaseYear());
        System.out.println("Total songs: " + countSongs(album));
    }

    private int countSongs(Album album) {
        int count = 0;
        Song current = album.getHead();
        while (current != null) {
            count++;
            current = current.getNext();
        }
        return count;
    }

    private static void addToPlaylistSafely(Playlist playlist, Operations operations,
                                            String albumName, String songName) {
        try {
            Song song = operations.searchForSong(albumName, songName);
            if (song != null) {
                playlist.enqueue(song);
                System.out.println("Added to playlist: " + songName + " from " + albumName);
            } else {
                System.out.println("Could not find: " + songName + " in " + albumName);
            }
        } catch (Exception e) {
            System.err.println("Error adding song to playlist: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Operations operations = new Operations();
        String projectRoot = System.getProperty("user.dir");
        String filePath = projectRoot + java.io.File.separator + "albumList.txt";

        try {
            System.out.println("Trying to load from: " + filePath);
            operations.loadAlbums(filePath);
            System.out.println("Successfully loaded " + operations.getAlbumCount() + " albums");
        } catch (IOException e) {
            System.err.println("Error loading albums: " + e.getMessage());
            System.err.println("Current working directory: " + projectRoot);
            return;
        }

        operations.printAlbums();

        // Create sample playlist
        Playlist playlist = new Playlist("Energy Boost");
        try {
            addToPlaylistSafely(playlist,operations,"Fine Line","Golden");
            addToPlaylistSafely(playlist,operations,"Midnights","Maroon");
            addToPlaylistSafely(playlist, operations, "Folklore", "Exile");
            addToPlaylistSafely(playlist, operations, "Lover", "The Man");
            addToPlaylistSafely(playlist, operations, "Reputation", "Delicate");
        } catch (Exception e) {
            System.err.println("Error creating playlist: " + e.getMessage());
        }

        try (Scanner scanner = new Scanner(System.in)) {
            int userInput = -1;
            while (userInput != 0) {
                System.out.println("\nMain Menu:");
                System.out.println("\t1-Play an album");
                System.out.println("\t2-Play a playlist");
                System.out.println("\t3-List all albums");
                System.out.println("\t0-Quit");
                System.out.print("Select an option: ");

                try {
                    userInput = scanner.nextInt();
                    scanner.nextLine(); // Clear buffer

                    switch (userInput) {
                        case 1:
                            System.out.print("Enter album name: ");
                            String albumName = scanner.nextLine();
                            operations.playAlbum(albumName);
                            break;
                        case 2:
                            playlist.startPlaylist();
                            break;
                        case 3:
                            operations.printAlbums();
                            break;
                        case 0:
                            System.out.println("Exiting program...");
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

}