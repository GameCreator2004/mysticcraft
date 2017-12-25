package mysticcraft.recipe.item;

import mysticcraft.entity.mob.Player;
import mysticcraft.item.BucketItem;
import mysticcraft.item.type.BucketState;
import mysticcraft.recipe.Recipe;
import tk.jidgu.util.Sys;

public class BucketRecipe extends Recipe {

	private BucketState bucketState;
	
	public BucketRecipe(BucketState bucketState) {
		super(new BucketItem(bucketState));
		this.bucketState = bucketState;
	}

	@Override
	public void craft(Player player) {
		player.inventory.add(new BucketItem(bucketState));
		
		Sys.println("Crafted " + new BucketItem(bucketState).getName(), Sys.out);
	}

}
