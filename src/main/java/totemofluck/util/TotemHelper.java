package totemofluck.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import totemofluck.item.ModItems;

public class TotemHelper {

    /**
     * 查找玩家手中的幸运图腾
     */
    public static ItemStack findLuckyTotem(PlayerEntity player) {
        // 先检查副手
        ItemStack offhand = player.getStackInHand(Hand.OFF_HAND);
        if (offhand.isOf(ModItems.TOTEM_OF_LUCK)) {
            return offhand;
        }

        // 再检查主手
        ItemStack mainhand = player.getStackInHand(Hand.MAIN_HAND);
        if (mainhand.isOf(ModItems.TOTEM_OF_LUCK)) {
            return mainhand;
        }

        return null;
    }

    /**
     * 检查玩家是否有幸运图腾（不消耗）
     */
    public static boolean hasLuckyTotem(PlayerEntity player) {
        ItemStack offhand = player.getStackInHand(Hand.OFF_HAND);
        ItemStack mainhand = player.getStackInHand(Hand.MAIN_HAND);

        return offhand.isOf(ModItems.TOTEM_OF_LUCK) ||
                mainhand.isOf(ModItems.TOTEM_OF_LUCK);
    }
}