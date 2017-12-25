package mysticcraft.entity.projectile;

import java.util.List;

import mysticcraft.entity.Entity;
import mysticcraft.entity.mob.Cronus;
import mysticcraft.entity.mob.Mob;
import mysticcraft.entity.mob.Player;
import mysticcraft.gfx.Color;
import mysticcraft.gfx.Screen;
import mysticcraft.sound.Sound;

public class PlayerSpark extends Entity {

	private int lifeTime; // how much time until the spark disappears
	public double xa, ya; // the x and y acceleration
	public double xx, yy; // the x and y positions
	private int time; // the amount of time that has occurred
	private Player owner;
	
	public PlayerSpark(Player owner, double xa, double ya) {
		this.owner = owner;
		xx = this.x = owner.x;
		yy = this.y = owner.y;
		xr = 0;
		yr = 0;
		
		this.xa = xa;
		this.ya = ya;
		
		lifeTime = 60 * 10 + random.nextInt(30);
	}
	
	@Override
	public void tick() {
		time++; // increases time by 1
		if (time >= lifeTime) { // if time is larger or equal to lifeTime then...
			remove(); // remove this from the world
			return; // skip the rest of the code
		}
		xx += xa; // move the xx position in the x acceleration direction
		yy += ya; // move the yy position in the x acceleration direction
		x = (int) xx; // the x position equals the integer converted xx position.
		y = (int) yy; // the y position equals the integer converted yy position.
		List<Entity> toHit = level.getEntities(x, y, x, y); // gets the entities in the current position to hit.
		for (int i = 0; i < toHit.size(); i++) { // cycles through the list
			Entity e = toHit.get(i); // gets the current entity
			if (e instanceof Mob && (!(e instanceof Player || e instanceof Cronus))) { // if the entity is a mob, but not a Player then...
				Sound.monsterHurt.play();
				e.hurt(owner, 3, ((Mob) e).dir ^ 1); // hurt the mob with 1 damage;
			}
		}
		//owner.heal(2);
	}
	
	@Override
	public boolean isBlockableBy(Mob mob) {
		return false;
	}
	
	@Override
	public void render(Screen screen) {
		/* this first part is for the blinking effect */
		if (time >= lifeTime - 6 * 20) {// if time is larger or equal to lifeTime - 6 * 20 then...
			if (time / 6 % 2 == 0) return; // if the remainder of (time/6)/2 = 0 then skip the rest of the code.
		}

		int xt = 8; // the x coordinate on the sprite-sheet
		int yt = 13; // the y coordinate on the sprite-sheet

		screen.render(x - 4, y - 4 - 2, xt + yt * 32, Color.get(-1, 555, 555, 555, -1), random.nextInt(4)); // renders the spark
		screen.render(x - 4, y - 4 + 2, xt + yt * 32, Color.get(-1, 000, 000, 000, -1), random.nextInt(4)); // renders the shadow on the ground
	}
}
