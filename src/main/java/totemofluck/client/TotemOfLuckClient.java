package totemofluck.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;   // 1. 修改 import
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import totemofluck.item.ModItems;

@SuppressWarnings("deprecation")
public class TotemOfLuckClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        registerItemRenderer();
    }

    private void registerItemRenderer() {
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.TOTEM_OF_LUCK,
                (stack, mode, matrices, vertexConsumers, light, overlay) ->
                        MinecraftClient.getInstance().getItemRenderer().renderItem(
                                stack,
                                ModelTransformation.Mode.GUI,
                                false,
                                matrices,
                                vertexConsumers,
                                light,
                                overlay,      // 直接用参数
                                null
                        ));
    }
}