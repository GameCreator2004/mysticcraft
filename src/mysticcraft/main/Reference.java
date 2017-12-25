package mysticcraft.main;

import tk.jidgu.util.Sys;

public class Reference {
	private static String GAME_DIRECTORY = Sys.userDir + "/mysticcraft";
	public static final String HOME_DIRECTORY = Sys.userHome + "/AppData/Roaming/mysticcraft";
	public static final String SCREENSHOT_DIRECTORY = GAME_DIRECTORY + "/screenshot"; 
	public static final String RESOURCE_DIRECTORY = GAME_DIRECTORY + "/resource";
	public static final String ICON_LOCATION = "/icon.png";
	public static final String CURSOR_ICON = "/cursor32x32.png";
	public static final String RESOURCE_LOCATION = "/mysticcraft/res";
	public static final String ESOURCE_LOCATION = "mysticcraft/res";
	public static final String EXCEPTION_RESOURCE_DIRECTORY = RESOURCE_DIRECTORY + "/exception";
	private static final String GAME_TITLE = "Mysticcraft";
	private static final String GAME_VERSION = "1.4";
	private static final String GAME_STATE = " Dev ";
	public static final String DEFAULT_SPRITE_SHEET = "/default_icons.png";
	public static final String DEFAULT_TITLE = GAME_TITLE + GAME_STATE + GAME_VERSION;
	public static final String SPRITE_SHEET_RESOURCE_DIRECTORY = RESOURCE_DIRECTORY + "/spritesheet";
}
