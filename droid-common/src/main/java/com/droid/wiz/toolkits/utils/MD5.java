package com.droid.wiz.toolkits.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import androidx.annotation.NonNull;

/**
 * Created by liuXiao on 10/19/2021
 */
public class MD5 {

  private static final char[] HEX_DIGITS =
      { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

  public static String toHexString2(@NonNull byte[] b) {
    StringBuilder sb = new StringBuilder(b.length * 2);
    for (byte i : b) {
      sb.append(HEX_DIGITS[(i & 0xf0) >>> 4]);
      sb.append(HEX_DIGITS[i & 0x0f]);
    }
    return sb.toString();
  }

  public static String md5(@NonNull String s) {
    try {
      MessageDigest digest = MessageDigest.getInstance("MD5");
      digest.update(s.getBytes(StandardCharsets.UTF_8));
      return toHexString2(digest.digest());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }

  public static String md5(@NonNull ByteBuffer byteBuffer) {
    try {
      MessageDigest digest = MessageDigest.getInstance("MD5");
      digest.update(byteBuffer);
      return toHexString2(digest.digest());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }

  @NonNull
  public static String md5(@NonNull File file) {
    FileInputStream inputStream = null;
    try {
      inputStream = new FileInputStream(file);
      FileChannel ch = inputStream.getChannel();
      return md5(ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length()));
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static boolean checkMD5(File tempFile, String serverMD5) {
    return Tools.equalsIgnoreCase(md5(tempFile), serverMD5);
  }

}
