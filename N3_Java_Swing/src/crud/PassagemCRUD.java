package crud;

import db.ConexaoMySQL;
import model.Passagem;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PassagemCRUD {
    Connection conect = null;

    public PassagemCRUD() {
        connectionDB();
    }

    public void connectionDB() {
        conect = ConexaoMySQL.getConexaoMySQL();
    }

    // <----------CREATE---------->

    public void createPassagem(Passagem p) {
        String sql = "INSERT INTO passagem (numero_voo, origem, destino, data_hora_partida, duracao, preco, assentos_disponiveis) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement pst = conect.prepareStatement(sql)) {
            pst.setString(1, p.getNumeroVoo());
            pst.setString(2, p.getOrigem());
            pst.setString(3, p.getDestino());
            
            Timestamp timestamp = new Timestamp(p.getDataHoraPartida().getTime());
            pst.setTimestamp(4, timestamp);
            
            pst.setString(5, p.getDuracao());
            
            BigDecimal preco = BigDecimal.valueOf(p.getPreco());
            pst.setBigDecimal(6, preco);
            
            pst.setInt(7, p.getAssentosDisponiveis());
            pst.executeUpdate();
            System.out.println("Passagem Cadastrada");
        } catch (SQLException ex) {
            System.out.println("Erro ao Cadastrar Passagem " + ex);
        }
    }

    // <----------READ---------->

    public List<Passagem> readPassagens() {
        String sql = "SELECT * FROM passagem";  // Consulta SQL para pegar todas as passagens
        List<Passagem> passagens = new ArrayList<>();
        
        try {
            // Verifique se a conexão está inicializada corretamente
            if (conect != null) {
                PreparedStatement pst = conect.prepareStatement(sql);
                ResultSet rst = pst.executeQuery();

                while (rst.next()) {
                    // Pegando os dados de cada linha da tabela
                    int id = rst.getInt("id");
                    String numeroVoo = rst.getString("numero_voo");
                    String origem = rst.getString("origem");
                    String destino = rst.getString("destino");
                    Timestamp dataHoraPartida = rst.getTimestamp("data_hora_partida");
                    String duracao = rst.getString("duracao");
                    float preco = rst.getBigDecimal("preco").floatValue();
                    int assentosDisponiveis = rst.getInt("assentos_disponiveis");

                    // Criando o objeto Passagem
                    Passagem passagem = new Passagem(id, numeroVoo, origem, destino, dataHoraPartida, duracao, preco, assentosDisponiveis);

                    // Adicionando a passagem na lista
                    passagens.add(passagem);
                    
                    // Exibindo dados para verificar se os dados foram lidos corretamente
                    System.out.println("Número do Voo: " + numeroVoo + " | Origem: " + origem + " | Destino: " + destino);
                }
            } else {
                System.out.println("Erro: Conexão com o banco não foi estabelecida.");
            }
        } catch (SQLException se) {
            System.out.println("Erro ao consultar passagens: " + se);
        }
        
        return passagens;
    }

    // <----------UPDATE---------->

    public void updatePassagem(String numeroVoo, String origem, String destino, java.util.Date dataHoraPartida, 
            String duracao, double preco, int assentosDisponiveis, int id) {
    String sql = "UPDATE passagem SET numero_voo = ?, origem = ?, destino = ?, data_hora_partida = ?, duracao = ?, preco = ?, assentos_disponiveis = ? WHERE id = ?";
    
    try (PreparedStatement pst = conect.prepareStatement(sql)) {
        // Set parameters in the prepared statement
        pst.setString(1, numeroVoo);               // Set numero_voo
        pst.setString(2, origem);                   // Set origem
        pst.setString(3, destino);                  // Set destino

        // Converte java.util.Date para java.sql.Date
        java.sql.Date sqlDate = new java.sql.Date(dataHoraPartida.getTime());
        pst.setDate(4, sqlDate);                    // Set data_hora_partida

        pst.setString(5, duracao);                  // Set duracao
        pst.setDouble(6, preco);                    // Set preco (using setDouble for precision)
        pst.setInt(7, assentosDisponiveis);         // Set assentos_disponiveis
        pst.setInt(8, id);                          // Set where clause using id

        // Execute the update
        int ret = pst.executeUpdate();
        if (ret > 0) {
            System.out.println("Passagem atualizada");
        } else {
            System.out.println("Não foi possível atualizar a passagem");
        }
    } catch (SQLException se) {
        System.out.println("Erro ao Atualizar Passagem " + se);
    }
}


    // <----------DELETE---------->

    public boolean deletePassagem(String numeroVoo) {
        String sql = "DELETE FROM passagem WHERE numero_voo = ?";
        try (PreparedStatement pst = conect.prepareStatement(sql)) {
            pst.setString(1, numeroVoo);
            int ret = pst.executeUpdate();
            if (ret > 0) {
                System.out.printf("Passagem Excluída: %s\n", numeroVoo);
                return true;  // Retorna true se a exclusão for bem-sucedida
            } else {
                System.out.println("Não foi possível Excluir a Passagem");
                return false;  // Retorna false se a exclusão falhar
            }
        } catch (SQLException se) {
            System.out.println("Erro ao Excluir Passagem " + se);
            return false;  // Retorna false em caso de erro
        }
    }

}
