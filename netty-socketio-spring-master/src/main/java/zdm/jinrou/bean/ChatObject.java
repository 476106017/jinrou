package zdm.jinrou.bean;

/**
 * Created by Kosinov_KV
 * on 22.10.2015. netty-socketio-spring.
 */
public class ChatObject {

    private String name;
    private String message;

    public ChatObject() {
    }

    public ChatObject(String name, String message) {
        super();
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

}
