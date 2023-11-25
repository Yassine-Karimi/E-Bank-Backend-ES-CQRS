package ma.yas.comptecqrses.common_api.exceptions;

public class AmountNegativeException extends RuntimeException {
    public AmountNegativeException(String message) {
        super(message);
    }
}
