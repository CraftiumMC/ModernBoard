package net.craftium.modernboard.config;

import net.craftium.modernboard.ModernBoard;
import net.craftium.modernboard.config.loader.Configuration;
import net.craftium.modernboard.utils.TextUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public class Messages extends Configuration
{
    public Messages(ModernBoard plugin)
    {
        super(plugin, "messages.yml", "messages.yml");
    }

    public Component get(String key, TagResolver... placeholders)
    {
        return Component.text()
                .append(TextUtil.miniMessage(getString("prefix", "prefix")))
                .append(TextUtil.miniMessage(getString(key, key), placeholders))
                .build();
    }

    public Component withPrefix(Component text)
    {
        return Component.text()
                .append(TextUtil.miniMessage(getString("prefix", "prefix")))
                .append(text)
                .build();
    }
}
