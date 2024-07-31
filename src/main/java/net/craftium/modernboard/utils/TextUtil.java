package net.craftium.modernboard.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;

public class TextUtil
{
    public static String stripColors(String text)
    {
        return COLOR_STRIPPER.stripTags(text);
    }

    public static String placeholders(Player player, String text)
    {
        return PlaceholderAPI.setPlaceholders(player, text);
    }

    public static String plain(Component text)
    {
        return PLAIN_SERIALIZER.serialize(text);
    }

    public static Component miniMessage(String text)
    {
        return MINI_MESSAGE.deserialize(text);
    }

    public static Component placeholderAndParse(Player player, String text)
    {
        return miniMessage(placeholders(player, text));
    }

    private static final MiniMessage MINI_MESSAGE = MiniMessage.builder()
            .tags(TagResolver.builder()
                    .resolver(StandardTags.defaults())
                    .build())
            .build();
    private static final MiniMessage COLOR_STRIPPER = MiniMessage.builder()
            .tags(TagResolver.builder()
                    .resolvers(StandardTags.color(), StandardTags.gradient(), StandardTags.rainbow())
                    .build())
            .build();
    private static final PlainTextComponentSerializer PLAIN_SERIALIZER = PlainTextComponentSerializer.plainText();
}
