package lk.job_finder_app.smart_job_aggregator.globalExceptionHandler.superClasses;

public class MethodArgumentNotValidException extends RuntimeException {
    public MethodArgumentNotValidException(String message) {
        super(message);
    }
}
