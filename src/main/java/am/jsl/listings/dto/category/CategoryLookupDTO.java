package am.jsl.listings.dto.category;

import am.jsl.listings.dto.NamedDTO;

import java.io.Serializable;

/**
 * Contains common category data.
 *
 * @author hamlet
 */
public class CategoryLookupDTO extends NamedDTO implements Serializable {


    /**
     * The parent id of category.
     */
    private long parentId;

    /**
     * Getter for property 'parentId'.
     *
     * @return Value for property 'parentId'.
     */
    public long getParentId() {
        return parentId;
    }

    /**
     * Setter for property 'parentId'.
     *
     * @param parentId Value to set for property 'parentId'.
     */
    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
}
