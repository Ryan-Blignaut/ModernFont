package me.thesilverecho.modernfont.mixin;

import com.mojang.blaze3d.font.GlyphProvider;
import me.thesilverecho.modernfont.utils.FontLog;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.FontManager;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Mixin(FontManager.class)
public abstract class FontManagerMixin
{
	@Shadow @Final Map<ResourceLocation, FontSet> fontSets;
	@Shadow @Final private FontSet missingFontSet;

	@Inject(method = "createFont", at = @At("HEAD"), cancellable = true)
	public void replaceTextVanillaFont(CallbackInfoReturnable<Font> cir)
	{
		FontLog.LogDebug("ModernFont: Injecting into FontManager");
		cir.setReturnValue(new Font(id -> this.fontSets.getOrDefault(new ResourceLocation("modernfont:custom_font"), this.missingFontSet), false));
	}

	@Inject(method = "getReloadListener", at = @At("HEAD"), cancellable = true)
	public void replaceReloadListener(CallbackInfoReturnable<PreparableReloadListener> cir)
	{
		cir.setReturnValue(new SimplePreparableReloadListener<Map<ResourceLocation, List<GlyphProvider>>>()
		{
			@Override
			protected @NotNull Map<ResourceLocation, List<GlyphProvider>> prepare(@NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profilerFiller)
			{
				final Set<Map.Entry<ResourceLocation, List<Resource>>> set = resourceManager.listResourceStacks("font", (location) -> location.getPath().endsWith(".ttf")).entrySet();
				FontLog.LogMessage(set.size() + " fonts found");
				set.forEach(resourceLocationListEntry -> FontLog.LogMessage(resourceLocationListEntry.getKey().toString()));
				final HashMap<ResourceLocation, List<GlyphProvider>> locationFonts = new HashMap<>();

				return locationFonts;
			}

			@Override
			protected void apply(@NotNull Map<ResourceLocation, List<GlyphProvider>> resourceLocationListMap, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profilerFiller)
			{

			}
		});
	}

	private static void cacheGlyphs(List<GlyphProvider> glyphProviders)
	{

	}


}
