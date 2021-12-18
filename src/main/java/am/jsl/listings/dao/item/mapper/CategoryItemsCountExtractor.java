package am.jsl.listings.dao.item.mapper;

import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.dto.Pair;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The {@link ResultSetExtractor} implementation for extracting
 * results from a {@link ResultSet} to category / items count.
 *
 * @author hamlet
 */
public class CategoryItemsCountExtractor implements ResultSetExtractor<List<Pair<String, Integer>>> {


    @Override
    public List<Pair<String, Integer>> extractData(ResultSet rs) throws SQLException {
        List<Pair<String, Integer>> result = new ArrayList<>();

        while (rs.next()) {
            result.add(new Pair<>(rs.getString(DBUtils.name), rs.getInt(DBUtils.item_count)));
        }

        return result;
    }
}
