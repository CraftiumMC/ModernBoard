package net.craftium.modernboard.entities.impl;

import net.craftium.modernboard.ModernBoard;
import net.craftium.modernboard.animations.AnimationParser;
import net.craftium.modernboard.boards.SidebarSettings;
import net.craftium.modernboard.entities.SidebarComponent;
import net.craftium.modernboard.entities.SidebarComponentUpdater;
import net.craftium.modernboard.utils.FrameList;
import net.craftium.modernboard.utils.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class AnimatedComponentUpdater implements SidebarComponentUpdater
{
    private final FrameList frames;
    private final int interval;
    private final ModernBoard plugin;
    private final SidebarComponent component;

    private int currentFrame = 0;

    public AnimatedComponentUpdater(ModernBoard plugin, SidebarComponent component, SidebarSettings.Line settings)
    {
        this.interval = settings.interval();
        this.plugin = plugin;
        this.component = component;
        this.frames = createFrameList(settings.frames());
    }

    @Override
    public boolean requiresUpdate(int lastTick)
    {
        return lastTick % interval == 0;
    }

    public int getInterval()
    {
        return interval;
    }

    @Override
    public SidebarComponent getComponent()
    {
        return component;
    }

    @Override
    public void update(Player player)
    {
        if(currentFrame >= frames.size() - 1)
            this.currentFrame = 0;
        else
            ++this.currentFrame;

        Component display = TextUtil.placeholderAndParse(player, frames.get(currentFrame));
        component.setText(display);
    }

    private FrameList createFrameList(FrameList unparsedFrames)
    {
        AnimationParser animationParser = plugin.getAnimationParser();
        FrameList frameList = new FrameList();

        for(String frame : unparsedFrames)
        {
            if(animationParser.hasAnimation(frame))
                frameList.addAll(animationParser.parseAnimation(frame));
            else
                frameList.add(frame);
        }

        return frameList;
    }
}
