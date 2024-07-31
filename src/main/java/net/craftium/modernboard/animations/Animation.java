package net.craftium.modernboard.animations;

import net.craftium.modernboard.utils.FrameList;

public abstract class Animation
{
    private final String identifier;

    public Animation(String identifier)
    {
        this.identifier = identifier;
    }

    public abstract FrameList animate(AnimationEvent event);

    public String getIdentifier()
    {
        return identifier;
    }
}
