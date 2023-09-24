package uk.fuby.skyecarpetaddons.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.fuby.skyecarpetaddons.Options;
import uk.fuby.skyecarpetaddons.features.ItemRituals;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {

	@Shadow public abstract ItemEntity copy();

	public ItemEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Override
	protected void tickInVoid() {
		ItemRituals.INSTANCE.detectItemRitual(this.copy());
		super.tickInVoid();
	}
	
	@Inject(
			method = "<init>(Lnet/minecraft/world/World;DDDLnet/minecraft/item/ItemStack;)V",
			at = @At("TAIL")
	)
	private void itemRandomVelocityInject(
			World world,
			double x, double y, double z,
			ItemStack stack,
			CallbackInfo ci
	) {
		if (Options.hardcodedItemRNG.equals("false")) return;
		
		String[] velocities = Options.hardcodedItemRNG.split(" ");
		
		this.setVelocity(
				Double.parseDouble(velocities[0]),
				Double.parseDouble(velocities[1]),
				Double.parseDouble(velocities[2])
		);
	}
}