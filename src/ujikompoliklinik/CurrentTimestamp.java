/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ujikompoliklinik;

import java.sql.Timestamp;


/**
 *
 * @author ilhamfi
 */
public class CurrentTimestamp {
    private Timestamp timestamp;
    public Timestamp CurrentTime (){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp;
    }
    public static void main (String args[]){
    }
}
