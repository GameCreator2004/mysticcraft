package mysticcraft.item.special;

import mysticcraft.entity.Entity;
import mysticcraft.entity.item.ItemEntity;
import mysticcraft.gfx.Color;
import mysticcraft.gfx.Font;
import mysticcraft.gfx.Screen;
import mysticcraft.item.Item;

public class SkyTotemItem extends Item {

	public SkyTotemItem() {
		super("item.sky_totem", "Sky Totem");
	}

	public int getColor() {
		return Color.get(-1, 550, 55, 555, -1);
	}

	public int getSprite() {

		return 6 + 5 * 32;
	}

	public void renderIcon(Screen screen, int x, int y) {
		screen.render(x, y, getSprite(), getColor(), 0);
	}

	public void renderInventory(Screen screen, int x, int y) {
		screen.render(x, y, getSprite(), getColor(), 0);
		Font.draw(getName(), screen, x + 8, y, Color.get(-1, 055, 055, 055, -1));
	}

	public String getName() {
		return super.getName();
	}

	@Override
	public void onTake(ItemEntity itemEntity) {
	}

	public int getAttackDamageBonus(Entity e) {
		return 0;
	}
}
