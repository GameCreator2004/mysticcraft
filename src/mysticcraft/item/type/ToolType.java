package mysticcraft.item.type;

public enum ToolType {

	
	SHOVEL("shvl", 0),
	HOE("hoe", 1),
	SWORD("sword", 2),
	PICKAXE("pickaxe", 3),
	AXE("axe", 4),
	SPEAR("spear", 5),
	HAMMER("hammer", 8);

    public final String name; // name of the type
	public final int sprite; // sprite location on the spritesheet

	ToolType(String name, int sprite) {
		this.name = name; // adds the name
		this.sprite = sprite; // adds the sprite location number
	}
}
