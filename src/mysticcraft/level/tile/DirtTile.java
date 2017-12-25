package mysticcraft.level.tile;

import mysticcraft.entity.item.ItemEntity;
import mysticcraft.entity.mob.Player;
import mysticcraft.gfx.Color;
import mysticcraft.gfx.Screen;
import mysticcraft.item.Item;
import mysticcraft.item.ResourceItem;
import mysticcraft.item.ToolItem;
import mysticcraft.item.resource.Resource;
import mysticcraft.item.type.ToolType;
import mysticcraft.level.Level;
import mysticcraft.sound.Sound;

public class DirtTile extends Tile {

	public DirtTile(int id) {
		super(id); // assigns the id
	}

	public void render(Screen screen, Level level, int x, int y) {
		int col = Color.get(level.dirtColor, level.dirtColor, level.dirtColor - 111, level.dirtColor - 111, -1); // Colors
																												// of
																												// the
																												// dirt
																												// (more
																												// info
																												// in
																												// level.java)
		screen.render(x * 16 + 0, y * 16 + 0, 0, col, 0); // renders the
															// top-left part of
															// the tile
		screen.render(x * 16 + 8, y * 16 + 0, 1, col, 0); // renders the
															// top-right part of
															// the tile
		screen.render(x * 16 + 0, y * 16 + 8, 2, col, 0); // renders the
															// bottom-left part
															// of the tile
		screen.render(x * 16 + 8, y * 16 + 8, 3, col, 0); // renders the
															// bottom-right part
															// of the tile
	}

	public boolean interact(Level level, int xt, int yt, Player player, Item item, int attackDir) {
		if (item instanceof ToolItem) { // if the player's current item is a
										// tool...
			ToolItem tool = (ToolItem) item; // Makes a ToolItem conversion of
												// item.
			if (tool.type == ToolType.SHOVEL) { // if the tool is a shovel...
				if (player.payStamina(4 - tool.level)) { // if the player can
															// pay the
															// stamina...
					level.setTile(xt, yt, Tile.hole, 0); // sets the tile to a
															// hole
					level.add(new ItemEntity(new ResourceItem(Resource.dirt, 1), xt * 16 + random.nextInt(10) + 3, yt * 16 + random.nextInt(10) + 3)); // pops
																																						// out
																																						// a
																																						// dirt
																																						// resource
					Sound.monsterHurt.play();
					return true;
				}
			}
			if (tool.type == ToolType.HOE) { // if the tool is a hoe...
				if (player.payStamina(4 - tool.level)) { // if the player can
															// pay the
															// stamina...
					level.setTile(xt, yt, Tile.farmland, 0); // sets the tile to
																// a FarmTile
					Sound.monsterHurt.play();
					return true;
				}
			}
		}
		return false;
	}
}
