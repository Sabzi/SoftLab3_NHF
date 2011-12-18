package nhf;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JTextArea;

/**
 * A NAGY Map oszt�ly, amiben a l�nyeg van.<br>
 * <b>Van benne:</b>
 * <ul>
 * <li>{@link #elemek} lista.</lk>
 * <li>{@link #kincsek} lista.</li>
 * <li>{@link #aik} lista.</li>
 * <li>{@link #pusztithatok} lista.</li>
 * <li>{@link #bomba}</li>
 * <li>{@link #ajto}</li>
 * <li>{@link #lives}</li>
 * </ul>
 * <b>F�ggv�nyek:</b>
 * <ul>
 * <li>{@link #feltoltCuccal(String)}</li>
 * <li>{@link #frissit()}</li>
 * <li>{@link #Map(String)}</li>
 * <li>{@link #alap()}</li>
 * <li>{@link #kirajzol(JTextArea)}</li>
 * </ul>
 * 
 * @author Sabzi
 * 
 */
public class Map {
	/**
	 * Elem-eket t�rol, ezt rajzoljuk ki. A {@link #frissit()} f�ggv�ny ebbe a
	 * list�ba rakja bele a t�bbi list�b�l az Elemeket. Legk�s�bb ker�l bele az,
	 * amit meg is k�v�nunk jelen�teni, "legf�l�l van". �gy a
	 * {@link #kirajzol(JTextArea)} ezt fogja megjelen�teni.
	 */
	static ArrayList<Elem> elemek; // Eleme-eket tratalmaz� lista.
	static final String newline = System.getProperty("line.separator");
	Random rnd = new Random();
	int talaltKincsek = 0;

	/**
	 * Map elemek:
	 */
	static Elem player = new Elem("player");
	/**
	 * Ez AI-kat t�rol, ezek poz�ci�it, stb. R�l�pve vagy ha � l�p r�nk
	 * meghalunk.
	 */
	static ArrayList<AI> aik;
	/**
	 * Ez t�rolja a kincsek poz�ci�it. R�l�pve kincset szerz�nk.
	 */
	static ArrayList<Elem> kincsek;
	/**
	 * Ez az elpuszt�that� Elem-eket t�rolja.
	 */
	static ArrayList<Elem> pusztithatok;
	/**
	 * Az ajt�, erre r�l�pve nyer�nk.
	 */
	static Elem ajto = new Elem("ajto");
	/**
	 * Bomba, amit mi rakunk le. Horizont�lisan �s vertik�lisan terjed a
	 * "robban�s", ami elpuszt�tja az els� �tj�ba ker�l� j�t�kost, ai-t, vagy
	 * elpuszt�that� falat. Sima falban "megakad".
	 */
	static Elem bomba;
	/**
	 * Ennyi �let�nk van.
	 */
	static int lives = 4;

	/**
	 * Az alap, v�ltozatlan kin�zete a mapnak. Az elpuszt�thatatlan falak.
	 */
	private static void alap() {
		elemek = new ArrayList<Elem>(428);

		for (int i = 0; i < 39; i++) {
			elemek.add(new Elem("fal"));
		}
		for (int i = 0; i < 9; i++)
			if (i % 2 == 0) {
				elemek.add(new Elem("fal"));
				for (int j = 1; j < 38; j++)
					elemek.add(new Elem(""));
				elemek.add(new Elem("fal"));
			} else {
				for (int j = 0; j < 39; j++) {
					if (j % 2 == 0)
						elemek.add(new Elem("fal"));
					else
						elemek.add(new Elem(""));
				}
			}
		for (int i = 0; i < 39; i++) {
			elemek.add(new Elem("fal"));
		}
	}

	/**
	 * Ez a f�ggv�ny t�lti be megfelel� sorrendben a k�l�nb�z� dolgokat, �gy
	 * mint: kincsek, aik, bomba, ajt�, j�t�kos. Itt ellen�rizz�k, hogy a player
	 * AI-n, kincsen, vagy ajt�n �ll-e.
	 */
	public void frissit() {
		alap();

		elemek.set(player.poz.pozToIndex(), player);

		/**
		 * AIk felt�lt�se. V�gigmegy�nk az aik list�n �s bet�ltj�k �ket az
		 * elemek list�ba. Ha az egyiken rajta van a j�t�kos, akkor az meghalt.
		 */
		for (AI mostani : aik) {
			if (elemek.get(mostani.poz.pozToIndex()).getElem().equals("@")) {
				System.out.println("�letet vesztett�l.");
				lives--;
				if (lives == 0)
					return;
			}
			elemek.set(mostani.poz.pozToIndex(), mostani);
		}

		/**
		 * Kincsek felt�lt�se. V�gigmegy�nk a kincsek list�n, bet�ltj�k �ket az
		 * elemek list�ba. Ha az egyiken rajta van a j�t�kos, azt elt�vol�tjuk,
		 * kincset tal�lt.
		 */
		for (int i = 0; i < kincsek.size(); i++) {
			Elem current = kincsek.get(i);
			if (elemek.get(current.poz.pozToIndex()).getElem().equals("@")) {
				System.out.println("Kincs tal�lat.");
				talaltKincsek++;
				kincsek.remove(i);
			}
			elemek.set(current.poz.pozToIndex(), current);
		}

		/**
		 * Ellen�rizz�k, hogy ajt�n �llunk-e.
		 */
		if (elemek.get(ajto.poz.pozToIndex()).getElem().equals("@")) {
			lives = 5;
			return;
		}

		/**
		 * Berakjuk az elemek-be az ajt�t.
		 */
		elemek.set(ajto.poz.pozToIndex(), ajto);

		/**
		 * Elpuszt�that� falak berak�sa.
		 */
		for (Elem ez : pusztithatok)
			elemek.set(ez.poz.pozToIndex(), ez);

		/**
		 * Bomba berak�sa, ha kell(van bomba lerakva).
		 */
		if (bomba != null)
			elemek.set(bomba.poz.pozToIndex(), bomba);

		/**
		 * J�t�kos berak�sa.
		 */
		elemek.set(player.poz.pozToIndex(), player);
	}

	/**
	 * Ezen f�ggv�ny felel a neh�zs�gnek megfelel� sz�m� ai, elpuszt�that� fal
	 * �s kincs bet�tel��rt, �s poz�ci�ik felv�tel��rt. Itt m�g csak a saj�t
	 * list�ikba rakjuk be �ket a megfelel� poz�ci�kkal.
	 * 
	 * @param nehezseg
	 *            Ez szab�lyozza a berak�sok gyakoris�g�t.
	 */
	private void feltoltCuccal(String nehezseg) {
		int percent_AI;
		int percent_Kincsek;
		int percent_Pusztithatok;
		int tavolsag_AjtoX;
		int tavolsag_AjtoY;
		int x, y;
		Elem temp;
		AI tempAI;
		boolean voltAjto = false;

		/**
		 * Neh�zs�gnek megfelel� �rt�kek be�ll�t�sa.
		 */
		if (nehezseg.equals("normal")) {
			percent_AI = 2;
			percent_Kincsek = 25;
			percent_Pusztithatok = 50;
			tavolsag_AjtoX = 20;
			tavolsag_AjtoY = 7;
		} else if (nehezseg.equals("hard")) {
			percent_AI = 20;
			percent_Kincsek = 15;
			percent_Pusztithatok = 55;
			tavolsag_AjtoX = 30;
			tavolsag_AjtoY = 8;
		} else {
			percent_AI = 1;
			percent_Kincsek = 35;
			percent_Pusztithatok = 30;
			tavolsag_AjtoX = 10;
			tavolsag_AjtoY = 6;
		}

		alap(); // Kell, hogy tudjuk hova lehet rakni.
		/**
		 * Elpuszt�that� falak berak�sa, v�letlenszer�en az �res helyekre. Az
		 * algoritmus kihagyja a bal-fels� r�szt, hogy el lehessen indulni. Itt
		 * rakjuk be az ajt�t.
		 */
		for (int i = 0; i < elemek.size(); i++) {
			x = i % 39;
			y = i / 39;

			Elem mostani = elemek.get(i);
			if (mostani.getElem().equals(" "))
				if (rnd.nextInt(100) <= percent_Pusztithatok) {
					if ((x < 4) & (y < 4))
						continue;
					/**
					 * Ajt� egy v�letlen jobb-als� r�szbe t�rt�n� berak�sa.
					 */
					if ((voltAjto == false)
							& ((x > tavolsag_AjtoX) & (y > tavolsag_AjtoY))) {
						ajto.poz = new Position(x, y);
						voltAjto = true;
					}
					temp = new Elem("pusztithato");
					temp.poz = new Position(x, y);
					pusztithatok.add(temp);
				}
		}

		/**
		 * Ha m�gsem raktunk be ajt�t, akkor most berakunk.
		 */
		if (voltAjto == false) {
			ajto.poz = new Position(25, 8);
		}

		/**
		 * AI-k bet�lt�se m�g �res helyekre, v�letlenszer�en.
		 */
		for (int i = 0; i < elemek.size(); i++) {
			x = i % 39;
			y = i / 39;

			Elem mostani = elemek.get(i);
			if (mostani.getElem().equals(" "))
				if (rnd.nextInt(100) <= percent_AI) {
					if ((x < 10) & (y < 4))
						continue;
					tempAI = new AI();
					tempAI.poz = new Position(x, y);
					aik.add(tempAI);
				}
		}

		frissit(); // Kell, hogy tudjuk hol az elpuszt�that� fal.
		/**
		 * Kincsek bet�lt�se azon helyekre, ahol van fal, v�letlenszer�en.
		 */
		for (int i = 0; i < elemek.size(); i++) {
			x = i % 39;
			y = i / 39;

			Elem mostani = elemek.get(i);
			if (mostani.getElem().equals("+")) {
				if (rnd.nextInt(100) <= percent_Kincsek) {
					temp = new Elem("kincs");
					temp.poz = new Position(x, y);
					kincsek.add(temp);
				}
			}
		}
	}

	/**
	 * Alap konstruktor. List�k megkre�l�sa.
	 */
	public Map(String nehezseg) {
		player.poz = new Position(1, 1);
		aik = new ArrayList<AI>();
		pusztithatok = new ArrayList<Elem>();
		kincsek = new ArrayList<Elem>();
		feltoltCuccal(nehezseg);
	}

	/**
	 * Kirajzolja a param�terk�nt kapott JTeaxtArea-ra a map-ot.
	 * 
	 * @param ide
	 *            Erre a JTextArea-ra megy a rajz.
	 */
	public void kirajzol(JTextArea ide) {
		for (int i = 0; i < 429; i++) {
			if (i % 39 == 0)
				ide.append(newline + "  ");
			ide.append(elemek.get(i).getElem());
		}
	}
}
