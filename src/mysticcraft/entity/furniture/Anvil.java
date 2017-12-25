package mysticcraft.entity.furniture;

import mysticcraft.crafting.Crafting;
import mysticcraft.entity.mob.Player;
import mysticcraft.gfx.Color;
import mysticcraft.screen.CraftingMenu;

public class Anvil extends Furniture {

	/* This is a sub-class of furniture.java, go there for more info */

	public Anvil() {
		super("Anvil"); // Name of the Anvil
		col = Color.get(-1, 000, 111, 222, -1); // Color of the anvil
		sprite = 0; // Sprite location
		xr = 3; // Width of the anvil (in-game, not sprite)
		yr = 2; // Height of the anvil (in-game, not sprite)
	}

	/** This is what occurs when the player uses the "Menu" command near this */
	public boolean use(Player player, int attackDir) {
		player.game.setMenu(new CraftingMenu(Crafting.AR, player));		
		return true;
	}
}