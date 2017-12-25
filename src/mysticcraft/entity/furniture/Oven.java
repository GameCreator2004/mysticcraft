package mysticcraft.entity.furniture;

import mysticcraft.crafting.Crafting;
import mysticcraft.entity.mob.Player;
import mysticcraft.gfx.Color;
import mysticcraft.screen.CraftingMenu;

public class Oven extends Furniture {

	/* This is a sub-class of furniture.java, go there for more info */

	public Oven() {
		super("Oven"); // Name of the oven.
		col = Color.get(-1, 000, 332, 442, -1); // Color of the oven
		sprite = 2; // Location of the sprite
		xr = 3; // Width of the oven
		yr = 2; // Height of the oven
	}

	/** This is what occurs when the player uses the "Menu" command near this */
	public boolean use(Player player, int attackDir) {
		player.game.setMenu(new CraftingMenu(Crafting.OR, player)); // Sets
																				// the
																				// menu
																				// to
																				// the
																				// crafting
																				// menu
																				// with
																				// oven
																				// recipes.
		return true;
	}

	public int getLightRadius() {
		return 3;
	}
}