package net.craftium.modernboard.entities.impl;

import net.craftium.modernboard.entities.SidebarComponent;
import net.kyori.adventure.text.Component;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;

import java.util.Objects;

public class SidebarLineComponent implements SidebarComponent
{
    private final int index;
    private final Sidebar sidebar;

    public SidebarLineComponent(int index, Sidebar sidebar)
    {
        this.index = index;
        this.sidebar = sidebar;
    }

    @Override
    public void setText(Component text)
    {
        Objects.requireNonNull(text);
        sidebar.line(index, text);
    }
}
