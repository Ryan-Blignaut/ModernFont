package me.thesilverecho.modernfont.forge.mixin;


import com.google.common.collect.ImmutableMap;
import me.thesilverecho.modernfont.font.ModernFontManager;
import me.thesilverecho.modernfont.mixin.MinecraftAccessor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.Minecraft.DEFAULT_FONT;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen
{
	protected TitleScreenMixin(Component component)
	{
		super(component);
	}

	@Inject(method = "init", at = @At(value = "HEAD"))
	public void init(CallbackInfo ci)
	{
		final boolean[] enabled = {false};

		addRenderableWidget(new ImageButton(this.width / 2 - 124, height - 100, 20, 20, 0, 106, 20, Button.WIDGETS_LOCATION, 256, 256, (p_96791_) ->
		{
			enabled[0] = !enabled[0];
			((MinecraftAccessor) minecraft).getFontManager().setRenames(enabled[0] ? ImmutableMap.of(DEFAULT_FONT, ModernFontManager.MODERN_FONT) : ImmutableMap.of());


		}, Component.translatable("test")));
	}
}
