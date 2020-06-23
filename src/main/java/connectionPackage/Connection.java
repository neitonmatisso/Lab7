package connectionPackage;

import connectionPackage.connectionData.TransferObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class Connection {
    private Socket connectionSocket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private ConnectionListener connectionListener;
    private Thread mainThread;

    public Connection(Socket serverSocket, ConnectionListener server) {
        connectionSocket = serverSocket;
        connectionListener = server;

        try {
            objectOutputStream = new ObjectOutputStream(connectionSocket.getOutputStream());
            objectInputStream = new ObjectInputStream(connectionSocket.getInputStream());
        } catch (IOException exception) {
            System.out.println("Socket connect exception");
        }


       mainThread =  new Thread(() -> {
            connectionListener.connectionReady(Connection.this);

            while (true) {
                try {
                    TransferObject transferObject = (TransferObject) objectInputStream.readObject();
                    connectionListener.getTransferObject( Connection.this, transferObject);

                } catch (Exception e) {
                    try {
                        System.out.println("Возникла проблема с подключением");
                        System.out.println("Попытка переподключиться № 1");
                        if (connectionSocket.isClosed()) {
                            break;
                        }
                        mainThread.wait(100);
                        System.out.println("Попытка переподключиться № 2");
                        if (connectionSocket.isClosed()) {
                            break;
                        }
                        System.out.println("Попытка переподключиться № 3");
                        if (connectionSocket.isClosed()) {
                            break;
                        }
                        connectionListener.disconnect(Connection.this);
                        isDisconnect();
                        break;
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        mainThread.start();
    }

    public Connection(ConnectionListener client, String IP, int port) throws IOException {
        this(new Socket(IP, port), client);
    }

    public void isDisconnect(){
        try {
            objectInputStream.close();
            objectOutputStream.close();
            connectionSocket.close();
            mainThread.interrupt();
        } catch (IOException exception){
            System.out.println("Error on error bruh");
        }

    }

    public void sendTransferObject(TransferObject transferObject) throws IOException {
            objectOutputStream.writeObject(transferObject);
            objectOutputStream.flush();
    }

}
