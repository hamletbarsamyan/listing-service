package am.jsl.listings.domain.item;

/**
 * An enum containing possible listing types.
 *
 * @author hamlet
 */
public enum ListingType {
    SALE((byte) 1),
    RENT((byte) 2),
    EXCHANGE((byte) 3),
    WANTED((byte) 4);
    private byte value;

    private ListingType(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
