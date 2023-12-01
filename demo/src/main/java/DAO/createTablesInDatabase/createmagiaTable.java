package DAO.createTablesInDatabase;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import DAO.ConnectionDAO;

public class createmagiaTable extends ConnectionDAO {

    boolean success = false;

    public boolean createTable() {
        connectToDB();
        String sql = "CREATE TABLE IF NOT EXISTS magia (" +
                "id INT PRIMARY KEY AUTO_INCREMENT," +
                "nome VARCHAR(255) NOT NULL," +
                "descricao VARCHAR(255) NOT NULL," +
                "id_professor INT," +
                "FOREIGN KEY (id_professor) REFERENCES professor(id))";

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

    public boolean adicionarMagiasIniciais() {
        connectToDB();
    
        String[] nomesMagias = {
            "Expelliarmus",
            "Lumos",
            "Alohomora",
            "Expecto Patronum",
            "Accio",
            "Protego",
            "Stupefy",
            "Aguamenti",
            "Invisibility Charm",
            "Leviosa"
        };
    
        String[] descricoesMagias = {
            "Disarms your opponent",
            "Creates light at the wand tip",
            "Unlocks doors and windows",
            "Summons a Patronus",
            "Summons objects",
            "Provides a magical barrier",
            "Stuns the target",
            "Produces water from the wand",
            "Makes objects invisible",
            "Levitates objects"
        };
    
        boolean sucesso = false;
    
        try {
            con.setAutoCommit(false);
    
            if (magiasJaAdicionadas()) {
                System.out.println("Magias já adicionadas. Não é possível adicionar novamente.");
            } else {
                String sql = "INSERT INTO magia (nome, descricao, id_professor) VALUES (?, ?, ?)";
    
                try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                    int totalProfessores = getTotalProfessores();
    
                    for (int i = 0; i < nomesMagias.length; i++) {
                        int professorId = (i % totalProfessores) + 1; // +1 para evitar ID 0
                        preparedStatement.setString(1, nomesMagias[i]);
                        preparedStatement.setString(2, descricoesMagias[i]);
                        preparedStatement.setInt(3, professorId);
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
    
    private boolean magiasJaAdicionadas() {
        return false;
    }

    private int getTotalProfessores() throws SQLException {
        String sql = "SELECT COUNT(*) FROM professor";
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        }
        return 0;
    }

    public void mostrarMagiasEProfessores() {
        connectToDB();
    
        String sql = "SELECT m.nome AS magia, m.descricao, p.nome AS professor " +
                     "FROM magia m " +
                     "JOIN professor p ON m.id_professor = p.id";
    
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String magia = resultSet.getString("magia");
                    String descricao = resultSet.getString("descricao");
                    String professor = resultSet.getString("professor");
    
                    System.out.println("Magia: " + magia);
                    System.out.println("Descriçao: " + descricao);
                    System.out.println("Professor: " + professor);
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao recuperar magias e professores: " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexao: " + e.getMessage());
            }
        }
    }
    
    
}
