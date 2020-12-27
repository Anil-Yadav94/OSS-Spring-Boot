package oss.airtel.util;

public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomException(String cause) {
        super(cause);
    }
}
