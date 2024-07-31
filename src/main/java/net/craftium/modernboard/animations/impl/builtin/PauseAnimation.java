package net.craftium.modernboard.animations.impl.builtin;

import net.craftium.modernboard.animations.Animation;
import net.craftium.modernboard.animations.AnimationEvent;
import net.craftium.modernboard.utils.FrameList;

public class PauseAnimation extends Animation
{
    public PauseAnimation()
    {
        super("pause");
    }

    @Override
    public FrameList animate(AnimationEvent event)
    {
        FrameList frames = new FrameList();
        int pauseTime = Integer.parseInt(event.options());
        for(int i = 0; i < pauseTime; i++)
            frames.add(event.text());
        return frames;
    }
}
