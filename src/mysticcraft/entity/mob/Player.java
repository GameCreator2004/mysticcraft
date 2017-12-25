package mysticcraft.entity.mob;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;

import com.google.gson.JsonObject;

import mysticcraft.entity.Entity;
import mysticcraft.entity.furniture.Anvil;
import mysticcraft.entity.furniture.Chest;
import mysticcraft.entity.furniture.Enchanter;
import mysticcraft.entity.furniture.Furnace;
import mysticcraft.entity.furniture.Furniture;
import mysticcraft.entity.furniture.GoldenWorkbench;
import mysticcraft.entity.furniture.Jukebox;
import mysticcraft.entity.furniture.Lantern;
import mysticcraft.entity.furniture.Oven;
import mysticcraft.entity.furniture.Torch;
import mysticcraft.entity.furniture.Workbench;
import mysticcraft.entity.item.Inventory;
import mysticcraft.entity.item.ItemEntity;
import mysticcraft.entity.particle.TextParticle;
import mysticcraft.entity.projectile.PlayerSpark;
import mysticcraft.gfx.Color;
import mysticcraft.gfx.Font;
import mysticcraft.gfx.Screen;
import mysticcraft.input.InputHandler;
import mysticcraft.item.BucketItem;
import mysticcraft.item.FurnitureItem;
import mysticcraft.item.GloveItem;
import mysticcraft.item.Item;
import mysticcraft.item.ResourceItem;
import mysticcraft.item.ToolItem;
import mysticcraft.item.resource.Resource;
import mysticcraft.item.special.BackpackItem;
import mysticcraft.item.special.MusicDiscItem;
import mysticcraft.item.special.SkyTotemItem;
import mysticcraft.item.type.BucketState;
import mysticcraft.item.type.GloveType;
import mysticcraft.item.type.MusicType;
import mysticcraft.item.type.ToolType;
import mysticcraft.level.Level;
import mysticcraft.level.tile.Tile;
import mysticcraft.main.Game;
import mysticcraft.screen.InventoryMenu;
import mysticcraft.screen.PauseMenu;
import mysticcraft.sound.Sound;

public class Player extends Mob {
	public InputHandler input; // keyboard input by the player
	private int attackTime, attackDir, attackDelay, attackType; // the time and
																// direction of
																// an attack.

	public int xa, ya = 0;
	///////////////////////
	// Random r = new Random();
	///////////////////////

	public Game game; // the game the player is in
	public Inventory inventory = new Inventory(this); // the inventory of the
														// player
	public Item attackItem; // the player's attack item
	public Item activeItem; // the player's active item
	public int stamina; // the player's stamina
	public int staminaRecharge; // the recharge rate of the player's stamina
	public int staminaRechargeDelay; // the recharge delay when the player uses
										// up their stamina.
	public int score; // the player's score
	public int maxStamina = 10; // the maximum stamina that the player can have
	private int onStairDelay; // the delay before changing levels.
	public int invulnerableTime = 0; // the invulnerability time the player
										// has// when he is hit
	private int itemStackCount = 64;
	// Note: the player's health & max health are inherited from Mob.java

	// special
	public boolean hasSkyTotem = false;
	public boolean hasTool = false;

	//
	public Player(Game game, InputHandler input) {
		this.game = game; // assigns the game that the player is in
		this.input = input; // assigns the input
		this.setMobName("Player");
		x = 24; // players x position
		y = 24; // players y position
		stamina = maxStamina; // assigns the stamina to be the max stamina (10)
		// this.canSwim(true); //return true or false if the player can swim or
		// can't
		// swim

		/*
		 * inventory.add(new ResourceItem(Resource.blackDye)); inventory.add(new
		 * FurnitureItem(new GoldenWorkbench())); inventory.add(new
		 * FurnitureItem(new Jukebox())); inventory.add(new FurnitureItem(new
		 * Enchanter())); inventory.add(new GloveItem(GloveType.HEAL));
		 * inventory.add(new MusicDiscItem(MusicType.SPECIAL));
		 * inventory.add(new ToolItem(ToolType.SPEAR, 6)); inventory.add(new
		 * SkyTotemItem()); inventory.add(new
		 * BucketItem(BucketState.WATER_FILLED));
		 */
		// this.inventory.items.clear();
		// this.inventory.add(new FurnitureItem(new Shelf()));
		//inventory.add(new FurnitureItem(new Anvil()));
		//inventory.add(new ToolItem(ToolType.HAMMER, 3));
		if (Game.isSurvivalMode && !Game.isCreativeMode) {
			//this.inventory.add(new TestItem());
			this.inventory.add(new FurnitureItem(new Workbench()));
			this.inventory.add(new FurnitureItem(new Enchanter()));
			this.inventory.add(new GloveItem(GloveType.POWER));
		} else if (Game.isCreativeMode && !Game.isSurvivalMode) {
			addAllItems();
		}
	}

	public void tick() {
		super.tick(); // ticks the parent (Mob.java)

		// if (Game.isCreativeMode) {
		// this.inventory.items.clear();
		// addAllItems();
		/// }
		if (Game.isCreativeMode && !Game.isSurvivalMode) {
			health = maxHealth = 100;
		}
		//
		if (invulnerableTime > 0)
			invulnerableTime--; // if invulnerableTime is above 0, then minus it
								// by 1.
		Tile onTile = level.getTile(x >> 4, y >> 4); // gets the current tile
														// the player is on.
		if (onTile == Tile.stairsDown || onTile == Tile.stairsUp) { // if the
																	// tile is a
																	// stairs up

			// or stairs
			// down...
			if (onStairDelay == 0) { // if the stair delay is 0 then...
				changeLevel((onTile == Tile.stairsUp) ? 1 : -1); // change level
																	// depending
																	// on if the
																	// Tile is
																	// an
																	// stairsUp
																	// or not.
				onStairDelay = 10; // creates a stair delay of 10.
				return; // skips the rest of the code
			}
			onStairDelay = 10; // stair delay is set to 10.
		} else {
			if (onStairDelay > 0)
				onStairDelay--; // if stair delay is above 0, then minus it by
								// 1.
		}

		/*
		 * if stamina is smaller or equal to 0, and if the recharge & recharge
		 * delay are both 0 then...
		 */
		if (stamina <= 0 && staminaRechargeDelay == 0 && staminaRecharge == 0) {
			staminaRechargeDelay = 40; // the recharge delay will equal 40
		}

		if (staminaRechargeDelay > 0) { // if the recharge delay is above 0
										// then...
			staminaRechargeDelay--; // minus the recharge delay by 1.
		}

		if (staminaRechargeDelay == 0) { // if the stamina recharge delay is 0
											// then...
			staminaRecharge++; // the stamina recharge adds up.
			if (isSwimming()) { // if the player is swimming then...
				staminaRecharge = 0; // the recharge is 0.
			}
			while (staminaRecharge > 10) { // while the stamina recharge is
											// above 10 then...
				staminaRecharge -= 10; // minus the recharge by 10
				if (stamina < maxStamina)
					stamina++; // if the player's stamina is less than their max
								// stamina then add 1 stamina.
			}
		}

		int xa = 0; // x acceleration
		int ya = 0; // y acceleration

		this.xa = xa;
		this.ya = ya;

		if (input.up.down)
			this.ya--; // if the player presses up then his y acceleration will
						// be -1
		if (input.down.down)
			this.ya++; // if the player presses down then his y acceleration
						// will
						// be 1
		if (input.left.down)
			this.xa--; // if the player presses left then his x acceleration
						// will
						// be -1
		if (input.right.down)
			this.xa++; // if the player presses up right his x acceleration will
						// be 1

		if (score >= 90) {
			score = 0;
		}
		if (isSwimming() && tickTime % 60 == 0) { // if the player is swimming
													// and the remainder of
													// (tickTime/60) equals 0
													// then...
			if (stamina > 0) { // if stamina is above 0 then...
				stamina--; // minus 0 by 1.
			} else { // else
				if (Game.isSurvivalMode && !Game.isCreativeMode) {
					hurt(this, 1, dir ^ 1); // do 1 damage to the player
				} else if (Game.isCreativeMode && !Game.isSurvivalMode) {
				}
			}
		}

		if (staminaRechargeDelay % 2 == 0) { // if the remainder of
												// (staminaRechargeDelay/2)
												// equals 0 then...
			move(this.xa, this.ya); // move the player in the x & y acceleration
		}

		if (input.attack.clicked) { // if the player presses the attack
									// button...
			if (stamina == 0) { // if the player's stamina is 0...
				// nothing
			} else {
				if (Game.isSurvivalMode && !Game.isCreativeMode) {
					stamina--; // minus the stamina by 1
					staminaRecharge = 0; // the recharge is set to 0
				} else if (Game.isCreativeMode && !Game.isSurvivalMode) {
				}
				attack(); // calls the attack() method
			}
		}

		if (input.menu.clicked) { // if the player presses the menu button...
			if (!use()) { // if the use() method returns false then (aka: no
							// furniture in-front of the player)
				game.setMenu(new InventoryMenu(this)); // set the current menu
														// to the inventory menu
			}
		}

		if (input.pause.clicked) {
			game.setMenu(new PauseMenu());
		}

		if (attackTime > 0)
			attackTime--; // if the attack time is larger than 0 then minus it
							// by 1

		getSpecialItemAbilities();
	}

	private boolean use() {
		if (dir == 0 && use(x - 8, y + 4 - 2, x + 8, y + 12 - 2))
			return true; // if the entity below has a use() method then return
							// true
		if (dir == 1 && use(x - 8, y - 12 - 2, x + 8, y - 4 - 2))
			return true; // if the entity above has a use() method then return
							// true
		if (dir == 3 && use(x + 4, y - 8 - 2, x + 12, y + 8 - 2))
			return true; // if the entity to the right has a use() method then
							// return true
		if (dir == 2 && use(x - 12, y - 8 - 2, x - 4, y + 8 - 2))
			return true; // if the entity to the left has a use() method then
							// return true
		return false;
	}

	private void attack() {
		walkDist += 8; // increase the walkDist (changes the sprite)
		attackDir = dir; // the attack direction equals the current direction
		attackItem = activeItem; // the attackItem is the active item
		boolean done = false; // not done.

		if (activeItem != null) { // if the player has a active Item
			attackTime = 10; // attack time will be set to 10.
			int yo = -2; // y offset
			int range = 12; // range from an object
			/*
			 * if the interaction between you and an entity is successful then
			 * done = true
			 */
			if (dir == 0 && interact(x - 8, y + 4 + yo, x + 8, y + range + yo))
				done = true;
			if (dir == 1 && interact(x - 8, y - range + yo, x + 8, y - 4 + yo))
				done = true;
			if (dir == 3 && interact(x + 4, y - 8 + yo, x + range, y + 8 + yo))
				done = true;
			if (dir == 2 && interact(x - range, y - 8 + yo, x - 4, y + 8 + yo))
				done = true;
			if (done)
				return; // if done = true, then skip the rest of the code.

			int xt = x >> 4; // current x-tile coordinate you are on.
			int yt = (y + yo) >> 4; // current y-tile coordinate you are on.
			int r = 12; // radius
			if (attackDir == 0)
				yt = (y + r + yo) >> 4; // gets the tile below that you are
										// attacking.
			if (attackDir == 1)
				yt = (y - r + yo) >> 4; // gets the tile above that you are
										// attacking.
			if (attackDir == 2)
				xt = (x - r) >> 4; // gets the tile to the left that you are
									// attacking.
			if (attackDir == 3)
				xt = (x + r) >> 4; // gets the tile to the right that you are
									// attacking.

			if (xt >= 0 && yt >= 0 && xt < level.w && yt < level.h) { // if (xt
																		// & yt)
																		// are
																		// larger
																		// or
																		// equal
																		// to 0
																		// and
																		// less
																		// than
																		// the
																		// level's
																		// width
																		// and
																		// height...
				if (activeItem.interactOn(level.getTile(xt, yt), level, xt, yt, this, attackDir)) { // if
																									// the
																									// interactOn()
																									// method
																									// in
																									// an
																									// item
																									// returns
																									// true...
					done = true; // done equals true
				} else {
					if (level.getTile(xt, yt).interact(level, xt, yt, this, activeItem, attackDir)) { // if
																										// the
																										// interact()
																										// method
																										// in
																										// an
																										// item
																										// returns
																										// true...
						done = true; // done equals true
					}
				}
				if (activeItem.isDepleted()) { // if the activeItem has 0
												// resources left then...
					activeItem = null; // removes the active item.
				}
			}
		}

		if (done)
			return; // if done is true, then skip the rest of the code

		if (activeItem == null || activeItem.canAttack()) { // if there is no
															// active item, OR
															// if the item can
															// be used to
															// attack...
			attackTime = 5; // attack time = 5
			int yo = -2; // y offset
			int range = 20; // range of attack
			if (dir == 0)
				hurt(x - 8, y + 4 + yo, x + 8, y + range + yo); // attacks the
																// entity below
																// you.
			if (dir == 1)
				hurt(x - 8, y - range + yo, x + 8, y - 4 + yo); // attacks the
																// entity above
																// you.
			if (dir == 3)
				hurt(x + 4, y - 8 + yo, x + range, y + 8 + yo); // attacks the
																// entity to the
																// right of you.
			if (dir == 2)
				hurt(x - range, y - 8 + yo, x - 4, y + 8 + yo); // attacks the
																// entity to the
																// left of you.

			int xt = x >> 4; // current x-tile coordinate you are on.
			int yt = (y + yo) >> 4; // current y-tile coordinate you are on.
			int r = 12; // radius
			if (attackDir == 0)
				yt = (y + r + yo) >> 4; // gets the tile below that you are
										// attacking.
			if (attackDir == 1)
				yt = (y - r + yo) >> 4; // gets the tile above that you are
										// attacking.
			if (attackDir == 2)
				xt = (x - r) >> 4; // gets the tile to the left that you are
									// attacking.
			if (attackDir == 3)
				xt = (x + r) >> 4; // gets the tile to the right that you are
									// attacking.

			if (xt >= 0 && yt >= 0 && xt < level.w && yt < level.h) { // if (xt
																		// & yt)
																		// are
																		// larger
																		// or
																		// equal
																		// to 0
																		// and
																		// less
																		// than
																		// the
																		// level's
																		// width
																		// and
																		// height...
				level.getTile(xt, yt).hurt(level, xt, yt, this, random.nextInt(3) + 1, attackDir); // calls
																									// the
																									// hurt()
																									// method
																									// in
																									// that
																									// tile's
																									// class
			}
		}

	}

	/**
	 * if the entity in-front of the player has a use() method, it will call it.
	 */
	private boolean use(int x0, int y0, int x1, int y1) {
		List<Entity> entities = level.getEntities(x0, y0, x1, y1); // gets the
																	// entities
																	// within
																	// the 4
																	// points
		for (int i = 0; i < entities.size(); i++) { // cycles through the
													// entities
			Entity e = entities.get(i); // gets the current entity
			if (e != this)
				if (e.use(this, attackDir))
					return true; // if the entity is not the player, and has a
									// use() method then return true.
		}
		return false;
	}

	/**
	 * if the entity in-front of the player has a interact() method, it will
	 * call it
	 */
	private boolean interact(int x0, int y0, int x1, int y1) {
		List<Entity> entities = level.getEntities(x0, y0, x1, y1); // gets the
																	// entities
																	// within
																	// the 4
																	// points
		for (int i = 0; i < entities.size(); i++) { // cycles through the
													// entities
			Entity e = entities.get(i); // gets the current entity
			if (e != this)
				if (e.interact(this, activeItem, attackDir))
					return true; // if the entity is not the player, and has a
									// interact() method then return true.
		}
		return false;
	}

	/**
	 * if the entity in-front of the player has a hurt() method, it will call it
	 */
	private void hurt(int x0, int y0, int x1, int y1) {
		List<Entity> entities = level.getEntities(x0, y0, x1, y1); // gets the
																	// entities
																	// within
																	// the 4
																	// points
		for (int i = 0; i < entities.size(); i++) { // cycles through the
													// entities
			Entity e = entities.get(i); // gets the current entity
			if (e != this)
				e.hurt(this, getAttackDamage(e), attackDir); // if the entity is
																// not the
																// player, and
																// has a hurt()
																// method then
																// return true.
		}
	}

	/** Gets the attack damage the player has */
	private int getAttackDamage(Entity e) {
		int dmg = random.nextInt(3) + 1; // damage is equal to a random number
											// between 1 and 3
		if (attackItem != null) { // if the current attack item isn't null
			dmg += attackItem.getAttackDamageBonus(e); // adds the attack damage
														// bonus (from a
														// sword/axe)
		}
		return dmg;
	}

	private void getSpecialItemAbilities() {
		if (activeItem instanceof SkyTotemItem) {
			hasSkyTotem = true;

		} else
			hasSkyTotem = false;

		if (activeItem instanceof ToolItem) {
			hasTool = true;
		} else {
			hasTool = false;
		}

		if (game.level == game.levels[4] || (game.level == game.levels[0] || game.level == game.levels[1] || game.level == game.levels[2])) {

			while (hasSkyTotem) {
				invulnerableTime = 1;
				if (attackDelay == 0 && attackTime == 0) {
					attackDelay = 60 * 2;
				}

				if (attackDelay > 0) { // if the attackDelay is larger than 0...
					// dir = (attackDelay - 45) / 4 % 4; // the direction of
					// attack.
					// dir = (dir * 2 % 4) + (dir / 2); // direction attack
					// changes
					// if (attackDelay < 45) { // If the attack delay is lower
					// than
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
																				// the
																				// attack
																				// time.
					double speed = (0.7) + attackType * 0.2; // speed is
																// dependent
																// on the
																// attackType.
																// (higher
																// attackType,
																// faster
																// speeds)
					level.add(new PlayerSpark(this, Math.cos(dir) * speed, Math.sin(dir) * speed));// adds
																									// a
																									// spark
																									// entity
																									// with
																									// the
																									// cosine
																									// and
																									// sine
																									// of
																									// dir
																									// times
																									// speed.
					level.add(new PlayerSpark(this, -Math.cos(dir) * speed, -Math.sin(dir) * speed));// adds
																										// a
																										// spark
																										// entity
																										// with
																										// the
																										// cosine
																										// and
																										// sine
																										// of
																										// dir
																										// times
																										// speed.
					return; // skips the rest of the code
				}
			}

			if (!hasSkyTotem) {
				if (attackDelay >= 60 * 2)
					attackDelay = 0;

				if (attackTime >= 60 * 2)
					attackTime = 0;
				invulnerableTime = 0;
			}
		}
	}

	/** Draws the player on the screen */
	@SuppressWarnings("deprecation")
	public void render(Screen screen) {
		int xt = 0; // X tile coordinate in the sprite-sheet
		int yt = 14; // Y tile coordinate in the sprite-sheet

		int flip1 = (walkDist >> 3) & 1; // This will either be a 1 or a 0
											// depending on the walk distance
											// (Used for walking effect by
											// mirroring the sprite)
		int flip2 = (walkDist >> 3) & 1; // This will either be a 1 or a 0
											// depending on the walk distance
											// (Used for walking effect by
											// mirroring the sprite)

		if (dir == 1) { // if the direction is 1 (Up)...
			xt += 2; // then move the sprite over 2 tiles
		}
		if (dir > 1) { // if the direction is larger than 1 (left or right)...
			flip1 = 0; // flip1 will equal 0.
			flip2 = ((walkDist >> 4) & 1); // This will either be a 1 or a 0
											// depending on the walk distance
											// (Used for walking effect by
											// mirroring the sprite)
			if (dir == 2) { // if the direction is 2 (left)
				flip1 = 1; // mirror the sprite
			}
			xt += 4 + ((walkDist >> 3) & 1) * 2; // animation based on walk
													// distance
		}

		/* where to draw the sprite relative to our position */
		int xo = x - 8; // the horizontal offset location to start drawing the
						// sprite
		int yo = y - 11; // the vertical offset location to start drawing the
							// sprite
		if (isSwimming()) { // if the player is swimming...
			yo += 4; // y offset is moved up by 4
			int waterColor = Color.get(-1, -1, 115, 335, -1); // color of water
																// circle
			if (tickTime / 8 % 2 == 0) { // if the remainder of (tickTime/8)/2
											// is equal to 0...
				waterColor = Color.get(-1, 335, 5, 115, -1); // change the color
																// of
																// water circle
			}
			screen.render(xo + 0, yo + 3, 5 + 13 * 32, waterColor, 0); // render
																		// the
																		// water
																		// graphic
			screen.render(xo + 8, yo + 3, 5 + 13 * 32, waterColor, 1); // render
																		// the
																		// mirrored
																		// water
																		// graphic
																		// to
																		// the
																		// right.
		}

		if (attackTime > 0 && attackDir == 1) { // if the attack time is larger
												// than 0 and the attack
												// Direction is 1 (Up)
			screen.render(xo + 0, yo - 4, 6 + 13 * 32, Color.get(-1, 555, 555, 555, -1), 0); // render
																								// a
																								// half-slash
			screen.render(xo + 8, yo - 4, 6 + 13 * 32, Color.get(-1, 555, 555, 555, -1), 1); // render
																								// a
																								// mirrored
																								// half-slash
																								// to
																								// the
																								// right
																								// of
																								// it.
			if (attackItem != null) { // if the player has an item
				attackItem.renderIcon(screen, xo + 4, yo - 4); // then render
																// the icon of
																// the item.
			}
		}

		int col = Color.get(-1, 100, 220, 532, -1); // color of the player

		if (activeItem instanceof SkyTotemItem) {
			xt += 8;
			col = Color.get(-1, 100, 055, 550, -1); // Color used in the top
													// half of
			// the sprite
		}

		if (activeItem instanceof GloveItem) {
			GloveItem glove = (GloveItem) activeItem;
			if (glove.getGloveType() == GloveType.HEAL) {
				col = Color.get(-1, 100, 500, 532, -1);
			}
		}

		if (hurtTime > 0) { // if the player is hurt...
			col = Color.get(-1, 555, 555, 555, -1); // then the color is white
		}

		if (activeItem instanceof FurnitureItem) { // if the active item is a
													// furniture item
			yt += 2; // moves the y tile 2 over. (for the player holding his
						// hands up)
		}

		screen.render(xo + 8 * flip1, yo + 0, xt + yt * 32, col, flip1); // render
																			// the
																			// top-left
																			// part
																			// of
																			// the
																			// sprite
		screen.render(xo + 8 - 8 * flip1, yo + 0, xt + 1 + yt * 32, col, flip1); // render
																					// the
																					// top-right
																					// part
																					// of
																					// the
																					// sprite
		if (!isSwimming()) { // if the player is NOT swimming
			screen.render(xo + 8 * flip2, yo + 8, xt + (yt + 1) * 32, col, flip2); // render
																					// the
																					// bottom-left
																					// part
																					// of
																					// the
																					// sprite
			screen.render(xo + 8 - 8 * flip2, yo + 8, xt + 1 + (yt + 1) * 32, col, flip2); // render
																							// the
																							// bottom-right
																							// part
																							// of
																							// the
																							// sprite
		}

		if (attackTime > 0 && attackDir == 2) { // if the attack time is larger
												// than 0 and the attack
												// Direction is 2 (Left)
			screen.render(xo - 4, yo, 7 + 13 * 32, Color.get(-1, 555, 555, 555, -1), 1); // render
																							// a
																							// half-slash
			screen.render(xo - 4, yo + 8, 7 + 13 * 32, Color.get(-1, 555, 555, 555, -1), 3); // render
																								// a
																								// mirrored
																								// half-slash
																								// below
																								// it.
			if (attackItem != null) { // if the player has an item
				attackItem.renderIcon(screen, xo - 4, yo + 4); // then render
																// the icon of
																// the item.
			}
		}
		if (attackTime > 0 && attackDir == 3) { // if the attack time is larger
												// than 0 and the attack
												// Direction is 3 (Right)
			screen.render(xo + 8 + 4, yo, 7 + 13 * 32, Color.get(-1, 555, 555, 555, -1), 0); // render
																								// a
																								// half-slash
			screen.render(xo + 8 + 4, yo + 8, 7 + 13 * 32, Color.get(-1, 555, 555, 555, -1), 2); // render
																									// a
																									// mirrored
																									// half-slash
																									// below
																									// it.
			if (attackItem != null) { // if the player has an item
				attackItem.renderIcon(screen, xo + 8 + 4, yo + 4); // then
																	// render
																	// the icon
																	// of the
																	// item.
			}
		}
		if (attackTime > 0 && attackDir == 0) { // if the attack time is larger
												// than 0 and the attack
												// Direction is 0 (Down)
			screen.render(xo + 0, yo + 8 + 4, 6 + 13 * 32, Color.get(-1, 555, 555, 555, -1), 2); // render
																									// a
																									// half-slash
			screen.render(xo + 8, yo + 8 + 4, 6 + 13 * 32, Color.get(-1, 555, 555, 555, -1), 3); // render
																									// a
																									// mirrored
																									// half-slash
																									// to
																									// the
																									// right
																									// of
																									// it.
			if (attackItem != null) { // if the player has an item
				attackItem.renderIcon(screen, xo + 4, yo + 8 + 4); // then
																	// render
																	// the icon
																	// of the
																	// item.
			}
		}

		if (activeItem instanceof FurnitureItem) { // if the active Item is a
													// furniture item.
			Furniture furniture = ((FurnitureItem) activeItem).furniture; // gets
																			// the
																			// furniture
																			// of
																			// that
																			// item
			furniture.x = x; // the x position is that of the player's
			furniture.y = yo; // the y position is that of the player's
			furniture.render(screen); // renders the furniture on the screen
										// (above his hands)

		}

		if (Game.GAME_JSON_FILE.exists()) {
			try {
				if (FileUtils.readFileToString(Game.GAME_JSON_FILE).contains("mysticcraft.playername")) {
					Object obj = Game.jsonParser.parse(new FileReader(Game.GAME_JSON_FILE));
					JsonObject jsonObject = (JsonObject) obj;
					String customPlayerName = (String) jsonObject.get("mysticcraft.playername").getAsString();
					Font.draw(customPlayerName, screen, xo, yo - 10, Color.get(-1, 555, 555, 555, -1));
				} else {

				}
			} catch (Exception e) {
				try {
					FileUtils.writeStringToFile(Game.GAME_JSON_FILE, e.toString());
				} catch (IOException e1) {
					//e1.printStackTrace();
				}
			}
		} else {
			// Sys.println("No player name", Sys.out);
		}
	}

	/** What happens when the player interacts with a itemEntity */
	public void touchItem(ItemEntity itemEntity) {
		itemEntity.take(this); // calls the take() method in ItemEntity
		inventory.add(itemEntity.item); // adds the item into your inventory
	}

	/** Returns if the entity can swim */
	public boolean canSwim() {
		return true; // yes the player can swim
	}

	/** Finds a start position for the player to start in. */
	public boolean findStartPos(Level level) {
		while (true) { // will loop until it returns
			int x = random.nextInt(level.w); // gets a random value between 0
												// and the world's width - 1
			int y = random.nextInt(level.h); // gets a random value between 0
												// and the world's height - 1
			if (level.getTile(x, y) == Tile.grass) { // if the tile at the x & y
														// coordinates is a
														// grass tile then...
				this.x = x * 16 + 8; // the player's x coordinate will be in the
										// middle of the tile
				this.y = y * 16 + 8; // the player's y coordinate will be in the
										// middle of the tile
				return true; // returns and stop's the loop
			}
		}
	}

	/** Pays the stamina used for an action */
	public boolean payStamina(int cost) {
		if (cost > stamina)
			return false; // if the player doesn't have enough stamina, then
							// return false
		stamina -= cost; // minus the current stamina by the cost
		return true; // return true
	}

	/** Changes the level */
	public void changeLevel(int dir) {
		game.scheduleLevelChange(dir); // schedules a level change.
	}

	/** Gets the player's light radius underground */
	public int getLightRadius() {
		int r = 2; // the radius of the light.

		while (hasSkyTotem) {
			return 999999;
		}

		if (activeItem != null) { // if the player has an item
			if (activeItem instanceof FurnitureItem) { // if item is a furniture
														// item
				int rr = ((FurnitureItem) activeItem).furniture.getLightRadius(); // gets
																					// the
																					// furniture's
																					// light
																					// radius
				if (rr > r)
					r = rr; // if the furniture's light radius is larger than
							// the player's, then the players light radius will
							// equal that of the furniture's.
			}
		}
		return r; // return the radius
	}

	/** What happens when the player dies */
	protected void die() {
		super.die();
		Sound.playerDeath.play();// plays a sound
	}

	/** What happens when the player touches an entity */
	public void touchedBy(Entity entity) {

		if (!(entity instanceof Player)) { // if the entity is not a player.
			entity.touchedBy(this); // calls the touchedBy() method in the
									// entity's class
		}
	}

	/** What happens when the player is hurt */
	protected void doHurt(int damage, int attackDir) {
		if (hurtTime > 0 || invulnerableTime > 0)
			return; // if hurt time OR invulnerableTime is above 0, then skip
					// the rest of the code.

		Sound.playerHurt.play(); // plays a sound
		level.add(new TextParticle("" + damage, x, y, Color.get(-1, 504, 504, 504, -1))); // adds
																							// a
																							// text
																							// particle
																							// telling
																							// how
																							// much
																							// damage
																							// was
																							// done.
		if (Game.isSurvivalMode && !Game.isCreativeMode) {
			health -= damage; // health is decreased by the damage amount
		} else if (Game.isCreativeMode && !Game.isSurvivalMode) {

		}
		if (attackDir == 0)
			yKnockback = +6; // if the direction was from below, then get
								// knocked above.
		if (attackDir == 1)
			yKnockback = -6; // if the direction was from above, then get
								// knocked below.
		if (attackDir == 2)
			xKnockback = -6; // if the direction was from the right, then get
								// knocked to the left.
		if (attackDir == 3)
			xKnockback = +6; // if the direction was from the left, then get
								// knocked to the right.
		hurtTime = 10; // hurt time set to 10
		invulnerableTime = 30; // invulnerable time is set to 30
	}

	/** What happens when the player wins */
	// public void gameWon() {
	// level.player.invulnerableTime = 60 * 5; // sets the invulnerable time to
	// 300
	// game.won(); // win the game
	// }

	public void addAllItems() {
		inventory.add(new ToolItem(ToolType.HAMMER, 1));
		inventory.add(new ResourceItem(Resource.lapis, itemStackCount));
		inventory.add(new ResourceItem(Resource.lapisOre, itemStackCount));
		inventory.add(new ResourceItem(Resource.rupee, itemStackCount));
		addAllFurniture();
		inventory.add(new GloveItem(GloveType.POWER));
		inventory.add(new GloveItem(GloveType.HEAL));
		inventory.add(new SkyTotemItem());
		inventory.add(new BucketItem(BucketState.EMPTY));
		inventory.add(new BucketItem(BucketState.WATER_FILLED));
		inventory.add(new BucketItem(BucketState.LAVA_FILLED));
		inventory.add(new BucketItem(BucketState.SAND_FILLED));
		inventory.add(new MusicDiscItem(MusicType.NORMAL));
		inventory.add(new MusicDiscItem(MusicType.SPECIAL));
		inventory.add(new BackpackItem());
		addAllToolItems();
		addAllResources();
	}

	public void addAllResources() {
		inventory.add(new ResourceItem(Resource.goldenApple, itemStackCount));
		inventory.add(new ResourceItem(Resource.wood, itemStackCount));
		inventory.add(new ResourceItem(Resource.stone, itemStackCount));
		inventory.add(new ResourceItem(Resource.hardStone, itemStackCount));
		inventory.add(new ResourceItem(Resource.yellowFlower, itemStackCount));
		inventory.add(new ResourceItem(Resource.flower, itemStackCount));
		inventory.add(new ResourceItem(Resource.acorn, itemStackCount));
		inventory.add(new ResourceItem(Resource.dirt, itemStackCount));
		inventory.add(new ResourceItem(Resource.sand, itemStackCount));
		inventory.add(new ResourceItem(Resource.cactusFlower, itemStackCount));
		inventory.add(new ResourceItem(Resource.seeds, itemStackCount));
		inventory.add(new ResourceItem(Resource.wheat, itemStackCount));
		inventory.add(new ResourceItem(Resource.bread, itemStackCount));
		inventory.add(new ResourceItem(Resource.apple, itemStackCount));
		inventory.add(new ResourceItem(Resource.appleStone, itemStackCount));
		inventory.add(new ResourceItem(Resource.coal, itemStackCount));
		inventory.add(new ResourceItem(Resource.ironOre, itemStackCount));
		inventory.add(new ResourceItem(Resource.goldOre, itemStackCount));
		inventory.add(new ResourceItem(Resource.ironNugget, itemStackCount));
		inventory.add(new ResourceItem(Resource.goldNugget, itemStackCount));
		inventory.add(new ResourceItem(Resource.ironIngot, itemStackCount));
		inventory.add(new ResourceItem(Resource.goldIngot, itemStackCount));
		inventory.add(new ResourceItem(Resource.slime, itemStackCount));
		inventory.add(new ResourceItem(Resource.glass, itemStackCount));
		inventory.add(new ResourceItem(Resource.cloth, itemStackCount));
		inventory.add(new ResourceItem(Resource.cloud, itemStackCount));
		inventory.add(new ResourceItem(Resource.gem, itemStackCount));
		inventory.add(new ResourceItem(Resource.airStar, itemStackCount));
		inventory.add(new ResourceItem(Resource.stick, itemStackCount));
		inventory.add(new ResourceItem(Resource.rottenFlesh, itemStackCount));
		inventory.add(new ResourceItem(Resource.charcoal, itemStackCount));
		addAllDyes();
		inventory.add(new ResourceItem(Resource.applePie, itemStackCount));
		inventory.add(new ResourceItem(Resource.bone, itemStackCount));
		inventory.add(new ResourceItem(Resource.leaf, itemStackCount));
	}

	public void addAllDyes() {
		inventory.add(new ResourceItem(Resource.cactusGreen, itemStackCount));
		inventory.add(new ResourceItem(Resource.appleRed, itemStackCount));
		inventory.add(new ResourceItem(Resource.pinkDye, itemStackCount));
		inventory.add(new ResourceItem(Resource.greyDye, itemStackCount));
		inventory.add(new ResourceItem(Resource.blackDye, itemStackCount));
		inventory.add(new ResourceItem(Resource.yellowDye, itemStackCount));
		inventory.add(new ResourceItem(Resource.whiteDye, itemStackCount));
		inventory.add(new ResourceItem(Resource.orangeDye, itemStackCount));
		inventory.add(new ResourceItem(Resource.lightBlueDye, itemStackCount));
		inventory.add(new ResourceItem(Resource.limeDye, itemStackCount));
	}

	public void addAllToolItems() {
		inventory.add(new ToolItem(ToolType.SHOVEL, 0));
		inventory.add(new ToolItem(ToolType.HOE, 0));
		inventory.add(new ToolItem(ToolType.SWORD, 0));
		inventory.add(new ToolItem(ToolType.PICKAXE, 0));
		inventory.add(new ToolItem(ToolType.AXE, 0));
		inventory.add(new ToolItem(ToolType.SPEAR, 0));
		inventory.add(new ToolItem(ToolType.SHOVEL, 1));
		inventory.add(new ToolItem(ToolType.HOE, 1));
		inventory.add(new ToolItem(ToolType.SWORD, 1));
		inventory.add(new ToolItem(ToolType.PICKAXE, 1));
		inventory.add(new ToolItem(ToolType.AXE, 1));
		inventory.add(new ToolItem(ToolType.SPEAR, 1));
		inventory.add(new ToolItem(ToolType.SHOVEL, 2));
		inventory.add(new ToolItem(ToolType.HOE, 2));
		inventory.add(new ToolItem(ToolType.SWORD, 2));
		inventory.add(new ToolItem(ToolType.PICKAXE, 2));
		inventory.add(new ToolItem(ToolType.AXE, 2));
		inventory.add(new ToolItem(ToolType.SPEAR, 2));
		inventory.add(new ToolItem(ToolType.SHOVEL, 3));
		inventory.add(new ToolItem(ToolType.HOE, 3));
		inventory.add(new ToolItem(ToolType.SWORD, 3));
		inventory.add(new ToolItem(ToolType.PICKAXE, 3));
		inventory.add(new ToolItem(ToolType.AXE, 3));
		inventory.add(new ToolItem(ToolType.SPEAR, 3));
		inventory.add(new ToolItem(ToolType.SHOVEL, 4));
		inventory.add(new ToolItem(ToolType.HOE, 4));
		inventory.add(new ToolItem(ToolType.SWORD, 4));
		inventory.add(new ToolItem(ToolType.PICKAXE, 4));
		inventory.add(new ToolItem(ToolType.AXE, 4));
		inventory.add(new ToolItem(ToolType.SPEAR, 4));
		inventory.add(new ToolItem(ToolType.SHOVEL, 5));
		inventory.add(new ToolItem(ToolType.HOE, 5));
		inventory.add(new ToolItem(ToolType.SWORD, 5));
		inventory.add(new ToolItem(ToolType.PICKAXE, 5));
		inventory.add(new ToolItem(ToolType.AXE, 5));
		inventory.add(new ToolItem(ToolType.SPEAR, 5));
		inventory.add(new ToolItem(ToolType.SHOVEL, 6));
		inventory.add(new ToolItem(ToolType.HOE, 6));
		inventory.add(new ToolItem(ToolType.SWORD, 6));
		inventory.add(new ToolItem(ToolType.PICKAXE, 6));
		inventory.add(new ToolItem(ToolType.AXE, 6));
		inventory.add(new ToolItem(ToolType.SPEAR, 6));
		inventory.add(new ToolItem(ToolType.SHOVEL, 7));
		inventory.add(new ToolItem(ToolType.HOE, 7));
		inventory.add(new ToolItem(ToolType.SWORD, 7));
		inventory.add(new ToolItem(ToolType.PICKAXE, 7));
		inventory.add(new ToolItem(ToolType.AXE, 7));
		inventory.add(new ToolItem(ToolType.SPEAR, 7));
	}

	public void addAllFurniture() {
		inventory.add(new FurnitureItem(new Workbench()));
		inventory.add(new FurnitureItem(new GoldenWorkbench()));
		inventory.add(new FurnitureItem(new Chest()));
		inventory.add(new FurnitureItem(new Enchanter()));
		inventory.add(new FurnitureItem(new Anvil()));
		inventory.add(new FurnitureItem(new Oven()));
		inventory.add(new FurnitureItem(new Furnace()));
		inventory.add(new FurnitureItem(new Lantern()));
		inventory.add(new FurnitureItem(new Jukebox()));
		inventory.add(new FurnitureItem(new Torch()));
	}

	@Override
	public boolean canWalkOnAir() {
		if (hasSkyTotem || (Game.isCreativeMode && !Game.isSurvivalMode)) {
			return true;
		} else {
			return false;
		}
	}

	public void loadFrom(StringTokenizer st) {
		super.loadFrom(st);
		this.x = nextInt(st);
		this.y = nextInt(st);
		this.stamina = nextInt(st);
		this.maxStamina = nextInt(st);
		this.score = nextInt(st);

		this.inventory.items.clear();
		this.inventory.loadFrom(st);

		int has = nextInt(st);
		if (has != 0) {
			this.activeItem = Item.get(st, this);
		}
	}

	public void saveTo(StringBuffer str) {
		super.saveTo(str);
		str.append(this.x + " " + this.y + " " + this.stamina + " " + this.maxStamina + " " + " " + this.score + " ");

		this.inventory.saveTo(str);

		int has = this.activeItem != null ? 1 : 0;
		str.append(has + " ");
		if (this.activeItem != null) {
			this.activeItem.saveTo(str);
		}
	}
}