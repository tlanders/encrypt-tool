package biz.lci.encrypttool;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
class EncryptToolController {
    @GetMapping
    public String hello() {
        return "Encrypt Tool is running...";
    }

    /**
     * Encrypts or decrypts a value using the provided secret.
     * @param encryptRequest
     * @return Either the encrypted value or the decrypted value.
     */
    @PostMapping("/encrypt")
    public String encrypt(@RequestBody @NonNull EncryptRequest encryptRequest) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(encryptRequest.secret());
        config.setPoolSize("1");
//        config.setAlgorithm("PBEWithMD5AndDES");
//        config.setKeyObtentionIterations("1000");
//        config.setProviderName("SunJCE");
//        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
//        config.setStringOutputType("base64");
        encryptor.setConfig(config);

        if (encryptRequest.value().contains("ENC")) {
            return encryptor.decrypt(encryptRequest.value().substring(4,encryptRequest.value().length()-1));
        }
        return "ENC("+encryptor.encrypt(encryptRequest.value())+")";
    }

    /**
     * Takes an old secret and encrypted value, and then encrypts that value with the new secret.
     * @param encryptSwapRequest
     * @return The new encryted value.
     */
    @PostMapping("/encryptswap")
    public String encryptSwap(@RequestBody @NonNull EncryptSwapRequest encryptSwapRequest) {
        PooledPBEStringEncryptor oldEncryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(encryptSwapRequest.oldsecret());
        config.setPoolSize("1");
//        config.setAlgorithm("PBEWithMD5AndDES");
//        config.setKeyObtentionIterations("1000");
//        config.setProviderName("SunJCE");
//        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
//        config.setStringOutputType("base64");
        oldEncryptor.setConfig(config);

        PooledPBEStringEncryptor newEncryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig newconfig = new SimpleStringPBEConfig();
        newconfig.setPassword(encryptSwapRequest.newsecret());
        newconfig.setPoolSize("1");
//        config.setAlgorithm("PBEWithMD5AndDES");
//        config.setKeyObtentionIterations("1000");
//        config.setProviderName("SunJCE");
//        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
//        config.setStringOutputType("base64");
        newEncryptor.setConfig(newconfig);

        if (encryptSwapRequest.oldvalue().contains("ENC")) {
            String oldRawValue = oldEncryptor.decrypt(encryptSwapRequest.oldvalue().substring(4,encryptSwapRequest.oldvalue().length()-1));
            return "ENC("+newEncryptor.encrypt(oldRawValue)+")";
        }
        return "oldvalue must be encrypted and in ENC(ENCRYPTED_VAL) format.";
    }

    public record EncryptRequest(String secret, String value) {}
    public record EncryptSwapRequest(String oldsecret, String oldvalue, String newsecret) {}
}
