package net.minecraft.src;

public enum EnumColor {
	
	WHITE(0xffffff),
	YELLOW(0xffff00),
	GREY(0xa0a0a0),
	FFGREY(0xffa0a0a0),
	FFBLACK(0xff000000),
	LIGHTGREY(0xe0e0e0),
	DARKGREY(0x505050);
	
	private EnumColor(int colorHex) {
		this.colorHex = colorHex;
	}
	
	public final int colorHex;
}
