package gui;

import crud.PagamentoCRUD;
import model.Pagamento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReadPagamentoFrame extends JFrame {

    private JTable tablePagamentos;
    private JScrollPane scrollPane;
    private JButton btnVisualizar;
    private PagamentoCRUD pagamentoCRUD;

    public ReadPagamentoFrame() {
        pagamentoCRUD = new PagamentoCRUD();

        setTitle("Visualizar Pagamentos");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Criando a tabela de pagamentos
        tablePagamentos = new JTable();
        scrollPane = new JScrollPane(tablePagamentos);
        tablePagamentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Configurando os botões
        btnVisualizar = new JButton("Visualizar Pagamento");

        // Adicionando os componentes ao frame
        add(scrollPane, BorderLayout.CENTER);
        JPanel panel = new JPanel();
        panel.add(btnVisualizar);
        add(panel, BorderLayout.SOUTH);

        // Carregar os pagamentos
        loadPagamentos();

        // Ação para visualizar um pagamento selecionado
        btnVisualizar.addActionListener(e -> visualizarPagamento());

        // Exibir a janela
        setVisible(true);
    }

    private void loadPagamentos() {
        List<Pagamento> pagamentos = pagamentoCRUD.readPagamentos();
        DefaultTableModel model = new DefaultTableModel();
        
        // Definindo os cabeçalhos das colunas
        model.addColumn("ID");
        model.addColumn("Reserva ID");
        model.addColumn("Método Pagamento ID");
        model.addColumn("Valor Pago");
        model.addColumn("Data do Pagamento");

        // Preenchendo a tabela com os dados dos pagamentos
        for (Pagamento pagamento : pagamentos) {
            model.addRow(new Object[]{
                pagamento.getId(),
                pagamento.getReservaId(),
                pagamento.getMetodoPagamentoId(),
                pagamento.getValorPago(),
                pagamento.getDataPagamento()
            });
        }

        tablePagamentos.setModel(model);
    }

    private void visualizarPagamento() {
        int selectedRow = tablePagamentos.getSelectedRow();
        if (selectedRow >= 0) {
            int pagamentoId = (int) tablePagamentos.getValueAt(selectedRow, 0);
            // Aqui você pode abrir um novo JFrame ou exibir detalhes do pagamento
            // Exemplo: 
            Pagamento pagamento = pagamentoCRUD.readPagamentos().get(selectedRow);  // Obter o pagamento selecionado
            JOptionPane.showMessageDialog(this, "Detalhes do Pagamento: \n"
                    + "ID: " + pagamento.getId() + "\n"
                    + "Reserva ID: " + pagamento.getReservaId() + "\n"
                    + "Método Pagamento ID: " + pagamento.getMetodoPagamentoId() + "\n"
                    + "Valor Pago: " + pagamento.getValorPago() + "\n"
                    + "Data Pagamento: " + pagamento.getDataPagamento());
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um pagamento para visualizar.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ReadPagamentoFrame::new);
    }
}
