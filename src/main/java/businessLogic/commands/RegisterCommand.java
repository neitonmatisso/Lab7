package businessLogic.commands;

import businessLogic.dataBase.LoginManager;
import businessLogic.mainApp.Result;

public class RegisterCommand implements Command {

    LoginManager loginManager;

    public RegisterCommand(LoginManager loginManager, ControlUnit cu){
        cu.addCommand("register", this,CommandType.ARGS);
        this.loginManager = loginManager;
    }

    @Override
    public void execute(String options, Result result) {
        try {
            String login = options.split("\\^")[0];
            String pass = options.split("\\^")[1];
            result.writeResult(loginManager.register(login, pass));
        }catch (Exception e){
            result.writeResult("Послупили неверные аргументы");
        }
    }

    @Override
    public String toString() {
        return "register - регистрация";
    }
}
