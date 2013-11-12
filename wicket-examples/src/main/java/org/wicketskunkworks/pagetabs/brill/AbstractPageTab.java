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
package org.wicketskunkworks.pagetabs.brill;

import org.apache.wicket.Page;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.model.IModel;

/**
 * Convenience class that takes care of common IPageTab functionality
 * 
 * @see IPageTab
 * 
 * @author Igor Vaynberg (ivaynberg)
 * 
 */
public abstract class AbstractPageTab implements IPageTab {
	private static final long serialVersionUID = 1L;
	IModel<String> title;
	IModel<String> badge;

	/**
	 * Constructor
	 * 
	 * @param title
	 *            IModel used to represent the title of the tab. Must contain a
	 *            string
	 */
	public AbstractPageTab(IModel<String> title) {
		this.title = title;
	}

	public AbstractPageTab(IModel<String> title, IModel<String> badge) {
		this.title = title;
		this.badge = badge;
	}

	/**
	 * @see org.wicketskunkworks.pagetabs.brill.IPageTab#getTitle()
	 */
	public IModel<String> getTitle() {
		return title;
	}

	@Override
	public IModel<String> getBadge() {
		return badge;
	}

	@Override
	public boolean isBadgeVisible() {
		return isVisible() && badge != null;
	}

	/**
	 * @see org.wicketskunkworks.pagetabs.brill.IPageTab#isVisible()
	 */
	public boolean isVisible() {
		return true;
	}

	/**
	 * Default implementation that returns an empty parameters object
	 * 
	 * @see org.wicketskunkworks.pagetabs.brill.IPageTab#getPageParameters()
	 */
	@Override
	public PageParameters getPageParameters() {
		return new PageParameters();
	}

	/**
	 * @see org.wicketskunkworks.pagetabs.brill.IPageTab#getPage()
	 */
	public abstract Class<? extends Page> getPage();

}