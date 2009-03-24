package T9A1.client.data;

import java.util.List;

import T9A1.common.Item;

public interface ICacheManager {

	public boolean isCacheHit(String query);

	public List<Item> doSearch(String query);
}
