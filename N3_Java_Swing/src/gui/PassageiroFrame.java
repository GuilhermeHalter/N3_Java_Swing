package gui;

import model.Passageiro;
import crud.PassageiroCRUD;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PassageiroFrame extends JFrame {
    private JTextField txtNome, txtCpf, txtEmail, txtTelefone;
    PassageiroCRUD passageiroCRUD = new PassageiroCRUD();  // Aqui já é criado corretamente e a conexão é feita no construtor da PassageiroCRUD
    
    public PassageiroFrame() {
        setTitle("Cadastrar Passageiro");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new GridLayout(5, 2));

        // Campos de entrada
        txtNome = new JTextField();
        txtCpf = new JTextField();
        txtEmail = new JTextField();
        txtTelefone = new JTextField();

        // Adicionando rótulos e campos
        add(new JLabel("Nome:"));
        add(txtNome);
        add(new JLabel("CPF:"));
        add(txtCpf);
        add(new JLabel("Email:"));
        add(txtEmail);
        add(new JLabel("Telefone:"));
        add(txtTelefone);

        // Botão de cadastro
        JButton btnCadastrar = new JButton("Cadastrar Passageiro");
        btnCadastrar.addActionListener(e -> cadastrarPassageiro());

        add(btnCadastrar);
    }

    private void cadastrarPassageiro() {
        // Pegando os dados dos campos
        String nome = txtNome.getText();
        String cpf = txtCpf.getText();
        String email = txtEmail.getText();
        String telefone = txtTelefone.getText();

        // Criando o objeto Passageiro com os 4 atributos
        Passageiro passageiro = new Passageiro(nome, cpf, email, telefone);

        // Cadastrando o passageiro no banco de dados
        passageiroCRUD.createPassageiro(passageiro);  // Aqui a conexão é feita dentro do PassageiroCRUD, não precisa chamar explicitamente

        // Opcional: Mensagem de confirmação
        JOptionPane.showMessageDialog(this, "Passageiro cadastrado com sucesso!");
    }
}
