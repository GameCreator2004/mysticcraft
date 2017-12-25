package mysticcraft.level.tile;

import mysticcraft.entity.Entity;
import mysticcraft.gfx.Screen;
import mysticcraft.level.Level;

public class InfiniteFallTile extends Tile {

	/* This will be easy :D */

	public InfiniteFallTile(int id) {
		super(id); // assigns the id
	}

	/** Infinite fall tile doesn't render anything! */
	public void render(Screen screen, Level level, int x, int y) {
	}

	/** Update method, updates (ticks) 60 times a second */
	public void tick(Level level, int xt, int yt) {
	}

	/** Determines if an entity can pass through this tile */
	public boolean mayPass(Level level, int x, int y, Entity e) {
		//if (e instanceof SkyWizard)
		//	return true; // If the entity is an Air Wizard, than it can pass
							// through
		/*if(e instanceof Player && ((Player) e).activeItem instanceof SkyTotemItem) {
			return true;
		}*/
		
		if(e.canWalkOnAir() == true) { 
			return true;
		}
		return false; // else the entity can't pass through
	}
}
