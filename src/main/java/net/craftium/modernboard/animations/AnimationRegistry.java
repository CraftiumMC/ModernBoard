package net.craftium.modernboard.animations;

import net.craftium.modernboard.animations.impl.builtin.FlashingAnimation;
import net.craftium.modernboard.animations.impl.builtin.PauseAnimation;
import net.craftium.modernboard.animations.impl.builtin.RainbowAnimation;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AnimationRegistry
{
    private final Map<String, Animation> animations;

    public AnimationRegistry()
    {
        this.animations = new HashMap<>();

        // Register built-in animations
        registerAnimation("flash", new FlashingAnimation());
        registerAnimation("pause", new PauseAnimation());
        registerAnimation("rainbow", new RainbowAnimation());
    }

    public void registerAnimation(String identifier, Animation animation)
    {
        if(identifier.isBlank())
            throw new IllegalArgumentException("Animation identifier cannot be empty!");
        Objects.requireNonNull(animation, "Animation object cannot be null");
        animations.put(identifier, animation);
    }

    @Nullable
    public Animation getAnimation(String identifier)
    {
        return animations.get(identifier);
    }

    public void unregisterAnimation(String identifier)
    {
        animations.remove(identifier);
    }

    public Map<String, Animation> getAnimations()
    {
        return Collections.unmodifiableMap(animations);
    }
}
