package mysticcraft.item;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;

import mysticcraft.engine.SaveEngine;
import mysticcraft.entity.Entity;
import mysticcraft.entity.furniture.Anvil;
import mysticcraft.entity.furniture.Chest;
import mysticcraft.entity.furniture.Enchanter;
import mysticcraft.entity.furniture.Furnace;
import mysticcraft.entity.furniture.GoldenWorkbench;
import mysticcraft.entity.furniture.Jukebox;
import mysticcraft.entity.furniture.Lantern;
import mysticcraft.entity.furniture.Oven;
import mysticcraft.entity.furniture.Torch;
import mysticcraft.entity.furniture.Workbench;
import mysticcraft.entity.item.ItemEntity;
import mysticcraft.entity.mob.Player;
import mysticcraft.gfx.Screen;
import mysticcraft.io.MysticcraftFile;
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
import mysticcraft.main.Reference;
import mysticcraft.screen.ListItem;
import tk.jidgu.util.Sys;

public class Item extends SaveEngine implements ListItem {

	/*
	 * Note: Most of the stuff in the class is expanded upon in
	 * ResourceItem/PowerGloveItem/FurnitureItem/etc
	 */

	public File itemNameFile = Game.GAME_JSON_FILE;
	protected String itemNameInJSONFile;
	protected String itemDefaultName;
	
	public Item(String itemNameInJSONFile, String itemDefaultName) {
		this.itemNameInJSONFile = itemNameInJSONFile.toLowerCase();
		this.itemDefaultName = itemDefaultName;
	}

	/** Gets the color of the item */
	public int getColor() {
		return 0;
	}

	/** Gets the sprite of the item */
	public int getSprite() {
		return 0;
	}

	/** What happens when you pick up the item off the ground */
	public void onTake(ItemEntity itemEntity) {
	}

	/** Renders an item (sprite & name) in an inventory */
	public void renderInventory(Screen screen, int x, int y) {
	}

	/** Determines what happens when the player interacts with a entity */
	public boolean interact(Player player, Entity entity, int attackDir) {
		return false;
	}

	/** Renders the icon of the Item */
	public void renderIcon(Screen screen, int x, int y) {
	}

	/** Determines what happens when you use a item in a tile */
	public boolean interactOn(Tile tile, Level level, int xt, int yt, Player player, int attackDir) {
		return false;
	}

	/** Returns if the item is depleted or not */
	public boolean isDepleted() {
		return false;
	}

	/** Returns if the item can attack mobs or not */
	public boolean canAttack() {
		return false;
	}

	public int getAttackDamage(Entity e) {
		return 2;
	}

	/** Gets the attack bonus from an item/tool (sword/axe) */
	public int getAttackDamageBonus(Entity e) {
		return 2;
	}

	/** Gets the name of the tool */
	@SuppressWarnings("deprecation")
	public String getName() {
		if (itemNameFile.exists()) {
			try {
				if (FileUtils.readFileToString(itemNameFile).contains(itemNameInJSONFile)) {
					Object obj = Game.jsonParser.parse(new FileReader(itemNameFile));
					JsonObject jsonObject = (JsonObject) obj;
					String name = (String) jsonObject.get(itemNameInJSONFile).getAsString();
					if (name.equals("")) {
						//Sys.println("No custom name tag to change the item name", Sys.out);
						return "" + itemDefaultName;
					}
					return "" + name;
				} else {
					return "" + itemDefaultName;
				}
			} catch (IOException e) {
				try {
					FileUtils.write(new File(Reference.EXCEPTION_RESOURCE_DIRECTORY + "/" + itemNameInJSONFile.replace("item.", "").replace("resource.", "").replace("tool.", "") + ".exception"), e.getMessage());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			return "";
		} else {
			return "" + itemDefaultName;
		}
	}

	public void loadFrom(StringTokenizer st, Entity e) {
		super.loadFrom(st);
		if (e instanceof Player) {
			Player p = (Player) e;
			if (this instanceof ResourceItem) {
				try {
					ResourceItem ri = (ResourceItem) this;
					Object obj = Game.jsonParser.parse(new FileReader(new MysticcraftFile(Reference.HOME_DIRECTORY, p.getMobName().toLowerCase() + "_" + itemNameInJSONFile, "count")));
					JsonObject jsonObject = (JsonObject) obj;
					ri.count = jsonObject.get(itemNameInJSONFile).getAsInt();
				} catch (Exception ex) {
				}
			}
		} else if (e instanceof Chest) {
			Chest c = (Chest) e;
			if (this instanceof ResourceItem) {
				try {
					ResourceItem ri = (ResourceItem) this;
					Object obj = Game.jsonParser.parse(new FileReader(new MysticcraftFile(Reference.HOME_DIRECTORY, c.getName().toLowerCase() + "_" + itemNameInJSONFile, "count")));
					JsonObject jsonObject = (JsonObject) obj;
					ri.count = jsonObject.get(itemNameInJSONFile).getAsInt();
				} catch (Exception ex) {
				}
			}
		}
	}

	public void saveTo(StringBuffer str, Entity e) {
		super.saveTo(str);
		if (e instanceof Player) {
			Player p = (Player) e;
			if (this instanceof ResourceItem) {
				ResourceItem ri = (ResourceItem) this;
				try (JsonWriter writer = new JsonWriter(new FileWriter(new MysticcraftFile(Reference.HOME_DIRECTORY, p.getMobName().toLowerCase() + "_" + itemNameInJSONFile, "count")))) {
					JsonObject jsonObject = new JsonObject();
					jsonObject.addProperty(itemNameInJSONFile, ri.count);
					writer.jsonValue(jsonObject.toString());
					writer.flush();
				} catch (IOException ex) {
				}
			}
		} else if (e instanceof Chest) {
			Chest c = (Chest) e;
			if (this instanceof ResourceItem) {
				ResourceItem ri = (ResourceItem) this;
				try (JsonWriter writer = new JsonWriter(new FileWriter(new MysticcraftFile(Reference.HOME_DIRECTORY, c.getName().toLowerCase() + "_" + itemNameInJSONFile, "count")))) {
					JsonObject jsonObject = new JsonObject();
					jsonObject.addProperty(itemNameInJSONFile, ri.count);
					writer.jsonValue(jsonObject.toString());
					writer.flush();
				} catch (IOException ex) {
				}
			}
		}
		str.append(itemNameInJSONFile + " ");
	}

	/** Sees if an item matches another item */
	public boolean matches(Item item) {
		return item.getClass() == getClass();
	}

	public static Item get(StringTokenizer st, Entity e) {
		String cl = st.nextToken();
		Item it = null;
		if (cl.equals("item.sky_totem")) {
			it = new SkyTotemItem();
		} else if (cl.equals("item.backpack")) {
			it = new BackpackItem();
		} else if (cl.equals("item.normal_disc")) {
			it = new MusicDiscItem(MusicType.NORMAL);
		} else if (cl.equals("item.special_disc")) {
			it = new MusicDiscItem(MusicType.SPECIAL);
		} else if (cl.equals("item.furniture.workbench")) {
			it = new FurnitureItem(new Workbench());
		} else if (cl.equals("item.furniture.gold_workbench")) {
			it = new FurnitureItem(new GoldenWorkbench());
		} else if (cl.equals("item.furniture.chest")) {
			it = new FurnitureItem(new Chest());
		} else if (cl.equals("item.furniture.enchanter")) {
			it = new FurnitureItem(new Enchanter());
		} else if (cl.equals("item.furniture.anvil")) {
			it = new FurnitureItem(new Anvil());
		} else if (cl.equals("item.furniture.oven")) {
			it = new FurnitureItem(new Oven());
		} else if (cl.equals("item.furniture.furnace")) {
			it = new FurnitureItem(new Furnace());
		} else if (cl.equals("item.furniture.lantern")) {
			it = new FurnitureItem(new Lantern());
		} else if (cl.equals("item.furniture.jukebox")) {
			it = new FurnitureItem(new Jukebox());
		} else if (cl.equals("item.furniture.torch")) {
			it = new FurnitureItem(new Torch());
		} else if (cl.equals("item.power_glove")) {
			it = new GloveItem(GloveType.POWER);
		} else if (cl.equals("item.heal_glove")) {
			it = new GloveItem(GloveType.HEAL);
		} else if (cl.equals("item.empty_bucket")) {
			it = new BucketItem(BucketState.EMPTY);
		} else if (cl.equals("item.water_bucket")) {
			it = new BucketItem(BucketState.WATER_FILLED);
		} else if (cl.equals("item.lava_bucket")) {
			it = new BucketItem(BucketState.LAVA_FILLED);
		} else if (cl.equals("item.sand_bucket")) {
			it = new BucketItem(BucketState.SAND_FILLED);
		} else if (cl.equals("tool.wood_axe")) {
			it = new ToolItem(ToolType.AXE, 0);
		} else if (cl.equals("tool.wood_sword")) {
			it = new ToolItem(ToolType.SWORD, 0);
		} else if (cl.equals("tool.wood_shovel")) {
			it = new ToolItem(ToolType.SHOVEL, 0);
		} else if (cl.equals("tool.wood_hoe")) {
			it = new ToolItem(ToolType.HOE, 0);
		} else if (cl.equals("tool.wood_pickaxe")) {
			it = new ToolItem(ToolType.PICKAXE, 0);
		} else if (cl.equals("tool.wood_spear")) {
			it = new ToolItem(ToolType.SPEAR, 0);
		} else if (cl.equals("tool.wood_hammer")) {
			it = new ToolItem(ToolType.HAMMER, 0);
		} else if (cl.equals("tool.rock_axe")) {
			it = new ToolItem(ToolType.AXE, 1);
		} else if (cl.equals("tool.rock_sword")) {
			it = new ToolItem(ToolType.SWORD, 1);
		} else if (cl.equals("tool.rock_shovel")) {
			it = new ToolItem(ToolType.SHOVEL, 1);
		} else if (cl.equals("tool.rock_hoe")) {
			it = new ToolItem(ToolType.HOE, 1);
		} else if (cl.equals("tool.rock_pickaxe")) {
			it = new ToolItem(ToolType.PICKAXE, 1);
		} else if (cl.equals("tool.rock_spear")) {
			it = new ToolItem(ToolType.SPEAR, 1);
		} else if (cl.equals("tool.rock_hammer")) {
			it = new ToolItem(ToolType.HAMMER, 1);
		} else if (cl.equals("tool.a.rock_axe")) {
			it = new ToolItem(ToolType.AXE, 2);
		} else if (cl.equals("tool.a.rock_sword")) {
			it = new ToolItem(ToolType.SWORD, 2);
		} else if (cl.equals("tool.a.rock_shovel")) {
			it = new ToolItem(ToolType.SHOVEL, 2);
		} else if (cl.equals("tool.a.rock_hoe")) {
			it = new ToolItem(ToolType.HOE, 2);
		} else if (cl.equals("tool.a.rock_pickaxe")) {
			it = new ToolItem(ToolType.PICKAXE, 2);
		} else if (cl.equals("tool.a.rock_spear")) {
			it = new ToolItem(ToolType.SPEAR, 2);
		} else if (cl.equals("tool.a.rock_hammer")) {
			it = new ToolItem(ToolType.HAMMER, 2);
		} else if (cl.equals("tool.iron_axe")) {
			it = new ToolItem(ToolType.AXE, 3);
		} else if (cl.equals("tool.iron_sword")) {
			it = new ToolItem(ToolType.SWORD, 3);
		} else if (cl.equals("tool.iron_shovel")) {
			it = new ToolItem(ToolType.SHOVEL, 3);
		} else if (cl.equals("tool.iron_hoe")) {
			it = new ToolItem(ToolType.HOE, 3);
		} else if (cl.equals("tool.iron_pickaxe")) {
			it = new ToolItem(ToolType.PICKAXE, 3);
		} else if (cl.equals("tool.iron_spear")) {
			it = new ToolItem(ToolType.SPEAR, 3);
		} else if (cl.equals("tool.iron_hammer")) {
			it = new ToolItem(ToolType.HAMMER, 3);
		} else if (cl.equals("tool.gold_axe")) {
			it = new ToolItem(ToolType.AXE, 4);
		} else if (cl.equals("tool.gold_sword")) {
			it = new ToolItem(ToolType.SWORD, 4);
		} else if (cl.equals("tool.gold_shovel")) {
			it = new ToolItem(ToolType.SHOVEL, 4);
		} else if (cl.equals("tool.gold_hoe")) {
			it = new ToolItem(ToolType.HOE, 4);
		} else if (cl.equals("tool.gold_pickaxe")) {
			it = new ToolItem(ToolType.PICKAXE, 4);
		} else if (cl.equals("tool.gold_spear")) {
			it = new ToolItem(ToolType.SPEAR, 4);
		} else if (cl.equals("tool.gold_hammer")) {
			it = new ToolItem(ToolType.HAMMER, 4);
		} else if (cl.equals("tool.gem_axe")) {
			it = new ToolItem(ToolType.AXE, 5);
		} else if (cl.equals("tool.gem_sword")) {
			it = new ToolItem(ToolType.SWORD, 5);
		} else if (cl.equals("tool.gem_shovel")) {
			it = new ToolItem(ToolType.SHOVEL, 5);
		} else if (cl.equals("tool.gem_hoe")) {
			it = new ToolItem(ToolType.HOE, 5);
		} else if (cl.equals("tool.gem_pickaxe")) {
			it = new ToolItem(ToolType.PICKAXE, 5);
		} else if (cl.equals("tool.gem_spear")) {
			it = new ToolItem(ToolType.SPEAR, 5);
		} else if (cl.equals("tool.gem_hammer")) {
			it = new ToolItem(ToolType.HAMMER, 5);
		} else if (cl.equals("tool.h.rock_axe")) {
			it = new ToolItem(ToolType.AXE, 6);
		} else if (cl.equals("tool.h.rock_sword")) {
			it = new ToolItem(ToolType.SWORD, 6);
		} else if (cl.equals("tool.h.rock_shovel")) {
			it = new ToolItem(ToolType.SHOVEL, 6);
		} else if (cl.equals("tool.h.rock_hoe")) {
			it = new ToolItem(ToolType.HOE, 6);
		} else if (cl.equals("tool.h.rock_pickaxe")) {
			it = new ToolItem(ToolType.PICKAXE, 6);
		} else if (cl.equals("tool.h.rock_spear")) {
			it = new ToolItem(ToolType.SPEAR, 6);
		} else if (cl.equals("tool.h.rock_hammer")) {
			it = new ToolItem(ToolType.HAMMER, 6);
		} else if (cl.equals("resource.apple")) {
			it = new ResourceItem(Resource.apple);
		} else if (cl.equals("resource.golden_apple")) {
			it = new ResourceItem(Resource.goldenApple);
		} else if (cl.equals("resource.apple_pie")) {
			it = new ResourceItem(Resource.applePie);
		} else if (cl.equals("resource.apple_rock")) {
			it = new ResourceItem(Resource.appleStone);
		} else if (cl.equals("resource.wood")) {
			it = new ResourceItem(Resource.wood);
		} else if (cl.equals("resource.coal")) {
			it = new ResourceItem(Resource.coal);
		} else if (cl.equals("resource.charcoal")) {
			it = new ResourceItem(Resource.charcoal);
		} else if (cl.equals("resource.rock")) {
			it = new ResourceItem(Resource.stone);
		} else if (cl.equals("resource.hard_rock")) {
			it = new ResourceItem(Resource.hardStone);
		} else if (cl.equals("resource.seeds")) {
			it = new ResourceItem(Resource.seeds);
		} else if (cl.equals("resource.wheat")) {
			it = new ResourceItem(Resource.wheat);
		} else if (cl.equals("resource.bread")) {
			it = new ResourceItem(Resource.bread);
		} else if (cl.equals("resource.acorn")) {
			it = new ResourceItem(Resource.acorn);
		} else if (cl.equals("resource.yellow_flower")) {
			it = new ResourceItem(Resource.yellowFlower);
		} else if (cl.equals("resource.gray_flower")) {
			it = new ResourceItem(Resource.flower);
		} else if (cl.equals("resource.cactus")) {
			it = new ResourceItem(Resource.cactusFlower);
		} else if (cl.equals("resource.dirt")) {
			it = new ResourceItem(Resource.dirt);
		} else if (cl.equals("resource.sand")) {
			it = new ResourceItem(Resource.sand);
		} else if (cl.equals("resource.iron_ore")) {
			it = new ResourceItem(Resource.ironOre);
		} else if (cl.equals("resource.gold_ore")) {
			it = new ResourceItem(Resource.goldOre);
		} else if (cl.equals("resource.iron_ingot")) {
			it = new ResourceItem(Resource.ironIngot);
		} else if (cl.equals("resource.gold_ingot")) {
			it = new ResourceItem(Resource.goldIngot);
		} else if (cl.equals("resource.slime")) {
			it = new ResourceItem(Resource.slime);
		} else if (cl.equals("resource.glass")) {
			it = new ResourceItem(Resource.glass);
		} else if (cl.equals("resource.cloth")) {
			it = new ResourceItem(Resource.cloth);
		} else if (cl.equals("resource.cloud")) {
			it = new ResourceItem(Resource.cloud);
		} else if (cl.equals("resource.gem")) {
			it = new ResourceItem(Resource.gem);
		} else if (cl.equals("resource.stick")) {
			it = new ResourceItem(Resource.stick);
		} else if (cl.equals("resource.sky_star")) {
			it = new ResourceItem(Resource.airStar);
		} else if (cl.equals("resource.rotten_flesh")) {
			it = new ResourceItem(Resource.rottenFlesh);
		} else if (cl.equals("resource.green_dye")) {
			it = new ResourceItem(Resource.cactusGreen);
		} else if (cl.equals("resource.red_dye")) {
			it = new ResourceItem(Resource.appleRed);
		} else if (cl.equals("resource.yellow_dye")) {
			it = new ResourceItem(Resource.yellowDye);
		} else if (cl.equals("resource.light_blue_dye")) {
			it = new ResourceItem(Resource.lightBlueDye);
		} else if (cl.equals("resource.lapis")) {
			it = new ResourceItem(Resource.lapis);
		} else if (cl.equals("resource.lapis_ore")) {
			it = new ResourceItem(Resource.lapisOre);
		} else if (cl.equals("resource.gold_nugget")) {
			it = new ResourceItem(Resource.goldNugget);
		} else if (cl.equals("resource.iron_nugget")) {
			it = new ResourceItem(Resource.ironNugget);
		} else if (cl.equals("resource.orange_dye")) {
			it = new ResourceItem(Resource.orangeDye);
		} else if (cl.equals("resource.black_dye")) {
			it = new ResourceItem(Resource.blackDye);
		} else if (cl.equals("resource.gray_dye")) {
			it = new ResourceItem(Resource.greyDye);
		} else if (cl.equals("resource.lime_dye")) {
			it = new ResourceItem(Resource.limeDye);
		} else if (cl.equals("resource.bone_meal")) {
			it = new ResourceItem(Resource.whiteDye);
		} else if (cl.equals("resource.pink_dye")) {
			it = new ResourceItem(Resource.pinkDye);
		} else if (cl.equals("resource.leaf")) {
			it = new ResourceItem(Resource.leaf);
		} else if (cl.equals("resource.bone")) {
			it = new ResourceItem(Resource.bone);
		} else if (cl.equals("resource.rupee")) {
			it = new ResourceItem(Resource.rupee);
		} else {
			Sys.println("[Item] Unknow item: " + cl, Sys.out);
		}
		if (it != null) {
			it.loadFrom(st, e);
		}
		return it;
	}

}
