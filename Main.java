package me.thinh_geographical.landmine;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{

	
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onPlaceBlock(BlockPlaceEvent event) {
		Player p = event.getPlayer();
		if(p.hasPermission("landmine.place")) {
			Block b = event.getBlock();
			if(b.getType() == Material.STONE_PLATE) {
			   b.setMetadata("landmine", new FixedMetadataValue(this, true));
			   p.sendMessage(ChatColor.GREEN+"Đã đặt mìn thành công !");
			}
		}
		
	}
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
	   Player p = event.getPlayer();
	   if(event.getAction() == Action.PHYSICAL && event.getClickedBlock() != null) {
		   Block b = event.getClickedBlock();
		   if(b.getType() == Material.STONE_PLATE && b.getMetadata("landmine").get(0) != null) {
			   b.setType(Material.AIR);
			   TNTPrimed tnt = p.getWorld().spawn(b.getLocation(), TNTPrimed.class);
			   tnt.setFuseTicks(1);
			   tnt.setIsIncendiary(true);
		   }
	   }
	}
	
	@EventHandler
	public void onMobInteract(EntityInteractEvent event) {
		Block b = event.getBlock();
		if(b != null) {
			if(b.getType() == Material.STONE_PLATE && b.getMetadata("landmine").get(0) != null) {
				   b.setType(Material.AIR);
				   TNTPrimed tnt = b.getWorld().spawn(b.getLocation(), TNTPrimed.class);
				   tnt.setFuseTicks(1);
				   tnt.setIsIncendiary(true);
			   }
		}
	}
	
	
	
	
}
