package jp.ac.titech.cs.se.reqchecker.model;

import jp.ac.titech.cs.se.reqchecker.cabocha.Phrase;
import jp.ac.titech.cs.se.reqchecker.cabocha.Sentence;
import lombok.Getter;

public class Definition {

    private final Sentence sentence;

    @Getter
    private final Phrase subject;

    @Getter
    private final Phrase object;

    @Getter
    private final Phrase modifier;

    public Definition(final Sentence sentence, final Phrase subject, final Phrase object, final Phrase modifier) {
        this.sentence = sentence;
        this.subject = subject;
        this.object = object;
        this.modifier = modifier;
    }

    public boolean matches(final Definition other) {
        return matchesSubject(other) && !matchesModifier(other) && (getObjectCritical().isEmpty() ^ other.getObjectCritical().isEmpty());
    }

    public boolean matchesSubject(final Definition other) {
        return getSubjectCritical().equals(other.getSubjectCritical());
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

    public String getSubjectCritical() {
        return subject == null ? "" : subject.getCriticalWord();
    }

    public String toHTML() {
        return "[" + sentence + "]<BR>";
    }
}
