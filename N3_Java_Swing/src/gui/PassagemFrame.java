package gui;

import model.Passagem;
import crud.PassagemCRUD;
import java.util.Date;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PassagemFrame extends JFrame {
    private JTextField txtNumeroVoo, txtOrigem, txtDestino, txtDuracao, txtPreco, txtAssentos;
    private JSpinner spDataPartida;
    private PassagemCRUD passagemCRUD = new PassagemCRUD();  // Inicializando a classe PassagemCRUD para conexão com o banco

    

    public PassagemFrame() {
        setTitle("Cadastrar Passagem");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Inicializando o PassagemCRUD e fazendo a conexão com o banco
        passagemCRUD = new PassagemCRUD();
        passagemCRUD.connectionDB();  // Estabelecendo a conexão com o banco

        // Definindo o layout como GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);  // Espaçamento entre os componentes
        gbc.anchor = GridBagConstraints.WEST;  // Alinha os componentes à esquerda

        // Campos de entrada
        txtNumeroVoo = new JTextField(20);
        txtOrigem = new JTextField(20);
        txtDestino = new JTextField(20);
        txtDuracao = new JTextField(20);
        txtPreco = new JTextField(20);
        txtAssentos = new JTextField(20);
        spDataPartida = new JSpinner(new SpinnerDateModel());

        // Adicionando o rótulo e campo para "Número do Voo"
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Número do Voo:"), gbc);

        gbc.gridx = 1;
        add(txtNumeroVoo, gbc);

        // Adicionando o rótulo e campo para "Origem"
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Origem:"), gbc);

        gbc.gridx = 1;
        add(txtOrigem, gbc);

        // Adicionando o rótulo e campo para "Destino"
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Destino:"), gbc);

        gbc.gridx = 1;
        add(txtDestino, gbc);

        // Adicionando o rótulo e campo para "Data de Partida"
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Data de Partida:"), gbc);

        gbc.gridx = 1;
        add(spDataPartida, gbc);

        // Adicionando o rótulo e campo para "Duração"
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Duração:"), gbc);

        gbc.gridx = 1;
        add(txtDuracao, gbc);

        // Adicionando o rótulo e campo para "Preço"
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Preço:"), gbc);

        gbc.gridx = 1;
        add(txtPreco, gbc);

        // Adicionando o rótulo e campo para "Assentos Disponíveis"
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Assentos Disponíveis:"), gbc);

        gbc.gridx = 1;
        add(txtAssentos, gbc);

        // Botão de cadastro
        JButton btnCadastrar = new JButton("Cadastrar Passagem");
        btnCadastrar.addActionListener(e -> cadastrarPassagem());

        // O botão ocupa as duas colunas, então a gridwidth é definida como 2
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        add(btnCadastrar, gbc);
    }

    private void cadastrarPassagem() {
        try {
            // Pegando os dados dos campos
            String numeroVoo = txtNumeroVoo.getText();
            String origem = txtOrigem.getText();
            String destino = txtDestino.getText();
            String duracao = txtDuracao.getText();
            double preco = Double.parseDouble(txtPreco.getText());
            int assentosDisponiveis = Integer.parseInt(txtAssentos.getText());
            Date dataPartida = (Date) spDataPartida.getValue();

            // Verificando se todos os campos foram preenchidos
            if (numeroVoo.isEmpty() || origem.isEmpty() || destino.isEmpty() || duracao.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");
                return;
            }

            // Criando o objeto Passagem
            Passagem passagem = new Passagem(numeroVoo, origem, destino, dataPartida, duracao, preco, assentosDisponiveis);

            // Cadastrando a passagem no banco de dados
            passagemCRUD.createPassagem(passagem);

            // Mensagem de confirmação
            JOptionPane.showMessageDialog(this, "Passagem cadastrada com sucesso!");
            limparCampos();  // Limpando os campos após o cadastro

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar passagem: " + ex.getMessage());
        }
    }

    // Método para limpar os campos após o cadastro
    private void limparCampos() {
        txtNumeroVoo.setText("");
        txtOrigem.setText("");
        txtDestino.setText("");
        txtDuracao.setText("");
        txtPreco.setText("");
        txtAssentos.setText("");
        spDataPartida.setValue(new Date());
    }
}
