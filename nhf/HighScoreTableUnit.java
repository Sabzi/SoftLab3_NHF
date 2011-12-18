package nhf;

import java.io.Serializable;

/**
 * HighScoreTableUnit oszt�ly. 
 * Ilyen elemekb�l �ll a HighScoreTable oszt�ly
 *
 */


public class HighScoreTableUnit implements Serializable  {

	private static final long serialVersionUID = 5731786868798872932L;
	private String name;
	private int score;
	
	/**
	 * Konstruktor n�vvel.
	 * @param s N�v.
	 */
		
	public HighScoreTableUnit(String s){
		name=s;
		score=0;
	}
	/**
	 * Konstruktor n�vvel �s pontsz�mmal.
	 * @param s N�v
	 * @param i Score
	 */
	public HighScoreTableUnit(String s,int i){
		name=s;
		score=i;
	}
	/**
	 * Visszaadja az adott unit nev�t
	 */	
	public String getName(){
		return name;		
	}
	/**
	 * Visszaadja az adott unit pontsz�m�t
	 */		
	public int getScore(){
		return score;
	}
	/**
	 * Be�ll�tja az adott unit nev�t
	 */	
	public void setScore(int i){
		score=i;
	}
	/**
	 * Be�ll�tja az adott unit nev�t
	 */	
	public void getScore(int i){
		score=i;
	}
}
