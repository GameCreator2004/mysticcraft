package mysticcraft.entity.furniture;

import mysticcraft.entity.furniture.enums.JukeboxData;
import mysticcraft.entity.mob.Player;
import mysticcraft.gfx.Color;
import mysticcraft.item.special.MusicDiscItem;
import mysticcraft.item.type.MusicType;

public class Jukebox extends Furniture {

	private JukeboxData jukeboxData;

	public Jukebox() {
		super("Jukebox");
		col = Color.get(-1, 100, 321, 210, -1);
		sprite = 8;
		xr = 3;
		yr = 2;
	}

	@Override
	public void take(Player player) {
		if (getJukeboxData() == JukeboxData.NORMAL_IN_JUKEBOX) {
			//player.inventory.add(new MusicDiscItem(MusicType.NORMAL));
			level.dropItem(x, y, new MusicDiscItem(MusicType.NORMAL));
		}
		if (getJukeboxData() == JukeboxData.SPECIAL_IN_JUKEBOX) {
			//player.inventory.add(new MusicDiscItem(MusicType.SPECIAL));
			level.dropItem(x, y, new MusicDiscItem(MusicType.SPECIAL));
		}
		
		super.take(player);
	}

	public JukeboxData getJukeboxData() {
		return jukeboxData;
	}

	public void setJukeboxData(JukeboxData jukeboxData) {
		this.jukeboxData = jukeboxData;
	}

}
