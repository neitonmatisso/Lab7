package serverPackage.RequestWorkers;

import com.google.gson.Gson;
import connectionPackage.Connection;
import connectionPackage.connectionData.Responce;
import connectionPackage.connectionData.ResponseType;
import connectionPackage.connectionData.TransferObject;

import java.io.IOException;
import java.util.Queue;

public class ResponceSender {
    private Queue<String> answerQueue;

    public ResponceSender(Queue<String> answerQueue) {
        this.answerQueue = answerQueue;
    }

    public void sendAnswer(Connection connection){
        try {
            Responce  responce = new Responce(ResponseType.ANSWER,answerQueue.poll());
            connection.sendTransferObject(new TransferObject(new Gson().toJson(responce)));
        } catch (IOException exception) {
            System.out.println("Error with Response sender");
        }
    }

}
