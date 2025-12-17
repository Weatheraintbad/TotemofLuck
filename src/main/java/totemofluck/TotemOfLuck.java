package totemofluck;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import totemofluck.config.ModConfig;
import totemofluck.event.DeathEventHandler;
import totemofluck.event.PlayerEventHandler;
import totemofluck.item.ModItems;

public class TotemOfLuck implements ModInitializer {

    public static final String MOD_ID = "totemofluck";

    @Override
    public void onInitialize() {
        ModItems.register();

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
                .register(entries -> entries.addAfter(Items.TOTEM_OF_UNDYING,
                        ModItems.TOTEM_OF_LUCK));

        PlayerEventHandler.register();
        DeathEventHandler.register();
    }
}