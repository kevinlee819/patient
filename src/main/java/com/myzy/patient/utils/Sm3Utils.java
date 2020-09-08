package com.myzy.patient.utils;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.nio.charset.StandardCharsets;

/**
 * 国密SM3算法。依赖bcprov-jdk15on.jar
 *
 * @author leekejin
 * @date 2020-02-14 16:56
 */
public class Sm3Utils {

    /**
     * 加密
     *
     * @param plainText 明文
     * @return 密文
     */
    public static String encryption(String plainText) {
        SM3Digest digest = new SM3Digest();
        byte[] bytes = plainText.getBytes(StandardCharsets.UTF_8);
        digest.update(bytes, 0, bytes.length);
        byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);
        return ByteUtils.toHexString(hash);
    }

    /**
     * 校验
     *
     * @param plainText  明文
     * @param cipherText 密文
     * @return 校验结果
     */
    public static boolean verification(String plainText, String cipherText) {
        String encryptionText = encryption(plainText);
        return encryptionText.equals(cipherText);
    }

}
