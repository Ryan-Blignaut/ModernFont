package me.thesilverecho.modernfont.mixin;

import com.mojang.blaze3d.font.SheetGlyphInfo;
import me.thesilverecho.modernfont.font.ModernFontManager;
import me.thesilverecho.modernfont.render.ModernFontRenderTypeHelper;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.gui.font.FontTexture;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(FontSet.class)
public abstract class FontSetMixin
{
	@Shadow @Final private ResourceLocation name;
	@Shadow @Final private TextureManager textureManager;
	@Shadow private BakedGlyph missingGlyph;
	@Final @Shadow private List<FontTexture> textures;

	@Inject(method = "stitch", at = @At(value = "HEAD"), cancellable = true)
	private void init(SheetGlyphInfo glyphInfo, CallbackInfoReturnable<BakedGlyph> cir)
	{
		for (FontTexture fonttexture : this.textures)
		{
			BakedGlyph bakedglyph = fonttexture.add(glyphInfo);
			if (bakedglyph != null)
				cir.setReturnValue(bakedglyph);
		}
		final ResourceLocation location = new ResourceLocation(name.getNamespace(), this.name.getPath() + "/" + this.textures.size());
		FontTexture fonttexture = new FontTexture(location, glyphInfo.isColored());

		if (this.name.equals(ModernFontManager.MODERN_FONT))
		{
			((FontTextureAccessor) fonttexture).setNormalType(ModernFontRenderTypeHelper.getNormalType(location));
			((FontTextureAccessor) fonttexture).setSeeThroughType(ModernFontRenderTypeHelper.getSeeThroughType(location));
			((FontTextureAccessor) fonttexture).setPolygonOffsetType(ModernFontRenderTypeHelper.getPolygonOffsetType(location));
		}

		this.textures.add(fonttexture);
		this.textureManager.register(fonttexture.getName(), fonttexture);
		BakedGlyph bakedglyph1 = fonttexture.add(glyphInfo);
		cir.setReturnValue(bakedglyph1 == null ? this.missingGlyph : bakedglyph1);
	}

}
