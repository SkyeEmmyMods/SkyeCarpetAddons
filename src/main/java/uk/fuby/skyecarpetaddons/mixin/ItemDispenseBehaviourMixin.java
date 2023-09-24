package uk.fuby.skyecarpetaddons.mixin;

import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import uk.fuby.skyecarpetaddons.Options;

@Mixin(ItemDispenserBehavior.class)
public abstract class ItemDispenseBehaviourMixin {
	
	@Inject(
			method = "spawnItem",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"
			),
			locals = LocalCapture.CAPTURE_FAILHARD
	)
	private static void itemVelocityInject(World world, ItemStack stack, int speed, Direction side, Position pos, CallbackInfo ci, double d, double e, double f, ItemEntity itemEntity, double g) {
		if (Options.hardcodedDispenserItemRNG.equals("false")) return;
		
		String[] velocities = Options.hardcodedDispenserItemRNG.split(" ");
		
		if (side.getAxis() == Direction.Axis.X) {
			itemEntity.setVelocity(
					Double.parseDouble(velocities[0]),
					0.2 + Double.parseDouble(velocities[1]),
					Double.parseDouble(velocities[2])
			);
		}
		if (side.getAxis() == Direction.Axis.Z) {
			itemEntity.setVelocity(
					Double.parseDouble(velocities[2]),
					0.2 + Double.parseDouble(velocities[1]),
					Double.parseDouble(velocities[0])
					);
		}
	}
}
