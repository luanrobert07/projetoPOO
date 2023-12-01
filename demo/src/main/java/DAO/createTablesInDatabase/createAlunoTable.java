package DAO.createTablesInDatabase;

import java.sql.SQLException;
import java.sql.Statement;

import DAO.ConnectionDAO;

public class createAlunoTable extends ConnectionDAO {

    boolean sucesso = false;

    public boolean createTable() {
        connectToDB();
        String sql = "CREATE TABLE IF NOT EXISTS aluno (" +
                "id INT PRIMARY KEY AUTO_INCREMENT," +
                "id_bruxo INT UNIQUE," +
                "casa VARCHAR(255) NOT NULL," +
                "FOREIGN KEY (id_bruxo) REFERENCES bruxo(id))";

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
