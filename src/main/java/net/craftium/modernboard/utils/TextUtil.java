package net.craftium.modernboard.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

import static net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.legacyAmpersand;

public class TextUtil
{
    public static String placeholders(Player player, String text)
    {
        return PlaceholderAPI.setPlaceholders(player, text);
    }

    public static Component miniMessage(String text)
    {
        return MINI_MESSAGE.deserialize(text);
    }

    private static final LegacyComponentSerializer LEGACY_SERIALIZER = legacyAmpersand();
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
}
