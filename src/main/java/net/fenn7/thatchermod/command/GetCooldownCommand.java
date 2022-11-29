package net.fenn7.thatchermod.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fenn7.thatchermod.item.ModItems;
import net.fenn7.thatchermod.item.custom.UnionBusterItem;
import net.fenn7.thatchermod.util.IEntityDataSaver;
import net.fenn7.thatchermod.util.ModText;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

public class GetCooldownCommand implements Command<ServerCommandSource> {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("cooldowns")
                .then(CommandManager.literal("record").executes(new GetCooldownCommand())));
    }

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        IEntityDataSaver player = (IEntityDataSaver) context.getSource().getPlayer();
        int unionBusterCD = (int) (UnionBusterItem.DURATION * context.getSource().getPlayer().getItemCooldownManager().
                getCooldownProgress(ModItems.UNION_BUSTER, 0));

        player.getPersistentData().putInt("union.buster.cd", unionBusterCD);

        // this is for testing only.
        context.getSource().sendFeedback(ModText.literal("All Cooldowns Recorded!"), true);
        context.getSource().sendFeedback(ModText.literal("Union Buster: " + unionBusterCD / 20 + " seconds"), true);
        return 1;
    }
}
