package utils;

/**
 * Verify the output of the operations. check if the operation is a success or failure
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */
public class OperationResult {
    private boolean success;
    private String message;

    
    /**
     * Constructor for InputValidator class
     * @param success the output of the operation
     * @param message the output message of the operation
     */
    private OperationResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    
    /**
     * when the operation is a success
     * @return boolean success
     */
    public boolean isSuccess() {
        return success;
    }

    
    /**
     * Retrieve the operation message
     * @return the operation message
     */
    public String getMessage() {
        return message;
    }

    /**
     * If the operation is a success
     * @return the OperationResult object
     */
    public static OperationResult success() {
        return new OperationResult(true, "Operation succeeded");
    }

    
    /**
     * If the operation is a failure
     * @return the OperationResult object
     */
    public static OperationResult failure(String message) {
        return new OperationResult(false, message);
    }
}
