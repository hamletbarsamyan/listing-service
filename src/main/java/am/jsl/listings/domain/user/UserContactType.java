package am.jsl.listings.domain.user;

/**
 * An enum containing possible user contact types.
 *
 * @author hamlet
 */
public enum UserContactType {
    PHONE((byte) 1),
    EMAIL ((byte) 2),
    SKYPE ((byte) 3),
    VIBER ((byte) 4),
    WATSAPP ((byte)5);

    private byte value;

    UserContactType(byte value) {
        this.value = value;
    }

    /**
     * Getter for property 'value'.
     *
     * @return Value for property 'value'.
     */
    public byte getValue() {
        return value;
    }
}
