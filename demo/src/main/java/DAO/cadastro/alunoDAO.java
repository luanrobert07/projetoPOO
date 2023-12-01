package DAO.cadastro;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DAO.ConnectionDAO;
import Model.Aluno;

public class alunoDAO extends ConnectionDAO {

    boolean sucesso = true;

    public boolean cadastrarAluno(Aluno aluno) {
        connectToDB();
        String sql = "INSERT INTO aluno (id_bruxo, casa) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, aluno.getIdBruxo());
            preparedStatement.setString(2, aluno.getCasa());
    
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

    public boolean DisciplinasAoAluno(int idAluno, List<Integer> idDisciplinas) {
        connectToDB();
        boolean sucesso = true; // Inicializa como verdadeiro por padrão

        try {
            for (int idDisciplina : idDisciplinas) {
                // Aqui você pode realizar validações, se necessário, antes de associar a disciplina ao aluno
                if (!selecionarDisciplina(idAluno, idDisciplina)) {
                    // Se não conseguir associar uma disciplina, marca como falha e interrompe o loop
                    sucesso = false;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            sucesso = false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }

        return sucesso;
    }

    private boolean selecionarDisciplina(int idAluno, int idDisciplina) throws SQLException {
        // Insira a associação do aluno à disciplina na tabela aluno_disciplina
        String sql = "INSERT INTO aluno_disciplina (id_aluno, id_disciplina) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, idAluno);
            preparedStatement.setInt(2, idDisciplina);

            preparedStatement.execute();
        }

        return true;
    }

    public List<Aluno> obterTodosAlunos() {
        connectToDB();
        List<Aluno> listaAlunos = new ArrayList<>();

        String sql = "SELECT * FROM aluno";
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int idBruxo = resultSet.getInt("id_bruxo");
                String casa = resultSet.getString("casa");

                // Aqui você pode adicionar outras colunas conforme necessário

                Aluno aluno = new Aluno();
                aluno.setIdBruxo(idBruxo);
                aluno.setCasa(casa);

                listaAlunos.add(aluno);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao obter alunos: " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        return listaAlunos;
    }

    public void imprimirTodosAlunos() {
        List<Aluno> alunos = obterTodosAlunos();

        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno encontrado no banco de dados.");
        } else {
            System.out.println("Lista de Alunos:");
            for (Aluno aluno : alunos) {
                System.out.println("ID Bruxo: " + aluno.getIdBruxo());
                System.out.println("Casa: " + aluno.getCasa());
                // Imprima outras informações conforme necessário
                System.out.println("-----");
            }
        }
    }
}
