package nhf;

import java.io.Serializable;

/**
 * HighScoreTableUnit osztály. 
 * Ilyen elemekbõl áll a HighScoreTable osztály
 *
 */


public class HighScoreTableUnit implements Serializable  {

	private static final long serialVersionUID = 5731786868798872932L;
	private String name;
	private int score;
	
	/**
	 * Konstruktor névvel.
	 * @param s Név.
	 */
		
	public HighScoreTableUnit(String s){
		name=s;
		score=0;
	}
	/**
	 * Konstruktor névvel és pontszámmal.
	 * @param s Név
	 * @param i Score
	 */
	public HighScoreTableUnit(String s,int i){
		name=s;
		score=i;
	}
	/**
	 * Visszaadja az adott unit nevét
	 */	
	public String getName(){
		return name;		
	}
	/**
	 * Visszaadja az adott unit pontszámát
	 */		
	public int getScore(){
		return score;
	}
	/**
	 * Beállítja az adott unit nevét
	 */	
	public void setScore(int i){
		score=i;
	}
	/**
	 * Beállítja az adott unit nevét
	 */	
	public void getScore(int i){
		score=i;
	}
}
