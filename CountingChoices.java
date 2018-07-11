import java.io.*;
import java.util.*;


/**
 * Counts (n/k) up to a Long.MAX_VALUE. Uses prime factorisation to prevent premature
 * overflows.
 * @author Joseph McManamon - 6021556
 * @author Jayde Medder
 */
public class CountingChoices {
	private static ArrayList<Long> numeratorLongs = new ArrayList<Long>();
	private static ArrayList<Long> factorOfDLongs = new ArrayList<Long>();

	public static void main(String[] args){
		ArrayList<Long> numerators = new ArrayList<Long>();
		ArrayList<Long> denominators = new ArrayList<Long>();

		Long n = Long.parseLong(args[0]);
		Long k = Long.parseLong(args[1]);
		/* Ints for initial manipulation (it's just easier). */
		//int intN = Integer.parseInt(args[0]);
		//int intK = Integer.parseInt(args[1]);

		/* Checks. */
		if (k >= n){
			System.out.println("1");
			return;
		} 

		/* Get the denominators and numerators. */
		numeratorLongs = nFactorials(n, k);
		denominators = kFactorials(n, k);

		//System.out.println(numerators + " : " + denominators);

		/* Get prime factors of denominators. */
		for(Long i : denominators){
			factorOfDLongs.addAll(primeFactors(i));
		}
		System.out.println(numeratorLongs);

		System.out.println(factorOfDLongs);

		/* Conversion to Longs as ints only used up until now. 
		Set them in the global variable */
		//convertNumerators(numerators);
		//convertFactors(factorsOfD);

		simplify();

		try {
		calculateAndPrint(numeratorLongs);		
		} catch (Exception e){
			System.out.println("Long overflow");
		}	

		//Long l = numeratorLongs/factorLongs;
		//System.out.println(l);

	}
		
	/**
	 * Get prime factors.
	 * @param number we want to find prime factors of.
	 */
	public static ArrayList<Long> primeFactors(Long number) {

		return primeFactors(number, 2L);
	}

	private static ArrayList<Long> primeFactors(Long num, Long factor) {
     if (num == 1L){ 
		//System.out.println(factor);     	
     	return new ArrayList<Long>();
     }
     if (num % factor == 0L) {
         ArrayList<Long> factors = primeFactors(num/factor, factor);
         factors.add(factor);
         return factors;
     } else
         return primeFactors(num, factor+1L);
     }




     public static void simplify() {

		for (int i = 0; i < numeratorLongs.size(); i++) {
			for (int j = 0; j < factorOfDLongs.size(); j++) {

				/* Condition met when we have a factor of the current numerator
				 (also goes through when the denominator has been set to 1 already*/
				if (numeratorLongs.get(i) % factorOfDLongs.get(j) == 0) {
					numeratorLongs.set(i, numeratorLongs.get(i) / factorOfDLongs.get(j));
					//System.out.println(numeratorLongs.get(i) +" divided by " + factorOfDLongs.get(i));
					factorOfDLongs.set(j, 1l);
				}
			}
		}
	}
	



	public static void calculateAndPrint(List<Long> numbers) throws Exception{
		long count = 1;
		for (Long num : numbers) {
			//if(count*num <= Long.MAX_VALUE){
			//	count = count*num;
			//} else {
			//	throw new Exception("Long overflow");
			//}

			//Uses Math function so we can detect an exception.
			count = Math.multiplyExact(count, num);
		}

		long answer = count/1L;
		System.out.println(answer);			

	}





	/** 
	* Get factorials from n to
	* @param n value
	* @param k value
	*/
	public static ArrayList<Long> nFactorials(Long n, Long k){
		Long limit;

		if (n/2 > k){
			limit = n-k;
		} else {
			limit = k;
		}
		
		ArrayList<Long> list = new ArrayList<Long>();
		for(Long i = n; i > limit; i--){
			list.add(i);
			if (list.size() == k){
				break;
			}
		}
		return list;
	}
	/** 
	* Get relevant factorials. Depending on the size of n and k we must adjust for different
	* parameters.
	* @param n
	* @param k
	*/	
	public static ArrayList<Long> kFactorials(Long n, Long k){
		Long limit;
		if (n/2 > k){
			limit = k;
		} else {
			limit = n-k;
		}
		
		ArrayList<Long> list = new ArrayList<Long>();
		for(Long i = limit; i > 0; i--){
			list.add(i);
		}
		return list;
	}



	/* NOT NEEDED ANYMORE 



	 Converts integers to Longs. Probably should just deal with Longs from the get go. 
	public static void convertNumerators(ArrayList<Integer> list){
		for(int i = 0; i < list.size(); i++){
			numeratorLongs.add(list.get(i).longValue());
		}
	}
	public static void convertFactors(ArrayList<Integer> list){
		for(int i = 0; i < list.size(); i++){
			factorOfDLongs.add(list.get(i).longValue());
		}
	}	
	*/



}

