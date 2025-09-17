public class Song {
    final String songName;
    final String singer;
    Song next;
    Song prev;

    public Song(String songName, String singer) {
        if (songName == null || singer == null) {
            throw new IllegalArgumentException("Song name and singer cannot be null");
        }
        this.songName = songName;
        this.singer = singer;
        this.next = null;
        this.prev = null;
    }

    // Getter methods
    public String getSongName() {
        return songName;
    }

    public String getSinger() {
        return singer;
    }

    public Song getNext() {
        return next;
    }

    public Song getPrev() {
        return prev;
    }

    protected void setNext(Song next) {
        this.next = next;
    }

    protected void setPrev(Song prev) {
        this.prev = prev;
    }

    @Override
    public String toString() {
        return songName + " by " + singer;
    }
}