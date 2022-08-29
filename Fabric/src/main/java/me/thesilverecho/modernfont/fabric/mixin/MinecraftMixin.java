package me.thesilverecho.modernfont.fabric.mixin;

import com.mojang.blaze3d.platform.Window;
import me.thesilverecho.modernfont.fabric.event.FabricRegisterClientReloadListenersEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin
{

	@Shadow @Final private ReloadableResourceManager resourceManager;

	@Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;updateVsync(Z)V"))
	public void injectIntoConstructor(Window instance, boolean $$0)
	{
		FabricRegisterClientReloadListenersEvent.EVENT.invoker().register(resourceManager);
	}
}
