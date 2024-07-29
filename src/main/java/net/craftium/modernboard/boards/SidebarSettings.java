package net.craftium.modernboard.boards;

import net.craftium.modernboard.utils.FrameList;

import java.util.List;

public record SidebarSettings(int priority, List<Line> lines)
{
    public record Line(int index, int interval, FrameList frames) {}
}
