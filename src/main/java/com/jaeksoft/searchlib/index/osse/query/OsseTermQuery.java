/**   
 * License Agreement for OpenSearchServer
 *
 * Copyright (C) 2013 Emmanuel Keller / Jaeksoft
 * 
 * http://www.open-search-server.com
 * 
 * This file is part of OpenSearchServer.
 *
 * OpenSearchServer is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 * OpenSearchServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with OpenSearchServer. 
 *  If not, see <http://www.gnu.org/licenses/>.
 **/

package com.jaeksoft.searchlib.index.osse.query;

import java.io.UnsupportedEncodingException;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Map;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.TermQuery;

import com.jaeksoft.searchlib.SearchLibException;
import com.jaeksoft.searchlib.index.osse.OsseTokenTermUpdate.OsseTermBuffer;
import com.jaeksoft.searchlib.index.osse.api.OsseCursor;
import com.jaeksoft.searchlib.index.osse.api.OsseErrorHandler;
import com.jaeksoft.searchlib.index.osse.api.OsseIndex;
import com.jaeksoft.searchlib.index.osse.api.OsseIndex.FieldInfo;
import com.jaeksoft.searchlib.index.osse.api.OsseLibrary;
import com.jaeksoft.searchlib.index.osse.memory.OsseFastStringArray;
import com.jaeksoft.searchlib.util.IOUtils;

public class OsseTermQuery extends OsseAbstractQuery {

	final private String field;
	final private String text;

	OsseTermQuery(TermQuery termQuery) {
		final Term term = termQuery.getTerm();
		field = term.field();
		text = term.text();
	}

	@Override
	public void execute(OsseIndex index, Map<String, FieldInfo> fieldMap,
			OsseErrorHandler error) throws SearchLibException {
		if (fieldMap == null)
			throw new SearchLibException("Unknown field: ", field);
		FieldInfo fieldInfo = fieldMap.get(field);
		if (fieldInfo == null)
			throw new SearchLibException("Unknown field: ", field);
		OsseFastStringArray osseFastStringArray = null;
		try {
			CharsetEncoder encoder = Charset.forName("UTF-8").newEncoder();
			osseFastStringArray = new OsseFastStringArray(new OsseTermBuffer(
					encoder, text));
			cursor = new OsseCursor(index, error, fieldInfo.id,
					osseFastStringArray, 1,
					OsseLibrary.OSSCLIB_QCURSOR_UI32BOP_AND);
		} catch (UnsupportedEncodingException e) {
			throw new SearchLibException(e);
		} catch (CharacterCodingException e) {
			throw new SearchLibException(e);
		} finally {
			IOUtils.close(osseFastStringArray);
		}
	}
}
