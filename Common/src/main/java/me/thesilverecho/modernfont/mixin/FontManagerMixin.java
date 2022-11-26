package me.thesilverecho.modernfont.mixin;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.font.GlyphProvider;
import com.mojang.blaze3d.platform.TextureUtil;
import me.thesilverecho.modernfont.font.ModernGlyphProvider;
import me.thesilverecho.modernfont.utils.FontLog;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

import static me.thesilverecho.modernfont.font.ModernFontManager.MODERN_FONT;

@Mixin(targets = "net.minecraft.client.gui.font.FontManager$1")
public abstract class FontManagerMixin
{

	@Inject(method = "prepare(Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)Ljava/util/Map;", at = @At("TAIL"))
	private void add(ResourceManager resourceManager, ProfilerFiller profilerFiller, CallbackInfoReturnable<Map<ResourceLocation, List<GlyphProvider>>> cir)
	{
		FontLog.LogMessage("Registering ModernFont");
		final Set<Map.Entry<ResourceLocation, List<Resource>>> set = resourceManager.listResourceStacks("font", (location) -> location.getPath().endsWith(".ttf")).entrySet();
		FontLog.LogMessage(set.size() + " fonts found");
		final HashMap<ResourceLocation, List<GlyphProvider>> locationFonts = new HashMap<>();
		final ArrayList<GlyphProvider> value = new ArrayList<>();

		set.forEach(resourceLocationListEntry ->
		{
			final ResourceLocation key = resourceLocationListEntry.getKey();
//				FontLog.LogMessage(key.toString());

//				FontLog.LogMessage(key.toString().equalsIgnoreCase(FontConfig.CLIENT.test().get(0)));

//				if (key.toString().equalsIgnoreCase(FontConfig.CLIENT.test().get(0)))
			{

				//Get font file data.
				try
				{
					FontLog.LogMessage("Loading font modern font: " + key);
					final STBTTFontinfo fontInfo = STBTTFontinfo.malloc();
					ByteBuffer byteBuffer = TextureUtil.readResource(resourceManager.open(key));
					byteBuffer.flip();
					if (!STBTruetype.stbtt_InitFont(fontInfo, byteBuffer))
						throw new IOException("Invalid ttf");
					value.add(new ModernGlyphProvider(fontInfo));
				} catch (Exception e)
				{
					throw new RuntimeException(e);
				}

				locationFonts.put(MODERN_FONT, value);
			}


		});
		value.forEach(glyphProvider -> glyphProvider.getSupportedGlyphs().forEach(codePoint ->
		{
			if (codePoint == 32)
			{
				return;
			}
			Lists.reverse(value).parallelStream().forEach(font1 -> font1.getGlyph(codePoint));
		}));

		FontLog.LogMessage("Done Registering ModernFont");

		cir.getReturnValue().putAll(locationFonts);

	}

}
