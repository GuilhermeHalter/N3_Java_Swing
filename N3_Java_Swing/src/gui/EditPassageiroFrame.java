package gui;

import crud.PassageiroCRUD;
import model.Passageiro;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EditPassageiroFrame extends JFrame {
    private List<Passageiro> listaPassageiros;
    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtEmail;
    private JTextField txtTelefone;
    private PassageiroCRUD passageiroCRUD;

    public EditPassageiroFrame() {
        this.passageiroCRUD = new PassageiroCRUD(); // Inicializa o CRUD
        this.listaPassageiros = passageiroCRUD.readPassageiros(); // Carrega a lista de passageiros diretamente do banco

        setTitle("Editar Passageiro");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Verifica se há passageiros para editar
        if (listaPassageiros.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há passageiros para editar.");
            dispose(); // Fecha a janela se não houver passageiros
            return;
        }

        // ComboBox para selecionar o passageiro
        String[] passageirosNames = new String[listaPassageiros.size()];
        for (int i = 0; i < listaPassageiros.size(); i++) {
            passageirosNames[i] = listaPassageiros.get(i).getNome();
        }

        JComboBox<String> comboBox = new JComboBox<>(passageirosNames);
        comboBox.addActionListener(e -> fillFields(comboBox.getSelectedIndex()));

        // Campos de texto para editar o passageiro
        txtNome = new JTextField(20);
        txtCpf = new JTextField(20);
        txtEmail = new JTextField(20);
        txtTelefone = new JTextField(20);

        // Botão para salvar as alterações
        JButton btnSave = new JButton("Salvar Alterações");
        btnSave.addActionListener(e -> saveChanges(comboBox.getSelectedIndex()));

        // Layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));
        panel.add(new JLabel("Passageiro:"));
        panel.add(comboBox);
        panel.add(new JLabel("Nome:"));
        panel.add(txtNome);
        panel.add(new JLabel("CPF:"));
        panel.add(txtCpf);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("Telefone:"));
        panel.add(txtTelefone);
        panel.add(new JLabel());
        panel.add(btnSave);

        add(panel);
    }

    // Preenche os campos de edição com os dados do passageiro selecionado
    private void fillFields(int selectedIndex) {
        Passageiro passageiro = listaPassageiros.get(selectedIndex);
        txtNome.setText(passageiro.getNome());
        txtCpf.setText(passageiro.getCpf());
        txtEmail.setText(passageiro.getEmail());
        txtTelefone.setText(passageiro.getTelefone());
    }

    // Salva as alterações feitas no passageiro
    private void saveChanges(int selectedIndex) {
        Passageiro passageiro = listaPassageiros.get(selectedIndex);
        String nome = txtNome.getText();
        String cpf = txtCpf.getText();
        String email = txtEmail.getText();
        String telefone = txtTelefone.getText();

        // Atualiza os atributos do objeto Passageiro
        passageiro.setNome(nome);
        passageiro.setCpf(cpf);
        passageiro.setEmail(email);
        passageiro.setTelefone(telefone);

        // Atualizando no banco de dados
        passageiroCRUD.updatePassageiro(nome, cpf, email, telefone, passageiro.getId());

        // Atualiza a lista local após sucesso
        listaPassageiros.set(selectedIndex, passageiro);

        JOptionPane.showMessageDialog(this, "Alterações salvas com sucesso.");
    }
}
