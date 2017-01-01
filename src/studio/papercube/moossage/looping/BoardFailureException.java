package studio.papercube.moossage.looping;

public class BoardFailureException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BoardFailureException() {
	}

	public BoardFailureException(String arg0) {
		super(arg0);
	}

	public BoardFailureException(Throwable arg0) {
		super(arg0);
	}

	public BoardFailureException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BoardFailureException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
