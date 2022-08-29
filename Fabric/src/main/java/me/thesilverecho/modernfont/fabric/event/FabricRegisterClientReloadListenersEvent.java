package me.thesilverecho.modernfont.fabric.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.packs.resources.ReloadableResourceManager;

public interface FabricRegisterClientReloadListenersEvent
{
	Event<FabricRegisterClientReloadListenersEvent> EVENT = EventFactory.createArrayBacked(FabricRegisterClientReloadListenersEvent.class,
			(listeners) -> (resourceManager) ->
			{
				for (FabricRegisterClientReloadListenersEvent listener : listeners)
				{
					listener.register(resourceManager);
				}
			});

	void register(ReloadableResourceManager resourceManager);


}
