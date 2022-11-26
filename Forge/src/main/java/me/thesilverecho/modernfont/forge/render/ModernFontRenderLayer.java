package me.thesilverecho.modernfont.forge.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import me.thesilverecho.modernfont.render.ModernFontShaders;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class ModernFontRenderLayer extends RenderStateShard
{
	public static Function<ResourceLocation, RenderType> sdfFontShader = Util.memoize(ModernFontRenderLayer::getSdfType);

	public ModernFontRenderLayer(String name, Runnable start, Runnable end)
	{
		super(name, start, end);
	}

	public static RenderType getSdfFontShader(ResourceLocation locationIn)
	{
		return ModernFontRenderLayer.sdfFontShader.apply(locationIn);
	}

	private static RenderType getSdfType(ResourceLocation locationIn)
	{
		RenderType.CompositeState fontTarget = RenderType.CompositeState.builder()
		                                                                .setShaderState(new ShaderStateShard(ModernFontShaders::getSdfFontShader))
		                                                                .setTextureState(new RenderStateShard.TextureStateShard(locationIn, true, true))
		                                                                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
//		                                                                .setLightmapState(LIGHTMAP)
		                                                                .createCompositeState(false);

		return RenderType.create("modernfont_translucent_font_target", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 256, false, true, fontTarget);
	}

}
