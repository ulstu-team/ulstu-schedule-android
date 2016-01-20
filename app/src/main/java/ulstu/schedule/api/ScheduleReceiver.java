package ulstu.schedule.api;

import android.support.annotation.Nullable;

public interface ScheduleReceiver<T> {
    @Nullable
    void onDataReceived(T data);
}
