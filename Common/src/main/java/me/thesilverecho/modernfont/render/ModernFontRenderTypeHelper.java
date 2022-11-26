package me.thesilverecho.modernfont.render;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;


// Util class to get the render type for the font, as in forge render type properties are public but in fabric they are private.
public class ModernFontRenderTypeHelper
{
	private static Function<ResourceLocation, RenderType> normalType, seeThroughType, polygonOffsetType;

	public static void registerRenderLayers(Function<ResourceLocation, RenderType> normalType, Function<ResourceLocation, RenderType> seeThroughType, Function<ResourceLocation, RenderType> polygonOffsetType)
	{
		ModernFontRenderTypeHelper.normalType = normalType;
		ModernFontRenderTypeHelper.seeThroughType = seeThroughType;
		ModernFontRenderTypeHelper.polygonOffsetType = polygonOffsetType;
	}

	public static RenderType getNormalType(ResourceLocation location)
	{
		return normalType.apply(location);
	}

	public static RenderType getSeeThroughType(ResourceLocation location)
	{
		return seeThroughType.apply(location);
	}

	public static RenderType getPolygonOffsetType(ResourceLocation location)
	{
		return polygonOffsetType.apply(location);
	}
}
