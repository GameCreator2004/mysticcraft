package mysticcraft.recipe;

import java.util.ArrayList;
import java.util.List;

import mysticcraft.entity.mob.Player;
import mysticcraft.gfx.Color;
import mysticcraft.gfx.Font;
import mysticcraft.gfx.Screen;
import mysticcraft.item.Item;
import mysticcraft.item.ResourceItem;
import mysticcraft.item.ToolItem;
import mysticcraft.item.resource.Resource;
import mysticcraft.item.type.ToolType;
import mysticcraft.screen.ListItem;

public abstract class Recipe implements ListItem {
	public List<Item> costs = new ArrayList<Item>(); // A list of costs for the
														// recipe
	public boolean canCraft = false; // checks if the player can craft the
										// recipe
	public Item resultTemplate; // the result item of the recipe
	
	public Recipe(Item resultTemplate) {
		this.resultTemplate = resultTemplate; // assigns the result item
	}

	/**
	 * Adds a resource cost to the list. requires a type of resource and an
	 * amount of it
	 */
	public Recipe addCost(Resource resource, int count) {
		costs.add(new ResourceItem(resource, count)); // adds a resource cost to
														// the list
		return this;
	}
	
	/*public Recipe addCost(Resource resource) {
		costs.add(new ResourceItem(resource, 0));
		return this;
	}*/

	public Recipe addCost(ToolType type, int level) {
		costs.add(new ToolItem(type, level));
		return this;
		
	}
	
	public Recipe addCost(Item item) {
		costs.add(item);
		return this;	
	}
	
	/** Checks if the player can craft the recipe */
	public void checkCanCraft(Player player) {
		for (int i = 0; i < costs.size(); i++) { // cycles through the costs
													// list
			Item item = costs.get(i); // current item on the list
			if (item instanceof ResourceItem) { // if the item is a resource...
				ResourceItem ri = (ResourceItem) item; // Makes a ResourceItem
														// conversion of item.
				if (!player.inventory.hasResources(ri.resource, ri.count)) {// if
																			// the
																			// player
																			// doesn't
																			// have
																			// the
																			// resources...
					canCraft = false;// then the player cannot craft the recipe
					return;
				}
			}
		}
		canCraft = true;// else he can craft the recipe
	}

	/** Renders the icon & text of an item to the screen. */
	public void renderInventory(Screen screen, int x, int y) {
		screen.render(x, y, resultTemplate.getSprite(), resultTemplate.getColor(), 0); // renders
																						// the
																						// sprite
																						// of
																						// the
																						// item
		int textColor = canCraft ? Color.get(-1, 555, 555, 555, -1) : Color.get(-1, 222, 222, 222, -1); // gets
																								// the
																								// text
																								// color
																								// based
																								// on
																								// if
																								// the
																								// player
																								// can
																								// craft
																								// the
																								// item
		Font.draw(resultTemplate.getName(), screen, x + 8, y, textColor); // draws
																			// the
																			// text
																			// to
																			// the
																			// screen
	}

	public abstract void craft(Player player); // abstract method given to the
												// sub-recipe classes.

	/** removes the resources from your inventory */
	public void deductCost(Player player) {
		for (int i = 0; i < costs.size(); i++) {// loops through the costs
			Item item = costs.get(i);// gets the current item in costs
			if (item instanceof ResourceItem) {// if the item is a resource...
				ResourceItem ri = (ResourceItem) item; // Makes a ResourceItem
														// conversion of item.
				player.inventory.removeResource(ri.resource, ri.count); // removes
																		// the
																		// resources
																		// from
																		// the
																		// player's
																		// inventory.
			}
		}
	}
}