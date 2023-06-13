package jp.ac.titech.cs.se.reqchecker.model;

import jp.ac.titech.cs.se.reqchecker.cabocha.Phrase;
import jp.ac.titech.cs.se.reqchecker.cabocha.Sentence;
import lombok.ToString;

public abstract class Coordination {
    protected final Sentence sentence;

    public Coordination(final Sentence sentence) {
        this.sentence = sentence;
    }

    protected abstract String getClassName(final Phrase phrase);

    public String toHTML() {
        final StringBuilder sb = new StringBuilder();
        sb.append("-[");
        for (final Phrase phrase : sentence.getPhrases()) {
            final String className = getClassName(phrase);
            if (className != null) {
                sb.append(" <span class=\"").append(className).append("\">");
                sb.append(phrase).append("</span>");
            } else {
                sb.append(" ").append(phrase);
            }
        }
        sb.append(" ]<br>");
        return sb.toString();
    }

    @ToString
    public static class TypeA extends Coordination {
        private final Phrase modifier;
        private final Phrase parallel1;
        private final Phrase parallel2;

        public TypeA(final Sentence sentence, final Phrase modifier, final Phrase parallel1, final Phrase parallel2) {
            super(sentence);
            this.modifier = modifier;
            this.parallel1 = parallel1;
            this.parallel2 = parallel2;
        }

        @Override
        protected String getClassName(final Phrase phrase) {
            if (phrase == modifier) {
                return "modifier";
            } else if (phrase == parallel1 || phrase == parallel2) {
                return "parallel";
            } else {
                return null;
            }
        }
    }

    @ToString
    public static class TypeB extends Coordination {
        private final Phrase parallel1;
        private final Phrase parallel2;
        private final Phrase lastParallel;

        public TypeB(final Sentence sentence, final Phrase parallel1, final Phrase parallel2, final Phrase lastParallel) {
            super(sentence);
            this.parallel1 = parallel1;
            this.parallel2 = parallel2;
            this.lastParallel = lastParallel;
        }

        @Override
        protected String getClassName(final Phrase phrase) {
            if (phrase == lastParallel) {
                return "lastParallel";
            } else if (phrase == parallel1 || phrase == parallel2) {
                return "parallel";
            } else {
                return null;
            }
        }
    }

    @ToString
    public static class TypeC extends Coordination {
        private final Phrase modifier;
        private final Phrase parallel;

        public TypeC(final Sentence sentence, final Phrase modifier, final Phrase parallel) {
            super(sentence);
            this.modifier = modifier;
            this.parallel = parallel;
        }

        @Override
        protected String getClassName(final Phrase phrase) {
            if (phrase == modifier) {
                return "modifier";
            } else if (phrase == parallel) {
                return "parallel";
            } else {
                return null;
            }
        }
    }
}
