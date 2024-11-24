package gui;

import model.Passagem;
import crud.PassagemCRUD;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReadPassagemFrame extends JFrame {
    private JTable table;
    private PassagemCRUD passagemCRUD;  // Instância do CRUD para obter a lista de passagens

    public ReadPassagemFrame() {
        // Inicializando a classe PassagemCRUD
        passagemCRUD = new PassagemCRUD();

        setTitle("Visualizar Passagens");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Criando o modelo da tabela e definindo as colunas
        String[] columnNames = {"Número do Voo", "Origem", "Destino", "Data/Hora Partida", "Duração", "Preço", "Assentos Disponíveis"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Obtendo a lista de passagens do banco de dados
        List<Passagem> listaPassagens = passagemCRUD.readPassagens();

        // Preenchendo a tabela com os dados das passagens
        for (Passagem passagem : listaPassagens) {
            Object[] row = {passagem.getNumeroVoo(), passagem.getOrigem(), passagem.getDestino(),
                            passagem.getDataHoraPartida(), passagem.getDuracao(), passagem.getPreco(), passagem.getAssentosDisponiveis()};
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
            new ReadPassagemFrame().setVisible(true);
        });
    }
}
