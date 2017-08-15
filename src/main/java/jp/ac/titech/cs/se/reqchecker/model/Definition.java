package jp.ac.titech.cs.se.reqchecker.model;

import jp.ac.titech.cs.se.reqchecker.cabocha.Phrase;
import jp.ac.titech.cs.se.reqchecker.cabocha.Sentence;
import lombok.Getter;

public class Definition {

    private final Sentence sentence;

    @Getter
    private final Phrase definingInstance;

    @Getter
    private final Phrase object;

    @Getter
    private final Phrase modifier;

    public Definition(final Sentence sentence, final Phrase definingInstance, final Phrase object, final Phrase modifier) {
        this.sentence = sentence;
        this.definingInstance = definingInstance;
        this.object = object;
        this.modifier = modifier;
    }

    public boolean matches(final Definition other) {
        return matchesSubject(other) && !matchesModifier(other) && (getObjectCritical().isEmpty() ^ other.getObjectCritical().isEmpty());
    }

    public boolean matchesSubject(final Definition other) {
        return getDefiningInstanceCritical().equals(other.getDefiningInstanceCritical());
    }

    public boolean matchesModifier(final Definition other) {
        return getModifierCritical().equals(other.getModifierCritical());
    }

    public String getModifierCritical() {
        return modifier == null ? "" : modifier.getCriticalWord();
    }

    public String getObjectCritical() {
        return object == null ? "" : object.getCriticalWord();
    }

    public String getDefiningInstanceCritical() {
        return definingInstance == null ? "" : definingInstance.getCriticalWord();
    }

    public String toHTML() {
        return "[" + sentence + "]<BR>";
    }
}
