package nico.kittylib.internal.scheduler;

import net.minecraft.world.World;
import nico.kittylib.api.util.schedule.KittyLibScheduledAction;
import nico.kittylib.api.util.schedule.KittyLibScheduledTask;
import nico.kittylib.api.util.schedule.KittyLibScheduler;

import java.util.ArrayList;
import java.util.List;

public class ImplementedScheduler implements KittyLibScheduler {
    public static List<KittyLibScheduledTask> serverTasks = new ArrayList<>();
    public static List<KittyLibScheduledTask> clientTasks = new ArrayList<>();

    public static void schedule(int duration, KittyLibScheduledAction task) {
        scheduleClient(duration, task);
        scheduleServer(duration, task);
    }

    public static void scheduleServer(int duration, KittyLibScheduledAction task) {
        serverTasks.add(new KittyLibScheduledTask(duration, task));
    }

    public static void scheduleClient(int duration, KittyLibScheduledAction task) {
        clientTasks.add(new KittyLibScheduledTask(duration, task));
    }

    public static void tick(World world) {
        if(world.isClient()) {
            for (KittyLibScheduledTask task : clientTasks) task.tick(world);
            clientTasks.removeIf(task -> task.duration <= 0);
        } else {
            for (KittyLibScheduledTask task : serverTasks) task.tick(world);
            serverTasks.removeIf(task -> task.duration <= 0);
        }
    }
}
