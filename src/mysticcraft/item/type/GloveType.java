package mysticcraft.item.type;

import mysticcraft.gfx.Color;

public enum GloveType {

	POWER("Power", Color.get(-1, 100, 320, 430, -1)),
	HEAL("Heal", Color.get(-1, 100, 300, 500, -1));

	public String type;
	public int color;

	GloveType(String type, int color) {
		this.type = type;
		this.color = color;
	}

}
