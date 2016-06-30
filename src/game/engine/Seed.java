package game.engine;

public enum Seed {
	EMPTY, CROSS, NOUGHT ;
	
	
	public static Seed nextPlayer(Seed seed)
	{
		if(seed == Seed.CROSS)
		{
			return Seed.NOUGHT ;
		}
		else {
			return Seed.CROSS ;
		}
	}
}
