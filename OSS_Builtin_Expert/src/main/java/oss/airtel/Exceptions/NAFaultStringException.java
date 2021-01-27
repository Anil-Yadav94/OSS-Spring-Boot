package oss.airtel.Exceptions;

public class NAFaultStringException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NAFaultStringException(String cause) {
        super(cause);
    }
}
