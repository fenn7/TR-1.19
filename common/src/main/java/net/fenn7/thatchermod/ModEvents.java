package net.fenn7.thatchermod;

import dev.architectury.event.events.common.BlockEvent;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.PlayerEvent;
import net.fenn7.thatchermod.event.BlockBreakEvents;
import net.fenn7.thatchermod.event.PlayConnectionEvents;
import net.fenn7.thatchermod.event.PlayerEvents;

public class ModEvents {

    public static void registerAllEvents() {
        PlayerEvent.PLAYER_JOIN.register(PlayConnectionEvents.INSTANCE);
        PlayerEvent.PLAYER_QUIT.register(PlayConnectionEvents.INSTANCE);
        PlayerEvent.PLAYER_CLONE.register(PlayerEvents.INSTANCE);
        EntityEvent.LIVING_DEATH.register(PlayerEvents.INSTANCE);
        BlockEvent.BREAK.register(BlockBreakEvents.INSTANCE);
    }
}
