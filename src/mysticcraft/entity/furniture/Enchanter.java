package mysticcraft.entity.furniture;

import mysticcraft.crafting.Crafting;
import mysticcraft.entity.mob.Player;
import mysticcraft.gfx.Color;
import mysticcraft.screen.CraftingMenu;

public class Enchanter extends Furniture {
	public Enchanter() {
		super("Enchanter"); 
		col = Color.get(-1, 100, 005, 055, -1); 
		sprite = 6;
		xr = 3; 
		yr = 2; 
	}

	/** This is what occurs when the player uses the "Menu" command near this */
	public boolean use(Player player, int attackDir) {
		player.game.setMenu(new CraftingMenu(Crafting.ECR, player)); // Sets
		return true;
	}
}