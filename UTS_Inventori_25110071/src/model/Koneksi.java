package model;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class Koneksi {
    private static Connection conn;
    public static Connection getConnection(){
        if(conn == null){
            try {
                String url = "jdbc:mysql://localhost:3306/db_inventori";
                String user = "root";
                String pass = ""; // ganti jika ada password
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(url, user, pass);
                System.out.println("Koneksi berhasil");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Koneksi Gagal: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return conn;
    }
}