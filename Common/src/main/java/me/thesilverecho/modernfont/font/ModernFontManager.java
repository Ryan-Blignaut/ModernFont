package me.thesilverecho.modernfont.font;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.font.GlyphProvider;
import com.mojang.blaze3d.platform.TextureUtil;
import me.thesilverecho.modernfont.utils.FontLog;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.AllMissingGlyphProvider;
import net.minecraft.client.gui.font.FontManager;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

public class ModernFontManager extends FontManager
{
	public static final ResourceLocation MODERN_FONT = new ResourceLocation("modernfont", "font");
	private final FontSet missingFontSet;

	private TextureManager textureManager;

	final Map<ResourceLocation, FontSet> fontSets = Maps.newHashMap();
	private final PreparableReloadListener reloadListener = new SimplePreparableReloadListener<Map<ResourceLocation, List<GlyphProvider>>>()
	{

		@Override
		protected @NotNull Map<ResourceLocation, List<GlyphProvider>> prepare(@NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profilerFiller)
		{
			final Set<Map.Entry<ResourceLocation, List<Resource>>> set = resourceManager.listResourceStacks("font", (location) -> location.getPath().endsWith(".ttf")).entrySet();
			FontLog.LogMessage(set.size() + " fonts found");
			final HashMap<ResourceLocation, List<GlyphProvider>> locationFonts = new HashMap<>();
			final ArrayList<GlyphProvider> value = new ArrayList<>();

			set.forEach(resourceLocationListEntry ->
			{
				final ResourceLocation key = resourceLocationListEntry.getKey();
//				FontConfig.CLIENT.test().add(key.toString());
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

			return locationFonts;
		}

		@Override
		protected void apply(@NotNull Map<ResourceLocation, List<GlyphProvider>> resourceLocationListMap, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profilerFiller)
		{
			profilerFiller.startTick();
			profilerFiller.push("closing");
			ModernFontManager.this.fontSets.values().forEach(FontSet::close);
			ModernFontManager.this.fontSets.clear();
			profilerFiller.popPush("reloading");
			resourceLocationListMap.forEach(($$0x, $$1x) ->
			{
				FontSet $$2x = new FontSet(ModernFontManager.this.textureManager, $$0x);
				$$2x.reload(Lists.reverse($$1x));
				ModernFontManager.this.fontSets.put($$0x, $$2x);
			});
			profilerFiller.pop();
			profilerFiller.endTick();
		}
	};

	@Override
	public @NotNull Font createFont()
	{

//		if (FontConfig.CLIENT.enabled.get())
//		{
//			FontLog.LogMessage(FontConfig.CLIENT.testFontList.get().toString());
//		}
		return new Font(($$0) -> this.fontSets.getOrDefault(MODERN_FONT, this.missingFontSet), false);
	}


	public ModernFontManager(TextureManager textureManager)
	{
		super(textureManager);
		this.textureManager = textureManager;
		this.missingFontSet = Util.make(new FontSet(textureManager, MISSING_FONT), (fontSet) -> fontSet.reload(Lists.newArrayList(new GlyphProvider[]{new AllMissingGlyphProvider()})));

	}

	@Override
	public @NotNull PreparableReloadListener getReloadListener()
	{
		return this.reloadListener;
	}




}
