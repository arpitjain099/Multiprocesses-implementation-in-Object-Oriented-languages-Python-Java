


	public class Typesafe {
	    
	    public static void typePrinterTest() {
	        TypePrinter typePrinter = Dispatch.using(TypePrinter.class, new TypePrinterImplementation());
	        System.out.println(typePrinter.typeOf(42));
	        System.out.println( typePrinter.typeOf("foo"));
	        System.out.println( typePrinter.typeOf(3.14));
	    }

	    interface TypePrinter {
	        public String typeOf(Object value);
	    }

	    static class TypePrinterImplementation implements TypePrinter {
	        public String typeOf(Integer value) {
	            return "It's an integer";
	        }

	        public String typeOf(String value) {
	            return "It's a string";
	        }

	        public String typeOf(Object value) {
	            return "It's unknown";
	        }
	    }
	    public static void main(String[] args){
	    	typePrinterTest();
	    }
	}
	
	
	
	
	
	
	
	
	
	


