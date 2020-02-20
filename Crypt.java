import javax.crypto.*;
//import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
//import java.security.Key;
import java.util.Base64;


public class Crypt {

    public static String crypt(String password) {
        // Somewhat uses Unix's old style of encrypting passwords. Instead of using the password as the key,
        // I used a static key instead because I couldn't get it to work. It encrypts the password, using DES, 25 times.
        byte[] pwBytes = new byte[0];
        //KeyGenerator keyGen;
        try {
            Cipher cipherCrypt = Cipher.getInstance("DES");
            pwBytes = password.getBytes(StandardCharsets.UTF_8);
            //byte[] keyBytes = password.getBytes();
            //keyGen = KeyGenerator.getInstance("DES");
            //DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
            //SecretKeyFactory keyF = SecretKeyFactory.getInstance("DES");

            //System.out.println(Arrays.toString(password.getBytes()));
            //System.out.println(Arrays.toString(keyBytes));
            //byte[] pwCrypt;
            //SecretKey key = keyF.generateSecret(desKeySpec);
            //Base64.getDecoder().decode(key64);
            SecretKey key = buildKey();
            if(key == null) {
                System.out.println("[crypt] failed in crypt().");
                return Base64.getEncoder().encodeToString("n0thing".getBytes());
            }
            //System.out.println("key: " + Base64.getEncoder().encodeToString(key.getEncoded()));

            //System.out.println(Arrays.toString(key.getEncoded()));

            cipherCrypt.init(Cipher.ENCRYPT_MODE, key);
            //byte[] newBytes = cipherCrypt.doFinal(pwBytes);
            for(int i = 0; i < 25; i++) {
                //System.out.printf("[crypt] %d ", i+1);
                pwBytes = cipherCrypt.doFinal(pwBytes);
                //System.out.println(Base64.getEncoder().encodeToString(pwBytes));
            }

            //pwBytes = cipherCrypt.doFinal(pwBytes);
            //String pw = new String(pwBytes, StandardCharsets.UTF_8);


            //System.out.println(Base64.getEncoder().encode(pwBytes));
        }
        catch(Exception e) {
            System.out.println("[crypt] failed in crypt().");
        }

        return Base64.getEncoder().encodeToString(pwBytes);
    }

    private static SecretKey buildKey() {
        //Builds the DES key from the base64 string
        SecretKeySpec key;
        try {
            String key64 = "ZDTO6Ua1xMQ=";
            byte[] keyBuffer = Base64.getDecoder().decode(key64);
            key = new SecretKeySpec(keyBuffer, "DES");

        }
        catch (Exception e) {
            System.out.println("[key] build failed.");
            key = null;
        }

        return key;
    }
}
