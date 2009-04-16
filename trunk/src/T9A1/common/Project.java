package T9A1.common;

import java.io.Serializable;

/**
 * Represents a project.
 *
 * @author Johannes
 *
 */
public class Project implements Serializable, Searchable {

	private String title;
	private String[] tools;
	private String[] instructions;
	private String[] materials;

	private long id;
	private int hours;
	private int minutes;

	public Project(String title, String[] tools, String[] materials,
			String[] instructions, long id, int hours, int minutes) {
		this.title = title;
		this.tools = tools;
		this.instructions = instructions;
		this.materials = materials;
		this.id = id;
		this.hours = hours;
		this.minutes = minutes;
	}

	public Project(String title, String[] tools, String[] materials,
			String[] instructions, long id, int minutes) {
		this.title = title;
		this.tools = tools;
		this.instructions = instructions;
		this.materials = materials;
		this.id = id;
		this.hours = minutes / 60;
		this.minutes = minutes % 60;
	}

	public Project() {
		this(null, null, null, null, 0, 0, 0);
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

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
}
