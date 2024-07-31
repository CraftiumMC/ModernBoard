package net.craftium.modernboard.utils;

import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.util.HSVLike;

public class Gradient
{
    private final int length;

    private int colorIndex;

    public Gradient(int length)
    {
        this.colorIndex = 0;
        this.length = length;
    }

    public void advanceColor()
    {
        this.colorIndex++;
    }

    public TextColor color()
    {
        float index = colorIndex;
        float hue = (index / length) % 1f;
        return TextColor.color(HSVLike.hsvLike(hue, 1f, 1f));
    }
}
