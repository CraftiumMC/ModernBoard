package net.craftium.modernboard.animations.impl.builtin;

import net.craftium.modernboard.animations.Animation;
import net.craftium.modernboard.animations.AnimationEvent;
import net.craftium.modernboard.utils.FrameList;

public class FlashingAnimation extends Animation
{
    public FlashingAnimation()
    {
        super("flash");
    }

    @Override
    public FrameList animate(AnimationEvent event)
    {
        FrameList frameList = new FrameList();
        frameList.add(event.text());
        // Doing this to keep the same length as the original originalFrame
        frameList.add(" ".repeat(event.text().length()));
        return frameList;
    }
}
