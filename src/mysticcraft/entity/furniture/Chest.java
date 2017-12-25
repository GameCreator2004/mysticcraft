package mysticcraft.entity.furniture;

import java.util.Random;
import java.util.StringTokenizer;

import mysticcraft.entity.item.Inventory;
import mysticcraft.entity.mob.Player;
import mysticcraft.gfx.Color;
import mysticcraft.item.FurnitureItem;
import mysticcraft.item.ResourceItem;
import mysticcraft.item.ToolItem;
import mysticcraft.item.resource.Resource;
import mysticcraft.item.type.ToolType;
import mysticcraft.main.Game;
import mysticcraft.screen.ContainerMenu;
import tk.jidgu.util.Sys;

public class Chest extends Furniture {
	public Inventory inventory = new Inventory(this); // Inventory of the chest
	/* This is a sub-class of furniture.java, go there for more info */
	private Random r = new Random();
	private int rand = r.nextInt(20);

	public Chest() {
		super("Chest"); // Name of the chest
		col = Color.get(-1, 110, 331, 552, -1); // Color of the chest
		sprite = 1; // Location of the sprite
	}

	public Chest(int x, int y, String name) {
		super(name);
		col = Color.get(-1, 110, 331, 552, -1); // Color of the chest
		sprite = 1;
		if (Game.ibc) {
			Sys.println(rand, Sys.out);
			addBonusItems();
		} else {
		}
		this.x = x;
		this.y = y;
	}

	public String getName() {
		return name;
	}

	/** This is what occurs when the player uses the "Menu" command near this */
	public boolean use(Player player, int attackDir) {
		// inventory.add(new SkyTotemItem());
		player.game.setMenu(new ContainerMenu(player, getName(), inventory)); // Opens
																				// up
																				// a
																				// menu
																				// with
																				// the
																				// player's
																				// inventory
																				// and
																				// the
																				// chest's
																				// inventory
		return true;
	}

	private void addBonusItems() {
		if (rand == 0 || rand == 2 || rand == 4 || rand == 6 || rand == 8 || rand == 10 || rand == 12 || rand == 14 || rand == 16 || rand == 18 || rand == 20) {
			inventory.add(new ToolItem(ToolType.AXE, 0));
			inventory.add(new ResourceItem(Resource.apple, 8));
			inventory.add(new ToolItem(ToolType.PICKAXE, 0));
		}
		if (rand == 1 || rand == 3 || rand == 5 || rand == 7 || rand == 9 || rand == 11 || rand == 13 || rand == 15 || rand == 17 || rand == 19) {
			inventory.add(new ToolItem(ToolType.AXE, 0));
			inventory.add(new ResourceItem(Resource.apple, 8));
			inventory.add(new FurnitureItem(new Torch()));
			// inventory.add(new ToolItem(ToolType.PICKAXE, 0));
		}
	}
	
	@Override
	public void loadFrom(StringTokenizer st) {
		super.loadFrom(st);
		this.x = nextInt(st);
		this.y = nextInt(st);
		this.inventory.items.clear();
		this.inventory.loadFrom(st);
	}
	
	@Override
	public void saveTo(StringBuffer str) {
		super.saveTo(str);
		str.append(this.x + " " + this.y + " ");
		this.inventory.saveTo(str);
	}
}