package nico.kittylib.api.util.schedule;

import nico.kittylib.internal.scheduler.ImplementedScheduler;

public interface KittyLibScheduler {
    static void schedule(int duration, KittyLibScheduledAction task) {
        ImplementedScheduler.schedule(duration, task);
    }

    static void scheduleClient(int duration, KittyLibScheduledAction task) {
        ImplementedScheduler.scheduleClient(duration, task);
    }

    static void scheduleServer(int duration, KittyLibScheduledAction task) {
        ImplementedScheduler.scheduleServer(duration, task);
    }
}
