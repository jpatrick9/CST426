package assets.Prototype;

public class Walrus implements Mammal{
	
	public Walrus(){
		System.out.println("Made walruses.");
		System.out.println();
	}
	
	public Mammal makeClone(){
		
		System.out.println("Displaying mammal types...");
		Walrus walrus = null;
		
		try{
			walrus = (Walrus)super.clone();
		}
		
		catch(CloneNotSupportedException e){
			System.out.println("Walrus creation interrupted.");
		}
		
		return walrus;
	}
	
	public String toString(){
		return "Walrus";
	}
}
