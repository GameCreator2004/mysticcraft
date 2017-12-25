package mysticcraft.level.tile;

import mysticcraft.entity.Entity;
import mysticcraft.entity.item.ItemEntity;
import mysticcraft.entity.mob.Boss;
import mysticcraft.entity.mob.Mob;
import mysticcraft.entity.mob.Player;
import mysticcraft.entity.particle.SmashParticle;
import mysticcraft.entity.particle.TextParticle;
import mysticcraft.gfx.Color;
import mysticcraft.gfx.Screen;
import mysticcraft.item.ResourceItem;
import mysticcraft.item.resource.Resource;
import mysticcraft.level.Level;
import mysticcraft.main.Game;

public class CactusTile extends Tile {
	public CactusTile(int id) {
		super(id); // Assigns the id
		connectsToSand = true; // Can connect to sand
	}

	public void render(Screen screen, Level level, int x, int y) {
		int col = Color.get(20, 40, 50, level.sandColor, -1); // colors of the
															// cactus
		screen.render(x * 16 + 0, y * 16 + 0, 8 + 2 * 32, col, 0); // renders
																	// the
																	// top-left
																	// part of
																	// the
																	// cactus
		screen.render(x * 16 + 8, y * 16 + 0, 9 + 2 * 32, col, 0); // renders
																	// the
																	// top-right
																	// part of
																	// the
																	// cactus
		screen.render(x * 16 + 0, y * 16 + 8, 8 + 3 * 32, col, 0); // renders
																	// the
																	// bottom-left
																	// part of
																	// the
																	// cactus
		screen.render(x * 16 + 8, y * 16 + 8, 9 + 3 * 32, col, 0); // renders
																	// the
																	// bottom-right
																	// part of
																	// the
																	// cactus
	}

	/* Player cannot walk on the cactus (will act as a wall) */
	public boolean mayPass(Level level, int x, int y, Entity e) {
		return false;
	}

	/* Damage do to the cactus by the player */
	public void hurt(Level level, int x, int y, Mob source, int dmg, int attackDir) {
		int damage = 0;
		if (Game.isSurvivalMode && !Game.isCreativeMode) {
			damage = level.getData(x, y) + dmg; // Damage done to the cactus
												// (it's "health" in some
												// sense). dmg is the amount the
												// player did to the cactus.
		} else if (Game.isCreativeMode && !Game.isSurvivalMode) {
			damage = creativeDmg;
		}
		level.add(new SmashParticle(x * 16 + 8, y * 16 + 8)); // creates a smash
																// particle
		level.add(new TextParticle("" + dmg, x * 16 + 8, y * 16 + 8, Color.get(-1, 500, 500, 500, -1))); // creates
																										// a
																										// text
																										// particle
																										// about
																										// how
																										// much
																										// damage
																										// has
																										// been
																										// done.
		if (damage >= 10) { // If the damage is equal to, or larger than 10
							// then...
			int count = random.nextInt(2) + 1; // count is random from 0 to 1
												// and adds one. (1-2 count)
			for (int i = 0; i < count; i++) { // cycles through the count
				level.add(new ItemEntity(new ResourceItem(Resource.cactusFlower, 1), x * 16 + random.nextInt(10) + 3, y * 16 + random.nextInt(10) + 3));// adds
																																						// a
																																						// cactus
																																						// flower
			}
			level.setTile(x, y, Tile.sand, 0); // sets the tile to cactus
		} else {
			level.setData(x, y, damage); // else it will set the data to damage
		}
	}

	public void bumpedInto(Level level, int x, int y, Entity entity) {
		if (entity instanceof Boss) {
			//Sys.println("A cactus can't hurt this entity", System.out);
		} else if (entity instanceof Player) {
			if (Game.isSurvivalMode && !Game.isCreativeMode) {
				entity.hurt(this, x, y, 1); // the player will take 1 damage if
											// they
											// bump into it.
			} else if (Game.isCreativeMode && !Game.isSurvivalMode){
				
			}
		} else {
			entity.hurt(this, x, y, 1);
		}
	}

	public void tick(Level level, int xt, int yt) {
		int damage = level.getData(xt, yt); // gets the amount of damage the
											// cactus has
		if (damage > 0)
			level.setData(xt, yt, damage - 1); // If the number of damage is
												// above 0, then it will minus
												// itself by 1 (heal)
		// Commenter note: I had no idea that cactuses healed themselves.
	}
}