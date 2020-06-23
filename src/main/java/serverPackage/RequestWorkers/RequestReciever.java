package serverPackage.RequestWorkers;

import connectionPackage.connectionData.Request;
import connectionPackage.connectionData.TransferObject;

import java.util.Queue;

public class RequestReciever {

    private Queue<String> requestQueue;

    public RequestReciever(Queue<String> requestQueue) {
        this.requestQueue = requestQueue;
    }

    public void recievRequest(Request request){
        String query = request.getCommandName() +"@"+request.getArgs();
        requestQueue.add(query);
    }
}
