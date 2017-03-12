package jp.ac.titech.cs.se.reqchecker.model;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import jp.ac.titech.cs.se.reqchecker.analyzer.DefinitionExtractor;
import jp.ac.titech.cs.se.reqchecker.cabocha.CabochaParser;
import jp.ac.titech.cs.se.reqchecker.cabocha.Sentence;
import lombok.Getter;

public class Requirement {
    /**
     * A Sentence object generated from the requirements sentence.
     */
    @Getter
    private final Sentence sentence;

    /**
     * A sentence string except for the number.
     */
    @Getter
    private final String rawSentence;

    /**
     * All the meaningful words.
     */
    @Getter
    private final Set<String> words;

    /**
     * The prefix number.
     */
    @Getter
    private final String number;

    /**
     * The type of the prefix number.
     */
    @Getter
    private final Type type;

    /**
     * Definitions occurring in this requirements sentence.
     */
    @Getter
    private final List<Definition> definitions;

    public enum Type {
        REQ, CHAPTER, SECTION, OTHER;

        public boolean isStructural() {
            return this == CHAPTER || this == SECTION;
        }
    }

    /**
     * The constructor.
     * 
     * @param line
     *            A string line in a requirements specification
     * @param parser
     *            A CabochaParser instance
     */
    public Requirement(final String line, final CabochaParser parser) {
        type = detectType(line);
        number = extractNumber(line, type);
        rawSentence = extractRawSentence(line, type);
        sentence = parser.parse(rawSentence);
        definitions = new DefinitionExtractor(sentence).extractDefinitions();
        words = sentence.extractMeaningfulWords();
    }

    private static final Pattern RE_REQ_NUMBER = Pattern.compile("[0-9a-zA-Z]+(\\.[0-9a-zA-Z]+)+\\.$");

    private static final Pattern RE_SECTION = Pattern.compile("[0-9a-zA-Z]+(\\.[0-9a-zA-Z]+)+$");

    private static final Pattern RE_CHAPTER = Pattern.compile("[0-9a-zA-Z]+\\.$");

    /**
     * Detects the type of the prefix number.
     * 
     * @param line
     *            A string line in a requirements specification
     * @return the type
     */
    protected static Type detectType(final String line) {
        final String prefix = line.split(" ")[0];
        if (prefix.contains("http://")) {
            return Type.OTHER;
        }
        if (RE_REQ_NUMBER.matcher(prefix).find()) {
            return Type.REQ;
        }
        if (RE_SECTION.matcher(prefix).find()) {
            return Type.SECTION;
        }
        if (RE_CHAPTER.matcher(prefix).find()) {
            return Type.CHAPTER;
        }
        return Type.OTHER;
    }

    /**
     * Extracts the ID number of the given requirements sentence.
     * 
     * @param line
     *            A string line in a requirements specification
     * @param type
     *            The type of the given sentence
     * @return String representing ID number
     */
    protected static String extractNumber(final String line, final Type type) {
        return type == Type.OTHER ? "" : line.split(" ")[0];
    }

    /**
     * Extracts the raw sentence from the given string by excluding its ID
     * number.
     * 
     * @param line
     *            A string line in a requirements specification
     * @param type
     *            The type of the given sentence
     * @return String representing requirements sentence
     */
    protected static String extractRawSentence(final String line, final Type type) {
        final int start = type == Type.OTHER ? 0 : 1;
        final String[] a = line.split(" ");
        return StringUtils.join(Arrays.copyOfRange(a, start, a.length));
    }
}
