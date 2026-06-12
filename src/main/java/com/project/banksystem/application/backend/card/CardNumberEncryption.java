package com.project.banksystem.application.backend.card;

import com.project.banksystem.application.backend.card.body.CardMigrationInfo;
import com.project.banksystem.application.config.properties.CardEncryptionConfig;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class CardNumberEncryption {
    private final CardEncryptionConfig cardEncryptionConfig;

    public CardNumberEncryption(CardEncryptionConfig cardEncryptionConfig) {
        this.cardEncryptionConfig = cardEncryptionConfig;
    }

    private SecretKeySpec getLastestKey(int keyVer) {
        byte[] keyBytes = Base64.getDecoder()
                .decode(cardEncryptionConfig
                        .getKeyByVersion(keyVer));
        return new SecretKeySpec(keyBytes, "AES");
    }

    private String encrypt(String text, SecretKeySpec secretKeySpec) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            byte[] encrypted = cipher.doFinal(text.getBytes());

            return Base64.getEncoder().encodeToString(encrypted);
        }catch (Exception e) {
            throw new IllegalStateException("Failed to encrypt data.");
        }
    }

    public String plainEncrypt(String text) {
        return encrypt(text, getLastestKey(cardEncryptionConfig.getKeyVersion()));
    }

    public String viewEncrypt(String text, int cardKeyVersion) {
        return encrypt(text, getLastestKey(cardKeyVersion));
    }


    public CardMigrationInfo migrateEncrypt(String text) {
        int lastest = cardEncryptionConfig.getKeyVersion();
        return new CardMigrationInfo(encrypt(text, getLastestKey(lastest)), lastest);
    }

    public String decrypt(String encrypted, int cardKeyVersion) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, getLastestKey(cardKeyVersion));

            byte[] decoded = Base64.getDecoder().decode(encrypted);

            return new String(cipher.doFinal(decoded));
        }catch (Exception e) {
            throw new IllegalStateException("Failed to decrypt data.");
        }

    }
}
