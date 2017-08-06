package Aukarapol;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Scanner;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents((Listener) new PlayerClickCheck(), this);
        pm.registerEvents((Listener) new PlayerCommands(), this);
        pm.registerEvents((Listener) new KillReward(), this);
        getCmds();

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        reloadConfig();

    }

    @Override
    public void onDisable() {
        this.getServer().getPluginManager().disablePlugins();
    }

    public void getCmds(){
        getCommand("playerrc").setExecutor(new PlayerCommands());
    }

    public class BlockExtar implements Listener{
        @EventHandler(priority = EventPriority.HIGHEST)
        public void BE(BlockBreakEvent event){

            Player player = (Player)event.getPlayer();
            Block block = (Block)event.getBlock();

            int RE = (int)(Math.random() * 3);

            Scanner scanner = new Scanner(System.in);
            String s = event.getBlock().getType().getData().getName();
            String re1 = scanner.nextLine();

            for (int i = 1; i <=3; i++){
                if(re1.length() == RE){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,20,1));
                }else{
                    return;
                }
                re1+=i;
            }

            if (player.isBlocking()){
                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20, 1));
            }

        }
    }

    public class KillReward implements Listener{

        @EventHandler (priority = EventPriority.HIGHEST)
        public void iQ(PlayerInteractEvent event){

            Player player = (Player)event.getPlayer();

            String killer = player.getKiller().getName();

            int RD = (int)(Math.random() * 4);
            if ((killer.length() == 1) && (killer.length() == 3)) {
                switch (RD) {
                    case 1:
                        player.getInventory().addItem(new ItemStack(Material.DIAMOND, 1));
                        player.updateInventory();
                        break;
                    case 2:
                        player.getInventory().addItem(new ItemStack(Material.IRON_INGOT, 3));
                        player.updateInventory();
                        break;
                    case 3:
                        player.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, 3));
                        player.updateInventory();
                        break;
                    case 4:
                        player.getInventory().addItem(new ItemStack(Material.EMERALD, 1));
                        player.updateInventory();
                        break;
                    default:
                        player.sendMessage("คุณไม่ได้รับรางวัลจากการฆ่า!");
                }
            }else if (killer.length() == 2){
                player.sendMessage("คุณต้องฆ่าคนนี้อีก 3 ครั้งเพื่อจะได้รับของรางวัล");
            }else{
                return;
            }

        }
    }

    public class PlayerClickCheck implements Listener {
        @EventHandler
        public void PIC(PlayerInteractEntityEvent event) {
            Player rc = (Player) event.getRightClicked();

            if (rc instanceof Player) {

                if (event.getPlayer().isSneaking()) {
                    event.getPlayer().sendMessage(
                            getConfig().getString("show*1").replaceAll("(&([a-f0-9]))", "\u00A7$2") + "\n" +
                                    getConfig().getString("player-msg").replaceAll("(&([a-f0-9]))", "\u00A7$2") + rc.getName() + "\n" +
                                    getConfig().getString("level-msg").replaceAll("(&([a-f0-9]))", "\u00A7$2") + rc.getLevel() + "\n" +
                                    getConfig().getString("exp-msg").replaceAll("(&([a-f0-9]))", "\u00A7$2") + rc.getExp() + "\n" +
                                    getConfig().getString("heal-msg").replaceAll("(&([a-f0-9]))", "\u00A7$2") + rc.getHealthScale() + "\n" +
                                    getConfig().getString("gamemode-msg").replaceAll("(&([a-f0-9]))", "\u00A7$2") + rc.getGameMode() + "\n" +
                                    getConfig().getString("item-msg").replaceAll("(&([a-f0-9]))", "\u00A7$2") + rc.getItemInHand().getItemMeta().getDisplayName() + "\n" +
                                    getConfig().getString("location-msg").replaceAll("(&([a-f0-9]))", "\u00A7$2") + rc.getLocation() + "\n" +
                                    getConfig().getString("show*2").replaceAll("(&([a-f0-9]))", "\u00A7$2")
                    );
                }

            }

        }

    }

    public class PlayerCommands implements CommandExecutor,Listener{

        @Override
        public boolean onCommand(CommandSender Sender, Command cmd, String alias, String[] args) {
            if(Sender instanceof Player) {
                if (Sender.hasPermission("player.rc")) {
                    if (cmd.getName().equalsIgnoreCase("playerrc")) {
                        saveConfig();
                        reloadConfig();
                        Sender.sendMessage(getConfig().getString("reload-msg").replaceAll("(&([a-f0-9]))", "\u00A7$2"));
                    }
                } else {
                    Sender.sendMessage("you don't has permission (player.rc) or op");
                }
            }else{
                Sender.sendMessage("Console can not use commands.");
            }
            return true;
        }
    }

    }