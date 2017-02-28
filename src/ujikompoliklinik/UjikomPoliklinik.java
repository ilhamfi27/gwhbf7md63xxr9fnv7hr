/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ujikompoliklinik;

import java.sql.SQLException;

/**
 *
 * @author ilhamfi
 */
public class UjikomPoliklinik {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        login l = new login();
        l.show(true);
    }
    
}
