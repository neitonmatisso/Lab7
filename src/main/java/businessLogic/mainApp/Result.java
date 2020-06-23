package businessLogic.mainApp;

public class Result {
    private String res;
    public Result(){
        res = "";
    }
    public void writeResult(String s){
        res +=  s + "\n" ;
    }
    public String checkResult(){
        return res;
    }
    public void  clear(){
        res = "";
    }
}
