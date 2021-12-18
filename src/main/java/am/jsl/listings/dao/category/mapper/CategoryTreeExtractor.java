package am.jsl.listings.dao.category.mapper;

import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.dto.category.CategoryDTO;
import am.jsl.listings.dto.category.CategoryTreeDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * The {@link ResultSetExtractor} implementation for extracting
 * results from a {@link java.sql.ResultSet} to tree of CategoryTreeDTOs.
 *
 * @author hamlet
 */
@Component
public class CategoryTreeExtractor implements ResultSetExtractor<List<CategoryTreeDTO>> {

    /**
     * The category images server
     */
    @Value("${listings.category.img.server}")
    private String categoryImgServer;

    @Override
    public List<CategoryTreeDTO> extractData(ResultSet rs) throws SQLException {
        List<CategoryTreeDTO> result = new ArrayList<>();
        Map<Long, CategoryTreeDTO> categoryMap = new HashMap<>();

        while (rs.next()) {
            long parentId = rs.getLong(DBUtils.parent_id);

            CategoryTreeDTO category = new CategoryTreeDTO();
            category.setId(rs.getLong(DBUtils.id));
            category.setName(rs.getString(DBUtils.name));
            category.setSlug(rs.getString(DBUtils.slug));
            category.setIcon(rs.getString(DBUtils.icon));
            category.setSortOrder(rs.getInt(DBUtils.sort_order));
            category.setDescription(rs.getString(DBUtils.description));
            category.setParentId(parentId);
            category.setIconServer(categoryImgServer);
            category.initIconPath();

            if (parentId == 0) {
                result.add(category);
            } else {
                CategoryTreeDTO parentCategory = categoryMap.get(parentId);

                if (parentCategory != null) {
                    parentCategory.addChild(category);
                }
            }

            categoryMap.put(category.getId(), category);
        }

        return result;
    }
}
