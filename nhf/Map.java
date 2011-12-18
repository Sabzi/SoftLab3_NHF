package nhf;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JTextArea;

/**
 * A NAGY Map osztály, amiben a lényeg van.<br>
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
 * <b>Függvények:</b>
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
	 * Elem-eket tárol, ezt rajzoljuk ki. A {@link #frissit()} függvény ebbe a
	 * listába rakja bele a többi listából az Elemeket. Legkésõbb kerül bele az,
	 * amit meg is kívánunk jeleníteni, "legfölül van". Így a
	 * {@link #kirajzol(JTextArea)} ezt fogja megjeleníteni.
	 */
	static ArrayList<Elem> elemek; // Eleme-eket tratalmazó lista.
	static final String newline = System.getProperty("line.separator");
	Random rnd = new Random();
	int talaltKincsek = 0;

	/**
	 * Map elemek:
	 */
	static Elem player = new Elem("player");
	/**
	 * Ez AI-kat tárol, ezek pozícióit, stb. Rálépve vagy ha õ lép ránk
	 * meghalunk.
	 */
	static ArrayList<AI> aik;
	/**
	 * Ez tárolja a kincsek pozícióit. Rálépve kincset szerzünk.
	 */
	static ArrayList<Elem> kincsek;
	/**
	 * Ez az elpusztítható Elem-eket tárolja.
	 */
	static ArrayList<Elem> pusztithatok;
	/**
	 * Az ajtó, erre rálépve nyerünk.
	 */
	static Elem ajto = new Elem("ajto");
	/**
	 * Bomba, amit mi rakunk le. Horizontálisan és vertikálisan terjed a
	 * "robbanás", ami elpusztítja az elsõ útjába kerülõ játékost, ai-t, vagy
	 * elpusztítható falat. Sima falban "megakad".
	 */
	static Elem bomba;
	/**
	 * Ennyi életünk van.
	 */
	static int lives = 4;

	/**
	 * Az alap, változatlan kinézete a mapnak. Az elpusztíthatatlan falak.
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
	 * Ez a függvény tölti be megfelelõ sorrendben a különbözõ dolgokat, úgy
	 * mint: kincsek, aik, bomba, ajtó, játékos. Itt ellenõrizzük, hogy a player
	 * AI-n, kincsen, vagy ajtón áll-e.
	 */
	public void frissit() {
		alap();

		elemek.set(player.poz.pozToIndex(), player);

		/**
		 * AIk feltöltése. Végigmegyünk az aik listán és betöltjük õket az
		 * elemek listába. Ha az egyiken rajta van a játékos, akkor az meghalt.
		 */
		for (AI mostani : aik) {
			if (elemek.get(mostani.poz.pozToIndex()).getElem().equals("@")) {
				System.out.println("Életet vesztettél.");
				lives--;
				if (lives == 0)
					return;
			}
			elemek.set(mostani.poz.pozToIndex(), mostani);
		}

		/**
		 * Kincsek feltöltése. Végigmegyünk a kincsek listán, betöltjük õket az
		 * elemek listába. Ha az egyiken rajta van a játékos, azt eltávolítjuk,
		 * kincset talált.
		 */
		for (int i = 0; i < kincsek.size(); i++) {
			Elem current = kincsek.get(i);
			if (elemek.get(current.poz.pozToIndex()).getElem().equals("@")) {
				System.out.println("Kincs találat.");
				talaltKincsek++;
				kincsek.remove(i);
			}
			elemek.set(current.poz.pozToIndex(), current);
		}

		/**
		 * Ellenõrizzük, hogy ajtón állunk-e.
		 */
		if (elemek.get(ajto.poz.pozToIndex()).getElem().equals("@")) {
			lives = 5;
			return;
		}

		/**
		 * Berakjuk az elemek-be az ajtót.
		 */
		elemek.set(ajto.poz.pozToIndex(), ajto);

		/**
		 * Elpusztítható falak berakása.
		 */
		for (Elem ez : pusztithatok)
			elemek.set(ez.poz.pozToIndex(), ez);

		/**
		 * Bomba berakása, ha kell(van bomba lerakva).
		 */
		if (bomba != null)
			elemek.set(bomba.poz.pozToIndex(), bomba);

		/**
		 * Játékos berakása.
		 */
		elemek.set(player.poz.pozToIndex(), player);
	}

	/**
	 * Ezen függvény felel a nehézségnek megfelelõ számú ai, elpusztítható fal
	 * és kincs betételéért, és pozícióik felvételéért. Itt még csak a saját
	 * listáikba rakjuk be õket a megfelelõ pozíciókkal.
	 * 
	 * @param nehezseg
	 *            Ez szabályozza a berakások gyakoriságát.
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
		 * Nehézségnek megfelelõ értékek beállítása.
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
		 * Elpusztítható falak berakása, véletlenszerûen az üres helyekre. Az
		 * algoritmus kihagyja a bal-felsõ részt, hogy el lehessen indulni. Itt
		 * rakjuk be az ajtót.
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
					 * Ajtó egy véletlen jobb-alsó részbe történõ berakása.
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
		 * Ha mégsem raktunk be ajtót, akkor most berakunk.
		 */
		if (voltAjto == false) {
			ajto.poz = new Position(25, 8);
		}

		/**
		 * AI-k betöltése még üres helyekre, véletlenszerûen.
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

		frissit(); // Kell, hogy tudjuk hol az elpusztítható fal.
		/**
		 * Kincsek betöltése azon helyekre, ahol van fal, véletlenszerûen.
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
	 * Alap konstruktor. Listák megkreálása.
	 */
	public Map(String nehezseg) {
		player.poz = new Position(1, 1);
		aik = new ArrayList<AI>();
		pusztithatok = new ArrayList<Elem>();
		kincsek = new ArrayList<Elem>();
		feltoltCuccal(nehezseg);
	}

	/**
	 * Kirajzolja a paraméterként kapott JTeaxtArea-ra a map-ot.
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
