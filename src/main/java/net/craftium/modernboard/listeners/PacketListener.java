package net.craftium.modernboard.listeners;

import com.comphenix.protocol.events.ListeningWhitelist;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.DisplaySlot;
import net.craftium.modernboard.ModernBoard;
import net.craftium.modernboard.entities.Sidebar;
import org.bukkit.plugin.Plugin;

import static com.comphenix.protocol.PacketType.Play.Server.SCOREBOARD_DISPLAY_OBJECTIVE;
import static com.comphenix.protocol.PacketType.Play.Server.SCOREBOARD_OBJECTIVE;
import static com.comphenix.protocol.wrappers.EnumWrappers.DisplaySlot.SIDEBAR;

public class PacketListener implements com.comphenix.protocol.events.PacketListener
{
    private final ModernBoard plugin;
    private final ListeningWhitelist whitelist;

    public PacketListener(ModernBoard plugin)
    {
        this.plugin = plugin;
        this.whitelist = ListeningWhitelist.newBuilder().highest()
                .types(SCOREBOARD_OBJECTIVE, SCOREBOARD_DISPLAY_OBJECTIVE)
                .build();
    }

    @Override
    public void onPacketSending(PacketEvent event)
    {
        if(event.isCancelled())
            return;

        PacketContainer packet = event.getPacket();
        Sidebar sidebar = plugin.getSidebarManager().getBoards().get(event.getPlayer());
        if(sidebar == null)
            return;

        switch(plugin.getSettings().compatMode)
        {
            case HIDE ->
            {
                // another plugin is sending an scoreboard
                if(packet.getType() == SCOREBOARD_DISPLAY_OBJECTIVE)
                {
                    String objectiveName = packet.getStrings().readSafely(0);
                    if(isSidebar(packet) && !sidebar.getObjectiveName().equals(objectiveName))
                    {
                        sidebar.setHidden(true);
                        plugin.getSLF4JLogger().debug("Disabling sidebar for {}, being replaced by {}",
                                event.getPlayer(), objectiveName);
                    }
                }
                // another plugin is hiding their scoreboard
                else if(packet.getType() == SCOREBOARD_OBJECTIVE)
                {
                    int pos = packet.getIntegers().readSafely(0);
                    String objectiveName = packet.getStrings().readSafely(0);
                    if(pos == 1 && sidebar.isHidden() && !sidebar.getObjectiveName().equals(objectiveName))
                    {
                        sidebar.setHidden(false);
                        plugin.getSLF4JLogger().debug("Re-enabling sidebar for {}, was replaced by {}",
                                event.getPlayer(), objectiveName);
                    }
                }
            }
            case OVERRIDE ->
            {
                if(packet.getType() == SCOREBOARD_DISPLAY_OBJECTIVE)
                {
                    String objectiveName = packet.getStrings().readSafely(0);
                    if(isSidebar(packet) && !sidebar.getObjectiveName().equals(objectiveName))
                    {
                        // They are trying to override us!!!
                        event.setCancelled(!sidebar.isHidden());
                        plugin.getSLF4JLogger().debug("Denying sidebar takeover attempt for {} by {}",
                                event.getPlayer(), objectiveName);
                    }
                }
            }
        }
    }

    private boolean isSidebar(PacketContainer packet)
    {
        return packet.getEnumModifier(DisplaySlot.class, 0).readSafely(0) == SIDEBAR;
    }

    @Override
    public void onPacketReceiving(PacketEvent packetEvent) {}

    @Override
    public ListeningWhitelist getSendingWhitelist()
    {
        return whitelist;
    }

    @Override
    public ListeningWhitelist getReceivingWhitelist()
    {
        return ListeningWhitelist.EMPTY_WHITELIST;
    }

    @Override
    public Plugin getPlugin()
    {
        return plugin;
    }
}
