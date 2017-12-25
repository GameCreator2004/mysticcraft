package mysticcraft.crafting;

import java.util.ArrayList;
import java.util.List;

import mysticcraft.entity.furniture.Anvil;
import mysticcraft.entity.furniture.Chest;
import mysticcraft.entity.furniture.Furnace;
import mysticcraft.entity.furniture.Jukebox;
import mysticcraft.entity.furniture.Lantern;
import mysticcraft.entity.furniture.Oven;
import mysticcraft.entity.furniture.Torch;
import mysticcraft.entity.furniture.Workbench;
import mysticcraft.item.GloveItem;
import mysticcraft.item.resource.Resource;
import mysticcraft.item.special.SkyTotemItem;
import mysticcraft.item.type.BucketState;
import mysticcraft.item.type.GloveType;
import mysticcraft.item.type.MusicType;
import mysticcraft.item.type.ToolType;
import mysticcraft.recipe.Recipe;
import mysticcraft.recipe.furniture.FurnitureRecipe;
import mysticcraft.recipe.item.BucketRecipe;
import mysticcraft.recipe.item.GloveRecipe;
import mysticcraft.recipe.item.ItemRecipe;
import mysticcraft.recipe.item.MusicDiscRecipe;
import mysticcraft.recipe.item.ResourceRecipe;
import mysticcraft.recipe.item.ToolRecipe;

public class Crafting {
	public static final List<Recipe> AR = new ArrayList<Recipe>();
	public static final List<Recipe> OR = new ArrayList<Recipe>();
	public static final List<Recipe> FR = new ArrayList<Recipe>();
	public static final List<Recipe> WBR = new ArrayList<Recipe>();
	public static final List<Recipe> ECR = new ArrayList<Recipe>();
	// public static final List<Recipe> EXR = new ArrayList<Recipe>();

	// private static HealGloveItem heal_glove;
	// private static AirTotemItem air_totem;

	static {
		try {
			/*
			 * WBR.add() adds a new recipe that has to be crafted in the workbench AR.add(),
			 * FR.add(), OR.add() does the same, but has to be crafted in anvil/furnace/oven
			 * (new FurnitureRecipe(Lantern.class)) makes a new furniture recipe for the
			 * lantern class addCost(Resource.wood, 5) adds a material to the recipe. The
			 * name after "Resource." is what material and the number is how many needed
			 */

			// Workbench Crafting Part
			WBR.add(new ResourceRecipe(Resource.stick, 2).addCost(Resource.wood, 1));
			WBR.add(new ResourceRecipe(Resource.appleStone, 1).addCost(Resource.stone, 1).addCost(Resource.apple, 1));
			WBR.add(new ResourceRecipe(Resource.goldNugget, 9).addCost(Resource.goldIngot, 1));
			WBR.add(new ResourceRecipe(Resource.ironNugget, 9).addCost(Resource.ironIngot, 1));
			WBR.add(new MusicDiscRecipe(MusicType.SPECIAL).addCost(Resource.coal, 1).addCost(Resource.goldNugget, 1));
			// furnitures recipe
			WBR.add(new FurnitureRecipe(Lantern.class).addCost(Resource.wood, 5).addCost(Resource.slime, 10).addCost(Resource.glass, 4));
			WBR.add(new FurnitureRecipe(Torch.class).addCost(Resource.wood, 6).addCost(Resource.coal, 10));
			WBR.add(new FurnitureRecipe(Oven.class).addCost(Resource.stone, 15));
			WBR.add(new FurnitureRecipe(Furnace.class).addCost(Resource.stone, 20));
			WBR.add(new FurnitureRecipe(Workbench.class).addCost(Resource.wood, 20));
			WBR.add(new FurnitureRecipe(Chest.class).addCost(Resource.wood, 10));
			WBR.add(new FurnitureRecipe(Anvil.class).addCost(Resource.ironIngot, 5));
			WBR.add(new FurnitureRecipe(Jukebox.class).addCost(Resource.wood, 10).addCost(Resource.gem, 1));

			WBR.add(new BucketRecipe(BucketState.EMPTY).addCost(Resource.ironIngot, 6));

			// dyes
			WBR.add(new ResourceRecipe(Resource.whiteDye, 3).addCost(Resource.bone, 1));
			WBR.add(new ResourceRecipe(Resource.yellowDye, 2).addCost(Resource.yellowFlower, 1));
			WBR.add(new ResourceRecipe(Resource.orangeDye, 2).addCost(Resource.appleRed, 1).addCost(Resource.yellowDye, 1));
			WBR.add(new ResourceRecipe(Resource.pinkDye, 2).addCost(Resource.appleRed, 1).addCost(Resource.whiteDye, 1));
			WBR.add(new ResourceRecipe(Resource.greyDye, 2).addCost(Resource.flower, 1));
			WBR.add(new ResourceRecipe(Resource.limeDye, 2).addCost(Resource.cactusGreen, 1).addCost(Resource.whiteDye, 1));

			// wood tools
			WBR.add(new ToolRecipe(ToolType.SWORD, 0).addCost(Resource.wood, 2).addCost(Resource.stick, 1));
			WBR.add(new ToolRecipe(ToolType.AXE, 0).addCost(Resource.wood, 3).addCost(Resource.stick, 2));
			WBR.add(new ToolRecipe(ToolType.HOE, 0).addCost(Resource.wood, 2).addCost(Resource.stick, 2));
			WBR.add(new ToolRecipe(ToolType.PICKAXE, 0).addCost(Resource.wood, 3).addCost(Resource.stick, 2));
			WBR.add(new ToolRecipe(ToolType.SHOVEL, 0).addCost(Resource.wood, 1).addCost(Resource.stick, 2));

			// stone tools
			WBR.add(new ToolRecipe(ToolType.SWORD, 1).addCost(Resource.stone, 2).addCost(Resource.stick, 1));
			WBR.add(new ToolRecipe(ToolType.AXE, 1).addCost(Resource.stone, 3).addCost(Resource.stick, 2));
			WBR.add(new ToolRecipe(ToolType.HOE, 1).addCost(Resource.stone, 2).addCost(Resource.stick, 2));
			WBR.add(new ToolRecipe(ToolType.PICKAXE, 1).addCost(Resource.stone, 3).addCost(Resource.stick, 2));
			WBR.add(new ToolRecipe(ToolType.SHOVEL, 1).addCost(Resource.stone, 1).addCost(Resource.stick, 2));

			// apple stone tools
			WBR.add(new ToolRecipe(ToolType.SWORD, 2).addCost(Resource.appleStone, 2).addCost(Resource.stick, 1));
			WBR.add(new ToolRecipe(ToolType.AXE, 2).addCost(Resource.appleStone, 3).addCost(Resource.stick, 2));
			WBR.add(new ToolRecipe(ToolType.HOE, 2).addCost(Resource.appleStone, 2).addCost(Resource.stick, 2));
			WBR.add(new ToolRecipe(ToolType.PICKAXE, 2).addCost(Resource.appleStone, 3).addCost(Resource.stick, 2));
			WBR.add(new ToolRecipe(ToolType.SHOVEL, 2).addCost(Resource.appleStone, 1).addCost(Resource.stick, 2));

			WBR.add(new ToolRecipe(ToolType.PICKAXE, 5).addCost(Resource.stick, 1));
			WBR.add(new ToolRecipe(ToolType.SPEAR, 5).addCost(Resource.stick, 1));

			// Anvil Crafting Part

			// iron tools
			AR.add(new ToolRecipe(ToolType.SWORD, 3).addCost(Resource.ironIngot, 2).addCost(Resource.stick, 1));
			AR.add(new ToolRecipe(ToolType.AXE, 3).addCost(Resource.ironIngot, 3).addCost(Resource.stick, 2));
			AR.add(new ToolRecipe(ToolType.HOE, 3).addCost(Resource.ironIngot, 2).addCost(Resource.stick, 2));
			AR.add(new ToolRecipe(ToolType.PICKAXE, 3).addCost(Resource.ironIngot, 3).addCost(Resource.stick, 2));
			AR.add(new ToolRecipe(ToolType.SHOVEL, 3).addCost(Resource.ironIngot, 1).addCost(Resource.stick, 2));

			// gold tools
			AR.add(new ToolRecipe(ToolType.SWORD, 4).addCost(Resource.goldIngot, 2).addCost(Resource.stick, 1));
			AR.add(new ToolRecipe(ToolType.AXE, 4).addCost(Resource.goldIngot, 3).addCost(Resource.stick, 2));
			AR.add(new ToolRecipe(ToolType.HOE, 4).addCost(Resource.goldIngot, 2).addCost(Resource.stick, 2));
			AR.add(new ToolRecipe(ToolType.PICKAXE, 4).addCost(Resource.goldIngot, 3).addCost(Resource.stick, 2));
			AR.add(new ToolRecipe(ToolType.SHOVEL, 4).addCost(Resource.goldIngot, 1).addCost(Resource.stick, 2));

			// gem tools
			AR.add(new ToolRecipe(ToolType.SWORD, 5).addCost(Resource.gem, 2).addCost(Resource.stick, 1));
			AR.add(new ToolRecipe(ToolType.AXE, 5).addCost(Resource.gem, 3).addCost(Resource.stick, 2));
			AR.add(new ToolRecipe(ToolType.HOE, 5).addCost(Resource.gem, 2).addCost(Resource.stick, 2));
			AR.add(new ToolRecipe(ToolType.PICKAXE, 5).addCost(Resource.gem, 3).addCost(Resource.stick, 2));
			AR.add(new ToolRecipe(ToolType.SHOVEL, 5).addCost(Resource.gem, 1).addCost(Resource.stick, 2));

			// Enchanter Crafting Part
			ECR.add(new ItemRecipe(new SkyTotemItem()).addCost(Resource.airStar, 1).addCost(Resource.gem, 3).addCost(Resource.goldIngot, 2));
			ECR.add(new GloveRecipe(GloveType.HEAL).addCost(Resource.appleRed, 8).addCost(Resource.goldenApple, 1).addCost(new GloveItem(GloveType.POWER)));
			ECR.add(new GloveRecipe(GloveType.HEAL).addCost(Resource.cloth, 35));

			// SPEARs recipe
			ECR.add(new ToolRecipe(ToolType.SPEAR, 0).addCost(Resource.wood, 5).addCost(Resource.stick, 2));
			ECR.add(new ToolRecipe(ToolType.SPEAR, 1).addCost(Resource.stone, 5).addCost(Resource.stick, 2));
			ECR.add(new ToolRecipe(ToolType.SPEAR, 2).addCost(Resource.appleStone, 5).addCost(Resource.stick, 2));
			ECR.add(new ToolRecipe(ToolType.SPEAR, 3).addCost(Resource.ironIngot, 5).addCost(Resource.stick, 2));
			ECR.add(new ToolRecipe(ToolType.SPEAR, 4).addCost(Resource.goldIngot, 5).addCost(Resource.stick, 2));
			ECR.add(new ToolRecipe(ToolType.SPEAR, 5).addCost(Resource.gem, 5).addCost(Resource.stick, 2));
			ECR.add(new ToolRecipe(ToolType.SPEAR, 6).addCost(Resource.hardStone, 5).addCost(Resource.stick, 2));

			// hard rock tools
			ECR.add(new ToolRecipe(ToolType.SWORD, 6).addCost(Resource.hardStone, 2).addCost(Resource.stick, 1));
			ECR.add(new ToolRecipe(ToolType.AXE, 6).addCost(Resource.hardStone, 3).addCost(Resource.stick, 2));
			ECR.add(new ToolRecipe(ToolType.HOE, 6).addCost(Resource.hardStone, 2).addCost(Resource.stick, 2));
			ECR.add(new ToolRecipe(ToolType.PICKAXE, 6).addCost(Resource.hardStone, 3).addCost(Resource.stick, 2));
			ECR.add(new ToolRecipe(ToolType.SHOVEL, 6).addCost(Resource.hardStone, 1).addCost(Resource.stick, 2));
			// ER.add(new HealGloveRecipe(heal_glove).addCost(Resource.stick, 1));

			// Furnace Smelting Part
			FR.add(new ResourceRecipe(Resource.ironIngot, 1).addCost(Resource.ironOre, 4).addCost(Resource.coal, 1));
			FR.add(new ResourceRecipe(Resource.goldIngot, 1).addCost(Resource.goldOre, 4).addCost(Resource.coal, 1));
			FR.add(new ResourceRecipe(Resource.glass, 1).addCost(Resource.sand, 4).addCost(Resource.coal, 1));
			FR.add(new ResourceRecipe(Resource.cactusGreen, 1).addCost(Resource.cactusFlower, 1).addCost(Resource.coal, 1));
			FR.add(new ResourceRecipe(Resource.appleRed, 1).addCost(Resource.apple, 1).addCost(Resource.coal, 1));
			FR.add(new ResourceRecipe(Resource.charcoal, 1).addCost(Resource.wood, 1).addCost(Resource.wood, 1));
			FR.add(new ResourceRecipe(Resource.charcoal, 1).addCost(Resource.wood, 1).addCost(Resource.coal, 1));

			// Oven Smelting Part
			OR.add(new ResourceRecipe(Resource.bread, 1).addCost(Resource.wheat, 4).addCost(Resource.wood, 3));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
