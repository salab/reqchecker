package jp.ac.titech.cs.se.reqchecker.model;

import jp.ac.titech.cs.se.reqchecker.cabocha.Phrase;
import jp.ac.titech.cs.se.reqchecker.cabocha.Sentence;
import lombok.Getter;

public class Definition {
    @Getter
    private final Sentence sentence;

    @Getter
    private final Phrase definingInstance;

    @Getter
    private final Phrase description;

    public Definition(final Sentence sentence, final Phrase definingInstance, final Phrase description) {
        this.sentence = sentence;
        this.definingInstance = definingInstance;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("[%s] := [%s] (%s)", definingInstance, description, sentence);
    }

    public boolean matches(final Definition other) {
        return matchesSubject(other) && !matchesDescription(other); // && (getObjectCritical().isEmpty() ^ other.getObjectCritical().isEmpty());
    }

    public boolean matchesSubject(final Definition other) {
        return getDefiningInstanceCritical().equals(other.getDefiningInstanceCritical());
    }

    public boolean matchesDescription(final Definition other) {
        return getDescriptionCritical().equals(other.getDescriptionCritical());
    }

    public String getDescriptionCritical() {
        return description == null ? "" : description.getCriticalWord();
    }

    public String getDefiningInstanceCritical() {
        return definingInstance == null ? "" : definingInstance.getCriticalWord();
    }

    public String toHTML() {
        return "[" + sentence + "]<BR>";
    }
}
