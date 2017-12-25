package mysticcraft.item;

import mysticcraft.entity.furniture.Furniture;
import mysticcraft.entity.item.ItemEntity;
import mysticcraft.entity.mob.Player;
import mysticcraft.gfx.Color;
import mysticcraft.gfx.Font;
import mysticcraft.gfx.Screen;
import mysticcraft.level.Level;
import mysticcraft.level.tile.Tile;

public class FurnitureItem extends Item {
	public Furniture furniture; // the furniture of this item
	public boolean placed = false; // value if the furniture has been placed or
									// not.

	public FurnitureItem(Furniture furniture) {
		super("item.furniture." + furniture.name.replace(" ", "_"), furniture.name);
		this.furniture = furniture; // Assigns the furniture to the item
	}

	/** Gets the color of the furniture */
	public int getColor() {
		return furniture.col;
	}

	/** Gets the sprite of the furniture */
	public int getSprite() {
		return furniture.sprite + 10 * 32;
	}

	/** Renders the icon used for the furniture */
	public void renderIcon(Screen screen, int x, int y) {
		screen.render(x, y, getSprite(), getColor(), 0); // renders the icon
	}

	/** Renders the icon, and name of the furniture */
	public void renderInventory(Screen screen, int x, int y) {
		screen.render(x, y, getSprite(), getColor(), 0); // renders the icon
		Font.draw(getName(), screen, x + 8, y, Color.get(-1, 555, 555, 555, -1)); // draws
																					// the
																					// name
																					// of
																					// the
																					// furniture
	}

	/**
	 * What happens when you pick up the item off the ground (Not with the power
	 * glove)
	 */
	public void onTake(ItemEntity itemEntity) {
	}

	/** Determines if you can attack enemies with furniture (you can't) */
	public boolean canAttack() {
		return false;
	}

	/**
	 * What happens when you press the "Attack" key with the furniture in your hands
	 */
	public boolean interactOn(Tile tile, Level level, int xt, int yt, Player player, int attackDir) {
		if (tile.mayPass(level, xt, yt, furniture)) { // If the furniture can go
														// on the tile
			furniture.x = xt * 16 + 8; // Placed furniture's X position
			furniture.y = yt * 16 + 8; // Placed furniture's Y position
			level.add(furniture); // adds the furniture to the world
			placed = true; // the value becomes true, which removes it from the
							// player's active item
			return true;
		}
		return false;
	}

	/**
	 * Removes this item from the player's active item slot when depleted is true
	 */
	public boolean isDepleted() {
		return placed;
	}

	/** Gets the name of the furniture */
	public String getName() {
		return super.getName();
	}

	public Item clone() {
		return new FurnitureItem(furniture);
	}
	
}