package dao;

import model.Barang;
import model.Koneksi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BarangDAO {
    private Connection conn = Koneksi.getConnection();

    public boolean insert(Barang b){
        String sql = "INSERT INTO barang (kode_barang, nama_barang, kategori, stok) VALUES (?,?,?,?)";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, b.getKodeBarang());
            ps.setString(2, b.getNamaBarang());
            ps.setString(3, b.getKategori());
            ps.setInt(4, b.getStok());
            return ps.executeUpdate() > 0;
        }catch(SQLException e){ e.printStackTrace(); return false; }
    }

    public boolean update(Barang b){
        String sql = "UPDATE barang SET nama_barang=?, kategori=?, stok=? WHERE kode_barang=?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, b.getNamaBarang());
            ps.setString(2, b.getKategori());
            ps.setInt(3, b.getStok());
            ps.setString(4, b.getKodeBarang());
            return ps.executeUpdate() > 0;
        }catch(SQLException e){ e.printStackTrace(); return false; }
    }

    public boolean delete(String kode){
        String sql = "DELETE FROM barang WHERE kode_barang=?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, kode);
            return ps.executeUpdate() > 0;
        }catch(SQLException e){ e.printStackTrace(); return false; }
    }

    public List<Barang> getAll(){
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT * FROM barang";
        try(Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)){
            while(rs.next()){
                list.add(new Barang(
                    rs.getString("kode_barang"),
                    rs.getString("nama_barang"),
                    rs.getString("kategori"),
                    rs.getInt("stok")
                ));
            }
        }catch(SQLException e){ e.printStackTrace(); }
        return list;
    }
}