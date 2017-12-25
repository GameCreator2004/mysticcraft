package mysticcraft.screen;

import java.util.Random;

import mysticcraft.gfx.Color;
import mysticcraft.gfx.Font;
import mysticcraft.gfx.Screen;
import tk.jidgu.util.Sys;
import tk.jidgu.util.Utils;

public class TitleMenu extends Menu {
	private int selected = 0; // Currently selected option

	private static final String version = "(dev 1.4)";
	private static final String space = "  ";
	private static final String second_space = " ";
	private static final String time = "HH:mm:ss";
	private static final String[] options = { "New game", "Instructions", "About", "Join Shylor's Minicraft Discord Server", "Donate to get more features", "Exit" };

	private Random random = new Random();
	int count = 0;
	boolean reverse = false;
	int rand;
	
	private String[] splashes = {
		    "No Save and load system in here!!!",
			"Have you try the sky totem yet ?",
			"ChrisJ is making Miniventure!!!", 
			"Pigeon made Pigeoncraft",
			"Something is wrong here",
			"There isn't a multiplayer mode here"
	};
	
	public TitleMenu() {
		rand = random.nextInt(splashes.length);
		Sys.println(splashes[rand], Sys.out);
	}

	public void tick() {
		
		if (input.r.clicked)
			rand = random.nextInt(splashes.length);
		
		if (input.up.clicked)
			selected--; // If the player presses the up key, then move up 1
						// option in the list
		if (input.down.clicked)
			selected++; // If the player presses the down key, then move down 1
						// option in the list

		int len = options.length; // The size of the list (normally 3 options)
		if (selected < 0)
			selected += len; // If the selected option is less than 0, then move
								// it to the last option of the list.
		if (selected >= len)
			selected -= len; // If the selected option is more than or equal to
								// the size of the list, then move it back to 0;

		if (reverse == false) {
			count++;
			if (count == 25) reverse = true;
		} else if (reverse == true) {
			count--;
			if (count == 0) reverse = false;
		}
		
		if (input.attack.clicked || input.menu.clicked) {
			if (options[selected].equals("New game")) {
				game.setMenu(new WorldSettingsMenu());
			}

			if (options[selected].equals("Instructions"))
				game.setMenu(new InstructionsMenu(this));

			if (options[selected].equals("About"))
				game.setMenu(new AboutMenu(this)); // If the selection is 2
													// ("About") then go to the
													// about menu.
			if (options[selected].equals("Join Shylor's Minicraft Discord Server"))
				Utils.browseToAWebsiteWhenCalled("https://discord.me/page/minicraft");

			if (options[selected].equals("Donate to get more features"))
				Utils.browseToAWebsiteWhenCalled("https://www.patreon.com/gamecreator");

			if (options[selected].equals("Exit"))
				Sys.exit(1, Sys.out);
		}

	}

	public void render(Screen screen) {
		screen.clear(0);// Clears the screen to a black color.

		/* This section is used to display the minicraft title */

		int h = 2; // Height of squares (on the spritesheet)
		int w = 17; // Width of squares (on the spritesheet)
		int titleColor = Color.get(0, 010, 131, 551, -1); // Colors of the title
		int xo = (screen.w - w * 8) / 2; // X location of the title
		int yo = 24; // Y location of the title
		int cols = Color.get(0, 550);
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				screen.render(xo + x * 8, yo + y * 8, x + (y + 6) * 32, titleColor, 0); // Loops
																						// through
																						// all
																						// the
																						// squares
																						// to
																						// render
																						// them
																						// all
																						// on
																						// the
																						// screen.
			}
		}

		boolean isblue = splashes[rand].contains("blue");

		int bcol = 5 - count / 5; // this number ends up being between 1 and 5, inclusive.
		cols = isblue ? Color.get(0, bcol) : Color.get(0, (bcol-1)*100+5, bcol*100+bcol*10, bcol*100+bcol*10, -1);
		
		/* This section is used to display this options on the screen */
		for (int i = 0; i < options.length; i++) { // Loops through all the
													// options in the list
			String msg = options[i]; // Text of the current option
			int col = Color.get(0, 222, 222, 222, -1); // Color of unselected text
			if (i == selected) { // If the current option is the option that is
									// selected
				msg = "> " + msg + " <"; // Add the cursors to the sides of the
											// message
				col = Color.get(0, 555, 555, 555, -1); // change the color of the
													// option
			}
			Font.draw(msg, screen, (screen.w - msg.length() * 8) / 2, (8 + i) * 8, col); // Draw
																							// the
																							// current
																							// option
																							// to
																							// the
																							// screen
		}
		Font.draw(splashes[rand], screen, centertext(splashes[rand])/*190*/, 49, cols/*Color.get(-1, 555, 555, 555, 555)*/);

		Font.draw("Time: " + Utils.getDateAndTime(time) + space + version + second_space + "(Arrow keys,X and C)", screen, 0, screen.h - 8, Color.get(0, 111, 111, 111, -1)); // Draw
		// text
		// at
		// the
		// bottom
	}
	public int centertext(String name) {
		return (358 - name.length() * 8) / 2;
	}
}
