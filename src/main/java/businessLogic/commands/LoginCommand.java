package businessLogic.commands;

import businessLogic.dataBase.LoginManager;
import businessLogic.mainApp.Result;

public class LoginCommand implements Command {

    LoginManager loginManager;

    public LoginCommand(LoginManager loginManager, ControlUnit cu){
        cu.addCommand("exit", this,CommandType.CLEAR);
        this.loginManager = loginManager;
    }

    @Override
    public void execute(String options, Result result) {
        // СУКАААААААААААААААААААААААААААААААААААААААААААААААААААА
    }




}
