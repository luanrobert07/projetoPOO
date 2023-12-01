package DAO.createTablesInDatabase;

import java.sql.SQLException;
import java.sql.Statement;

import DAO.ConnectionDAO;

public class createBruxoTable extends ConnectionDAO {

    boolean sucesso = false;

    public boolean createTable() {
        connectToDB();
        String sql = "CREATE TABLE IF NOT EXISTS bruxo (" +
                "id INT PRIMARY KEY AUTO_INCREMENT," +
                "id_usuario INT," +
                "varinha VARCHAR(255) NOT NULL," +
                "patrono VARCHAR(255) NOT NULL," +
                "habilidade_magica VARCHAR(255) NOT NULL," +
                "FOREIGN KEY (id_usuario) REFERENCES user(id))";
    
        try {
            Statement statement = con.createStatement();
            statement.execute(sql);
            statement.close();
            sucesso = true;
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
            sucesso = false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
        return sucesso;
    }
    
}
