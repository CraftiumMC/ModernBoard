package net.craftium.modernboard.entities.impl;

import net.craftium.modernboard.entities.SidebarComponent;
import net.craftium.modernboard.entities.SidebarComponentUpdater;
import net.craftium.modernboard.utils.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class StaticComponentUpdater implements SidebarComponentUpdater
{
    private final SidebarComponent component;
    private final String line;

    public StaticComponentUpdater(SidebarComponent component, String line)
    {
        this.component = component;
        this.line = line;
    }

    @Override
    public boolean requiresUpdate(int lastTick)
    {
        return false;
    }

    @Override
    public SidebarComponent getComponent()
    {
        return component;
    }

    @Override
    public void update(Player player)
    {
        Component display = TextUtil.placeholderAndParse(player, line);
        component.setText(display);
    }
}
