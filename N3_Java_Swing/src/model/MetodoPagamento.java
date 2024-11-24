package model;

public class MetodoPagamento {
    private int id;
    private String nome;

    // Construtor
    public MetodoPagamento(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome; // Para exibir no JComboBox
    }
}
