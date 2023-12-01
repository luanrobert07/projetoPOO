package DAO.createTablesInDatabase;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import DAO.ConnectionDAO;

public class createAlunoDisciplinaTable extends ConnectionDAO {

    public boolean createTable() {
        connectToDB();
        boolean sucesso = false;

        String sql = "CREATE TABLE IF NOT EXISTS aluno_disciplina (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "id_aluno INT," +
                "id_disciplina INT," +
                "FOREIGN KEY (id_aluno) REFERENCES aluno(id)," +
                "FOREIGN KEY (id_disciplina) REFERENCES disciplina(id)" +
                ")";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.executeUpdate();
            sucesso = true;
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela 'aluno_disciplina': " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }

        return sucesso;
    }
}
