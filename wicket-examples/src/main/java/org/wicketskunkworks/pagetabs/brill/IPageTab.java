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
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Interface used to represent a single tab in a TabbedPanel
 * 
 * @see org.wicketskunkworks.pagetabs.brill.PageTabbedPanel
 * @see org.wicketskunkworks.pagetabs.brill.AbstractPageTab
 * 
 * @author Igor Vaynberg (ivaynberg)
 * 
 */
public interface IPageTab extends org.apache.wicket.util.io.IClusterable {
	/**
	 * @return IModel used to represent the title of the tab. Must contain a
	 *         string.
	 */
	IModel<String> getTitle();

	/**
	 * @return IModel used to represent the badge text of the tab. Must contain
	 *         a string.
	 */
	IModel<String> getBadge();

	/**
	 * @param panelId
	 *            returned panel MUST have this id
	 * @return a Panel object that will be placed as the content panel
	 */
	Class<? extends Page> getPage();

	PageParameters getPageParameters();

	/**
	 * @return whether this tab should be visible
	 */
	boolean isVisible();

	/**
	 * @return whether this tab's badge should be visible
	 */
	boolean isBadgeVisible();
}