package mysticcraft.item;

import mysticcraft.entity.Entity;
import mysticcraft.entity.furniture.Furniture;
import mysticcraft.entity.mob.Player;
import mysticcraft.gfx.Color;
import mysticcraft.gfx.Font;
import mysticcraft.gfx.Screen;
import mysticcraft.item.type.GloveType;
import mysticcraft.level.Level;
import mysticcraft.level.tile.Tile;
import mysticcraft.sound.Sound;
import tk.jidgu.util.Sys;

public class GloveItem extends Item {

	private GloveType gloveType;
	private int staminaCost;

	public GloveItem(GloveType gloveType) {
		super("item." + gloveType.type + "_" + "glove", gloveType.type + " " + "Glove");
		this.gloveType = gloveType;
		this.setGloveType(gloveType);
	}

	public String getName() {
		return super.getName();
	}


	public int getColor() {
		return gloveType.color;
	}

	public int getSprite() {
		return 7 + 4 * 32; // returns the location of the sprite(image of the
							// glove)
	}

	public void renderIcon(Screen screen, int x, int y) {
		screen.render(x, y, getSprite(), getColor(), 0); // Renders the icon of
															// the power glove
															// to the screen
	}

	public void renderInventory(Screen screen, int x, int y) {
		if (getGloveType() == GloveType.POWER) {
			screen.render(x, y, getSprite(), getColor(), 0); // renders the icon of
																// the power glove
																// to the screen
			Font.draw(getName(), screen, x + 8, y, Color.get(-1, 100, 320, 430, -1)); // renders the name of the powerglove
																					// to the screen
		}
		if (getGloveType() == GloveType.HEAL) {
			screen.render(x, y, getSprite(), getColor(), 0);
			Font.draw(getName(), screen, x + 8, y, Color.get(-1, 100, 300, 500, -1));
		}
	}

	public boolean interact(Player player, Entity entity, int attackDir) {
		if (entity instanceof Furniture) { // If the power glove hits a furnace
			Furniture f = (Furniture) entity; // Assigns the furniture
			f.take(player); // Takes (picks up) the furniture
			return true; // Method returns true
		}
		return false; // method returns false if it did not hit a furniture
						// entity.
	}

	public boolean interactOn(Tile tile, Level level, int xt, int yt, Player player, int attackDir) {
		if (getGloveType() == GloveType.HEAL) {
			if (player.health < player.maxHealth && player.payStamina(staminaCost)) {
				player.heal(5);
				Sys.println("Healed the player", Sys.out);
				player.payStamina(3);
				Sound.healGlove.play();
				return true;
			}
		}
		return false;
	}

	public GloveType getGloveType() {
		return gloveType;
	}

	public void setGloveType(GloveType gloveType) {
		this.gloveType = gloveType;
	}

	public boolean canAttack() {
		if (getGloveType() == GloveType.HEAL) {
			return true;
		} else
			return false;
	}

	@Override
	public int getAttackDamage(Entity e) {
		if (getGloveType() == GloveType.HEAL) {
			return 4;
		} else
			return 0;
	}

	/*
	 * @Override public boolean consumeable() { return true; }
	 */
	
	public Item clone() {
		return new GloveItem(gloveType);
	}
}
