/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ujikompoliklinik;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author ilhamfi
 */
public class ConvertToMD5 {
    public static String convertPassMd5(String pass) {
      String password = null;
      MessageDigest mdEnc;
      try {
        mdEnc = MessageDigest.getInstance("MD5");
        mdEnc.update(pass.getBytes(), 0, pass.length());
        pass = new BigInteger(1, mdEnc.digest()).toString(16);
        while (pass.length() < 32) {
          pass = "0" + pass;
        }
        password = pass;
      } catch (NoSuchAlgorithmException e1) {
        e1.printStackTrace();
      }
      return password;
    }
}
