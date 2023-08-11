package uk.fuby.skyecarpetaddons.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import uk.fuby.skyecarpetaddons.Options;

@Mixin(ItemEntity.class)
public abstract class ThunderRitualMixin extends Entity {

	@Shadow public abstract ItemStack getStack();

	public ThunderRitualMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Override
	protected void tickInVoid() {
		if (
				!Options.thunderRitual ||
				!getStack().isOf(Items.LIGHTNING_ROD ) ||
				getStack().getCount() != 64
		) {
			super.tickInVoid();
			return;
		}
		if (!world.isClient) {
			getServer().getWorld(world.getRegistryKey()).setWeather(0, 12000, true, true);
		}
		super.tickInVoid();
	}
}