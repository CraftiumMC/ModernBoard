package net.craftium.modernboard.managers;

import com.comphenix.protocol.ProtocolLibrary;
import net.craftium.modernboard.ModernBoard;
import net.craftium.modernboard.listeners.ScoreboardPacketListener;

public class ProtocolLibManager
{
    public static void init(ModernBoard plugin)
    {
        ProtocolLibrary.getProtocolManager().addPacketListener(new ScoreboardPacketListener(plugin));
    }
}
