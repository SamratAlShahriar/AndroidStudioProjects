package md.samratalshahriar.nayeem.customlistview;

public class MessageModel {
    private int id;
    private String message;

    public MessageModel(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
