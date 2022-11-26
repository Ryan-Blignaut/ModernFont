package me.thesilverecho.modernfont.fabric.mixin;

import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RenderType.class)
public interface RenderTypeAccessor
{


	@Invoker("create")
	static RenderType.CompositeRenderType create(String name, VertexFormat vertexFormat, VertexFormat.Mode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, RenderType.CompositeState phases)
	{
		throw new IllegalStateException("Mixin did not apply");
	}

}
