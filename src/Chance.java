import java.util.Random;

public class Chance {
	private static Random rgen = new Random();
	
	/*
	 * Returns a value within two bounds
	 * 
	 * @param low: the lower bound of the possible return value
	 * @param high: the upper bound of the possible return value
	 * @return double
	 */
	public static double range (double low, double high) {
		return rgen.nextDouble(high - low) + low;
	}
	
	/*
	 * Returns a value within two bounds. Same thing as the other one but as an int now
	 * 
	 * @param low: the lower bound of the possible return value
	 * @param high: the upper bound of the possible return value
	 * @return int
	 */
	public static int range (int low, int high) {
		return rgen.nextInt(high+1 - low) + low;
	}
	
	/*
	 * Has a chance to return true and a chance to return false
	 * 
	 * @param percentage: A double between 0.0 and 1.0
	 * @return True or False at random
	 */
	public static boolean coinflip (double percentage) {
		return rgen.nextDouble() < percentage;
	}
	
	/*
	 * 
	 */
	public static String choose (String[] options) {
		int i = rgen.nextInt(options.length);
		return options[i];
	}
}
