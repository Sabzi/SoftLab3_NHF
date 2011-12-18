package nhf;

/**
 * Pposition oszt�ly, mely x illetve y koordin�t�kat tartalmaz.
 * Ezen oszt�ly l�p�s f�ggv�nyeit megh�vva lehet a koordin�t�kon v�ltoztatni.
 * A koordin�t�kat a {@link #pozToIndex()} f�gv�ny�vel lehet lek�rni, index form�tumban.
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
	 * Konstruktor, mely m�sik poz�ci� alapj�n csin�l poz�ci�t.
	 * @param p M�sik poz�ci�.
	 */
	Position(Position p){
		x = p.x;
		y = p.y;
	}
	
	/**
	 * Konstruktor alap�rt�kekkel.
	 * @param kezd� x
	 * @param kezd� y
	 */	
	Position(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Balra l�pteti az adott poz�ci�t.
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
	 * Jobbra l�pteti az adott poz�ci�t.
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
	 * Fel l�pteti az adott poz�ci�t.
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
	 * Le l�pteti az adott poz�ci�t.
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
	 * Indexet ad vissza koorfin�t�kb�l, egy indexel�s� t�rol�hoz.
	 * @return index
	 */	
	int pozToIndex(){
		return y*39+x;				
	}
}
