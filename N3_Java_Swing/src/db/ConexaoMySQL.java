package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySQL {
    private static Connection conect = null;  // Alterei para que a conexão seja estática e reutilizável.

    public static Connection getConexaoMySQL() {
        if (conect == null) {
            try {
                String driverName = "com.mysql.cj.jdbc.Driver";
                Class.forName(driverName);

                String serverName = "localhost";  // Endereço do servidor do BD
                String mydatabase = "vendapassagemdb";  // Nome do seu banco de dados
                String url = "jdbc:mysql://" + serverName + "/" + mydatabase;  // String de Conexão.
                String username = "root";  // Nome do usuário
                String password = "";  // Senha do banco

                conect = DriverManager.getConnection(url, username, password);
                System.out.println("Banco conectado com sucesso.");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return conect;
    }
}
