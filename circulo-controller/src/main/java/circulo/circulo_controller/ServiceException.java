package circulo.circulo_controller;

public class ServiceException extends Exception {

	/**
	 * Serial Uid
	 */
	private static final long serialVersionUID = 1L;

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
