package am.jsl.listings.service.item;

import am.jsl.listings.dao.item.ItemImageDao;
import am.jsl.listings.domain.item.ItemImage;
import am.jsl.listings.dto.item.ItemImageDTO;
import am.jsl.listings.service.MultiLingualServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The service implementation of the {@link ItemImageService}.
 *
 * @author hamlet
 */
@Service("itemImageService")
public class ItemImageServiceImpl extends MultiLingualServiceImpl<ItemImage> implements ItemImageService {

    /**
     * The item image url
     */
    @Value("${listings.item.image.url}")
    private String itemImgUrl;
    /**
     * The item image dao.
     */
    private ItemImageDao itemImageDao;

    @Override
    public List<ItemImageDTO> getItemImages(long itemId) {
        List<ItemImageDTO> images = itemImageDao.getItemImages(itemId);

        for (ItemImageDTO image : images) {
            image.setFileUrl(String.format(itemImgUrl, image.getId() + "/" + image.getFileName()));
        }
        return images;
    }

    /**
     * Setter for property 'itemImageDao'.
     *
     * @param itemImageDao Value to set for property 'itemImageDao'.
     */
    @Autowired
    public void setItemImageDao(@Qualifier("itemImageDao") ItemImageDao itemImageDao) {
        this.itemImageDao = itemImageDao;
        setBaseDao(itemImageDao);
    }
}
