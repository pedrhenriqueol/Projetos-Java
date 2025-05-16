package pacote;

import java.awt.EventQueue;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Estoque extends JFrame {

    private JPanel contentPane;
    private JTextField textFieldNome;
    private JTextField textFieldQuantidade;
    private JTextField textFieldPreco;
    private DefaultListModel<String> listaProdutosModel = new DefaultListModel<>();
    private JList<String> listaProdutos;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Estoque frame = new Estoque();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Estoque() {
        setTitle("Controle de Estoque");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNome = new JLabel("Nome");
        lblNome.setBounds(10, 10, 100, 14);
        contentPane.add(lblNome);

        textFieldNome = new JTextField();
        textFieldNome.setBounds(10, 30, 150, 20);
        contentPane.add(textFieldNome);

        JLabel lblQuantidade = new JLabel("Quantidade");
        lblQuantidade.setBounds(10, 60, 100, 14);
        contentPane.add(lblQuantidade);

        textFieldQuantidade = new JTextField();
        textFieldQuantidade.setBounds(10, 80, 150, 20);
        contentPane.add(textFieldQuantidade);

        JLabel lblPreco = new JLabel("Preço");
        lblPreco.setBounds(10, 110, 100, 14);
        contentPane.add(lblPreco);

        textFieldPreco = new JTextField();
        textFieldPreco.setBounds(10, 130, 150, 20);
        contentPane.add(textFieldPreco);

        JButton btnAdicionar = new JButton("Adicionar");
        btnAdicionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = textFieldNome.getText();
                    int quantidade = Integer.parseInt(textFieldQuantidade.getText());
                    BigDecimal preco = new BigDecimal(textFieldPreco.getText());

                    Conecta con = new Conecta();
                    con.inserirProduto(nome, quantidade, preco);
                    JOptionPane.showMessageDialog(null, "Produto adicionado com sucesso!");
                    atualizarListaProdutos();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro ao adicionar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnAdicionar.setBounds(10, 170, 100, 23);
        contentPane.add(btnAdicionar);

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listaProdutos.getSelectedIndex();
                if (selectedIndex != -1) {
                    try {
                        String selectedItem = listaProdutos.getSelectedValue();
                        int id = Integer.parseInt(selectedItem.split(" - ")[0]);

                        String nome = textFieldNome.getText();
                        int quantidade = Integer.parseInt(textFieldQuantidade.getText());
                        BigDecimal preco = new BigDecimal(textFieldPreco.getText());

                        Conecta con = new Conecta();
                        con.atualizarProduto(id, nome, quantidade, preco);
                        JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso!");
                        atualizarListaProdutos();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Erro ao atualizar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um produto para atualizar!", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        btnAtualizar.setBounds(10, 204, 100, 23);
        contentPane.add(btnAtualizar);

        JButton btnRemover = new JButton("Remover");
        btnRemover.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listaProdutos.getSelectedIndex();
                if (selectedIndex != -1) {
                    try {
                        String selectedItem = listaProdutos.getSelectedValue();
                        int id = Integer.parseInt(selectedItem.split(" - ")[0]);

                        Conecta con = new Conecta();
                        con.removerProduto(id);
                        JOptionPane.showMessageDialog(null, "Produto removido com sucesso!");
                        atualizarListaProdutos();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Erro ao remover produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um produto para remover!", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        btnRemover.setBounds(10, 238, 100, 23);
        contentPane.add(btnRemover);

        listaProdutosModel = new DefaultListModel<>();
        listaProdutos = new JList<>(listaProdutosModel);
        JScrollPane scrollPane = new JScrollPane(listaProdutos);
        scrollPane.setBounds(200, 10, 370, 300);
        contentPane.add(scrollPane);

        atualizarListaProdutos();
    }

    // Método corrigido para listar produtos com segurança
    private void atualizarListaProdutos() {
        listaProdutosModel.clear();
        try {
            Conecta con = new Conecta();
            List<Produto> produtos = (List<Produto>) con.listarProdutos();

            for (Produto p : produtos) {
                String item = p.getId() + " - " + p.getNome() + " - " + p.getQuantidade() + " - R$" + p.getPreco();
                listaProdutosModel.addElement(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
