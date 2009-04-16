package T9A1.common;

import java.io.Serializable;

/**
 * Represents a project.
 *
 * @author Johannes
 *
 */
public class Project implements Serializable {

	private String title;
	private String[] tools;
	private String[] instructions;
	private String[] materials;

	private long id;

	public Project(String title, String[] tools, String[] instructions,
			String[] materials, long id) {
		this.title = title;
		this.tools = tools;
		this.instructions = instructions;
		this.materials = materials;
		this.id = id;
	}

	public Project() {
		this(null, null, null, null, 0);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getTools() {
		return tools;
	}

	public void setTools(String[] tools) {
		this.tools = tools;
	}

	public String[] getInstructions() {
		return instructions;
	}

	public void setInstructions(String[] instructions) {
		this.instructions = instructions;
	}

	public String[] getMaterials() {
		return materials;
	}

	public void setMaterials(String[] materials) {
		this.materials = materials;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
