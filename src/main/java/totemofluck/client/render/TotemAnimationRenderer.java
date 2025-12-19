package totemofluck.client.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.model.json.ModelTransformation;   // 1. 修改 import
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import totemofluck.item.ModItems;

public class TotemAnimationRenderer {

    public static void renderTotemAnimation(LivingEntity entity, MatrixStack matrices,
                                            VertexConsumerProvider vertexConsumers, int light) {

        if (entity.deathTime > 0 || entity.getHealth() <= 0) {
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

        float animationProgress = (entity.age + MinecraftClient.getInstance().getTickDelta()) % 20 / 20.0f;
        float scale = 1.0f + animationProgress * 0.5f;

        matrices.translate(0, entity.getHeight() + 0.5, 0);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(animationProgress * 360));
        matrices.scale(scale, scale, scale);

        // 2. 把 ModelTransformationMode.GROUND 改成 ModelTransformation.GROUND
        MinecraftClient.getInstance().getItemRenderer().renderItem(
                totemStack,
                ModelTransformation.Mode.GROUND,
                false,                       // leftHand
                matrices,
                vertexConsumers,
                light,                       // light
                OverlayTexture.DEFAULT_UV,   // overlay
                null                         // model
        );

        matrices.pop();
        spawnCustomParticles(entity);
    }

    private static void spawnCustomParticles(LivingEntity entity) {
    }
}