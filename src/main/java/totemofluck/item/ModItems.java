package totemofluck.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import totemofluck.TotemOfLuck;

public class ModItems {

    public static final Item TOTEM_OF_LUCK = new Item(new FabricItemSettings()
            .maxCount(1)
            .rarity(Rarity.EPIC));

    public static void register() {
        Registry.register(Registries.ITEM,
                new Identifier(TotemOfLuck.MOD_ID, "totem_of_luck"),
                TOTEM_OF_LUCK);
    }
}