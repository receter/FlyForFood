package at.gehirnstroem.flyforfood;

import java.util.ArrayList;
import org.bukkit.entity.Player;

public class FlyingPlayerList extends ArrayList<FlyingPlayer> {

	private static final long serialVersionUID = -4930231806595872277L;
	
	public FlyingPlayer getByPlayer(Player player)
	{
		for(FlyingPlayer fp: this)
			if(fp.thePlayer == player)
				return fp;
		
		return null;
	}
	

}
