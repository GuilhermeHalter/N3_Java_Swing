package gui;

import crud.PassagemCRUD;
import model.Passagem;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

public class EditPassagemFrame extends JFrame {
    private List<Passagem> listaPassagens;
    private JTextField txtNumeroVoo;
    private JTextField txtOrigem;
    private JTextField txtDestino;
    private JTextField txtDataHoraPartida;
    private JTextField txtDuracao;
    private JTextField txtPreco;
    private JTextField txtAssentosDisponiveis;
    private PassagemCRUD passagemCRUD;

    public EditPassagemFrame() {
        this.passagemCRUD = new PassagemCRUD(); // Inicializa o CRUD
        this.listaPassagens = passagemCRUD.readPassagens(); // Carrega a lista de passagens diretamente do banco

        setTitle("Editar Passagem");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Verifica se há passagens para editar
        if (listaPassagens.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há passagens para editar.");
            dispose(); // Fecha a janela se não houver passagens
            return;
        }

        // ComboBox para selecionar a passagem
        String[] passagensDetails = new String[listaPassagens.size()];
        for (int i = 0; i < listaPassagens.size(); i++) {
            passagensDetails[i] = listaPassagens.get(i).getNumeroVoo() + " - " + listaPassagens.get(i).getOrigem() + " -> " + listaPassagens.get(i).getDestino();
        }

        JComboBox<String> comboBox = new JComboBox<>(passagensDetails);
        comboBox.addActionListener(e -> fillFields(comboBox.getSelectedIndex()));

        // Campos de texto para editar a passagem
        txtNumeroVoo = new JTextField(20);
        txtOrigem = new JTextField(20);
        txtDestino = new JTextField(20);
        txtDataHoraPartida = new JTextField(20); // Espera-se que a data seja fornecida no formato yyyy-MM-dd HH:mm
        txtDuracao = new JTextField(20);
        txtPreco = new JTextField(20);
        txtAssentosDisponiveis = new JTextField(20);

        // Botão para salvar as alterações
        JButton btnSave = new JButton("Salvar Alterações");
        btnSave.addActionListener(e -> saveChanges(comboBox.getSelectedIndex()));

        // Layout usando BoxLayout para empilhar os componentes
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));  // Layout empilhado verticalmente

        // Adiciona os componentes ao painel com espaçamento
        panel.add(createFieldPanel("Passagem:", comboBox));
        panel.add(createFieldPanel("Número do Voo:", txtNumeroVoo));
        panel.add(createFieldPanel("Origem:", txtOrigem));
        panel.add(createFieldPanel("Destino:", txtDestino));
        panel.add(createFieldPanel("Data e Hora da Partida (yyyy-MM-dd HH:mm):", txtDataHoraPartida));
        panel.add(createFieldPanel("Duração:", txtDuracao));
        panel.add(createFieldPanel("Preço:", txtPreco));
        panel.add(createFieldPanel("Assentos Disponíveis:", txtAssentosDisponiveis));
        panel.add(createFieldPanel("", btnSave));  // Botão sem label adicional

        add(panel);
    }

    // Método auxiliar para criar o painel de cada campo (label + componente)
    private JPanel createFieldPanel(String labelText, JComponent component) {
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new FlowLayout(FlowLayout.LEFT));  // Layout simples com alinhamento à esquerda
        fieldPanel.add(new JLabel(labelText));
        fieldPanel.add(component);
        return fieldPanel;
    }

    // Preenche os campos de edição com os dados da passagem selecionada
    private void fillFields(int selectedIndex) {
        Passagem passagem = listaPassagens.get(selectedIndex);
        txtNumeroVoo.setText(passagem.getNumeroVoo());
        txtOrigem.setText(passagem.getOrigem());
        txtDestino.setText(passagem.getDestino());

        // Formatar a data como String antes de passar para setText()
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formattedDate = dateFormat.format(passagem.getDataHoraPartida());  // Converte para String
        txtDataHoraPartida.setText(formattedDate);  // Passa a String formatada para o JTextField

        txtDuracao.setText(passagem.getDuracao());
        txtPreco.setText(String.valueOf(passagem.getPreco()));
        txtAssentosDisponiveis.setText(String.valueOf(passagem.getAssentosDisponiveis()));
    }

    // Salva as alterações feitas na passagem
    private void saveChanges(int selectedIndex) {
        Passagem passagem = listaPassagens.get(selectedIndex);
        String numeroVoo = txtNumeroVoo.getText();
        String origem = txtOrigem.getText();
        String destino = txtDestino.getText();
        String dataHoraPartidaText = txtDataHoraPartida.getText();
        String duracao = txtDuracao.getText();
        String precoText = txtPreco.getText();
        String assentosDisponiveisText = txtAssentosDisponiveis.getText();

        double preco = 0.0;
        int assentosDisponiveis = 0;
        Date dataHoraPartida = null;

        try {
            // Parse da data
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            dataHoraPartida = dateFormat.parse(dataHoraPartidaText);

            // Conversão de preço e assentos
            preco = Double.parseDouble(precoText);
            assentosDisponiveis = Integer.parseInt(assentosDisponiveisText);
        } catch (ParseException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro ao processar dados. Verifique os valores inseridos.");
            return;
        }

        // Atualiza os atributos do objeto Passagem
        passagem.setNumeroVoo(numeroVoo);
        passagem.setOrigem(origem);
        passagem.setDestino(destino);
        passagem.setDataHoraPartida(dataHoraPartida);  // O método agora aceita java.util.Date
        passagem.setDuracao(duracao);
        passagem.setPreco(preco);
        passagem.setAssentosDisponiveis(assentosDisponiveis);

        // Atualizando no banco de dados
        passagemCRUD.updatePassagem(numeroVoo, origem, destino, dataHoraPartida, duracao, preco, assentosDisponiveis, passagem.getId());

        // Atualiza a lista local após sucesso
        listaPassagens.set(selectedIndex, passagem);

        JOptionPane.showMessageDialog(this, "Alterações salvas com sucesso.");
    }
}
