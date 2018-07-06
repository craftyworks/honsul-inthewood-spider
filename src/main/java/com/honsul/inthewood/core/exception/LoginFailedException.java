package com.honsul.inthewood.core.exception;

public class LoginFailedException extends RuntimeException {

  private static final long serialVersionUID = 4304900424094142336L;

  public LoginFailedException() {
    super();
  }

  public LoginFailedException(String message) {
    super(message);
  }

  public LoginFailedException(Throwable cause) {
    super(cause);
  }
  
  public LoginFailedException(String message, Throwable cause) {
    super(message, cause);
  }
}
