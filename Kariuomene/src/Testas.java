import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

abstract class Soldier {
	
	int soldierID;
	String soldierName;
	Random IDgen = new Random();
	public int getRandomID()
	{
		return 100000+IDgen.nextInt(100000);
	}
//random name gen.
//------------------------------------------------------------------------------------------------------
	public static String[] Beginning = { "Kr", "Ca", "Ra", "Mrok", "Cru",
	         "Ray", "Bre", "Zed", "Drak", "Mor", "Jag", "Mer", "Jar", "Mjol",
	         "Zork", "Mad", "Cry", "Zur", "Creo", "Azak", "Azur", "Rei", "Cro",
	         "Mar", "Luk" };
	   private static String[] Middle = { "air", "ir", "mi", "sor", "mee", "clo",
	         "red", "cra", "ark", "arc", "miri", "lori", "cres", "mur", "zer",
	         "marac", "zoir", "slamar", "salmar", "urak" };
	   private static String[] End = { "d", "ed", "ark", "arc", "es", "er", "der",
	         "tron", "med", "ure", "zur", "cred", "mur" };
	   
	   
	   private static Random rand = new Random();
	   public static String generateName() {
		   

	      return Beginning[rand.nextInt(Beginning.length)] + 
	            Middle[rand.nextInt(Middle.length)]+
	            End[rand.nextInt(End.length)];
	   }
 //------------------------------------------------------------------------------------------------------
	public Soldier(){
		
		this.soldierName = generateName();
		this.soldierID = getRandomID();
	}
	
	abstract int monthlyPay ();
	abstract String soldiersInfo();
}
//------------------------------------------------------------------------------------------------------
class PrivateS extends Soldier{

	public int tariff; //abstract calc
	public int timeInArmy; //abstract calc
	public int penaltyQuant; //inner calc
	public int pushUps; //inner calc
	
	 Random gen = new Random();
		{
			timeInArmy = gen.nextInt(100-0+1);
			penaltyQuant = gen.nextInt(40-0+1);
			tariff = 2;
			pushUps = 100;
		}
		
	 public PrivateS(){
		 super();
	 }
	 
	public int totalPunishment() {
		 int quant = 0;
		 if(penaltyQuant <= 10) {
			 quant = penaltyQuant *pushUps;
		 }
		 else if(penaltyQuant > 10) {
			quant = penaltyQuant * (pushUps*2);
		}
		else if(penaltyQuant > 20){
			quant = penaltyQuant * (pushUps*3);
		}
		else if(penaltyQuant > 25) {
			quant = penaltyQuant * (pushUps*5);
		}
		return quant;
	}
	 
	public int monthlyPay() {	//abstract method overwriting
		int pay = timeInArmy * tariff;
		return pay;
	}
	
	
	public String soldiersInfo() { //abstract method overwriting
		return String.format("  ID: %d,  "
				+ "tarifas: %d, "
				+ "laikaskariuomeneje: %d, "
				+ "vardas: %s, "
				+ " alga:  %d, "
				+ "atsispaudimai: %d"
				, soldierID, tariff, timeInArmy, soldierName, monthlyPay(), totalPunishment());
	}
}
//------------------------------------------------------------------------------------------------------
class Captain extends Soldier{
	
	public int tariff;
	public int timeInArmy;
	public int bonus;

	 Random gen = new Random();
		{
			timeInArmy = gen.nextInt(100-0+1);
			bonus = gen.nextInt(220-0+1);
			tariff = 4;
		}
		
	 public Captain(){
		 super();
	 }
	public int monthlyPay() {
		int pay = (timeInArmy * tariff) + bonus;
		return pay;
	}
	public String soldiersInfo() {
		return String.format(" Uzkolojama reiksme ID: %d,  "
				+ "tarifas: %d, "
				+ "bonusas: %d, "
				+ "laikaskariuomeneje: %d, "
				+ "vardas: %s, "
				+ "alga:  %d", soldierID, tariff, bonus, timeInArmy, soldierName, monthlyPay());
	}
}
//------------------------------------------------------------------------------------------------------
class AllSoldiers extends ArrayList<Soldier>{
	
	int allSoldierCount;
	int biggestPay = 1;
	public int sumOfAllSoldiers;
	int mostPushUps = 0;

	public AllSoldiers(int soldierCount){
		allSoldierCount = soldierCount;
	}
	
	int evaluateBiggestMnthPay() {
		for(int i = 0; i < allSoldierCount; i++) {    //cia nezinau kaip pasiimti ta reiksme is allSoldiersCount
			get(i).monthlyPay();
			if(get(i) instanceof PrivateS) {
				if(((PrivateS)get(i)).monthlyPay() > biggestPay) {
					biggestPay = ((PrivateS)get(i)).monthlyPay();
				}
			}
			else if (get(i) instanceof Captain) {
				if(((Captain)get(i)).monthlyPay() > biggestPay) {
					biggestPay = ((Captain)get(i)).monthlyPay();
				}
			}
	}
		return biggestPay;
	}
	

	
	int mostPushUpsDone() {
		for(int i = 0; i < size(); i++) {
			
			if(get(i) instanceof PrivateS) {
				mostPushUps = ((PrivateS)get(i)).totalPunishment();
			}
	}
		return mostPushUps;
	}
}
//------------------------------------------------------------------------------------------------------
class DifferenceBetweenMostEarningSoldiers implements Comparator <Batallion>{

	@Override
    public int compare(Batallion arg0, Batallion arg1) {
        
        return (arg0.allsoldiers).evaluateBiggestMnthPay() - (arg1.allsoldiers).evaluateBiggestMnthPay();
    }
}
class MostPushUpsComperator implements Comparator <Batallion>{

	@Override
    public int compare(Batallion arg0, Batallion arg1) {
        
        return (arg0.allsoldiers).mostPushUpsDone() - (arg1.allsoldiers).mostPushUpsDone();
    }
}
//------------------------------------------------------------------------------------------------------
class Batallion{
	
	int soldiercount;
	AllSoldiers allsoldiers;
	Random rnd = new Random();
	int batallionID;
	
	public static void printLine() {
		 System.out.println("------------------------------------------------------------------------------------------------");
	}
	public Batallion(int id, int soldierCount){
		this.batallionID = id;
		this.soldiercount = soldierCount;
		allsoldiers = new AllSoldiers(soldiercount);
		
		for(int i = 0; i < soldierCount; i ++) {
			int randomSoldier;
			randomSoldier = rnd.nextInt(2);
			if(randomSoldier == 0) {
				allsoldiers.add(new PrivateS());
			}else {
				allsoldiers.add(new Captain());
			}
		}
	}
	
	public void showAllSoldiers() {
		printLine();
		for(Soldier temp:allsoldiers) {System.out.println(temp);}
		printLine();
		for(Soldier temp: allsoldiers) {System.out.println(temp.soldiersInfo());}
		printLine();
	}
}
//------------------------------------------------------------------------------------------------------
class Legion extends ArrayList<Batallion>{
	
	Random rnd = new Random();
	public Legion() {
		for(int i = 0; i < 5; i++) {
			int randomSoldierCount = rnd.nextInt(10-0+1);
			this.add(new Batallion(i, randomSoldierCount));
		}
	}
	void sortBatallionsByMostEarningSoldier() {
        Collections.sort(this, new DifferenceBetweenMostEarningSoldiers());
    }
	void sortBattalionByMostPushUps() {
        Collections.sort(this, new MostPushUpsComperator());
    }
	
//	void sortAllSoldiersPay() {
//  Collections.sort(this, new DifferenceBetweenMostEarningSoldiers());
//}
}
//------------------------------------------------------------------------------------------------------
public class Testas {
	
	public static void main(String[] args) {
		Legion myBigArmy = new Legion();
//		for(Batallion bat:myBigArmy)   
//	    {
//	        System.out.println(bat.batallionID + ": " + bat.allsoldiers.biggestPay );
//	       
//	    }
		
	    System.out.println("----------------------");
	    myBigArmy.sortBatallionsByMostEarningSoldier();
	    for(Batallion bat:myBigArmy)   
	    {
	        System.out.println(bat.batallionID + ": Didziausias atlyginimas - " + bat.allsoldiers.biggestPay);
	    }
	    
	    System.out.println("----------------------");
	    myBigArmy.sortBattalionByMostPushUps();
	    for(Batallion bat:myBigArmy)   
	    {
	        System.out.println(bat.batallionID + ": Daugiausiai atsispaudimu - " + bat.allsoldiers.mostPushUps);
	    }
	    	
	    for(Batallion bat:myBigArmy)   
	    {
	    	System.out.println(bat.batallionID);
	    	System.out.println("----------------------");
	    	bat.showAllSoldiers();
	    } 
	}
}