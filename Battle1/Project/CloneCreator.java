package assets.Prototype;

public class CloneCreator {
	public static void main(String []args){
		
		System.out.println("This program creates walrus prototypes.");
		System.out.println();
		
		CloneCreator prototyper = new CloneCreator();
		Walrus paddles = new Walrus();
		Walrus selddap = (Walrus)prototyper.getClone(paddles);
	
		cloningTest ct = new cloningTest(paddles, selddap);
		
	}
	
	public Mammal getClone(Mammal parent){
		return parent.makeClone();
	}
}
