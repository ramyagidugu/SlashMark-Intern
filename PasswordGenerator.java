import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class PasswordGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_+=<>?/";
    private static final SecureRandom random = new SecureRandom();
    private static SecretKey secretKey;

    public PasswordGenerator() throws Exception {
        // Generate a secret key for encryption
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128); // AES key size (128, 192, or 256 bits)
        secretKey = keyGen.generateKey();
    }

    // Generate a random password
    public String generatePassword(int length) {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }
        return password.toString();
    }

    // Encrypt a password
    public String encryptPassword(String password) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decrypt a password
    public String decryptPassword(String encryptedPassword) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
        return new String(decryptedBytes);
    }

    public static void main(String[] args) {
        try {
            PasswordGenerator generator = new PasswordGenerator();

            // Generate a password
            int passwordLength = 12;
            String generatedPassword = generator.generatePassword(passwordLength);
            System.out.println("Generated Password: " + generatedPassword);

            // Encrypt the password
            String encryptedPassword = generator.encryptPassword(generatedPassword);
            System.out.println("Encrypted Password: " + encryptedPassword);

            // Decrypt the password
            String decryptedPassword = generator.decryptPassword(encryptedPassword);
            System.out.println("Decrypted Password: " + decryptedPassword);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
