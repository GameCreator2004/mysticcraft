package mysticcraft.recipe.item;

import mysticcraft.entity.mob.Player;
import mysticcraft.item.ResourceItem;
import mysticcraft.item.resource.Resource;
import mysticcraft.recipe.Recipe;
import tk.jidgu.util.Sys;

public class ResourceRecipe extends Recipe {
	private Resource resource; // The resource used in this recipe
	private int count;
	/**
	 * Adds a recipe to craft a resource
	 * 
	 * @param i
	 */

	public ResourceRecipe(Resource resource, int count) {
		super(new ResourceItem(resource, count)); // this goes through Recipe.java
												// to be put on a list.
		this.resource = resource; // resource to be added
		this.count = count;
	}

	
	/** Adds the resource into your inventory */
	public void craft(Player player) {
		player.inventory.add(0, new ResourceItem(resource, count)); // adds the
																// resource
		Sys.out.println("Crafted " + count + " " + resource.name + "s");
	}
}
