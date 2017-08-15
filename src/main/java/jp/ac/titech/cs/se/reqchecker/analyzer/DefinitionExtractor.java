package jp.ac.titech.cs.se.reqchecker.analyzer;

import java.util.ArrayList;
import java.util.List;

import jp.ac.titech.cs.se.reqchecker.cabocha.Phrase;
import jp.ac.titech.cs.se.reqchecker.cabocha.Sentence;
import jp.ac.titech.cs.se.reqchecker.cabocha.Word;
import jp.ac.titech.cs.se.reqchecker.model.Definition;
import jp.ac.titech.cs.se.reqchecker.util.Utils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefinitionExtractor {

    protected List<Definition> definitions = new ArrayList<>();

    protected final Sentence sentence;

    public DefinitionExtractor(final Sentence sentence) {
        this.sentence = sentence;
    }

    public List<Definition> extractDefinitions() {
        search_TOHA_DEARU();
        search_TOHA_WOSASU();
        search_HA_TOSURU();
        search_GA_DEARU();
        return definitions;
    }

    private void search_GA_DEARU() {
        Phrase definingInstance = null;
        for (final Phrase phrase : sentence.getPhrases()) {
            if (definingInstance != null) {
                if (phrase.contains(Word.DE_AUXILIARY) && phrase.contains(Word.ARU)) {
                    final Definition d = new Definition(sentence, definingInstance, phrase);
                    log.debug("Definition found: {}", d);
                    definitions.add(d);
                    definingInstance = null;
                }
            } else if (phrase.contains(Word.GA) || (phrase.contains(Word.HA) && !phrase.contains(Word.TO_CASE))) {
                definingInstance = phrase;
            }
        }
    }

    private void search_TOHA_WOSASU() {
        Phrase definingInstance = null;
        for (final Phrase phrase : sentence.getPhrases()) {
            if (definingInstance != null) {
                if (phrase.contains(Word.WO) && phrase.getForwardPhrase().contains(Word.SASU)) {
                    final Definition d = new Definition(sentence, definingInstance, phrase);
                    log.debug("Definition found: {}", d);
                    definitions.add(d);
                    definingInstance = null;
                }
            } else if (phrase.contains(Word.TO_CASE) && phrase.contains(Word.HA)) {
                definingInstance = phrase;
            }
        }
    }

    private void search_TOHA_DEARU() {
        Phrase definingInstance = null;
        for (final Phrase phrase : sentence.getPhrases()) {
            if (definingInstance != null) {
                if (phrase.contains(Word.DE_AUXILIARY) && phrase.contains(Word.ARU)) {
                    final Definition d = new Definition(sentence, definingInstance, phrase);
                    log.debug("Definition found: {}", d);
                    definitions.add(d);
                    definingInstance = null;
                }
            } else if (phrase.contains(Word.TO_CASE) && phrase.contains(Word.HA)) {
                definingInstance = phrase;
            }
        }
    }

    private void search_HA_TOSURU() {
        // A は B とする
        // A は B と呼ぶ
        Phrase description = null;
        for (final Phrase phrase : sentence.getPhrases()) {
            if (description != null) {
                final Phrase forward = phrase.getForwardPhrase();
                if (forward == null) {
                    break;
                }
                if (isTO_kaku(phrase.getLast())) {
                    final String verb = forward.getCriticalWord();
                    log.trace(verb);
                    if (verb.equals("する") || verb.equals("呼ぶ")) {
                        final Definition d = new Definition(sentence, phrase, description);
                        log.debug("Definition found: {}", d);
                        definitions.add(d);
                        description = null;
                    }
                }
            } else if (phrase.contains(Word.HA)) {
                description = phrase;
            }
        }
    }

    private Phrase findModifier(final Phrase subject) {
        final List<Phrase> backwards = subject.getBackwardPhrases();
        if (!backwards.isEmpty()) {
            final Phrase m = Utils.last(backwards);
            if (m.getLast().isPosOf("助動詞")) {
                return m;
            }
        }
        return null;
    }

    /**
     * Checks whether the given word is Binding particle "と".
     */
    private boolean isTO_kaku(final Word w) {
        return w.getOriginal().equals("と") && w.isPosOf("助詞") && w.isDetailedPosOf("格助詞");
    }
}
