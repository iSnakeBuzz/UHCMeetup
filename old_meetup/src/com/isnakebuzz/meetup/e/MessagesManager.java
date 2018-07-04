package com.isnakebuzz.meetup.e;

import static com.isnakebuzz.meetup.a.Main.messages;
import static com.isnakebuzz.meetup.a.Main.messagesmenu;
import static com.isnakebuzz.meetup.e.API.c;

@SuppressWarnings("unused")
public class MessagesManager {
	
	//Kits
	public static String LobbyItemName;
	public static String PlayersItemName;
	public static String SpeedOptions;
	public static String ArenaLobby;
	public static String VoteNameItem;
	
	//Join
	public static String JoinMessage;
	public static String LeftMessage;
	
	//Starting
	public static String StartMSG;
	public static String StartedMSG;
	
	//Restarting
	public static String RestartingMessage;
	
	//MLG
	public static String MLGStarted;
	public static String MLGFailed;
	public static String MLGWin;
	public static String MLGFirst;
	public static String MLGSecond;
	public static String MLGThird;
	
	//Rerolls
	public static String Rerolled;
	
	//BorderMessages
	public static String BorderMessage;
	public static String BorderNow;
	
	//Motd stages
	public static String MotdLoading;
	public static String MotdWaiting;
	public static String MotdStarting;
	public static String MotdInGame;
	
	//Scenario NoClean
	public static String removedanticlean;
	
	//Kick Messages
	public static String KickMapLoading;
	public static String KickFullGame;
	public static String KickStarted;
	
	//ScenariosMessages
	public static String ScenBombExplode;
	
	//Extras
	public static String SoonUpdate;
	
	public static void setupMessages() {


		//Kits
		LobbyItemName = c(messagesmenu.getString("SpectatorItems.LobbyItem.name"));
		PlayersItemName = c(messagesmenu.getString("SpectatorItems.PlayersItem.name"));
		SpeedOptions = c(messagesmenu.getString("SpectatorItems.SpeedOptions.name"));
		ArenaLobby = c(messagesmenu.getString("SpectatorItems.ArenaLobby.name"));
		VoteNameItem = c(messagesmenu.getString("SpectatorItems.VoteItem.name"));
		
		//Join
		JoinMessage = c(messages.getString("PlayerMessage.JoinMessage"));
		LeftMessage = c(messages.getString("PlayerMessage.QuitMessage"));
		
		//Starting
		StartMSG = c(messages.getString("PlayerMessage.StartMSG"));
		StartedMSG = c(messages.getString("PlayerMessage.StartedMSG"));
		
		//Restarting
		RestartingMessage = c(messages.getString("PlayerMessage.Restarting"));
		
		//MLG
		MLGStarted = c(messages.getString("MLGMessages.Started"));
		MLGFailed = c(messages.getString("MLGMessages.Failed"));
		MLGWin = c(messages.getString("MLGMessages.Win"));
		MLGFirst = c(messages.getString("MLGMessages.First"));
		MLGSecond = c(messages.getString("MLGMessages.Second"));
		MLGThird = c(messages.getString("MLGMessages.Third"));
		
		//Rerolls
		Rerolled = c(messages.getString("PlayerMessage.Rerolled"));
		
		//BorderMEssages
		BorderMessage = c(messages.getString("BorderMessages.Border"));
		BorderNow = c(messages.getString("BorderMessages.BorderNow"));
		
		//Motd Stages
		MotdLoading = c(messages.getString("MotdStages.Loading"));
		MotdWaiting = c(messages.getString("MotdStages.Waiting"));
		MotdStarting = c(messages.getString("MotdStages.Starting"));
		MotdInGame = c(messages.getString("MotdStages.InGame"));
		
		//Scenario NoClean
		removedanticlean = c(messages.getString("ScenariosMessages.RemovedNoClean"));
		
		//Kick Messages
		KickMapLoading = c(messages.getString("KickMessages.MapLoading"));
		KickFullGame = c(messages.getString("KickMessages.GameFull"));
		KickStarted = c(messages.getString("KickMessages.GameStarted"));
		
		//Scen msg
		ScenBombExplode = c(messages.getString("ScenariosMessages.TimeBombExplode"));
		
		//Extra
		SoonUpdate = c(messages.getString("Extra.SoonUpdate"));
	}
}
