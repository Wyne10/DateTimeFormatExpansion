package me.wyne.dtf.duration;

import me.wyne.dtf.Ticks;

import java.util.concurrent.TimeUnit;

public class Hours implements Duration {
    @Override
    public long getMillis(long duration) {
        return duration * 60 * 60 * 1000;
    }

    @Override
    public long getTicks(long duration) {
        return Ticks.ofSeconds(duration * 60 * 60);
    }

    @Override
    public long getUnit(long duration, TimeUnit unit) {
        return unit.convert(duration, TimeUnit.HOURS);
    }
}
