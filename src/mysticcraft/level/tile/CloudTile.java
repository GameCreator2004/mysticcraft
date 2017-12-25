package mysticcraft.level.tile;

import mysticcraft.entity.Entity;
import mysticcraft.entity.mob.Player;
import mysticcraft.gfx.Color;
import mysticcraft.gfx.Screen;
import mysticcraft.item.Item;
import mysticcraft.item.ToolItem;
import mysticcraft.item.type.ToolType;
import mysticcraft.level.Level;

public class CloudTile extends Tile {
	public CloudTile(int id) {
		super(id);// assigns the id
	}

	/*
	 * Oh boy, it's one of these more complicated connecting tiles classes.
	 * 
	 * Sorry if I can't explain these well - David.
	 */

	public void render(Screen screen, Level level, int x, int y) {
		int col = Color.get(444, 444, 555, 555, -1); // Color of the cloud
		int transitionColor = Color.get(333, 444, 555, -1, -1); // Transitional
															// color between
															// connections

		boolean u = level.getTile(x, y - 1) == Tile.infiniteFall; // Checks if
																	// the tile
																	// above it
																	// is a
																	// infiniteFall
																	// tile.
		boolean d = level.getTile(x, y + 1) == Tile.infiniteFall; // Checks if
																	// the tile
																	// below it
																	// is a
																	// infiniteFall
																	// tile.
		boolean l = level.getTile(x - 1, y) == Tile.infiniteFall; // Checks if
																	// the tile
																	// to the
																	// left is a
																	// infiniteFall
																	// tile.
		boolean r = level.getTile(x + 1, y) == Tile.infiniteFall; // Checks if
																	// the tile
																	// to the
																	// right is
																	// a
																	// infiniteFall
																	// tile.

		boolean ul = level.getTile(x - 1, y - 1) == Tile.infiniteFall; // Checks
																		// if
																		// the
																		// upper-left
																		// tile
																		// is an
																		// infiniteFall
																		// tile.
		boolean dl = level.getTile(x - 1, y + 1) == Tile.infiniteFall; // Checks
																		// if
																		// the
																		// lower-left
																		// tile
																		// is an
																		// infiniteFall
																		// tile.
		boolean ur = level.getTile(x + 1, y - 1) == Tile.infiniteFall; // Checks
																		// if
																		// the
																		// upper-right
																		// tile
																		// is an
																		// infiniteFall
																		// tile.
		boolean dr = level.getTile(x + 1, y + 1) == Tile.infiniteFall; // Checks
																		// if
																		// the
																		// lower-right
																		// tile
																		// is an
																		// infiniteFall
																		// tile.

		/*
		 * Commenter Note: All sentences with a "*" at the end means I'm making
		 * a guess, and not 100% sure. Please confirm it sometime in the future.
		 */

		if (!u && !l) { // If there is no infiniteFall tile above or to the left
						// of this...
			if (!ul) // if there is no infiniteFall tile at the upper-left
						// corner then...
				screen.render(x * 16 + 0, y * 16 + 0, 17, col, 0); // render it
																	// as a
																	// normal
																	// flat
																	// cloud
																	// tile*
			else
				screen.render(x * 16 + 0, y * 16 + 0, 7 + 0 * 32, transitionColor, 3); // else
																						// render
																						// it
																						// as
																						// a
																						// corner
																						// piece*
		} else
			screen.render(x * 16 + 0, y * 16 + 0, (l ? 6 : 5) + (u ? 2 : 1) * 32, transitionColor, 3); // else
																										// have
																										// it
																										// render
																										// like
																										// a
																										// end
																										// peace*

		if (!u && !r) { // If there is no infiniteFall tile above or to the
						// right of this...
			if (!ur) // if there is no infiniteFall tile at the upper-right
						// corner then...
				screen.render(x * 16 + 8, y * 16 + 0, 18, col, 0); // render it
																	// as a
																	// normal
																	// flat
																	// cloud
																	// tile*
			else
				screen.render(x * 16 + 8, y * 16 + 0, 8 + 0 * 32, transitionColor, 3); // else
																						// render
																						// it
																						// as
																						// a
																						// corner
																						// piece*
		} else
			screen.render(x * 16 + 8, y * 16 + 0, (r ? 4 : 5) + (u ? 2 : 1) * 32, transitionColor, 3); // else
																										// have
																										// it
																										// render
																										// like
																										// a
																										// end
																										// peace*

		if (!d && !l) { // If there is no infiniteFall tile below or to the left
						// of this...
			if (!dl) // if there is no infiniteFall tile at the lower-left
						// corner then...
				screen.render(x * 16 + 0, y * 16 + 8, 20, col, 0); // render it
																	// as a
																	// normal
																	// flat
																	// cloud
																	// tile*
			else
				screen.render(x * 16 + 0, y * 16 + 8, 7 + 1 * 32, transitionColor, 3); // else
																						// render
																						// it
																						// as
																						// a
																						// corner
																						// piece*
		} else
			screen.render(x * 16 + 0, y * 16 + 8, (l ? 6 : 5) + (d ? 0 : 1) * 32, transitionColor, 3); // else
																										// have
																										// it
																										// render
																										// like
																										// a
																										// end
																										// peace*
		if (!d && !r) { // If there is no infiniteFall tile below or to the
						// right of this...
			if (!dr) // if there is no infiniteFall tile at the lower-right
						// corner then...
				screen.render(x * 16 + 8, y * 16 + 8, 19, col, 0); // render it
																	// as a
																	// normal
																	// flat
																	// cloud
																	// tile*
			else
				screen.render(x * 16 + 8, y * 16 + 8, 8 + 1 * 32, transitionColor, 3); // else
																						// render
																						// it
																						// as
																						// a
																						// corner
																						// piece*
		} else
			screen.render(x * 16 + 8, y * 16 + 8, (r ? 4 : 5) + (d ? 0 : 1) * 32, transitionColor, 3); // else
																										// have
																										// it
																										// render
																										// like
																										// a
																										// end
																										// peace*
	}

	/* Players can entities can walk on clouds */
	public boolean mayPass(Level level, int x, int y, Entity e) {
		return true;
	}

	public boolean interact(Level level, int xt, int yt, Player player, Item item, int attackDir) {
		if (item instanceof ToolItem) { // If the player's current item is a
										// tool...
			ToolItem tool = (ToolItem) item; // Makes a ToolItem conversion of
												// item.
			if (tool.type == ToolType.SHOVEL) { // If the tool is a shovel...
				if (player.payStamina(5)) { // If the player can pay 5 stamina
					return true;
				}
			}
		}
		return false;
	}

}
