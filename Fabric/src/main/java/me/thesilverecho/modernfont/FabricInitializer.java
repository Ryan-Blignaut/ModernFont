package me.thesilverecho.modernfont;

import me.thesilverecho.modernfont.utils.FontLog;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class FabricInitializer implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		FontLog.LogMessage("Hello from Fabric!");
	}
}
