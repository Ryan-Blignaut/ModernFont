package me.thesilverecho.modernfont.font;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.List;

public class FontConfig
{



	public static final FontConfig CLIENT;

	public final ForgeConfigSpec.BooleanValue enabled;
	public ForgeConfigSpec.BooleanValue testConfigSetting;
	public ForgeConfigSpec.ConfigValue<List<? extends String>> testFontList;

	public FontConfig(ForgeConfigSpec.Builder builder)
	{
		builder.push("general");
		testConfigSetting = builder.comment("test").define("test", true);

		builder.pop();


		builder.push("Enabled");
		enabled = builder.comment("Enable/Disable ModernFont").define("enabled", true);
		builder.pop();

		builder.push("Font");
		testFontList = builder.comment("Test")
		                      .defineList("test", Collections.singletonList("modernfont:font/modernsans.ttf"), o -> o instanceof String s && ResourceLocation.tryParse(s) != null);

		builder.pop();


	}


	@SuppressWarnings("unchecked") // NightConfig's types are weird
	public List<String> test()
	{
		return (List<String>) testFontList.get();
	}

	public static final ForgeConfigSpec clientSpec;

	static
	{
		final Pair<FontConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(FontConfig::new);
		clientSpec = specPair.getRight();
		CLIENT = specPair.getLeft();
	}


	public static void registerConfig()
	{
	}


}
