package totemofluck.event;

import totemofluck.util.TotemHelper;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DeathEventHandler {

    public static void register() {
        ServerLivingEntityEvents.ALLOW_DEATH.register(DeathEventHandler::onAllowDeath);
    }

    private static boolean onAllowDeath(LivingEntity entity, DamageSource source, float amount) {
        if (!(entity instanceof PlayerEntity player)) return true;

        ItemStack totem = TotemHelper.findLuckyTotem(player);
        if (totem == null) return true; // 没有图腾，死吧牢弟

        // 原版不死图腾同款逻辑
        player.setHealth(1.0F);
        player.clearStatusEffects();
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
        entity.getWorld().sendEntityStatus(player, (byte) 35); // 触发动画
        totem.decrement(1);
        return false; // 取消死亡
    }
}