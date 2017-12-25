package mysticcraft.item.type;

public enum BucketState {
	
	EMPTY("Empty", 333),
	WATER_FILLED("Water", 005),
	LAVA_FILLED("Lava",  400),
	SAND_FILLED("Sand", 550);
	
	public final String nameBasedOnState;
	public final int color;
	private BucketState(String nameBasedOnState, int color) {
		this.nameBasedOnState = nameBasedOnState;
		this.color = color;
	}

}
