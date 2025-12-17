// src/main/java/totemofluck/client/TotemOfLuckClient.java
package totemofluck.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import totemofluck.item.ModItems;

@SuppressWarnings("deprecation")
public class TotemOfLuckClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // 注册自定义物品渲染器（如果需要特殊渲染）
        registerItemRenderer();
    }

    private void registerItemRenderer() {
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.TOTEM_OF_LUCK,
                (stack, mode, matrices, vertexConsumers, light, overlay) -> {
                    // 自定义渲染逻辑
                    MinecraftClient.getInstance().getItemRenderer().renderItem(
                            stack,
                            ModelTransformationMode.GUI,
                            light,
                            OverlayTexture.DEFAULT_UV,
                            matrices,
                            vertexConsumers,
                            null,
                            0
                    );
                });
    }
}