package me.thesilverecho.modernfont.forge;

import me.thesilverecho.modernfont.render.ModernFontShaders;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD, modid = "modernfont")
public class ClientEvents
{
	@SubscribeEvent
	public static void registerShaders(RegisterShadersEvent evt) throws IOException
	{
		ModernFontShaders.registerShaders(evt.getResourceManager(), pair -> evt.registerShader(pair.getFirst(), pair.getSecond()));
	}

}
