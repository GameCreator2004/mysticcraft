package mysticcraft.main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.apache.commons.io.FileUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import mysticcraft.entity.furniture.Chest;
import mysticcraft.entity.mob.Player;
import mysticcraft.gfx.Color;
import mysticcraft.gfx.Font;
import mysticcraft.gfx.Screen;
import mysticcraft.gfx.SpriteSheet;
import mysticcraft.input.InputHandler;
import mysticcraft.io.MysticcraftFile;
import mysticcraft.level.Level;
import mysticcraft.level.levelgen.LevelGen;
import mysticcraft.level.tile.Tile;
import mysticcraft.screen.AboutMenu;
import mysticcraft.screen.DeadMenu;
import mysticcraft.screen.InstructionsMenu;
import mysticcraft.screen.LevelTransitionMenu;
import mysticcraft.screen.Menu;
import mysticcraft.screen.PauseMenu;
import mysticcraft.screen.TitleMenu;
import mysticcraft.screen.WonMenu;
import mysticcraft.utils.MinicraftUtils;
import tk.jidgu.util.Sys;
import tk.jidgu.util.Utils;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 367;
	public static final int HEIGHT = 230;
	public static final int SCALE = 3;
	private File screenShotDirectory = new File(Reference.SCREENSHOT_DIRECTORY);
	public JFrame frame = new JFrame(/*
										 * Reference.GAME_TITLE + " " +
										 * Reference.GAME_VERSION
										 */); // creates a new window

	public static JsonParser jsonParser = new JsonParser();
	public Toolkit toolkit = Toolkit.getDefaultToolkit();
	public Dimension dim = toolkit.getScreenSize();
	public int screenShotCliked = 0;
	public static File GAME_JSON_FILE = new MysticcraftFile("mysticcraft");
	public static boolean isCreativeMode = false;
	public static boolean isSurvivalMode = false;
	public static boolean enablesSound = true;
	public static boolean ibc = false;
	public static String esft = "mysticcraft.enables_sound";
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB); // creates
																								// an
																								// image
																								// to
																								// be
																								// displayed
																								// on
																								// the
																								// screen.
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData(); // the
																							// array
																							// of
																							// pixels
																							// that
																							// will
																							// be
																							// displayed
																							// on
																							// the
																							// screen.

	// of
	// pixels
	// that
	// will
	// be
	// displayed
	// on
	// the
	// screen.
	public static boolean running = false; // This stores if the game is running or
									// paused
	public Screen screen; // Creates the main screen
	public Screen lightScreen; // Creates a front screen to render the darkness
								// in caves (Fog of war).
	public InputHandler input = new InputHandler(this); // Creates the class
															// (InputHandler.java)
															// that will take in
															// out inputs (aka:
															// pressing the 'W'
															// key).

	private int[] colors = new int[256]; // All of the colors put into an array
	public int tickCount = 0; // Used in the ticking system
	public int gameTime = 0; // Main value in the timer used on the dead screen.

	public Level level; // This is the current level you are on.
	// This array is about the different levels.
	// Remember that arrays start at 0 so you have 0,1,2,3,4
	public Level[] levels = new Level[15];
	// This is the level the player is on.
	// This is set to 3 which is the surface.
	public int currentLevel = 3;
	public Player player; // the player himself
	public Chest chest;
	
	public Menu menu; // the current menu you are on.
	private int playerDeadTime; // the paused time when you die before the dead
								// menu shows up.
	private int pendingLevelChange; // used to determined if the player should
									// change levels or not.
	private int wonTimer = 0; // the paused time when you win before the win
								// menu shows up.
	public boolean hasWon = false; // If the player wins this is set to true

	private int ticks;

	private int fps;

	private boolean isFullScreen;

	private boolean isShowGameInfo = false;

	// public static boolean includesBonusChest = false;

	// @SuppressWarnings("deprecation")
	@SuppressWarnings("deprecation")
	public Game() {
		try {
			if (Game.GAME_JSON_FILE.exists()) {
				if (FileUtils.readFileToString(GAME_JSON_FILE).contains(esft)) {
					Object obj = jsonParser.parse(new FileReader(GAME_JSON_FILE));
					JsonObject jsonObject = (JsonObject) obj;
					boolean statement = jsonObject.get(esft).getAsBoolean();
					enablesSound = statement;
				} else {
					enablesSound = true;
				}
			} else {
				enablesSound = true;
			}
		} catch (Exception e) {
		}
	}

	public void loadGame() {
		resetGame();

		String filename = Reference.HOME_DIRECTORY + "/saves/game.dat";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			StringTokenizer st = new StringTokenizer(reader.readLine());
			this.gameTime = Integer.parseInt(st.nextToken());
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found : " + filename);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 5; i++) {
			this.levels[i].load(Reference.HOME_DIRECTORY + "/saves/level" + i + ".dat");
		}
		this.player.load(Reference.HOME_DIRECTORY + "/saves/player.dat");
		Sys.println("Loaded !", Sys.out);
	}

	public void saveGame() {
		File f = new File(Reference.HOME_DIRECTORY + "/saves");
		try {
			f.mkdir();
			String filename = Reference.HOME_DIRECTORY + "/saves/game.dat";
			FileOutputStream writer = new FileOutputStream(filename);
			StringBuffer str = new StringBuffer();
			str.append(this.gameTime + " ");
			writer.write(str.toString().getBytes());
			writer.close();
		} catch (Exception e) {
			// System.out.println("File not found : " + filename);
		}
		for (int i = 0; i < 5; i++) {
			this.levels[i].save(Reference.HOME_DIRECTORY + "/saves/level" + i + ".dat");
		}
		this.player.save(Reference.HOME_DIRECTORY + "/saves/player.dat");
		Sys.println("Saved !", Sys.out);
	}

	// Blue text is used in eclipse to set the description of a method. Put your
	// mouse over the "SetMenu(Menu menu)" method text to see it.
	/** Use this method to switch to another menu. */
	public void setMenu(Menu menu) {
		this.menu = menu;
		if (menu != null) {
			menu.init(this, input);
		}
	}

	public Menu getMenu() {
		return menu;
	}

	/** This starts the game logic after a pause */

	public void start() {
		running = true;
		Sys.println("Creating the game interface and creating the game loop", Sys.out);
	}

	/** This pauses the game */
	public void stop() {
		running = false;
	}

	/** This resets the game */
	public void resetGame() {
		// Resets all values
		playerDeadTime = 0;
		wonTimer = 0;
		gameTime = 0;
		hasWon = false;

		levels = new Level[15];
		currentLevel = 3;

		// generates new maps
		levels[5] = new Level(128, 128, 2, levels[6]);
		levels[4] = new Level(128, 128, 1, levels[5]); // creates the sky map
		levels[3] = new Level(128, 128, 0, levels[4]); // creates the overworld
		levels[2] = new Level(128, 128, -1, levels[3]); // creates the mines
														// (iron level)
		levels[1] = new Level(128, 128, -2, levels[2]); // creates the deep
														// mines (water/gold
														// level)
		levels[0] = new Level(128, 128, -3, levels[1]); // creates the nether
														// (lava/gem level)

		/*
		 * Please note: the terms "Mines", "Deep Mines", and "Nether" are not
		 * the real names used in the code I just got those names from the wiki
		 * where someone named them that. Those levels don't have any real names
		 * yet -David
		 */

		level = levels[currentLevel]; // puts level to the current level
										// (surface)

		player = new Player(this, input); // creates a new player
		chest = new Chest(player.x, player.y, "Chest");
		player.findStartPos(level); // finds the start level for the player

		level.add(player); // adds the player to the current level
		for (int i = 0; i < 5; i++) {
			levels[i].trySpawn(5000); // populates all 5 levels with mobs.
		}
	}

	/**
	 * Initialization step, this is called when the game first starts. Sets up
	 * the colors and the screens.
	 * 
	 * @throws ParseException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void init() throws Exception {
		int pp = 0;
		/* This loop below creates the 216 colors in minicraft. */
		for (int r = 0; r < 6; r++) {
			for (int g = 0; g < 6; g++) {
				for (int b = 0; b < 6; b++) {
					int rr = (r * 255 / 5);
					int gg = (g * 255 / 5);
					int bb = (b * 255 / 5);
					int mid = (rr * 30 + gg * 59 + bb * 11) / 100;

					int r1 = ((rr + mid * 1) / 2) * 230 / 255 + 10;
					int g1 = ((gg + mid * 1) / 2) * 230 / 255 + 10;
					int b1 = ((bb + mid * 1) / 2) * 230 / 255 + 10;
					colors[pp++] = r1 << 16 | g1 << 8 | b1;

				}
			}
		}
		/* This sets up the screens, loads the icons.png spritesheet. */

		initGraphicalComponents();

		resetGame(); // starts a new game... for some reason?
		setMenu(new TitleMenu()); // Sets the menu to the title menu.
	}

	/**
	 * This is the main loop that runs the game It keeps track of the amount of
	 * time that. It keeps track of the amount of time that has passed and fires
	 * the ticks needed to run the game. It also fires the command to render out
	 * the screen.
	 */
	public void run() {
		start();
		long lastTime = System.nanoTime();
		double unprocessed = 0;
		// Nanoseconds per Tick
		double nsPerTick = 1000000000.0 / 60; // There are 60 ticks per second.
		int frames = 0;
		int ticks = 0;
		long lastTimer1 = System.currentTimeMillis(); // current time in
														// milliseconds.

		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		} // preps the game by setting up the screens and colors.

		while (running) {
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick; // figures out the
															// processed time
															// between now and
															// last Time.
			lastTime = now;
			boolean shouldRender = true;
			while (unprocessed >= 1) { // If there is unprocessed time, then//
										// tick.
				this.ticks = ticks;
				ticks++;
				ticks++;
				ticks++;
				ticks *= 1000;
				tick(); // calls the tick method (in which it calls the other
						// tick methods throughout the code.
				unprocessed -= 1; // the method is now processed. so it minuses
									// by 1.
				shouldRender = true; // causes the should render to be true...
										// why is this here since it was already
										// true? whatever.

			}

			try {
				Thread.sleep(2);// makes a small pause for 2 milliseconds
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				this.fps = frames;
				frames++;
				frames++;
				frames++;
				frames++;
				frames++;
				frames++;
				frames++;
				frames++;
				frames++;
				frames++;
				frames++;
				frames++;
				frames *= 1000;
				render(); // renders the screen
			}

			if (System.currentTimeMillis() - lastTimer1 > 1000) { // updates
																	// every 1
																	// second
				// Printer.printOutGameConsole(ticks, frames, "fps", "ticks");
				Sys.println("FPS: " + fps + ", " + "TICKS: " + ticks, Sys.out);
				lastTimer1 += 1000;// adds a second to the timer
				frames = 0;// resets the frames value.
				ticks = 0;// resets the ticks value.
			}
		}
	}

	/**
	 * Pre Initialization
	 */
	public static void preInit() {
		Utils.createDirectory(Reference.RESOURCE_DIRECTORY);
		Utils.createDirectory(Reference.SPRITE_SHEET_RESOURCE_DIRECTORY);
		Utils.createDirectory(Reference.HOME_DIRECTORY);
	}

	public static void getComputerInfo() {
		Sys.println("Operating System Name: " + MinicraftUtils.getOSName(), Sys.out);
		Sys.println("Operating System Architecture: " + MinicraftUtils.getOSArchitecture(), Sys.out);
		Sys.println("Is Window: " + MinicraftUtils.isWindows(), Sys.out);
		Sys.println("Is Mac: " + MinicraftUtils.isMac(), Sys.out);
		Sys.println("Is Unix: " + MinicraftUtils.isUnix(), Sys.out);
	}

	/**
	 * The tick method is the updates that happen in the game, there are 60
	 * ticks that happen per second.
	 */
	@SuppressWarnings("deprecation")
	public void tick() {
		tickCount++; // increases tickCount by 1, not really used for anything.
		if (!hasFocus()) {
			input.releaseAll(); // If the player is not focused on the screen,
								// then all the current inputs will be set to
								// off (well up).
		} else {
			if (!player.removed && !hasWon)
				gameTime++; // increases tickCount by 1, this is used for the
							// timer on the death screen.

			input.tick(); // calls the tick() method in InputHandler.java

			if (menu != null) {
				menu.tick(); // If there is a menu active, it will call the tick
								// method of that menu.
			} else {
				if (player.removed) {
					playerDeadTime++;
					if (playerDeadTime > 60) {
						setMenu(new DeadMenu()); // If the player has been
													// removed and a second has
													// passed, then set the menu
													// to the dead menu.
					}
				} else {
					if (pendingLevelChange != 0) {
						setMenu(new LevelTransitionMenu(pendingLevelChange)); // if
																				// the
																				// player
																				// hits
																				// a
																				// stairs,
																				// then
																				// a
																				// screen
																				// transition
																				// menu
																				// will
																				// appear.
						pendingLevelChange = 0;
					}
				}
				if (wonTimer > 0) {
					if (--wonTimer == 0) {
						setMenu(new WonMenu()); // if the wonTimer is above 0,
												// this will be called and if it
												// hits 0 then it actives the
												// win menu.
					}
				}
				level.tick(); // calls the tick() method in Level.java
				Tile.tickCount++; // increases the tickCount in Tile.java. Used
									// for Water.java and Lava.java.
			}
		}
		if (input.screenshot.clicked) {
			screenShotCliked += 1;
			screenShotTakingEvent();
		}
		if (input.f5.clicked) {
			initGraphicalComponents();
		}
		
		if (input.x.clicked) {
			if (isCreativeMode && !isSurvivalMode) {

			} else if (isSurvivalMode && !isCreativeMode) {
				saveGame();
			}
		}
		if (input.l.clicked) {
			if (isCreativeMode && !isSurvivalMode) {

			} else if (isSurvivalMode && !isCreativeMode) {
				loadGame();
			}
		}

		if (GAME_JSON_FILE.exists()) {
			try {
				if (FileUtils.readFileToString(Game.GAME_JSON_FILE).contains("mysticcraft.title")) {
					Object obj = Game.jsonParser.parse(new FileReader(Game.GAME_JSON_FILE));
					JsonObject jsonObject = (JsonObject) obj;
					String title = (String) jsonObject.get("mysticcraft.title").getAsString();
					if (title.equals("")) {
						frame.setTitle(Reference.DEFAULT_TITLE);
					} else {
						frame.setTitle(title);
					}
				} else {
					frame.setTitle(Reference.DEFAULT_TITLE);
				}
			} catch (Exception e) {
			}
		} else {
			frame.setTitle(Reference.DEFAULT_TITLE);
		}

		try {
			if (Game.GAME_JSON_FILE.exists()) {
				if (FileUtils.readFileToString(GAME_JSON_FILE).contains(esft)) {
					Object obj = jsonParser.parse(new FileReader(GAME_JSON_FILE));
					JsonObject jsonObject = (JsonObject) obj;
					Boolean statement = jsonObject.get(esft).getAsBoolean();
					enablesSound = statement;
				} else {
					enablesSound = true;
				}
			} else {
				enablesSound = true;
			}
		} catch (Exception e) {
		}
	}

	/**
	 *
	 * This method changes the level that the player is currently on. It takes 1
	 * integer variable, which is used to tell the game which direction to go.
	 * For example, 'changeLevel(1)' will make you go up a level, while
	 * 'changeLevel(-1)' will make you go down a level.
	 */
	public void changeLevel(int dir) {
		level.remove(player); // removes the player from the current level.
		currentLevel += dir; // changes the current level by the amount
		level = levels[currentLevel]; // sets the level to the current level
		player.x = (player.x >> 4) * 16 + 8; // sets the player's x coord (to
												// center yourself on the
												// stairs)
		player.y = (player.y >> 4) * 16 + 8; // sets the player's y coord (to
												// center yourself on the
												// stairs)
		level.add(player); // adds the player to the level.
	}

	/**
	 * renders the current screen
	 * 
	 */
	public void render() {
		if (input.map.clicked) {
			if (menu instanceof TitleMenu | menu instanceof InstructionsMenu | menu instanceof PauseMenu | menu instanceof AboutMenu) {
				Sys.println("You can't use a map in this menu", System.out);
			} else {
				LevelGen.showMap(level.tiles, player);
				Sys.println("A map just got created", System.out);
			}
		}

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();

		if (input.fullscreen.clicked) {
			if (isFullScreen == false) {
				gd.setFullScreenWindow(frame);
				isFullScreen = true;
			} else {
				gd.setFullScreenWindow(null);
				isFullScreen = false;
			}
		}

		BufferStrategy bs = getBufferStrategy(); // creates a buffer strategy to
													// determine how the
													// graphics should be
													// buffered.
		if (bs == null) {
			createBufferStrategy(3); // if the buffer strategy is null, then
										// make a new one!
			requestFocus(); // requests the focus of the screen.
			return;
		}

		int xScroll = player.x - screen.w / 2; // scrolls the screen in the x
												// axis.
		int yScroll = player.y - (screen.h - 8) / 2; // scrolls the screen in
														// the y axis.
		if (xScroll < 16)
			xScroll = 16; // if the screen is at the left border, then stop
							// scrolling.
		if (yScroll < 16)
			yScroll = 16; // if the screen is at the top border, then stop
							// scrolling.
		if (xScroll > level.w * 16 - screen.w - 16)
			xScroll = level.w * 16 - screen.w - 16; // if the screen is at the
													// right border, then stop
													// scrolling.
		if (yScroll > level.h * 16 - screen.h - 16)
			yScroll = level.h * 16 - screen.h - 16; // if the screen is at the
													// bottom border, then stop
													// scrolling.
		if (currentLevel > 3) { // if the current level is higher than 3 (which
								// only the sky level is)
			int col = Color.get(20, 20, 121, 121, -1); // background color.
			for (int y = 0; y < 14; y++)
				for (int x = 0; x < 24; x++) {
					screen.render(x * 8 - ((xScroll / 4) & 7), y * 8 - ((yScroll / 4) & 7), 0, col, 0); // creates
																										// the
																										// background
																										// for
																										// the
																										// sky
																										// level.
				}
		}

		level.renderBackground(screen, xScroll, yScroll); // Calls the
															// renderBackground()
															// method in
															// Level.java
		level.renderSprites(screen, xScroll, yScroll); // Calls the
														// renderSprites()
														// method in Level.java

		// this creates the fog-of-war (darkness) in the caves
		if (currentLevel < 3) {
			lightScreen.clear(0); // clears the light screen to a black color
			level.renderLight(lightScreen, xScroll, yScroll); // finds all (and
																// renders) the
																// light from
																// objects (like
																// the player,
																// lanterns, and
																// lava).
			screen.overlay(lightScreen, xScroll, yScroll); // overlays the light
															// screen over the
															// main screen.
		}

		renderGui(); // calls the renderGui() method.

		if (!hasFocus())
			renderFocusNagger(); // calls the renderFocusNagger() method, which
									// creates the "Click to Focus" message.

		for (int y = 0; y < screen.h; y++) {
			for (int x = 0; x < screen.w; x++) {
				// loops through all the pixels on the screen
				int cc = screen.pixels[x + y * screen.w]; // finds a pixel on
															// the screen.
				if (cc < 255)
					pixels[x + y * WIDTH] = colors[cc]; // colors the pixel
														// accordingly.
			}
		}

		Graphics2D g2d = (Graphics2D) bs.getDrawGraphics(); // gets the graphics
															// in which java
		// draws the picture
		g2d.fillRect(0, 0, getWidth(), getHeight()); // fills the window with
														// the
														// graphics we draw in
														// the
														// window.
		int ww = WIDTH * 3; // scales the pixels 3 times as large so we can see
							// the screen good.
		int hh = HEIGHT * 3; // scales the pixels 3 times as large so we can see
								// the screen good.
		int xo = (getWidth() - ww) / 2; // gets an offset for the image.
		int yo = (getHeight() - hh) / 2; // gets an offset for the image.
		g2d.drawImage(image, xo, yo, ww, hh, null); // draws the image on the
													// window

		g2d.dispose(); // releases any system resources that are using this
						// method. (so we don't have crappy framerates)
		bs.show(); // makes the picture visible. (I think)
	}

	private void renderGui() {
		if (isCreativeMode && !isSurvivalMode) {
			Font.draw("Creative Mode", screen, screen.w - 105, screen.h - 228, Color.get(-1, 555, 555, 555, -1));
		} else if (isSurvivalMode && !isCreativeMode) {
			Font.draw("Survival Mode", screen, screen.w - 105, screen.h - 228, Color.get(-1, 555, 555, 555, -1));
		} else {

		}

		if (input.show_game_info.clicked) {
			if (!isShowGameInfo) {
				isShowGameInfo = true;
			} else {
				isShowGameInfo = false;
			}
		}

		if (isShowGameInfo) {
			Font.draw("FPS: " + fps, screen, screen.w - 365, screen.h - 228, Color.get(-1, 555, 555, 555, -1));
			Font.draw("Ticks: " + ticks, screen, screen.w - 365, screen.h - 218, Color.get(-1, 555, 555, 555, -1));
			Font.draw("X: " + player.x, screen, screen.w - 365, screen.h - 208, Color.get(-1, 555, 555, 555, -1));
			Font.draw("Y: " + player.y, screen, screen.w - 365, screen.h - 198, Color.get(-1, 555, 555, 555, -1));
			Font.draw("Score: " + player.score, screen, screen.w - 365, screen.h - 188, Color.get(-1, 555, 555, 555, -1));
		} else {

		}

		for (int y = 0; y < 2; y++) {
			for (int x = 0; x < 29; x++) {
				screen.render(x * 7, screen.h - 16 + y * 8, 0 + 12 * 32, Color.get(-1, -1, -1, -1, -1), 0);
			}
		}

		for (int y = 1; y < 2; y++) {
			for (int x = 12; x < 29; x++) {
				screen.render(x * 7, screen.h - 16 + y * 8, 0 + 1 * 32, Color.get(0, 0, 0, 0, -1), 0);
			}
		}

		if (!isCreativeMode && isSurvivalMode) {
			for (int i = 0; i < 10; i++) {
				if (i < player.health)
					screen.render(i * 8, screen.h - 16, 0 + 12 * 32, Color.get(000, 200, 500, 533, -1), 0);// renders
																											// your
																											// current
																											// red
																											// hearts.
				else
					screen.render(i * 8, screen.h - 16, 0 + 12 * 32, Color.get(000, 100, 000, 000, -1), 0);// renders
																											// black
																											// hearts
																											// for
																											// damaged
																											// health.	
				if (player.staminaRechargeDelay > 0) {
					if (player.staminaRechargeDelay / 4 % 2 == 0)
						screen.render(i * 8, screen.h - 8, 1 + 12 * 32, Color.get(000, 555, 000, 000, -1), 0);// creates
																												// the
																												// blinking
																												// effect
																												// when
																												// you
																												// run
																												// out
																												// of
																												// stamina.
																												// (white
																												// part)
					else
						screen.render(i * 8, screen.h - 8, 1 + 12 * 32, Color.get(000, 110, 000, 000, -1), 0);// creates
																												// the
																												// blinking
																												// effect
																												// when
																												// you
																												// run
																												// out
																												// of
																												// stamina.
																												// (gray
																												// part)
				} else {
					if (i < player.stamina)
						screen.render(i * 8, screen.h - 8, 1 + 12 * 32, Color.get(000, 220, 550, 553, -1), 0);// renders
																												// your
																												// current
																												// stamina
					else
						screen.render(i * 8, screen.h - 8, 1 + 12 * 32, Color.get(000, 110, 000, 000, -1), 0);// renders
																												// your
																												// uncharged
																												// stamina
																												// (grayed)
				}
			}
		}
		if (player.activeItem != null) {
			player.activeItem.renderInventory(screen, 12 * 7, screen.h - 8);// if
																			// you
																			// have
																			// an
																			// active
																			// item
																			// then
																			// it
																			// will
																			// render
																			// the
																			// item
																			// sprite
																			// and
																			// it's
																			// name.
		}

		if (menu != null) {
			menu.render(screen);// if there is an active menu, then it will
								// render it.
		}
	}

	/** Renders the "Click to focus" box when you click off the screen. */
	private void renderFocusNagger() {
		String msg = "Click to focus!"; // the message when you click off the
										// screen.
		int xx = (WIDTH - msg.length() * 8) / 2; // the width of the box
		int yy = (HEIGHT - 8) / 2; // the height of the box
		int w = msg.length(); // length of the message. (by characters)
		int h = 1;

		screen.render(xx - 8, yy - 8, 0 + 15 * 32, Color.get(-1, 1, 5, 445, -1), 0); // renders
																						// a
																						// corner
																						// of
																						// the
																						// box
		screen.render(xx + w * 8, yy - 8, 0 + 15 * 32, Color.get(-1, 1, 5, 445, -1), 1); // renders
																							// a
																							// corner
																							// of
																							// the
																							// box
		screen.render(xx - 8, yy + 8, 0 + 15 * 32, Color.get(-1, 1, 5, 445, -1), 2); // renders
																						// a
																						// corner
																						// of
																						// the
																						// box
		screen.render(xx + w * 8, yy + 8, 0 + 15 * 32, Color.get(-1, 1, 5, 445, -1), 3); // renders
																							// a
																							// corner
																							// of
																							// the
																							// box
		for (int x = 0; x < w; x++) {
			screen.render(xx + x * 8, yy - 8, 1 + 13 * 32, Color.get(-1, 1, 5, 445, -1), 0); // renders
																								// the
																								// top
																								// part
																								// of
																								// the
																								// box
			screen.render(xx + x * 8, yy + 8, 1 + 13 * 32, Color.get(-1, 1, 5, 445, -1), 2); // renders
																								// the
																								// bottom
																								// part
																								// of
																								// the
																								// box
		}
		for (int y = 0; y < h; y++) {
			screen.render(xx - 8, yy + y * 8, 2 + 13 * 32, Color.get(-1, 1, 5, 445, -1), 0); // renders
																								// the
																								// left
																								// part
																								// of
																								// the
																								// box
			screen.render(xx + w * 8, yy + y * 8, 2 + 13 * 32, Color.get(-1, 1, 5, 445, -1), 1); // renders
																									// the
																									// right
																									// part
																									// of
																									// the
																									// box
		}

		if ((tickCount / 20) % 2 == 0) {
			Font.draw(msg, screen, xx, yy, Color.get(5, 333, 333, 333, -1)); // renders
																				// the
																				// text
																				// with
																				// a
																				// flash
																				// effect.
																				// (medium
																				// yellow
																				// color)
		} else {
			Font.draw(msg, screen, xx, yy, Color.get(5, 555, 555, 555, -1)); // renders
																				// the
																				// text
																				// with
																				// a
																				// flash
																				// effect.
																				// (bright
																				// yellow
																				// color)
		}
	}

	/**
	 * This method is called when you interact with stairs, this will give you
	 * the transition effect. While changeLevel(int) just changes the level.
	 */
	public void scheduleLevelChange(int dir) {
		pendingLevelChange = dir; // same as changeLevel(). Call
									// scheduleLevelChange(1) if you want to go
									// up 1 level, or call -1 to go down by 1.
	}

	/*
	 * This is called when the player has won the game
	 * 
	 * @throws ParseException
	 * 
	 * @throws IOException
	 * 
	 * @throws FileNotFoundException
	 */
	// public void won() {
	// wonTimer = 60 * 3; // the pause time before the win menu shows up.
	// hasWon = true; // confirms that the player has indeed, won the game.
	// }

	/**
	 * Initialize the graphical components
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	@SuppressWarnings("deprecation")
	private void initGraphicalComponents() {
		try {
			if (GAME_JSON_FILE.exists()) {
				if (FileUtils.readFileToString(GAME_JSON_FILE).contains("mysticcraft.spritesheet")) {
					Object obj = jsonParser.parse(new FileReader(GAME_JSON_FILE));
					JsonObject jsonObj = (JsonObject) obj;
					String spritesheet = (String) jsonObj.get("mysticcraft.spritesheet").getAsString();
					File spritesheetFile = new File(Reference.SPRITE_SHEET_RESOURCE_DIRECTORY + "/" + spritesheet + ".png");
					if (spritesheetFile.exists()) {
						screen = new Screen(WIDTH, HEIGHT, new SpriteSheet(ImageIO.read(spritesheetFile)));
						lightScreen = new Screen(WIDTH, HEIGHT, new SpriteSheet(ImageIO.read(spritesheetFile)));
					} else {
						screen = new Screen(WIDTH, HEIGHT, new SpriteSheet(ImageIO.read(Game.class.getResourceAsStream(Reference.RESOURCE_LOCATION + Reference.DEFAULT_SPRITE_SHEET))));
						lightScreen = new Screen(WIDTH, HEIGHT, new SpriteSheet(ImageIO.read(Game.class.getResourceAsStream(Reference.RESOURCE_LOCATION + Reference.DEFAULT_SPRITE_SHEET))));
					}
					if (spritesheet.equals("")) {
						screen = new Screen(WIDTH, HEIGHT, new SpriteSheet(ImageIO.read(Game.class.getResourceAsStream(Reference.RESOURCE_LOCATION + Reference.DEFAULT_SPRITE_SHEET))));
						lightScreen = new Screen(WIDTH, HEIGHT, new SpriteSheet(ImageIO.read(Game.class.getResourceAsStream(Reference.RESOURCE_LOCATION + Reference.DEFAULT_SPRITE_SHEET))));
					} else {
						screen = new Screen(WIDTH, HEIGHT, new SpriteSheet(ImageIO.read(spritesheetFile)));
						lightScreen = new Screen(WIDTH, HEIGHT, new SpriteSheet(ImageIO.read(spritesheetFile)));
					}
				} else {
					screen = new Screen(WIDTH, HEIGHT, new SpriteSheet(ImageIO.read(Game.class.getResourceAsStream(Reference.RESOURCE_LOCATION + Reference.DEFAULT_SPRITE_SHEET))));
					lightScreen = new Screen(WIDTH, HEIGHT, new SpriteSheet(ImageIO.read(Game.class.getResourceAsStream(Reference.RESOURCE_LOCATION + Reference.DEFAULT_SPRITE_SHEET))));
				}
			} else {
				screen = new Screen(WIDTH, HEIGHT, new SpriteSheet(ImageIO.read(Game.class.getResourceAsStream(Reference.RESOURCE_LOCATION + Reference.DEFAULT_SPRITE_SHEET))));
				lightScreen = new Screen(WIDTH, HEIGHT, new SpriteSheet(ImageIO.read(Game.class.getResourceAsStream(Reference.RESOURCE_LOCATION + Reference.DEFAULT_SPRITE_SHEET))));
			}
		} catch (Exception e) {
		}
	}

	// Utils section
	public void screenShotTakingEvent() {

		if (getScreenShotCliked() == 1) {
			if (!screenShotDirectory.exists()) {
				Utils.createDirectory(Reference.SCREENSHOT_DIRECTORY);
			}
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 2) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 3) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 4) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 5) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 6) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 7) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 8) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 9) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 10) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 11) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 12) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 13) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 14) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 15) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 16) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 17) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 18) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 19) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 20) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 21) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 22) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 23) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 24) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 25) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 26) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 27) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 28) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 29) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 30) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}
		if (getScreenShotCliked() == 31) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 32) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 33) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 34) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}
		if (getScreenShotCliked() == 35) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 36) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 37) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 38) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}
		if (getScreenShotCliked() == 39) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 40) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 41) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 42) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 43) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 44) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 45) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 46) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 47) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 48) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 49) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 50) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 51) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 52) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 53) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 54) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 55) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 56) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 57) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 58) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 59) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 60) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 61) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}
		if (getScreenShotCliked() == 62) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 63) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 64) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 65) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 66) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 67) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 68) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 69) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 70) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 71) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 72) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 73) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 74) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 75) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 76) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 77) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 78) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 79) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 80) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 81) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 82) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 83) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 84) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 85) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 86) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 87) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 88) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 89) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 90) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 91) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 92) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 93) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}
		if (getScreenShotCliked() == 94) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 95) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 96) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 97) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 98) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 99) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
		}

		if (getScreenShotCliked() == 100) {
			MinicraftUtils.takeScreenshot(dim.width, dim.height, "/screenshot_" + getScreenShotCliked());
			input.screenshot.toggle(false);
			setScreenShotCliked(0);
		}

	}

	public int getScreenShotCliked() {
		return screenShotCliked;
	}

	public void setScreenShotCliked(int screenShotCliked) {
		this.screenShotCliked = screenShotCliked;
	}

}