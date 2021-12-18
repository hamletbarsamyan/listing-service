package am.jsl.listings.service.attribute;

import am.jsl.listings.domain.attribute.Attribute;
import am.jsl.listings.dto.attribute.AttributeManageDTO;
import am.jsl.listings.service.MultiLingualService;

/**
 * Service interface which defines all the methods for working with {@link Attribute} domain object.
 *
 * @author hamlet
 */
public interface AttributeService extends MultiLingualService<Attribute> {

    /**
     * Returns AttributeManageDTO by id
     *
     * @param attributeId the attribute id
     * @param locale the locale
     * @return the AttributeManageDTO
     */
    AttributeManageDTO getManageDTO(long attributeId, String locale);

    /**
     * Creates or updates the attribute based on attributeManageDTO data.
     * @param attributeManageDTO the AttributeManageDTO
     */
    void save(AttributeManageDTO attributeManageDTO);
}
