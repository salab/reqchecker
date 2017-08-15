package jp.ac.titech.cs.se.reqchecker.cabocha;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * The word structure of Cabocha.
 */
@EqualsAndHashCode(of = { "detailedPos", "pos", "lemma" })
public class Word {
    /**
     * Binding particle "は".
     */
    public static final Word HA = new Word("は", "助詞,係助詞,*,*,*,*,は,ハ,ワ", "O");

    /**
     * Binding particle "も".
     */
    public static final Word MO = new Word("も", "助詞,係助詞,*,*,*,*,も,モ,モ", "O");

    /**
     * Binding particle "が".
     */
    public static final Word GA = new Word("が", "助詞,格助詞,一般,*,*,*,が,ガ,ガ", "O");

    /**
     * Binding particle "を".
     */
    public static final Word WO = new Word("を", "助詞,格助詞,一般,*,*,*,を,ヲ,ヲ", "O");

    /**
     * Binding particle "の".
     */
    public static final Word NO = new Word("の", "助詞,連体化,*,*,*,*,の,ノ,ノ", "O");

    /**
     * Parallel particle "と".
     */
    public static final Word TO_PARALLEL = new Word("と", "助詞,並立助詞,*,*,*,*,と,ト,ト", "O");

    /**
     * Case-making particle "に".
     */
    public static final Word NI = new Word("に", "助詞,格助詞,一般,*,*,*,に,ニ,ニ", "O");

    /**
     * Case-making particle "で".
     */
    public static final Word DE_CASE = new Word("で", "助詞,格助詞,一般,*,*,*,で,デ,デ", "O");

    /**
     * Case-making particle "へ".
     */
    public static final Word HE = new Word("へ", "助詞,格助詞,一般,*,*,*,へ,ヘ,エ", "O");

    /**
     * Case-making particle "から".
     */
    public static final Word KARA = new Word("から", "助詞,格助詞,一般,*,*,*,から,カラ,カラ", "O");

    /**
     * Case-making particle "と".
     */
    public static final Word TO_CASE = new Word("と", "助詞,格助詞,一般,*,*,*,と,ト,ト", "O");

    /**
     * Supplementary particle "まで".
     */
    public static final Word MADE = new Word("まで", "助詞,副助詞,*,*,*,*,まで,マデ,マデ", "O");

    /**
     * Case-making particle "より".
     */
    public static final Word YORI = new Word("より", "助詞,格助詞,一般,*,*,*,より,ヨリ,ヨリ", "O");

    /**
     * Case-making particle "にて".
     */
    public static final Word NITE = new Word("にて", "助詞,格助詞,一般,*,*,*,にて,ニテ,ニテ", "O");

    /**
     * Auxiliary verb "で".
     */
    public static final Word DE_AUXILIARY = new Word("で", "助動詞,*,*,*,特殊・ダ,連用形,だ,デ,デ", "O");

    /**
     * Auxiliary verb "ある".
     */
    public static final Word ARU = new Word("ある", "助動詞,*,*,*,五段・ラ行アル,基本形,ある,アル,アル", "O");

    /**
     * Verb "さす".
     */
    public static final Word SASU = new Word("さす", "動詞,自立,*,*,五段・サ行,基本形,さす,サス,サス", "O");

    /**
     * Verb "れる".
     */
    public static final Word RERU = new Word("れる", "動詞,接尾,*,*,一段,連用形,れる,レ,レ", "O");

    /**
     * Verb "する".
     */
    public static final Word SURU = new Word("する", "動詞,自立,*,*,サ変・スル,基本形,する,スル,スル", "O");

    /**
     * Noun ".".
     */
    public static final Word DOT = new Word(".", "名詞,サ変接続,*,*,*,*,*", "O");

    /**
     * Conjunction "および".
     */
    public static final Word OYOBI = new Word("および", "接続詞,*,*,*,*,*,および,オヨビ,オヨビ", "O");

    /**
     * Particle "として".
     */
    public static final Word TOSHITE = new Word("として", "助詞,格助詞,連語,*,*,*,として,トシテ,トシテ", "O");

    /**
     * Symbol "・".
     */
    public static final Word MIDDLE = new Word("・", "記号,一般,*,*,*,*,・,・,・", "O");

    /**
     * Noun "/".
     */
    public static final Word SLASH_NOUN = new Word("/", "名詞,サ変接続,*,*,*,*,*", "O");

    /**
     * Symbol "／".
     */
    public static final Word SLASH_SYMBOL = new Word("／", "記号,一般,*,*,*,*,／,／,／", "O");

    /**
     * The original representation of this word.
     */
    @Getter
    private final String original;

    /**
     * The lemma (stemmed, normalized) of this word.
     */
    @Getter
    private final String lemma;

    /**
     * The Part-of-Speech tag of this word.
     */
    @Getter
    private final String pos;

    /**
     * The detailed Part-of-Speech tag of this word.
     */
    @Getter
    private final String detailedPos;

    /**
     * The conjugation pattern (e.g., "サ行変格活用")
     */
    @Getter
    private final String conjugationPattern;

    /**
     * The conjugation form.
     */
    @Getter
    private final String conjugationForm;

    /**
     * Extra information.
     */
    @Getter
    private final String extra;

    /**
     * Raw string representation obtained from Cabocha.
     */
    private final String raw;

    /**
     * The type of this word.
     */
    @Getter
    private final String type;

    /**
     * The constructor.
     * 
     * @param original
     *            word string occurring in the sentence
     * @param grammaticalData
     *            grammatical data of the word
     * @param extra
     *            extra information
     */
    private Word(final String original, final String grammaticalData, final String extra) {
        raw = original + "\t" + grammaticalData + "\t" + extra;
        final String[] data = grammaticalData.split(",");
        this.original = original;
        this.extra = extra;

        this.pos = data[0];
        this.detailedPos = data[1];
        this.type = data[2];
        this.conjugationPattern = data[4];
        // this.conjugatePattern = data[5];
        this.conjugationForm = data[5];
        this.lemma = data[6];
    }

    /**
     * Parses a word line and returns Word object.
     * 
     * @param raw
     *            a string of line obtained from Cabocha
     * @return a Word object
     */
    public static Word of(final String raw) {
        final String[] data = raw.split("\t");
        return new Word(data[0], data[1], data[2]);
    }

    public static Word numberOf(final String number) {
        return new Word(number, "名詞,数,*,*,*,*,*", "0");
    }

    /**
     * Checks whether this word represents a person.
     * 
     * @return true if this word represents a person
     */
    public boolean isPerson() {
        return extra.contains("PERSON");
    }

    /**
     * Checks whether this word represents a location.
     * 
     * @return true if this word represents a location
     */
    public boolean isLocation() {
        return extra.contains("LOCATION");
    }

    /**
     * Checks whether this word represents an organization.
     * 
     * @return true if this word represents an organization
     */
    public boolean isOrganization() {
        return extra.contains("ORGANIZATION");
    }

    @Override
    public String toString() {
        return original;
    }

    public String toDetailString() {
        return raw;
    }

    public boolean isDetailedPosOf(final String... poses) {
        for (final String p : poses) {
            if (detailedPos.equals(p)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPosOf(final String... poses) {
        for (final String p : poses) {
            if (pos.equals(p)) {
                return true;
            }
        }
        return false;
    }
}
