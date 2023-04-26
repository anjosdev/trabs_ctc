import java.sql.*;
import java.util.*;

public class PlanetarioDAO {
    private Connection connection;

    public PlanetarioDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://regulus.cotuca.unicamp.br/BD22595", "BD22595", "BD22595");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Criar novo planetário
    public void criar(Planetario planetario) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO planetarios (nome, cep, complemento, urlFoto, descricao) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, planetario.getNome());
            preparedStatement.setString(2, planetario.getCep());
            preparedStatement.setString(3, planetario.getComplemento());
            preparedStatement.setString(4, planetario.getUrlFoto());
            preparedStatement.setString(5, planetario.getDescricao());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retorna um planetario, dado um ID
    public Planetario recuperar(int idPlanetario) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM planetarios WHERE idPlanetario = ?");
            preparedStatement.setInt(1, idPlanetario);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Planetario(resultSet.getInt("idPlanetario"), resultSet.getString("nome"), resultSet.getString("cep"), resultSet.getString("complemento"), resultSet.getString("urlFoto"), resultSet.getString("descricao"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

	public void update(Planetario planetario) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE planetarios SET nome = ?, cep = ?, complemento = ?, urlFoto = ?, descricao = ? WHERE idPlanetario = ?");
            preparedStatement.setString(1, planetario.getNome());
            preparedStatement.setString(2, planetario.getCep());
            preparedStatement.setString(3, planetario.getComplemento());
            preparedStatement.setString(4, planetario.getUrlFoto());
            preparedStatement.setString(5, planetario.getDescricao());
            preparedStatement.setString(6, Integer.toString(planetario.getIdPlanetario()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
  }

    public void delete(int idPlanetario) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM planetarios WHERE idPlanetario = ?");
            preparedStatement.setInt(1, idPlanetario);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
