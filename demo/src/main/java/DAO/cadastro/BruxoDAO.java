package DAO.cadastro;

import Model.Bruxo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DAO.ConnectionDAO;

public class BruxoDAO extends ConnectionDAO {

    boolean sucesso = false;

    public boolean cadastrarBruxo(Bruxo bruxo) {
        connectToDB();
        String sql = "INSERT INTO bruxo (id_usuario, varinha, patrono, habilidade_magica) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, bruxo.getIdUsuario());
            preparedStatement.setString(2, bruxo.getVarinha());
            preparedStatement.setString(3, bruxo.getPatrono());
            preparedStatement.setString(4, bruxo.getHabilidadeMagica());
    
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


    public boolean existeBruxoParaUsuario(int idUsuario) {
        connectToDB();
        String sql = "SELECT COUNT(*) FROM bruxo WHERE id_usuario = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, idUsuario);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar bruxo para usuário: " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
        return false;
    }

    public List<Bruxo> obterTodosBruxos() {
        connectToDB();
        List<Bruxo> listaBruxos = new ArrayList<>();

        String sql = "SELECT * FROM bruxo";
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int idUsuario = resultSet.getInt("id_usuario");
                String varinha = resultSet.getString("varinha");
                String patrono = resultSet.getString("patrono");
                String habilidadeMagica = resultSet.getString("habilidade_magica");

                Bruxo bruxo = new Bruxo();
                bruxo.setIdUsuario(idUsuario);
                bruxo.setVarinha(varinha);
                bruxo.setPatrono(patrono);
                bruxo.setHabilidadeMagica(habilidadeMagica);

                listaBruxos.add(bruxo);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao obter bruxos: " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        return listaBruxos;
    }

    public void imprimirTodosBruxos() {
        List<Bruxo> bruxos = obterTodosBruxos();

        if (bruxos.isEmpty()) {
            System.out.println("Nenhum bruxo encontrado no banco de dados.");
        } else {
            System.out.println("Lista de Bruxos:");
            for (Bruxo bruxo : bruxos) {
                System.out.println("ID Usuário: " + bruxo.getIdUsuario());
                System.out.println("Varinha: " + bruxo.getVarinha());
                System.out.println("Patrono: " + bruxo.getPatrono());
                System.out.println("Habilidade Mágica: " + bruxo.getHabilidadeMagica());
                System.out.println("-----");
            }
        }
    }
}

