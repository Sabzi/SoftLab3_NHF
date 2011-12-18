package nhf;

/**
 * Az Elem osztály, tartalmazza a pozíciót és a "képét" egy elemnek.
 * Lekérhetõ a "kép".
 * @author Sabzi
 *
 */
public class Elem {
	private char elem;
	Position poz;
	
	/**
	 * Konstruktor alap értékkel.
	 * @param mi: milyen elem is ez
	 */
	public Elem(String mi){
		if(mi.equals("fal")) elem = '#';
			else if(mi.equals("player")) elem = '@';
				else if(mi.equals("AI")) elem = '%';
					else if(mi.equals("kincs")) elem = '$';
						else if(mi.equals("ajto")) elem = '=';
							else if(mi.equals("pusztithato")) elem = '+';
								else if(mi.equals("bomba"))elem = 'ß';
									else elem = ' ';
	}
	
	
	/**
	 * Visszatér a "kinézettel".
	 * @return A "kinézet".
	 */
	public String getElem(){
		char temp[] = {elem};        
		return new String(temp);  
	}
}
