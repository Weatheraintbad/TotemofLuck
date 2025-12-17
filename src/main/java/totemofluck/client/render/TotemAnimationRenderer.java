package totemofluck.client.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import totemofluck.item.ModItems;

public class TotemAnimationRenderer {

    public static void renderTotemAnimation(LivingEntity entity, MatrixStack matrices,
                                            VertexConsumerProvider vertexConsumers, int light) {

        // 检查是否正在播放图腾动画
        if (entity.deathTime > 0 || entity.getHealth() <= 0) {
            // 检查手中是否有幸运图腾
            for (ItemStack stack : entity.getHandItems()) {
                if (stack.isOf(ModItems.TOTEM_OF_LUCK)) {
                    renderCustomTotemAnimation(entity, matrices, vertexConsumers, light, stack);
                    break;
                }
            }
        }
    }

    private static void renderCustomTotemAnimation(LivingEntity entity, MatrixStack matrices,
                                                   VertexConsumerProvider vertexConsumers,
                                                   int light, ItemStack totemStack) {

        matrices.push();

        // 调整位置和旋转
        float animationProgress = (entity.age + MinecraftClient.getInstance().getTickDelta()) % 20 / 20.0f;
        float scale = 1.0f + animationProgress * 0.5f;

        matrices.translate(0, entity.getHeight() + 0.5, 0);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(animationProgress * 360));
        matrices.scale(scale, scale, scale);

        // 渲染图腾物品
        MinecraftClient.getInstance().getItemRenderer().renderItem(
                totemStack,
                ModelTransformationMode.GROUND,
                light,
                OverlayTexture.DEFAULT_UV,
                matrices,
                vertexConsumers,
                entity.getWorld(),
                0
        );

        matrices.pop();

        // 添加自定义粒子效果
        spawnCustomParticles(entity);
    }

    private static void spawnCustomParticles(LivingEntity entity) {
        // 这里可以添加自定义的幸运粒子效果
        // 比如金色星星、四叶草等
    }
}