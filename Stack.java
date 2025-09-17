public class Stack {
    Song top;
    public Stack(){
        this.top=null;
    }

    public boolean isEmpty() {
        return top == null;
    }

    // Remove and return top song
    public Song pop(){
        if(top==null)
            return null;

        Song temp = top;
        top = top.next;
        temp.next = null; // Prevent memory leak
        return temp;
    }

    // Add song to top of stack
    public void push(Song song){
        if (song == null) {
            throw new IllegalArgumentException("Cannot push null song");
        }

        if(top==null)
            top=song;

        else{
            song.next=top;
            top=song;
        }
    }

    // Print all songs in stack
    public void PrintStack(){
        if(top==null)
            System.out.println("Stack is empty");

        else{
            System.out.println("Stack contents: ");
            Song current=top;

            while(current!=null){
                System.out.println("- "+current);
                current=current.next;
            }
        }
    }





}
