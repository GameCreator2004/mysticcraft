package mysticcraft.recipe.item;

import mysticcraft.entity.mob.Player;
import mysticcraft.item.special.MusicDiscItem;
import mysticcraft.item.type.MusicType;
import mysticcraft.recipe.Recipe;
import tk.jidgu.util.Sys;

public class MusicDiscRecipe extends Recipe{
	
	private MusicType musicType;

	public MusicDiscRecipe(MusicType musicType) {
		super(new MusicDiscItem(musicType));
		this.musicType = musicType;
	}

	@Override
	public void craft(Player player) {
		player.inventory.add(new MusicDiscItem(musicType));
		
		Sys.println("Crafted " + new MusicDiscItem(musicType).getName(), Sys.out);
	}

}
