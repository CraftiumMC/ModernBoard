package net.craftium.modernboard.animations.impl;

import com.google.common.collect.Lists;
import net.craftium.modernboard.animations.Animation;
import net.craftium.modernboard.animations.AnimationEvent;
import net.craftium.modernboard.utils.FrameList;

import java.util.Arrays;
import java.util.List;

public final class ReplacingAnimation extends Animation
{
    private final boolean repeatBackwards;
    private final String[] frames;

    public ReplacingAnimation(String identifier, boolean repeatBackwards, String[] frames)
    {
        super(identifier);
        this.repeatBackwards = repeatBackwards;
        this.frames = frames;
    }

    @Override
    public FrameList animate(AnimationEvent event)
    {
        FrameList animatedFrames = new FrameList(getFrames());

        if(repeatBackwards)
        {
            List<String> reversed = Lists.reverse(animatedFrames);
            animatedFrames.addAll(reversed.subList(1, reversed.size()));
        }

        return animatedFrames;
    }

    public boolean repeatBackwards()
    {
        return repeatBackwards;
    }

    public List<String> getFrames()
    {
        return Arrays.asList(frames);
    }
}
