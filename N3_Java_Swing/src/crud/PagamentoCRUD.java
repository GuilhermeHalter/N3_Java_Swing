package crud;

import db.ConexaoMySQL;
import model.Pagamento;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagamentoCRUD {
    private Connection conect = null;

    // Método para estabelecer conexão com o banco
    public void connectionDB() {
        conect = ConexaoMySQL.getConexaoMySQL();
        if (conect == null) {
            System.out.println("Erro ao conectar ao banco de dados.");
        }
    }

    // <----------CREATE---------->
    public void createPagamento(Pagamento p) {
        connectionDB(); // Assegura que a conexão esteja estabelecida
        String sql = "INSERT INTO pagamento (reserva_id, metodo_pagamento_id, valor_pago, data_pagamento) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pst = conect.prepareStatement(sql)) {
            pst.setInt(1, p.getReservaId()); // ID da reserva
            pst.setInt(2, p.getMetodoPagamentoId()); // ID do método de pagamento
            pst.setBigDecimal(3, BigDecimal.valueOf(p.getValorPago())); // Valor pago
            pst.setTimestamp(4, new java.sql.Timestamp(p.getDataPagamento().getTime())); // Data do pagamento

            int affectedRows = pst.executeUpdate();
            
            if (affectedRows > 0) {
                System.out.println("Pagamento cadastrado com sucesso!");
            } else {
                System.out.println("Nenhuma linha foi afetada, verifique os dados.");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao cadastrar pagamento: " + ex.getMessage());
        }
    }



    // <----------READ---------->
 // Método para ler os pagamentos
    public List<Pagamento> readPagamentos() {
        connectionDB(); // Assegura que a conexão esteja estabelecida
        List<Pagamento> pagamentos = new ArrayList<>();
        String sql = "SELECT * FROM pagamento"; // Você pode ajustar conforme os dados que você precisa

        try (PreparedStatement pst = conect.prepareStatement(sql);
             ResultSet rst = pst.executeQuery()) {

            // Verifica se o ResultSet contém dados
            while (rst.next()) {
                // Criar o objeto Pagamento a partir dos dados da consulta
                Pagamento pagamento = new Pagamento(0, 0, 0, null);
                pagamento.setId(rst.getInt("id"));  // Ajuste conforme o seu banco
                pagamento.setReservaId(rst.getInt("reserva_id"));
                pagamento.setMetodoPagamentoId(rst.getInt("metodo_pagamento_id"));
                pagamento.setValorPago(rst.getDouble("valor_pago"));
                pagamento.setDataPagamento(rst.getTimestamp("data_pagamento"));

                pagamentos.add(pagamento);
            }
        } catch (SQLException se) {
            System.out.println("Erro ao consultar pagamentos: " + se);
        }

        return pagamentos;
    }



    // <----------UPDATE---------->
    public void updatePagamento(int pagamentoId, String metodoPagamento, float valorPago) {
        connectionDB(); // Assegura que a conexão esteja estabelecida
        String sql = "UPDATE pagamento SET metodo_pagamento = ?, valor_pago = ? WHERE id = ?";
        
        try (PreparedStatement pst = conect.prepareStatement(sql)) {
            pst.setString(1, metodoPagamento);
            pst.setFloat(2, valorPago);
            pst.setInt(3, pagamentoId);
            int ret = pst.executeUpdate();
            if (ret > 0) {
                System.out.println("Pagamento Atualizado");
            } else {
                System.out.println("Não foi possível Atualizar o Pagamento");
            }
        } catch (SQLException se) {
            System.out.println("Erro ao Atualizar Pagamento " + se);
        }
    }

    // <----------DELETE---------->
    public void deletePagamento(int pagamentoId) {
        connectionDB(); // Assegura que a conexão esteja estabelecida
        String sql = "DELETE FROM pagamento WHERE id = ?";
        
        try (PreparedStatement pst = conect.prepareStatement(sql)) {
            pst.setInt(1, pagamentoId);
            int ret = pst.executeUpdate();
            if (ret > 0) {
                System.out.println("Pagamento Excluído");
            } else {
                System.out.println("Não foi possível Excluir o Pagamento");
            }
        } catch (SQLException se) {
            System.out.println("Erro ao Excluir Pagamento " + se);
        }
    }
    
    // Método para obter todas as reservas (substitua conforme a tabela de reservas)
    public List<String> getReservas() {
        connectionDB();
        List<String> reservas = new ArrayList<>();
        String sql = "SELECT id FROM reserva";  // Ajuste o SQL conforme sua tabela de reservas
        try (PreparedStatement pst = conect.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                reservas.add(String.valueOf(rs.getInt("id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }

    // Método para obter os métodos de pagamento
    public List<String> getMetodosPagamento() {
        connectionDB();
        List<String> metodosPagamento = new ArrayList<>();
        String sql = "SELECT metodo_pagamento FROM pagamento GROUP BY metodo_pagamento";  // Ajuste conforme necessário
        try (PreparedStatement pst = conect.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                metodosPagamento.add(rs.getString("metodo_pagamento"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return metodosPagamento;
    }
}
