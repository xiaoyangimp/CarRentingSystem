package cs.constant;


/**
*
* @author Jiyu Xiao
*/
public class Translate {
	
	/** function that used to get the first several integer
	 * at the beginning of a string
	 * */
	public static int SToI (String instring) {
		int temp = 0;
		int index = 0;
		
		while(index < instring.length() && instring.charAt(index) >= '0' && instring.charAt(index) <= '9') {
			temp *= 10;
			temp += instring.charAt(index) - '0';
			index++;
		}
		
		return temp;
	}
	
	/**function used to valid a string contains only none negative number*/
	public static boolean SValid (String instring) {
		int index = 0;
		
		while(index < instring.length() ) {
			if ( instring.charAt(index) < '0' || instring.charAt(index) > '9') return false;
			index++;
		}
		
		return true;
	}
	

}
