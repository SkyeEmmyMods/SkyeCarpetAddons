package uk.fuby.skyecarpetaddons.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import uk.fuby.skyecarpetaddons.features.ItemRituals;

@Mixin(ItemEntity.class)
public abstract class ItemRitualMixin extends Entity {

	@Shadow public abstract ItemEntity copy();

	public ItemRitualMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Override
	protected void tickInVoid() {
		ItemRituals.INSTANCE.detectItemRitual(this.copy());
		super.tickInVoid();
	}
}