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

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Page;

/**
 * Dumps a wicket component/page hierarchy to text, eventually writing down the class and the model
 * value as a string.
 * <p>
 * Each line in the dump follow the <code>componentId(class) 'value'</code> format.
 * </p>
 * <p>
 * The class can be reused for multiple prints, but it's not thread safe
 * </p>
 */
public class WicketHierarchyPrinter
{
	static final Pattern NEWLINE = Pattern.compile("\\n", Pattern.MULTILINE);

	PrintStream out;

	boolean valueDumpEnabled;

	boolean classDumpEnabled;

	/**
	 * Utility method to dump a single component/page to standard output
	 * 
	 * @param c
	 * @param dumpClass
	 * @param dumpValue
	 */
	public static void print(Component c, boolean dumpClass, boolean dumpValue)
	{
		WicketHierarchyPrinter printer = new WicketHierarchyPrinter();
		printer.setClassDumpEnabled(dumpClass);
		printer.setValueDumpEnabled(dumpValue);
		if (c instanceof Page)
		{
			printer.print(c);
		}
		else
		{
			printer.print(c);
		}
	}

	/**
	 * Creates a printer that will dump to standard output
	 */
	public WicketHierarchyPrinter()
	{
		out = System.out;
	}

	/**
	 * Creates a printer that will dump to the specified print stream
	 */
	public WicketHierarchyPrinter(PrintStream out)
	{
		this.out = out;
	}

	/**
	 * Set to true if you want to see the model values in the dump
	 * 
	 * @param valueDumpEnabled
	 */
	public void setValueDumpEnabled(boolean valueDumpEnabled)
	{
		this.valueDumpEnabled = valueDumpEnabled;
	}

	/**
	 * Set to true if you want to see the component classes in the dump
	 * 
	 * @param classDumpEnabled
	 */
	public void setClassDumpEnabled(boolean classDumpEnabled)
	{
		this.classDumpEnabled = classDumpEnabled;
	}

	/**
	 * Prints the component containment hierarchy
	 * 
	 * @param c
	 */
	public void print(Component c)
	{
		walkHierarchy(c, 0);
	}

	/**
	 * Walks down the containment hierarchy depth first and prints each component found
	 */
	private void walkHierarchy(Component c, int level)
	{
		printComponent(c, level);
		if (c instanceof MarkupContainer)
		{
			MarkupContainer mc = (MarkupContainer)c;
			for (Iterator it = mc.iterator(); it.hasNext();)
			{
				walkHierarchy((Component)it.next(), level + 1);
			}
		}
	}

	/**
	 * Prints a single component
	 */
	private void printComponent(Component c, int level)
	{
		if (c instanceof Page)
			out.print(tab(level) + "PAGE_ROOT");
		else
			out.print(tab(level) + c.getId());

		if (classDumpEnabled)
		{
			String className;
			if (c.getClass().isAnonymousClass())
			{
				className = c.getClass().getSuperclass().getName();
			}
			else
			{
				className = c.getClass().getName();
			}

			out.print("(" + className + ")");
		}

		if (valueDumpEnabled)
		{
			try
			{
				String value = NEWLINE.matcher(c.getDefaultModelObjectAsString()).replaceAll(
					"\\\\n");
				out.print(" '" + value + "'");
			}
			catch (Exception e)
			{
				out.print(" 'ERROR_RETRIEVING_MODEL " + e.getMessage() + "'");
			}
		}
		out.println();
	}

	/**
	 * Generates three spaces per level
	 */
	String tab(int level)
	{
		char[] spaces = new char[level * 3];
		Arrays.fill(spaces, ' ');
		return new String(spaces);
	}
}