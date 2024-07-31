package net.craftium.modernboard.animations.impl.builtin;

import net.craftium.modernboard.animations.Animation;
import net.craftium.modernboard.animations.AnimationEvent;
import net.craftium.modernboard.utils.FrameList;
import net.craftium.modernboard.utils.Gradient;
import net.craftium.modernboard.utils.TextUtil;

public class RainbowAnimation extends Animation
{
    public RainbowAnimation()
    {
        super("rainbow");
    }

    @Override
    public FrameList animate(AnimationEvent event)
    {
        FrameList frames = new FrameList();
        int length = event.options().isEmpty() ? DEFAULT_LENGTH : Integer.parseInt(event.options());
        Gradient gradient = new Gradient(length);
        String frame = TextUtil.stripColors(event.text());

        for(int i = 0; i < length; i++)
        {
            String hex = gradient.color().asHexString();
            frames.add("<" + hex + ">" + frame + "</" + hex + ">");
            gradient.advanceColor();
        }

        return frames;
    }

    private static final int DEFAULT_LENGTH = 30;
}
