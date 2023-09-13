package com.example.configvaultserver.security;

import org.bouncycastle.asn1.eac.RSAPublicKey;
import org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rsa")
public record RsaKeyProperties(RSAPublicKey publicKey, RSAPrivateKey privateKey) {

}
