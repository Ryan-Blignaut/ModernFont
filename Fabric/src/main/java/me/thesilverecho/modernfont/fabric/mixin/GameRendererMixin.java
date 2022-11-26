package me.thesilverecho.modernfont.fabric.mixin;

import com.mojang.blaze3d.shaders.Program;
import com.mojang.datafixers.util.Pair;
import me.thesilverecho.modernfont.render.ModernFontShaders;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin
{

	@Inject(method = "reloadShaders", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;shutdownShaders()V", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	private void reloadShaders(ResourceManager resourceManager, CallbackInfo ci, List<Program> list, List<Pair<ShaderInstance, Consumer<ShaderInstance>>> list2)
	{
		try
		{
			ModernFontShaders.registerShaders(resourceManager, pair -> list2.add(Pair.of(pair.getFirst(), pair.getSecond())));
		} catch (Exception e)
		{
			throw new RuntimeException(e);
		}

	}

}
