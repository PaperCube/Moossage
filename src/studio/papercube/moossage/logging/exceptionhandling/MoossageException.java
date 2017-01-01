package studio.papercube.moossage.logging.exceptionhandling;

/**
 * Created by imzhy on 2016/7/22.
 */
public class MoossageException extends RuntimeException {
    public MoossageException() {
        super();
    }

    public MoossageException(String message) {
        super(message);
    }

    public MoossageException(String message, Throwable cause) {
        super(message, cause);
    }

    public MoossageException(Throwable cause) {
        super(cause);
    }


}
