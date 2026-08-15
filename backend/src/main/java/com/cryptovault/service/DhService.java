package com.cryptovault.service;

import org.springframework.stereotype.Service;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

@Service
public class DhService {

    public Result processMessages(String aliceMessage, String bobMessage)
            throws Exception {

        // 1. Generate DH parameters
        AlgorithmParameterGenerator paramGen =
                AlgorithmParameterGenerator.getInstance("DH");

        paramGen.init(2048);

        AlgorithmParameters params = paramGen.generateParameters();

        DHParameterSpec dhSpec =
                params.getParameterSpec(DHParameterSpec.class);

        // 2. Generate Alice's key pair
        KeyPairGenerator keyPairGenerator =
                KeyPairGenerator.getInstance("DH");

        keyPairGenerator.initialize(dhSpec);

        KeyPair aliceKeyPair = keyPairGenerator.generateKeyPair();

        // 3. Generate Bob's key pair
        keyPairGenerator.initialize(dhSpec);

        KeyPair bobKeyPair = keyPairGenerator.generateKeyPair();

        // 4. Alice creates shared secret
        KeyAgreement aliceAgreement =
                KeyAgreement.getInstance("DH");

        aliceAgreement.init(aliceKeyPair.getPrivate());

        aliceAgreement.doPhase(bobKeyPair.getPublic(), true);

        byte[] aliceSecret =
                aliceAgreement.generateSecret();

        // 5. Bob creates shared secret
        KeyAgreement bobAgreement =
                KeyAgreement.getInstance("DH");

        bobAgreement.init(bobKeyPair.getPrivate());

        bobAgreement.doPhase(aliceKeyPair.getPublic(), true);

        byte[] bobSecret =
                bobAgreement.generateSecret();

        // 6. Convert DH secret into AES key
        byte[] aesKeyBytes = new byte[16];

        System.arraycopy(
                aliceSecret,
                0,
                aesKeyBytes,
                0,
                16
        );

        SecretKeySpec aesKey =
                new SecretKeySpec(aesKeyBytes, "AES");

        // 7. Encrypt Alice's message
        String encryptedAlice =
                encrypt(aliceMessage, aesKey);

        // 8. Encrypt Bob's message
        String encryptedBob =
                encrypt(bobMessage, aesKey);

        return new Result(
                Base64.getEncoder().encodeToString(
                        aliceKeyPair.getPublic().getEncoded()
                ),
                Base64.getEncoder().encodeToString(
                        bobKeyPair.getPublic().getEncoded()
                ),
                Base64.getEncoder().encodeToString(aliceSecret),
                Base64.getEncoder().encodeToString(bobSecret),
                encryptedAlice,
                encryptedBob
        );
    }

    private String encrypt(String message, SecretKeySpec key)
            throws Exception {

        Cipher cipher = Cipher.getInstance("AES");

        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encrypted =
                cipher.doFinal(
                        message.getBytes(StandardCharsets.UTF_8)
                );

        return Base64.getEncoder().encodeToString(encrypted);
    }

    public record Result(
            String alicePublicKey,
            String bobPublicKey,
            String aliceSharedSecret,
            String bobSharedSecret,
            String encryptedAliceMessage,
            String encryptedBobMessage
    ) {}
}