
package mysticcraft.item.special;

import mysticcraft.entity.item.Inventory;
import mysticcraft.entity.mob.Player;
import mysticcraft.gfx.Color;
import mysticcraft.gfx.Font;
import mysticcraft.gfx.Screen;
import mysticcraft.item.Item;
import mysticcraft.level.Level;
import mysticcraft.level.tile.Tile;
import mysticcraft.screen.ContainerMenu;

public class BackpackItem extends Item {

	private int staminaCost;
	public Inventory inventory;

	public BackpackItem() {
		super("item.backpack", "Backpack");
	}

	@Override
	public int getColor() {
		return Color.get(-1, 322, 553, 330, -1);
	}

	@Override
	public int getSprite() {
		return 1 + 10 * 32;
	}

	@Override
	public void renderIcon(Screen screen, int x, int y) {
		screen.render(x, y, getSprite(), getColor(), 0);
	}

	@Override
	public void renderInventory(Screen screen, int x, int y) {
		screen.render(x, y, getSprite(), getColor(), 0);
		Font.draw(getName(), screen, x + 8, y, Color.get(-1, 555, 555, 555, -1));
	}

	@Override
	public String getName() {
		return super.getName();
	}

	@Override
	public boolean canAttack() {
		return false;
	}

	@Override
	public boolean interactOn(Tile tile, Level level, int xt, int yt, Player player, int attackDir) {
		if (player.payStamina(staminaCost)) {
			inventory = new Inventory(player);
			player.game.setMenu(new ContainerMenu(player, getName(), inventory));
			player.payStamina(3);
			return true;
		}
		return false;
	}

	public Item clone() {
		return new BackpackItem();
	}

}
