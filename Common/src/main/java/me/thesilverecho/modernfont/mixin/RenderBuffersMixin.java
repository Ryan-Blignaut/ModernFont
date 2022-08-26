package me.thesilverecho.modernfont.mixin;

import com.mojang.blaze3d.vertex.BufferBuilder;
import me.thesilverecho.modernfont.utils.FontLog;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.SortedMap;

@Mixin(RenderBuffers.class)
public abstract class RenderBuffersMixin
{
	@Shadow @Final private SortedMap<RenderType, BufferBuilder> fixedBuffers;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void addCustomRenderLayer(CallbackInfo ci)
	{
		FontLog.LogMessage("Hello from Render Layers");
	}
}
