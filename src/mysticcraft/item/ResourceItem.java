package mysticcraft.item;

import mysticcraft.entity.item.ItemEntity;
import mysticcraft.entity.mob.Player;
import mysticcraft.gfx.Color;
import mysticcraft.gfx.Font;
import mysticcraft.gfx.Screen;
import mysticcraft.item.resource.FoodResource;
import mysticcraft.item.resource.PlantableResource;
import mysticcraft.item.resource.Resource;
import mysticcraft.level.Level;
import mysticcraft.level.tile.Tile;
import mysticcraft.main.Game;

public class ResourceItem extends Item {
	public Resource resource; // The resource of this item
	public int count = 1; // The amount of resources

	public ResourceItem(Resource resource, int count) {
		super("resource." + resource.name.replace(" ", "_"), resource.name);
		this.resource = resource; // assigns the resource
		this.count = count; // assigns the count
	}
	
	public ResourceItem(Resource resource) {
		super("resource." + resource.name.replace(" ", "_"), resource.name);
		this.resource = resource; // assigns the resource
	}

	/** Gets the color of the resource */
	public int getColor() {
		return resource.color;
	}

	/** Gets the sprite of the resource */
	public int getSprite() {
		return resource.sprite;
	}

	/** Renders the icon used for the resource */
	public void renderIcon(Screen screen, int x, int y) {
		screen.render(x, y, resource.sprite, resource.color, 0); // renders the
																	// icon
	}

	/** Renders the icon, name, and count of the resource */
	public void renderInventory(Screen screen, int x, int y) {
		screen.render(x, y, resource.sprite, resource.color, 0); // renders the
																	// icon
		if (Game.isSurvivalMode && !Game.isCreativeMode) {
			if (resource == Resource.rupee) {
				Font.draw(getName(), screen, x + 32, y, Color.get(-1, 153, 153, 153, -1));
			} else {
				Font.draw(getName(), screen, x + 32, y, Color.get(-1, 555, 555, 555, -1));
			}
		} else if (Game.isCreativeMode && !Game.isSurvivalMode) {
			if (resource == Resource.rupee) {
				Font.draw("64 " + getName(), screen, x + 8, y, Color.get(-1, 153, 153, 153, -1));
			} else {
				Font.draw("64 " + getName(), screen, x + 8, y, Color.get(-1, 555, 555, 555, -1));
			}
		}

		if (Game.isSurvivalMode && !Game.isCreativeMode) {
			int cc = count; // count of the resource
			if (cc > 999)
				cc = 999; // If the resource count is above 999, then just
							// render
							// 999 (for spacing reasons)
			Font.draw("" + cc, screen, x + 8, y, Color.get(-1, 444, 444, 444, -1));// draws
																				// the
																				// resource
																				// count
		}
	}

	/** Gets the name of the resource */
	public String getName() {
		return super.getName();
	}

	/** What happens when you pick up the item off the ground */
	public void onTake(ItemEntity itemEntity) {
	}

	/** What happens when you interact and item with the world */
	public boolean interactOn(Tile tile, Level level, int xt, int yt, Player player, int attackDir) {

		// if (resource.interactOn(tile, level, xt, yt, player, attackDir)) //
		// Calls
		// the
		// resource's
		// 'interactOn()'
		// method,
		// if
		// true
		// then...
		if ((resource instanceof FoodResource || resource instanceof PlantableResource) && resource.interactOn(tile, level, xt, yt, player, attackDir)) {
			count--;
			return true;
		}
		// minuses the count by 1.
		return false;
		// return false;
	}

	/** If the count is equal to, or less than 0. Then this will return true. */
	public boolean isDepleted() {
		if (Game.isCreativeMode && !Game.isSurvivalMode) {
			return false;
		} else if (!Game.isCreativeMode && Game.isSurvivalMode) {
			return count <= 0;
		} else {
			return count <= 0;
		}
	}

	public boolean matches(Item item) {
		return super.matches(item) && item instanceof ResourceItem;
	}
}