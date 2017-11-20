package at.gehirnstroem.flyforfood;
import java.util.HashSet;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public class HungerMaker implements Runnable {
	
	FlyForFood fff = null;
	
    public HungerMaker(FlyForFood fff){
    	this.fff = fff;
    }

    public void run() {

    	HashSet<FlyingPlayer> toRemove = new HashSet<FlyingPlayer>();
    	
    	for(int i=0; i<fff.playersInFlightMode.size(); i++)
    	{
    		FlyingPlayer flyingPlayer = fff.playersInFlightMode.get(i);
    		if(flyingPlayer.thePlayer.isOnline())
    		{
	    		if(flyingPlayer.thePlayer.getFoodLevel()<=0)
	    		{
	    			flyingPlayer.thePlayer.setAllowFlight(false);
	    			if(fff.playerMessagesActivated)
	    				flyingPlayer.thePlayer.sendMessage(fff.playerMessageNoFoodWhileFlying);
	    			toRemove.add(flyingPlayer);
	    			//Better to build hashtable to remove?
	    		}
	    		else
	    		{
    				String playerName = flyingPlayer.thePlayer.getName();
	    			//System.out.println("Looped "+playerName+".");
	    			if(flyingPlayer.thePlayer.isFlying())
	    			{
	    				Location lastLocation = flyingPlayer.lastLocation;
	    				Location currentLoc = flyingPlayer.thePlayer.getLocation().clone();
	    					    				
	    				//Decrease foodlevel if needed 
	    				if(!flyingPlayer.thePlayer.hasPermission("flyforfood.freeflight"))
	    				{
		    				double costTime = fff.costTime;
		    				double costDistance = fff.costDistance;
		    				double foodCost = ( (lastLocation.distance(currentLoc)/6000)*costDistance + (0.001*costTime) );
	    					flyingPlayer.setFineFoodLevel(flyingPlayer.fineFoodLevel - foodCost);
	    					if(fff.consoleOutputActivated)
	    						System.out.println("Flying costed "+playerName+" "+foodCost+" Food.");
	    				}
	    				
	    				//Update last location
	    				flyingPlayer.lastLocation = currentLoc;
	    				
	    			}
	    			else if (flyingPlayer.thePlayer.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR
	    					||flyingPlayer.thePlayer.getLocation().getBlock().getRelative(BlockFace.EAST).getType() != Material.AIR
	    					||flyingPlayer.thePlayer.getLocation().getBlock().getRelative(BlockFace.EAST_NORTH_EAST).getType() != Material.AIR
	    					||flyingPlayer.thePlayer.getLocation().getBlock().getRelative(BlockFace.EAST_SOUTH_EAST).getType() != Material.AIR
	    					||flyingPlayer.thePlayer.getLocation().getBlock().getRelative(BlockFace.NORTH).getType() != Material.AIR
	    					||flyingPlayer.thePlayer.getLocation().getBlock().getRelative(BlockFace.NORTH_EAST).getType() != Material.AIR
	    					||flyingPlayer.thePlayer.getLocation().getBlock().getRelative(BlockFace.NORTH_NORTH_EAST).getType() != Material.AIR
	    					||flyingPlayer.thePlayer.getLocation().getBlock().getRelative(BlockFace.NORTH_NORTH_WEST).getType() != Material.AIR
	    					||flyingPlayer.thePlayer.getLocation().getBlock().getRelative(BlockFace.NORTH_WEST).getType() != Material.AIR
	    					||flyingPlayer.thePlayer.getLocation().getBlock().getRelative(BlockFace.SOUTH).getType() != Material.AIR
	    					||flyingPlayer.thePlayer.getLocation().getBlock().getRelative(BlockFace.SOUTH_EAST).getType() != Material.AIR
	    					||flyingPlayer.thePlayer.getLocation().getBlock().getRelative(BlockFace.SOUTH_SOUTH_EAST).getType() != Material.AIR
	    					||flyingPlayer.thePlayer.getLocation().getBlock().getRelative(BlockFace.SOUTH_SOUTH_WEST).getType() != Material.AIR
	    					||flyingPlayer.thePlayer.getLocation().getBlock().getRelative(BlockFace.SOUTH_WEST).getType() != Material.AIR
	    					||flyingPlayer.thePlayer.getLocation().getBlock().getRelative(BlockFace.WEST).getType() != Material.AIR
	    					||flyingPlayer.thePlayer.getLocation().getBlock().getRelative(BlockFace.WEST_NORTH_WEST).getType() != Material.AIR
	    					||flyingPlayer.thePlayer.getLocation().getBlock().getRelative(BlockFace.WEST_SOUTH_WEST).getType() != Material.AIR
	    					||flyingPlayer.thePlayer.getLocation().getBlock().getRelative(BlockFace.SELF).getType() != Material.AIR)
	    			{
	    				flyingPlayer.thePlayer.setAllowFlight(false);
		    			if(fff.playerMessagesActivated)
		    				flyingPlayer.thePlayer.sendMessage(fff.playerMessageLanded);
	    				toRemove.add(flyingPlayer);
	    			}
	    		}
    		}
    		else
    		{
    			toRemove.add(flyingPlayer);
    		}
    		
    	}
    	fff.playersInFlightMode.removeAll(toRemove);
    }
}
