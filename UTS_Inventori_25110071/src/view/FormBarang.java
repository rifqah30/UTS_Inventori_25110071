package view;

import dao.BarangDAO;
import model.Barang;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class FormBarang extends JFrame {
    private JTextField txtKode, txtNama, txtStok;
    private JComboBox<String> cbKategori;
    private JButton btnSimpan, btnUbah, btnHapus, btnBersihkan;
    private JTable table;
    private DefaultTableModel modelTable;
    private BarangDAO dao = new BarangDAO();

    public FormBarang(){
        initComponents();
        loadTable();
    }

    private void initComponents(){
        setTitle("Pengelolaan Data Barang Lab");
        setSize(650, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel l1 = new JLabel("Kode Barang"); l1.setBounds(20,20,100,25); add(l1);
        txtKode = new JTextField(); txtKode.setBounds(130,20,150,25); add(txtKode);

        JLabel l2 = new JLabel("Nama Barang"); l2.setBounds(20,55,100,25); add(l2);
        txtNama = new JTextField(); txtNama.setBounds(130,55,150,25); add(txtNama);

        JLabel l3 = new JLabel("Kategori"); l3.setBounds(20,90,100,25); add(l3);
        cbKategori = new JComboBox<>(new String[]{"Komputer","Jaringan","Alat Ukur"});
        cbKategori.setBounds(130,90,150,25); add(cbKategori);

        JLabel l4 = new JLabel("Stok"); l4.setBounds(20,125,100,25); add(l4);
        txtStok = new JTextField(); txtStok.setBounds(130,125,150,25); add(txtStok);

        btnSimpan = new JButton("Simpan"); btnSimpan.setBounds(20,170,80,30); add(btnSimpan);
        btnUbah = new JButton("Ubah"); btnUbah.setBounds(110,170,70,30); add(btnUbah);
        btnHapus = new JButton("Hapus"); btnHapus.setBounds(190,170,70,30); add(btnHapus);
        btnBersihkan = new JButton("Bersihkan"); btnBersihkan.setBounds(270,170,100,30); add(btnBersihkan);

        modelTable = new DefaultTableModel(new String[]{"Kode","Nama Barang","Kategori","Stok"},0);
        table = new JTable(modelTable);
        JScrollPane sp = new JScrollPane(table); sp.setBounds(20,220,600,180); add(sp);

        // Event
        btnSimpan.addActionListener(e -> simpan());
        btnUbah.addActionListener(e -> ubah());
        btnHapus.addActionListener(e -> hapus());
        btnBersihkan.addActionListener(e -> bersihkan());
        table.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent evt){
                int row = table.getSelectedRow();
                if(row >=0){
                    txtKode.setText(modelTable.getValueAt(row, 0).toString());
                    txtNama.setText(modelTable.getValueAt(row, 1).toString());
                    cbKategori.setSelectedItem(modelTable.getValueAt(row, 2).toString());
                    txtStok.setText(modelTable.getValueAt(row, 3).toString());
                }
            }
        });
    }

    // Validasi Skor 20
    private boolean validasi(){
        if(txtKode.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(this, "Kode barang tidak boleh kosong"); return false;
        }
        if(txtNama.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(this, "Nama barang tidak boleh kosong"); return false;
        }
        try{
            int stok = Integer.parseInt(txtStok.getText().trim());
            if(stok < 0){
                JOptionPane.showMessageDialog(this, "Stok harus >= 0"); return false;
            }
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this, "Stok harus angka!"); return false;
        }
        return true;
    }

    private void simpan(){
        if(!validasi()) return;
        Barang b = new Barang(txtKode.getText().trim(), txtNama.getText().trim(), 
                    cbKategori.getSelectedItem().toString(), Integer.parseInt(txtStok.getText().trim()));
        if(dao.insert(b)){
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan");
            loadTable(); bersihkan();
        }else{
            JOptionPane.showMessageDialog(this, "Gagal simpan, kode mungkin sudah ada");
        }
    }

    private void ubah(){
        if(!validasi()) return;
        Barang b = new Barang(txtKode.getText().trim(), txtNama.getText().trim(), 
                    cbKategori.getSelectedItem().toString(), Integer.parseInt(txtStok.getText().trim()));
        if(dao.update(b)){
            JOptionPane.showMessageDialog(this, "Data berhasil diubah");
            loadTable(); bersihkan();
        }else{
            JOptionPane.showMessageDialog(this, "Gagal ubah");
        }
    }

    private void hapus(){
        String kode = txtKode.getText().trim();
        if(kode.isEmpty()){
            JOptionPane.showMessageDialog(this, "Pilih kode yang akan dihapus"); return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus data "+kode+" ?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION){
            if(dao.delete(kode)){
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
                loadTable(); bersihkan();
            }
        }
    }

    private void loadTable(){
        modelTable.setRowCount(0);
        List<Barang> list = dao.getAll();
        for(Barang b : list){
            modelTable.addRow(new Object[]{b.getKodeBarang(), b.getNamaBarang(), b.getKategori(), b.getStok()});
        }
    }

    private void bersihkan(){
        txtKode.setText(""); txtNama.setText(""); txtStok.setText(""); cbKategori.setSelectedIndex(0);
        txtKode.requestFocus();
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new FormBarang().setVisible(true));
    }
}