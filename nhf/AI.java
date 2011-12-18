package nhf;

import java.util.Random;

/**
 * AI oszt�ly. Az Elem lesz�rmazottja. "K�pe": '@'
 * @author Sabzi
 *
 */
public class AI extends Elem{
	
	/**
	 * Konstruktor poz�ci�val.
	 * @param x Kezd� x �rt�k.
	 * @param y Kezd� y �rt�k.
	 */
	public AI(){
		super("AI");
	}
	
	/**
	 * Megh�vja az adott ai l�p f�gv�ny�t, ami egy random lehets�ges helyre l�p, vagy marad helyben. 
	 * @param map A map, amin dolgozunk, l�phet-e ellen�rz�s miatt.
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
