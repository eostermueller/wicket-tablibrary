/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wicket.examples.tablibrary;


import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValueConversionException;
import org.wicketskunkworks.pagetabs.brill.AbstractPageTab;
import org.wicketskunkworks.pagetabs.brill.IPageTab;
import org.wicketskunkworks.pagetabs.brill.PageTabbedPanel;


/**
 * A book details page. Shows information about a book.
 * 
 * @author Jonathan Locke
 */
public final class BookDetails extends AuthenticatedWebPage
{
	private PageTabbedPanel m_pageTabbedPanel = null;

	/**
	 * Constructor for calls from external page links
	 * 
	 * @param parameters
	 *            Page parameters
	 * @throws StringValueConversionException
	 */
	public BookDetails(final PageParameters parameters) throws StringValueConversionException
	{
		this(Book.get(parameters.get("id").toLong()));
	}

	private void dispStructure()
	{
		WicketHierarchyPrinter.print(this, true, true);

	}

	/**
	 * Constructor
	 * 
	 * @param book
	 *            The model
	 */
	public BookDetails(final Book book)
	{
		List<IPageTab> tabs = new ArrayList<IPageTab>();

		tabs.add(new AbstractPageTab(new Model<String>("first tab"))
		{

			@Override
			public Class<? extends WebPage> getPage()
			{
				return TabPanel1.class;
			}
		});

		tabs.add(new AbstractPageTab(new Model<String>("second tab"))
		{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Class<? extends WebPage> getPage()
			{
				return TabPanel2.class;
			}
		});
		tabs.add(new AbstractPageTab(new Model<String>("third tab"))
		{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Class<? extends WebPage> getPage()
			{
				return TabPanel3.class;
			}
		});

		m_pageTabbedPanel = new PageTabbedPanel("tabs", tabs);
		IModel<?> mc = getDefaultModel();
		m_pageTabbedPanel.add(AttributeModifier.replace("class", mc));


		m_pageTabbedPanel.setParent(getPage());
		m_pageTabbedPanel.setSelectedTab(0);
		add(m_pageTabbedPanel);
		dispStructure();
	}

	/**
	 * Creates an external page link
	 * 
	 * @param name
	 *            The name of the link component to create
	 * @param book
	 *            The book to link to
	 * @param noBookTitle
	 *            The title to show if book is null
	 * @return The external page link
	 */
	public static BookmarkablePageLink<Void> link(final String name, final Book book,
		final String noBookTitle)
	{
		final BookmarkablePageLink<Void> link = new BookmarkablePageLink<Void>(name,
			BookDetails.class);

		if (book != null)
		{
			link.getPageParameters().add("id", book.getId());
			link.add(new Label("title", new Model<Book>(book)));
		}
		else
		{
			link.add(new Label("title", noBookTitle));
			link.setEnabled(false);
		}

		return link;
	}

	Book getBook(String id)
	{
		User user = getLibrarySession().getUser();
		Book rc = null;
		for (Book book : user.getBooks())
		{
			if (id.equals(book.getId()))
			{
				rc = book;
			}
		}
		return rc;

	}
}
