package hk.hku.cs.comp3330project;

public class ChatsModel {
    private String message;
    private String sender;

    public ChatsModel(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }
}
