package nhf;

import java.util.Random;

/**
 * AI osztály. Az Elem leszármazottja. "Képe": '@'
 * @author Sabzi
 *
 */
public class AI extends Elem{
	
	/**
	 * Konstruktor pozícióval.
	 * @param x Kezdõ x érték.
	 * @param y Kezdõ y érték.
	 */
	public AI(){
		super("AI");
	}
	
	/**
	 * Meghívja az adott ai lép fügvényét, ami egy random lehetséges helyre lép, vagy marad helyben. 
	 * @param map A map, amin dolgozunk, léphet-e ellenõrzés miatt.
	 */
	public void lep(Map map){
		Random rnd = new Random();
		try{
			switch(rnd.nextInt(4)){
				case 0:{
					poz.balra(map);
					break;
				}
				case 1:{
					poz.fel(map);
					break;
				}
				case 2:{
					poz.le(map);
					break;
				}
				case 3:{
					poz.jobbra(map);
					break;
				}
			}
		} catch(InvalidMove e){};
	}
}
