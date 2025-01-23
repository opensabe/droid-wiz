package com.droid.wiz.toolkits.utils.encrypt;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

/**
 * AES加密
 */
public class AESUtil {

  private static final String KEY_ALGORITHM = "AES";   //AES 加密
  private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";   //默认的加密算法

  /**
   * 加密
   *
   * @param aesKey  aes
   * @param content 内容
   */
  public static byte[] encrypt(String aesKey, byte[] content) throws Exception {
    byte[] raw = Base64.decode(aesKey, Base64.NO_WRAP);
    SecretKeySpec sKeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
    Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
    SecureRandom r = new SecureRandom();
    byte[] ivBytes = new byte[16];
    r.nextBytes(ivBytes);
    cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, new IvParameterSpec(ivBytes));
    byte[] res = new byte[(content.length + 32) & ~0xF];
    System.arraycopy(ivBytes, 0, res, 0, 16);
    cipher.doFinal(content, 0, content.length, res, 16);
    return res;
  }

  /**
   * 解密
   *
   * @param aesKey    aes
   * @param encrypted 内容
   */
  public static byte[] decrypt(String aesKey, byte[] encrypted) throws Exception {
    byte[] raw = Base64.decode(aesKey, Base64.NO_WRAP);
    SecretKeySpec sKeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
    Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
    byte[] ivByte = new byte[16];
    System.arraycopy(encrypted, 0, ivByte, 0, 16);
    cipher.init(Cipher.DECRYPT_MODE, sKeySpec, new IvParameterSpec(ivByte));
    return cipher.doFinal(encrypted, 16, encrypted.length - 16);
  }
}


