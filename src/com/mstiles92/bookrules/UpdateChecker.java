package com.mstiles92.bookrules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class UpdateChecker implements Runnable {

	private final String updateAddress = "http://updates.mstiles92.com/updates/bookrules.txt";
	private final BookRulesPlugin plugin;
	
	public UpdateChecker(BookRulesPlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		
		plugin.log("Starting UpdateChecker.");
		
		try {
			URL url = new URL(updateAddress);
			URLConnection connection = url.openConnection();
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(10000);
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String version = reader.readLine();
			plugin.log("Version found: " + version);
			
			if (version != null) {
				plugin.latestKnownVersion = version;
				
				if (!plugin.getDescription().getVersion().equalsIgnoreCase(version)) {
					plugin.updateAvailable = true;
					
					plugin.getLogger().info("Update available! New version: " + version);
					plugin.getLogger().info("See http://dev.bukkit.org/server-mods/bookrules/ for more information!");
				} else {
					plugin.log("BookRules already up to date!");
				}
				return;
			}
		} catch (IOException e) {
			
		}
		
		plugin.getLogger().info("Error: Unable to check for updates. Will check again later.");
		
	}

}
