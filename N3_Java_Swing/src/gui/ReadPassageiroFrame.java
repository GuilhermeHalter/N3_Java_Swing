package gui;

import model.Passageiro;
import crud.PassageiroCRUD;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReadPassageiroFrame extends JFrame {
    private JTable table;
    private PassageiroCRUD passageiroCRUD;  // Instância do CRUD para obter a lista de passageiros

    public ReadPassageiroFrame() {
        // Inicializando a classe PassageiroCRUD
        passageiroCRUD = new PassageiroCRUD();

        setTitle("Visualizar Passageiros");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Criando o modelo da tabela e definindo as colunas
        String[] columnNames = {"Nome", "Email", "Cpf", "Telefone"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Obtendo a lista de passageiros do banco de dados
        List<Passageiro> listaPassageiros = passageiroCRUD.readPassageiros();

        // Preenchendo a tabela com os dados dos passageiros
        for (Passageiro passageiro : listaPassageiros) {
            Object[] row = {passageiro.getNome(), passageiro.getEmail(), passageiro.getCpf(), passageiro.getTelefone()};
            model.addRow(row);
        }

        // Criando a tabela e adicionando ao scroll pane
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Adicionando o scroll pane (com a tabela) ao frame
        add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        // Tornando a interface gráfica responsiva
        SwingUtilities.invokeLater(() -> {
            new ReadPassageiroFrame().setVisible(true);
        });
    }
}
