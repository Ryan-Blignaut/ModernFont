package me.thesilverecho.modernfont.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;
import java.util.function.Consumer;

public class ModernFontShaders
{
	private static ShaderInstance sdfFontShader;

	public static void registerShaders(ResourceManager resourceManager, Consumer<Pair<ShaderInstance, Consumer<ShaderInstance>>> registrations) throws IOException
	{
		registrations.accept(Pair.of(
				new ShaderInstance(resourceManager, "font_sdf", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP),
				shaderInstance -> sdfFontShader = shaderInstance)
		);
	}

	public static ShaderInstance getSdfFontShader()
	{
		return sdfFontShader;
	}
}
