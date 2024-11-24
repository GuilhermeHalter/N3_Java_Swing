package model;

import java.util.Date;

public class Passagem {
    private int id;
    private String numeroVoo;
    private String origem;
    private String destino;
    private Date dataHoraPartida;
    private String duracao;
    private double preco;
    private int assentosDisponiveis;

    
    // Construtor
    public Passagem(String numeroVoo, String origem, String destino, Date dataHoraPartida, 
            String duracao, double preco, int assentosDisponiveis) {
    		this.numeroVoo = numeroVoo;
    		this.origem = origem;
    		this.destino = destino;
    		this.dataHoraPartida = dataHoraPartida;
    		this.duracao = duracao;
    		this.preco = preco;
    		this.assentosDisponiveis = assentosDisponiveis;
    }
    
    public Passagem(int id, String numeroVoo, String origem, String destino, Date dataHoraPartida, 
            String duracao, double preco, int assentosDisponiveis) {
    		this.id = id;
    		this.numeroVoo = numeroVoo;
    		this.origem = origem;
    		this.destino = destino;
    		this.dataHoraPartida = dataHoraPartida;
    		this.duracao = duracao;
    		this.preco = preco;
    		this.assentosDisponiveis = assentosDisponiveis;
    }

	// Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroVoo() {
        return numeroVoo;
    }

    public void setNumeroVoo(String numeroVoo) {
        this.numeroVoo = numeroVoo;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Date getDataHoraPartida() {
        return dataHoraPartida;
    }

    public void setDataHoraPartida(Date dataHoraPartida) {
        this.dataHoraPartida = dataHoraPartida;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getAssentosDisponiveis() {
        return assentosDisponiveis;
    }

    public void setAssentosDisponiveis(int assentosDisponiveis) {
        this.assentosDisponiveis = assentosDisponiveis;
    }

    // Métodos
    public void exibirDetalhes() {
        System.out.println("Número do Voo: " + numeroVoo);
        System.out.println("Origem: " + origem);
        System.out.println("Destino: " + destino);
        System.out.println("Data e Hora de Partida: " + dataHoraPartida);
        System.out.println("Duração: " + duracao);
        System.out.println("Preço: R$ " + preco);
        System.out.println("Assentos Disponíveis: " + assentosDisponiveis);
    }

    public void reservarAssento() {
        if (assentosDisponiveis > 0) {
            assentosDisponiveis--;
            System.out.println("Assento reservado com sucesso!");
        } else {
            System.out.println("Não há assentos disponíveis.");
        }
    }
}
