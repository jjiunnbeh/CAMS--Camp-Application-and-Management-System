package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * Verify the input keyed in by the user. check if the input is within range
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */
public class InputValidator {
    private Scanner scanner;
    
    /**
     * Constructor for InputValidator class
     * declare new scanner
     */
    public InputValidator() {
        scanner = new Scanner(System.in);
    }

    
    /**
     * Request for string input
     * @param invalidMessage to be displayed if there is an error
     * @return the prompt string
     */
    public String promptString(String invalidMessage) {
        return prompt(invalidMessage, Function.identity(), input -> !input.isEmpty());
    }

    /**
     * Request for date input
     * @param invalidMessage to be displayed if the input is wrong
     * @param format of the date entered
     * @return the date
     */
    public LocalDate promptDate(String invalidMessage, String format) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(format);
        return prompt(invalidMessage,
                input -> LocalDate.parse(input, dateFormatter),
                date -> true);
    }

    
    /**
     * Request for boolean input
     * @param invalidMessage to be displayed if the input is wrong
     * @param trueString the string if its true
     * @param falseString the string if its false
     * @return the boolean outcome
     */
    public boolean promptBoolean(String invalidMessage, String trueString, String falseString) {
        return prompt(invalidMessage,
                input -> input.equalsIgnoreCase(trueString) ? true : input.equalsIgnoreCase(falseString) ? false : null,
                Objects::nonNull);
    }


    
    /**
     * Request for an Integer input
     * @return the int input
     */
    public int promptInt() {
        return prompt("Please enter an integer", Integer::parseInt, num -> true);
    }

    
    /**
     * Checks if the value entered is within the range. mainly used in menu options
     * @param min the minimum acceptable integer
     * @param max the maximumacceptable integer
     * @return the input integer
     */
    public int promptIntInRange(int min, int max) {
        return prompt(String.format("Invalid input. Please enter a number between %d and %d.", min, max),
                Integer::parseInt,
                number -> number >= min && number <= max);
    }
    
    /**
     * Checks if the value entered is within the range. mainly used in menu options
     * @param min the minimum acceptable integer
     * @param max the maximumacceptable integer
     * @return the input integer
     */
    public List<Integer> promptInts(int min, int max) {
        return prompt(
                String.format("Invalid input. Please enter numbers between %d and %d.", min, max),
                input -> Arrays.stream(input.split("\\s+"))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList()),
                list -> {
                    boolean hasNoDuplicates = list.size() == new HashSet<>(list).size();
                    boolean allWithinRange = list.stream().allMatch(i -> i >= min && i <= max);
                    return hasNoDuplicates && allWithinRange;
                }
        );
    }



    /**
     * Request for an input 
     * @param <T> Function
     * @param invalidMessage to be displayed if an error is encountered
     * @param parser the type of parser
     * @param validator the type of validator
     * @return
     */
    private <T> T prompt(String invalidMessage, Function<String, T> parser, Predicate<T> validator) {
        while (true) {
            try {
                T result = parser.apply(scanner.nextLine().trim());
                if (validator.test(result)) {
                    return result;
                } else {
                    System.out.println(invalidMessage);
                }
            } catch (Exception e) {
                System.out.println(invalidMessage);
            }
        }
    }

    /**
     * Close the scanner
     */
    public void closeScanner() {
        scanner.close();
    }
}
