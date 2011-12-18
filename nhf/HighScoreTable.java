package nhf;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Ebben az osztályban tároljuk a rekordokat, a táblázat modelljét, és a
 * comboBox listáját.
 */

public class HighScoreTable implements Serializable {

	private static final long serialVersionUID = -2793267680707774041L;
	private ArrayList<HighScoreTableUnit> table;
	ArrayList<String> names;
	private DefaultTableModel model;

	/**
	 * Konstruktor ami létrehoz egy új példányt és feltölti a táblázat modelljét
	 * és a comboBox modelljét is.
	 */
	public HighScoreTable() {
		table = new ArrayList<HighScoreTableUnit>();
		names = new ArrayList<String>();
		model = new DefaultTableModel();
		model.addColumn("Név");
		model.addColumn("Pontszám");
		for (int i = 0; i < table.size(); i++) {
			model.addRow(new Object[] { (table.get(i)).getName(),
					(table.get(i)).getScore() });
		}
		model.fireTableRowsInserted(0, model.getRowCount());
	}

	/**
	 * Visszaadja a táblázat modellt
	 */
	public DefaultTableModel getModel() {
		return model;
	}

	/**
	 * Kinulázz minden tárolót
	 */
	public void reset() {
		names.clear();
		table.clear();
		for (int i = 0; i < model.getRowCount(); i++)
			model.removeRow(i);
	}

	/**
	 * Felvesz egy értéket mind3 tárolóba, vagy ha már létezik egy ilyen
	 * bejegyzés frissíti azt
	 * 
	 * @param u
	 *            Hozzáadni kívánt elem
	 */
	public void add(HighScoreTableUnit u) {
		if (u.getName().equals(""))
			JOptionPane.showMessageDialog(new JFrame(),
					"Új játékos felvétele nem sikerült");
		else if (find(u.getName()) != null) {
			model.setValueAt(u.getScore(), table.indexOf(u), 1);
			JOptionPane.showMessageDialog(new JFrame(),
					"Játékos adatainak frissítése megtörtént");
		} else {
			table.add(u);
			names.add(u.getName());
			Collections.sort(names);
			model.addRow(new Object[] { u.getName(), u.getScore() });
			JOptionPane.showMessageDialog(new JFrame(),
					"Új játékos felvétele sikerült");
		}
	}

	/**
	 * Megkeresi a paraméternként kapott elemet, ha nincs benne null-t ad vissza
	 * 
	 * @param s
	 *            A keresett elem neve
	 */
	public HighScoreTableUnit find(String s) {
		for (HighScoreTableUnit h : table)
			if (h.getName().equals(s))
				return h;
		return null;
	}

	/**
	 * Vissza adja a legmagasabb pontszámmal rendelkezõ játékos nevét és pontját
	 * egy stringben
	 */
	public String legjobb() {
		int max = 0;
		if (table.size() == 0)
			return new String(" ");
		else {
			for (int i = 1; i < table.size(); i++)
				if (table.get(i).getScore() > table.get(max).getScore())
					max = i;
			return new String(table.get(max).getName() + " "
					+ Integer.toString(table.get(max).getScore()));
		}
	}

	private static OutputStream honlapOutputStream(String url) {
		URL tempURL;
		URLConnection con;

		try {
			tempURL = new URL(url);
			con = tempURL.openConnection();
			return con.getOutputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static InputStream honlapInputStream(String url) {
		URL tempURL;
		URLConnection con;

		try {
			tempURL = new URL(url);
			con = tempURL.openConnection();
			return con.getInputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Szerializálás
	 */
	public void save(String hova) {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(honlapOutputStream(hova));
			out.writeObject(this);
			out.close();
		} catch (IOException e) {
			System.out.print("Ohóhóhó(save): ");
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Szerializálás
	 */
	public void load(String honnan) {
		ObjectInputStream in = null;
		HighScoreTable tempClass = null;

		try {
			in = new ObjectInputStream(honlapInputStream(honnan));
			try {
				tempClass = (HighScoreTable) in.readObject();
			} catch (ClassNotFoundException e) {
				System.out.print("Ohóhóhó: load");
				System.out.println(e.getMessage());
			}
			names = tempClass.names;
			table = tempClass.table;
			model = tempClass.model;
			in.close();
		} catch (IOException e) {
			System.out.print("Ohóhóhó: ezmeg?");
			System.out.println(e.getMessage());
		}
	}
}
