package main.java.model.response;

public class ResponseBody {
    /**
     * Byte data of response body such as html or image file.
     */
    private byte[] body;

    public void setBody(byte[] body) {
        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }
}
