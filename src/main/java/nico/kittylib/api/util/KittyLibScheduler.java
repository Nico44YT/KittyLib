package nico.kittylib.api.util;

import nico.kittylib.internal.scheduler.ImplementedScheduler;

public interface KittyLibScheduler {
    static void schedule(int duration, KittyLibScheduledAction task) {
        ImplementedScheduler.scheduleServer(duration, task);
    }
}
