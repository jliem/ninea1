package T9A1.client.data;

import T9A1.common.Item;

public interface ICacheManager {

	public boolean shouldHandleQuery(String query);

	public Item[] doSearch(String query);
}
