package mysticcraft.screen;

import mysticcraft.entity.furniture.Chest;
import mysticcraft.gfx.Color;
import mysticcraft.gfx.Font;
import mysticcraft.gfx.Screen;
import mysticcraft.main.Game;
import mysticcraft.sound.Sound;

public class WorldSettingsMenu extends Menu {
	private int selection = 0;
	private int mode = 0;
	private int bonusChest = 0;
	//private int sound = 1;

	public void tick() {
		if (input.up.clicked)
			selection--;

		if (input.down.clicked)
			selection++;

		if (input.up.clicked) {
			if (selection < 0) {
				
			} else {
				Sound.craft.play();
			}
		}

		if (input.down.clicked) {
			if (selection > 3) {

			} else {
				Sound.craft.play();
			}
		}

		if (input.attack.clicked || input.menu.clicked) {
			if (selection == 3)
				Sound.craft.play();
		}

		if (selection < 0)
			selection = 3;

		if (selection == 0) {
			if (input.right.clicked) {
				if (mode == 0) {
					mode = 1;
					Sound.craft.play();
				} else if (mode == 1) {
					mode = 0;
					Sound.craft.play();
				}
			}
			if (input.left.clicked) {
				if (mode == 0) {
					mode = 1;
					Sound.craft.play();
				} else if (mode == 1) {
					mode = 0;
					Sound.craft.play();
				}
			}
		}
		if (mode == 0) {
			Game.isSurvivalMode = true;
			Game.isCreativeMode = false;
			// Sys.println("Survival: " + Game.isSurvivalMode, Sys.out);
		}
		if (mode == 1) {
			Game.isCreativeMode = true;
			Game.isSurvivalMode = false;
			// Sys.println("Creative: " + Game.isCreativeMode, Sys.out);
		}

		if (selection == 1) {
			if (input.right.clicked) {
				if (bonusChest == 0) {
					bonusChest = 1;
					Sound.craft.play();
				} else if (bonusChest == 1) {
					bonusChest = 0;
					Sound.craft.play();
				}
			}
			if (input.left.clicked) {
				if (bonusChest == 0) {
					bonusChest = 1;
					Sound.craft.play();
				} else if (bonusChest == 1) {
					bonusChest = 0;
					Sound.craft.play();
				}
			}
		}

		if (bonusChest == 0) {
			Game.ibc = false;
		} else if (bonusChest == 1) {
			Game.ibc = true;
		}

		/*try {
		if (selection == 2) {
			if (input.right.clicked) {
				if (sound == 0) {
					sound = 1;
					// Sound.craft.play();
					if (Game.ENABLES_SOUND_FILE.exists()) {
						if (FileUtils.readFileToString(Game.ENABLES_SOUND_FILE).contains(Game.fs)) {

						} else {
							Game.enablesSound = true;
						}
					} else {
						Sound.craft.play();
						//Game.enablesSound = true;
					}
				} else if (sound == 1) {
					sound = 0;
					if (Game.ENABLES_SOUND_FILE.exists()) {
						if (FileUtils.readFileToString(Game.ENABLES_SOUND_FILE).contains(Game.fs)) {

						} else {
							Sound.craft.play();
							Game.enablesSound = false;
						}
					} else {
						Sound.craft.play();
						//Game.enablesSound = false;
					}
				}
			}
			if (input.left.clicked) {
				if (sound == 0) {
					sound = 1;
					// Sound.craft.play();
					if (Game.ENABLES_SOUND_FILE.exists()) {
						if ((FileUtils.readFileToString(Game.ENABLES_SOUND_FILE).contains(Game.ts) || FileUtils.readFileToString(Game.ENABLES_SOUND_FILE).contains(Game.fs))) {

						} else {
							Game.enablesSound = true;
						}
					} else {
						Sound.craft.play();
						//Game.enablesSound = true;
					}
				} else if (sound == 1) {
					sound = 0;
					if (Game.ENABLES_SOUND_FILE.exists()) {
						if ((FileUtils.readFileToString(Game.ENABLES_SOUND_FILE).contains(Game.ts) || FileUtils.readFileToString(Game.ENABLES_SOUND_FILE).contains(Game.fs))) {

						} else {
							Game.enablesSound = false;
						}
					} else {
						Sound.craft.play();
						//Game.enablesSound = false;
					}
				}
			}
		}
		} catch (Exception e) {}
		*/
		if (selection == 2) {
			if (input.attack.clicked || input.menu.clicked) {
				game.resetGame();
				if (Game.ibc) {
					game.level.add(new Chest(game.level.player.x, game.level.player.y + 6, "Bonus Chest"));
				}
				game.setMenu(null);
			}
		}

		if (selection > 2)
			selection = 0;
	}

	public void render(Screen screen) {
		screen.clear(0);

		Font.draw("World Settings", screen, 115, 15, Color.get(0, 550, 550, 550, -1));

		/* This section is used to display this options on the screen */
		if (selection == 0) {
			Font.draw("Gamemode: ", screen, 115, 25, Color.get(-1, 555, 555, 555, -1));
			Font.draw(">                    <", screen, 99, 25, Color.get(-1, 555));
			if (mode == 0)
				Font.draw("Survival", screen, 195, 25, Color.get(-1, 555, 555, 555, -1));
			else if (mode == 1)
				Font.draw("Creative", screen, 195, 25, Color.get(-1, 555, 555, 555, -1));

		} else if (selection != 0) {
			Font.draw("Gamemode: ", screen, 115, 25, Color.get(-1, 555));
			if (mode == 0)
				Font.draw("Survival", screen, 195, 25, Color.get(-1, 555));
			else if (mode == 1)
				Font.draw("Creative", screen, 195, 25, Color.get(-1, 555));
		}

		if (selection == 1) {
			Font.draw("Bonus Chest: ", screen, 115, 35, Color.get(-1, 555));
			Font.draw(">                 <", screen, 99, 35, Color.get(-1, 555));
			if (bonusChest == 0)
				Font.draw("Off", screen, 215, 35, Color.get(-1, 555));
			else if (bonusChest == 1)
				Font.draw("On", screen, 215, 35, Color.get(-1, 555));
		} else if (selection != 1) {
			Font.draw("Bonus Chest: ", screen, 115, 35, Color.get(-1, 555));
			if (bonusChest == 0)
				Font.draw("Off", screen, 215, 35, Color.get(-1, 555));
			else if (bonusChest == 1)
				Font.draw("On", screen, 215, 35, Color.get(-1, 555));
		}

		/*if (selection == 2) {
			Font.draw("Enables Sound: ", screen, 115, 45, Color.get(-1, 555));
			Font.draw(">                  <", screen, 99, 45, Color.get(-1, 555));
			if (sound == 0)
				Font.draw("  Off", screen, 215, 45, Color.get(-1, 555));
			else if (sound == 1)
				Font.draw("  On", screen, 215, 45, Color.get(-1, 555));
		} else if (selection != 2) {
			Font.draw("Enables Sound: ", screen, 115, 45, Color.get(-1, 555));
			if (sound == 0)
				Font.draw("  Off", screen, 215, 45, Color.get(-1, 555));
			else if (sound == 1)
				Font.draw("  On", screen, 215, 45, Color.get(-1, 555));
		}*/
		if (selection == 2) {
			Font.draw(">      <", screen, 99, 45, Color.get(-1, 555));
			Font.draw("Play ", screen, 115, 45, Color.get(-1, 153));
		} else if (selection != 3) {
			Font.draw("Play", screen, 115, 45, Color.get(-1, 555));
		}
	}

}
