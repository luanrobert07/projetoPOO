package DAO.createTablesInDatabase;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import DAO.ConnectionDAO;

public class createProfessorTable extends ConnectionDAO {

    boolean success = false;

    public boolean createTable() {
        connectToDB();
        String sql = "CREATE TABLE IF NOT EXISTS professor (" +
                "id INT PRIMARY KEY AUTO_INCREMENT," +
                "nome VARCHAR(255) NOT NULL)";

        try {
            Statement statement = con.createStatement();
            statement.execute(sql);
            statement.close();
            success = true;
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
            success = false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
        return success;
    }

    public boolean adicionarProfessoresIniciais() {
        connectToDB();

        String[] nomesProfessores = {
            "Professor Dumbledore",
            "Professor McGonagall",
            "Professor Snape",
            "Professor Flitwick",
            "Professor Hagrid"
        };

        boolean sucesso = false;

        try {
            con.setAutoCommit(false);

            if (professoresJaAdicionados()) {
                System.out.println("Professores já adicionados. Não é possível adicionar novamente.");
            } else {
                String sql = "INSERT INTO professor (nome) VALUES (?)";

                try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                    for (String nomeProfessor : nomesProfessores) {
                        preparedStatement.setString(1, nomeProfessor);
                        preparedStatement.addBatch();
                    }
                    preparedStatement.executeBatch();
                }

                con.commit();
                sucesso = true;
            }
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

    private boolean professoresJaAdicionados() throws SQLException {
        String sql = "SELECT COUNT(*) FROM professor";
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

    public void mostrarProfessores() {
        connectToDB();
    
        String sql = "SELECT id, nome FROM professor";
    
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nome = resultSet.getString("nome");
                    System.out.println("ID: " + id + ", Nome: " + nome);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao recuperar professores: " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}
