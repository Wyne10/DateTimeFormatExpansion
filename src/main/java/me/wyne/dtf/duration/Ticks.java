package me.wyne.dtf.duration;

import java.util.concurrent.TimeUnit;

public class Ticks implements Duration {
    @Override
    public long getMillis(long duration) {
        return me.wyne.dtf.Ticks.toMillis(duration);
    }

    @Override
    public long getTicks(long duration) {
        return duration;
    }

    @Override
    public long getUnit(long duration, TimeUnit unit) {
        return unit.convert(getMillis(duration), TimeUnit.MILLISECONDS);
    }
}
