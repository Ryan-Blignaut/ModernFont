/*
package me.thesilverecho.modernfont.font;

import com.mojang.blaze3d.font.SheetGlyphInfo;
import com.mojang.blaze3d.platform.GlStateManager;
import me.thesilverecho.modernfont.utils.FontLog;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;

public class ModernGlyph implements SheetGlyphInfo
{

	private static STBTTFontinfo fontInfo;
	private static float scaleFactor;
	private static float ascent;

	public static void initModernGlyphs(STBTTFontinfo fontInfo, float scaleFactor, float ascent)
	{
		ModernGlyph.fontInfo = fontInfo;
		ModernGlyph.scaleFactor = scaleFactor;
		ModernGlyph.ascent = ascent;
	}


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
		ascent = y + ascent;
		this.advance = advance;
		this.glyphIndex = glyphIndex;
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
	public void upload(int sheetX, int sheetY)
	{
		try (MemoryStack memoryStack = MemoryStack.stackPush())
		{
			IntBuffer w = memoryStack.mallocInt(1);
			IntBuffer h = memoryStack.mallocInt(1);
			IntBuffer x = memoryStack.mallocInt(1);
			IntBuffer y = memoryStack.mallocInt(1);
			final ByteBuffer sdf = STBTruetype.stbtt_GetGlyphSDF(fontInfo, scaleFactor, glyphIndex, 5, (byte) 180, 36, w, h, x, y);
			FontLog.LogMessage(sdf == null ? "sdf is null at second call" : "sdf is not null at second call");

			GlStateManager._pixelStore(GL_UNPACK_ROW_LENGTH, 0);
			GlStateManager._pixelStore(GL_UNPACK_SKIP_PIXELS, 0);
			GlStateManager._pixelStore(GL_UNPACK_SKIP_ROWS, 0);
			GlStateManager._pixelStore(GL_UNPACK_ALIGNMENT, 1);
			if (sdf != null)
			{
				glTexSubImage2D(GL_TEXTURE_2D, 0, sheetX, sheetY, w.get(), h.get(), GL_RED, GL_UNSIGNED_BYTE, sdf);
			}
//			GL11.glTexSubImage2D(GL_TEXTURE_2D, 0, sheetX, sheetY, width, height, GL_RED, GL_UNSIGNED_BYTE, sdf);
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
}
*/
