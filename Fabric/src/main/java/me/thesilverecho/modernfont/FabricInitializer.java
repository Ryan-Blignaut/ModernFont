package me.thesilverecho.modernfont;

import me.thesilverecho.modernfont.fabric.render.ModernFontRenderLayer;
import me.thesilverecho.modernfont.font.FontConfig;
import me.thesilverecho.modernfont.render.ModernFontRenderTypeHelper;
import me.thesilverecho.modernfont.utils.FontLog;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

import static me.thesilverecho.modernfont.font.FontConfig.clientSpec;

@Environment(EnvType.CLIENT)
public class FabricInitializer implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		FontLog.LogMessage("Hello from Fabric!");
		ModLoadingContext.registerConfig("modernfont", ModConfig.Type.CLIENT, clientSpec);
		FontConfig.registerConfig();


		ModernFontRenderTypeHelper.registerRenderLayers(ModernFontRenderLayer::getSdfFontShader, ModernFontRenderLayer::getSdfFontShader, ModernFontRenderLayer::getSdfFontShader);

	}
}
