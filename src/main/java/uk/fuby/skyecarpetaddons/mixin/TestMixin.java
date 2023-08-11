package uk.fuby.skyecarpetaddons.mixin;

import net.minecraft.block.piston.PistonHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.fuby.skyecarpetaddons.Options;

@Mixin(PistonHandler.class)
public class TestMixin {
	@Shadow @Final private World world;

	@Inject(at = @At("HEAD"), method = "calculatePush")
	private void init(CallbackInfoReturnable<Boolean> cir) {
		if (Options.testOption) {
			this.world.getPlayers().forEach(p -> p.sendMessage(Text.of("uwu")));
		}
	}
}