package mod08_01;

/**
 * The App class demonstrates prime number checking using
 * both single-threaded and multi-threaded approaches.
 * 
 * @author angel
 */
public class App {

    /**
     * The main method checks if a given number is prime
     * using both single-threaded and multi-threaded methods
     * and measures the time taken for each approach.
     * 
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        
        // Number to check for primality
        long number = 1000000007;

        // Check primality using the single-threaded method
        long start = System.currentTimeMillis(); // Record the start time
        boolean prime = isPrime(number); // Call single-threaded prime check
        long elapsed = System.currentTimeMillis() - start; // Calculate elapsed time
        
        // Output the result of single-threaded prime check
        System.out.println(number + ": " + prime + ": " + elapsed + "ms");

        // Check primality using the multi-threaded method with 2 threads
        start = System.currentTimeMillis(); // Reset start time
        prime = isPrime(number, 2); // Call multi-threaded prime check with 2 threads
        elapsed = System.currentTimeMillis() - start; // Calculate elapsed time

        // Output the result of multi-threaded prime check
        System.out.println(number + ": " + prime + ": " + elapsed + "ms");

        // Check primality using the multi-threaded method with 4 threads
        start = System.currentTimeMillis(); // Reset start time
        prime = isPrime(number, 4); // Call multi-threaded prime check with 4 threads
        elapsed = System.currentTimeMillis() - start; // Calculate elapsed time

        // Output the result of multi-threaded prime check
        System.out.println(number + ": " + prime + ": " + elapsed + "ms");

    }

    /**
     * Checks whether a given number is prime using a single-threaded approach.
     * 
     * @param number the number to check for primality
     * @return true  if the number is prime, false otherwise
     */
    private static boolean isPrime(long number) {
        
        // Handle edge cases for non-prime numbers
        if ((number == 0) || (number == 1) || (number < 0))
            return false;

        // Check divisibility for numbers starting from 2 up to number-1
        for (long i = 2; i < number - 1; i++) {
            // If any number divides the input number evenly, it's not prime
            if (number % i == 0)
                return false;
        }

        // If no divisors were found, the number is prime
        return true;
    }

    /**
     * Checks whether a given number is prime using a multi-threaded approach. The
     * range of numbers to check is divided among the specified number of threads.
     * 
     * @param number      the number to check for primality
     * @param threadCount the number of threads to use for the computation
     * @return true       if the number is prime, false otherwise
     */
    private static boolean isPrime(long number, int threadCount) {

        // Create an array to hold the threads
        Thread[] threads = new Thread[threadCount];

        // Initialize the CheckPrime shared resource
        CheckPrime.init();

        // Divide the work across the threads
        for (int i = 0; i < threadCount; i++) {
            // Calculate the range for each thread
            long start = Math.max(2, i * number / threadCount); // Starting number
            long end = (i + 1) * number / threadCount; // Ending number
            
            // Create a new CheckPrime task for the current range
            CheckPrime check = new CheckPrime(number, start, end);

            // Create and start a new thread for the task
            threads[i] = new Thread(check);
            threads[i].start();
        }

        // Wait for all threads to complete
        for (int i = 0; i < threadCount; i++) {
            try {
                threads[i].join(); // Wait for thread to finish
            } catch (InterruptedException e) {
                e.printStackTrace(); // Handle any interruptions
            }
        }

        // Return the result from the shared CheckPrime class
        return CheckPrime.getResult();
    }
}
