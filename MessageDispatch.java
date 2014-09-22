
public class MessageDispatch {
	
	static class Vehicle {
		void collide( Vehicle v ){
			 
		 }
		 void collide2( Bike v ){
			 
		 }
		 void collide2( Car v ){
			 
		 }
		 void collide2( Truck v ){
			 
		 }
		
	}

	static class Bike extends Vehicle {
		   public void collide( Vehicle v ) { v.collide2( this ); }
		   public void collide2( Bike b ) {
		      System.out.println( "Bike collides with bike" ); }
		   public void collide2( Truck t ) {
		      System.out.println( "Truck collides with Bike" ); }
		   public void collide2( Car c ) {
		      System.out.println( "Car collides with Bike" ); }
		}
	
	static class Car extends Vehicle {
		   public void collide( Vehicle v ) { v.collide2( this ); }
		   public void collide2( Bike b ) {
		      System.out.println( "Bike collides with car" ); }
		   public void collide2( Truck t ) {
		      System.out.println( "Truck collides with car" ); }
		   public void collide2( Car c ) {
		      System.out.println( "Car collides with car" ); }
		}
	
	
	static class Truck extends Vehicle {
		   public void collide( Vehicle v ) { v.collide2( this ); }
		   public void collide2( Bike b ) {
		      System.out.println( "Bike collides with Truck" ); }
		   public void collide2( Truck t ) {
		      System.out.println( "Truck collides with Truck" ); }
		   public void collide2( Car c ) {
		      System.out.println( "Car collides with Truck" ); }
		}
	
	public static void main(String[] args) {
		Vehicle array[] = { new Car(), new Bike(), new Truck() };
		   for (int i=0; i < array.length; i++)
		      for (int j=0; j < 3; j++)
		         array[i].collide( array[j] );

	}

}
