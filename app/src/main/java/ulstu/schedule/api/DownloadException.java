package ulstu.schedule.api;

public class DownloadException extends RuntimeException {
    public DownloadException() {
        super("Data did not download from server");
    }
}