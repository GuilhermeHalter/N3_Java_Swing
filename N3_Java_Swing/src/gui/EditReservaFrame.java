package gui;

import crud.ReservaCRUD;
import crud.PassageiroCRUD;
import crud.PassagemCRUD;
import model.Reserva;
import model.Passageiro;
import model.Passagem;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EditReservaFrame extends JFrame {
    private List<Reserva> listaReservas;
    private List<Passageiro> listaPassageiros;
    private List<Passagem> listaPassagens;
    private JTextField txtNumeroAssento;
    private JTextField txtValorTotal;
    private JComboBox<String> comboBoxReservas;
    private JComboBox<String> comboBoxPassageiros;
    private JComboBox<String> comboBoxPassagens;
    private ReservaCRUD reservaCRUD;
    private PassageiroCRUD passageiroCRUD;
    private PassagemCRUD passagemCRUD;

    public EditReservaFrame() {
        this.reservaCRUD = new ReservaCRUD();
        this.passageiroCRUD = new PassageiroCRUD();
        this.passagemCRUD = new PassagemCRUD();
        
        // Carrega os dados diretamente do banco
        this.listaReservas = reservaCRUD.readReservas();
        this.listaPassageiros = passageiroCRUD.readPassageiros();
        this.listaPassagens = passagemCRUD.readPassagens();

        setTitle("Editar Reserva");
        setSize(400, 400);  
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Verifica se há reservas para editar
        if (listaReservas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há reservas para editar.");
            dispose();
            return;
        }

        // ComboBox para selecionar a reserva
        String[] reservasNames = new String[listaReservas.size()];
        for (int i = 0; i < listaReservas.size(); i++) {
            Reserva reserva = listaReservas.get(i);
            reservasNames[i] = "ID: " + reserva.getId() + " - Passageiro: " + reserva.getPassageiro().getNome();
        }

        comboBoxReservas = new JComboBox<>(reservasNames);
        comboBoxReservas.addActionListener(e -> fillFields(comboBoxReservas.getSelectedIndex()));

        // ComboBox para selecionar passageiro
        String[] passageirosNames = new String[listaPassageiros.size()];
        for (int i = 0; i < listaPassageiros.size(); i++) {
            passageirosNames[i] = listaPassageiros.get(i).getNome(); // Mostrar nome do passageiro
        }

        comboBoxPassageiros = new JComboBox<>(passageirosNames);

        // ComboBox para selecionar passagem
        String[] passagensNumbers = new String[listaPassagens.size()];
        for (int i = 0; i < listaPassagens.size(); i++) {
            passagensNumbers[i] = listaPassagens.get(i).getNumeroVoo(); // Mostrar número da passagem
        }

        comboBoxPassagens = new JComboBox<>(passagensNumbers);

        // Campos de texto para editar a reserva
        txtNumeroAssento = new JTextField(20);
        txtValorTotal = new JTextField(20);

        // Botão para salvar as alterações
        JButton btnSave = new JButton("Salvar Alterações");
        btnSave.addActionListener(e -> saveChanges(comboBoxReservas.getSelectedIndex()));

        // Layout usando GridBagLayout para um layout mais bonito
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Adicionando os componentes no GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Reserva:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(comboBoxReservas, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Passageiro:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(comboBoxPassageiros, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Passagem:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(comboBoxPassagens, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Número Assento:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(txtNumeroAssento, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Valor Total:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(txtValorTotal, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(btnSave, gbc);

        add(panel);
    }

    // Preenche os campos de edição com os dados da reserva selecionada
    private void fillFields(int selectedIndex) {
        Reserva reserva = listaReservas.get(selectedIndex);
        if (reserva.getId() <= 0) {
            JOptionPane.showMessageDialog(this, "ID da reserva inválido.");
            return;
        }
        Passageiro passageiro = reserva.getPassageiro();
        Passagem passagem = reserva.getPassagem();

        comboBoxPassageiros.setSelectedItem(passageiro.getNome());
        comboBoxPassagens.setSelectedItem(passagem.getNumeroVoo());
        txtNumeroAssento.setText(reserva.getNumeroAssento());
        txtValorTotal.setText(String.valueOf(reserva.getValorTotal()));
    }

    // Salva as alterações feitas na reserva
    private void saveChanges(int selectedIndex) {
        Reserva reserva = listaReservas.get(selectedIndex);

        // Obtém os dados atualizados
        Passageiro selectedPassageiro = listaPassageiros.get(comboBoxPassageiros.getSelectedIndex());
        Passagem selectedPassagem = listaPassagens.get(comboBoxPassagens.getSelectedIndex());

        String numeroAssento = txtNumeroAssento.getText();
        double valorTotal = Double.parseDouble(txtValorTotal.getText());

        // Atualiza os dados da reserva com os novos valores
        reserva.setPassageiro(selectedPassageiro);
        reserva.setPassagem(selectedPassagem);
        reserva.setNumeroAssento(numeroAssento);
        reserva.setValorTotal(valorTotal);

        // Aqui você deve garantir que o ID da reserva está definido
        // Se você não estiver definindo o ID na criação da reserva, você pode fazer isso aqui
        if (reserva.getId() == 0) {
            JOptionPane.showMessageDialog(this, "ID da reserva não pode ser zero.");
            return;
        }

        // Atualiza no banco de dados
        reservaCRUD.updateReserva(reserva);

        // Atualiza a lista local após sucesso
        listaReservas.set(selectedIndex, reserva);

        JOptionPane.showMessageDialog(this, "Alterações salvas com sucesso.");
    }
}
