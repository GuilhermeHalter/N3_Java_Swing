package model;

import java.util.Date;

public class Pagamento {
    private int id;
    private int reservaId; // ID da reserva associada ao pagamento
    private int metodoPagamentoId; // ID do método de pagamento
    private double valorPago;
    private Date dataPagamento;

    // Construtor
    public Pagamento(int reservaId, int metodoPagamentoId, double valorPago, Date dataPagamento) {
        this.reservaId = reservaId;
        this.metodoPagamentoId = metodoPagamentoId;
        this.valorPago = valorPago;
        this.dataPagamento = dataPagamento;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReservaId() {
        return reservaId;
    }

    public void setReservaId(int reservaId) {
        this.reservaId = reservaId;
    }

    public int getMetodoPagamentoId() {
        return metodoPagamentoId;
    }

    public void setMetodoPagamentoId(int metodoPagamentoId) {
        this.metodoPagamentoId = metodoPagamentoId;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
}

