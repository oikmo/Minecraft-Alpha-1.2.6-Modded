package net.minecraft.src;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

public class UUIDRetriever {
	@SuppressWarnings("null")
	public static String GetUUIDFromName(String name) {
		
		System.setProperty("http.agent", "Chrome");
		String content = null;
		String[] values = new String[10];
		URLConnection connection = null;
		try {
			connection =  new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openConnection();
			connection.addRequestProperty("User-Agent", "Mozilla");
			
			if(connection != null) {
				//System.out.println(connection.getRequestProperty("User-Agent"));
				Scanner scanner = new Scanner(connection.getInputStream());
				scanner.useDelimiter("\\Z");
				content = scanner.next();
				scanner.close();
			}
			
		} catch ( Exception ex ) {
			ex.printStackTrace();
		}
		
		JsonParser parser = Json.createParser(new StringReader(content));

		int valueI = 0;
		while (parser.hasNext()) {
			final Event event = parser.next();
			switch (event) {
			case VALUE_STRING:
				values[valueI] = parser.getString();
				valueI++;
				break;
			default:
				break;
			}
		}
		parser.close();
		
		return values[0];
	}
	
	public static String GetSkinLinkFromUUID(String UUID) {
		return (new StringBuilder()).append("https://crafthead.net/skin/").append(UUID).toString() + "/";
	}
}