package connectionPackage;

import businessLogic.dataBase.LoginManager;
import connectionPackage.connectionData.TransferObject;

public interface ConnectionListener {
    void getTransferObject(Connection connection, TransferObject transferObject);
    void connectionReady(Connection connection);
    void disconnect(Connection connection);
}
