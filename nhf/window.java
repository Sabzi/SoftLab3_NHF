package nhf;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import java.awt.Component;

public class window implements ActionListener {

	private JFrame frame;
	private JTextField textField;
	public static HighScoreTable table=new HighScoreTable();
	private JTable table_1;
	private static HighScoreTableUnit player=null;
	@SuppressWarnings("rawtypes")
	private static JComboBox comboBox;
	private static JRadioButton rdbtnLevel;
	private static JRadioButton rdbtnLevel_1;
	private static JRadioButton rdbtnLevel_2;
	private static Map map;
	private static int bombaRange = 3;
	private Timer timer;
	private Timer frissito;
	private static JTabbedPane tabbedPane;
	private static JLabel life_label; 
	private static JLabel lbl_maxpont;
	JTextArea textArea; 
	private static long kezdoIdo;
	private static int hasznaltBombak = 0;
	private static int nehezsegiSzorzo=0;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		table.load("ftp://asciiman:horesz@ftp.atw.hu/table.dat;type=i");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window window = new window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		/*String name=(String) comboBox.getSelectedItem();
		player=table.find(name);			
		rdbtnLevel_1.setEnabled(false);
		rdbtnLevel_2.setEnabled(player.getLvl3());*/
	}

	/**
	 * Create the application.
	 */
	public window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "static-access", "rawtypes", "unchecked" })
	private void initialize() {
		frame = new JFrame("ASCII-MAN");
		frame.setMaximumSize(new Dimension(1024,768));
		frame.setMinimumSize(new Dimension (1024,768));
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 1018, 740);
		frame.getContentPane().add(tabbedPane);
		
		JPanel Start = new JPanel();
		tabbedPane.addTab("Start", null, Start, null);
		Start.setLayout(null);
				
		comboBox = new JComboBox();
		comboBox.setBounds(64, 136, 171, 46);
		Start.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel(table.names.toArray(new String[table.names.size()])));
		comboBox.setSelectedItem(null);
		comboBox.setFont(new Font("Tahoma", Font.BOLD, 16));
				
		final JButton btnGomb = new JButton("Játék!",new ImageIcon(getClass().getClassLoader().getResource("icons/tick.png")));
		btnGomb.setBounds(300, 316, 96, 25);
		Start.add(btnGomb);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.BOLD, 16));
		textField.setBounds(64, 82, 171, 39);
		Start.add(textField);
		textField.setColumns(10);
		
		JButton btnHozzad = new JButton("Felvesz");
		btnHozzad.setBounds(300, 117, 96, 23);
		Start.add(btnHozzad);
		
		ButtonGroup btnGroup=new ButtonGroup();
		
		rdbtnLevel = new JRadioButton("Easy");
		rdbtnLevel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		rdbtnLevel.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnLevel.setBounds(64, 250, 128, 52);
		rdbtnLevel.setSelected(true);
		Start.add(rdbtnLevel);
		btnGroup.add(rdbtnLevel);
		
		rdbtnLevel_1 = new JRadioButton("Normal");
		rdbtnLevel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		rdbtnLevel_1.setMinimumSize(new Dimension(49, 23));
		rdbtnLevel_1.setMaximumSize(new Dimension(49, 23));
		rdbtnLevel_1.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnLevel_1.setBounds(64, 300, 128, 52);
		Start.add(rdbtnLevel_1);
		btnGroup.add(rdbtnLevel_1);
		
		rdbtnLevel_2 = new JRadioButton("Hard");
		rdbtnLevel_2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		rdbtnLevel_2.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnLevel_2.setBounds(64, 350, 128, 52);
		Start.add(rdbtnLevel_2);
		btnGroup.add(rdbtnLevel_2);
		
		JLabel lblNewLabel = new JLabel("<html><p>Tutorial:<br><br><br>-Mozogni a kurzorbillenytyûkkel lehet,bombát letenni " +
				"pedig a SPACE billenytyû lenyomásával(bomba range=3).<br><br>-Pályaelemek:<br># -> nem elpusztítható fal<br> +-> elpusztítható fal<br>" +
				" $ -> kincs<br> ß -> bomba<br>=-> ajtó<br> @ -> player<br><br> -Nehézségi szintek dinemikusan változtatják a pályagenerálást és így nehezedik a játék is," +
				"természetesen nehezebb játék szinten nagyon pontszámot lehet elérni.<br><br>-Játék elkezdésehez elõször válassza ki a nevét a lenyíló listából " +
				"( ha elõször játszik adjon meg egy nevet a szövegmezõbe és nyomjon a felvesz gombra)<br>utána állítsa be a kívánt nehézségi szintet és kattintson a Játék! feliratú gombra.</p></html>",
				SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.ITALIC, 16));
		lblNewLabel.setBounds(521, 49, 461, 474);
		Start.add(lblNewLabel);
		
		JLabel lblLegjobbPontszm = new JLabel("Legjobb pontszám:");
		lblLegjobbPontszm.setForeground(Color.BLUE);
		lblLegjobbPontszm.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblLegjobbPontszm.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblLegjobbPontszm.setBounds(64, 560, 181, 46);
		Start.add(lblLegjobbPontszm);
		
		lbl_maxpont = new JLabel(table.legjobb());
		lbl_maxpont.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbl_maxpont.setBounds(276, 560, 706, 46);
		Start.add(lbl_maxpont);
		
		JButton btnEredmnyekTrlse = new JButton("Eredmények törlése");
		btnEredmnyekTrlse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					table.reset();
					Feltolt(comboBox);
					lbl_maxpont.setText(table.legjobb());
					table.save("ftp://asciiman:horesz@ftp.atw.hu/table.dat;type=i");
			}
		});
		btnEredmnyekTrlse.setBounds(796, 678, 207, 23);
		Start.add(btnEredmnyekTrlse);
				
		btnHozzad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				table.add(new HighScoreTableUnit(textField.getText()));
				Feltolt(comboBox);
				comboBox.validate();
				table.save("ftp://asciiman:horesz@ftp.atw.hu/table.dat;type=i");
				comboBox.setSelectedItem(null);
				textField.setText("");
			}
		});
		btnGomb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				map.lives=4;
				life_label.setText(Integer.toString(map.lives));
				hasznaltBombak=0;
				kezdoIdo = System.currentTimeMillis();
				
				String name =(String) comboBox.getSelectedItem();
				player=table.find(name);
				if(player==null){
					JOptionPane.showMessageDialog(frame,"Nem adott meg játékost, kérjük válasszon a listából");
				}
				else{ 
					if(rdbtnLevel.isSelected()){
						map = new Map("easy");
						nehezsegiSzorzo=1;
					}
					else if(rdbtnLevel_1.isSelected()){
							map= new Map("normal");
							nehezsegiSzorzo=2;
						}
						else{
							map= new Map("hard");
							nehezsegiSzorzo=3;
						}
					
					frissito.setInitialDelay(50);
					frissito.start();					
					timer.start();					
					tabbedPane.setSelectedIndex(1);
				}
			}
		});
		
		//a játélfelület panelje
		
		JPanel Game = new JPanel();
		tabbedPane.addTab("Game", null, Game, null);
		Game.setLayout(null);
		
		//key listnerek
		
		Game.addKeyListener(new KeyListener() {
					
			@Override
			public void keyTyped(KeyEvent e) {}			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			/**
			 * Ha volt billentyűlenyomás irány a {@link lep()} függvény.
			 */
			@Override
			public void keyPressed(KeyEvent e) {
				lep(e);
			}
		});
		
		//Ez a játékhoz használt textArea
		
		textArea = new JTextArea();
		textArea.setFont(new Font("MONOSPACED", Font.BOLD, 30));
		textArea.setBackground(Color.black);
		textArea.setForeground(Color.WHITE);
		textArea.setBounds(106, 41, 800, 600);
		textArea.setPreferredSize(new Dimension(800,600));
		textArea.setEnabled(false);
		Game.add(textArea);
		
		JLabel lblLives = new JLabel("Lives: ");
		lblLives.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblLives.setBounds(10, 41, 46, 14);
		Game.add(lblLives);
		
		life_label = new JLabel((Integer.toString(map.lives)));
		life_label.setAlignmentX(Component.CENTER_ALIGNMENT);
		life_label.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		life_label.setBounds(50, 41, 46, 14);
		Game.add(life_label);
				
		/**
		 * Frissítő timer felvétele. A frissítés gyakoriságát lehet megadni.
		 */
		frissito = new Timer(15, this);
		timer = new Timer(600, this);
		frissito.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				map.frissit();
				if(map.lives <= 0){
					frissito.stop();
					timer.stop();
					JOptionPane.showMessageDialog(frame, "GAME OVER");
					tabbedPane.setSelectedIndex(0);
				}
				if(map.lives == 5){
					frissito.stop();
					timer.stop();
					if(pont()>player.getScore()){
						JOptionPane.showMessageDialog(frame,"if-be léptünk, sáros lett az új cipőnk");
						player.setScore(pont());
						table.add(player);
					}
					table.save("ftp://asciiman:horesz@ftp.atw.hu/table.dat;type=i");
					lbl_maxpont.setText(table.legjobb());
					tabbedPane.setSelectedIndex(2);
				}
				textArea.setText("");
				map.kirajzol(textArea);				
			}
		});
		
		/**
		 * Ezzel próbálom kikerülni az egyszerre 2 frissit() fügvényhívást. 
		 * Hiszen a Map konstruktora is hív egyszer.
		 * Majd start.
		 */
		/*frissito.setInitialDelay(50);
		frissito.start();*/
		
		/**
		 * AI-knak a timer-e. Ennyi időnként lépnek.
		 */
		timer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(AI ez : map.aik)
					ez.lep(map);		
			}
		});
		
		JPanel Rekordok = new JPanel();
		tabbedPane.addTab("Rekordok", null, Rekordok, null);
		Rekordok.setLayout(null);
		
		table_1 = new JTable(table.getModel());
		table_1.setBounds(106,41, 800,600);
		Rekordok.add(table_1);
		table_1.validate();
		table_1.getColumnModel().getColumn(0).setResizable(false);
		table_1.getColumnModel().getColumn(1).setResizable(false);
		table_1.setEnabled(false);
		table_1.setFillsViewportHeight(true);
		table_1.setCellSelectionEnabled(false);
		table_1.setAutoCreateRowSorter(true);
		table_1.getColumnModel().getColumn(0).setHeaderValue("Név");
		table_1.getColumnModel().getColumn(1).setHeaderValue("Score");
		table_1.validate();
		
		JLabel lblScore = new JLabel("Score");
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore.setBounds(645, 18, 46, 14);
		Rekordok.add(lblScore);
		
		JLabel lblNv = new JLabel("Név");
		Rekordok.add(lblNv);
		lblNv.setBounds(253, 11, 46, 28);
		lblNv.setHorizontalAlignment(SwingConstants.CENTER);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void Feltolt(JComboBox jb) {
		jb.setModel(new DefaultComboBoxModel(table.names.toArray(new String[table.names.size()])));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Ez a függvény a lenyomott gomb-nak megfelelő parancsot hajtja végre.
	 * Fel, le, balra, jobbra lép és bombát rak le.
	 * @param uccsogomb Mi volt lenyomva utoljára.
	 */
	@SuppressWarnings("static-access")
	private void lep(KeyEvent uccsogomb) { 
		try{
	    	switch(uccsogomb.getKeyCode()){		//bal
		    	case 37: {	
		    		map.player.poz.balra(map);  
		    		break;
		    	}  	
		    	
		    	case 38: {				//fel
		    		map.player.poz.fel(map);   
		    		break;
		    	}	
		    	
		    	case 39: {				//jobb
		    		map.player.poz.jobbra(map);     
		    		break;
		    	}
				
		    	case 40: {				//le
					map.player.poz.le(map);   
		    		break;
		    	}
		    	case 32: {
		    		/**
		    		 * Ha még nincs bomba lerakva és space volt nyomva, 
		    		 * akkor létrehozzuk a bombát az adott player pozíción.
		    		 * Csinálunk neki egy timer-t, ami 2 sec múlva jelez, 
		    		 * ekkor meghívjuk a {@link #robbant()} függvényt, 
		    		 * ami elvégzi a piszkos munkát.
		    		 * Ha megvolt a piszkkos munka, akkor a bombát kinullázzuk,
		    		 * és a timer leállítjuk.
		    		 */
		    		if(map.bomba == null){ 
		    			hasznaltBombak++;
						map.bomba = new Elem("bomba");
						map.bomba.poz = new Position(map.player.poz);
						
			    		final Timer ujTimer = new Timer(2000, this);
			    		ujTimer.addActionListener(new ActionListener() {
							
							public void actionPerformed(ActionEvent e) {
								robbant();
								map.bomba = null;
								ujTimer.stop();
							}
						});
			    		/**
			    		 * Bombatimer start.
			    		 */
			    		ujTimer.start();
		    		} 
		    	}
	    	}
		}catch (InvalidMove e) {};
	}
	
	/**
	 * Ez a függvény felelős a felrobbantott elemek listából való eltávolításáért.
	 * Végigmegyünk a merre iránynak megfelelően a soron/oszlopon, amíg falat/ai-t/playert nem találunk.
	 * Illetve el nem érjük a bomba hatótávolságát(3).
	 * Ha a fal/ai felrobbantható kivesszük a listából.
	 * Ha playert találtunk, GAME OVER, meghaltunk.
	 * Elég erőforrásigényesnek tűnik, de valójában nem fogunk sokáig menni, 
	 * mert hamar fogunk vmit találni, amivel abbahagyjuk.
	 * @param merre Merre induljunk el?
	 */
	@SuppressWarnings("static-access")
	private void kiszed(String merre){
		int valtozas = 0;
		int mennyitMentünk = 0;
		
		if(merre.equals("balra")) valtozas = -1;
			else if(merre.equals("jobbra")) valtozas = +1;
				else if(merre.equals("fel")) valtozas = -39;
					else if(merre.equals("le")) valtozas = +39;
						else System.exit(1);
		
		int temp_idx = map.bomba.poz.pozToIndex();
		
		while((map.elemek.get(temp_idx).getElem().equals("#")==false)&(mennyitMentünk <= bombaRange)){
			/**
			 * Ha pusztítható falat találtunk.
			 */
			if(map.elemek.get(temp_idx).getElem().equals("+")){
				for(int i = 0; i<map.pusztithatok.size();i++){
					if(map.pusztithatok.get(i).poz.pozToIndex()==temp_idx){
						map.pusztithatok.remove(i);
						break;  //jöhet a következő irány
					}
				}						
				break;  //jöhet a következő irány
			}
			
			/**
			 * Ha ai-t találtunk.
			 */
			if(map.elemek.get(temp_idx).getElem().equals("%")){
				for(int i = 0; i<map.aik.size();i++){
					if(map.aik.get(i).poz.pozToIndex()==temp_idx){
						map.aik.remove(i);
						break;  //jöhet a következő irány
					}
				}	
				break;  //jöhet a következő irány
			}
			
			/**
			 * Ha player találtunk.
			 */
			if(map.elemek.get(temp_idx).getElem().equals("@")){
				map.lives--;
				life_label.setText(Integer.toString(map.lives));
				System.out.println("Életet vesztettél.");
				if(map.lives == 0){
					break;
				}
			}
				
			temp_idx = temp_idx + valtozas;
			mennyitMentünk++;
		}
	}
	
	/**
	 * A kiszed fügvényt hívja meg 4-szer minden irányban.
	 * Ha ai van bombán, azt is kiszedjük.
	 */
	@SuppressWarnings("static-access")
	private void robbant() {
		kiszed("balra");
		kiszed("jobbra");
		kiszed("fel");
		kiszed("le");
		/**
		 * Ha ai van bombán, azt is kiszedjük.
		 */
		for(int i = 0; i<map.aik.size();i++)
			if(map.aik.get(i).poz.pozToIndex()==map.bomba.poz.pozToIndex())
				map.aik.remove(i);
	}
	/**
	 * Visszatér a játékos pontszámával.
	 */
	@SuppressWarnings("static-access")
	private int pont(){		
		if(map.lives == 5){
			int mennyiTeltEL = (int) (System.currentTimeMillis() - kezdoIdo);
			
			return (map.talaltKincsek*10000-(hasznaltBombak)*1000-(mennyiTeltEL/30000)*5000)*nehezsegiSzorzo;
		}
		return 0;
	}
}
