package me.thesilverecho.modernfont.forge;

import me.thesilverecho.modernfont.font.FontConfig;
import me.thesilverecho.modernfont.utils.FontLog;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD, modid = "modernfont")
public class ClientEvents
{
	@SubscribeEvent
	public static void onRegisterReloadListeners(RegisterClientReloadListenersEvent event)
	{
		FontLog.LogMessage(FontConfig.CLIENT.enabled.get());
	}
}
