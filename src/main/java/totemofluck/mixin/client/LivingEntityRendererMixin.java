package totemofluck.mixin.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import totemofluck.item.ModItems;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity> {

    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(T livingEntity, float f, float g, MatrixStack matrixStack,
                          VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {

        // 检查实体是否正在使用幸运图腾
        // 这里你可以添加自定义渲染逻辑
    }
}