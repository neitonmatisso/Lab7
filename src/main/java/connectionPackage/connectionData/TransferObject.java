package connectionPackage.connectionData;

import java.io.Serializable;

public class TransferObject implements Serializable {
    private String jsonTransfer;

    public TransferObject(String jsonTransfer) {
        this.jsonTransfer = jsonTransfer;
    }

    public String getJsonTransfer() {
        return jsonTransfer;
    }

    public void setJsonTransfer(String jsonTransfer) {
        this.jsonTransfer = jsonTransfer;
    }
}
