package mysticcraft.item;

import java.util.Random;

import mysticcraft.entity.Entity;
import mysticcraft.entity.item.ItemEntity;
import mysticcraft.gfx.Color;
import mysticcraft.gfx.Font;
import mysticcraft.gfx.Screen;
import mysticcraft.item.type.ToolType;

public class ToolItem extends Item {
	private Random random = new Random();

	public static final int MAX_LEVEL = 5; // How many different levels of tools
											// there are
	public static final String[] LEVEL_NAMES = { "Wood", "Rock", "A.Rock", "Iron", "Gold", "Gem", "H.Rock", "Rupee", "Air"
			// names
			// of
			// the
			// different
			// levels.
			// Later
			// levels
			// means
			// stronger
			// tool
	};

	public static final int[] LEVEL_COLORS = { // Colors of the tools, same
												// position as LEVEL_NAMES
			Color.get(-1, 100, 321, 431, -1), // Colors for Wood tools
			Color.get(-1, 100, 321, 111, -1), // Colors for Rock/Stone tools
			Color.get(-1, 100, 321, 500, -1), // Colors for Apple tools
			Color.get(-1, 100, 321, 555, -1), // Colors for Iron tools
			Color.get(-1, 100, 321, 550, -1), // Colors for Gold tools
			Color.get(-1, 100, 321, 404, -1), // Colors for Gem tools
			Color.get(-1, 100, 321, 334, -1), // Colors for Hard Rock tools
			Color.get(-1, 100, 321, 141, -1), 
			Color.get(-1, 055, 055, 555, -1) 
	};

	public ToolType type; // Type of tool (Sword, hoe, axe, pickaxe, shovel,
							// spear)
	public int level = 0; // Level of said tool

	private int getLevelColors() {
		return LEVEL_COLORS[level];
	}
	
	/**
	 * Tool Item, requires a tool type (ToolType.sword, ToolType.axe,
	 * ToolType.hoe, etc) and a level (0 = wood, 2 = iron, 4 = gem, etc)
	 */
	public ToolItem(ToolType type, int level) {
		super("tool." + LEVEL_NAMES[level] + "_" + type.name, LEVEL_NAMES[level] + " " + type.name);
		this.type = type; // type of tool for this item
		this.level = level; // level of tool for this item
		// Sys.println("Tool type and level in the inventory: " +
		// LEVEL_NAMES[level].toUpperCase() + " " + type.name.toUpperCase(),
		// System.out);
	}

	/** Gets the colors for this tool */
	public int getColor() {
		return getLevelColors();
	}

	/** gets the sprite for this tool */
	public int getSprite() {
		return type.sprite + 5 * 32;
	}

	/** Renders the icon for this tool on the screen */
	public void renderIcon(Screen screen, int x, int y) {
		screen.render(x, y, getSprite(), getColor(), 0);
	}

	/** Renders the icon & name of this tool for inventory/crafting purposes. */
	public void renderInventory(Screen screen, int x, int y) {
		screen.render(x, y, getSprite(), getColor(), 0);
		if (level == 7) {
			int col = Color.get(-1, 153, 153, 153, -1); 
			Font.draw(getName(), screen, x + 8, y, col);
		} else {
			Font.draw(getName(), screen, x + 8, y, Color.get(-1, 555, 555, 555, -1));
		}
	}
	
	
	/** Gets the name of this tool (and it's type) */
	public String getName() {
		if(this.type == ToolType.SHOVEL) {
			itemNameInJSONFile = "tool." + LEVEL_NAMES[level].toLowerCase() + "_" + type.name.replace("shvl", "shovel");
		}
		return super.getName();
	}

	public void onTake(ItemEntity itemEntity) {
	}

	/** Can attack mobs with tools. */
	public boolean canAttack() {
		return true;
	}

	/** Calculates Damage */
	public int getAttackDamageBonus(Entity e) {
		if (type == ToolType.AXE) {
			return (level + 1) * 2 + random.nextInt(4); // axes: (level + 1) * 2
														// + random number
														// beteween 0 and 3, do
														// slightly less damage
														// than swords.
		}
		if (type == ToolType.SWORD) {
			return (level + 1) * 3 + random.nextInt(2 + level * level * 2); // swords:
																			// (level
																			// +
																			// 1)
																			// *
																			// 3
																			// +
																			// random
																			// number
																			// between
																			// 0
																			// and
																			// (2
																			// +
																			// level
																			// *
																			// level
																			// *
																			// 2)
		}
		if (type == ToolType.SPEAR) {
			return (level + 1) * 4 + random.nextInt(2 + level * level * 3);
		}
		return 1;
	}

	/** Sees if this item matches another. */
	public boolean matches(Item item) {
		if (item instanceof ToolItem) {
			ToolItem other = (ToolItem) item;
			if (other.type != type)
				return false;
			if (other.level != level)
				return false;
			return true;
		}
		return false;
	}

}
