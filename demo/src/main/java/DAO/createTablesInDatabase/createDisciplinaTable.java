package DAO.createTablesInDatabase;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import DAO.ConnectionDAO;
import DAO.createTablesInDatabase.exceptions.disciplicaJaExisteException;

public class createDisciplinaTable extends ConnectionDAO {
    
    boolean sucesso = false;
    
        public boolean createTable() {
            connectToDB();
            String sql = "CREATE TABLE IF NOT EXISTS disciplina (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "nome VARCHAR(255) NOT NULL," +
                    "codigo VARCHAR(255) UNIQUE NOT NULL)";
    
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

    public boolean adicionarDisciplinasIniciais() throws disciplicaJaExisteException {
        connectToDB();

        String[] nomesDisciplinas = {
            "Poçoes",
            "Defesa Contra as Artes das Trevas",
            "Transfiguraçao",
            "Herbologia",
            "Adivinhaçao",
            "Astronomia",
            "História da Magia",
            "Feitiços",
            "Voo com Vassoura",
            "Estudo dos Trouxas"
        };

        String[] codigosDisciplinas = {
            "POT",
            "DCAT",
            "TRF",
            "HER",
            "ADV",
            "AST",
            "HIS",
            "FEI",
            "VVD",
            "ETR"
        };

        boolean sucesso = false;

        try {
            con.setAutoCommit(false);

            if (disciplinasJaAdicionadas()) {
                throw new disciplicaJaExisteException("Disciplinas já adicionadas. Não é possível adicionar novamente.");
            }

            String sql = "INSERT INTO disciplina (nome, codigo) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                for (int i = 0; i < nomesDisciplinas.length; i++) {
                    preparedStatement.setString(1, nomesDisciplinas[i]);
                    preparedStatement.setString(2, codigosDisciplinas[i]);
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }

            con.commit();
            sucesso = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            sucesso = false;

            try {
                con.rollback();
            } catch (SQLException rollbackException) {
                System.out.println("Erro no rollback: " + rollbackException.getMessage());
            }
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
        return sucesso;
    }

    private boolean disciplinasJaAdicionadas() throws SQLException {
        // Check if disciplines already exist in the table
        String sql = "SELECT COUNT(*) FROM disciplina";
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }
}
