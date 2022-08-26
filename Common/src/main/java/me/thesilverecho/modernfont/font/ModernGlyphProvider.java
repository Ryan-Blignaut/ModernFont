package me.thesilverecho.modernfont.font;

import com.mojang.blaze3d.font.GlyphInfo;
import com.mojang.blaze3d.font.GlyphProvider;
import it.unimi.dsi.fastutil.ints.IntSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.stb.STBTTFontinfo;

import java.nio.ByteBuffer;

public class ModernGlyphProvider implements GlyphProvider
{


	private final ByteBuffer fontContent;
	private final STBTTFontinfo fontInfo;

	public ModernGlyphProvider(ByteBuffer fontContent, STBTTFontinfo fontInfo)
	{

		this.fontContent = fontContent;
		this.fontInfo = fontInfo;
	}

	@Override
	public void close()
	{
		fontInfo.close();
	}

	@Nullable
	@Override
	public GlyphInfo getGlyph(int $$0)
	{
		return null;
	}

	@Override
	public @NotNull IntSet getSupportedGlyphs()
	{
		return IntSet.of();
	}
}
