package T9A1.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a project consisting of a task and associated instructions
 * and materials that the user can search for.
 *
 * @author Johannes Liem
 *
 */
public class Project implements Serializable, Searchable {

	private String title;
	private String[] tools;
	private ArrayList<String> instructions;
	private String[] materials;

	private long id;
	private int hours;
	private int minutes;

	public Project(String title, String[] tools, String[] materials,
			String[] instructions, long id, int hours, int minutes) {
		this(title, tools, materials, instructions, id);

		this.hours = hours;
		this.minutes = minutes;
	}

	public Project(String title, String[] tools, String[] materials,
			String[] instructions, long id, int minutes) {
		this(title, tools, materials, instructions, id);

		this.hours = minutes / 60;
		this.minutes = minutes % 60;
	}

	public Project(String title, String[] tools, String[] materials,
			String[] instructions, long id) {

		this.title = title;
		this.tools = tools;

		if (instructions != null) {
			this.instructions = new ArrayList<String>(Arrays.asList(instructions));
		} else {
			this.instructions = new ArrayList<String>();
		}

		this.materials = materials;
		this.id = id;
	}

	public Project() {
		this(null, null, null, null, 0, 0, 0);
	}

	public void addInstruction(String instruction) {
		instructions.add(instruction);
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
		return instructions.toArray(new String[0]);
	}

	public void setInstructions(ArrayList<String> instructions) {
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
