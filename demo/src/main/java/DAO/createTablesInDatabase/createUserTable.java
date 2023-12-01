package DAO.createTablesInDatabase;

import java.sql.SQLException;
import java.sql.Statement;

import DAO.ConnectionDAO;

public class createUserTable extends ConnectionDAO {

    boolean sucesso = false;

    public boolean createTable() {
        connectToDB();
        String sql = "CREATE TABLE IF NOT EXISTS user (" +
                "id INT PRIMARY KEY AUTO_INCREMENT," +
                "nome VARCHAR(255) NOT NULL," +
                "email VARCHAR(255) NOT NULL," +
                "senha VARCHAR(255) NOT NULL," +
                "telefone VARCHAR(20) NOT NULL," +
                "endereco VARCHAR(255) NOT NULL," +
                "cpf VARCHAR(14) NOT NULL)";
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
