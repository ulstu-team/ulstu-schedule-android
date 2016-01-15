package ulstu.schedule.api;

public interface ScheduleReceiver<T> {
    void onDataReceived(T data);
}
