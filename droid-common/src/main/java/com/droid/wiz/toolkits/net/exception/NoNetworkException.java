package com.droid.wiz.toolkits.net.exception;

import java.io.IOException;

/**
 * Created by fangzheng on 02/22/2021
 */
public class NoNetworkException extends IOException {
  public NoNetworkException() {
  }

  public NoNetworkException(String message) {
    super(message);
  }

  public NoNetworkException(String message, Throwable cause) {
    super(message, cause);
  }

  public NoNetworkException(Throwable cause) {
    super(cause);
  }
}
