package gui;

import crud.MetodoPagamentoCRUD;
import crud.PagamentoCRUD;
import crud.ReservaCRUD;
import model.MetodoPagamento;
import model.Pagamento;
import model.Reserva;

import javax.swing.*;
import javax.swing.SpinnerDateModel;
import java.awt.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class PagamentoFrame extends JFrame {
    private JComboBox<Reserva> comboReservas;
    private JComboBox<MetodoPagamento> comboMetodosPagamento; // Alterado para MetodoPagamento
    private JTextField txtValorPago;
    private JSpinner spinnerDataPagamento; // Substituindo JTextField para data
    private JButton btnCadastrar;

    private PagamentoCRUD pagamentoCRUD;
    private ReservaCRUD reservaCRUD;
    private MetodoPagamentoCRUD metodoPagamentoCRUD; // Adicionado

    public PagamentoFrame() {
        pagamentoCRUD = new PagamentoCRUD();
        reservaCRUD = new ReservaCRUD();
        metodoPagamentoCRUD = new MetodoPagamentoCRUD(); // Inicializado

        setTitle("Cadastrar Pagamento");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        // Criando os componentes
        comboReservas = new JComboBox<>();
        comboMetodosPagamento = new JComboBox<>();
        txtValorPago = new JTextField();
        txtValorPago.setEditable(false); // Tornando o campo "Valor Pago" somente leitura

        // Configurando JSpinner para entrada de data
        spinnerDataPagamento = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerDataPagamento, "yyyy-MM-dd HH:mm:ss");
        spinnerDataPagamento.setEditor(editor);

        btnCadastrar = new JButton("Cadastrar Pagamento");

        // Carregar reservas e métodos de pagamento
        loadReservas();
        loadMetodosPagamento();

        // Adicionando ActionListener para atualizar o valor ao selecionar a reserva
        comboReservas.addActionListener(e -> updateValorPago());

        // Adicionando os componentes ao frame
        add(new JLabel("Reserva:"));
        add(comboReservas);
        add(new JLabel("Método de Pagamento:"));
        add(comboMetodosPagamento);
        add(new JLabel("Valor Pago:"));
        add(txtValorPago);
        add(new JLabel("Data do Pagamento:"));
        add(spinnerDataPagamento);
        add(btnCadastrar);

        // Ação do botão de cadastro
        btnCadastrar.addActionListener(e -> cadastrarPagamento());
    }

    private void loadReservas() {
        List<Reserva> reservas = reservaCRUD.readReservas();
        for (Reserva reserva : reservas) {
            comboReservas.addItem(reserva);
        }
    }

    private void loadMetodosPagamento() {
        List<MetodoPagamento> metodosPagamento = metodoPagamentoCRUD.readMetodosPagamento();
        for (MetodoPagamento metodo : metodosPagamento) {
            comboMetodosPagamento.addItem(metodo);
        }
    }

    private void updateValorPago() {
        Reserva reservaSelecionada = (Reserva) comboReservas.getSelectedItem();
        if (reservaSelecionada != null) {
            txtValorPago.setText(String.valueOf(reservaSelecionada.getValorTotal()));
        }
    }

    private void cadastrarPagamento() {
        try {
            Reserva reservaSelecionada = (Reserva) comboReservas.getSelectedItem();
            MetodoPagamento metodoSelecionado = (MetodoPagamento) comboMetodosPagamento.getSelectedItem();
            double valorPago = Double.parseDouble(txtValorPago.getText());
            Timestamp dataPagamento = new Timestamp(((Date) spinnerDataPagamento.getValue()).getTime());

            System.out.println("Reserva: " + reservaSelecionada.getId());
            System.out.println("Método de Pagamento: " + metodoSelecionado.getId());
            System.out.println("Valor Pago: " + valorPago);
            System.out.println("Data do Pagamento: " + dataPagamento);

            Pagamento pagamento = new Pagamento(
                reservaSelecionada.getId(),
                metodoSelecionado.getId(),
                valorPago,
                dataPagamento
            );

            pagamentoCRUD.createPagamento(pagamento);

            JOptionPane.showMessageDialog(this, "Pagamento cadastrado com sucesso!");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar pagamento: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PagamentoFrame().setVisible(true));
    }
}
