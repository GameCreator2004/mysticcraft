package mysticcraft.item.resource;

import mysticcraft.entity.mob.Player;
import mysticcraft.gfx.Color;
import mysticcraft.level.Level;
import mysticcraft.level.tile.Tile;

public class Resource {

	public static Resource goldenApple = new FoodResource("Golden Apple", 9 + 4 * 32, Color.get(-1, 110, 440, 553, -1), 9, 6);
	public static Resource wood = new Resource("Wood", 1 + 4 * 32, Color.get(-1, 200, 531, 430, -1));
	public static Resource stone = new Resource("Rock", 2 + 4 * 32, Color.get(-1, 111, 333, 555, -1));
	public static Resource hardStone = new Resource("Hard Rock", 2 + 4 * 32, Color.get(-1, 111, 001, 334, -1));
	public static Resource flower = new PlantableResource("Gray Flower", 0 + 4 * 32, Color.get(-1, 10, 444, 330, -1), Tile.flower, Tile.grass);
	public static Resource yellowFlower = new Resource("Yellow Flower", 0 + 4 * 32, Color.get(-1, 100, 550, 330, -1));
	
	public static Resource acorn = new PlantableResource("Acorn", 3 + 4 * 32, Color.get(-1, 100, 531, 320, -1), Tile.treeSapling, Tile.grass);
	public static Resource dirt = new PlantableResource("Dirt", 2 + 4 * 32, Color.get(-1, 100, 322, 432, -1), Tile.dirt, Tile.hole, Tile.water, Tile.lava);
	public static Resource sand = new PlantableResource("Sand", 2 + 4 * 32, Color.get(-1, 110, 440, 550, -1), Tile.sand, Tile.grass, Tile.dirt);
	public static Resource cactusFlower = new PlantableResource("Cactus", 4 + 4 * 32, Color.get(-1, 10, 40, 50, -1), Tile.cactusSapling, Tile.sand);
	public static Resource seeds = new PlantableResource("Seeds", 5 + 4 * 32, Color.get(-1, 10, 40, 50, -1), Tile.wheat, Tile.farmland);
	public static Resource wheat = new Resource("Wheat", 6 + 4 * 32, Color.get(-1, 110, 330, 550, -1));
	public static Resource bread = new FoodResource("Bread", 8 + 4 * 32, Color.get(-1, 110, 330, 550, -1), 3, 5);
	public static Resource apple = new FoodResource("Apple", 9 + 4 * 32, Color.get(-1, 100, 300, 500, -1), 2, 4);
	public static Resource appleStone = new Resource("Apple Rock", 2 + 4 * 32, Color.get(-1, 111, 300, 500, -1));

	public static Resource coal = new Resource("Coal", 10 + 4 * 32, Color.get(-1, 000, 111, 111, -1));
	public static Resource charcoal = new Resource("Charcoal", 16 + 4 * 32, Color.get(-1, 100, 320, 100, -1));
	public static Resource ironOre = new OreResource("Iron Ore", 10 + 4 * 32, Color.get(-1, 100, 322, 544, -1));
	public static Resource goldOre = new OreResource("Gold Ore", 10 + 4 * 32, Color.get(-1, 110, 440, 553, -1));
	public static Resource lapisOre = new OreResource("Lapis Ore", 10 + 4 * 32, Color.get(-1, 005, 115, 055, -1));
	public static Resource ironIngot = new Resource("Iron Ingot", 11 + 4 * 32, Color.get(-1, 100, 322, 544, -1));
	public static Resource goldIngot = new Resource("Gold Ingot", 11 + 4 * 32, Color.get(-1, 110, 330, 553, -1));
	public static Resource rupee = new OreResource("Rupee", 23 + 4 * 32, Color.get(-1, 10, 141, 153, -1));
	public static Resource lapis = new Resource("Lapis", 24 + 4 * 32, Color.get(-1, 005, 115, 055, -1));
	public static Resource gem = new Resource("Gem", 13 + 4 * 32, Color.get(-1, 101, 404, 545, -1));
	
	public static Resource slime = new Resource("Slime", 10 + 4 * 32, Color.get(-1, 10, 30, 50, -1));
	public static Resource glass = new Resource("Glass", 12 + 4 * 32, Color.get(-1, 555, 555, 555, -1));
	public static Resource cloth = new Resource("Cloth", 1 + 4 * 32, Color.get(-1, 10, 252, 050, -1));
	public static Resource cloud = new PlantableResource("Cloud", 2 + 4 * 32, Color.get(-1, 222, 555, 444, -1), Tile.cloud, Tile.infiniteFall);
	public static Resource airStar = new Resource("Sky Star", 14 + 4 * 32, Color.get(-1, 555, 55, 555, -1));
	//public static Resource fireGem = new Resource("Fire Gem", 14 + 4 * 32, Color.get(-1, 111, 111, 0xDD0000));
	public static Resource stick = new Resource("stick", 15 + 4 * 32, Color.get(-1, 200, 531, 430, -1));
	public static Resource rottenFlesh = new FoodResource("Rotten Flesh", 6 + 4 * 32, Color.get(-1, 100, 0xCC3300, 0xFFCC00, -1), 1, 4);
	
	//Dyes
	public static Resource cactusGreen = new Resource("Green Dye", 11 + 4 * 32, Color.get(-1, 10, 40, 50, -1));
	public static Resource appleRed = new Resource("Red Dye", 11 + 4 * 32, Color.get(-1, 100, 300, 500, -1));
	public static Resource pinkDye = new Resource("Pink Dye", 11 + 4 * 32, Color.get(-1, 525, 524, 534, -1));
	public static Resource greyDye = new Resource("Gray Dye", 3 + 4 * 32, Color.get(-1, 10, 444, 444, -1));
	public static Resource blackDye = new Resource("Black Dye", 11 + 4 * 32, Color.get(-1, 100, 100, 100, -1));
	public static Resource yellowDye = new Resource("Yellow Dye", 16 + 4 * 32, Color.get(-1, 550,  550, 550, -1));
	public static Resource whiteDye = new PlantableResource("Bone Meal", 17 + 4 * 32, Color.get(-1, 322, 444, 555, -1), Tile.tree, Tile.treeSapling);
	public static Resource orangeDye = new Resource("Orange Dye", 11 + 4 * 32, Color.get(-1, 510, 510, 510, -1));
	public static Resource lightBlueDye = new Resource("Light Blue Dye", 21 + 4 * 32, Color.get(-1, 034, 145, 245, -1));
	public static Resource limeDye = new Resource("Lime Dye", 21 + 4 * 32, Color.get(-1, 010, 051, 052, -1));
	///////
	
	////////Misc Items//////////
	public static Resource goldNugget = new Resource("Gold Nugget", 22 + 4 * 32, Color.get(-1, 330, 550, 555, -1));
	public static Resource ironNugget = new Resource("Iron Nugget", 22 + 3 * 32, Color.get(-1, 322, 444, 555, -1));
	
	//Foods
	public static Resource applePie = new FoodResource("Apple Pie", 18 + 4 * 32, Color.get(-1, 553, 0, 300, -1), 4, 5);
	public static Resource bone  = new Resource("Bone", 19 + 4 * 32, Color.get(-1, 444, 553, 555, -1));
	public static Resource leaf = new Resource("Leaf", 20 + 4 * 32, Color.get(-1, 10, 20, 30, -1));
	
	
	// public static Resource resource = new Resource("Name", x-sprite position + y-sprite position * 32, Color.get(-1,555,555,555))
	
	public String name; // the name of the resource
	public final int sprite; // the sprite of the resource
	public final int color; // the color of the resource

	public Resource(String name, int sprite, int color) {
		/* if the name is longer than six characters, then throw an error. */
		if (name.length() > 16)
			throw new RuntimeException("Name cannot be longer than sixteen characters!");
		this.name = name; // assigns the name
		this.sprite = sprite; // assigns the sprite
		this.color = color; // assigns the color
	}

	/** Determines what happens when the resource is used on a certain tile */
	public boolean interactOn(Tile tile, Level level, int xt, int yt, Player player, int attackDir) {
		return false;
	}
	
}
