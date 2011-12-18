package nhf;

/**
 * Az Elem oszt�ly, tartalmazza a poz�ci�t �s a "k�p�t" egy elemnek.
 * Lek�rhet� a "k�p".
 * @author Sabzi
 *
 */
public class Elem {
	private char elem;
	Position poz;
	
	/**
	 * Konstruktor alap �rt�kkel.
	 * @param mi: milyen elem is ez
	 */
	public Elem(String mi){
		if(mi.equals("fal")) elem = '#';
			else if(mi.equals("player")) elem = '@';
				else if(mi.equals("AI")) elem = '%';
					else if(mi.equals("kincs")) elem = '$';
						else if(mi.equals("ajto")) elem = '=';
							else if(mi.equals("pusztithato")) elem = '+';
								else if(mi.equals("bomba"))elem = '�';
									else elem = ' ';
	}
	
	
	/**
	 * Visszat�r a "kin�zettel".
	 * @return A "kin�zet".
	 */
	public String getElem(){
		char temp[] = {elem};        
		return new String(temp);  
	}
}
