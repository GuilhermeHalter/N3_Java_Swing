package model;

public class Reserva {
    private int id;
    private Passageiro passageiro;
    private Passagem passagem;
    private String numeroAssento;
    private double valorTotal;
    
    public Reserva(int id, Passageiro passageiro, Passagem passagem, String numeroAssento, double valorTotal) {
        this.id = id;
    	this.passageiro = passageiro;
        this.passagem = passagem;
        this.numeroAssento = numeroAssento;
        this.valorTotal = valorTotal;
    }

    // Construtor
    public Reserva(Passageiro passageiro, Passagem passagem, String numeroAssento, double valorTotal) {
        this.passageiro = passageiro;
        this.passagem = passagem;
        this.numeroAssento = numeroAssento;
        this.valorTotal = valorTotal;
    }
    
    

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Passageiro getPassageiro() {
        return passageiro;
    }

    public void setPassageiro(Passageiro passageiro) {
        this.passageiro = passageiro;
    }

    public Passagem getPassagem() {
        return passagem;
    }

    public void setPassagem(Passagem passagem) {
        this.passagem = passagem;
    }

    public String getNumeroAssento() {
        return numeroAssento;
    }

    public void setNumeroAssento(String numeroAssento) {
        this.numeroAssento = numeroAssento;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    // Métodos
    
    @Override
    public String toString() {
        return "ID: " + id + ", Passageiro: " + passageiro.getNome() + ", Assento: " + numeroAssento;
    }

    
    public void confirmarReserva() {
        passagem.reservarAssento();
        System.out.println("Reserva confirmada para o passageiro: " + passageiro.getNome());
    }

    public void exibirReserva() {
        System.out.println("Reserva ID: " + id);
        passageiro.exibirDetalhes();
        passagem.exibirDetalhes();
        System.out.println("Número do Assento: " + numeroAssento);
        System.out.println("Valor Total: R$ " + valorTotal);
    }
}
