package net.craftium.modernboard.entities;

import net.craftium.modernboard.entities.Sidebar.ComponentUpdate;
import org.bukkit.entity.Player;

public interface SidebarComponentUpdater
{
    boolean requiresUpdate(int lastTick);

    SidebarComponent getComponent();

    ComponentUpdate update(Player player);
}
