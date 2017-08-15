package jp.ac.titech.cs.se.reqchecker.cabocha;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import jp.ac.titech.cs.se.reqchecker.util.Utils;
import lombok.Getter;

/**
 * The phrase structure of Cabocha.
 */
public class Phrase {
    /**
     * Raw string representing this phrase obtained from cabocha.
     */
    @Getter
    protected String raw;

    /**
     * The sentence to which this phrase belongs to.
     */
    protected final Sentence parent;

    /**
     * The phrase that this phrase affects.
     */
    @Getter
    protected Phrase forwardPhrase;

    /**
     * The phrases that affect this phrase.
     */
    @Getter
    protected final List<Phrase> backwardPhrases = new ArrayList<>();

    /**
     * Words included in this phrase.
     */
    @Getter
    protected final List<Word> words = new ArrayList<>();

    /**
     * Additional information obtained from the analysis.
     */
    protected String info;

    /**
     * The constructor.
     *
     * @param rawPhrase
     *            raw line obtained from cabocha
     * @param parent
     *            a Sentence object to which this phrase belongs
     */
    public Phrase(final String rawPhrase, final Sentence parent) {
        this.raw = rawPhrase;
        this.parent = parent;
        final String[] rawWords = rawPhrase.split("\n");
        this.info = rawWords[0];
        for (int i = 1; i < rawWords.length; i++) {
            words.add(Word.of(rawWords[i]));
        }

        mergeNumberWords();
        mergeSectionNumberWords();
    }

    /**
     * Checks whether this phrase is verb.
     * 
     * @return true if this phrase is verb.
     */
    public boolean isVerb() {
        // TODO Weak condition; the routine should be revised
        for (final Word w : words) {
            if (w.getPos().startsWith("動詞")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds the critical word of this phrase based on the phrase structure.
     * (e.g., "報告 し た と" to "報告", "思わ れ た" to "思われる")
     *
     * @return a string representing the critical word.
     */
    public String getCriticalWord() {
        final int size = words.size();
        final Word beg = getFirst();
        switch (beg.getPos()) {
        case "名詞":
        case "接頭詞":
            if (size == 1) {
                return beg.getNormalized();
            } else {
                int i = 1;
                while (i < size - 1 && words.get(i).isPosOf("名詞")) {
                    i++;
                }
                return words.get(i - 1).getNormalized();
            }
        case "動詞":
            if (size == 1) {
                return beg.getNormalized();
            } else {
                final StringBuilder sb = new StringBuilder();
                int i = 0;
                String pos;
                do {
                    final Word w = words.get(i);
                    if (w.getConjugationForm().equals("連用形") || w.getConjugationForm().equals("連用タ接続") || w.getConjugationForm().equals("仮定形") || w.getConjugationForm().equals("未然形")) {
                        sb.append(w.getNormalized());
                    } else if (!w.isDetailedPosOf("接尾")) {
                        sb.append(w);
                    }

                    i++;
                    final Word w1 = words.get(i);
                    pos = w1.getPos();
                    if (i == size - 1 && pos.equals("動詞")) {
                        sb.append(w1.getNormalized());
                    }
                } while (pos.equals("動詞") && i < size - 1);
                return sb.toString();
            }
        default:
            return "";
        }
    }

    /**
     * Adds a backward phrase.
     * 
     * @param phrase
     *            phrase to be added
     */
    public void addBackwardPhrase(final Phrase phrase) {
        backwardPhrases.add(phrase);
    }

    /**
     * Configures relationships between phrases.
     */
    public void setRelation() {
        final String[] data = raw.split(" ");
        final int index = Integer.parseInt(data[1].replaceAll("D", ""));
        if (index > 0) {
            forwardPhrase = parent.getPhrases().get(index);
            forwardPhrase.addBackwardPhrase(this);
        }
    }

    /**
     * Checks whether this phrase contains the given word.
     * 
     * @param word
     *            the word to be searched
     * @return true if it contains the given word
     */
    public boolean contains(final Word word) {
        return words.contains(word);
    }

    /**
     * Merges multiple number words.
     * 
     * Cabocha outputs multiple single letter numbers (e.g., "1" and "2") from a
     * single number (e.g., "12"). This method merges these words into a single
     * word object.
     */
    protected void mergeNumberWords() {
        for (int i = 0; i < words.size(); i++) {
            Word w = words.get(i);
            if (w.isPosOf("名詞") && w.isDetailedPosOf("数")) {
                final StringBuilder sb = new StringBuilder();
                do {
                    sb.append(w);
                    words.remove(i);
                    w = words.get(i);
                } while (w.isPosOf("名詞") && w.isDetailedPosOf("数"));
                words.add(i, Word.numberOf(sb.toString()));
            }
        }
    }

    /**
     * Merges section number words (e.g., "1", ".", "a") into one.
     */
    protected void mergeSectionNumberWords() {
        for (int i = 0; i < words.size(); i++) {
            Word w = words.get(i);
            if (w.isPosOf("名詞") && w.isDetailedPosOf("数")) {
                boolean afterDot = false;
                final StringBuilder sb = new StringBuilder(w.toString());
                words.remove(i);
                w = words.get(i);
                while (w.isPosOf("名詞")) {
                    if (!afterDot && w.equals(Word.DOT)) {
                        afterDot = true;
                    } else if (afterDot && w.isDetailedPosOf("数", "固有名詞")) {
                        afterDot = false;
                    } else {
                        break;
                    }
                    sb.append(words.get(i));
                    words.remove(i);
                    w = words.get(i);
                }
                words.add(i, Word.numberOf(sb.toString()));
            }
        }
    }

    @Override
    public String toString() {
        return StringUtils.join(words, "");
    }

    /**
     * Finds a phrase that affects this phrase using the given case.
     *
     * @param caseWord
     *            a case word. Intended to use static fields in Word class.
     * @return The found Phrase object. Otherwise, it returns null
     */
    public Phrase findPhraseWithCase(final Word caseWord) {
        for (final Phrase p : backwardPhrases) {
            if (p.contains(caseWord)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Finds phrases that affects this phrase using the given case by analyzing
     * parallel relation with a particle "と".
     *
     * @param caseWord
     *            a case Word
     * @return a list of phrases.
     */
    public List<Phrase> findPhrasesWithCase(final Word caseWord) {
        final List<Phrase> result = new ArrayList<>();
        final Phrase basePhrase = findPhraseWithCase(caseWord);
        if (basePhrase != null) {
            result.add(basePhrase);
            Phrase otherPhrase = basePhrase.findPhraseWithCase(Word.TO_PARALLEL);
            while (otherPhrase != null) {
                result.add(otherPhrase);
                otherPhrase = otherPhrase.findPhraseWithCase(Word.TO_PARALLEL);
            }
        }
        return result;
    }

    /**
     * Finds subjective phrases with considering parallel relation.
     *
     * @return a list of subjective phrases
     */
    public List<Phrase> findSubjectivePhrases() {
        return findPhrasesWithCase(Word.GA);
    }

    /**
     * Finds objective phrases with considering parallel relation.
     *
     * @return a list of objective phrases
     */
    public List<Phrase> getObjectivePhrases() {
        final List<Phrase> result = new ArrayList<>();
        for (final Word w : new Word[] { Word.WO, Word.MO, Word.TOSHITE }) {
            result.addAll(findPhrasesWithCase(w));
        }
        if (result.isEmpty()) {
            if (this.forwardPhrase != null) {
                final Word w = forwardPhrase.getFirst();
                if (forwardPhrase.isNoun() || w.isPosOf("名詞")) {
                    if (getLast().isPosOf("動詞", "助動詞")) {
                        // Except for "～場合" or "～とき"
                        if (!w.getType().equals("副詞可能") && !w.isDetailedPosOf("副詞可能")) {
                            result.add(forwardPhrase);
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * Returns the word occurring at the latest (most right).
     *
     * @return a Word object or null when no noun is found
     */
    public Word getLastNoun() {
        Word result = null;
        for (final Word w : words) {
            if (w.isPosOf("名詞")) {
                result = w;
            }
        }
        return result;
    }

    /**
     * Finds nouns, verbs, or adjectives occurring at the last.
     * 
     * @return the found word
     */
    public Word getLastCriticalWord() {
        Word result = null;
        for (final Word w : words) {
            if (w.isPosOf("名詞", "動詞", "形容詞")) {
                result = w;
            }
        }
        return result;
    }

    /**
     * Checks whether this phrase is noun.
     * 
     * @return true if this phrase is noun
     */
    public boolean isNoun() {
        for (int i = 0; i < words.size() - 1; i++) {
            if (!words.get(i).isPosOf("名詞")) {
                return false;
            }
        }
        return true;
    }

    public String toDetailString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(info);
        if (!words.isEmpty()) {
            sb.append(" [").append("\n");
            for (final Word w : words) {
                sb.append("  ").append(w.toDetailString()).append("\n");
            }
            sb.append("]");
        }
        return sb.toString();
    }

    /**
     * Returns the word occurring at the first position.
     * 
     * @return the found word
     */
    public Word getFirst() {
        return words.get(0);
    }

    /**
     * Returns the word occurring at the final position.
     * 
     * @return the found word
     */
    public Word getLast() {
        return Utils.last(words);
    }

    /**
     * Returns the set of all the words except for 助詞, 助動詞, 記号, or 接続詞.
     * 
     * @return a set of the found words
     */
    public Set<String> extractMeaningfulWords() {
        final Set<String> result = new HashSet<>();
        for (final Word w : words) {
            if (!w.isPosOf("助詞", "助動詞", "記号", "接続詞")) {
                result.add(w.getNormalized());
            }
        }
        return result;
    }

    /**
     * Checks whether this contains at least one of the given words.
     * 
     * @param words
     *            the query words
     * @return true if this contains at least one of the given words
     */
    public boolean containsAnyOf(final Word[] words) {
        for (final Word word : words) {
            if (contains(word)) {
                return true;
            }
        }
        return false;
    }
}
