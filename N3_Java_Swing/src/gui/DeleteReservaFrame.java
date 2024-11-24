package gui;

import model.Reserva;
import crud.ReservaCRUD;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DeleteReservaFrame extends JFrame {
    private List<Reserva> listaReservas;
    private ReservaCRUD reservaCRUD;

    public DeleteReservaFrame() {
        this.reservaCRUD = new ReservaCRUD();

        setTitle("Deletar Reserva");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        this.listaReservas = reservaCRUD.readReservas(); // Carregar reservas

        if (listaReservas == null || listaReservas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma reserva encontrada no banco de dados.");
            return;
        }

        // Listando as reservas na interface
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Reserva reserva : listaReservas) {
            // Verificando se o ID e informações adicionais estão sendo adicionados corretamente
            System.out.println("Reserva: " + reserva.getId() + " | Passageiro: " + reserva.getPassageiro().getNome());
            listModel.addElement("Reserva ID: " + reserva.getId() + 
                " - Passageiro: " + reserva.getPassageiro().getNome() +
                " - Assento: " + reserva.getNumeroAssento());
        }

        JList<String> list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(list);

        JButton btnDelete = new JButton("Deletar");
        btnDelete.addActionListener(e -> deleteReserva(list));

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnDelete, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    private void deleteReserva(JList<String> list) {
        int selectedIndex = list.getSelectedIndex();
        if (selectedIndex != -1) {
            Reserva reserva = listaReservas.get(selectedIndex);

            boolean sucesso = reservaCRUD.deleteReserva(reserva.getId());
            if (sucesso) {
                listaReservas.remove(selectedIndex);
                JOptionPane.showMessageDialog(this, "Reserva ID " + reserva.getId() + " deletada com sucesso.");
                ((DefaultListModel<String>) list.getModel()).remove(selectedIndex);
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao tentar deletar a reserva.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma reserva para deletar.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DeleteReservaFrame();
        });
    }
}
