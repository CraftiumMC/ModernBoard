package net.craftium.modernboard.tasks;

import net.craftium.modernboard.entities.Sidebar;
import org.bukkit.scheduler.BukkitRunnable;

public class SidebarUpdateTask extends BukkitRunnable
{
    private final Sidebar sidebar;

    public SidebarUpdateTask(Sidebar sidebar)
    {
        this.sidebar = sidebar;
    }

    @Override
    public void run()
    {
        if(!sidebar.isHidden())
            sidebar.update();
    }
}
