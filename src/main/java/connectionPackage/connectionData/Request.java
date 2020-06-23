package connectionPackage.connectionData;

public class Request {
    private RequestType requestType;
    private String commandName;
    private String args;
    //String login;  для 7 лабы
    //String pass;
    public Request(RequestType requestType, String commandName, String args) {
        this.requestType = requestType;
        this.commandName = commandName;
        this.args = args;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }
}

