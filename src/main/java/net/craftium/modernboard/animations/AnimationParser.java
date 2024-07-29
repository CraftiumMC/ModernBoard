package net.craftium.modernboard.animations;

import com.google.common.collect.Lists;
import net.craftium.modernboard.config.AnimationRegistry;
import net.craftium.modernboard.utils.FrameList;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
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
            return registry.animations.get(matcher.group(1));
        return null;
    }

    public List<String> parseAnimation(FrameList frames)
    {
        List<String> ret = new LinkedList<>();
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

        List<String> animatedFrames = new LinkedList<>(Arrays.asList(animation.frames()));
        if(animation.repeatBackwards())
        {
            List<String> reversed = Lists.reverse(animatedFrames);
            animatedFrames.addAll(reversed.subList(1, reversed.size()));
        }

        return animatedFrames;
    }

    private static final Pattern ANIMATION_PATTERN = Pattern.compile("<([^>]+)>");
}
