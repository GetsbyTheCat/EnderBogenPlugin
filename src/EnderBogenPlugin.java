import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.NamespacedKey;
import org.bukkit.ChatColor;

public class EnderBogenPlugin extends JavaPlugin {
	public class EnderBogenListener implements Listener {
		@EventHandler
		public void onEvent(ProjectileHitEvent event){
			Projectile geschoss = event.getEntity();
			
			if(geschoss.getType() == EntityType.ARROW){
				ProjectileSource schuetze = geschoss.getShooter();
				
				if(schuetze instanceof Player){
					Player spieler = (Player) schuetze;
					ItemStack gegenstand = spieler.getInventory().getItemInMainHand();
					ItemMeta metaData = gegenstand.getItemMeta();
					
					if(metaData.getDisplayName().equals(ChatColor.AQUA + "Enderbogen")){
						spieler.teleport(geschoss.getLocation());
					}
				}
			}
		}
	}
	
	public void onEnable() {
		ItemStack enderbogen = new ItemStack(Material.BOW);
		ItemMeta metaData = enderbogen.getItemMeta();
		metaData.setDisplayName(ChatColor.AQUA + "Enderbogen");
		enderbogen.setItemMeta(metaData);
		enderbogen.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
		
		NamespacedKey bowkey = new NamespacedKey(this, "blabla");
		ShapedRecipe enderbogenRezept = new ShapedRecipe(bowkey, enderbogen);
		enderbogenRezept.shape("***", "*B*", "***");
		enderbogenRezept.setIngredient('B', Material.BOW);
		enderbogenRezept.setIngredient('*',  Material.ENDER_EYE);
		this.getServer().addRecipe(enderbogenRezept);
		
		PluginManager pluginManager = this.getServer().getPluginManager();
		EnderBogenListener listener = new EnderBogenListener();
		pluginManager.registerEvents(listener,  this);;
	}
	
	public void onDisable() {
	}
}