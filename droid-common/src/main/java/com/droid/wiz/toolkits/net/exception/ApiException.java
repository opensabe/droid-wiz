package com.droid.wiz.toolkits.net.exception;

public class ApiException extends Exception {

  public int code;
  public String msg;

  public ApiException(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }
}
