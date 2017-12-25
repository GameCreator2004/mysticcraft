package mysticcraft.item.special;

import mysticcraft.entity.Entity;
import mysticcraft.entity.furniture.Furniture;
import mysticcraft.entity.furniture.Jukebox;
import mysticcraft.entity.furniture.enums.JukeboxData;
import mysticcraft.entity.mob.Player;
import mysticcraft.gfx.Color;
import mysticcraft.gfx.Font;
import mysticcraft.gfx.Screen;
import mysticcraft.item.Item;
import mysticcraft.item.type.MusicType;
import mysticcraft.sound.Sound;

public class MusicDiscItem extends Item {

	private MusicType musicType;

	public MusicDiscItem(MusicType musicType) {
		super("item." + musicType.name + "_" + "disc", musicType.name + " " + "Disc");
		this.musicType = musicType;
		this.setMusicType(musicType);
	}

	public int getSprite() {
		return 21 + 3 * 32;
	}

	public int getColor() {
		return musicType.color;
	}

	public String getName() {
		return super.getName();
	}

	public void renderIcon(Screen screen, int x, int y) {
		screen.render(x, y, getSprite(), getColor(), 0);
	}

	public void renderInventory(Screen screen, int x, int y) {
		screen.render(x, y, getSprite(), getColor(), 0);
		Font.draw(getName(), screen, x + 8, y, Color.get(-1, 555, 555, 555, -1));
	}

	public boolean interact(Player player, Entity entity, int attackDir) {
		if (entity instanceof Furniture) {
			Furniture furniture = (Furniture) entity;
			if (furniture instanceof Jukebox) {
				if (getMusicType() == MusicType.NORMAL) {
					Sound.music.play();
					player.activeItem = null;
					((Jukebox) furniture).setJukeboxData(JukeboxData.NORMAL_IN_JUKEBOX);
					return true;
				}

				if (getMusicType() == MusicType.SPECIAL) {
					//Sound.zombieHurt.play();
					//Sound.zombieDeath.play();
					player.activeItem = null;
					((Jukebox) furniture).setJukeboxData(JukeboxData.SPECIAL_IN_JUKEBOX);
					return true;
				}
			}
		}
		return false;
	}

	public MusicType getMusicType() {
		return musicType;
	}

	public void setMusicType(MusicType musicType) {
		this.musicType = musicType;
	}

	public Item clone() {
		return new MusicDiscItem(musicType);
	}
}
