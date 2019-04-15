package eg.com.iti.triporganizer.model;

import java.io.Serializable;

public class NoteDTO implements Serializable {

    // Arafa Created NoteDTO
    /*
    this class contains information about note like if it is checked or not , Note Content
    which is a part of Trip as , each trip has it's own notes.
     */
    private boolean isChecked;
    private String content;

    public NoteDTO() {}

    public NoteDTO(boolean isChecked, String content) {
        this.isChecked = isChecked;
        this.content = content;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public String getContent() {
        return content;
    }
}
