package net.craftium.modernboard.animations;

import net.craftium.modernboard.utils.FrameList;
import net.craftium.modernboard.utils.TextUtil;
import org.bukkit.entity.Player;
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
            return registry.getAnimation(matcher.group(GROUP_ANIMATION).toLowerCase(Locale.ROOT));
        return null;
    }

    public List<String> parseAnimation(Player player, String frame)
    {
        return parseAnimation0(player, frame, 1);
    }

    private List<String> parseAnimation0(Player player, String frame, int n)
    {
        Matcher matcher = ANIMATION_PATTERN.matcher(frame);
        Animation animation = extractAnimation(matcher);
        if(animation == null)
            return List.of(frame);

        String textBefore = matcher.group(GROUP_TXT_BEFORE);
        String text = matcher.group(GROUP_TXT);
        String textAfter = matcher.group(GROUP_TXT_AFTER);
        String args = TextUtil.placeholders(player, matcher.group(GROUP_ARGS).trim());

        FrameList animatedFrames = animation.animate(new AnimationEvent(frame, args, text));
        if(!animatedFrames.isEmpty())
        {
            animatedFrames = appendBeforeAfter(animatedFrames, textBefore, textAfter);
            // Limit to 2 nested animations
            if(n < 2)
                animatedFrames = parseAnimations(player, animatedFrames, ++n);
        }

        if(animatedFrames.isEmpty())
            return List.of("");
        return animatedFrames;
    }

    private FrameList parseAnimations(Player player, FrameList frames, int n)
    {
        FrameList ret = new FrameList();
        for(String frame : frames)
            ret.addAll(parseAnimation0(player, frame, n));
        return ret;
    }

    private FrameList appendBeforeAfter(FrameList frames, String textBefore, String textAfter)
    {
        FrameList appendedFrames = new FrameList();
        for(String frame : frames)
            appendedFrames.add(textBefore + frame + textAfter);
        return appendedFrames;
    }

    private static final int GROUP_TXT_BEFORE = 1;
    private static final int GROUP_ANIMATION  = 3;
    private static final int GROUP_ARGS       = 4;
    private static final int GROUP_TXT        = 5;
    private static final int GROUP_TXT_AFTER  = 6;
    private static final Pattern ANIMATION_PATTERN = Pattern.compile("(.*?)(\\[([^ ]*)(.*?)](.*?)\\[/\\3])(.*)");
}
