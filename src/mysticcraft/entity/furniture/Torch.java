package mysticcraft.entity.furniture;

import mysticcraft.gfx.Color;

public class Torch extends Furniture {

	Lantern lantern;
	
	public Torch() {
		super("Torch");
		col  = Color.get(-1, 500, 520, 320, -1);
		sprite = 7;
		xr = 3;
		yr = 2;
	}
	
	@Override
	public int getLightRadius() {
		return 4;
	}

}
