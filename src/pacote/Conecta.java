package pacote;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Conecta {

    // Método para criar a conexão com o banco de dados
    public static Connection criarConexao() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/ControleDeEstoque";
        String usuario = "postgres";
        String senha = "root";

        return DriverManager.getConnection(url, usuario, senha);
    }

    // Método para inserir produto
    public void inserirProduto(String nome, int quantidade, BigDecimal preco) {
        String sql = "INSERT INTO produtos (nome, quantidade, preco) VALUES (?, ?, ?)";

        try (Connection con = criarConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nome);
            ps.setInt(2, quantidade);
            ps.setBigDecimal(3, preco);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao inserir produto:");
            e.printStackTrace();
        }
    }

    // Método para remover produto
    public void removerProduto(int id) {
        String sql = "DELETE FROM produtos WHERE id = ?";

        try (Connection con = criarConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao remover produto:");
            e.printStackTrace();
        }
    }

    // Método para atualizar produto
    public void atualizarProduto(int id, String nome, int quantidade, BigDecimal preco) {
        String sql = "UPDATE produtos SET nome = ?, quantidade = ?, preco = ? WHERE id = ?";

        try (Connection con = criarConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nome);
            ps.setInt(2, quantidade);
            ps.setBigDecimal(3, preco);
            ps.setInt(4, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar produto:");
            e.printStackTrace();
        }
    }

    // Método para listar produtos (agora retorna uma lista de objetos Produto)
    public List<Produto> listarProdutos() {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtos";

        try (Connection con = criarConexao();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setQuantidade(rs.getInt("quantidade"));
                p.setPreco(rs.getBigDecimal("preco"));
                lista.add(p);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos:");
            e.printStackTrace();
        }

        return lista;
    }
}
