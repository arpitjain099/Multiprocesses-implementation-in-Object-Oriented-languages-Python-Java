/*
 * This essentially the same example as MessageDispatch.java but with one more class scooter. 
 * Adding one more class and its methods require us to modify code at many places which might cause errors
 */
public class DoubleDispatch {
	
	static class Vehicle {
		void collide( Vehicle v ){
			 
		 }
		 void collide2( Bike v ){
			 
		 }
		 void collide2( Car v ){
			 
		 }
		 void collide2( Truck v ){
			 
		 }
		 void collide2(Scooter v){
			 
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
		   public void collide2( Scooter s ) {
			      System.out.println( "Scooter collides with Bike" ); }
		}
	
	static class Car extends Vehicle {
		   public void collide( Vehicle v ) { v.collide2( this ); }
		   public void collide2( Bike b ) {
		      System.out.println( "Bike collides with car" ); }
		   public void collide2( Truck t ) {
		      System.out.println( "Truck collides with car" ); }
		   public void collide2( Car c ) {
		      System.out.println( "Car collides with car" ); }
		   public void collide2( Scooter s ) {
			      System.out.println( "Scooter collides with car" ); }
		}
	
	
	static class Truck extends Vehicle {
		   public void collide( Vehicle v ) { v.collide2( this ); }
		   public void collide2( Bike b ) {
		      System.out.println( "Bike collides with Truck" ); }
		   public void collide2( Truck t ) {
		      System.out.println( "Truck collides with Truck" ); }
		   public void collide2( Car c ) {
		      System.out.println( "Car collides with Truck" ); }
		   public void collide2( Scooter s ) {
			      System.out.println( "Scooter collides with Truck" ); }
		}
	
	static class Scooter extends Vehicle {
		   public void collide( Vehicle v ) { v.collide2( this ); }
		   public void collide2( Bike b ) {
		      System.out.println( "Bike collides with Scooter" ); }
		   public void collide2( Truck t ) {
		      System.out.println( "Truck collides with Scooter" ); }
		   public void collide2( Car c ) {
		      System.out.println( "Car collides with Scooter" ); }
		   public void collide2( Scooter s ) {
			      System.out.println( "Scooter collides with Scooter" ); }
		}
	
	public static void main(String[] args) {
		Vehicle array[] = { new Car(), new Bike(), new Truck(), new Scooter() };
		   for (int i=0; i < array.length; i++)
		      for (int j=0; j < 4; j++)
		         array[i].collide( array[j] );

	}

}
