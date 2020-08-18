package businessLogic.dataBase;

import businessLogic.mainApp.Result;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class dataBaseManager {

    private dataBaseHandler handler;

    public dataBaseManager(dataBaseHandler handler){
        this.handler = handler;
    }

    public void executeUpdate(String query){
        try {
            Statement statement = handler.getStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            System.out.println("Ошибка при запросе update dataBaseManager");
        }
    }

    public List<Map<String, Object>> executeQuery(String query){
        List<Map<String, Object>> out = null;
        try {
            Statement statement = handler.getStatement();
            ResultSet resultSet = statement.executeQuery(query);
            out = getResultSet(resultSet);
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Ошибка при запросе query dataBaseManager");
        }
        return out;
    }

    public List<Map<String, Object>> getResultSet(ResultSet resultSet) {
        // this list will hold all the data returned from resultset
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        try{
            while(resultSet.next()) {
                // this map corresponds to each row of the resultset
                // key: column-name, value: column-value
                Map<String, Object> row = new LinkedHashMap<String, Object>();

                // populate each row using resultset Meta data
                ResultSetMetaData meta = resultSet.getMetaData();
                for (int i=0; i<meta.getColumnCount(); i++)
                    row.put(meta.getColumnName(i+1), resultSet.getObject(i+1));
                rows.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }

    public void clear(){
        //
    }


}
