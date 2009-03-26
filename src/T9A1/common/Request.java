package T9A1.common;

import java.io.Serializable;

public class Request implements Serializable {

	public enum Type {project_search, item_search};

	public Object data;
	public Type type;

	public Request(Type type, Object data) {
		this.type = type;
		this.data = data;
	}
}
