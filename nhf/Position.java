package nhf;

/**
 * Pposition osztály, mely x illetve y koordinátákat tartalmaz.
 * Ezen osztály lépés függvényeit meghívva lehet a koordinátákon változtatni.
 * A koordinátákat a {@link #pozToIndex()} fügvényével lehet lekérni, index formátumban.
 * @author Sabzi
 *
 */
public class Position{
	private int x, y;
	
	/**
	 * Alap konstruktor.
	 */	
	Position(){
		x=0;
		y=0;
	}
	
	/**
	 * Konstruktor, mely másik pozíció alapján csinál pozíciót.
	 * @param p Másik pozíció.
	 */
	Position(Position p){
		x = p.x;
		y = p.y;
	}
	
	/**
	 * Konstruktor alapértékekkel.
	 * @param kezdõ x
	 * @param kezdõ y
	 */	
	Position(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Balra lépteti az adott pozíciót.
	 * @throws OutOfMapException
	 */
	@SuppressWarnings("static-access")
	void balra(Map map) throws InvalidMove{
		if(x == 1) 
			throw new InvalidMove();
		x--;
		if(map.elemek.get(pozToIndex()).getElem().equals("#")|map.elemek.get(pozToIndex()).getElem().equals("+")){
			x++;
			throw new InvalidMove();
		}
	}
	
	/**
	 * Jobbra lépteti az adott pozíciót.
	 * @throws OutOfMapException
	 */
	@SuppressWarnings("static-access")
	void jobbra(Map map) throws InvalidMove{
		if(x == 37) 							
			throw new InvalidMove();
		x++;
		if(map.elemek.get(pozToIndex()).getElem().equals("#")|map.elemek.get(pozToIndex()).getElem().equals("+")){
			x--;
			throw new InvalidMove();
		}
	}
	
	/**
	 * Fel lépteti az adott pozíciót.
	 * @throws OutOfMapException
	 */
	@SuppressWarnings("static-access")	
	void fel(Map map) throws InvalidMove{
		if(y == 1) 
			throw new InvalidMove();
		y--;
		if(map.elemek.get(pozToIndex()).getElem().equals("#")|map.elemek.get(pozToIndex()).getElem().equals("+")){
			y++;
			throw new InvalidMove();
		}	
	}
	
	/**
	 * Le lépteti az adott pozíciót.
	 * @throws OutOfMapException
	 */	
	@SuppressWarnings("static-access")
	void le(Map map) throws InvalidMove{
		if(y == 9) 					
			throw new InvalidMove();
		y++;
		if(map.elemek.get(pozToIndex()).getElem().equals("#")|map.elemek.get(pozToIndex()).getElem().equals("+")){
			y--;
			throw new InvalidMove();
		}
	}
	
	/**
	 * Indexet ad vissza koorfinátákból, egy indexelésõ tárolóhoz.
	 * @return index
	 */	
	int pozToIndex(){
		return y*39+x;				
	}
}
