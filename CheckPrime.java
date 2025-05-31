package mod08_01;

/**
 * The CheckPrime class checks whether a number is prime within
 * a specific range of values. It implements the Runnable
 * interface to allow multi-threaded prime number checking.
 * 
 * @author angel
 */
public class CheckPrime implements Runnable {

    // Range of numbers to check for divisibility
    private long start;
    private long end;
    
    // The number to check for primality
    private long number;

    // Shared result of whether the number is prime
    private static boolean result;

    /**
     * Constructor to initialize the CheckPrime task with a range of numbers to check.
     * 
     * @param number the number to check for primality
     * @param start  the starting number of the range
     * @param end    the ending number of the range
     */
    public CheckPrime(long number, long start, long end) {
        this.number = number;
        this.start = start;
        this.end = end;
    }

    /**
     * Returns the result of the primality check.
     * 
     * @return true if the number is prime, false otherwise
     */
    public static boolean getResult() {
        return result;
    }

    /**
     * Initializes the result to true. This should be called
     * before starting any threads to ensure a clean state.
     */
    public static void init() {
        result = true; // Initialize to true
    }

    /**
     * The run method is executed when a thread starts. It checks if the given
     * number is divisible by any value in the specified range. If a divisor is
     * found, the result is set to false, indicating the number is not prime.
     */
    @Override
    public void run() {
        
        // Handle edge cases for non-prime numbers
        if ((this.number == 0) || (this.number == 1) || (this.number < 0)) {
            result = false; // Set result to false if the number cannot be prime
            return;
        }

        // Check for divisibility in the specified range
        for (long i = this.start; i < this.end; i++) {
            // If a divisor is found, the number is not prime
            if (this.number % i == 0) {
                result = false; // Set result to false
                return; // Exit early as the number is confirmed not prime
            }
        }
    }
}
