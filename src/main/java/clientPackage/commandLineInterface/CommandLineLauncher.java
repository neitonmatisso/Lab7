package clientPackage.commandLineInterface;

import clientPackage.Client;
import clientPackage.RequestBuilder;
import clientPackage.ServerStatus;
import clientPackage.excpetions.InvalidCommandException;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CommandLineLauncher {

    public static void main(String[] args) throws InterruptedException {
        //String login = "";
        //String pass = "";
        Client client = new Client();
        Scanner scanner = new Scanner(System.in);
        connect(client,scanner);

        RequestBuilder requestBuilder = new RequestBuilder(client.getSettings());




        while (true){
            boolean loginned;
            if (!client.getLogin().equals("###\n")){
                loginned = true;
            }else{
                loginned = false;
            }
            while (!loginned){
                System.out.println("Login или Register?");
                String ans = scanner.nextLine();
                if (ans.equals("Login") || ans.equals("login")){
                    tryToLogin(client, scanner);
                    Thread.sleep(700);
                    if (!(client.getLogin().equals("###\n") || client.getLogin() == null)){
                        loginned = true;
                    }
                }else{
                    tryToRegister(client, scanner);
                }
            }

            scanner = new Scanner(System.in);

            if(client.getServerStatus().equals(ServerStatus.CLOSE)){
                connect(client,scanner);
            }

            Thread.sleep(150);
            System.out.println("Create your request");
            System.out.print(">");

            String commandData = scanner.nextLine();

            if(commandData.equals("")){
                continue;
            }

            if(commandData.equals("getLogin")){
                System.out.println(client.getLogin());
            }

            if(commandData.contains("login")){
                tryToLogin(client, scanner);
            }

            if(commandData.equals("exit")){
                System.exit(0);
            }

            List<String> request = Arrays.asList(commandData.split(" "));
            Pair<String,String> query = null;

            try {
                switch (request.size()){
                    case 1:
                         query = requestBuilder.completeQuery(request.get(0),null, client.getLogin());
                        break;
                    case 2:
                         query = requestBuilder.completeQuery(request.get(0),request.get(1), client.getLogin());
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
        if (notEmptyCheck(alogin) && notEmptyCheck(apass)){
            client.createLogin(alogin + "^" + apass);
        }
    }

    public static void tryToRegister(Client client, Scanner scanner){
        System.out.println("Начнем процесс регистрации");
        System.out.println("Введите логин:");
        String alogin = scanner.nextLine();
        System.out.println("Введите пароль:");
        String apass = scanner.nextLine();
        if (notEmptyCheck(alogin) && notEmptyCheck(apass)){
            client.createQuery("register",alogin + "^" + apass);
        }

    }

    private static boolean notEmptyCheck(String str){
        return !str.equals("");
    }
}
