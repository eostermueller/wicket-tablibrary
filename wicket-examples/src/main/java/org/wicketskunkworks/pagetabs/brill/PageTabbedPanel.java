package org.wicketskunkworks.pagetabs.brill;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.request.resource.PackageResource;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CssPackageResource;
import org.apache.wicket.markup.head.CssHeaderItem;
/**
 * PageTabbedPanel component represents a panel with tabs that are used to switch
 * between different pages.
 * 
 * <p>
 * <b>Note:</b> When the currently selected tab is replaced by changing the
 * underlying list of tabs, the change is not picked up unless a call is made to
 * {@link #setSelectedTab(int)}.
 * <p>
 * 
 * Example:
 * 
 * <pre>
 * 
 * List tabs=new ArrayList();
 * 
 * tabs.add(new AbstractPageTab(new Model&lt;String&gt;(&quot;first tab&quot;)) {
 * 
 *   public Page&lt;? extends Page&gt; getPage()
 *   {
 *     return FirstPage.class;
 *   }
 * 
 * });
 * 
 * tabs.add(new AbstractPageTab(new Model&lt;String&gt;(&quot;second tab&quot;)) {
 * 
 *   public Page&lt;? extends Page&gt; getPage()
 *   {
 *     return SecondPage.class;
 *   }
 * 
 * });
 * 
 * add(new PageTabbedPanel(&quot;tabs&quot;, tabs));
 * 
 * 
 * &lt;span wicket:id=&quot;tabs&quot; class=&quot;tabpanel&quot;&gt;[tabbed panel will be here]&lt;/span&gt;
 * 
 * 
 * </pre>
 * 
 * </p>
 * 
 * <p>
 * For a complete example see the component references in wicket-examples
 * project
 * </p>
 * 
 * @see org.wicketskunkworks.pagetabs.brill.IPageTab
 * 
 * @author Brill Pappin (brill at pappin dot ca) based on Igor Vaynberg's
 *         TabbedPanel
 * 
 */
public class PageTabbedPanel extends Panel {
	private static final long serialVersionUID = 1L;

	/**
	 * id used for child panels
	 */
	public static final String TAB_PANEL_ID = "panel";

	private final List<IPageTab> tabs;

	private transient Boolean[] tabsVisibilityCache;
	/**
	 * Render to the web response whatever the component wants to contribute to the head section.
	 * 
	 * @param response
	 *            Response object
	 */
	@Override
	public void renderHead(IHeaderResponse response)
	{
	      PackageResourceReference cssFile = 
                  new PackageResourceReference(this.getClass(), "PageTabbedPanel-Badge.css");
	      CssHeaderItem cssItem = CssHeaderItem.forReference(cssFile);
	      response.render(cssItem);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            component id
	 * @param tabs
	 *            list of ITab objects used to represent tabs
	 */
	public PageTabbedPanel(String id, List<IPageTab> tabs) {
		super(id, new Model<Integer>(new Integer(-1)));


		if (tabs == null) {
			throw new IllegalArgumentException("argument [tabs] cannot be null");
		}

		this.tabs = tabs;

		final IModel<Integer> tabCount = new AbstractReadOnlyModel<Integer>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Integer getObject() {
				return PageTabbedPanel.this.tabs.size();
			}
		};

		WebMarkupContainer tabsContainer = new WebMarkupContainer(
		        "tabs-container") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
				tag.put("class", getTabContainerCssClass());
			}
		};
		add(tabsContainer);

		// add the loop used to generate tab names
		tabsContainer.add(new Loop("tabs", tabCount) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(LoopItem item) {
				final int index = item.getIndex();
				final IPageTab tab = (PageTabbedPanel.this.tabs.get(index));

				final WebMarkupContainer titleLink = newLink("link", index);

				titleLink.add(newTitle("title", tab.getTitle(), index));
				item.add(titleLink);
				item
				        .add(newBadge("badge", tab.getBadge(), tab
				                .isBadgeVisible()));
			}

			@Override
			protected LoopItem newItem(int iteration) {
				return newTabContainer(iteration);
			}

		});
	}

	/**
	 * Generates a loop item used to represent a specific tab's <code>li</code>
	 * element.
	 * 
	 * @param tabIndex
	 * @return new loop item
	 */
	protected LoopItem newTabContainer(final int tabIndex) {
		return new LoopItem(tabIndex) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
				String cssClass = (String) tag.getAttribute("class");
				if (cssClass == null) {
					cssClass = " ";
				}
				cssClass += " tab" + getIndex();

				Page thisPage = getPage();
				IPageTab t = tabs.get(getIndex());

				if (t.getPage().equals(thisPage.getClass())) {
					cssClass += " selected";
				}

				if (getIndex() == getTabs().size() - 1) {
					cssClass += " last";
				}
				tag.put("class", cssClass.trim());
			}

			@Override
			public boolean isVisible() {
				return getTabs().get(tabIndex).isVisible();
			}

		};
	}

	// @see org.apache.wicket.Component#onAttach()
	@Override
	protected void onBeforeRender() {
		for (int i = 0; i < tabs.size(); i++) {
			if (tabs.get(i).getPage().equals(getPage().getClass())) {
				setDefaultModelObject(new Integer(i));
			}
		}

		super.onBeforeRender();
	}

	/**
	 * @return the value of css class attribute that will be added to a div
	 *         containing the tabs. The default value is <code>tab-row</code>
	 */
	protected String getTabContainerCssClass() {
		return "tab-row";
	}

	/**
	 * @return list of tabs that can be used by the user to add/remove/reorder
	 *         tabs in the panel
	 */
	public final List<IPageTab> getTabs() {
		return tabs;
	}

	/**
	 * Factory method for tab titles. Returned component can be anything that
	 * can attach to span tags such as a fragment, panel, or a label
	 * 
	 * @param titleId
	 *            id of title component
	 * @param titleModel
	 *            model containing tab title
	 * @param index
	 *            index of tab
	 * @return title component
	 */
	protected Component newTitle(String titleId, IModel<?> titleModel, int index) {
		return new Label(titleId, titleModel);
	}

	protected Component newBadge(String titleId, IModel<?> titleModel,
	        boolean visible) {
		Label badge = new Label(titleId, titleModel);
		badge.setVisible(visible);
		return badge;
	}

	/**
	 * Factory method for links used to switch between tabs.
	 * 
	 * The created component is attached to the following markup. Label
	 * component with id: title will be added for you by the tabbed panel.
	 * 
	 * <pre>
	 * &lt;a href=&quot;#&quot; wicket:id=&quot;link&quot;&gt;&lt;span wicket:id=&quot;title&quot;&gt;[[tab title]]&lt;/span&gt;&lt;/a&gt;
	 * </pre>
	 * 
	 * Example implementation:
	 * 
	 * <pre>
	 * protected WebMarkupContainer newLink(String linkId, final int index) {
	 * 	return new Link(linkId) {
	 * 		private static final long serialVersionUID = 1L;
	 * 
	 * 		public void onClick() {
	 * 			setSelectedTab(index);
	 * 		}
	 * 	};
	 * }
	 * </pre>
	 * 
	 * @param linkId
	 *            component id with which the link should be created
	 * @param index
	 *            index of the tab that should be activated when this link is
	 *            clicked. See {@link #setSelectedTab(int)}.
	 * @return created link component
	 */
	protected WebMarkupContainer newLink(String linkId, final int index) {
		return new Link(linkId) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setSelectedTab(index);
			}
		};
	}

	/**
	 * sets the selected tab
	 * 
	 * @param index
	 *            index of the tab to select
	 * 
	 */
	public void setSelectedTab(int index) {
		if (index < 0 || (index >= tabs.size() && index > 0)) {
			throw new IndexOutOfBoundsException();
		}

		setDefaultModelObject(new Integer(index));
		IPageTab tab = tabs.get(index);

		Page thisPage = getPage();

		if (!tab.getPage().equals(thisPage.getClass())) {
			setResponsePage(tab.getPage(), tab.getPageParameters());
		}

	}

	/**
	 * @return index of the selected tab
	 */
	public final int getSelectedTab() {
		return (Integer) getDefaultModelObject();
	}

	private boolean isTabVisible(int tabIndex) {
		if (tabsVisibilityCache == null) {
			tabsVisibilityCache = new Boolean[tabs.size()];
		}

		Boolean visible = tabsVisibilityCache[tabIndex];
		if (visible == null) {
			visible = tabs.get(tabIndex).isVisible();
			tabsVisibilityCache[tabIndex] = visible;
		}
		return visible;
	}

	@Override
	protected void onDetach() {
		tabsVisibilityCache = null;
		super.onDetach();
	}

}
