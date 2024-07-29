package net.craftium.modernboard.animations;

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
