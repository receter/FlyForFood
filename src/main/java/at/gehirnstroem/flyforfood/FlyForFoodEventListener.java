package at.gehirnstroem.flyforfood;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class FlyForFoodEventListener implements Listener {

	FlyForFood fff = null;
	
    public FlyForFoodEventListener(FlyForFood fff){
    	this.fff = fff; 
    }
    
	@EventHandler(priority=EventPriority.HIGH)
    public void onFoodLevelChange(FoodLevelChangeEvent event){

		HumanEntity entity =  event.getEntity();        
        if (entity instanceof Player)
        {
        	Player player = (Player) entity;
        	
        	//If food level changed, update the fine food level
        	FlyingPlayer fp = fff.playersInFlightMode.getByPlayer(player);
        	if(fp != null) {
        		int foodLevelDifference = event.getFoodLevel() - player.getFoodLevel();
				if(fff.consoleOutputActivated) {
	        		System.out.println("foodLevelDifference "+foodLevelDifference+" Food.");
	        		System.out.println("event.getFoodLevel() "+event.getFoodLevel()+" Food.");
	        		System.out.println("player.getFoodLevel() "+player.getFoodLevel()+" Food.");
	        		System.out.println("fp.fineFoodLevel "+fp.fineFoodLevel+" Food.");
				}
        		fp.setFineFoodLevel(fp.fineFoodLevel + foodLevelDifference, false);
        	}
        }	
		
	}
    
	@EventHandler(priority=EventPriority.HIGH)
    public void onPlayerUse(PlayerInteractEvent event){
		
		Player player = event.getPlayer();
		
        if(player.getInventory().getItemInMainHand().getType() == Material.FEATHER
           && (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) ){
            
    		int currentFoodLevel = player.getFoodLevel();
    		
    		if(currentFoodLevel > 0 && player.hasPermission("flyforfood.fly"))
    		{
    			FlyingPlayer flyingPlayer = fff.playersInFlightMode.getByPlayer(player);
    			
    			boolean playerWasAlreadyInFlightMode = true;
    			
    			if(flyingPlayer == null)
    			{
    				playerWasAlreadyInFlightMode = false;
    				flyingPlayer = new FlyingPlayer(player);
    				fff.playersInFlightMode.add(flyingPlayer);
    			}
    			
    			player.setAllowFlight(true);
    			player.setFlying(true);
    			if(fff.playerMessagesActivated && !playerWasAlreadyInFlightMode)
    				player.sendMessage(fff.playerMessageStart);
    		}
    		else if (currentFoodLevel <= 0)
    		{
    			/* Delete???
    			 * player.setAllowFlight(false);
    			 
    			FlyingPlayer flyingPlayer = fff.playersInFlightMode.getByPlayer(player);
    			
    			if(fff.playersInFlightMode.contains(player))
    			{
    				fff.locationsInFlightMode.remove(fff.playersInFlightMode.indexOf(player));
    				fff.fineFoodLevelInFlightMode.remove(fff.playersInFlightMode.indexOf(player));
    				fff.playersInFlightMode.remove(player);
    			}*/
    			if(fff.playerMessagesActivated)
    				player.sendMessage(fff.playerMessageNoFood);
    		}
        }
        
    }
	
    @EventHandler(priority=EventPriority.HIGH)
    public void onPlayerLogin(PlayerLoginEvent event){
        //System.out.println("hui");
    }

}
