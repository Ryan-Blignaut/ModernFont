package me.thesilverecho.modernfont.mixin;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.platform.Window;
import me.thesilverecho.modernfont.font.ModernFontManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.FontManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static net.minecraft.client.Minecraft.DEFAULT_FONT;
import static net.minecraft.client.Minecraft.UNIFORM_FONT;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin
{
	@Mutable @Shadow @Final private FontManager fontManager;

	@Shadow @Final private TextureManager textureManager;
	@Shadow
	public abstract boolean isEnforceUnicode();

	@Mutable @Shadow @Final public Font font;
	@Shadow @Final private ReloadableResourceManager resourceManager;

	@Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;updateVsync(Z)V"))
	public void injectIntoConstructor(Window instance, boolean $$0)
	{
		final ModernFontManager manager = new ModernFontManager(this.textureManager);
		this.fontManager = manager;
		this.font = manager.createFont();
		this.resourceManager.registerReloadListener(manager.getReloadListener());
		this.fontManager.setRenames(/*FontConfig.CLIENT.enabled.get() ?*/true ? ImmutableMap.of(DEFAULT_FONT, ModernFontManager.MODERN_FONT) : this.isEnforceUnicode() ? ImmutableMap.of(DEFAULT_FONT, UNIFORM_FONT) : ImmutableMap.of());
	}
}
