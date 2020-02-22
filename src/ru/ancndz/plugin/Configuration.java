package ru.ancndz.plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;


public class Configuration {
	
	
	public static ConfigurationSection groups;
	public static ConfigurationSection configs;
	public static List<String> disallowedToUse;
	public static List<String> allowedToCraft;
	
	public static void load(final Plugin plugin) {
		
		Logger log = plugin.getLogger();
		
		
		final File configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            plugin.getDataFolder().mkdirs();
            plugin.saveResource("config.yml", true);
            plugin.saveDefaultConfig();
        }
        final YamlConfiguration config = new YamlConfiguration();
		try {
            config.load(configFile);
        }
        catch (InvalidConfigurationException e) {
            e.printStackTrace();
            log.warning("The configuration is not a valid YAML file! Please check it with a tool like http://yaml-online-parser.appspot.com/");
            return;
        }
        catch (IOException e2) {
            e2.printStackTrace();
            log.warning("I/O error while reading the configuration. Was the file in use?");
            return;
        }
        catch (Exception e3) {
            e3.printStackTrace();
            log.warning("Unhandled exception while reading the configuration!");
            return;
        }
        
		Configuration.configs = config.getConfigurationSection("config");
		Configuration.groups = config.getConfigurationSection("groups");
		Configuration.allowedToCraft = config.getStringList("allowedItems");
		Configuration.disallowedToUse = config.getStringList("disallowedBlocks");
		
	}

}
