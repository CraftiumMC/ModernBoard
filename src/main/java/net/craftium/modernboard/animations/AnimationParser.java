package net.craftium.modernboard.animations;

import net.craftium.modernboard.utils.FrameList;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnimationParser
{
    private final AnimationRegistry registry;

    public AnimationParser(AnimationRegistry registry)
    {
        this.registry = registry;
    }

    public boolean hasAnimation(FrameList frames)
    {
        for(String frame : frames)
        {
            if(hasAnimation(frame))
                return true;
        }

        return false;
    }

    public boolean hasAnimation(String frame)
    {
        return extractAnimation(frame) != null;
    }

    @Nullable
    public Animation extractAnimation(String frame)
    {
        Matcher matcher = ANIMATION_PATTERN.matcher(frame);
        return extractAnimation(matcher);
    }

    @Nullable
    private Animation extractAnimation(Matcher matcher)
    {
        if(matcher.matches())
            return registry.getAnimation(matcher.group(1).toLowerCase(Locale.ROOT));
        return null;
    }

    public List<String> parseAnimation(FrameList frames)
    {
        FrameList ret = new FrameList();
        for(String frame : frames)
            ret.addAll(parseAnimation(frame));
        return ret;
    }

    public List<String> parseAnimation(String frame)
    {
        Matcher matcher = ANIMATION_PATTERN.matcher(frame);
        Animation animation = extractAnimation(matcher);
        if(animation == null)
            return List.of(frame);

        return animation.animate();
    }

    private static final Pattern ANIMATION_PATTERN = Pattern.compile("<([^>]+)>");
}
