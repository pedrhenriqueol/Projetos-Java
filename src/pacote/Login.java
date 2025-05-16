package pacote;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldUsuario;
    private JPasswordField passwordField;

    public Login() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitulo = new JLabel("Login");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitulo.setBounds(150, 20, 100, 30);
        contentPane.add(lblTitulo);

        JLabel lblUsuario = new JLabel("Usuário:");
        lblUsuario.setBounds(50, 80, 80, 25);
        contentPane.add(lblUsuario);

        textFieldUsuario = new JTextField();
        textFieldUsuario.setBounds(120, 80, 200, 25);
        contentPane.add(textFieldUsuario);
        textFieldUsuario.setColumns(10);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(50, 120, 80, 25);
        contentPane.add(lblSenha);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 120, 200, 25);
        contentPane.add(passwordField);

        JButton btnLogin = new JButton("Entrar");
        btnLogin.setBounds(50, 180, 100, 30);
        contentPane.add(btnLogin);

        JButton btnCadastro = new JButton("Cadastro");
        btnCadastro.setBounds(200, 180, 100, 30);
        contentPane.add(btnCadastro);

        // Ação do botão de login
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = textFieldUsuario.getText();
                String senha = new String(passwordField.getPassword());

                if (validarLogin(usuario, senha)) {
                    JOptionPane.showMessageDialog(Login.this, "Login bem-sucedido!");

                    // Após login bem-sucedido, abrir o estoque
                    abrirEstoque();
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Usuário ou senha inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Ação do botão de cadastro
        btnCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cadastro cadastro = new Cadastro();
                cadastro.setVisible(true);
                dispose(); // Fecha a tela de login
            }
        });
    }

    // Método de validação do login
    private boolean validarLogin(String usuario, String senha) {
        try (Connection com = Conecta.criarConexao()) {
            String query = "SELECT * FROM usuarios WHERE usuarios = ? AND senha = ?";
            PreparedStatement stmt = com.prepareStatement(query);
            stmt.setString(1, usuario);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // Se retornar algum resultado, o login foi bem-sucedido
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Método para abrir a tela do estoque
    private void abrirEstoque() {
        Estoque estoque = new Estoque(); // Aqui você deve ter a classe Estoque
        estoque.setVisible(true);
        dispose(); // Fecha a janela de login após abrir o estoque
    }
}
