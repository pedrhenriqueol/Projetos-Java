package pacote;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Cadastro extends JFrame {

    private JPanel contentPane;
    private JTextField textFieldUsuario;
    private JPasswordField passwordField;

    public Cadastro() {
        setTitle("Cadastro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitulo = new JLabel("Cadastro");
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

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBounds(50, 180, 100, 30);
        contentPane.add(btnCadastrar);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(200, 180, 100, 30);
        contentPane.add(btnVoltar);

        // Ação do botão Cadastrar
        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = textFieldUsuario.getText();
                String senha = new String(passwordField.getPassword());

                if (cadastrarUsuario(usuario, senha)) {
                    JOptionPane.showMessageDialog(Cadastro.this, "Usuário cadastrado com sucesso!");
                    Login login = new Login(); // Certifique-se que a classe Login existe
                    login.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(Cadastro.this, "Erro ao cadastrar usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Ação do botão Voltar
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login(); // Certifique-se que a classe Login existe
                login.setVisible(true);
                dispose();
            }
        });
    }

    private boolean cadastrarUsuario(String usuario, String senha) {
        String query = "INSERT INTO usuarios (usuarios, senha) VALUES (?, ?)";
        Connection conn = null;

        try {
            conn = Conecta.criarConexao();
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados!", "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, usuario);
                stmt.setString(2, senha);
                stmt.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
