package T9A1.client.gui;

import javax.swing.JPanel;

/**
 * An interface Used to specify panels that are a representation of search results.
 * @author Catie
 *
 */
public interface SearchResult {
	/**
	 * Returns a JPanel representation of search results.
	 * @return A JPanel representation of search results
	 */
	public abstract JPanel getListPanel();
}
