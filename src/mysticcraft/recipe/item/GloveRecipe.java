package mysticcraft.recipe.item;

import mysticcraft.entity.mob.Player;
import mysticcraft.item.GloveItem;
import mysticcraft.item.type.GloveType;
import mysticcraft.recipe.Recipe;
import tk.jidgu.util.Sys;

public class GloveRecipe extends Recipe {

	private GloveType gloveType;

	public GloveRecipe(GloveType gloveType) {
		super(new GloveItem(gloveType));
		this.gloveType = gloveType;
	}

	@Override
	public void craft(Player player) {
		player.inventory.add(new GloveItem(gloveType));
		
		Sys.println("Crafted " + new GloveItem(gloveType).getName(), Sys.out);
	}

}
