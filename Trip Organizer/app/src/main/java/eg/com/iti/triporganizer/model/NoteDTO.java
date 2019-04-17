package eg.com.iti.triporganizer.model;

import java.io.Serializable;

public class NoteDTO implements Serializable {

    // Arafa Created NoteDTO
    /*
    this class contains information about note like if it is checked or not , Note Content
    which is a part of Trip as , each trip has it's own notes.
     */
    private boolean done;
    private String body;

    public NoteDTO() {
    }

    public NoteDTO(boolean done, String body) {
        this.done = done;
        this.body = body;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
