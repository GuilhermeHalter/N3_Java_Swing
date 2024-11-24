package gui;

import javax.swing.*;
import model.Reserva;
import model.Passageiro;
import model.Passagem;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {
    private List<Passageiro> listaPassageiros;  // Lista de passageiros
    private List<Passagem> listaPassagens;      // Lista de passagens
    private List<Reserva> listaReservas;

    // Campos de entrada
    private JTextField txtNomePassageiro;
    private JTextField txtNumeroVoo;

    public MainFrame() {
        // Inicializando as listas
        listaPassageiros = new ArrayList<>();
        listaPassagens = new ArrayList<>();
        listaReservas = new ArrayList<>();

        setTitle("Sistema de Reservas");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela

        // Layout principal
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); // Organiza em colunas

        // Criação dos painéis de botões
        JPanel panelPassagens = createButtonPanel("Passagem", new String[]{"Cadastrar Passagem", "Editar Passagem", "Visualizar Passagens", "Deletar Passagem"});
        JPanel panelPassageiros = createButtonPanel("Passageiro", new String[]{"Cadastrar Passageiro", "Editar Passageiro", "Visualizar Passageiros", "Deletar Passageiro"});
        JPanel panelReservas = createButtonPanel("Reserva", new String[]{"Cadastrar Reserva", "Editar Reserva", "Visualizar Reservas", "Deletar Reserva"});
        JPanel panelPagamentos = createButtonPanel("Pagamento", new String[]{"Cadastrar Pagamento", "Visualizar Pagamentos"});

        // Adicionando os painéis ao frame
        add(panelPassagens);
        add(Box.createVerticalStrut(20));  // Espaço entre as linhas de botões
        add(panelPassageiros);
        add(Box.createVerticalStrut(20));  // Espaço entre as linhas de botões
        add(panelReservas);
        add(Box.createVerticalStrut(20));  // Espaço entre as linhas de botões
        add(panelPagamentos);
    }

    // Método para criar os painéis de botões para cada tipo de entidade
    private JPanel createButtonPanel(String title, String[] buttonLabels) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Organiza botões à esquerda com espaçamento
        panel.setBorder(BorderFactory.createTitledBorder(title));  // Título para o painel

        // Adicionando os botões ao painel
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(e -> handleButtonAction(label)); // Ação do botão
            panel.add(button);
        }

        return panel;
    }

    // Ação dos botões (exemplo de como você pode tratar as ações dos botões)
    private void handleButtonAction(String action) {
        switch (action) {
            case "Cadastrar Passagem":
                openPassagemFrame();
                break;
            case "Editar Passagem":
                editPassagem();
                break;
            case "Visualizar Passagens":
                viewPassagens();
                break;
            case "Deletar Passagem":
                deletePassagem();
                break;
            case "Cadastrar Passageiro":
                openPassageiroFrame();
                break;
            case "Editar Passageiro":
                editPassageiro();
                break;
            case "Visualizar Passageiros":
                viewPassageiros();
                break;
            case "Deletar Passageiro":
                deletePassageiro();
                break;
            case "Cadastrar Reserva":
                openReservaFrame();
                break;
            case "Editar Reserva":
                editReserva();
                break;
            case "Visualizar Reservas":
                viewReservas();
                break;
            case "Deletar Reserva":
                deleteReserva();
                break;
            case "Cadastrar Pagamento":
                openPagamentoFrame();
                break;
            case "Visualizar Pagamentos":
                viewPagamentos();
                break;
        }
    }

    // Ações de passagem
    private void openPassagemFrame() {
        new PassagemFrame().setVisible(true);  
    }

    private void editPassagem() {
        new EditPassagemFrame().setVisible(true);
    }

    private void viewPassagens() {
        new ReadPassagemFrame().setVisible(true);
    }

    private void deletePassagem() {
        new DeletePassagemFrame().setVisible(true);
    }

    // Ações de passageiro
    private void openPassageiroFrame() {
        new PassageiroFrame().setVisible(true);  
    }

    private void editPassageiro() {
        new EditPassageiroFrame().setVisible(true);
    }

    private void viewPassageiros() {
        new ReadPassageiroFrame().setVisible(true);
    }

    private void deletePassageiro() {
        new DeletePassageiroFrame().setVisible(true);
    }

    // Ações de reserva
    private void openReservaFrame() {
        new ReservaFrame().setVisible(true);  
    }

    private void editReserva() {
        new EditReservaFrame().setVisible(true);
    }

    private void viewReservas() {
        new ReadReservaFrame().setVisible(true);
    }

    private void deleteReserva() {
        new DeleteReservaFrame().setVisible(true);
    }

    // Ações de pagamento
    private void openPagamentoFrame() {
    	new PagamentoFrame().setVisible(true);
    }

    private void viewPagamentos() {
        new ReadPagamentoFrame().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
