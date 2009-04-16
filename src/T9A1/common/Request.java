package T9A1.common;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Represents a request made to the server.
 *
 * @author Chase
 *
 */
public class Request implements Serializable {

	public enum Type {project_search, customer_project_list, project_email,
		item_search, customer_item_list, sale_search,
		update_request, results};

	public enum Key {store_id, query, email, data, project};

	private HashMap<Key, Object> map;

	public Type type;

	public Request(Type type) {
		this.type = type;
		map = new HashMap<Key, Object>();
	}

	public Object get(Key key) {
		return map.get(key);
	}

	public Object put(Key key, Object value) {
		return map.put(key, value);
	}
}
