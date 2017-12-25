package mysticcraft.item;

import mysticcraft.entity.mob.Player;
import mysticcraft.gfx.Color;
import mysticcraft.gfx.Font;
import mysticcraft.gfx.Screen;
import mysticcraft.item.type.BucketState;
import mysticcraft.level.Level;
import mysticcraft.level.tile.Tile;

public class BucketItem extends Item {

	private BucketState bucketState;

	public BucketItem(BucketState bucketState) {
		super("item." + bucketState.nameBasedOnState + "_" + "bucket", bucketState.nameBasedOnState + " " + "Bucket");
		this.bucketState = bucketState;
		this.setBucketState(bucketState);
	}

	public int getColor() {
		return Color.get(-1, 222, bucketState.color, 555, -1);
	}

	public int getSprite() {
		return 7 + 5 * 32;
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

	public boolean interactOn(Tile tile, Level level, int xt, int yt, Player player, int attackDir) {
		if (getBucketState() == BucketState.EMPTY) {
			if (tile == Tile.water) {
				level.setTile(xt, yt, Tile.hole, 0);
				Item item = (new BucketItem(BucketState.WATER_FILLED));
				player.activeItem = item;
			}
			if (tile == Tile.lava) {
				level.setTile(xt, yt, Tile.hole, 0);
				Item item = (new BucketItem(BucketState.LAVA_FILLED));
				player.activeItem = item;
			}
			if (tile == Tile.sand) {
				level.setTile(xt, yt, Tile.dirt, 0);
				Item item = (new BucketItem(BucketState.SAND_FILLED));
				player.activeItem = item;
			}
			return true;
		}

		if (getBucketState() == BucketState.LAVA_FILLED) {
			if (tile == Tile.hole) {
				level.setTile(xt, yt, Tile.lava, 0);
				Item item = (new BucketItem(BucketState.EMPTY));
				player.activeItem = item;
			}
			return true;
		}

		if (getBucketState() == BucketState.WATER_FILLED) {
			if (tile == Tile.hole) {
				level.setTile(xt, yt, Tile.water, 0);
				Item item = (new BucketItem(BucketState.EMPTY));
				player.activeItem = item;
			}
			return true;
		}
		if (getBucketState() == BucketState.SAND_FILLED) {
			if (tile == Tile.dirt) {
				level.setTile(xt, yt, Tile.sand, 0);
				Item item = (new BucketItem(BucketState.EMPTY));
				player.activeItem = item;
			}

		}
		return true;
	}

	public BucketState getBucketState() {
		return bucketState;
	}

	public void setBucketState(BucketState BucketState) {
		this.bucketState = BucketState;
	}

	@Override
	public Item clone() {
		return new BucketItem(bucketState);
	}

}
