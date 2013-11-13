package org.wicketskunkworks.tabs;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.PageParameters;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;


public class BookmarkableTabbedPanel extends TabbedPanel
{

	private PageParameters pageParameters;
	private String tabParameterName;
	private int defaultTabIndex = 0;
	private List unbookmarkableTabIndex = new ArrayList();


	/**
	 * Using this constructor the following defaults take effect:
	 * <ul>
	 *	<li>tabParameterName = component id</li>
	 *	<li>defaultTabIndex = 0</li>
	 * </ul>
	 * @param id component id
	 * @param tabs list of ITab objects used to represent tabs
	 * @param pageParameters Container for parameters to a requested page. A
	 * parameter for the selected tab will be inserted.
	 */
	public BookmarkableTabbedPanel(
		String id,
		List<ITab> tabs,
		PageParameters pageParameters)
	{
		super(id, tabs);
		this.pageParameters = pageParameters;
		this.tabParameterName = id;
		if (pageParameters.containsKey(tabParameterName))
		{
			String tab = pageParameters.getString(tabParameterName);
			try
			{
				int tabIndex = Integer.parseInt(tab);
				if (tabIndex >= 0 && tabIndex < tabs.size())
					setSelectedTab(tabIndex);
				else
					setSelectedTab(defaultTabIndex);
				}
			catch (NumberFormatException e)
			{
				setSelectedTab(defaultTabIndex);
			}
		}
		else
			setSelectedTab(defaultTabIndex);
	}


	/**
	 * @param id component id
	 * @param tabs list of ITab objects used to represent tabs
	 * @param defaultTabIndex Set the tab to by displayed by default. The url
	 * for this tab will not contain any tab specific information. If you want to
	 * display the first tab by default, you can use the constructor without this 
	 * parameter.
	 * @param pageParameters Container for parameters to a requested page. A
	 * parameter for the selected tab will be inserted.
	 * @param unbookmarkableTabIndex Indexes of tabs with standard (no
	 * bookmarkable) links. First tab has index 0.
	 */
	public BookmarkableTabbedPanel(
		String id, 
		List<ITab> tabs,
		int defaultTabIndex,
		PageParameters pageParameters,
		int ...unbookmarkableTabIndex) 
	{
		this(id, tabs, pageParameters);
		this.defaultTabIndex = defaultTabIndex;
		if (!pageParameters.containsKey(tabParameterName))
			setSelectedTab(defaultTabIndex);
		for(int element : unbookmarkableTabIndex)
			this.unbookmarkableTabIndex.add(element);
	}

	
	@Override
	protected WebMarkupContainer newLink(String linkId, int index)
	{
		WebMarkupContainer link;

		// create default (not bookmarkable) links for the specified tabs.
		if (unbookmarkableTabIndex.contains(index))
			link = super.newLink(linkId, index);
		// create bookmarkable links
		else
		{
			if (index == defaultTabIndex)
				pageParameters.remove(tabParameterName); // keep URLs short
			else
				pageParameters.put(tabParameterName, "" + index);
			link = new BookmarkablePageLink(
		      		linkId, getPage().getClass(), pageParameters);

			/* Overwrite parameters only used for link cunstruction, but doesn't
			 * reflect the actual state.
			 */
			if (index != getSelectedTab())        
				pageParameters.put(tabParameterName, "" + getSelectedTab());
		}

		if (index == getSelectedTab())
			link.setEnabled(false);

		return link;
	}
  
}
