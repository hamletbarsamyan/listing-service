package am.jsl.listings.dao.item.mapper;

import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.dto.item.ItemListDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@link ResultSetExtractor} implementation for extracting
 * results from a {@link ResultSet} to ItemListDTOs.
 *
 * @author hamlet
 */
@Component
public class ItemListDTOExtractor implements ResultSetExtractor<List<ItemListDTO>> {

    /**
     * The item images server
     */
    @Value("${listings.item.img.server}")
    private String itemImgServer;

    @Override
    public List<ItemListDTO> extractData(ResultSet rs) throws SQLException {
        List<ItemListDTO> result = new ArrayList<>();

        while (rs.next()) {
            ItemListDTO item = new ItemListDTO();
            item.setId(rs.getLong(DBUtils.id));
            item.setCategory(rs.getString(DBUtils.category));
            item.setPrice(rs.getDouble(DBUtils.price));
            item.setCurrency(rs.getString(DBUtils.currency));
            item.setActive(rs.getBoolean(DBUtils.active));
            item.setName(rs.getString(DBUtils.name));
            item.setUpc(rs.getString(DBUtils.upc));
            item.setActive(rs.getBoolean(DBUtils.active));
            item.setCreatedAt(rs.getTimestamp(DBUtils.created_at));
            item.initThumbnailPathPath(itemImgServer, rs.getString(DBUtils.thumbnail));
            result.add(item);
        }

        return result;
    }
}
