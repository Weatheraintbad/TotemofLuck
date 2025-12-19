package totemofluck.api;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.entry.RegistryEntry;
import totemofluck.config.ModConfig;
import totemofluck.util.TotemHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.effect.StatusEffectInstance;

public class LuckyTotemAPI {

    public static void tryApplyBuff(PlayerEntity player) {
        if (!TotemHelper.hasLuckyTotem(player)) return;
        if (player.getRandom().nextInt(100) < ModConfig.BUFF_CHANCE_PERCENT) {
            RegistryEntry<StatusEffect> entry = ModConfig.BUFF_POOL
                    .get(player.getRandom().nextInt(ModConfig.BUFF_POOL.size()));
            player.addStatusEffect(new StatusEffectInstance(entry,
                    20 * ModConfig.BUFF_SECONDS, 0));
        }
    }
}