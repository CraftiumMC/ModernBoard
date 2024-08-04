package net.craftium.modernboard.config.loader;

import net.craftium.modernboard.ModernBoard;
import net.craftium.modernboard.entities.SidebarSettings;

import java.io.File;

public class SidebarConfig extends Configuration
{
    public SidebarConfig(ModernBoard plugin, File file)
    {
        super(plugin, file);
    }

    public SidebarSettings load()
    {
        return get(null, SidebarSettings.class, null);
    }
}
