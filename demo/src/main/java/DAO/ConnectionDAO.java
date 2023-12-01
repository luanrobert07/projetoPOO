package DAO;

import java.sql.*;

public abstract class ConnectionDAO {
    protected Connection con; 
    PreparedStatement pst; 
    Statement st; 
    ResultSet rs;
    String database = "integracao";
    String user = "root";
    String password = "Reliquia1007";
    String url = "jdbc:mysql://localhost:3306/" + database + "?useTimezone=true&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";

    public void connectToDB() {
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException exc) {
            System.out.println("Erro: " + exc.getMessage());
        }
    }
}