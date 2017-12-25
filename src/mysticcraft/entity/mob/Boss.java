package mysticcraft.entity.mob;

import mysticcraft.entity.Entity;
import mysticcraft.entity.interfaces.IBoss;
import mysticcraft.gfx.Screen;
import mysticcraft.level.Level;
import mysticcraft.level.tile.Tile;

public class Boss extends Mob implements IBoss{
	
	public Boss() {
		super();
	}
	
	@Override
	public void tick() { super.tick(); }
	
	@Override
	public void render(Screen screen) { super.render(screen); }
	
	@Override
	protected void die() { super.die(); }
	
	@Override
	public boolean move(int xa, int ya) { return super.move(xa, ya); }

	@Override
	protected boolean isSwimming() { return super.isSwimming(); }
	
	@Override
	public boolean blocks(Entity e) { return super.blocks(e); }
	
	@Override
	public void hurt(Mob mob, int damage, int attackDir) { super.hurt(mob, damage, attackDir); }
	
	@Override
	public void hurt(Tile tile, int x, int y, int damage) { super.hurt(tile, x, y, damage); }
	
	@Override
	protected void doHurt(int damage, int attackDir) { super.doHurt(damage, attackDir); }
	
	@Override
	public void heal(int heal) { super.heal(heal); }
	
	@Override
	public boolean findStartPos(Level level) { return super.findStartPos(level); }
		
	@Override
	public String getMobName() { return super.getMobName(); }
	
	@Override
	public void setMobName(String mobName) { super.setMobName(mobName); }

	@Override
	public boolean isABoss() { return true; }

	@Override
	public boolean isKillable() { return true; }
	
	
}
