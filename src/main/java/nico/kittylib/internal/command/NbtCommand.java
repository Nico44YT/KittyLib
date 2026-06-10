package nico.kittylib.internal.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;

public class NbtCommand {

    public static LiteralArgumentBuilder<ServerCommandSource> create() {
        LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal("nbt");

        builder.executes(NbtCommand::execute);

        return builder;
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
        PlayerEntity player = context.getSource().getPlayer();

        if(player == null) return -1;
        ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);
        NbtCompound nbtCompound = stack.hasNbt() ? stack.getNbt() : new NbtCompound();

        Text text = NbtHelper.toPrettyPrintedText(nbtCompound);

        context.getSource().sendFeedback(() -> text, false);

        return 0;
    }
}
