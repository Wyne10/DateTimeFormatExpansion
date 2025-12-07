package me.wyne.dtf.duration;

import me.wyne.dtf.Ticks;

import java.util.concurrent.TimeUnit;

public class Millis implements Duration {
    @Override
    public long getMillis(long duration) {
        return duration;
    }

    @Override
    public long getTicks(long duration) {
        return Ticks.ofMillis(duration);
    }

    @Override
    public long getUnit(long duration, TimeUnit unit) {
        return unit.convert(duration, TimeUnit.MILLISECONDS);
    }
}
