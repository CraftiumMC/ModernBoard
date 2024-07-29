package net.craftium.modernboard.entities;

import org.bukkit.entity.Player;

public interface SidebarComponentUpdater
{
    boolean requiresUpdate(int lastTick);

    SidebarComponent getComponent();

    void update(Player player);
}
