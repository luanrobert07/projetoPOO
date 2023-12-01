package DAO.cadastro;

import Model.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import DAO.ConnectionDAO;

public class UserDAO extends ConnectionDAO {

    boolean sucesso = false;

    public boolean cadastrarUsuario(User user) {
        connectToDB();
        String sql = "INSERT INTO user (nome, email, senha, telefone, endereco, cpf) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, user.getNome());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getSenha());
            preparedStatement.setString(4, user.getTelefone());
            preparedStatement.setString(5, user.getEndereco());
            preparedStatement.setString(6, user.getCpf());

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
}
