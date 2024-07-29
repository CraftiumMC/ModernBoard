package net.craftium.modernboard.animations;

import net.craftium.modernboard.utils.FrameList;

import java.util.Arrays;
import java.util.List;

public abstract class Animation
{
    private final String identifier;
    private final String[] frames;

    public Animation(String identifier, String[] frames)
    {
        this.identifier = identifier;
        this.frames = frames;
    }

    public abstract FrameList animate();

    public String getIdentifier()
    {
        return identifier;
    }

    public List<String> getFrames()
    {
        return Arrays.asList(frames);
    }
}
