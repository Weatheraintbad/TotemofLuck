package totemofluck.event;

import totemofluck.config.ModConfig;
import totemofluck.util.TotemHelper;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class DeathEventHandler {

    public static void register() {
        if (ModConfig.ENABLE_TOTEM_RESURRECTION) {
            ServerLivingEntityEvents.ALLOW_DEATH.register(DeathEventHandler::onAllowDeath);
        }
    }

    private static boolean onAllowDeath(LivingEntity entity, DamageSource source, float amount) {
        // 检查是否启用功能
        if (!ModConfig.ENABLE_TOTEM_RESURRECTION) {
            return true;
        }

        if (!(entity instanceof PlayerEntity player)) {
            return true;
        }

        // 检查困难难度
        if (!ModConfig.WORK_IN_HARD_DIFFICULTY &&
                player.getWorld().getDifficulty().getId() >= 3) { // 3=困难
            return true;
        }

        ItemStack totem = TotemHelper.findLuckyTotem(player);
        if (totem == null) {
            return true;
        }

        // 检查不可阻挡伤害（使用修复后的方法）
        if (ModConfig.CHECK_UNBLOCKABLE_DAMAGE && isUnblockableDamage(source)) {
            return true;
        }

        // 检查创造模式
        if (player.isCreative() && !ModConfig.CONSUME_TOTEM_IN_CREATIVE) {
            // 创造模式不消耗图腾，但仍然复活
            applyLuckTotemEffects(player, null);
            return false;
        }

        // 应用幸运图腾效果
        applyLuckTotemEffects(player, totem);

        return false;
    }

    private static boolean isUnblockableDamage(DamageSource source) {
        try {
            // 对于 Minecraft 1.19+，使用 DamageTypes
            // 检查虚空伤害
            if (source.isOf(net.minecraft.entity.damage.DamageTypes.OUT_OF_WORLD)) {
                return true;
            }

            // 检查/kill命令
            if (source.isOf(net.minecraft.entity.damage.DamageTypes.GENERIC_KILL)) {
                return true;
            }

            return false;

        } catch (NoSuchFieldError | NoClassDefFoundError e) {
            // 对于旧版本（1.18.2及更早），使用字符串比较
            try {
                String damageTypeName = source.getName();
                return "out_of_world".equals(damageTypeName) ||
                        "generic_kill".equals(damageTypeName);
            } catch (Exception ex) {
                // 如果连 getName() 都没有，返回 false
                return false;
            }
        }
    }

    private static void applyLuckTotemEffects(PlayerEntity player, ItemStack totem) {
        // 核心特性：满血复活或1点血
        if (ModConfig.FULL_HEALTH_RESURRECTION) {
            player.setHealth(player.getMaxHealth());
        } else {
            player.setHealth(1.0F);
        }

        // 清除所有负面效果
        player.clearStatusEffects();
        player.extinguish(); // 灭火

        // 应用正面效果
        applyResurrectionEffects(player);

        // 恢复饥饿值
        if (ModConfig.RESTORE_HUNGER) {
            player.getHungerManager().setFoodLevel(20);
            player.getHungerManager().setSaturationLevel(5.0F);
        }

        // 播放音效
        if (ModConfig.PLAY_RESURRECTION_SOUND) {
            player.getWorld().playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    SoundEvents.ENTITY_PLAYER_LEVELUP,
                    SoundCategory.PLAYERS,
                    ModConfig.RESURRECTION_SOUND_VOLUME,
                    ModConfig.RESURRECTION_SOUND_PITCH
            );
        }

        // 发送动画状态
        player.getWorld().sendEntityStatus(player, (byte) 35);

        // 消耗图腾（如果不是创造模式或不消耗）
        if (totem != null && !(player.isCreative() && !ModConfig.CONSUME_TOTEM_IN_CREATIVE)) {
            totem.decrement(1);
        }
    }

    private static void applyResurrectionEffects(PlayerEntity player) {
        // 再生效果
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.REGENERATION,
                ModConfig.RESURRECTION_REGENERATION_DURATION,
                ModConfig.RESURRECTION_REGENERATION_AMPLIFIER
        ));

        // 吸收效果
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.ABSORPTION,
                ModConfig.RESURRECTION_ABSORPTION_DURATION,
                ModConfig.RESURRECTION_ABSORPTION_AMPLIFIER
        ));

        // 防火效果
        if (ModConfig.RESURRECTION_FIRE_RESISTANCE_DURATION > 0) {
            player.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.FIRE_RESISTANCE,
                    ModConfig.RESURRECTION_FIRE_RESISTANCE_DURATION,
                    0
            ));
        }

        // 抗性提升
        if (ModConfig.RESURRECTION_RESISTANCE_DURATION > 0) {
            player.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.RESISTANCE,
                    ModConfig.RESURRECTION_RESISTANCE_DURATION,
                    ModConfig.RESURRECTION_RESISTANCE_AMPLIFIER
            ));
        }

        // 夜视效果
        if (ModConfig.ADD_NIGHT_VISION && ModConfig.NIGHT_VISION_DURATION > 0) {
            player.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.NIGHT_VISION,
                    ModConfig.NIGHT_VISION_DURATION,
                    0
            ));
        }
    }
}