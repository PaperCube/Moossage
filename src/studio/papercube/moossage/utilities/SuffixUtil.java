package studio.papercube.moossage.utilities;

public class SuffixUtil {
	public static int toMillis(String formula){
		String pure = formula.trim();
		String suffix = String.valueOf(pure.charAt(pure.length()-1)).toLowerCase();
		String numberString = pure.substring(0, pure.length()-1);
		
		if(pure.endsWith("ms")){
			try{
				return Integer.parseInt(pure.substring(0,pure.length()-2));
			}catch(NumberFormatException e){
				throw new InvalidSuffixException(e);
			}
		}
		
		try{
			double baseNum = Double.parseDouble(numberString);
			
			switch(suffix){
			case "s":
				return (int)(baseNum*1000);
			case "m":
				return (int)(baseNum*1000*60);
			case "h":
				return (int)(baseNum * 1000 * 60 * 60);
			default:
				return Integer.parseInt(pure);
			}
		}catch(NumberFormatException e){
			throw new InvalidSuffixException(e);
		}
		
	}
	
	public static int toTimes(String formula){
		String pure = formula.trim();
		String suffix = String.valueOf(pure.charAt(pure.length()-1)).toLowerCase();
		String numberString = pure.substring(0, pure.length()-1);
		
		
		try{
			double baseNum = Double.parseDouble(numberString);
			
			switch(suffix){
			case "k":
				return (int)(baseNum*1000);
			case "m":
				return (int)(baseNum*1000*1000);
			default:
				return Integer.parseInt(pure);
			}
		}catch(NumberFormatException e){
			throw new InvalidSuffixException(e);
		}
	}
	
	public static class InvalidSuffixException extends RuntimeException{

		private static final long serialVersionUID = 5881490402039607656L;

		public InvalidSuffixException() {
			super();
		}

		public InvalidSuffixException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
			super(arg0, arg1, arg2, arg3);
		}

		public InvalidSuffixException(String arg0, Throwable arg1) {
			super(arg0, arg1);
		}

		public InvalidSuffixException(String arg0) {
			super(arg0);
		}

		public InvalidSuffixException(Throwable arg0) {
			super(arg0);
		}
		
	}
}
