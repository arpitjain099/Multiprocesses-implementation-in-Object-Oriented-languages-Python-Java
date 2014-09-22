

public class Test {
	interface VehicleInterface {
        void collide(Vehicle v);
    }
	
	static class Vehicle implements VehicleInterface {
		public void collide (Vehicle v){
			System.out.println( "vehicle is called" );
		}

		
			
	}
	
	
	static class Bike extends Vehicle{
		void collide( Bike v ){
			System.out.println( "Bike collides with bike" );}
		public void collide( Truck t ) {
		      System.out.println( "Bike collides with Truck" ); }
		public void collide( Car c ) {
		      System.out.println( "Bike collides with car" ); }
	}
		
		 
	static class Car extends Vehicle {
		   
		   public void collide( Bike b ) {
		      System.out.println( "Car collides with Bike" ); }
		   public void collide( Truck t ) {
		      System.out.println( "Car collides with Truck" ); }
		   public void collide( Car c ) {
		      System.out.println( "Car collides with car" ); }
		}
	static class Truck extends Vehicle {
		
		   public void collide( Bike b ) {
		      System.out.println( "Truck collides with Bike" ); }
		   public void collide( Truck t ) {
		      System.out.println( "Truck collides with Truck" ); }
		   public void collide( Car c ) {
		      System.out.println( "Truck collides with Car" ); }
		}



	
	public static void main(String[] args) {
		Bike b = new Bike();
		VehicleInterface v =  Dispatch.using(VehicleInterface.class, b);
		   v.collide(new Truck());
		Vehicle c = new Bike();
		Multimethod.call( void.class, new Truck(), "collide", new Car());
		
	}

	}


