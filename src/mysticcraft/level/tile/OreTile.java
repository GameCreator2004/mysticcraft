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
import mysticcraft.item.Item;
import mysticcraft.item.ResourceItem;
import mysticcraft.item.ToolItem;
import mysticcraft.item.resource.Resource;
import mysticcraft.item.type.ToolType;
import mysticcraft.level.Level;
import mysticcraft.main.Game;

public class OreTile extends Tile {
	private Resource toDrop; // the resource the ore drops
	private int color; // the color of the ore

	public OreTile(int id, Resource toDrop) {
		super(id); // assigns the id
		this.toDrop = toDrop; // assigns the drop
		this.color = toDrop.color & 0xffff00; // assigns the color based off the
												// resource's color with an edit
												// to the red & green in the
												// color.
	}

	public void render(Screen screen, Level level, int x, int y) {
		color = (toDrop.color & 0xffffff00) + Color.get(level.dirtColor); // adds
																			// the
																			// background
																			// dirt
																			// color
																			// to
																			// the
																			// sprite
		screen.render(x * 16 + 0, y * 16 + 0, 17 + 1 * 32, color, 0); // renders
																		// the
																		// top
																		// left
																		// of
																		// the
																		// ore
		screen.render(x * 16 + 8, y * 16 + 0, 18 + 1 * 32, color, 0); // renders
																		// the
																		// top
																		// right
																		// of
																		// the
																		// ore
		screen.render(x * 16 + 0, y * 16 + 8, 17 + 2 * 32, color, 0); // renders
																		// the
																		// bottom
																		// left
																		// of
																		// the
																		// ore
		screen.render(x * 16 + 8, y * 16 + 8, 18 + 2 * 32, color, 0); // renders
																		// the
																		// bottom
																		// right
																		// of
																		// the
																		// ore
	}

	/** Determines if the player can walk through the ore (they can't) */
	public boolean mayPass(Level level, int x, int y, Entity e) {
		return false;
	}

	/** When you punch the ore, it will do 0 damage to the ore */
	public void hurt(Level level, int x, int y, Mob source, int dmg, int attackDir) {
		if (Game.isSurvivalMode && !Game.isCreativeMode) {
			hurt(level, x, y, 0);
		} else if (Game.isCreativeMode && !Game.isSurvivalMode) {
			hurt(level, x, y, creativeDmg);
		}
	}

	/** What happens when you hit the ore with a item */
	public boolean interact(Level level, int xt, int yt, Player player, Item item, int attackDir) {
		if (item instanceof ToolItem) { // If the item happens to be a tool...
			ToolItem tool = (ToolItem) item; // converts the Item object into a
												// ToolItem object.
			if (tool.type == ToolType.PICKAXE) { // if the tool type is a
													// pickaxe...
				if (player.payStamina(6 - tool.level)) { // if the player can
															// pay the
															// stamina...
					hurt(level, xt, yt, 1); // Will do 1 damage to the ore
					return true; // returns true
				}
			}
		}
		return false; // returns false
	}

	/** Hurts the ore by an amount of damage */
	public void hurt(Level level, int x, int y, int dmg) {
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
																// particle to
																// the level
		level.add(new TextParticle("" + dmg, x * 16 + 8, y * 16 + 8, Color.get(-1, 500, 500, 500, -1))); // creates
																										// a
																										// text
																										// particle
																										// telling
																										// you
																										// how
																										// much
																										// damage
																										// you
																										// have
																										// done.
		if (dmg > 0) { // if the damage is above 0...
			int count = random.nextInt(2); // A random number between 0 to 1
			if (damage >= random.nextInt(10) + 3) { // if the number of damage
													// the ore has is ((random
													// number between 0 to 9) +
													// 3) then...
				level.setTile(x, y, Tile.dirt, 0); // set the level to dirt
				count += 2; // adds 2 to the count variable
			} else {
				level.setData(x, y, damage); // else it adds up the damage that
												// the ore has.
			}
			for (int i = 0; i < count; i++) {// loops through the count
				level.add(new ItemEntity(new ResourceItem(toDrop, random.nextInt(2)), x * 16 + random.nextInt(10) + 3, y * 16 + random.nextInt(10) + 3)); // drops
																																							// the
																																							// resource
																																							// to
																																							// the
																																							// world
			}
		}
	}

	/** This is what happens when an entity runs into the ore */
	public void bumpedInto(Level level, int x, int y, Entity entity) {
		if (entity instanceof Boss) {
		} else if (entity instanceof Player) {
			if (Game.isSurvivalMode && !Game.isCreativeMode) {
				entity.hurt(this, x, y, 3);
			} else if (Game.isCreativeMode && !Game.isSurvivalMode) {}
		} else {
			entity.hurt(this, x, y, 3); // does 3 damage to the entity. Ouch!
		}
	}
}