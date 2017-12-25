package mysticcraft.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import mysticcraft.main.Game;

public class InputHandler implements KeyListener {

	Game game;
	/*
	 * This class is used for handling your inputs and translating them as
	 * booleans
	 */

	public class Key {
		public int presses, absorbs; // presses determine how long you held it
										// down, absorbs will determined if you
										// clicked or held it down.
		public boolean down, clicked; // booleans to tell if the player has
										// clicked the button, or held down the
										// button.

		public Key() {
			keys.add(this); // Adds this object to a list of Keys used in the
							// game.
		}

		public void toggle(boolean pressed) {
			if (pressed != down) {
				down = pressed; // If the key is being pressed, then down is
								// true.
			}
			if (pressed) {
				presses++; // If pressed, then presses value goes up.
			}
		}

		public void tick() {
			if (absorbs < presses) { // if presses are above absorbs
				absorbs++;// increase the absorbs value
				clicked = true;// clicked is true
			} else {
				clicked = false;// else clicked is false
			}
		}
	}

	public List<Key> keys = new ArrayList<Key>(); // List of keys used in the
													// game

	/* Action keys */
	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	public Key attack = new Key();
	public Key menu = new Key();
	public Key map = new Key();
	public Key pause = new Key();
	public Key screenshot = new Key();
	public Key show_game_info = new Key();
	public Key fullscreen = new Key();
	public Key r = new Key();
	public Key f5 = new Key();
	public Key x = new Key();
	public Key l = new Key();

	/**
	 * This is used to stop all of the actions when the game is out of focus.
	 */
	public void releaseAll() {
		for (int i = 0; i < keys.size(); i++) {
			keys.get(i).down = false; // turns all the keys down value to false
		}
	}

	public void tick() {
		for (int i = 0; i < keys.size(); i++) {
			keys.get(i).tick(); // Ticks every key to see if it is pressed.
		}

	}

	public InputHandler(Game game) {
		game.addKeyListener(this); // Adds this to Game.java so it can detect
									// when a key is being pressed.
		this.game = game;
	}

	public void keyPressed(KeyEvent ke) {
		toggle(ke, true); // triggered when a key is pressed.
	}

	public void keyReleased(KeyEvent ke) {
		toggle(ke, false); // triggered when a key is let go.
	}

	/** This method is used to turn keyboard key presses into actions */
	private void toggle(KeyEvent ke, boolean pressed) {
		int key = ke.getKeyCode();
		
		switch (key) {
		case KeyEvent.VK_UP:
			up.toggle(pressed);
			break;

		case KeyEvent.VK_DOWN:
			down.toggle(pressed);
			break;

		case KeyEvent.VK_LEFT:
			left.toggle(pressed);
			break;

		case KeyEvent.VK_RIGHT:
			right.toggle(pressed);
			break;

		case KeyEvent.VK_W:
			up.toggle(pressed);
			break;

		case KeyEvent.VK_S:
			down.toggle(pressed);
			break;

		case KeyEvent.VK_A:
			left.toggle(pressed);
			break;

		case KeyEvent.VK_D:
			right.toggle(pressed);
			break;

		case KeyEvent.VK_NUMPAD8:
			up.toggle(pressed);
			break;

		case KeyEvent.VK_NUMPAD2:
			down.toggle(pressed);
			break;

		case KeyEvent.VK_NUMPAD4:
			left.toggle(pressed);
			break;

		case KeyEvent.VK_NUMPAD6:
			right.toggle(pressed);
			break;

		case KeyEvent.VK_TAB:
			menu.toggle(pressed);
			break;

		case KeyEvent.VK_ALT:
			menu.toggle(pressed);
			break;

		case KeyEvent.VK_ALT_GRAPH:
			menu.toggle(pressed);
			break;

		case KeyEvent.VK_SPACE:
			attack.toggle(pressed);
			break;

		case KeyEvent.VK_CONTROL:
			attack.toggle(pressed);
			break;

		case KeyEvent.VK_NUMPAD0:
			attack.toggle(pressed);
			break;

		case KeyEvent.VK_INSERT:
			attack.toggle(pressed);
			break;

		case KeyEvent.VK_ENTER:
			attack.toggle(pressed);
			break;

		case KeyEvent.VK_X:
			menu.toggle(pressed);
			break;

		case KeyEvent.VK_C:
			attack.toggle(pressed);
			break;

		case KeyEvent.VK_M:
			map.toggle(pressed);
			break;

		case KeyEvent.VK_ESCAPE:
			pause.toggle(pressed);
			break;

		case KeyEvent.VK_F2:
			screenshot.toggle(pressed);
			break;

		case KeyEvent.VK_F3:
			show_game_info.toggle(pressed);
			break;

		case KeyEvent.VK_F5:
			f5.toggle(pressed);
			break;
		case KeyEvent.VK_F11:
			fullscreen.toggle(pressed);
			break;

		case KeyEvent.VK_R:
			r.toggle(pressed);
			break;

		case KeyEvent.VK_L:
			l.toggle(pressed);
			break;

		case KeyEvent.VK_G:
			x.toggle(pressed);
			break;

		default:
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
