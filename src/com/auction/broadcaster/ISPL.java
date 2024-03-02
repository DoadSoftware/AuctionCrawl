package com.auction.broadcaster;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.auction.containers.Data;
import com.auction.containers.Scene;
import com.auction.model.Player;
import com.auction.model.Statistics;
import com.auction.model.Team;
import com.auction.service.AuctionService;
import com.auction.model.Auction;
import com.auction.util.AuctionFunctions;
import com.auction.util.AuctionUtil;

public class ISPL extends Scene{

	private String status;
	private String slashOrDash = "-";
	public String session_selected_broadcaster = "ISPL";
	public Data data = new Data();
	public String which_graphics_onscreen = "BG";
	public int current_layer = 1;
	private String logo_path = "C:\\Images\\AUCTION\\Logos\\";
	private String photo_path  = "C:\\Images\\AUCTION\\Photos\\";
	private int value1 = 0;
	
	public ISPL() {
		super();
	}

	public ISPL(String scene_path, String which_Layer) {
		super(scene_path, which_Layer);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Data updateData(Scene scene, Auction auction,AuctionService auctionService, PrintWriter print_writer) throws InterruptedException
	{
		if(which_graphics_onscreen.equalsIgnoreCase("PLAYERPROFILE")) {
			populatePlayerProfile(true,print_writer, "", data.getPlayer_id(),auctionService.getAllStats(),auction,auctionService, session_selected_broadcaster);
		}else if(which_graphics_onscreen.equalsIgnoreCase("SQUAD")) {
			populateSquad(true,print_writer, "",value1, auction,auctionService,session_selected_broadcaster);
		}else if(which_graphics_onscreen.equalsIgnoreCase("REMAINING_PURSE_ALL")) {
			populateRemainingPurse(true,print_writer, "", auction,auctionService, session_selected_broadcaster);
		}else if(which_graphics_onscreen.equalsIgnoreCase("SINGLE_PURSE")) {
			populateRemainingPurseSingle(true,print_writer, "",value1,auction,auctionService, session_selected_broadcaster);
		}else if(which_graphics_onscreen.equalsIgnoreCase("TOP_SOLD")) {
			populateTopSold(true,print_writer, "", auction,auctionService, session_selected_broadcaster);
		}
		return data;
	}
	
	public Object ProcessGraphicOption(String whatToProcess, Auction auction, AuctionService auctionService,
			PrintWriter print_writer, List<Scene> scenes, String valueToProcess) throws InterruptedException, NumberFormatException, IllegalAccessException, IOException {
		switch (whatToProcess.toUpperCase()) {
		case "POPULATE-FF-PLAYERPROFILE": case "POPULATE-SQUAD": case "POPULATE-REMAINING_PURSE_ALL": case "POPULATE-SINGLE_PURSE": 
		case "POPULATE-TOP_SOLD": case "POPULATE-CRAWL": case "POPULATE-CRAWL_FREE_TEXT": case "POPULATE-SQUAD_ROLE":
		case "POPULATE-REMAINING_PURSE": case "POPULATE-ZONE": case "POPULATE-SQUAD_ALL":
			
			switch (session_selected_broadcaster.toUpperCase()) {
			case "HANDBALL": case "ISPL":
				switch(whatToProcess.toUpperCase()) {
				case "POPULATE-CRAWL": case "POPULATE-CRAWL_FREE_TEXT":
					print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*In SHOW 71.0;");
					scenes.get(0).setWhich_layer(String.valueOf(1));
					scenes.get(0).setScene_path(valueToProcess.split(",")[0]);
					scenes.get(0).scene_load(print_writer,session_selected_broadcaster);
					break;
				default:
					print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*In SHOW 71.0;");
					//current_layer = 3 - current_layer;
					
					scenes.add(new Scene(valueToProcess.split(",")[0],"2"));
					scenes.get(1).setWhich_layer(String.valueOf(2));
					scenes.get(1).setScene_path(valueToProcess.split(",")[0]);
					scenes.get(1).scene_load(print_writer,session_selected_broadcaster);
					break;
				}
				switch (whatToProcess.toUpperCase()) {
				case "POPULATE-SQUAD_ALL":
					populateSquadAll(false,print_writer, valueToProcess.split(",")[0], auction,auctionService, session_selected_broadcaster);
					break;
				case "POPULATE-SQUAD":
					value1 = Integer.valueOf(valueToProcess.split(",")[1]);
					populateSquad(false,print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), auction,auctionService,session_selected_broadcaster);
					break;
				case "POPULATE-REMAINING_PURSE_ALL":
					populateRemainingPurse(false,print_writer, valueToProcess.split(",")[0], auction,auctionService, session_selected_broadcaster);
					break;
				case "POPULATE-REMAINING_PURSE":
					populatePurseRemain(false,print_writer, valueToProcess.split(",")[0], auction,auctionService, session_selected_broadcaster);
					break;
				case "POPULATE-SINGLE_PURSE":
					value1 = Integer.valueOf(valueToProcess.split(",")[1]);
					populateRemainingPurseSingle(false,print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), 
							auction,auctionService, session_selected_broadcaster);
					break;
				case "POPULATE-FF-PLAYERPROFILE":
					data.setPlayer_id(Integer.valueOf(valueToProcess.split(",")[1]));
					populatePlayerProfile(false,print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
							auctionService.getAllStats(),auction,auctionService, session_selected_broadcaster);
					break;
				case "POPULATE-TOP_SOLD":
					populateTopSold(false,print_writer, valueToProcess.split(",")[0], auction,auctionService, session_selected_broadcaster);
					break;
				case "POPULATE-CRAWL":
					populateCrawl(false,print_writer, valueToProcess.split(",")[1],valueToProcess.split(",")[2], auction,auctionService,auctionService.getTeams(), session_selected_broadcaster);
					break;
				case "POPULATE-CRAWL_FREE_TEXT":	
					populateFreeCrawl(false,print_writer, auction,auctionService, session_selected_broadcaster);
					break;
				case "POPULATE-SQUAD_ROLE":
					populateSquadRole(false,print_writer, Integer.valueOf(valueToProcess.split(",")[1]), auction,auctionService,session_selected_broadcaster);
					break;
				case "POPULATE-ZONE":
					populateZone(false,print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), auction,auctionService,session_selected_broadcaster);
					break;	
				}
				//return JSONObject.fromObject(this_doad).toString();
			}
			
		case "ANIMATE-OUT": case "CLEAR-ALL": case "ANIMATE-IN-PLAYERPROFILE": case "ANIMATE-IN-SQUAD": case "ANIMATE-IN-REMAINING_PURSE_ALL": case "ANIMATE-IN-SINGLE_PURSE":
		case "ANIMATE-IN-TOP_SOLD": case "ANIMATE-IN-CRAWL": case "ANIMATE-IN-CRAWL_FREE_TEXT": case "ANIMATE-IN-SQUAD_ROLE":
		case "ANIMATE-IN-ZONE":	case "ANIMATE-IN-REMAINING_PURSE": case "ANIMATE-IN-SQUAD_ALL":
		
			switch (session_selected_broadcaster.toUpperCase()) {
			case "HANDBALL": case "ISPL":
				switch (whatToProcess.toUpperCase()) {
				case "ANIMATE-IN-SQUAD_ALL":
					print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*LOOP START;");
					which_graphics_onscreen = "SQUAD_ALL";
					break;
				case "ANIMATE-IN-REMAINING_PURSE":
					print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*LOOP START;");
					which_graphics_onscreen = "PURSE";
					break;
				case "ANIMATE-IN-SQUAD_ROLE":
					print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*LOOP START;");
					which_graphics_onscreen = "ROLE";
					break;
				case "ANIMATE-IN-ZONE":
					print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*LOOP START;");
					which_graphics_onscreen = "ZONE";
					break;	
				case "ANIMATE-IN-CRAWL":
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET CLEAR INVOKE;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET BUILD_QUEUE INVOKE;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET CRAWL 1;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET ACTIVE 1;");
					which_graphics_onscreen = "CRAWL";
					
					data.setData_on_screen(true);
					break;
				case "ANIMATE-IN-CRAWL_FREE_TEXT":
					print_writer.println("LAYER1*EVEREST*TREEVIEW*S1*SCROLLER SET CLEAR INVOKE;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET BUILD_QUEUE INVOKE;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET CRAWL 1;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET ACTIVE 1;");
					which_graphics_onscreen = "CRAWL";
					
					data.setData_on_screen(true);
					break;
				case "ANIMATE-IN-PLAYERPROFILE": 
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
					TimeUnit.SECONDS.sleep(2);
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
					which_graphics_onscreen = "PLAYERPROFILE";
					break;
				case "ANIMATE-IN-SQUAD":
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
					which_graphics_onscreen = "SQUAD";
					break;
				case "ANIMATE-IN-REMAINING_PURSE_ALL":
					print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Loop START;");
					which_graphics_onscreen = "REMAINING_PURSE_ALL";
					break;
				case "ANIMATE-IN-SINGLE_PURSE":
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
					which_graphics_onscreen = "SINGLE_PURSE";
					break;
				case "ANIMATE-IN-TOP_SOLD":
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
					which_graphics_onscreen = "TOP_SOLD";
					break;
				
				case "CLEAR-ALL":
					print_writer.println("LAYER1*EVEREST*SINGLE_SCENE CLEAR;");
					print_writer.println("LAYER2*EVEREST*SINGLE_SCENE CLEAR;");
					which_graphics_onscreen = "";
					break;
				
				case "ANIMATE-OUT":
					switch(which_graphics_onscreen) {
					case "INFOBAR":
						processAnimation(print_writer, "Out", "START", session_selected_broadcaster,current_layer);
						which_graphics_onscreen = "";
						break;
					
					
					case "PLAYERPROFILE": case "SQUAD": case "REMAINING_PURSE_ALL": case "SINGLE_PURSE": case "TOP_SOLD":
					case "ROLE": case "ZONE": case "PURSE": case "SQUAD_ALL":
						processAnimation(print_writer, "Out", "START", session_selected_broadcaster,2);
						
						if(data.isData_on_screen() == true) {
							which_graphics_onscreen = "CRAWL";
						}else {
							which_graphics_onscreen = "";
						}
						
						break;
					case "CRAWL":
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET CRAWL 0;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET ACTIVE 0;");
						
						data.setData_on_screen(false);
						which_graphics_onscreen = "";
						break;
					case "CRAWL_FREE_TEXT":
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET CRAWL 0;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET ACTIVE 0;");
						
						data.setData_on_screen(false);
						which_graphics_onscreen = "";
						break;	
					}
					break;
				}
				
			}
			
			
		}
		return null;
	}
	public void populatePlayerProfile(boolean is_this_updating,PrintWriter print_writer,String viz_scene, int playerId,List<Statistics> stats, Auction auction,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + 
				"PLAYER\nAUCTION 2024" + ";");
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBaePointsHead " + 
				"BASE PRIZE" + ";");
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBasePrice " + 
				"3" + ";");
		
		if(auction.getPlayers() != null && auction.getPlayers().size() > 0) {
			if(data.isPlayer_sold_or_unsold() == false) {
				for(int i=auction.getPlayers().size()-1; i >= 0; i--) {
					if(playerId == auction.getPlayers().get(i).getPlayerId()) {
						if(auction.getPlayers().get(i).getSoldOrUnsold().equalsIgnoreCase(AuctionUtil.SOLD)) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSoldUnsold 1 ;");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSoldPrice " + ConvertToLakh(auction.getPlayers().get(i).getSoldForPoints()) + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgSoldToTeam " + logo_path + 
									auctionService.getTeams().get(auction.getPlayers().get(i).getTeamId() - 1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSoldHead SOLD TO;");
							
							TimeUnit.MILLISECONDS.sleep(200);
							print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Result START;");
							data.setPlayer_sold_or_unsold(true);
						}else if(auction.getPlayers().get(i).getSoldOrUnsold().equalsIgnoreCase(AuctionUtil.UNSOLD)) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSoldUnsold 0 ;");
							print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Result START;");
							data.setPlayer_sold_or_unsold(true);
						}
					}
				}
			}
		}
		
		int remaining=0;
		for(int i=0; i <= auction.getTeam().size()-1; i++) {
			remaining=Integer.valueOf(auction.getTeam().get(i).getTeamTotalPurse());
			for(int j=0; j <= auction.getPlayers().size()-1; j++) {
				if(auction.getPlayers().get(j).getTeamId() == auction.getTeam().get(i).getTeamId()) {
					remaining = remaining - auction.getPlayers().get(j).getSoldForPoints();
				}
			}
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPurse0" + (i+1) + " " + 
					ConvertToLakh(remaining) + ";");
			remaining=0;
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo0" + (i+1) + " " + logo_path + 
					auctionService.getTeams().get(auction.getTeam().get(i).getTeamId()-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
			
		}
		
		if(is_this_updating == false) {
			data.setPlayer_sold_or_unsold(false);
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vPlayerPic 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName " + 
					auctionService.getAllPlayer().get(playerId - 1).getFirstname() + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
					auctionService.getAllPlayer().get(playerId - 1).getPhotoName() + AuctionUtil.PNG_EXTENSION + ";");
			
			if(auctionService.getAllPlayer().get(playerId - 1).getSurname() != null) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName " + auctionService.getAllPlayer().get(playerId - 1).getSurname() + ";");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName " + "" + ";");
			}
			
			if(auctionService.getAllPlayer().get(playerId - 1).getAge()!=null && !auctionService.getAllPlayer().get(playerId - 1).getAge().isEmpty()) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAge " + auctionService.getAllPlayer().get(playerId - 1).getAge() + " yrs" + ";");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAge " + "-" + ";");
			}
			
			if(auctionService.getAllPlayer().get(playerId - 1).getRole()!=null && !auctionService.getAllPlayer().get(playerId - 1).getRole().isEmpty()) {
				
				if(auctionService.getAllPlayer().get(playerId - 1).getRole().equalsIgnoreCase("BATSMAN")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRole " + "BATTER" + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRole " + 
							auctionService.getAllPlayer().get(playerId - 1).getRole().toUpperCase() + ";"); 	
				}
	
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRole " + "-" + ";");
			}
			
			
			if(auctionService.getAllPlayer().get(playerId - 1).getBatsmanStyle()!=null && 
					!auctionService.getAllPlayer().get(playerId - 1).getBatsmanStyle().isEmpty()) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatStyle " + auctionService.getAllPlayer().get(playerId - 1).getBatsmanStyle() + ";");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatStyle " + "-" + ";");			
			}
			
			
			if(auctionService.getAllPlayer().get(playerId - 1).getBowlerStyle()!=null && 
					!auctionService.getAllPlayer().get(playerId - 1).getBowlerStyle().isEmpty()) {
				String bowlerStyle=getBowlerType(auctionService.getAllPlayer().get(playerId - 1).getBowlerStyle());
				if(bowlerStyle==null||bowlerStyle.isEmpty()) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyle " + auctionService.getAllPlayer().get(playerId - 1).getBowlerStyle() + ";");	
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyle " + bowlerStyle + ";");		
				}
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyle " + "-" + ";");
			}
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Achivements$First*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Achivements$Second*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Achivements$Third*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Achivements$Fourth*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFourth03 " + "" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAchiveHead " + "ACHIEVEMENTS" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vAchive 0;");
			
			TimeUnit.MILLISECONDS.sleep(1000);
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 71.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
		
	}
	
	public void populateTopSold(boolean is_this_updating,PrintWriter print_writer,String viz_scene, Auction auction,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		int row = 0;
		List<Player> top_sold = new ArrayList<Player>();
		
		for(Player plyr : auction.getPlayers()) {
			top_sold.add(plyr);
		}
		Collections.sort(top_sold,new AuctionFunctions.PlayerStatsComparator());
		
		if(is_this_updating == false) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + 
					"PLAYER\nAUCTION 2024" + ";");
			
			for(int i=1; i<= 10; i++) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayername0" + i + " " + 
	    				"" + ";");
	    		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam0" + i + " " + 
	    				"" + ";");
	    		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPrice0" + i + " " + 
	    				"" + ";");
	    		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInternational0" + i + " 0 ;");
	    		
			}
		}
		
		for(int m=0; m<= top_sold.size() - 1; m++) {
			if(!top_sold.get(m).getSoldOrUnsold().equalsIgnoreCase("BID")) {
				row = row + 1;
	        	if(row <= 10) {
	        		
	        		
	        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getTicker_name() != null) {
	        			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayername0" + row + " " + 
	        					auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getTicker_name() + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayername0" + row + " " + 
								auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getFirstname() + ";");
					}
	        		
	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam0" + row + " " + 
	        				auction.getTeam().get(top_sold.get(m).getTeamId()-1).getTeamName1() + ";");
	        		
	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPrice0" + row + " " + 
	    					ConvertToLakh(top_sold.get(m).getSoldForPoints()) + ";");
	        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId()-1).getCategory().equalsIgnoreCase("FOREIGN")) {
	    				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInternational0" + row + " 1 ;");
	    			}else {
	    				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInternational0" + row + " 0 ;");
	    			}
	        		
	        	}
			}
		}
		
		if(is_this_updating == false) {
			TimeUnit.MILLISECONDS.sleep(1000);
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 71.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
	}
	
	public void populateCrawl(boolean is_this_updating,PrintWriter print_writer,String data,String data2, Auction auction,AuctionService auctionService,
			List<Team> team, String session_selected_broadcaster) throws InterruptedException 
	{
		int remaining = 0, row = 0;
		Map<String,  ArrayList<String>> prices = new HashMap<String, ArrayList<String>>();
		Map<String,  Integer> topSold = new HashMap<String, Integer>();
		Map<String,  Integer> remainingPurse = new HashMap<String, Integer>();
		String name="";
		List<Player> top_sold = new ArrayList<Player>();
		
		for(Player plyr : auction.getPlayers()) {
			top_sold.add(plyr);
		}
		Collections.sort(top_sold,new AuctionFunctions.PlayerStatsComparator());
		
		for(int m=0; m<= top_sold.size() - 1; m++) {
			if(top_sold.get(m).getSoldOrUnsold().equalsIgnoreCase("SOLD")) {
				row = row + 1;
	        	if(row <= 10) {
	        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getTicker_name() != null) {
	        			name = auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getTicker_name() 
	        					+ " - " + auctionService.getTeams().get(top_sold.get(m).getTeamId()-1).getTeamName4();
					}else {
						name = auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getFirstname() 
								+ " - " + auctionService.getTeams().get(top_sold.get(m).getTeamId()-1).getTeamName4();
					}
	        		
	        		topSold.put(name,top_sold.get(m).getSoldForPoints());
	        	}
			}
		}
		//sort map
		List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(topSold.entrySet());
        Collections.sort(sortedEntries, (e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        
		 for(int i=0; i <= auction.getTeam().size()-1; i++) {
			 ArrayList<String> list= new ArrayList<String>();
			 remaining = Integer.valueOf(auction.getTeam().get(i).getTeamTotalPurse());
				for(int j=0; j <= auction.getPlayers().size()-1; j++) {
					if(auction.getPlayers().get(j).getSoldOrUnsold().equalsIgnoreCase("SOLD") &&
							auction.getPlayers().get(j).getTeamId() == auction.getTeam().get(i).getTeamId()) {
						list.add(auction.getPlayers().get(j).getFull_name()+" ("+ConvertToLakh(auction.getPlayers().get(j).getSoldForPoints())+" L)");
						remaining = remaining - auction.getPlayers().get(j).getSoldForPoints();
					}
				}
				
				remainingPurse.put(auction.getTeam().get(i).getTeamName1(), remaining);
				if(!list.isEmpty()) {
					prices.put(auction.getTeam().get(i).getTeamName4(), list);
				}
				remaining = 0;
			}
		 String TopSold="" ,Purse="" ,pricesSold="" ;
		 
		 for(int i=1;i<=prices.keySet().size();i++) {
			 if(i==1) {
				 pricesSold=pricesSold+ "D1|TextInfo="  + (String) prices.keySet().toArray()[0] +  "\nD2|TextInfo02= " + 
							String.join(",  ", prices.get((String) prices.keySet().toArray()[0])).replaceFirst(",([^,]*)$", " &$1") + " \nD3|TextInfo03= " + "\nD4|Gap";
			 }else if(i==2) {
				 pricesSold=pricesSold+ "\nD1|TextInfo=" + (String) prices.keySet().toArray()[1] +  "\nD2|TextInfo02= " + 
							String.join(",  ", prices.get((String) prices.keySet().toArray()[1])).replaceFirst(",([^,]*)$", " &$1")+ " \nD3|TextInfo03= "  + "\nD4|Gap";
			 }else if(i==3) {
				 pricesSold=pricesSold+ "\nD1|TextInfo="  + (String) prices.keySet().toArray()[2] +  "\nD2|TextInfo02= " + 
							String.join(",  ", prices.get((String) prices.keySet().toArray()[2])).replaceFirst(",([^,]*)$", " &$1")+ " \nD3|TextInfo03= "  + "\nD4|Gap";
			 }else if(i==4) {
				 pricesSold=pricesSold+ "\nD1|TextInfo="  + (String) prices.keySet().toArray()[3] + "\nD2|TextInfo02= " + 
							String.join(",  ", prices.get((String) prices.keySet().toArray()[3])).replaceFirst(",([^,]*)$", " &$1") + " \nD3|TextInfo03= " + "\nD4|Gap";
			 }else if(i==5) {
				 pricesSold=pricesSold+ "\nD1|TextInfo="  + (String) prices.keySet().toArray()[4] +  "\nD2|TextInfo02= " + 
							String.join(",  ", prices.get((String) prices.keySet().toArray()[4])).replaceFirst(",([^,]*)$", " &$1")+ " \nD3|TextInfo03= "  + "\nD4|Gap";
			 }else if(i==6) {
				 pricesSold=pricesSold+ "\nD1|TextInfo="  + (String) prices.keySet().toArray()[5] +  "\nD2|TextInfo02= "+ 
							String.join(",  ", prices.get((String) prices.keySet().toArray()[5])).replaceFirst(",([^,]*)$", " &$1")+ " \nD3|TextInfo03= "  + "\nD4|Gap";
			 }
		 }
		 
		 
		 for(int i=1;i<=remainingPurse.keySet().size();i++) {
			 if(i==1) {
				 if(ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[0])).equalsIgnoreCase("0")){
					 Purse = Purse+"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[0] + " :  " + 
							 "-" + " \nD3|TextInfo03= \nD4|Gap";
				 }else {
					 Purse = Purse+"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[0] + " :  " + 
							 ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[0])) + " L" + " \nD3|TextInfo03= \nD4|Gap"; 
				 } 
			 }else if(i==2) {
				 if(ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[1])).equalsIgnoreCase("0")){
					 Purse = Purse+"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[1] + " :  " + 
							 "-" + " \nD3|TextInfo03= \nD4|Gap"; 
				 }else {
					 Purse = Purse+"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[1] + " :  " + 
							 ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[1])) + " L" + " \nD3|TextInfo03= \nD4|Gap"; 
				 }
			 }else if(i==3) {
				 if(ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[2])).equalsIgnoreCase("0")){
					 Purse = Purse+"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[2] + " :  " + 
							 "-" + " \nD3|TextInfo03= \nD4|Gap";
				 }else {
					 Purse = Purse+"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[2] + " :  " + 
							 ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[2])) + " L" + " \nD3|TextInfo03= \nD4|Gap";
				 }
			 }else if(i==4) {
				 if(ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[3])).equalsIgnoreCase("0")){
					 Purse = Purse+"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[3] + " :  " + 
							 "-" + " \nD3|TextInfo03= \nD4|Gap";  
				 }else {
					 Purse = Purse+"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[3] + " :  " + 
							 ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[3])) + " L" + " \nD3|TextInfo03= \nD4|Gap";  
				 }
			 }else if(i==5) {
				 if(ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[4])).equalsIgnoreCase("0")){
					 Purse = Purse+"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[4] + " :  " + 
							 "-" + " \nD3|TextInfo03= \nD4|Gap"; 
				 }else {
					 Purse = Purse+"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[4] + " :  " + 
							 ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[4])) + " L" + " \nD3|TextInfo03= \nD4|Gap"; 
				 }
			 }else if(i==6) {
				 if(ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[5])).equalsIgnoreCase("0")){
					 Purse = Purse+"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[5] + " :  " + 
							 "-" + " \nD3|TextInfo03= \nD4|Gap"; 
				 }else {
					 Purse = Purse+"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[5] + " :  " + 
							 ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[5])) + " L" + " \nD3|TextInfo03= \nD4|Gap"; 
				 }
			 }
		 }
 
		 
		 for(int i=1;i<=sortedEntries.size();i++) {
			 if(i==1) {
				 TopSold =TopSold +"\nD2|TextInfo02= " + sortedEntries.get(0).getKey() + " - " + 
						 ConvertToLakh(sortedEntries.get(0).getValue()) + " L" + " \nD3|TextInfo03= \nD4|Gap" ;
			 }else if(i==2) {
				 TopSold =TopSold +"\nD2|TextInfo02= " + sortedEntries.get(1).getKey() + " - " + 
						 ConvertToLakh(sortedEntries.get(1).getValue()) + " L" + " \nD3|TextInfo03= \nD4|Gap" ; 
			 }else if(i==3) {
				 TopSold =TopSold +"\nD2|TextInfo02= " + sortedEntries.get(2).getKey() + " - " + 
						 ConvertToLakh(sortedEntries.get(2).getValue()) + " L" + " \nD3|TextInfo03= \nD4|Gap" ; 
			 }else if(i==4) {
				 TopSold =TopSold +"\nD2|TextInfo02= " + sortedEntries.get(3).getKey() + " - " + 
						 ConvertToLakh(sortedEntries.get(3).getValue()) + " L" + " \nD3|TextInfo03= \nD4|Gap" ; 
			 }else if(i==5) {
				 TopSold =TopSold +"\nD2|TextInfo02= " + sortedEntries.get(4).getKey()+ " - " + 
						 ConvertToLakh(sortedEntries.get(4).getValue()) + " L" + " \nD3|TextInfo03= \nD4|Gap" ; 
			 }else if(i==6) {
				 TopSold =TopSold +"\nD2|TextInfo02= " + sortedEntries.get(5).getKey() + " - " + 
						 ConvertToLakh(sortedEntries.get(5).getValue()) + " L" + " \nD3|TextInfo03= \nD4|Gap" ; 
			 }else if(i==7) {
				 TopSold =TopSold +"\nD2|TextInfo02= " + sortedEntries.get(6).getKey() + " - " + 
						 ConvertToLakh(sortedEntries.get(6).getValue()) + " L" + " \nD3|TextInfo03= \nD4|Gap" ; 
			 }else if(i==8) {
				 TopSold =TopSold +"\nD2|TextInfo02= " +sortedEntries.get(7).getKey() + " - " + 
						 ConvertToLakh(sortedEntries.get(7).getValue()) + " L" + " \nD3|TextInfo03= \nD4|Gap" ; 
			 }else if(i==9) {
				 TopSold =TopSold +"\nD2|TextInfo02= " +sortedEntries.get(8).getKey() + " - " + 
						 ConvertToLakh(sortedEntries.get(8).getValue()) + " L" + " \nD3|TextInfo03= \nD4|Gap" ; 
			 }else if(i==10) {
				 TopSold =TopSold +"\nD2|TextInfo02= " + sortedEntries.get(9).getKey() + " - " + 
						 ConvertToLakh(sortedEntries.get(9).getValue()) + " L" + " \nD3|TextInfo03= \nD4|Gap" ;
			 } 
		 }
		 
		if(is_this_updating == false) {
			if(data.equalsIgnoreCase("SOLD") && data2.equalsIgnoreCase("PURSE")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA " +pricesSold+"\nD1|TextInfo=" + "PURSE REMAINING" +Purse+";");
	
			}else if(data.equalsIgnoreCase("SOLD") && data2.equalsIgnoreCase("TOP")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA " +pricesSold+ "\nD1|TextInfo=" + "TOP BUYS" + TopSold+";");

			}else if(data.equalsIgnoreCase("SOLD") && data2.equalsIgnoreCase("NOTSELECTED")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA " +pricesSold+";");

			}else if(data.equalsIgnoreCase("PURSE") && data2.equalsIgnoreCase("NOTSELECTED")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "+ "D1|TextInfo=" + "PURSE REMAINING" +Purse+";");

			}else if(data.equalsIgnoreCase("PURSE") && data2.equalsIgnoreCase("TOP")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "+ "D1|TextInfo=" + "PURSE REMAINING" +Purse+ "\nD1|TextInfo=" + "TOP BUYS" + TopSold + ";");

			}else if(data.equalsIgnoreCase("PURSE") && data2.equalsIgnoreCase("SOLD")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "+ "D1|TextInfo=" + "PURSE REMAINING" +Purse+ "\n" + pricesSold + ";");

			}else if(data.equalsIgnoreCase("TOP") && data2.equalsIgnoreCase("NOTSELECTED")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "+ "D1|TextInfo=" + "TOP BUYS" + TopSold + ";");
				
			}else if(data.equalsIgnoreCase("TOP") && data2.equalsIgnoreCase("PURSE")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "+ "D1|TextInfo=" + "TOP BUYS" + TopSold+ "\nD1|TextInfo="+ "PURSE REMAINING"  +Purse+";");

			}else if(data.equalsIgnoreCase("TOP") && data2.equalsIgnoreCase("SOLD")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "+ "D1|TextInfo=" + "TOP BUYS" + TopSold + "\n" + pricesSold + ";");

			}	
		}
	}
	
//	public void populateCrawl(boolean is_this_updating,PrintWriter print_writer,String data,String data2, Auction auction,AuctionService auctionService,
//			List<Team> team, String session_selected_broadcaster) throws InterruptedException 
//	{
//		int remaining = 0, row = 0;
//		Map<String,  ArrayList<String>> prices = new HashMap<String, ArrayList<String>>();
//		Map<String,  Integer> topSold = new HashMap<String, Integer>();
//		Map<String,  Integer> remainingPurse = new HashMap<String, Integer>();
//		String name="";
//		List<Player> top_sold = new ArrayList<Player>();
//		
//		for(Player plyr : auction.getPlayers()) {
//			top_sold.add(plyr);
//		}
//		Collections.sort(top_sold,new AuctionFunctions.PlayerStatsComparator());
//		
//		for(int m=0; m<= top_sold.size() - 1; m++) {
//			if(top_sold.get(m).getSoldOrUnsold().equalsIgnoreCase("SOLD")) {
//				row = row + 1;
//	        	if(row <= 10) {
//	        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getTicker_name() != null) {
//	        			name = auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getTicker_name();
//					}else {
//						name = auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getFirstname();
//					}
//	        		
//	        		topSold.put(name,top_sold.get(m).getSoldForPoints());
//	        	}
//			}
//		}
//		 for(int i=0; i <= auction.getTeam().size()-1; i++) {
//			 ArrayList<String> list= new ArrayList<String>();
//			 remaining = Integer.valueOf(auction.getTeam().get(i).getTeamTotalPurse());
//				for(int j=0; j <= auction.getPlayers().size()-1; j++) {
//					if(auction.getPlayers().get(j).getSoldOrUnsold().equalsIgnoreCase("SOLD") &&
//							auction.getPlayers().get(j).getTeamId() == auction.getTeam().get(i).getTeamId()) {
//						list.add(auction.getPlayers().get(j).getFull_name());
//						remaining = remaining - auction.getPlayers().get(j).getSoldForPoints();
//					}
//				}
//				
//				remainingPurse.put(auction.getTeam().get(i).getTeamName1(), remaining);
//				if(!list.isEmpty()) {
//					prices.put(auction.getTeam().get(i).getTeamName1(), list);
//				}
//				remaining = 0;
//			}
//		 String TopSold="" ,Purse="" ,pricesSold="" ;
//		 
//		 for(int i=1;i<=prices.keySet().size();i++) {
//			 if(i==1) {
//				 pricesSold=pricesSold+ "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
//							String.join(",  ", prices.get((String) prices.keySet().toArray()[0])) + "\nD4|Gap";
//			 }else if(i==2) {
//				 pricesSold=pricesSold+ "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + 
//							String.join(",  ", prices.get((String) prices.keySet().toArray()[1])) + "\nD4|Gap";
//			 }else if(i==3) {
//				 pricesSold=pricesSold+ "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + 
//							String.join(",  ", prices.get((String) prices.keySet().toArray()[2])) + "\nD4|Gap";
//			 }else if(i==4) {
//				 pricesSold=pricesSold+ "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + 
//							String.join(",  ", prices.get((String) prices.keySet().toArray()[3])) + "\nD4|Gap";
//			 }else if(i==5) {
//				 pricesSold=pricesSold+ "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + 
//							String.join(",  ", prices.get((String) prices.keySet().toArray()[4])) + "\nD4|Gap";
//			 }else if(i==6) {
//				 pricesSold=pricesSold+ "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + 
//							String.join(",  ", prices.get((String) prices.keySet().toArray()[5])) + "\nD4|Gap";
//			 }
//		 }
//		 
//		 
//		 for(int i=1;i<=remainingPurse.keySet().size();i++) {
//			 if(i==1) {
//				 Purse = Purse+"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
//							ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[0])) + " L" + "\nD4|Gap"; 
//			 }else if(i==2) {
//				 Purse = Purse+"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + 
//							ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[1])) + " L" + "\nD4|Gap"; 
//			 }else if(i==3) {
//				 Purse = Purse+"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + 
//							ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[2])) + " L" + "\nD4|Gap";  
//			 }else if(i==4) {
//				 Purse = Purse+"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + 
//							ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[3])) + " L" + "\nD4|Gap";  
//			 }else if(i==5) {
//				 Purse = Purse+"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + 
//							ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[4])) + " L" + "\nD4|Gap"; 
//			 }else if(i==6) {
//				 Purse = Purse+"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + 
//							ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[5])) + " L" + "\nD4|Gap"; 
//			 }
//		 }
// 
//		 
//		 for(int i=1;i<=topSold.keySet().size();i++) {
//			 if(i==1) {
//				 TopSold =TopSold +"\nD2|TextInfo02= " + (String) topSold.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
//							ConvertToLakh(topSold.get((String) topSold.keySet().toArray()[0])) + " L" + "\nD4|Gap" ;
//			 }else if(i==2) {
//				 TopSold =TopSold +"\nD2|TextInfo02= " + (String) topSold.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + 
//							ConvertToLakh(topSold.get((String) topSold.keySet().toArray()[1])) + " L" + "\nD4|Gap" ; 
//			 }else if(i==3) {
//				 TopSold =TopSold +"\nD2|TextInfo02= " + (String) topSold.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + 
//							ConvertToLakh(topSold.get((String) topSold.keySet().toArray()[2])) + " L" + "\nD4|Gap" ; 
//			 }else if(i==4) {
//				 TopSold =TopSold +"\nD2|TextInfo02= " + (String) topSold.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + 
//							ConvertToLakh(topSold.get((String) topSold.keySet().toArray()[3])) + " L" + "\nD4|Gap" ; 
//			 }else if(i==5) {
//				 TopSold =TopSold +"\nD2|TextInfo02= " + (String) topSold.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + 
//							ConvertToLakh(topSold.get((String) topSold.keySet().toArray()[4])) + " L" + "\nD4|Gap" ; 
//			 }else if(i==6) {
//				 TopSold =TopSold +"\nD2|TextInfo02= " + (String) topSold.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + 
//							ConvertToLakh(topSold.get((String) topSold.keySet().toArray()[5])) + " L" + "\nD4|Gap" ; 
//			 }else if(i==7) {
//				 TopSold =TopSold +"\nD2|TextInfo02= " + (String) topSold.keySet().toArray()[6] + " :" + " \nD3|TextInfo03=" + 
//							ConvertToLakh(topSold.get((String) topSold.keySet().toArray()[6])) + " L" + "\nD4|Gap" ; 
//			 }else if(i==8) {
//				 TopSold =TopSold +"\nD2|TextInfo02= " + (String) topSold.keySet().toArray()[7] + " :" + " \nD3|TextInfo03=" + 
//							ConvertToLakh(topSold.get((String) topSold.keySet().toArray()[7])) + " L" + "\nD4|Gap" ; 
//			 }else if(i==9) {
//				 TopSold =TopSold +"\nD2|TextInfo02= " + (String) topSold.keySet().toArray()[8] + " :" + " \nD3|TextInfo03=" + 
//							ConvertToLakh(topSold.get((String) topSold.keySet().toArray()[8])) + " L" + "\nD4|Gap" ; 
//			 }else if(i==10) {
//				 TopSold =TopSold +"\nD2|TextInfo02= " + (String) topSold.keySet().toArray()[9] + " :" + " \nD3|TextInfo03=" + 
//							ConvertToLakh(topSold.get((String) topSold.keySet().toArray()[9])) + " L" + "\nD4|Gap" ;
//			 } 
//		 }
//		 
//		 if(is_this_updating == false) {
//			if(data.equalsIgnoreCase("SOLD") && data2.equalsIgnoreCase("PURSE")) {
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "+ "D1|TextInfo=" + "ISPL AUCTION 2024" +pricesSold+"\nD1|TextInfo=" + "REMAINING PURSE" +Purse+";");
//	
//			}else if(data.equalsIgnoreCase("SOLD") && data2.equalsIgnoreCase("TOP")) {
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "+ "D1|TextInfo="+ "ISPL AUCTION 2024" +pricesSold+ "\nD1|TextInfo=" +String.format("%20s","TOP BUYS")+TopSold+";");
//
//			}else if(data.equalsIgnoreCase("SOLD") && data2.equalsIgnoreCase("NOTSELECTED")) {
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "+ "D1|TextInfo="+ "ISPL AUCTION 2024" +pricesSold+";");
//
//			}else if(data.equalsIgnoreCase("PURSE") && data2.equalsIgnoreCase("NOTSELECTED")) {
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "+ "D1|TextInfo=" + "REMAINING PURSE" +Purse+";");
//
//			}else if(data.equalsIgnoreCase("PURSE") && data2.equalsIgnoreCase("TOP")) {
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "+ "D1|TextInfo=" + "REMAINING PURSE" +Purse+ "\nD1|TextInfo=" + String.format("%20s","TOP BUYS")+TopSold+";");
//
//			}else if(data.equalsIgnoreCase("PURSE") && data2.equalsIgnoreCase("SOLD")) {
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "+ "D1|TextInfo=" + "REMAINING PURSE" +Purse+ "\nD1|TextInfo=" + "ISPL AUCTION 2024"+pricesSold+";");
//
//			}else if(data.equalsIgnoreCase("TOP") && data2.equalsIgnoreCase("NOTSELECTED")) {
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "+ "D1|TextInfo="+ String.format("%20s","TOP BUYS")+TopSold+";");
//				
//			}else if(data.equalsIgnoreCase("TOP") && data2.equalsIgnoreCase("PURSE")) {
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "+ "D1|TextInfo=" +String.format("%20s","TOP BUYS")+TopSold+ "\nD1|TextInfo="+ "REMAINING PURSE"  +Purse+";");
//
//			}else if(data.equalsIgnoreCase("TOP") && data2.equalsIgnoreCase("SOLD")) {
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "+ "D1|TextInfo="+String.format("%20s","TOP BUYS")+TopSold+ "\nD1|TextInfo=" + "ISPL AUCTION 2024"+pricesSold+";");
//
//			}	
//		}
//	}
	
	public void populateFreeCrawl(boolean is_this_updating,PrintWriter print_writer, Auction auction,AuctionService auctionService,
			String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		if(new File("C:\\Sports\\Auction\\Free_Text\\Crawl_FreeText.txt").exists()) {
			String data = new String(Files.readAllBytes(Paths.get("C:\\Sports\\Auction\\Free_Text\\Crawl_FreeText.txt")));
			
			if(data != null && !data.isEmpty()) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "+ "D1|TextInfo=" + "ISPL 2024 "
						+ "\nD2|TextInfo02= " + data + " \nD3|TextInfo03= \nD4|Gap "
						+ "\nD1|TextInfo=ISPL 2024 \nD2|TextInfo02= " + data + " \nD3|TextInfo03= \nD4|Gap;");
			}else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "+ "D1|TextInfo=" + "ISPL 2024 "
						+ "\nD2|TextInfo02= \nD3|TextInfo03= \nD4|Gap;");
			}
		}else {
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "+ "D1|TextInfo=" + "ISPL 2024 "
					+ "\nD2|TextInfo02= \nD3|TextInfo03= \nD4|Gap;");
		}
	}
	public void populateRemainingPurse(boolean is_this_updating,PrintWriter print_writer,String viz_scene, Auction match,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		int row = 0,total = 0;
		
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 SIZE;");
		
		for(int i=0; i <= match.getTeam().size()-1; i++) {
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName0" + (i+1)+ " " + match.getTeam().get(i).getTeamName4() + ";");

			if(match.getPlayers() != null ) {
				for(int j=0; j <= match.getPlayers().size()-1; j++) {
					if(match.getPlayers().get(j).getTeamId() == match.getTeam().get(i).getTeamId()) {
						row = row + match.getPlayers().get(j).getSoldForPoints();
						total = total + 1;
					}
				}
			}
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + (i+1) + "A " + total + ";");
			
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPurse0" + (i+1) +" "+ 
					ConvertToLakh((Integer.valueOf(match.getTeam().get(i).getTeamTotalPurse()) - row)) + ";");
			//System.out.println("TEAM : "+match.getTeam().get(i).getTeamName4()+"\nTOTAL : "+total+"\nRS : "+ConvertToLakh((Integer.valueOf(match.getTeam().get(i).getTeamTotalPurse()) - row)));
			row = 0;
			total = 0;
		}
		
		

		
		if(is_this_updating == false) {
			TimeUnit.MILLISECONDS.sleep(200);
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 71.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.MILLISECONDS.sleep(200);
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
	}
	
	public void populateSquadAll(boolean is_this_updating,PrintWriter print_writer,String viz_scene, Auction match,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException {
		int total = 0;
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 " + "SQUAD" + ";");
		
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataType01 0;");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataType02 0;");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataType03 0;");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataType04 0;");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataType05 0;");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataType06 0;");
		
		for(int i=0; i <= match.getTeam().size()-1; i++) {
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName0" + (i+1) + " " + match.getTeam().get(i).getTeamName4() + ";");
			if(match.getPlayers() != null ) {
				for(int j=0; j <= match.getPlayers().size()-1; j++) {
					if(match.getPlayers().get(j).getTeamId() == match.getTeam().get(i).getTeamId()) {
						total = total + 1;
					}
				}
			}
			
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + (i+1) + "A " + total + ";");
			total = 0;
			
		}
		
		if(is_this_updating == false) {
			TimeUnit.MILLISECONDS.sleep(200);
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 70.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.MILLISECONDS.sleep(200);
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
	}

	public void populateRemainingPurseSingle(boolean is_this_updating,PrintWriter print_writer,String viz_scene,int team_id , Auction match,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		int row = 0;
		
		
		for(int j=0; j <= match.getPlayers().size()-1; j++) {
			if(match.getPlayers().get(j).getTeamId() == team_id) {
				row = row + match.getPlayers().get(j).getSoldForPoints();
			}
		}
		
		if(is_this_updating == false) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName " + 
					"REMAINING PURSE" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + 
					"PLAYER\nAUCTION 2024" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tUnsold " + 
					"PURSE REMAINING" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTotal " + 
					"100" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + logo_path + 
					match.getTeam().get(team_id-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
		}
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRemaining " + 
				ConvertToLakh((Integer.valueOf(match.getTeam().get(team_id-1).getTeamTotalPurse()) - row)) + ";");
		row = 0;
		
		
		if(is_this_updating == false) {
//			TimeUnit.MILLISECONDS.sleep(1000);
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 71.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
//			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
	}
	public void populateSquad(boolean is_this_updating,PrintWriter print_writer,String viz_scene,int team_id , Auction match,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		int row = 0;
		int remaining=0;
		if(is_this_updating == false) {
			for(int i = 1; i <= 18; i++) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$"+ i +"*CONTAINER SET ACTIVE 0;");
			}
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + 
					"PLAYER\nAUCTION 2024" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$8_to_14*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vAbove14 0;");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + logo_path + 
					auctionService.getTeams().get(team_id - 1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
			
		}
		remaining=Integer.valueOf(auctionService.getTeams().get(team_id - 1).getTeamTotalPurse());
		for(int j=0; j <= match.getPlayers().size()-1; j++) {
			if(match.getPlayers().get(j).getTeamId() == team_id) {
				row = row + 1;
				remaining=remaining-match.getPlayers().get(j).getSoldForPoints();
				if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getTicker_name() != null) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayername0"+ row + " " + 
							auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getTicker_name() + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayername0"+ row + " " + 
							auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getFirstname() + ";");
				}
				
				if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() - 1).getRole() != null &&
						!auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() - 1).getRole().isEmpty()) {
					
					if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() - 1).getRole().equalsIgnoreCase("BATSMAN")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0" + row + " " + 
								"BATTER" + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0" + row + " " + 
								auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() - 1).getRole().toUpperCase() + ";");
					} 	
		
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0" + row + " " + "-" + ";");
				}
				
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0"+ row + " " + auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() - 1).getRole() + ";");
				
				if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() - 1).getCategory().equalsIgnoreCase("FOREIGN")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInternational0" + row + " 1" + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInternational0" + row + " 0" + ";");
				}
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$"+ row +"*CONTAINER SET ACTIVE 1;");
				if(row >=8) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$8_to_14*CONTAINER SET ACTIVE 1;");
				}
				if(row >14) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vAbove14 1 ;");
				}
				
			}
		}
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRemainingValue " + ConvertToLakh(remaining) + ";");

		
		if(is_this_updating == false) {
			TimeUnit.MILLISECONDS.sleep(1000);
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 71.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
	}
	
	public void populateSquadRole(boolean is_this_updating,PrintWriter print_writer, int teamId, Auction auction,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		int batter=0,bowler=0,all_rounder=0,wicket_keeper = 0;
		if(auction.getPlayers() != null) {
			for(Player plyr : auction.getPlayers()) {
				if(plyr.getTeamId() == teamId) {	
					if(auctionService.getAllPlayer().get(plyr.getPlayerId()-1).getRole().toUpperCase().equalsIgnoreCase("BATSMAN")) {
						batter++;
					}else if(auctionService.getAllPlayer().get(plyr.getPlayerId()-1).getRole().toUpperCase().equalsIgnoreCase("WICKET-KEEPER")) {
						wicket_keeper++;
					}else if(auctionService.getAllPlayer().get(plyr.getPlayerId()-1).getRole().toUpperCase().equalsIgnoreCase("BOWLER")) {
						bowler++;
					}else if(auctionService.getAllPlayer().get(plyr.getPlayerId()-1).getRole().toUpperCase().equalsIgnoreCase("ALL-ROUNDER")) {
						all_rounder++;
					}
				}
			}
		}
		
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataType01 0;");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataType02 0;");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataType03 0;");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataType04 0;");
		
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 " + 
				auctionService.getTeams().get(teamId - 1).getTeamName4() + ";");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 " + 
				"" + ";");
		
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName01 " + 
				"BATTER" + ";");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName02 " + 
				"WICKET-KEEPER" + ";");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName03 " + 
				"BOWLER" + ";");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName04 " + 
				"ALL-ROUNDER" + ";");
		
		
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01A " + 
				batter + ";");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02A " + 
				wicket_keeper + ";");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03A " + 
				bowler + ";");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue04A " + 
				all_rounder + ";");
		
		if(is_this_updating == false) {
			TimeUnit.MILLISECONDS.sleep(200);
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 71.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.MILLISECONDS.sleep(200);
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
		
	}
	
	public void populateZone(boolean is_this_updating,PrintWriter print_writer,String viz_scene,int team_id , Auction match,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		int row = 0, East = 0,North = 0,West = 0,South = 0, Central = 0, U19 = 0;
		
		if(is_this_updating == false) {
			
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 " + 
					"" + ";");
			
		}
		
		if(match.getPlayers() != null) {
			for(int j=0; j <= match.getPlayers().size()-1; j++) {
				if(match.getPlayers().get(j).getTeamId() == team_id) {
					row = row + 1;
					if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getCategory().equalsIgnoreCase("North Zone")) {
						North = North + 1;
					}else if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getCategory().equalsIgnoreCase("East Zone")) {
						East = East + 1;
					}else if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getCategory().equalsIgnoreCase("West Zone")) {
						West = West + 1;
					}else if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getCategory().equalsIgnoreCase("Central Zone")) {
						Central = Central + 1;
					}else if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getCategory().equalsIgnoreCase("U19")) {
						U19 = U19 + 1;
					}else if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getCategory().equalsIgnoreCase("South Zone")) {
						South = South + 1;
					}
					
				}
			}
		}
		
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 " + 
				auctionService.getTeams().get(team_id - 1).getTeamName4() + ";");
		
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataType01 0;");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataType02 0;");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataType03 0;");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataType04 0;");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataType05 0;");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataType06 0;");

		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName01 " + 
				"EAST ZONE" + ";");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName02 " + 
				"WEST ZONE" + ";");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName03 " + 
				"NORTH ZONE" + ";");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName04 " + 
				"SOUTH ZONE" + ";");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName05 " + 
				"CENTRAL ZONE" + ";");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName06 " + 
				"U19" + ";");
		
		
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01A " + 
				East + ";");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02A " + 
				West + ";");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03A " + 
				North + ";");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue04A " + 
				South + ";");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue05A " + 
				Central + ";");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue06A " + 
				U19 + ";");
		
		if(is_this_updating == false) {
			TimeUnit.MILLISECONDS.sleep(200);
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 71.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.MILLISECONDS.sleep(200);
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
	}
	
	public void populatePurseRemain(boolean is_this_updating,PrintWriter print_writer,String viz_scene , Auction match,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		
		int row = 0;
		
		if(is_this_updating == false) {
			
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 " + 
					"PURSE REMAINING" + ";");
			
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 " + 
					"" + ";");
		}
		
		for(int i=0; i <= match.getTeam().size()-1; i++) {
			for(int j=0; j <= match.getPlayers().size()-1; j++) {
				if(match.getPlayers().get(j).getTeamId() == match.getTeam().get(i).getTeamId()) {
					row = row + match.getPlayers().get(j).getSoldForPoints();
					
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName0" + (i+1) + " " + 
							auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName4() + ";");
				}
			}
			
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataType0" + (i+1) + " 1" + ";");

			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPurse0" + (i+1) + " " + 
					ConvertToLakh((Integer.valueOf(match.getTeam().get(i).getTeamTotalPurse()) - row)) + ";");
			row = 0;
		}
		
		if(is_this_updating == false) {
			TimeUnit.MILLISECONDS.sleep(200);
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 71.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.MILLISECONDS.sleep(200);
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
	}
	
	public void AnimateInGraphics(PrintWriter print_writer, String whichGraphic) throws InterruptedException
	{
		
		switch(whichGraphic) {
		case "FFPLAYERPROFILE":	
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			break;
		
		case "RESET":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Reset START \0");
			break;
		
		}	
	}	
	public void AnimateOutGraphics(PrintWriter print_writer, String whichGraphic)
	{
		switch(whichGraphic.toUpperCase()) {
		
		case "FFPLAYERPROFILE":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out START \0");
			break;
		
		}	
	}
	
	public void processAnimation(PrintWriter print_writer, String animationName,String animationCommand, String which_broadcaster,int which_layer)
	{
		switch(which_broadcaster.toUpperCase()) {
		case "HANDBALL":  case "ISPL":
			switch(which_layer) {
			case 1:
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
				break;
				
			case 2:
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
				//print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*LOOP START;");	
				break;
			}
			break;
		}
		
	}
	
	public String toString() {
		return "Doad [status=" + status + ", slashOrDash=" + slashOrDash + "]";
	}
	
	public static String ConvertToLakh(double num) {
		String str=String.format("%.2f", num/100000);
		if(str.contains(".")) {
			if(str.endsWith(".00")) {
				return  str.substring(0, str.length()-3) ;
			}else {
				return  str;
			}
		}
		return str;
	}
	
	public static String getBowlerType(String BowlerType) {
		switch(BowlerType.toUpperCase()) {
		case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
			return "PACE";
		case "ROB": case "RLB": case "RLG": case "WSR": case "LSL": case "WSL": case "LCH":  case "LSO":
			return "SPIN";
		}
		return "";
	}
}