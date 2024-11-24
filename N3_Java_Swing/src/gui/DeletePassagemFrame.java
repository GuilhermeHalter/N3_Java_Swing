package gui;

import model.Passagem;
import crud.PassagemCRUD;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DeletePassagemFrame extends JFrame {
    private List<Passagem> listaPassagens;
    private PassagemCRUD passagemCRUD;

    public DeletePassagemFrame() {
        this.passagemCRUD = new PassagemCRUD();

        setTitle("Deletar Passagem");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        this.listaPassagens = passagemCRUD.readPassagens(); // Carregar passagens

        if (listaPassagens == null || listaPassagens.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma passagem encontrada no banco de dados.");
            return;
        }

        // Listando as passagens na interface
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Passagem passagem : listaPassagens) {
            // Verificando se o número do voo está sendo adicionado corretamente
            System.out.println("Passagem: " + passagem.getNumeroVoo() + " | ID: " + passagem.getId());
            listModel.addElement(passagem.getNumeroVoo() + " (ID: " + passagem.getId() + ")");
        }

        JList<String> list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(list);

        JButton btnDelete = new JButton("Deletar");
        btnDelete.addActionListener(e -> deletePassagem(list));

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnDelete, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    private void deletePassagem(JList<String> list) {
        int selectedIndex = list.getSelectedIndex();
        if (selectedIndex != -1) {
            Passagem passagem = listaPassagens.get(selectedIndex);

            boolean sucesso = passagemCRUD.deletePassagem(passagem.getNumeroVoo());
            if (sucesso) {
                listaPassagens.remove(selectedIndex);
                JOptionPane.showMessageDialog(this, "Passagem " + passagem.getNumeroVoo() + " deletada com sucesso.");
                ((DefaultListModel<String>) list.getModel()).remove(selectedIndex);
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao tentar deletar a passagem.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma passagem para deletar.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DeletePassagemFrame();
        });
    }
}
