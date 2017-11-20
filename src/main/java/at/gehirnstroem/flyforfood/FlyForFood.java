package at.gehirnstroem.flyforfood;

import org.bukkit.plugin.java.JavaPlugin;

public class FlyForFood extends JavaPlugin {
	public FlyingPlayerList playersInFlightMode = new FlyingPlayerList();
	public double costTime = 0;
	public double costDistance = 0;
	public boolean consoleOutputActivated = false;
	public boolean playerMessagesActivated = false;
	public String playerMessageStart;
	public String playerMessageLanded;
	public String playerMessageNoFood;
	public String playerMessageNoFoodWhileFlying;	
//	public List<Player> playersInFlightMode = new ArrayList<Player>();
//	public List<Location> locationsInFlightMode = new ArrayList<Location>();
//	public List<Double> fineFoodLevelInFlightMode = new ArrayList<Double>();
    @Override
    public void onEnable(){
        // Save a copy of the default config.yml if one is not there
        this.saveDefaultConfig();
        
		costTime = this.getConfig().getDouble("cost_time");
		costDistance = this.getConfig().getDouble("cost_distance");
		consoleOutputActivated =  this.getConfig().getBoolean("console_output");
		
		playerMessagesActivated = this.getConfig().getBoolean("player_messages");
		
		playerMessageStart = this.getConfig().getString("player_message_start");
		playerMessageLanded = this.getConfig().getString("player_message_landed");
		playerMessageNoFood = this.getConfig().getString("player_message_no_food");
		playerMessageNoFoodWhileFlying = this.getConfig().getString("player_message_no_food_while_flying");
		
		getLogger().info("Fly For Food has been enabled.");
		//Am anfang abfragen wer im flug modus ist?
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new HungerMaker(this), 60L, 60L);
		this.getServer().getPluginManager().registerEvents(new FlyForFoodEventListener(this), this);
    }
    @Override
    public void onDisable() {
		getLogger().info("Fly For Food has been disabled!");
	}
}