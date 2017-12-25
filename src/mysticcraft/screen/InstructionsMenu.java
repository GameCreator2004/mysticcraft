package mysticcraft.screen;

import mysticcraft.gfx.Color;
import mysticcraft.gfx.Font;
import mysticcraft.gfx.Screen;

public class InstructionsMenu extends Menu {
	private Menu parent; // Creates a parent object to go back to

	/**
	 * The about menu is a read menu about what you have to do in the game. Only
	 * contains text and a black background
	 */
	public InstructionsMenu(Menu parent) {
		this.parent = parent; // The parent Menu that it will go back to.
	}

	public void tick() {
		if (input.attack.clicked || input.menu.clicked) {
			game.setMenu(parent); // If the user presses the "Attack" or "Menu"
									// button, it will go back to the parent
									// menu.
		}
	}

	/** Renders the text on the screen */
	public void render(Screen screen) {
		screen.clear(0); // clears the screen to be a black color.

		/*
		 * Font.draw Parameters: Font.draw(String text, Screen screen, int x,
		 * int y, int color)
		 */

		Font.draw("HOW TO PLAY", screen, 4 * 8 + 4, 1 * 8, Color.get(0, 555, 555, 555, -1)); // draws
																							// Title
																							// text
		Font.draw("Move your character", screen, 0 * 8 + 4, 3 * 8, Color.get(0, 333, 333, 333, -1)); // draws
																									// text
		Font.draw("with the arrow keys", screen, 0 * 8 + 4, 4 * 8, Color.get(0, 333, 333, 333, -1)); // draws
																									// text
		Font.draw("press C to attack", screen, 0 * 8 + 4, 5 * 8, Color.get(0, 333, 333, 333, -1)); // draws
																								// text
		Font.draw("and X to open the", screen, 0 * 8 + 4, 6 * 8, Color.get(0, 333, 333, 333, -1)); // draws
																								// text
		Font.draw("inventory and to", screen, 0 * 8 + 4, 7 * 8, Color.get(0, 333, 333, 333, -1)); // draws
																								// text
		Font.draw("use items.", screen, 0 * 8 + 4, 8 * 8, Color.get(0, 333, 333, 333, -1)); // draws
																						// text
		Font.draw("Select an item in the inventory and then ", screen, 0 * 8 + 4, 9 * 8, Color.get(0, 333, 333, 333, -1)); // draws
																								// text
		Font.draw("press C to equip it.", screen, 0 * 8 + 4, 10 * 8, Color.get(0, 333, 333, 333, -1)); // draws
																								// text
		//Font.draw("to equip it.", screen, 0 * 8 + 4, 11 * 8, Color.get(0, 333, 333, 333)); // draws
																						// text
		Font.draw("Press M to open the map,", screen, 0 * 8 + 4, 12 * 8, Color.get(0, 333, 333, 333, -1)); // draws
		// text
		Font.draw("Press Enter to close the map,", screen, 0 * 8 + 4, 13 * 8, Color.get(0, 333, 333, 333, -1)); // draws
		Font.draw("Press F2 to take a screenshot,", screen, 0 * 8 + 4, 14 * 8, Color.get(0, 333, 333, 333, -1));
		Font.draw("Press F3 to show the game info,", screen, 0 * 8 + 4, 15 * 8, Color.get(0, 333, 333, 333, -1));
		Font.draw("Press Esc to pause.", screen, 0 * 8 + 4, 16 * 8, Color.get(0, 333, 333, 333, -1));
	}
}
