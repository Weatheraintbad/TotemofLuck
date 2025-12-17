package totemofluck.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import totemofluck.item.ModItems;

public class TotemHelper {

    public static boolean hasLuckyTotem(PlayerEntity player) {
        return findLuckyTotem(player) != null;
    }

    public static ItemStack findLuckyTotem(PlayerEntity player) {
        ItemStack main = player.getMainHandStack();
        if (!main.isEmpty() && main.isOf(ModItems.TOTEM_OF_LUCK)) return main;

        ItemStack off = player.getOffHandStack();
        if (!off.isEmpty() && off.isOf(ModItems.TOTEM_OF_LUCK)) return off;

        return null;
    }
}