package mysticcraft.entity.furniture;

import mysticcraft.crafting.Crafting;
import mysticcraft.entity.mob.Player;
import mysticcraft.gfx.Color;
import mysticcraft.screen.CraftingMenu;

public class GoldenWorkbench extends Furniture {

	public GoldenWorkbench() {
		super("Gold Workbench");
		col = Color.get(-1, 330, 550, 553, -1);
		sprite = 4;
		xr = 3; // Width of the workbench
		yr = 2; // Height of the workbench
	}

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
