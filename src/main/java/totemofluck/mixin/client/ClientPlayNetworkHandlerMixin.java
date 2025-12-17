// src/main/java/totemofluck/mixin/client/ClientPlayNetworkHandlerMixin.java
package totemofluck.mixin.client;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import totemofluck.item.ModItems;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

    @Inject(method = "getActiveTotemOfUndying", at = @At("RETURN"), cancellable = true)
    private static void getActiveTotemOfLuck(PlayerEntity player, CallbackInfoReturnable<ItemStack> cir) {
        // 检查玩家手中是否有幸运图腾
        for (Hand hand : Hand.values()) {
            ItemStack stack = player.getStackInHand(hand);
            if (stack.isOf(ModItems.TOTEM_OF_LUCK)) {
                cir.setReturnValue(stack);
                return;
            }
        }
    }
}