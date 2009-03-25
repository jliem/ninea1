package T9A1.client.data;

import java.util.List;

import T9A1.common.Item;

public interface ICacheManager {

	public void add(String query, List<Item> items);

	public boolean isCacheHit(String query);

	public List<Item> doSearch(String query);

	public void remove(String query);

	public void clear();
}
