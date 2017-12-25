package mysticcraft.screen;

import mysticcraft.gfx.Color;
import mysticcraft.gfx.Font;
import mysticcraft.gfx.Screen;
import mysticcraft.sound.Sound;

public class PauseMenu extends Menu {

	private int selection = 0;

	// public PauseMenu() {
	// Sys.println("The current menu rigth now is the Pause Menu", System.out);
	// }

	public void tick() {
		if (selection < 0)
			selection = 0;

		if (input.up.clicked)
			selection--;
		
		if(input.up.clicked)
			Sound.craft.play();
		
		if (input.down.clicked)
			selection++;
		
		if(input.down.clicked)
			Sound.craft.play();
		
		if (input.attack.clicked || input.menu.clicked) {
			if (selection == 0)
				game.setMenu(null);
			if (selection == 1) {
				game.setMenu(new InstructionsMenu(this));
			}
		}

	}

	public void render(Screen screen) {
		Font.renderFrame(screen, "    " + "Pause", 15, 1, 30, 15);
		if (selection == 0) {
			Font.draw("Resume", screen, 159, 26, Color.get(-1, 555));
			Font.draw(">        <", screen, 145, 26, Color.get(-1, 555));        
		} else if (selection != 0) {
			Font.draw("Resume", screen, 159, 26, Color.get(-1, 555));
		}
		if(selection == 1) {
			Font.draw("Instructions", screen, 139, 36, Color.get(-1, 555));
			Font.draw(">             <", screen, 126, 36, Color.get(-1, 555));
		} else if (selection != 1) {
		Font.draw("Instructions", screen, 139, 36, Color.get(-1, 555));
		}
	}
}
