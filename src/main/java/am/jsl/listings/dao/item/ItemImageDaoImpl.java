package am.jsl.listings.dao.item;


import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.dao.MultiLingualDaoImpl;
import am.jsl.listings.dao.item.mapper.ItemImageDTOMapper;
import am.jsl.listings.dao.item.mapper.ItemImageMapper;
import am.jsl.listings.domain.item.Item;
import am.jsl.listings.domain.item.ItemImage;
import am.jsl.listings.dto.item.ItemImageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The implementation of Dao interface for accessing {@link ItemImage} domain object.
 *
 * @author hamlet
 */
@Repository("itemImageDao")
@Lazy
public class ItemImageDaoImpl extends MultiLingualDaoImpl<ItemImage> implements ItemImageDao {
    private ItemImageDTOMapper itemImageDTOMapper = new ItemImageDTOMapper();
    private ItemImageMapper itemImageMapper = new ItemImageMapper();

    @Autowired
    public ItemImageDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    private static final String deleteSql = "delete from item_image where id = :id";

    @Override
    public void delete(long id) {
        delete(id, deleteSql);
    }

    private static final String getByIdSql = "select * from item_image where id = :id";

    @Override
    public ItemImage get(long id) {
        return get(id, getByIdSql, itemImageMapper);
    }

    private static final String createSql = "insert into item_image "
            + "(id, item_id, file_name, sort_order) "
            + "values(:id, :item_id, :file_name, sort_order)";

    @Override
    public void create(ItemImage itemImage) {
        long id = DBUtils.getNextId(getJdbcTemplate(), "item_image");
        itemImage.setId(id);
        Map<String, Object> params = new HashMap<>(4);
        params.put(DBUtils.id, itemImage.getId());
        params.put(DBUtils.item_id, itemImage.getItemId());
        params.put(DBUtils.file_name, itemImage.getFileName());
        params.put(DBUtils.sort_order, itemImage.getSortOrder());
        parameterJdbcTemplate.update(createSql, params);

    }

    private static final String updateSql = "update item_image "
            + "set sort_order = :sort_order "
            + "where id = :id";

    @Override
    public void update(ItemImage itemImage) {
        Map<String, Object> params = new HashMap<>(2);
        params.put(DBUtils.id, itemImage.getId());
        params.put(DBUtils.sort_order, itemImage.getSortOrder());
        parameterJdbcTemplate.update(updateSql, params);
    }

    private static final String getItemImagesSql = "select * from item_image i "
            + "where i.item_id = :item_id order by i.sort_order";
    @Override
    public List<ItemImageDTO> getItemImages(long itemId) {
        try {
            return parameterJdbcTemplate.query(getItemImagesSql,
                    Collections.singletonMap(DBUtils.item_id, itemId), itemImageDTOMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
