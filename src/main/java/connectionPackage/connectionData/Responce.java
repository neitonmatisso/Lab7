package connectionPackage.connectionData;

public class Responce {
    private ResponseType responseType;
    private String responseOption;

    public Responce(ResponseType responseType, String responseOption) {
        this.responseType = responseType;
        this.responseOption = responseOption;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public String getResponseOption() {
        return responseOption;
    }

    public void setResponseOption(String responseOption) {
        this.responseOption = responseOption;
    }
}
