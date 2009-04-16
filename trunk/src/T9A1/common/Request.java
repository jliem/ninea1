package T9A1.common;

import java.io.Serializable;

/**
 * Represents a request made to the server.
 *
 * @author Chase
 *
 */
public class Request implements Serializable {

	public enum Type {project_search, project_list, project_email,
		item_search, item_list,
		update_request, results};

	public Object data;
	public Type type;
	public boolean isString = false;

	public Request(Type type, Object data) {
		this.type = type;
		this.data = data;
	}

	public Request(Type type, String data) {
		this.type = type;
		this.data = data;
		this.isString = true;
	}
}
