package mysticcraft.entity.furniture;

import mysticcraft.crafting.Crafting;
import mysticcraft.entity.mob.Player;
import mysticcraft.gfx.Color;
import mysticcraft.screen.CraftingMenu;

public class Workbench extends Furniture {

	/* This is a sub-class of furniture.java, go there for more info */

	public Workbench() {
		super("Workbench"); // Name of the Workbench
		col = Color.get(-1, 100, 321, 431, -1); // Color of the workbench
		sprite = 4; // Location of the sprite
		xr = 3; // Width of the workbench
		yr = 2; // Height of the workbench
	}

	/** This is what occurs when the player uses the "Menu" command near this */
	public boolean use(Player player, int attackDir) {
		player.game.setMenu(new CraftingMenu(Crafting.WBR, player)); // Sets
																					// the
																					// menu
																					// to
																					// the
																					// crafting
																					// menu
																					// with
																					// workbench
																					// recipes.
		return true;
	}
}