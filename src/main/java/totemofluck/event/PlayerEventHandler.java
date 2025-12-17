package totemofluck.event;

import totemofluck.api.LuckyTotemAPI;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.Entity;              // ← 补上
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class PlayerEventHandler {

    public static void register() {
        PlayerBlockBreakEvents.AFTER.register(PlayerEventHandler::onBreak);
        AttackEntityCallback.EVENT.register(PlayerEventHandler::onAttack);
        UseItemCallback.EVENT.register(PlayerEventHandler::onUseItem);
    }

    private static void onBreak(World world, PlayerEntity player,
                                net.minecraft.util.math.BlockPos pos,
                                net.minecraft.block.BlockState state,
                                net.minecraft.block.entity.BlockEntity blockEntity) {
        if (!world.isClient) LuckyTotemAPI.tryApplyBuff(player);
    }

    private static ActionResult onAttack(PlayerEntity player, World world,
                                         net.minecraft.util.Hand hand,
                                         Entity entity,
                                         net.minecraft.util.hit.EntityHitResult hitResult) {
        if (!world.isClient && entity instanceof LivingEntity target && target.isDead()) {
            LuckyTotemAPI.tryApplyBuff(player);
        }
        return ActionResult.PASS;
    }

    private static TypedActionResult<net.minecraft.item.ItemStack> onUseItem(PlayerEntity player,
                                                                             World world,
                                                                             net.minecraft.util.Hand hand) {
        if (!world.isClient && player.isUsingItem() && player.getItemUseTimeLeft() <= 0) {
            LuckyTotemAPI.tryApplyBuff(player);
        }
        return TypedActionResult.pass(player.getStackInHand(hand));
    }
}