package jp.ac.titech.cs.se.reqchecker.wordnet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.cmu.lti.jawjaw.db.SQL;
import edu.cmu.lti.jawjaw.db.SenseDAO;
import edu.cmu.lti.jawjaw.db.WordDAO;
import edu.cmu.lti.jawjaw.pobj.Sense;
import edu.cmu.lti.jawjaw.pobj.Word;

public class JawjawUtils {
    public static final String FIND_SYNSETS_BY_SYNSET = "SELECT lemma FROM sense, word WHERE synset=? AND word.lang =? AND sense.wordid = word.wordid";

    /**
     * Fetches the synonyms of the given word.
     * 
     * @param word
     *            the query word
     * @param lang
     *            the language parameter
     * @return set of synonym strings
     */
    public static Set<String> getSynonyms(final String word, final String lang) {
        final List<Word> words = WordDAO.findWordsByLemma(word);
        if (words.isEmpty()) {
            final Set<String> result = new HashSet<>();
            result.add(word);
            return result;
        }
        final List<Sense> senses = SenseDAO.findSensesByWordid(words.get(0).getWordid());
        return getMergedSynset(senses, lang);
    }

    protected static Set<String> getMergedSynset(final List<Sense> senses, final String lang) {
        final HashSet<String> result = new HashSet<>();
        for (final Sense sense : senses) {
            result.addAll(findSynsetsBySynsetId(sense.getSynset(), lang));
        }
        return result;
    }

    protected static List<String> findSynsetsBySynsetId(final String synset, final String lang) {
        final List<String> result = new ArrayList<>();
        try {
            final PreparedStatement ps = getPreparedStatement(FIND_SYNSETS_BY_SYNSET);
            ps.setString(1, synset);
            ps.setString(2, lang);
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(rs.getString(1));
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static PreparedStatement getPreparedStatement(final String query) {
        final Connection conn = SQL.getInstance().getConnection();
        try {
            return conn.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
