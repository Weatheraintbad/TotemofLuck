package totemofluck.config;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;

import java.util.List;

public class ModConfig {

    public static final List<StatusEffect> BUFF_POOL = List.of(
            StatusEffects.SPEED, StatusEffects.HASTE, StatusEffects.STRENGTH,
            StatusEffects.JUMP_BOOST, StatusEffects.REGENERATION,
            StatusEffects.FIRE_RESISTANCE, StatusEffects.WATER_BREATHING,
            StatusEffects.NIGHT_VISION
    );
    public static final int BUFF_SECONDS = 60;

    public static final int BUFF_CHANCE_PERCENT = 10;
}