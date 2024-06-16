package co.com.poli.moviesservice.helpers;

public class Response {

    private int code;
    private Object data;

    public Response(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }
}
