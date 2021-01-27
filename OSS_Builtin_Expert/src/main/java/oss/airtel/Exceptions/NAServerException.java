package oss.airtel.Exceptions;

public class NAServerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NAServerException(String cause) {
        super(cause);
    }
}
