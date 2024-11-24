package crud;

import db.ConexaoMySQL;
import model.Passageiro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class PassageiroCRUD {
    private Connection conect;

    public PassageiroCRUD() {
        this.conect = ConexaoMySQL.getConexaoMySQL();  // Garantindo que a conexão será recuperada aqui
    }

    public void createPassageiro(Passageiro passageiro) {
        String sql = "INSERT INTO passageiro (nome, cpf, email, telefone) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pst = conect.prepareStatement(sql)) {
            pst.setString(1, passageiro.getNome());
            pst.setString(2, passageiro.getCpf());
            pst.setString(3, passageiro.getEmail());
            pst.setString(4, passageiro.getTelefone());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // <----------READ---------->

    public List<Passageiro> readPassageiros() {
        String sql = "SELECT * FROM passageiro";
        List<Passageiro> passageiros = new ArrayList<>();
        try {
            PreparedStatement pst = conect.prepareStatement(sql);
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                int id = rst.getInt("id");
                String nome = rst.getString("nome");
                String cpf = rst.getString("cpf");
                String email = rst.getString("email");
                String telefone = rst.getString("telefone");

                // Verificando o valor do ID
                System.out.println("ID: " + id + " | Nome: " + nome);

                Passageiro passageiro = new Passageiro(id, nome, cpf, email, telefone);
                passageiros.add(passageiro);
            }
        } catch (SQLException se) {
            System.out.println("Erro ao Consultar Passageiro " + se);
        }
        return passageiros;
    }


    // <----------UPDATE---------->

    public void updatePassageiro(String nome, String cpf, String email, String telefone, int id) {
        String sql = "UPDATE passageiro SET nome = ?, cpf = ?, email = ?, telefone = ? WHERE id = ?";
        try {
            PreparedStatement pst = conect.prepareStatement(sql);
            pst.setString(1, nome);
            pst.setString(2, cpf);
            pst.setString(3, email);
            pst.setString(4, telefone);
            pst.setInt(5, id);
            int ret = pst.executeUpdate();
            if (ret > 0) {
                System.out.println("Passageiro atualizado");
            } else {
                System.out.println("Não foi possível atualizar o Passageiro");
            }
        } catch (SQLException se) {
            System.out.println("Erro ao Atualizar Passageiro " + se);
        }
    }

    // <----------DELETE---------->


    public boolean deletePassageiro(int id) {
        String sql = "DELETE FROM passageiro WHERE id = ?";
        try (PreparedStatement pst = conect.prepareStatement(sql)) {
            pst.setInt(1, id);
            int ret = pst.executeUpdate();
            if (ret > 0) {
                System.out.printf("Passageiro Excluído: %d\n", id);
                return true;  // Retorna true se a exclusão for bem-sucedida
            } else {
                System.out.println("Não foi possível Excluir o Passageiro");
                return false;  // Retorna false se a exclusão falhar
            }
        } catch (SQLException se) {
            System.out.println("Erro ao Excluir Passageiro " + se);
            return false;  // Retorna false em caso de erro
        }
    }
}

