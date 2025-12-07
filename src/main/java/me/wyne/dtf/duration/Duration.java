package me.wyne.dtf.duration;

import java.util.concurrent.TimeUnit;

public interface Duration {
    long getMillis(long duration);
    long getTicks(long duration);
    long getUnit(long duration, TimeUnit unit);
}
