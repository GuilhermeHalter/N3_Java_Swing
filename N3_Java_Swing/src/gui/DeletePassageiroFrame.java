package gui;

import model.Passageiro;
import crud.PassageiroCRUD;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DeletePassageiroFrame extends JFrame {
    private List<Passageiro> listaPassageiros;
    private PassageiroCRUD passageiroCRUD;

    public DeletePassageiroFrame() {
        this.passageiroCRUD = new PassageiroCRUD();

        setTitle("Deletar Passageiro");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        this.listaPassageiros = passageiroCRUD.readPassageiros(); // Carregar passageiros

        if (listaPassageiros == null || listaPassageiros.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum passageiro encontrado no banco de dados.");
            return;
        }

        // Listando os passageiros na interface
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Passageiro passageiro : listaPassageiros) {
            // Verificando se o ID está sendo adicionado corretamente
            System.out.println("Passageiro: " + passageiro.getNome() + " | ID: " + passageiro.getId());
            listModel.addElement(passageiro.getNome() + " (ID: " + passageiro.getId() + ")");
        }

        JList<String> list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(list);

        JButton btnDelete = new JButton("Deletar");
        btnDelete.addActionListener(e -> deletePassageiro(list));

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnDelete, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    private void deletePassageiro(JList<String> list) {
        int selectedIndex = list.getSelectedIndex();
        if (selectedIndex != -1) {
            Passageiro passageiro = listaPassageiros.get(selectedIndex);

            boolean sucesso = passageiroCRUD.deletePassageiro(passageiro.getId());
            if (sucesso) {
                listaPassageiros.remove(selectedIndex);
                JOptionPane.showMessageDialog(this, "Passageiro " + passageiro.getNome() + " deletado com sucesso.");
                ((DefaultListModel<String>) list.getModel()).remove(selectedIndex);
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao tentar deletar o passageiro.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um passageiro para deletar.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DeletePassageiroFrame();
        });
    }
}

