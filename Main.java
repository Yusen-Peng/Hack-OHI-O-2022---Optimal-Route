import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
public class Main{
 
    public static Place findClosest(List<Place> list, Person person){
        Place nextPlace = null;
        int minDistance  = Integer.MAX_VALUE;
        for(Place p : list){
            int distance = (person.getXPos()-p.getX())*(person.getXPos()-p.getX())+(person.getYPos()-p.getY())*(person.getYPos()-p.getY());
            if(distance < minDistance){
                minDistance = distance;
                nextPlace = p;
            }
        }
        return nextPlace;
    }
   
   public static int goNext(Person person, String[][] grid, int distance, Place place){
     int actualRowPerson = grid.length - person.getYPos();
	   int actualColPerson = person.getXPos()-1;
	   int actualRowDes = grid.length - place.getY();
	   int actualColDes = place.getX()-1;

     
	   grid[actualRowDes][actualColDes] = "D ";
     
	   if(actualColPerson <= actualColDes){
	       for(int i = actualColPerson+1; i < actualColDes; i++){
		       grid[actualRowPerson][i] = "->";
		        distance++;
	       }
	   }else{
	       for(int i = actualColPerson-1; i > actualColDes; i--){
		       grid[actualRowPerson][i] = "<-";
		       distance++;
	       }
	   }
	   if(actualRowPerson >= actualRowDes){
	       for(int i = actualRowPerson; i > actualRowDes; i--){
		       grid[i][actualColDes] = "| ";
		       distance++;
	        }
	   }else{
	       for(int i = actualRowPerson; i < actualRowDes; i++){
		       grid[i][actualColDes] = "| ";
		       distance++;
	        }
	   }
     
	   person.setXPos(place.getX());
	   person.setYPos(place.getY());
	   distance++;
	   return distance;
   }
   
	public static void main(String[] args) {
	    int distance = 0;
	    String[][] grid = new String[40][40];
	    for(int i = 0; i < grid.length; i++){
	       for(int j = 0; j < grid.length; j++){
	           grid[i][j] = ". ";
	       }
	    }
	    
	    Scanner scnr = new Scanner(System.in);
	    
	    System.out.print("where are you right now？(x,y coordinates separated by a space)");
	    String[] xAndY = scnr.nextLine().split(" ");
	    int x = Integer.parseInt(xAndY[0]);  
	    int y = Integer.parseInt(xAndY[1]);
    
	    System.out.print("Do you have a scooter? (yes or no)");
	    String reply = scnr.nextLine();
    
	    boolean scooterRocks = true;
	    double velocity = 1.0;
	    if(reply.equalsIgnoreCase("yes")){
	        scooterRocks = true;
	        velocity *= 5;
	    }else{
	        scooterRocks = false;
	    }
	    Person person = new Person(x,y,scooterRocks,velocity);
	    System.out.println(person);
	    
	    
	    List<Place> places = new ArrayList<>();
	    
	    while(true){
	        System.out.print("what's your destination name?(enter a blank line to exit)");
	        String st = scnr.nextLine();
	        if(st.equals("")){
	            System.out.println("You are all set!");
	            break;
	        }
	        System.out.print("where is your destination?(x,y coordinates separated by a space)");
	        xAndY = scnr.nextLine().split(" ");
	        x = Integer.parseInt(xAndY[0]);
	        y = Integer.parseInt(xAndY[1]);
	        Place place = new Place(st,x,y);
	        System.out.println(place);
	        places.add(place);
	    }
	        
	        
	    System.out.println(places);    
	    int actualRowPerson = grid.length - person.getYPos();
	    int actualColPerson = person.getXPos()-1;
		  grid[actualRowPerson][actualColPerson] = "U!";
	    
	    
	    
	    while(!places.isEmpty()){
	        Place nextOne = findClosest(places,person);    
	        distance = goNext(person,grid,distance,nextOne);    
	        places.remove(nextOne);
	    }

		for(String[] sArr : grid){
		    for(String s : sArr){
		        System.out.print(s);
		    }
		    System.out.println();
		}
    
		System.out.println("you travel " + distance + " meters to get to your destination");
		System.out.println("you will spend " + distance/person.getSpeed() + " seconds to reach there");
    scnr.close();
	}
}


class Place{
    private String name;
    private int xP;
    private int yP;
    
    public Place(String s, int x, int y){
        name = s;
        xP = x;
        yP = y;
    }
    
    
    public String getName(){
        return name;
    }
    
    public int getX(){
        return xP;
    }
    public int getY(){
        return yP;
    }
    @Override
    public String toString(){
        return name + ": at position(" + xP + "," + yP + ")";
    }
    
}


class Person{
    private int xPos;
    private int yPos;
    private boolean hasScooter;
  
    private double speed;
    
    
    public Person(int x, int y, boolean scooter,double s){
        xPos = x;
        yPos = y;
        hasScooter = scooter;
        speed = s;
    }
    
    public int getXPos(){
        return xPos;
    }
    
    public int getYPos(){
        return yPos;
    }
    public double getSpeed(){
        return speed;
    }
    public void setXPos(int x){
        xPos = x;
    }
    
    public void setYPos(int y){
        yPos = y;
    }
    
    @Override
    public String toString(){
        return "You are at (" + xPos + "," + yPos + ") position and your speed is " + speed;
    }
}