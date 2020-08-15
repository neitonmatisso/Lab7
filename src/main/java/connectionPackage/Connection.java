package connectionPackage;

import connectionPackage.connectionData.TransferObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Connection {
    private Socket connectionSocket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private ConnectionListener connectionListener;
    private Thread mainThread;
    private ExecutorService service =  Executors.newFixedThreadPool(10);

    public Connection(Socket serverSocket, ConnectionListener server) {
        connectionSocket = serverSocket;
        connectionListener = server;

        try {
            objectOutputStream = new ObjectOutputStream(connectionSocket.getOutputStream());
            objectInputStream = new ObjectInputStream(connectionSocket.getInputStream());
        } catch (IOException exception) {
            System.out.println("Socket connect exception");
        }

        Runnable task = () -> {
            connectionListener.connectionReady(Connection.this);

            while (true) {
                try {
                    TransferObject transferObject = (TransferObject) objectInputStream.readObject();
                    connectionListener.getTransferObject( Connection.this, transferObject);

                } catch (Exception e) {
                    System.out.println("Возникла проблема с подключением");
                    connectionListener.disconnect(Connection.this);
                    isDisconnect(Thread.currentThread());
                    break;
                }
            }
        };
       service.execute(task);
    }

    public Connection(ConnectionListener client, String IP, int port) throws IOException {
        this(new Socket(IP, port), client);
    }

    public void isDisconnect(Thread thread){
        try {
            objectInputStream.close();
            objectOutputStream.close();
            connectionSocket.close();
            thread.interrupt();
        } catch (IOException exception){
            System.out.println("Error on error bruh");
        }

    }

    public void sendTransferObject(TransferObject transferObject) throws IOException {
            objectOutputStream.writeObject(transferObject);
            objectOutputStream.flush();
    }

}
