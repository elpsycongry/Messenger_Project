package com.example.messenger.mail;

import com.example.messenger.token.JwtService;
import com.example.messenger.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VerificationTokenServiceImp implements VerificationTokenService{
    private final JwtService jwtService;
    private final VerificationTokenRepository verifyTokenRepo;
    public static final byte[] KEY =
            {118, 106, 107, 122, 76, 99, 69, 83, 101, 103, 82, 101, 116, 75, 101, 127};

    @Override
    public VerificationToken createVerificationToken(User user) {
        String token = UUID.randomUUID().toString();

        return verifyTokenRepo.save(VerificationToken.builder()
                .token(token)
                .createAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(15))
                .action("verify email")
                .build());
    }

    @Override
    public boolean checkVerificationToken(VerificationToken token) {
        Optional<VerificationToken> verificationToken = verifyTokenRepo.findByToken(token.getToken());

        return verificationToken.isPresent();
    }



    public static String encrypt(String salt, String plainText) {
        if (plainText == null || plainText.isEmpty()) {
            System.out.println("No data to encrypt!");
            return plainText;
        }
        Cipher cipher = null;
        String encryptedString = "";
        try {
            // Creating a Cipher object
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            // Creating a secret key from KEY byte array
            final SecretKeySpec secretKey = new SecretKeySpec(KEY, "AES");

            // Initializing a Cipher object
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // Encrypting the plain text string
            byte[] encryptedText = cipher.doFinal(salt.concat(plainText).getBytes());

            // Encoding the encrypted text to Base64
            encryptedString = Base64.getEncoder().encodeToString(encryptedText);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                 | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println("Exception caught while encrypting : " + ex);
        }
        return encryptedString;
    }


    public static String decrypt(String salt, String cipherText) {
        if (cipherText == null || cipherText.isEmpty()) {
            System.out.println("No data to decrypt!");
            return cipherText;
        }
        String decryptedString = "";
        Cipher cipher = null;
        try {
            // Creating a Cipher object
            cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");

            // Creating a secret key from KEY byte array
            final SecretKeySpec secretKey = new SecretKeySpec(KEY, "AES");

            // Initializing a Cipher object
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            // Decoding from Base64
            byte[] encryptedText = Base64.getDecoder().decode(cipherText.getBytes());

            // Decrypting to plain text
            decryptedString = new String(cipher.doFinal(encryptedText));

        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException
                 | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println("Exception caught while decrypting : " + ex);
        }
        return decryptedString.replace(salt, "");
    }
}
