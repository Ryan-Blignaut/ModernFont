package me.thesilverecho.modernfont.mixin;

import net.minecraft.client.gui.font.FontTexture;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FontTexture.class)
public interface FontTextureAccessor
{
	@Accessor("polygonOffsetType")
	@Mutable
	void setPolygonOffsetType(RenderType polygonOffsetType);

	@Accessor("seeThroughType")
	@Mutable
	void setSeeThroughType(RenderType seeThroughType);

	@Accessor("normalType")
	@Mutable
	void setNormalType(RenderType normalType);
}
