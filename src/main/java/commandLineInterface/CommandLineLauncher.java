package commandLineInterface;

import clientPackage.Client;
import clientPackage.RequestBuilder;
import clientPackage.ServerStatus;
import clientPackage.excpetions.InvalidCommandException;
import javafx.util.Pair;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CommandLineLauncher {

    public static void main(String[] args) throws InterruptedException {
        String login = "";
        String pass = "";
        Client client = new Client();
        Scanner scanner = new Scanner(System.in);
        connect(client,scanner);

        RequestBuilder requestBuilder = new RequestBuilder(client.getSettings());
        while (true){
            scanner = new Scanner(System.in);

            if(client.getServerStatus().equals(ServerStatus.CLOSE)){
                connect(client,scanner);
            }

            tryToLogin(client, scanner);
            //client.

            System.out.println("Create your request");
            System.out.print(">");

            String commandData = scanner.nextLine();

            if(commandData.equals("")){
                continue;
            }

            if(commandData.equals("exit")){
                System.exit(0);
            }
            String[] data = commandData.split(" ");
            String[] okData = new String[data.length-2];
            for (int i = 0; i < data.length; i++){
                if ((i == (data.length-1)) || (i == (data.length-2))){
                    login = data[data.length-2];
                    pass = data[data.length-1];
                }
                else{
                    okData[i] = data[i];
                }
            }
            List<String> request = Arrays.asList(okData);
            Pair<String,String> query = null;


            try {
                switch (request.size()){
                    case 1:
                         query = requestBuilder.completeQuery(request.get(0),null, login);
                        break;
                    case 2:
                         query = requestBuilder.completeQuery(request.get(0),request.get(1), login);
                        break;
                    default:
                        continue;
                }

                client.createQuery(query.getKey(),query.getValue());

            } catch (InvalidCommandException exception){
                continue;
            }
            Thread.sleep(10);
        }
    }

    public static void connect(Client client, Scanner scanner) throws InterruptedException {
        while (client.getServerStatus().equals(ServerStatus.CLOSE)) {
            System.out.println("Welcome! Enter server IP and Port");
            String[] serverData = scanner.nextLine().split(" ");
            try {
                client.connectToServer(serverData[0], Integer.parseInt(serverData[1]));
            } catch (Exception e){
                continue;
            }
            Thread.sleep(1000);
        }
    }

    public static void tryToLogin(Client client, Scanner scanner){
        System.out.println("Начнем процесс входа в сеть");
        System.out.println("Введите логин:");
        String alogin = scanner.nextLine();
        System.out.println("Введите пароль:");
        String apass = scanner.nextLine();
        client.createLogin(alogin + "^" + apass);
    }
}
