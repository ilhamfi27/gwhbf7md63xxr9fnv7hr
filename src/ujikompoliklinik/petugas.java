/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ujikompoliklinik;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author ilhamfi
 */
public class petugas extends javax.swing.JFrame {
    
    Timestamp time = new CurrentTimestamp().CurrentTime();
    Connection koneksi = new koneksi().conn();
    PreparedStatement ps;
    ResultSet rs;
    DefaultTableModel ambilDataPasienUntukResep;
    Integer total_harga = 0, uang_bayar, kembalian = 0, id_resep = 0, id_pasien;
    public void dataPoliklinik(){
        try{
            cb_klinik.removeAll();
            koneksi = new koneksi().conn();
            String query = "select name from polyclinics";
            Statement s = koneksi.createStatement();
            ResultSet r = s.executeQuery(query);
            while (r.next()){
                cb_klinik.addItem(r.getString("name"));
            }
        }catch(SQLException ex){
            System.out.println("Error" + ex.getMessage());
        }
    }

    public void dataDokter(){
        try{
            cb_dokter.removeAll();
            koneksi = new koneksi().conn();
            String query = "select name from doctors";
            Statement s = koneksi.createStatement();
            ResultSet r = s.executeQuery(query);
            while (r.next()){
                cb_dokter.addItem(r.getString("name"));
            }
        }catch(SQLException ex){
            System.out.println("Error" + ex.getMessage());
        }
    }

    public void dataPasien(){
        try{
            cb_pasien.removeAll();
            koneksi = new koneksi().conn();
            String query = "select name from patients";
            Statement s = koneksi.createStatement();
            ResultSet r = s.executeQuery(query);
            while (r.next()){
                cb_pasien.addItem(r.getString("name"));
            }
        }catch(SQLException ex){
            System.out.println("Error" + ex.getMessage());
        }
    }

    public void dataObat(){
        try{
            cb_obat.removeAll();
            koneksi = new koneksi().conn();
            String query = "select name from medicines";
            Statement s = koneksi.createStatement();
            ResultSet r = s.executeQuery(query);
            while (r.next()){
                cb_obat.addItem(r.getString("name"));
            }
        }catch(SQLException ex){
            System.out.println("Error" + ex.getMessage());
        }
    }
    
    public void resetPendaftaran(){
        biaya.setText("");
        keterangan.setText("");
        nama_pasien.setText("");
        alamat_pasien.setText("");
        umur_pasien.setText("");
        telepon_pasien.setText("");
        jenis_kelamin_pasien.clearSelection();
        pasien_baru.setSelected(false);
    }
    
    public Integer idDokter(String name){
        String dokter = null;
        try {
            String query = "SELECT id FROM doctors where name like '%" + name + "%'";
            com.mysql.jdbc.Statement s = (com.mysql.jdbc.Statement) koneksi.createStatement();
            rs = s.executeQuery(query);
            while (rs.next()) {
                dokter = rs.getString("id");
            }
        } catch (SQLException ex) {
            System.out.println("Error!" + ex.getMessage());
        }
        return Integer.parseInt(dokter);
    }
    
    public Integer idPoliklinik(String name){
        String poliklinik = null;
        try {
            String query = "SELECT id FROM polyclinics where name like '%" + name + "%'";
            com.mysql.jdbc.Statement s = (com.mysql.jdbc.Statement) koneksi.createStatement();
            rs = s.executeQuery(query);
            while (rs.next()) {
                poliklinik = rs.getString("id");
            }
        } catch (SQLException ex) {
            System.out.println("Error!" + ex.getMessage());
        }
        return Integer.parseInt(poliklinik);
    }
    
    public Integer idPasien(String name){
        String pasien = null;
        try {
            String query = "SELECT id FROM patients where name like '%" + name + "%'";
            com.mysql.jdbc.Statement s = (com.mysql.jdbc.Statement) koneksi.createStatement();
            rs = s.executeQuery(query);
            while (rs.next()) {
                pasien = rs.getString("id");
            }
        } catch (SQLException ex) {
            System.out.println("Error!" + ex.getMessage());
        }
        return Integer.parseInt(pasien);
    }
    
    public Integer idObat(String name){
        String obat = null;
        try {
            String query = "SELECT id FROM medicines where name like '%" + name + "%'";
            com.mysql.jdbc.Statement s = (com.mysql.jdbc.Statement) koneksi.createStatement();
            rs = s.executeQuery(query);
            while (rs.next()) {
                obat = rs.getString("id");
            }
        } catch (SQLException ex) {
            System.out.println("Error!" + ex.getMessage());
        }
        return Integer.parseInt(obat);
    }
    
    public Integer hargaObat(Integer id){
        Integer harga = 0;
        try {
            String query = "SELECT price FROM medicines where id = " + id;
            com.mysql.jdbc.Statement s = (com.mysql.jdbc.Statement) koneksi.createStatement();
            rs = s.executeQuery(query);
            while (rs.next()) {
                harga = Integer.parseInt(rs.getString("price"));
            }
        } catch (SQLException ex) {
            System.out.println("Error!" + ex.getMessage());
        }
        return harga;
    }
    
    public void formPasienBaru(){
            nama_pasien.setEnabled(false);
            alamat_pasien.setEnabled(false);
            rb_pria.setEnabled(false);
            rb_wanita.setEnabled(false);
            umur_pasien.setEnabled(false);
            telepon_pasien.setEnabled(false); 
    }
    
    public void dataPasienUntukResep() throws SQLException {
        try {
            Object[] row = {"ID", "Nama"};
            ambilDataPasienUntukResep = new DefaultTableModel(null, row);
            data_pasien_untuk_resep.setModel(ambilDataPasienUntukResep);

            koneksi = new koneksi().conn();
            String query = "select p.id as id, p.name as name from registrations r " +
                         "inner join patients p on r.patient_id = p.id " +
                         "inner join doctors d on r.doctor_id = d.id " +
                         "inner join polyclinics pl on r.polyclinic_id = pl.id";
            com.mysql.jdbc.Statement s = (com.mysql.jdbc.Statement) koneksi.createStatement();
            ResultSet r = s.executeQuery(query);

            while (r.next()) {
                String a = r.getString("id");
                String b = r.getString("name");
                String[] data = {a, b};
                ambilDataPasienUntukResep.addRow(data);
            }
        } catch (SQLException ex) {
            System.out.println("Error!" + ex.getMessage());
        }
    }
        
    public petugas() throws SQLException {
        initComponents();
        dataPoliklinik();
        dataDokter();
        dataPasien();
        dataObat();
        formPasienBaru();
        dataPasienUntukResep();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jenis_kelamin_pasien = new javax.swing.ButtonGroup();
        jPanel4 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cb_klinik = new javax.swing.JComboBox<>();
        cb_dokter = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        biaya = new javax.swing.JTextField();
        cb_pasien = new javax.swing.JComboBox<>();
        pasien_baru = new javax.swing.JCheckBox();
        nama_pasien = new javax.swing.JTextField();
        umur_pasien = new javax.swing.JTextField();
        rb_pria = new javax.swing.JRadioButton();
        rb_wanita = new javax.swing.JRadioButton();
        telepon_pasien = new javax.swing.JTextField();
        btn_daftar = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        keterangan = new javax.swing.JTextArea();
        jScrollPane6 = new javax.swing.JScrollPane();
        alamat_pasien = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        data_pasien_untuk_resep = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        cari_pasien = new javax.swing.JTextField();
        btn_cari_pasien = new javax.swing.JButton();
        tambah_obat_di_resep = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        dosis_obat = new javax.swing.JTextArea();
        jLabel18 = new javax.swing.JLabel();
        simpan_resep = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jumlah_obat = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        cb_obat = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        tambah_resep = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        set_nama_pasien = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        set_nama_klinik = new javax.swing.JLabel();
        set_nama_dokter = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        lbl_total_harga = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        lbl_kembalian = new javax.swing.JLabel();
        bayar = new javax.swing.JTextField();
        btn_bayar = new javax.swing.JButton();
        selesai = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ujikom - Poliklinik");

        jPanel4.setBackground(new java.awt.Color(0, 204, 255));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("SISTEM MANAJEMEN RUMAH SAKIT");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("IBNU SINA");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(325, 325, 325)
                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(357, 357, 357))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(292, 292, 292)
                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(293, 293, 293))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(51, 102, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 520));

        jPanel5.setBackground(new java.awt.Color(155, 155, 155));

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel1.setText("Poliklinik");

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel2.setText("Dokter");

        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel3.setText("Keterangan");

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel4.setText("Pasien");

        jLabel5.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel5.setText("Nama");

        jLabel6.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel6.setText("Alamat");

        jLabel7.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel7.setText("Jenis Kelamin");

        jLabel8.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel8.setText("Telepon");

        jLabel9.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel9.setText("Umur");

        jLabel10.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel10.setText("Biaya");

        pasien_baru.setText("Pasien Baru");
        pasien_baru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasien_baruActionPerformed(evt);
            }
        });

        jenis_kelamin_pasien.add(rb_pria);
        rb_pria.setText("Pria");

        jenis_kelamin_pasien.add(rb_wanita);
        rb_wanita.setText("Wanita");

        btn_daftar.setText("Daftar");
        btn_daftar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_daftarMouseClicked(evt);
            }
        });

        keterangan.setColumns(20);
        keterangan.setRows(5);
        jScrollPane5.setViewportView(keterangan);

        alamat_pasien.setColumns(20);
        alamat_pasien.setRows(5);
        jScrollPane6.setViewportView(alamat_pasien);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel10))
                                .addGap(42, 42, 42)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(pasien_baru)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(cb_dokter, javax.swing.GroupLayout.Alignment.LEADING, 0, 220, Short.MAX_VALUE)
                                        .addComponent(cb_klinik, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cb_pasien, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(biaya, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(umur_pasien, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                                .addComponent(rb_pria)
                                .addGap(18, 18, 18)
                                .addComponent(rb_wanita)
                                .addGap(81, 81, 81))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(telepon_pasien, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(nama_pasien, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_daftar, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(cb_klinik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cb_dokter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cb_pasien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pasien_baru))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(nama_pasien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(rb_pria)
                        .addComponent(rb_wanita))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(biaya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(umur_pasien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(telepon_pasien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 181, Short.MAX_VALUE)
                .addComponent(btn_daftar)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Pendaftaran", jPanel2);

        jPanel6.setBackground(new java.awt.Color(155, 155, 155));

        data_pasien_untuk_resep.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "Nama"
            }
        ));
        data_pasien_untuk_resep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                data_pasien_untuk_resepMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(data_pasien_untuk_resep);
        if (data_pasien_untuk_resep.getColumnModel().getColumnCount() > 0) {
            data_pasien_untuk_resep.getColumnModel().getColumn(0).setPreferredWidth(50);
            data_pasien_untuk_resep.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cari Data Pasien", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 14))); // NOI18N

        btn_cari_pasien.setText("Cari");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cari_pasien)
                .addContainerGap())
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(btn_cari_pasien, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cari_pasien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(btn_cari_pasien)
                .addContainerGap())
        );

        tambah_obat_di_resep.setName(""); // NOI18N

        dosis_obat.setColumns(15);
        dosis_obat.setRows(5);
        jScrollPane4.setViewportView(dosis_obat);

        jLabel18.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel18.setText("Dosis");

        simpan_resep.setText("Tambahkan");
        simpan_resep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                simpan_resepMouseClicked(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel19.setText("Jumlah Obat");

        jLabel20.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel20.setText("Buah");

        jLabel15.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel15.setText("Obat");

        javax.swing.GroupLayout tambah_obat_di_resepLayout = new javax.swing.GroupLayout(tambah_obat_di_resep);
        tambah_obat_di_resep.setLayout(tambah_obat_di_resepLayout);
        tambah_obat_di_resepLayout.setHorizontalGroup(
            tambah_obat_di_resepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tambah_obat_di_resepLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tambah_obat_di_resepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tambah_obat_di_resepLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(34, 34, 34)
                        .addComponent(jumlah_obat, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(tambah_obat_di_resepLayout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tambah_obat_di_resepLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(tambah_obat_di_resepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tambah_obat_di_resepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(tambah_obat_di_resepLayout.createSequentialGroup()
                                    .addGap(129, 129, 129)
                                    .addComponent(cb_obat, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(simpan_resep, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        tambah_obat_di_resepLayout.setVerticalGroup(
            tambah_obat_di_resepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tambah_obat_di_resepLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tambah_obat_di_resepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(cb_obat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(tambah_obat_di_resepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jumlah_obat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(tambah_obat_di_resepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(simpan_resep)
                .addContainerGap())
        );

        tambah_resep.setText("Tambah Resep Pasien");
        tambah_resep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tambah_resepMouseClicked(evt);
            }
        });

        set_nama_pasien.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        set_nama_pasien.setText("Pasien");

        jLabel14.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel14.setText("Pasien");

        jLabel12.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel12.setText("Poliklinik");

        set_nama_klinik.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        set_nama_klinik.setText("Poliklinik");

        set_nama_dokter.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        set_nama_dokter.setText("Dokter");

        jLabel11.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel11.setText("Dokter");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel11)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(set_nama_klinik)
                    .addComponent(set_nama_dokter)
                    .addComponent(set_nama_pasien, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addComponent(set_nama_dokter)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(set_nama_klinik)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(set_nama_pasien)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Total Harga", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 18))); // NOI18N

        jLabel13.setFont(new java.awt.Font("Ubuntu", 0, 40)); // NOI18N
        jLabel13.setText("Rp.");

        lbl_total_harga.setFont(new java.awt.Font("Ubuntu", 0, 40)); // NOI18N
        lbl_total_harga.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_total_harga.setText("0");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addGap(31, 31, 31)
                .addComponent(lbl_total_harga)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(lbl_total_harga))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel17.setBorder(null);

        jLabel30.setFont(new java.awt.Font("Ubuntu", 1, 36)); // NOI18N
        jLabel30.setText("Bayar");

        jLabel31.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel31.setText("Kembali");

        jLabel32.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel32.setText("Rp.");

        lbl_kembalian.setFont(new java.awt.Font("Ubuntu", 0, 40)); // NOI18N
        lbl_kembalian.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_kembalian.setText("0");

        bayar.setFont(new java.awt.Font("Ubuntu", 0, 32)); // NOI18N

        btn_bayar.setText("Bayar");
        btn_bayar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_bayarMouseClicked(evt);
            }
        });
        btn_bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_bayarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bayar))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_kembalian)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(lbl_kembalian)
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_bayar)
                .addContainerGap())
        );

        selesai.setText("Selesai");
        selesai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selesaiMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
                            .addComponent(tambah_resep, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tambah_obat_di_resep, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(selesai, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tambah_resep))
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tambah_obat_di_resep, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(selesai)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Resep Pasien", jPanel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 821, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void selesaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selesaiMouseClicked
        try {
            String update_harga = "update recipes set `pay` = ?, `change` = ? where id = " + id_resep;
            ps = koneksi.prepareStatement(update_harga);
            ps.setString(1, bayar.getText());
            ps.setString(2, kembalian.toString());
            ps.executeUpdate();

            String insert_tabel_pembayaran = "insert into payments (patient_id, payment_date, total_payment, created_at, updated_at) values (?,?,?,?,?)";
            ps = koneksi.prepareStatement(insert_tabel_pembayaran);
            ps.setString(1, id_pasien.toString());
            ps.setString(2, time.toString());
            ps.setString(3, total_harga.toString());
            ps.setString(4, time.toString());
            ps.setString(5, time.toString());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(petugas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_selesaiMouseClicked

    private void btn_bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_bayarActionPerformed

    private void btn_bayarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_bayarMouseClicked
        uang_bayar = Integer.parseInt(bayar.getText());
        kembalian = uang_bayar - total_harga;
        lbl_kembalian.setText(Integer.toString(kembalian));
    }//GEN-LAST:event_btn_bayarMouseClicked

    private void tambah_resepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tambah_resepMouseClicked
        try {
            int row = data_pasien_untuk_resep.getSelectedRow();
            String table_klik = (data_pasien_untuk_resep.getModel().getValueAt(row, 0).toString());
            String sql = "select p.id as p_id, d.id as d_id, pl.id as pl_id, p.name as nama_pasien, d.name as nama_dokter, pl.name as nama_klinik from registrations r " +
            "inner join patients p on r.patient_id = p.id " +
            "inner join doctors d on r.doctor_id = d.id " +
            "inner join polyclinics pl on r.polyclinic_id = pl.id WHERE p.id = " + table_klik;
            Statement s = (Statement) koneksi.createStatement();
            rs = s.executeQuery(sql);
            if (rs.next()) {
                String a = rs.getString("nama_dokter");
                set_nama_dokter.setText(a);
                String b = rs.getString("nama_pasien");
                set_nama_pasien.setText(b);
                String c = rs.getString("nama_klinik");
                set_nama_klinik.setText(c);

                String insert_resep = "INSERT INTO recipes (`total_price`, `pay`, `change`, `doctor_id`, `patient_id`, `polyclinic_id`, `created_at`, `updated_at`) " +
                "VALUES (0,0,0,?,?,?,?,?)";
                ps = koneksi.prepareStatement(insert_resep);
                ps.setString(1, rs.getString("d_id"));
                ps.setString(2, rs.getString("p_id"));
                ps.setString(3, rs.getString("pl_id"));
                ps.setString(4, time.toString());
                ps.setString(5, time.toString());
                ps.executeUpdate();
            }
            data_pasien_untuk_resep.setEnabled(false);
        } catch (SQLException ex) {
            Logger.getLogger(petugas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tambah_resepMouseClicked

    private void simpan_resepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_simpan_resepMouseClicked
        Integer sub_total = 0;
        try {
            String sql = "SELECT id FROM recipes ORDER BY id DESC LIMIT 1";
            Statement s = (Statement) koneksi.createStatement();
            rs = s.executeQuery(sql);
            if (rs.next()) {
                String insert_resep = "INSERT INTO details (recipe_id, medicine_id, price, doses, amount_of_medicine, sub_total, `created_at`, `updated_at`) " +
                "VALUES (?,?,?,?,?,?,?,?)";
                ps = koneksi.prepareStatement(insert_resep);
                id_resep = Integer.parseInt(rs.getString("id"));
                ps.setString(1, id_resep.toString());
                Integer obat = idObat(cb_obat.getSelectedItem().toString());
                ps.setString(2, obat.toString());
                Integer harga = hargaObat(obat);
                ps.setString(3, harga.toString());
                ps.setString(4, dosis_obat.getText());
                ps.setString(5, jumlah_obat.getText());
                sub_total = harga * Integer.parseInt(jumlah_obat.getText());
                ps.setString(6, sub_total.toString());
                ps.setString(7, time.toString());
                ps.setString(8, time.toString());
                ps.executeUpdate();
                total_harga = total_harga + sub_total;
                System.out.println("=========================================================");
                System.out.println(cb_obat.getSelectedItem().toString());
                System.out.println(sub_total);
                System.out.println(dosis_obat.getText());
                System.out.println(jumlah_obat.getText());
            }
            lbl_total_harga.setText(total_harga.toString());

            String update_harga = "update recipes set total_price=? where id = " + id_resep;
            ps = koneksi.prepareStatement(update_harga);
            ps.setString(1, total_harga.toString());
            ps.executeUpdate();

            data_pasien_untuk_resep.setEnabled(false);
        } catch (SQLException ex) {
            Logger.getLogger(petugas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_simpan_resepMouseClicked

    private void data_pasien_untuk_resepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_data_pasien_untuk_resepMouseClicked
        try {
            int row = data_pasien_untuk_resep.getSelectedRow();
            String table_klik = (data_pasien_untuk_resep.getModel().getValueAt(row, 0).toString());
            String sql = "select p.id as p_id, d.id as d_id, pl.id as pl_id, p.name as nama_pasien, d.name as nama_dokter, pl.name as nama_klinik from registrations r " +
            "inner join patients p on r.patient_id = p.id " +
            "inner join doctors d on r.doctor_id = d.id " +
            "inner join polyclinics pl on r.polyclinic_id = pl.id WHERE p.id = " + table_klik;
            id_pasien = Integer.parseInt(table_klik);
            Statement s = (Statement) koneksi.createStatement();
            rs = s.executeQuery(sql);
            if (rs.next()) {
                String a = rs.getString("nama_dokter");
                set_nama_dokter.setText(a);
                String b = rs.getString("nama_pasien");
                set_nama_pasien.setText(b);
                String c = rs.getString("nama_klinik");
                set_nama_klinik.setText(c);
            }
        } catch (SQLException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }//GEN-LAST:event_data_pasien_untuk_resepMouseClicked

    private void btn_daftarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_daftarMouseClicked
        String last_id = null;
        if (pasien_baru.isSelected()){
            try {
                String sql1 = "INSERT INTO patients (name, address, gender, age, phone_number, created_at, updated_at) VALUES (?,?,?,?,?,?,?)";
                ps = koneksi.prepareStatement(sql1);
                ps.setString(1, nama_pasien.getText());
                ps.setString(2, alamat_pasien.getText());

                if (rb_pria.isSelected()) {
                    ps.setString(3, rb_pria.getText());
                } else if (rb_wanita.isSelected()) {
                    ps.setString(3, rb_wanita.getText());
                }
                ps.setString(4, umur_pasien.getText());
                ps.setString(5, telepon_pasien.getText());
                ps.setString(6, time.toString());
                ps.setString(7, time.toString());
                ps.executeUpdate();
                ps.close();

                //##############################################################################################

                String query = "SELECT id FROM patients ORDER BY id DESC LIMIT 1";
                com.mysql.jdbc.Statement s = (com.mysql.jdbc.Statement) koneksi.createStatement();
                rs = s.executeQuery(query);
                if(rs.next()){
                    last_id = rs.getString("id");
                }
                String sql2 = "INSERT INTO registrations (cost, information, doctor_id, patient_id, polyclinic_id, created_at, updated_at) VALUES (?,?,?,?,?,?,?)";
                ps = koneksi.prepareStatement(sql2);
                ps.setString(1, biaya.getText());
                ps.setString(2, keterangan.getText());
                Integer dokter = idDokter(cb_dokter.getSelectedItem().toString());
                ps.setString(3, dokter.toString());
                ps.setString(4, last_id);
                Integer klinik = idPoliklinik(cb_klinik.getSelectedItem().toString());
                ps.setString(5, klinik.toString());
                ps.setString(6, time.toString());
                ps.setString(7, time.toString());
                ps.executeUpdate();
                ps.close();

                //################################################################################################
                String sql3 = "INSERT INTO payments (payment_date, total_payment, patient_id, created_at, updated_at) VALUES (?,?,?,?,?)";
                ps = koneksi.prepareStatement(sql3);
                ps.setString(1, time.toString());
                ps.setString(2, biaya.getText());
                ps.setString(3, last_id);
                ps.setString(4, time.toString());
                ps.setString(5, time.toString());
                ps.executeUpdate();
                ps.close();

                JOptionPane.showMessageDialog(null, "Data Pendaftaran Pasien Baru Sudah Tersimpan");
                resetPendaftaran();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                koneksi = new koneksi().conn();
                String sql = "INSERT INTO registrations (cost, information, doctor_id, patient_id, polyclinic_id, created_at, updated_at) VALUES (?,?,?,?,?,?,?)";
                ps = koneksi.prepareStatement(sql);
                ps.setString(1, biaya.getText());
                ps.setString(2, keterangan.getText());
                Integer dokter = idDokter(cb_dokter.getSelectedItem().toString());
                ps.setString(3, dokter.toString());
                Integer pasien = idPasien(cb_pasien.getSelectedItem().toString());
                ps.setString(4, pasien.toString());
                Integer klinik = idPoliklinik(cb_klinik.getSelectedItem().toString());
                ps.setString(5, klinik.toString());
                ps.setString(6, time.toString());
                ps.setString(7, time.toString());
                ps.executeUpdate();
                ps.close();

                //JOptionPane.showMessageDialog(null, "Data Tersimpan");
                JOptionPane.showMessageDialog(null, "Data Pendaftaran Tersimpan");
                resetPendaftaran();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btn_daftarMouseClicked

    private void pasien_baruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasien_baruActionPerformed
        if(pasien_baru.isSelected()){
            nama_pasien.setEnabled(true);
            alamat_pasien.setEnabled(true);
            rb_pria.setEnabled(true);
            rb_wanita.setEnabled(true);
            umur_pasien.setEnabled(true);
            telepon_pasien.setEnabled(true);
        } else {
            nama_pasien.setEnabled(false);
            alamat_pasien.setEnabled(false);
            rb_pria.setEnabled(false);
            rb_wanita.setEnabled(false);
            umur_pasien.setEnabled(false);
            telepon_pasien.setEnabled(false);
        }
    }//GEN-LAST:event_pasien_baruActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new petugas().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(petugas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea alamat_pasien;
    private javax.swing.JTextField bayar;
    private javax.swing.JTextField biaya;
    private javax.swing.JButton btn_bayar;
    private javax.swing.JButton btn_cari_pasien;
    private javax.swing.JButton btn_daftar;
    private javax.swing.JTextField cari_pasien;
    private javax.swing.JComboBox<String> cb_dokter;
    private javax.swing.JComboBox<String> cb_klinik;
    private javax.swing.JComboBox<String> cb_obat;
    private javax.swing.JComboBox<String> cb_pasien;
    private javax.swing.JTable data_pasien_untuk_resep;
    private javax.swing.JTextArea dosis_obat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.ButtonGroup jenis_kelamin_pasien;
    private javax.swing.JTextField jumlah_obat;
    private javax.swing.JTextArea keterangan;
    private javax.swing.JLabel lbl_kembalian;
    private javax.swing.JLabel lbl_total_harga;
    private javax.swing.JTextField nama_pasien;
    private javax.swing.JCheckBox pasien_baru;
    private javax.swing.JRadioButton rb_pria;
    private javax.swing.JRadioButton rb_wanita;
    private javax.swing.JButton selesai;
    private javax.swing.JLabel set_nama_dokter;
    private javax.swing.JLabel set_nama_klinik;
    private javax.swing.JLabel set_nama_pasien;
    private javax.swing.JButton simpan_resep;
    private javax.swing.JPanel tambah_obat_di_resep;
    private javax.swing.JButton tambah_resep;
    private javax.swing.JTextField telepon_pasien;
    private javax.swing.JTextField umur_pasien;
    // End of variables declaration//GEN-END:variables
}
