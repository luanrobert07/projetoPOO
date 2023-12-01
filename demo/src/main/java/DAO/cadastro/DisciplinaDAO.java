package DAO.cadastro;

import Model.AlunoDisciplinaInfo;
import Model.Disciplina;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

import DAO.ConnectionDAO;

public class DisciplinaDAO extends ConnectionDAO {

    boolean sucesso = false;

    public boolean cadastrarDisciplina(Disciplina disciplina) {
        connectToDB();
        String sql = "INSERT INTO disciplina (nome, codigo) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, disciplina.getNome());
            preparedStatement.setString(2, disciplina.getCodigo());

            preparedStatement.execute();
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

    public List<Disciplina> listarDisciplinas() {
        connectToDB();
        List<Disciplina> disciplinas = new ArrayList<>();
        String sql = "SELECT * FROM disciplina";
    
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
    
            while (resultSet.next()) {
                Disciplina disciplina = new Disciplina();
                disciplina.setId(resultSet.getInt("id"));
                disciplina.setNome(resultSet.getString("nome"));
                disciplina.setCodigo(resultSet.getString("codigo"));
                disciplinas.add(disciplina);
            }
    
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    
        return disciplinas;
    }

    public boolean associarAlunoDisciplina(int idAluno, int idDisciplina) {
        connectToDB();
        boolean sucesso = false;
        String sql = "INSERT INTO aluno_disciplina (id_aluno, id_disciplina) VALUES (?, ?)";
    
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, idAluno);
            preparedStatement.setInt(2, idDisciplina);
    
            preparedStatement.execute();
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

    public List<AlunoDisciplinaInfo> listarAlunosComDisciplinas() {
        connectToDB();
        List<AlunoDisciplinaInfo> alunosDisciplinas = new ArrayList<>();
        String sql = "SELECT a.id AS id_aluno, a.id_bruxo, a.casa, d.id AS id_disciplina, d.nome AS nome_disciplina, d.codigo " +
                     "FROM aluno a " +
                     "JOIN aluno_disciplina ad ON a.id = ad.id_aluno " +
                     "JOIN disciplina d ON ad.id_disciplina = d.id";
        
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
        
            while (resultSet.next()) {
                AlunoDisciplinaInfo alunoDisciplinaInfo = new AlunoDisciplinaInfo();
                alunoDisciplinaInfo.setIdAluno(resultSet.getInt("id_aluno"));
                alunoDisciplinaInfo.setIdBruxo(resultSet.getInt("id_bruxo"));
                alunoDisciplinaInfo.setCasa(resultSet.getString("casa"));
                alunoDisciplinaInfo.setIdDisciplina(resultSet.getInt("id_disciplina"));
                alunoDisciplinaInfo.setNomeDisciplina(resultSet.getString("nome_disciplina"));
                alunoDisciplinaInfo.setCodigoDisciplina(resultSet.getString("codigo"));
                
                alunosDisciplinas.add(alunoDisciplinaInfo);
            }
        
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
        
        return alunosDisciplinas;
    }
    
}
