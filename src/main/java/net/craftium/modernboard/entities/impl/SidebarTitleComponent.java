package net.craftium.modernboard.entities.impl;

import net.craftium.modernboard.entities.SidebarComponent;
import net.kyori.adventure.text.Component;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;

import java.util.Objects;

public class SidebarTitleComponent implements SidebarComponent
{
    private final Sidebar sidebar;

    public SidebarTitleComponent(Sidebar sidebar)
    {
        this.sidebar = sidebar;
    }

    @Override
    public void setText(Component text)
    {
        Objects.requireNonNull(text);
        sidebar.title(text);
    }
}
