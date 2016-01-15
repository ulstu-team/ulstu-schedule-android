package ulstu.schedule.api;

public class DownloadException extends Exception {
    public DownloadException() {
        super("Data did not download from server");
    }
}