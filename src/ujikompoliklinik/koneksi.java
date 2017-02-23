package ujikompoliklinik;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class koneksi {
    
    private Connection koneksi;

    public Connection conn() {
        //koneksi ke driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Gagal Koneksi " + cnfe);
        }

        //koneksi ke db
        try {
            //nama database yang ingin dipanggil
            String url = "jdbc:mysql://localhost:3306/ujikom-poliklinik_development";
            koneksi = DriverManager.getConnection(url, "root", "");
        } catch (SQLException ex) {
            System.out.println("Gagal Koneksi ke database " + ex);
        }
        return koneksi;
    }
    
    public static void main(String args[]){
        Connection c = new koneksi().conn();
    }
}
