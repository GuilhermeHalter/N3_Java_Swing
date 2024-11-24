package gui;

import model.Reserva;
import crud.ReservaCRUD;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReadReservaFrame extends JFrame {
    private JTable table;
    private ReservaCRUD reservaCRUD;  // Instância do CRUD para obter a lista de reservas

    public ReadReservaFrame() {
        // Inicializando a classe ReservaCRUD
        reservaCRUD = new ReservaCRUD();

        setTitle("Visualizar Reservas");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Criando o modelo da tabela e definindo as colunas
        String[] columnNames = {"Passageiro", "Número do Voo", "Número do Assento", "Valor Total"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Obtendo a lista de reservas do banco de dados
        List<Reserva> listaReservas = reservaCRUD.readReservas();

        // Preenchendo a tabela com os dados das reservas
        for (Reserva reserva : listaReservas) {
            Object[] row = {reserva.getPassageiro().getNome(),  // Exibe o nome do passageiro
                            reserva.getPassagem().getNumeroVoo(),  // Exibe o número do voo da passagem
                            reserva.getNumeroAssento(),  // Exibe o número do assento
                            reserva.getValorTotal()};  // Exibe o valor total da reserva
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
            new ReadReservaFrame().setVisible(true);
        });
    }
}
