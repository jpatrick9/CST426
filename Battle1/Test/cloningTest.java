package assets.Prototype;

public class cloningTest {
	public cloningTest(Walrus paddles, Walrus selddap){
		
		System.out.print("Object type of Paddles: ");
		System.out.println(paddles);
		System.out.print("Object type of Selddap: ");
		System.out.println(selddap);
		System.out.println();
		
		System.out.println("Mammal type identical!");
		System.out.println();
		
		System.out.println("Displaying address of each...");
		System.out.println("Hashcode of Paddles the Walrus: " +
				System.identityHashCode(System.identityHashCode(paddles)));
		
		System.out.println("Hashcode of Selddap the Walrus: " +
				System.identityHashCode(System.identityHashCode(selddap)));
		
		System.out.println();
		System.out.println("Object addresses not identical!");
		
	
	}
}
