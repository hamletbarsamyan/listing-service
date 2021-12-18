package am.jsl.listings.api.rest.attribute;

import am.jsl.listings.api.rest.Response;
import am.jsl.listings.api.rest.ResponseCode;
import am.jsl.listings.domain.attribute.Attribute;
import am.jsl.listings.domain.attribute.AttributeType;
import am.jsl.listings.domain.attribute.AttributeValue;
import am.jsl.listings.dto.attribute.AttributeManageDTO;
import am.jsl.listings.dto.attribute.AttributeValueManageDTO;
import am.jsl.listings.dto.attribute.AttributeValuesListDTO;
import am.jsl.listings.dto.attribute.AttributeViewDTO;
import am.jsl.listings.ex.CannotDeleteException;
import am.jsl.listings.service.attribute.AttributeService;
import am.jsl.listings.service.attribute.AttributeValueService;
import am.jsl.listings.api.rest.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The AttributeController defines methods for searching, viewing and managing attributes and values.
 *
 * @author hamlet
 */
@RestController
@RequestMapping(value = "/api/attributes")
@Lazy
public class AttributeController extends BaseController {

    /**
     * The attribute service
     */
    @Autowired
    private transient AttributeService attributeService;

    /**
     * The attribute value service
     */
    @Autowired
    private transient AttributeValueService attributeValueService;

    /**
     * Lookup attributes.
     *
     * @return the response
     */
    @GetMapping("/lookup")
    public Response<List<Attribute>> lookup() {
        List<Attribute> attributes = attributeService.lookup(getLocale());
        return Response.ok(attributes);
    }

    /**
     * Returns attribute types.
     *
     * @return the response
     */
    @GetMapping("/types")
    @PreAuthorize("hasAuthority('attribute.view')")
    public Response<List<String>> getTypes() {
        return Response.ok(AttributeType.names());
    }

    /**
     * Returns attribute list.
     *
     * @return the response
     */
    @GetMapping()
    @PreAuthorize("hasAuthority('attribute.view')")
    public Response<List<Attribute>> list() {
        List<Attribute> attributes = attributeService.list(getLocale());
        return Response.ok(attributes);
    }

    /**
     * Returns an attribute by id.
     *
     * @return the response
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('attribute.view')")
    public Response<AttributeViewDTO> get(@PathVariable long id) {
        String locale = getLocale();
        Attribute attribute = attributeService.get(id, locale);
        AttributeViewDTO attributeViewDTO = AttributeViewDTO.from(attribute);
        List<AttributeValue> attributeValues = attributeValueService.getAttributeValues(id, locale);
        attributeViewDTO.setAttributeValues(attributeValues);
        return Response.ok(attributeViewDTO);
    }

    /**
     * Returns an AttributeManageDTO by id.
     *
     * @return the response
     */
    @GetMapping("/manage/{id}")
    @PreAuthorize("hasAuthority('attribute.manage')")
    public Response<AttributeManageDTO> getManageDTO(@PathVariable long id) {
        AttributeManageDTO attribute = attributeService.getManageDTO(id, getLocale());
        return Response.ok(attribute);
    }

    /**
     * Creates or updates the attribute.
     *
     * @return the response
     */
    @PostMapping()
    @PreAuthorize("hasAuthority('attribute.manage')")
    public Response<?> save(@RequestBody AttributeManageDTO attributeManageDTO) {
        try {
            attributeService.save(attributeManageDTO);
            return Response.ok();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(e.getMessage());
        }
    }

    /**
     * Deletes the attribute.
     *
     * @return the response
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('attribute.manage')")
    public Response<?> delete(HttpServletRequest req, @PathVariable long id) {
        try {
            attributeService.delete(id);
            return Response.ok();
        } catch (CannotDeleteException e) {
            return Response.errorWithCode(ResponseCode.CAN_NOT_DELETE);
        }
    }

    /**
     * Returns attribute values by attribute id.
     *
     * @return the response
     */
    @GetMapping("/{attributeId}/values")
    public Response<List<AttributeValue>> attributes(@PathVariable long attributeId) {
        List<AttributeValue> attributeValues = attributeValueService.getAttributeValues(attributeId, getLocale());
        return Response.ok(attributeValues);
    }

    /**
     * Returns attribute values by attribute id.
     *
     * @return the response
     */
    @GetMapping("/{attributeId}/valuesWithTranslations")
    @PreAuthorize("hasAuthority('attribute.view')")
    public Response<AttributeValuesListDTO> attributeValuesWithTranslations(@PathVariable long attributeId) {
        Attribute attribute = attributeService.get(attributeId, getLocale());
        AttributeValuesListDTO attributeValuesListDTO = new AttributeValuesListDTO();
        attributeValuesListDTO.setId(attribute.getId());
        attributeValuesListDTO.setName(attribute.getName());
        List<AttributeValueManageDTO> attributeValues = attributeValueService.getAttributeManageValues(attributeId, getLocale());
        attributeValuesListDTO.setAttributeValues(attributeValues);
        return Response.ok(attributeValuesListDTO);
    }

    /**
     * Saves attribute all values.
     *
     * @return the response
     */
    @PostMapping("/{attributeId}/values")
    @PreAuthorize("hasAuthority('attribute.manage')")
    public Response<?> saveAttributeValues(@PathVariable long attributeId,
                                                @RequestBody List<AttributeValueManageDTO> attributeValues) {
        try {
            attributeValueService.saveAttributeValues(attributeId, attributeValues);
            List<AttributeValueManageDTO> response = attributeValueService.getAttributeManageValues(attributeId, getLocale());
            return Response.ok(response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(e.getMessage());
        }
    }
}