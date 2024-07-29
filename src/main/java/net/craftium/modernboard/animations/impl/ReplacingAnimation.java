package net.craftium.modernboard.animations.impl;

import com.google.common.collect.Lists;
import net.craftium.modernboard.animations.Animation;
import net.craftium.modernboard.utils.FrameList;

import java.util.List;

public final class ReplacingAnimation extends Animation
{
    private final boolean repeatBackwards;

    public ReplacingAnimation(String identifier, boolean repeatBackwards, String[] frames)
    {
        super(identifier, frames);
        this.repeatBackwards = repeatBackwards;
    }

    @Override
    public FrameList animate()
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
}
