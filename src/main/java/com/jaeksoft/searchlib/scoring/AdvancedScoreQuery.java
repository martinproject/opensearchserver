/**   
 * License Agreement for OpenSearchServer
 *
 * Copyright (C) 2012-2014 Emmanuel Keller / Jaeksoft
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

package com.jaeksoft.searchlib.scoring;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.function.CustomScoreProvider;
import org.apache.lucene.search.function.CustomScoreQuery;

public class AdvancedScoreQuery extends CustomScoreQuery {

	private static final long serialVersionUID = -8913463918851953771L;

	private final AdvancedScoreItem[] scoreItems;

	private AdvancedScoreItemValue[] scoreItemValues;

	private final String name;

	private class AdvancedScoreProvider extends CustomScoreProvider {

		public AdvancedScoreProvider(final IndexReader reader)
				throws IOException {
			super(reader);
			scoreItemValues = new AdvancedScoreItemValue[scoreItems.length];
			int i = 0;
			for (AdvancedScoreItem scoreItem : scoreItems)
				scoreItemValues[i++] = new AdvancedScoreItemValue(scoreItem,
						reader);
		}

		final private Explanation customExplanation(final int doc) {
			Explanation expl = new Explanation(0, "sum of:");
			float sc = 0;
			for (AdvancedScoreItemValue scoreItemValue : scoreItemValues) {
				Explanation e = scoreItemValue.getExplanation(doc);
				sc += e.getValue();
				expl.addDetail(e);
			}
			expl.setValue(sc);
			return expl;
		}

		final private float customScore(final int doc) {
			float sc = 0;
			for (AdvancedScoreItemValue scoreItemValue : scoreItemValues)
				sc += scoreItemValue.getValue(doc);
			return sc;
		}

		@Override
		final public Explanation customExplain(final int doc,
				final Explanation subQueryExpl, final Explanation valSrcExpl) {
			Explanation expl = new Explanation(0, "product of:");
			Explanation e1 = customExplanation(doc);
			expl.addDetail(e1);
			expl.addDetail(subQueryExpl);
			expl.addDetail(valSrcExpl);
			expl.setValue((e1.getValue() * subQueryExpl.getValue() * valSrcExpl
					.getValue()));
			return expl;

		}

		@Override
		final public float customScore(final int doc,
				final float subQueryScore, final float valSrcScore) {
			return customScore(doc) * subQueryScore * valSrcScore;
		}

		@Override
		final public Explanation customExplain(final int doc,
				final Explanation subQueryExpl, final Explanation[] valSrcExpls)
				throws IOException {
			Explanation expl = new Explanation(0, "product of:");
			Explanation e1 = customExplanation(doc);
			expl.addDetail(e1);
			expl.addDetail(subQueryExpl);
			float sc = e1.getValue() * subQueryExpl.getValue();
			for (Explanation valSrcExpl : valSrcExpls) {
				sc *= valSrcExpl.getValue();
				expl.addDetail(valSrcExpl);
			}
			expl.setValue(sc);
			return expl;
		}

		@Override
		final public float customScore(final int doc,
				final float subQueryScore, final float[] valSrcScores) {
			float sc = customScore(doc) * subQueryScore;
			for (float valSrcScore : valSrcScores)
				sc *= valSrcScore;
			return sc;
		}
	}

	public AdvancedScoreQuery(final Query subQuery,
			final AdvancedScore advancedScore) {
		super(subQuery);
		scoreItems = advancedScore.getArray();
		name = computeName();
	}

	@Override
	final public CustomScoreProvider getCustomScoreProvider(
			final IndexReader reader) {
		try {
			return new AdvancedScoreProvider(reader);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	final private String computeName() {
		StringBuilder sb = new StringBuilder("advscore(");
		for (AdvancedScoreItem scoreItem : scoreItems) {
			if (sb.length() > 9)
				sb.append('+');
			sb.append(scoreItem.name());
		}
		sb.append(')');
		return sb.toString();
	}

	@Override
	public final String name() {
		return name;
	}
	/**
	 * 
	 */

}
