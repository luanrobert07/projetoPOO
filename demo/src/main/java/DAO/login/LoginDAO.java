package DAO.login;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DAO.ConnectionDAO;

public class LoginDAO extends ConnectionDAO {

    public boolean realizarLogin(String email, String senha) {
        try (PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM user WHERE email = ? AND senha = ?")) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, senha);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Login bem-sucedido! Bem-vindo, " + resultSet.getString("nome"));
                return true;
            } else {
                System.out.println("Login falhou. Verifique suas credenciais.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
            return false;
        }
    }

    public int obterIdUsuarioPorLogin(String email, String senha) {
        connectToDB();
        
        try (PreparedStatement preparedStatement = con.prepareStatement("SELECT id FROM user WHERE email = ? AND senha = ?")) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, senha);
            ResultSet resultSet = preparedStatement.executeQuery();
    
            if (resultSet.next()) {
                return resultSet.getInt("id");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
            return -1;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
    

    public boolean isPrimeiroLogin(int idUsuario) {
        connectToDB();

        try (PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM bruxo WHERE id_usuario = ?")) {
            preparedStatement.setInt(1, idUsuario);
            ResultSet resultSet = preparedStatement.executeQuery();

            return !resultSet.next();
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}
