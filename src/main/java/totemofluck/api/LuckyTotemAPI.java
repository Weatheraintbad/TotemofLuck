package totemofluck.api;

import totemofluck.config.ModConfig;
import totemofluck.util.TotemHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.effect.StatusEffectInstance;

public class LuckyTotemAPI {

    public static void tryApplyBuff(PlayerEntity player) {
        if (!TotemHelper.hasLuckyTotem(player)) return;
        // 使用配置里的概率
        if (player.getRandom().nextInt(100) < ModConfig.BUFF_CHANCE_PERCENT) {
            var effect = ModConfig.BUFF_POOL.get(player.getRandom().nextInt(ModConfig.BUFF_POOL.size()));
            player.addStatusEffect(new StatusEffectInstance(effect,
                    20 * ModConfig.BUFF_SECONDS, 0));
        }
    }
}