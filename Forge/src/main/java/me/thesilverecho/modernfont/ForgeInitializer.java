package me.thesilverecho.modernfont;

import me.thesilverecho.modernfont.font.FontConfig;
import me.thesilverecho.modernfont.utils.FontLog;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import static me.thesilverecho.modernfont.font.FontConfig.clientSpec;

@Mod("modernfont")
public class ForgeInitializer
{
	public ForgeInitializer()
	{
		FontLog.LogMessage("Hello from Forge!");
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, clientSpec);

		FontConfig.registerConfig();
	}

}
