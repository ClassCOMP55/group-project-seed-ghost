import acm.graphics.GObject;
import acm.program.*;


import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MainApplication extends GraphicsProgram{
	//Settings
	public static final int WINDOW_WIDTH = 1366;
	public static final int WINDOW_HEIGHT = 700;
	
	
	//List of all the full screen panes
	private WelcomePane welcomePane;
	private DescriptionPane descriptionPane;
	private GraphicsPane currentScreen;
	private GraphicsPane menuPane;
	private GraphicsPane characterSelectionPane;
	private SettingsPane settingPane;
	private ShopPane shopPane; 
	private MapPane mapPane;
	private CampFirePane campFirePane;
	public static CombatPane combatPane; 
	private LootPane lootPane;
	private static final String THEME_MAIN_MENU = "SONG_mainmenu.wav";
	private static final String THEME_LOW = "SONG_low.wav";
	private static final String THEME_HIGH = "SONG_high.wav";
	private static final String THEME_BOSS = "SONG_bossnode.wav";


	public MainApplication() {
		super();
	}
	
	protected void setupInteractions() {
		requestFocus();
		addKeyListeners();
		addMouseListeners();
	}
	
	
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}
	
	public void run() {
		System.out.println("Lets' Begin!");
		setupInteractions();
		
		//Initialize all Panes
		welcomePane = new WelcomePane(this);
		descriptionPane = new DescriptionPane(this);
		menuPane = new MenuPane(this);
		characterSelectionPane = new CharacterSelectionPane(this);
		settingPane = new SettingsPane(this);
		shopPane = new ShopPane(this);
		campFirePane = new CampFirePane(this);
		mapPane =  new MapPane(this);
		combatPane = new CombatPane(this);
		lootPane = new LootPane(this);
		
		// preload music + sound effects once at startup
		AudioManager.preloadMusic(
			THEME_MAIN_MENU,
			THEME_LOW,
			THEME_HIGH,
			THEME_BOSS
		);
		GameSounds.preloadAll();

		
		

		//TheDefaultPane
		switchToScreen(menuPane);
	}
	
	public static void main(String[] args) {
		new MainApplication().start();

	}
	
	public void switchToDescriptionScreen() {
		switchToScreen(descriptionPane);
	}
	
	public void switchToWelcomeScreen() {
		switchToScreen(welcomePane);
	}
	
	public void switchToMenuPane() {
		switchToScreen(menuPane);
	}
	
	public void switchToCharacterSelectionPane() {
		switchToScreen(characterSelectionPane);
	}
	
	public void switchToMapPane() {
		switchToScreen(mapPane);
	}
	
	public void switchToSettingsPane() {
		switchToScreen(settingPane);
	}
	
	public void switchToShopPane() {
		switchToScreen(shopPane); 
	}
	
	public void switchToCampFirePane() {
		campFirePane.startCampSession();
		switchToScreen(campFirePane); 
		}
	
	public void switchToCombatPane() {
		switchToScreen(combatPane);
	}
	
	public void switchToLootPane() {
		switchToScreen (lootPane);
	}

	
	protected void switchToScreen(GraphicsPane newScreen) {
		if(currentScreen != null) {
			currentScreen.hideContent();
		}
		playMusicForScreen(newScreen);
		newScreen.showContent();
		currentScreen = newScreen;
	}
	private void playMusicForScreen(GraphicsPane screen) {
		String trackName = getThemeForScreen(screen);
		if (trackName == null) {
			AudioManager.stopMusic();
			return;
		}
		AudioManager.playMusicLoop(trackName);
	}

	private String getThemeForScreen(GraphicsPane screen) {
		if (screen instanceof LootPane) return THEME_LOW;
		if (screen instanceof ShopPane) return THEME_LOW;
		if (screen instanceof SettingsPane) return THEME_MAIN_MENU;
		if (screen instanceof MenuPane) return THEME_MAIN_MENU;
		if (screen instanceof WelcomePane) return THEME_MAIN_MENU;
		if (screen instanceof DescriptionPane) return THEME_MAIN_MENU;
		if (screen instanceof CharacterSelectionPane) return THEME_MAIN_MENU;
		if (screen instanceof MapPane) return THEME_LOW;
		if (screen instanceof CampFirePane) return THEME_LOW;
		if (screen instanceof CombatPane) {
			if (MapPane.currPosition != null && MapPane.currPosition.getIndex() == 13) {
				return THEME_BOSS;
			}
			return THEME_HIGH;
		}
		return null;
	}
	
	public GObject getElementAtLocation(double x, double y) {
		return getElementAt(x, y);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(currentScreen != null) {
			currentScreen.mousePressed(e);
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(currentScreen != null) {
			currentScreen.mouseReleased(e);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(currentScreen != null) {
			currentScreen.mouseClicked(e);
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(currentScreen != null) {
			currentScreen.mouseDragged(e);
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if(currentScreen != null) {
			currentScreen.mouseMoved(e);
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(currentScreen != null) {
			currentScreen.keyPressed(e);
		}
	} 
	
	
	@Override
	public void keyReleased(KeyEvent e) {
		if(currentScreen != null) {
			currentScreen.keyReleased(e);
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		if(currentScreen != null) {
			currentScreen.keyTyped(e);
		}
	}
	
}
