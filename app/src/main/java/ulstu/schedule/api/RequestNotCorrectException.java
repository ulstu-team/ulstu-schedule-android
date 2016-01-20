package ulstu.schedule.api;

public class RequestNotCorrectException extends RuntimeException {

    public RequestNotCorrectException() {
        super("Some fields has not correct values. Check call chain to API");
    }
}
