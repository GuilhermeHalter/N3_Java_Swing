package crud;

import db.ConexaoMySQL;
import model.Passageiro;
import model.Passagem;
import model.Reserva;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaCRUD {
    private Connection conect;

    public ReservaCRUD() {
        this.conect = ConexaoMySQL.getConexaoMySQL(); // Garantindo que a conexão será recuperada aqui
    }

    // <----------CREATE---------->

    public void createReserva(Reserva reserva) {
        String sql = "INSERT INTO reserva (passageiro_id, passagem_id, numero_assento, valor_total) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = conect.prepareStatement(sql)) {
            pst.setInt(1, reserva.getPassageiro().getId()); // Usa o ID do Passageiro
            pst.setInt(2, reserva.getPassagem().getId());   // Usa o ID da Passagem
            pst.setString(3, reserva.getNumeroAssento());   // Número do assento
            pst.setDouble(4, reserva.getValorTotal());      // Valor total da reserva
            pst.executeUpdate();
            System.out.println("Reserva Cadastrada com Sucesso");
        } catch (SQLException e) {
            System.out.println("Erro ao Cadastrar Reserva: " + e.getMessage());
        }
    }

    // <----------READ---------->

    public List<Reserva> readReservas() {
        String sql = "SELECT * FROM reserva";
        List<Reserva> reservas = new ArrayList<>();
        try (PreparedStatement pst = conect.prepareStatement(sql)) {
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                int id = rst.getInt("id"); // Certifique-se de que você está recuperando o ID da reserva
                int passageiroId = rst.getInt("passageiro_id");
                int passagemId = rst.getInt("passagem_id");
                String numeroAssento = rst.getString("numero_assento");
                double valorTotal = rst.getDouble("valor_total");

                // Aqui você precisa recuperar o Passageiro e Passagem com os IDs
                Passageiro passageiro = getPassageiroById(passageiroId);  // Método para buscar Passageiro pelo ID
                Passagem passagem = getPassagemById(passagemId);  // Método para buscar Passagem pelo ID

                // Criando a reserva com os objetos Passageiro e Passagem, incluindo o ID
                Reserva reserva = new Reserva(id, passageiro, passagem, numeroAssento, valorTotal);
                reservas.add(reserva);

                // Imprimindo informações para debug
                System.out.printf("Reserva ID: %d | Passageiro: %s | Passagem ID: %d | Assento: %s | Valor Total: %.2f\n", 
                        id, passageiro.getNome(), passagemId, numeroAssento, valorTotal);
            }
        } catch (SQLException se) {
            System.out.println("Erro ao Consultar Reservas: " + se.getMessage());
        }
        return reservas;
    }

    // Método para buscar Passageiro pelo ID
    private Passageiro getPassageiroById(int id) {
        String sql = "SELECT * FROM passageiro WHERE id = ?";
        Passageiro passageiro = null;  // Inicializa como null caso não encontre

        try (PreparedStatement pst = conect.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rst = pst.executeQuery();
            
            if (rst.next()) {
                // Recupera os dados do passageiro
                String nome = rst.getString("nome");
                String cpf = rst.getString("cpf");
                String email = rst.getString("email");
                String telefone = rst.getString("telefone");

                // Cria o objeto Passageiro
                passageiro = new Passageiro(id, nome, cpf, email, telefone);
            }
        } catch (SQLException se) {
            System.out.println("Erro ao Consultar Passageiro: " + se.getMessage());
        }

        return passageiro;  // Retorna o objeto Passageiro ou null caso não encontre
    }

    // Método para buscar Passagem pelo ID
    private Passagem getPassagemById(int id) {
        String sql = "SELECT * FROM passagem WHERE id = ?";
        Passagem passagem = null;  // Inicializa como null caso não encontre

        try (PreparedStatement pst = conect.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rst = pst.executeQuery();

            if (rst.next()) {
                // Recupera os dados da passagem
                String numeroVoo = rst.getString("numero_voo");
                String origem = rst.getString("origem");
                String destino = rst.getString("destino");
                
                // Converte a data e hora de partida de String ou Timestamp para Date
                Timestamp timestamp = rst.getTimestamp("data_hora_partida");
                java.util.Date dataHoraPartida = null;
                if (timestamp != null) {
                    dataHoraPartida = new java.util.Date(timestamp.getTime());  // Converte para Date
                }

                String duracao = rst.getString("duracao");
                double preco = rst.getDouble("preco");
                int assentosDisponiveis = rst.getInt("assentos_disponiveis");

                // Cria o objeto Passagem
                passagem = new Passagem(id, numeroVoo, origem, destino, dataHoraPartida, duracao, preco, assentosDisponiveis);
            }
        } catch (SQLException se) {
            System.out.println("Erro ao Consultar Passagem: " + se.getMessage());
        }

        return passagem;  // Retorna o objeto Passagem ou null caso não encontre
    }

    // <----------UPDATE---------->

    public void updateReserva(Reserva reserva) {
        if (reserva.getId() <= 0) {
            System.out.println("ID da reserva inválido: " + reserva.getId());
            return;
        }

        String sql = "UPDATE reserva SET passageiro_id = ?, passagem_id = ?, numero_assento = ?, valor_total = ? WHERE id = ?";
        try (PreparedStatement pst = conect.prepareStatement(sql)) {
            pst.setInt(1, reserva.getPassageiro().getId());
            pst.setInt(2, reserva.getPassagem().getId());
            pst.setString(3, reserva.getNumeroAssento());
            pst.setDouble(4, reserva.getValorTotal());
            pst.setInt(5, reserva.getId());

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Reserva atualizada com sucesso.");
            } else {
                System.out.println("Nenhuma reserva foi atualizada.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar reserva: " + e.getMessage());
        }
    }
    // <----------DELETE---------->

    public boolean deleteReserva(int id) {
        String sql = "DELETE FROM reserva WHERE id = ?";
        try (PreparedStatement pst = conect.prepareStatement(sql)) {
            pst.setInt(1, id);
            int ret = pst.executeUpdate();
            if (ret > 0) {
                System.out.println("Reserva Excluída com Sucesso");
                return true; // Retorna true se a exclusão for bem-sucedida
            } else {
                System.out.println("Não foi possível Excluir a Reserva");
                return false; // Retorna false se a exclusão falhar
            }
        } catch (SQLException se) {
            System.out.println("Erro ao Excluir Reserva: " + se.getMessage());
            return false; // Retorna false em caso de erro
        }
    }
}
