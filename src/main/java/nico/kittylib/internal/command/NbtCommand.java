package nico.kittylib.internal.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;

public class NbtCommand {

    public static LiteralArgumentBuilder<ServerCommandSource> create() {
        LiteralArgumentBuilder<ServerCommandSource> builder =
                CommandManager.literal("nbt");

        builder.executes(NbtCommand::execute);

        builder.then(CommandManager.literal("set")
                .then(CommandManager.argument("name", StringArgumentType.string())
                        .then(CommandManager.argument("value", StringArgumentType.string())
                                .executes(NbtCommand::setExecute))
                )
        );

        builder.then(CommandManager.literal("remove")
                .then(CommandManager.argument("name", StringArgumentType.string())
                        .executes(NbtCommand::removeExecute)
                )
        );

        return builder;
    }

    private static int removeExecute(CommandContext<ServerCommandSource> context) {
        PlayerEntity player = context.getSource().getPlayer();

        if (player == null) return -1;

        ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);

        NbtCompound nbtCompound = stack.hasNbt()
                ? stack.getNbt()
                : new NbtCompound();

        String name = StringArgumentType.getString(context, "name");

        String[] path = name.split("\\.");

        NbtCompound current = nbtCompound;

        for (int i = 0; i < path.length - 1; i++) {
            String key = path[i];

            NbtElement element = current.get(key);

            if (element instanceof NbtCompound compound) {
                current = compound;
            } else {
                NbtCompound compound = new NbtCompound();
                current.put(key, compound);
                current = compound;
            }
        }

        String key = path[path.length - 1];

        current.remove(key);

        stack.setNbt(nbtCompound);

        return 0;
    }

    private static int setExecute(CommandContext<ServerCommandSource> context) {
        PlayerEntity player = context.getSource().getPlayer();

        if (player == null) return -1;

        ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);

        NbtCompound nbtCompound = stack.hasNbt()
                ? stack.getNbt()
                : new NbtCompound();

        String name = StringArgumentType.getString(context, "name");
        String value = StringArgumentType.getString(context, "value");

        String[] path = name.split("\\.");

        NbtCompound current = nbtCompound;

        for (int i = 0; i < path.length - 1; i++) {
            String key = path[i];

            NbtElement element = current.get(key);

            if (element instanceof NbtCompound compound) {
                current = compound;
            } else {
                NbtCompound compound = new NbtCompound();
                current.put(key, compound);
                current = compound;
            }
        }

        String key = path[path.length - 1];

        current.put(key, parseValue(value));

        stack.setNbt(nbtCompound);

        return 0;
    }

    private static NbtElement parseValue(String value) {
        if (value.equalsIgnoreCase("true")) {
            return NbtByte.of(true);
        }

        if (value.equalsIgnoreCase("false")) {
            return NbtByte.of(false);
        }

        String numberPart = value.substring(0, value.length() - 1);

        try {
            if (value.endsWith("b") || value.endsWith("B")) {
                return NbtByte.of(Byte.parseByte(numberPart));
            }

            if (value.endsWith("s") || value.endsWith("S")) {
                return NbtShort.of(Short.parseShort(numberPart));
            }

            if (value.endsWith("l") || value.endsWith("L")) {
                return NbtLong.of(Long.parseLong(numberPart));
            }

            if (value.endsWith("f") || value.endsWith("F")) {
                return NbtFloat.of(Float.parseFloat(numberPart));
            }

            if (value.endsWith("d") || value.endsWith("D")) {
                return NbtDouble.of(Double.parseDouble(numberPart));
            }

            // Integer
            if (value.matches("-?\\d+")) {
                return NbtInt.of(Integer.parseInt(value));
            }

            // Decimal
            if (value.matches("-?\\d*\\.\\d+")) {
                return NbtDouble.of(Double.parseDouble(value));
            }
        } catch (NumberFormatException ignored) {

        }

        return NbtString.of(value);
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
        PlayerEntity player = context.getSource().getPlayer();

        if (player == null) return -1;

        ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);

        NbtCompound nbtCompound = stack.hasNbt()
                ? stack.getNbt()
                : new NbtCompound();

        Text text = NbtHelper.toPrettyPrintedText(nbtCompound);

        context.getSource().sendFeedback(() -> text, false);

        return 0;
    }
}
