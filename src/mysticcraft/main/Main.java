package mysticcraft.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import mysticcraft.utils.ThreadPool;
import tk.jidgu.util.Sys;
import tk.jidgu.util.Utils;

public class Main {
	private static final int WIDTH = Game.WIDTH;
	private static final int HEIGHT = Game.HEIGHT;
	private static final int SCALE = Game.SCALE;
	private static final PrintStream err = System.err;
	private static final PrintStream out = System.out;

	/**
	 * The first method that is called when the application starts.
	 * 
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			Game.getComputerInfo();
			Game.preInit();
			Utils.displayOptionPaneIfIncompatible(1.8, Reference.RESOURCE_LOCATION + Reference.ICON_LOCATION);
			ThreadPool pool = new ThreadPool(2);
			Game game = new Game(); // creates a new game.
			game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
			game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
			game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
			
			game.frame.setTitle(Reference.DEFAULT_TITLE);
			Utils.setImageAsApplicationDefaultIcon(game.frame, Reference.RESOURCE_LOCATION, Reference.ICON_LOCATION);
			Utils.setCustomCursorImage(game.frame, Reference.RESOURCE_LOCATION, Reference.CURSOR_ICON, game.frame.getX(), game.frame.getY(), "cursor");

			game.frame.addWindowListener(new WindowAdapter() {

				@Override
				public void windowClosing(WindowEvent e) {
					Sys.println("Exiting game", err);
					game.stop();
					Sys.println("Game Exited", err);
				}

				@Override
				public void windowOpened(WindowEvent e) {
					Sys.println("Window opened", out);
				}
			});
			game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			game.frame.setLayout(new BorderLayout());
			
			game.frame.add(game, BorderLayout.CENTER); // Adds the game (since
														// it's a canvas) to the
														// center of the
														// applet.
			game.frame.pack(); // contains everything in to the preferredSize
			game.frame.setResizable(false); // A user cannot resize the window.
			game.frame.setLocationRelativeTo(null); // the window will pop up in
													// the middle of the screen
													// when launched.
			game.frame.setVisible(true);

			pool.runTask(game);
			pool.join();

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {}
	}

}
