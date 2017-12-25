package mysticcraft.recipe.item;

import mysticcraft.entity.mob.Player;
import mysticcraft.item.Item;
import mysticcraft.recipe.Recipe;
import tk.jidgu.util.Sys;

public class ItemRecipe extends Recipe {

	private Item result;
	
	public ItemRecipe(Item result) {
		super(result);
		this.result = result;
	}

	@Override
	public void craft(Player player) {
		player.inventory.add(result);
		Sys.println("Crafted " + result.getName(), Sys.out);
	}

}
