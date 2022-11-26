package me.thesilverecho.modernfont.font;

import com.mojang.blaze3d.font.GlyphInfo;
import com.mojang.blaze3d.font.GlyphProvider;
import com.mojang.blaze3d.font.SheetGlyphInfo;
import com.mojang.blaze3d.platform.GlStateManager;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.function.Function;
import java.util.stream.IntStream;

import static org.lwjgl.opengl.GL11.*;

//ModernGlyphProvider is based on the TrueTypeGlyphProvider class from the vanilla game.
public class ModernGlyphProvider implements GlyphProvider
{
	//Stb font info.
	private final STBTTFontinfo fontInfo;
	private final float scaleFactor;
	private final float ascent;

	public ModernGlyphProvider(STBTTFontinfo fontInfo)
	{

		this.fontInfo = fontInfo;
		this.scaleFactor = STBTruetype.stbtt_ScaleForPixelHeight(fontInfo, 11);
		try (MemoryStack memoryStack = MemoryStack.stackPush())
		{
			IntBuffer ascent = memoryStack.mallocInt(1);
			IntBuffer descent = memoryStack.mallocInt(1);
			IntBuffer lineGap = memoryStack.mallocInt(1);
			STBTruetype.stbtt_GetFontVMetrics(fontInfo, ascent, descent, lineGap);
			this.ascent = (float) ascent.get(0) * this.scaleFactor;
		}

	}

	@Override
	public void close()
	{
		fontInfo.close();
	}

	@Nullable
	@Override
	public GlyphInfo getGlyph(int codePoint)
	{
		try (MemoryStack memoryStack = MemoryStack.stackPush())
		{
			//Buffers to hold width, height, x and y coordinates of the glyph
			IntBuffer w = memoryStack.mallocInt(1);
			IntBuffer h = memoryStack.mallocInt(1);
			IntBuffer x = memoryStack.mallocInt(1);
			IntBuffer y = memoryStack.mallocInt(1);

			//This is the glyph for the code point.
			final int glyphIndex = STBTruetype.stbtt_FindGlyphIndex(this.fontInfo, codePoint);
			//Character codepoint is not defined in the font.
			if (glyphIndex == 0) return null;


			//TODO: remove this call
			STBTruetype.stbtt_GetGlyphSDF(this.fontInfo, this.scaleFactor, glyphIndex, 5, (byte) 200, 200, w, h, x, y);

			//Glyph has no width or height
			if (w.get(0) == 0 || y.get(0) == 0) return null;
			IntBuffer advanceWidth = memoryStack.mallocInt(1);
			IntBuffer leftSideBearing = memoryStack.mallocInt(1);
			//Horizontal metrics of glyph (NB.unscaled coordinates)
			STBTruetype.stbtt_GetGlyphHMetrics(this.fontInfo, glyphIndex, advanceWidth, leftSideBearing);

			return new ModernGlyph(w.get(0), h.get(0), x.get(0), y.get(0), advanceWidth.get(0) * scaleFactor, leftSideBearing.get(0) * scaleFactor, glyphIndex);
		}

	}

	@Override
	public @NotNull IntSet getSupportedGlyphs()
	{
		return IntStream.range(0, 65535).collect(IntOpenHashSet::new, IntCollection::add, IntCollection::addAll);
	}


	private class ModernGlyph implements GlyphInfo
	{
		private final float ascent;
		private final int width;
		private final int height;
		private final float bearingX;
		private final float advance;
		private final int glyphIndex;

		public ModernGlyph(int width, int height, int x, int y, float advance, float leftSideBearing, int glyphIndex)
		{
			this.width = width;
			this.height = height;
			this.bearingX = leftSideBearing + x;
			this.ascent = y + ModernGlyphProvider.this.ascent;
			this.advance = advance;
			this.glyphIndex = glyphIndex;
		}

		@Override
		public float getAdvance()
		{
			return this.advance;
		}

		@Override
		public @NotNull BakedGlyph bake(Function<SheetGlyphInfo, BakedGlyph> function)
		{
			return function.apply(new SheetGlyphInfo()
			{
				@Override
				public int getPixelWidth()
				{
					return width;
				}

				@Override
				public int getPixelHeight()
				{
					return height;
				}

				@Override
				public float getBearingX()
				{
					return bearingX;
				}

				@Override
				public float getBearingY()
				{
					return ascent;
				}

				@Override
				public void upload(int sheetX, int sheetY)
				{
					try (MemoryStack memoryStack = MemoryStack.stackPush())
					{
						IntBuffer w = memoryStack.mallocInt(1);
						IntBuffer h = memoryStack.mallocInt(1);
						IntBuffer x = memoryStack.mallocInt(1);
						IntBuffer y = memoryStack.mallocInt(1);
						//Create the sdf and save it to the buffer.
						final ByteBuffer sdf = STBTruetype.stbtt_GetGlyphSDF(ModernGlyphProvider.this.fontInfo, ModernGlyphProvider.this.scaleFactor, glyphIndex, 5, (byte) 180, 36, w, h, x, y);

						//Setups the gl params for the texture.
						GlStateManager._pixelStore(GL_UNPACK_ROW_LENGTH, 0);
						GlStateManager._pixelStore(GL_UNPACK_SKIP_PIXELS, 0);
						GlStateManager._pixelStore(GL_UNPACK_SKIP_ROWS, 0);
						GlStateManager._pixelStore(GL_UNPACK_ALIGNMENT, 1);
//							GlStateManager._texSubImage2D(GL_TEXTURE_2D, 0, sheetX, sheetY, w.get(), h.get(), GL_RED, GL_UNSIGNED_BYTE, sdf.getLong());
						if (sdf != null)
							glTexSubImage2D(GL_TEXTURE_2D, 0, sheetX, sheetY, w.get(), h.get(), GL_RED, GL_UNSIGNED_BYTE, sdf);
					}
				}

				@Override
				public boolean isColored()
				{
					return false;
				}

				@Override
				public float getOversample()
				{
					return 1;
				}
			});
		}
	}

}
