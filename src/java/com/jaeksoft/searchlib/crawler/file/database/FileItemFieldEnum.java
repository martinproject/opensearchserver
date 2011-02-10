/**   
 * License Agreement for Jaeksoft OpenSearchServer
 *
 * Copyright (C) 2008-2011 Emmanuel Keller / Jaeksoft
 * 
 * http://www.open-search-server.com
 * 
 * This file is part of Jaeksoft OpenSearchServer.
 *
 * Jaeksoft OpenSearchServer is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 * Jaeksoft OpenSearchServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Jaeksoft OpenSearchServer. 
 *  If not, see <http://www.gnu.org/licenses/>.
 **/

package com.jaeksoft.searchlib.crawler.file.database;

import java.util.ArrayList;
import java.util.List;

import com.jaeksoft.searchlib.util.ExtensibleEnum;

public class FileItemFieldEnum extends ExtensibleEnum<FileItemFieldEnum> {

	private final static List<FileItemFieldEnum> list = new ArrayList<FileItemFieldEnum>();

	public final static FileItemFieldEnum repository = new FileItemFieldEnum(
			"repository");

	public final static FileItemFieldEnum name = new FileItemFieldEnum("name");

	public final static FileItemFieldEnum uri = new FileItemFieldEnum("uri");

	public final static FileItemFieldEnum directory = new FileItemFieldEnum(
			"directory");

	public final static FileItemFieldEnum subDirectory = new FileItemFieldEnum(
			"subDirectory");

	public final static FileItemFieldEnum lang = new FileItemFieldEnum("lang");

	public final static FileItemFieldEnum langMethod = new FileItemFieldEnum(
			"langMethod");

	public final static FileItemFieldEnum contentLength = new FileItemFieldEnum(
			"contentLength");

	public final static FileItemFieldEnum parserStatus = new FileItemFieldEnum(
			"parserStatus");

	public final static FileItemFieldEnum fetchStatus = new FileItemFieldEnum(
			"fetchStatus");

	public final static FileItemFieldEnum indexStatus = new FileItemFieldEnum(
			"indexStatus");

	public final static FileItemFieldEnum crawlDate = new FileItemFieldEnum(
			"repository");

	public final static FileItemFieldEnum fileSystemDate = new FileItemFieldEnum(
			"fileSystemDate");

	public final static FileItemFieldEnum fileSize = new FileItemFieldEnum(
			"fileSize");

	public final static FileItemFieldEnum fileExtension = new FileItemFieldEnum(
			"fileExtension");

	public final static FileItemFieldEnum fileType = new FileItemFieldEnum(
			"fileType");

	protected FileItemFieldEnum(String name) {
		super(name);
	}

	@Override
	protected List<FileItemFieldEnum> getValues() {
		return list;
	}

}
