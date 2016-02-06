package ru.ulstu_team.ulstuschedule.data.remote;

public class DownloadException extends RuntimeException {
    public DownloadException() {
        super("Data did not download from server");
    }
}