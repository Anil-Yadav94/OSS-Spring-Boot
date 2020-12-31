package oss.airtel.util;

public class CustomerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomerException(String cause) {
        super(cause);
    }
}
