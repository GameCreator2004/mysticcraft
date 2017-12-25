package mysticcraft.entity.mob;

import mysticcraft.entity.Entity;
import mysticcraft.entity.projectile.MageProjectile;
import mysticcraft.gfx.Color;
import mysticcraft.gfx.Screen;
import mysticcraft.main.Game;
import mysticcraft.sound.Sound;

public class Mage extends Mob {

	public int xa, ya; // x & y acceleration
	private int lvl; // how tough the zombie is
	private int randomWalkTime = 0; // time till next walk
	private boolean tick = false;
	private int attackTime, attackDelay, attackType; // the time and direction
														// of an attack.
	public boolean died = false;
	public int pc = 0;

	public Mage(int lvl) {
		this.lvl = lvl;
		this.setMobName("Mage");
		x = random.nextInt(64 * 16);
		// to 64)]
		y = random.nextInt(64 * 16); // gives it a random y position anywhere
		// between (0 to 1023) [Tile position (0
		// to 64)]
		health = maxHealth = lvl * lvl * 11; // Health based on level
	}

	@Override
	public void tick() {
		super.tick(); // ticks the Entity.java part of this class

		if (level.player != null && randomWalkTime == 0) { // checks if player
															// is on zombies
															// level and if
															// there is no time
															// left on timer
			int xd = level.player.x - x; // gets the horizontal distance between
											// the zombie and the player
			int yd = level.player.y - y; // gets the vertical distance between
											// the zombie and the player
			if (xd * xd + yd * yd < 50 * 50) { // more evil distance checker
												// code
				xa = 0; // sets direction to nothing
				ya = 0;
				if (xd < 0)
					xa = -1; // if the horizontal difference is smaller than 0,
								// then the x acceleration will be 1 (negative
								// direction)
				if (xd > 0)
					xa = +1; // if the horizontal difference is larger than 0,
								// then the x acceleration will be 1
				if (yd < 0)
					ya = -1; // if the vertical difference is smaller than 0,
								// then the y acceleration will be 1 (negative
								// direction)
				if (yd > 0)
					ya = +1; // if the vertical difference is larger than 0,
								// then the y acceleration will be 1
			}
		}

		tick = true;

		// halp david! I have no idea what the & sign does in maths! Unless it's
		// a bit opereator, in which case I'm rusty
		// Calm down, go google "java bitwise AND operator" for information
		// about this. -David
		int speed = tickTime & 1; // Speed is either 0 or 1 depending on the
									// tickTime
		if (!move(xa * speed, ya * speed) || random.nextInt(200) == 0) { // moves
																			// the
																			// zombie,
																			// doubles
																			// as
																			// a
																			// check
																			// to
																			// see
																			// if
																			// it's
																			// still
																			// moving
																			// -OR-
																			// random
																			// chance
																			// out
																			// of
																			// 200
			randomWalkTime = 60; // sets the not-so-random walk time to 60
			xa = (random.nextInt(3) - 1) * random.nextInt(2); // sets the
																// acceleration
																// to random
																// i.e. idling
																// code
			ya = (random.nextInt(3) - 1) * random.nextInt(2); // sets the
																// acceleration
																// to random
																// i.e. idling
																// code
		}
		if (randomWalkTime > 0)
			randomWalkTime--;// if walk time is larger than 0, decrement!
		getSpecialItemAbilities();
	}

	public void render(Screen screen) {
		/* our texture in the png file */
		int xt = 16; // X tile coordinate in the sprite-sheet
		int yt = 14; // Y tile coordinate in the sprite-sheet

		// change the 3 in (walkDist >> 3) to change the time it will take to
		// switch sprite. (bigger number = longer time).
		int flip1 = (walkDist >> 3) & 1; // This will either be a 1 or a 0
											// depending on the walk distance
											// (Used for walking effect by
											// mirroring the sprite)
		int flip2 = (walkDist >> 3) & 1; // This will either be a 1 or a 0
											// depending on the walk distance
											// (Used for walking effect by
											// mirroring the sprite)

		if (dir == 1) { // if facing up
			xt += 2; // change sprite to up
		}
		if (dir > 1) { // if facing left or down

			flip1 = 0; // controls flapping left and right
			flip2 = ((walkDist >> 4) & 1); // This will either be a 1 or a 0
											// depending on the walk distance
											// (Used for walking effect by
											// mirroring the sprite)
			if (dir == 2) { // if facing left
				flip1 = 1; // flip the sprite so it looks like we are facing
							// left
			}
			xt += 4 + ((walkDist >> 3) & 1) * 2; // animation based on walk
													// distance
		}

		/* where to draw the sprite relative to our position */
		int xo = x - 8; // the horizontal location to start drawing the sprite
		int yo = y - 11; // the vertical location to start drawing the sprite

		int col = Color.get(-1, 100, 101, 255, -1); // Level 1 color green
		if (lvl == 2)
			col = Color.get(-1, 100, 522, 255, -1); // lvl 2 colour pink
		if (lvl == 3)
			col = Color.get(-1, 111, 444, 255, -1); // lvl 3 light gray
		if (lvl == 4)
			col = Color.get(-1, 000, 111, 255, -1); // lvl 4 dark grey
		if (hurtTime > 0) { // if hurt
			// SoundUtils.play(Sound.zombieHurt);
			col = Color.get(-1, 555, 555, 555, -1); // make our colour white
			Sound.monsterHurt.play();
		}

		/*
		 * Draws the sprite as 4 different 8*8 images instead of one 16*16 image
		 */
		screen.render(xo + 8 * flip1, yo + 0, xt + yt * 32, col, flip1); // draws
																			// the
																			// top-left
																			// tile
		screen.render(xo + 8 - 8 * flip1, yo + 0, xt + 1 + yt * 32, col, flip1); // draws
																					// the
																					// top-right
																					// tile
		screen.render(xo + 8 * flip2, yo + 8, xt + (yt + 1) * 32, col, flip2); // draws
																				// the
																				// bottom-left
																				// tile
		screen.render(xo + 8 - 8 * flip2, yo + 8, xt + 1 + (yt + 1) * 32, col, flip2); // draws
																						// the
																						// bottom-right
																						// tile
	}

	private void getSpecialItemAbilities() {
		while (tick) {
			if (attackDelay == 0 && attackTime == 0) {
				attackDelay = 60 * 2;
			}

			if (attackDelay > 0) { // if the attackDelay is larger than 0...
				// dir = (attackDelay - 45) / 4 % 4; // the direction of attack.
				// dir = (dir * 2 % 4) + (dir / 2); // direction attack changes
				// if (attackDelay < 45) { // If the attack delay is lower than
				// 45
				// dir = 0; // direction is reset
				// }
				attackDelay--; // minus attack delay by 1
				if (attackDelay == 0) { // if attack delay is equal to 0
					// attackType = 0; // attack type is set to 0.
					attackType = 2;
					attackTime = 60 * 2;
				}
				return; // skips the rest of the code
			}

			if (attackTime > 0) { // if the attackTime is larger than 0
				attackTime--; // attackTime will minus by 1.
				double dir = attackTime * 0.25 * (attackTime % 2 * 2 - 1); // assigns
																			// a
																			// local
																			// direction
																			// variable
																			// from
				// the attack time.
				double speed = (0.7) + attackType * 0.2; // speed is dependent
															// on the
															// attackType.
															// (higher
															// attackType,
				// faster speeds)
				level.add(new MageProjectile(this, Math.cos(dir) * speed, Math.sin(dir) * speed));// adds
																									// a
																									// spark
																									// entity
				attackDelay = 0;
				attackTime = 0;
				attackType = 0;
				return; // skips the rest of the code
			}
		}
	}

	public void touchedBy(Entity entity) {
		if (entity instanceof Player) { // if the entity touches the player
			if (Game.isSurvivalMode && !Game.isCreativeMode) {
				if (lvl == 1) {
					entity.hurt(this, 2, dir);
				}
				if (lvl == 2) {
					entity.hurt(this, 2, dir); // hurts the player, damage is
												// based on lvl.
				}
				if (lvl == 2) {
					entity.hurt(this, 2, dir);
				}
				if (lvl == 3) {
					entity.hurt(this, 3, dir);
				}
			} else if (Game.isCreativeMode && !Game.isCreativeMode) {

			}

		}
	}

	@Override
	protected void die() {
		died = true;
		super.die();
	}

}
