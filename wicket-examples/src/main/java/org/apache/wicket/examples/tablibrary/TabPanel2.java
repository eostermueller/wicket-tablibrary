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

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.examples.tablibrary.Book.WritingStyle;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.StringList;

/**
 * Panel representing the content panel for the first tab
 * 
 * @author Igor Vaynberg (ivaynberg)
 * 
 */
public class TabPanel2 extends WebPage
{
	/**
	 * 
	 */
	private BookDetails bookDetails = null;
	Book b = null;

	public TabPanel2(BookDetails bookDetails)
	{
		this.bookDetails = bookDetails;
		b = this.bookDetails.getBook(getId());
	}

	public TabPanel2()
	{
		System.out.println("id for TabPanel1 [" + getId() + "]");
	}

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param id
	 *            component id
	 * @param bookDetails
	 *            TODO
	 */
	public TabPanel2(BookDetails bookDetails, String id)
	{
		super();
		this.bookDetails = bookDetails;
		Book book = this.bookDetails.getBook(id);
		add(new Label("title", book.getTitle()));
		add(new Label("author", book.getAuthor()));
		add(new Label("fiction", Boolean.toString(book.getFiction())));
		add(BookDetails.link("companion", book.getCompanionBook(),
			getLocalizer().getString("noBookTitle", this)));
		add(BookDetails.link("related", book.getRelatedBook(),
			getLocalizer().getString("noBookTitle", this)));

		String writingStyles;
		final boolean hasStyles = (book.getWritingStyles() != null) &&
			(book.getWritingStyles().size() > 0);

		if (hasStyles)
		{
			StringList styles = new StringList();

			for (WritingStyle style : book.getWritingStyles())
			{
				styles.add(getLocalizer().getString(style.toString(), this));
			}

			writingStyles = styles.toString();
		}
		else
		{
			writingStyles = getLocalizer().getString("noWritingStyles", this);
		}

		Label writingStylesLabel = new Label("writingStyles", writingStyles);

		final AttributeModifier italic = new AttributeModifier("class", new Model<String>("italic"))
		{
			@Override
			public boolean isEnabled(Component component)
			{
				return !hasStyles;
			}
		};

		add(writingStylesLabel.add(italic));
		add(EditBook.link("edit", book.getId()));
	}

}