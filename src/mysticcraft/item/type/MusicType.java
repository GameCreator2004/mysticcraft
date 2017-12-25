package mysticcraft.item.type;

import mysticcraft.gfx.Color;

public enum MusicType {

	NORMAL("Normal",Color.get(-1, 111, 444, 330, -1)),
	SPECIAL("Special",Color.get(-1, 111, 444, 202, -1));
	
	public String name;
	public int color;
	
	MusicType(String name, int color) {
		this.name = name;
		this.color = color;
	}

}