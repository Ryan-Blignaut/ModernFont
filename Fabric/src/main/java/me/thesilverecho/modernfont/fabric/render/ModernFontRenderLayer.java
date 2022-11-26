package me.thesilverecho.modernfont.fabric.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import me.thesilverecho.modernfont.fabric.mixin.RenderTypeAccessor;
import me.thesilverecho.modernfont.render.ModernFontShaders;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class ModernFontRenderLayer extends RenderType
{

	public static Function<ResourceLocation, RenderType> sdfFontShader = Util.memoize(ModernFontRenderLayer::getSdfType);

	public ModernFontRenderLayer(String string, VertexFormat vertexFormat, VertexFormat.Mode mode, int i, boolean bl, boolean bl2, Runnable runnable, Runnable runnable2)
	{
		super(string, vertexFormat, mode, i, bl, bl2, runnable, runnable2);
	}

	public static RenderType getSdfFontShader(ResourceLocation locationIn)
	{
		return ModernFontRenderLayer.sdfFontShader.apply(locationIn);
	}

	private static RenderType getSdfType(ResourceLocation locationIn)
	{
		RenderType.CompositeState fontTarget = RenderType.CompositeState.builder()
		                                                                .setShaderState(new ShaderStateShard(ModernFontShaders::getSdfFontShader))
		                                                                .setTextureState(new RenderStateShard.TextureStateShard(locationIn, true, false))
		                                                                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
		                                                                .setLightmapState(LIGHTMAP)
		                                                                .createCompositeState(false);

		return RenderTypeAccessor.create("modernfont_translucent_font_target", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 256, false, true, fontTarget);
	}
}
