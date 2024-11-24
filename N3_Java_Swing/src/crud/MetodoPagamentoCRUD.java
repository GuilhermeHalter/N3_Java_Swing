package crud;

import db.ConexaoMySQL;
import model.MetodoPagamento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MetodoPagamentoCRUD {

    private Connection conect = null;

    public MetodoPagamentoCRUD() {
        connectionDB();
    }

    public void connectionDB() {
        conect = ConexaoMySQL.getConexaoMySQL();
    }

    // <----------CREATE---------->

    public void createMetodoPagamento(MetodoPagamento metodo) {
        String sql = "INSERT INTO metodo_pagamento (nome) VALUES (?)";
        try (PreparedStatement pst = conect.prepareStatement(sql)) {
            pst.setString(1, metodo.getNome());
            pst.executeUpdate();
            System.out.println("Método de Pagamento Cadastrado");
        } catch (SQLException ex) {
            System.out.println("Erro ao Cadastrar Método de Pagamento: " + ex.getMessage());
        }
    }

    // <----------READ---------->

    public List<MetodoPagamento> readMetodosPagamento() {
        String sql = "SELECT * FROM metodo_pagamento";
        List<MetodoPagamento> metodosPagamento = new ArrayList<>();

        try {
            if (conect != null) {
                PreparedStatement pst = conect.prepareStatement(sql);
                ResultSet rst = pst.executeQuery();

                while (rst.next()) {
                    int id = rst.getInt("id");
                    String nome = rst.getString("nome");

                    MetodoPagamento metodo = new MetodoPagamento(id, nome);
                    metodosPagamento.add(metodo);

                    // Exibindo dados para verificar se os dados foram lidos corretamente
                    System.out.println("ID: " + id + " | Nome: " + nome);
                }
            } else {
                System.out.println("Erro: Conexão com o banco não foi estabelecida.");
            }
        } catch (SQLException se) {
            System.out.println("Erro ao consultar métodos de pagamento: " + se);
        }

        return metodosPagamento;
    }

    // <----------UPDATE---------->

    public void updateMetodoPagamento(int id, String nome) {
        String sql = "UPDATE metodo_pagamento SET nome = ? WHERE id = ?";
        try (PreparedStatement pst = conect.prepareStatement(sql)) {
            pst.setString(1, nome);
            pst.setInt(2, id);

            int ret = pst.executeUpdate();
            if (ret > 0) {
                System.out.println("Método de Pagamento Atualizado");
            } else {
                System.out.println("Não foi possível atualizar o método de pagamento");
            }
        } catch (SQLException se) {
            System.out.println("Erro ao Atualizar Método de Pagamento: " + se);
        }
    }

    // <----------DELETE---------->

    public boolean deleteMetodoPagamento(int id) {
        String sql = "DELETE FROM metodo_pagamento WHERE id = ?";
        try (PreparedStatement pst = conect.prepareStatement(sql)) {
            pst.setInt(1, id);
            int ret = pst.executeUpdate();
            if (ret > 0) {
                System.out.println("Método de Pagamento Excluído");
                return true;
            } else {
                System.out.println("Não foi possível Excluir o Método de Pagamento");
                return false;
            }
        } catch (SQLException se) {
            System.out.println("Erro ao Excluir Método de Pagamento: " + se);
            return false;
        }
    }

    public MetodoPagamento readMetodoPagamentoById(int metodoPagamentoId) {
        String sql = "SELECT * FROM metodo_pagamento WHERE id = ?";
        MetodoPagamento metodoPagamento = null;

        try (PreparedStatement pst = conect.prepareStatement(sql)) {
            pst.setInt(1, metodoPagamentoId);
            ResultSet rst = pst.executeQuery();

            if (rst.next()) {
                int id = rst.getInt("id");
                String nome = rst.getString("nome");

                metodoPagamento = new MetodoPagamento(id, nome);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar método de pagamento por ID: " + e.getMessage());
        }

        return metodoPagamento;
    }
}
