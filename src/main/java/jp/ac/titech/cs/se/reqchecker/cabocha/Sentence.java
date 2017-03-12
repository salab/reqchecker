package jp.ac.titech.cs.se.reqchecker.cabocha;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;

/**
 * The sentence structure of Cabocha.
 */
public class Sentence {
    /**
     * The phrases that this sentence has.
     */
    @Getter
    private final List<Phrase> phrases = new ArrayList<>();

    @Getter
    private final String raw;

    /**
     * The constructor.
     *
     * @param rawSentence
     *            a raw sentence string
     * @param cabochaOutput
     *            a parsed sentence line obtained from cabocha
     */
    public Sentence(final String rawSentence, final String cabochaOutput) {
        this.raw = rawSentence;

        for (final String rawPhrase : splitCabochaOutput(cabochaOutput)) {
            final Phrase p = new Phrase(rawPhrase, this);
            phrases.add(p);
        }
        for (final Phrase p : phrases) {
            p.setRelation();
        }
    }

    /**
     * Splits a sentence string obtained from Cabocha into phrase strings.
     * 
     * @param cabochaOutput
     *            raw string obtained using Cabocha
     * @return a list of line strings
     */
    public static List<String> splitCabochaOutput(final String cabochaOutput) {
        final List<String> result = new ArrayList<>();
        final String[] data = cabochaOutput.split("\\* ");
        // Ignore 0th index
        for (int i = 1; i < data.length; i++) {
            result.add(data[i]);
        }
        return result;
    }

    /**
     * Returns verb phrases.
     * 
     * @return a list of verb phrases
     */
    public List<Phrase> getVerbPhrases() {
        final List<Phrase> result = new ArrayList<>();
        for (final Phrase p : phrases) {
            if (p.isVerb()) {
                result.add(p);
            }
        }
        return result;
    }

    public String toDetailString() {
        final List<String> strings = new ArrayList<>();
        for (final Phrase p : phrases) {
            strings.add(p.toDetailString());
        }
        return StringUtils.join(strings, "\n");
    }

    @Override
    public String toString() {
        return StringUtils.join(phrases, "");
    }

    /**
     * Extracts all the meaningful words.
     * 
     * @return a set of meaningful word strings
     */
    public Set<String> extractMeaningfulWords() {
        final Set<String> result = new HashSet<>();
        for (final Phrase p : getPhrases()) {
            result.addAll(p.extractMeaningfulWords());
        }
        return result;
    }
}