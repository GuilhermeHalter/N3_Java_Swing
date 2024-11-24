package gui;

import model.Reserva;
import model.Passageiro;
import model.Passagem;
import crud.PassageiroCRUD;
import crud.PassagemCRUD;
import crud.ReservaCRUD;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ReservaFrame extends JFrame {
    private JTextField txtNumeroAssento;
    private JLabel lblValorTotal;
    private JComboBox<String> comboPassageiro, comboPassagem;
    private List<Passageiro> listaPassageiros; // Lista de objetos Passageiro
    private List<Passagem> listaPassagens; // Lista de objetos Passagem

    public ReservaFrame() {
        setTitle("Cadastrar Reserva");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new GridLayout(6, 2));

        // Carregar dados de Passageiros e Passagens
        PassageiroCRUD passageiroCRUD = new PassageiroCRUD();
        PassagemCRUD passagemCRUD = new PassagemCRUD();
        
        // Buscar passageiros e passagens do banco de dados
        listaPassageiros = passageiroCRUD.readPassageiros(); // Buscar passageiros
        listaPassagens = passagemCRUD.readPassagens(); // Buscar passagens

        // Campos de entrada
        txtNumeroAssento = new JTextField();
        lblValorTotal = new JLabel("0.00"); // Exibe o valor total, não editável
        comboPassageiro = new JComboBox<>();
        comboPassagem = new JComboBox<>();

        // Preenchendo o JComboBox com os dados de Passageiro
        for (Passageiro passageiro : listaPassageiros) {
            comboPassageiro.addItem(passageiro.getNome()); // Adiciona o nome do passageiro ao combo
        }

        // Preenchendo o JComboBox com os dados de Passagem
        for (Passagem passagem : listaPassagens) {
            comboPassagem.addItem(passagem.getNumeroVoo()); // Adiciona o número do voo da passagem ao combo
        }

        // Adicionando rótulos e campos
        add(new JLabel("Número do Assento:"));
        add(txtNumeroAssento);
        add(new JLabel("Passageiro:"));
        add(comboPassageiro);
        add(new JLabel("Passagem:"));
        add(comboPassagem);
        add(new JLabel("Valor Total:"));
        add(lblValorTotal);

        // Listener para atualizar o valor total com base na passagem selecionada
        comboPassagem.addActionListener(e -> atualizarValorTotal());

        // Botão de cadastro
        JButton btnCadastrar = new JButton("Cadastrar Reserva");
        btnCadastrar.addActionListener(e -> cadastrarReserva());

        add(btnCadastrar);
    }

    private void atualizarValorTotal() {
        int selectedIndex = comboPassagem.getSelectedIndex();
        if (selectedIndex >= 0) {
            Passagem passagem = listaPassagens.get(selectedIndex);
            lblValorTotal.setText(String.format("%.2f", passagem.getPreco())); // Exibe o preço base formatado
        }
    }

    private void cadastrarReserva() {
        try {
            // Pegando os dados dos campos
            String numeroAssento = txtNumeroAssento.getText();
            // Substituir vírgula por ponto antes de converter
            double valorTotal = Double.parseDouble(lblValorTotal.getText().replace(",", "."));
            Passageiro passageiro = listaPassageiros.get(comboPassageiro.getSelectedIndex()); // Seleção do Passageiro
            Passagem passagem = listaPassagens.get(comboPassagem.getSelectedIndex()); // Seleção da Passagem

            // Criando a reserva
            Reserva reserva = new Reserva(passageiro, passagem, numeroAssento, valorTotal);

            // Cadastrando a reserva no banco de dados
            ReservaCRUD reservaCrud = new ReservaCRUD();
            reservaCrud.createReserva(reserva);

            // Exibe uma mensagem de sucesso
            JOptionPane.showMessageDialog(this, "Reserva cadastrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar reserva. Verifique os dados inseridos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

}
