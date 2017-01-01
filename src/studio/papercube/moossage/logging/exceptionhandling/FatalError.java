package studio.papercube.moossage.logging.exceptionhandling;

public class FatalError extends RuntimeException {

	private static final long serialVersionUID = -73110898525429291L;

	public FatalError() {
		super();
	}

	public FatalError(String arg0) {
		super(arg0);
	}

	public FatalError(Throwable arg0) {
		super(arg0);
	}

	public FatalError(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public FatalError(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
