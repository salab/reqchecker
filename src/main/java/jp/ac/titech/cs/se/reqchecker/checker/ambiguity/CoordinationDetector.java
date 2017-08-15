package jp.ac.titech.cs.se.reqchecker.checker.ambiguity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.ac.titech.cs.se.reqchecker.HTMLWriter;
import jp.ac.titech.cs.se.reqchecker.cabocha.Phrase;
import jp.ac.titech.cs.se.reqchecker.cabocha.Sentence;
import jp.ac.titech.cs.se.reqchecker.cabocha.Word;
import jp.ac.titech.cs.se.reqchecker.checker.AbstractSentenceChecker;
import jp.ac.titech.cs.se.reqchecker.model.Coordination;
import jp.ac.titech.cs.se.reqchecker.util.Utils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CoordinationDetector extends AbstractSentenceChecker {

    protected List<Coordination> result = new ArrayList<>();

    public CoordinationDetector(final Sentence sentence) {
        super(sentence);
    }

    @Override
    public boolean check() {
        for (final Phrase phrase : sentence.getPhrases()) {
            check_AnoBtoC(phrase);
            check_AnoBslashC(phrase);
            check_AoyobiBtenC(phrase);
        }
        return !result.isEmpty();
    }

    @Override
    public void render(final HTMLWriter out) throws IOException {
        out.writeln("<B>係り受け関係が複数</B>考えられる可能性があります。<BR>");
        out.writeln("<a href=\"quality/unambiguity.html\"><SPAN class=\"quality\" title=\"語句の係り受け関係が複数存在するか\">非あいまい性a</SPAN></a>に問題がある可能性があります。<BR>");
        for (final Coordination c : result) {
            out.writeln(c.toHTML());
        }
        out.writeln("---<BR>");
    }

    protected void check_AoyobiBtenC(final Phrase phrase) {
        // A および B 、 C
        // TODO A、およびB

        // * 0 3D 1/1 1.272748
        // りんご 名詞,一般,*,*,*,*,りんご,リンゴ,リンゴ O
        // および 接続詞,*,*,*,*,*,および,オヨビ,オヨビ O
        // * 1 2D 0/0 1.293621
        // みかん 名詞,一般,*,*,*,*,みかん,ミカン,ミカン O
        // ， 記号,読点,*,*,*,*,，,，,， O
        // * 2 3D 0/1 0.000000
        // いちご 名詞,一般,*,*,*,*,いちご,イチゴ,イチゴ O
        // を 助詞,格助詞,一般,*,*,*,を,ヲ,ヲ O
        // * 3 -1D 0/0 0.000000
        // 食べる 動詞,自立,*,*,一段,基本形,食べる,タベル,タベル O
        // EOS

        if (!phrase.contains(Word.OYOBI)) {
            return;
        }
        log.trace("1st round passed");
        final String pos = phrase.getLastCriticalWord().getPos();

        final Phrase forward = phrase.getForwardPhrase();
        log.trace(forward.getLast().getDetailedPos() + forward.getLastCriticalWord().getPos());
        if (!(forward.getLast().isDetailedPosOf("読点") && forward.getLastCriticalWord().isPosOf(pos))) {
            return;
        }
        log.trace("2nd round passed");

        final Phrase forward2 = forward.getForwardPhrase();
        if (forward2.getLast().isDetailedPosOf("格助詞") && forward2.getLastCriticalWord().isPosOf(pos)) {
            log.trace("3rd round passed");
            result.add(new Coordination.TypeB(sentence, phrase, forward, forward2));
        }
    }

    protected void check_AnoBtoC(final Phrase phrase) {
        // A (の または 助動詞) B と C
        // A (の または 助動詞) B および C

        if (!isModifier(phrase)) {
            return;
        }
        log.trace("1st round passed");

        final Phrase forward = phrase.getForwardPhrase();
        if (forward == null) {
            return;
        }
        if (!(forward.contains(Word.TO_PARALLEL) && forward.isNoun())) {
            // 第二段階突破 並立助詞 "と"の場合
            log.trace("2nd round passed");
            final Phrase forward2 = forward.getForwardPhrase();
            if (forward2 == null) {
                return;
            }

            if (!(forward2.contains(Word.WO) && forward2.isNoun())) {
                return;
            }
            log.trace("3rd round passed");

            final Phrase adverb = Utils.last(forward2.getBackwardPhrases());
            if (!adverb.toString().equals(forward.toString())) {
                if (!isModifier(adverb)) {
                    // 第四段階突破
                    log.trace("4th round passed");
                    result.add(new Coordination.TypeA(sentence, phrase, forward, forward2));
                }
            }
        } else if (forward.contains(Word.OYOBI) && forward.isNoun()) {
            // 第二段階突破 接続詞 "および"の場合
            log.trace("2nd round passed");
            final Phrase parallel = Utils.last(forward.getForwardPhrase().getBackwardPhrases());
            if (parallel == null) {
                return;
            }
            if (parallel.contains(Word.WO) && parallel.isNoun()) {
                // 第三段階突破
                log.trace("3rd round passed");
                final List<Phrase> tempTemp = parallel.getBackwardPhrases();
                if (!tempTemp.isEmpty() && isModifier(Utils.last(tempTemp))) {
                    return;
                }
                log.trace("4th round passed");
                result.add(new Coordination.TypeA(sentence, phrase, forward, parallel));
            }
        }
    }

    protected static final Word[] PARALLEL_SYMBOLS = { Word.MIDDLE, Word.SLASH_SYMBOL, Word.SLASH_NOUN };

    protected void check_AnoBslashC(final Phrase phrase) {
        // A (の または 助動詞) B ・ C
        // A (の または 助動詞) B / C

        if (!isModifier(phrase)) {
            return;
        }
        log.trace("1st round passed");

        final Phrase forward = phrase.getForwardPhrase();
        if (forward == null || !forward.containsAnyOf(PARALLEL_SYMBOLS)) {
            return;
        }
        log.trace("2nd round passed");

        // 3rd round (C)
        if (forward.getLast().getDetailedPos().equals("格助詞")) {
            result.add(new Coordination.TypeC(sentence, phrase, forward));
        }

        // 3rd round (A)
        final Phrase forward2 = forward.getForwardPhrase();
        if (forward2 == null) {
            return;
        }
        if (!forward2.contains(Word.WO) || !forward2.isNoun()) {
            return;
        }
        log.trace("3rd round passed");

        final Phrase adverb = Utils.last(forward2.getBackwardPhrases());
        if (adverb.toString().equals(forward.toString())) {
            return;
        }
        if (isModifier(adverb)) {
            return;
        }
        result.add(new Coordination.TypeA(sentence, phrase, forward, forward2));
        log.trace("4th round passed");
    }

    protected boolean isModifier(final Phrase adverb) {
        return adverb.getLast().isPosOf("助動詞") || adverb.contains(Word.NO);
    }
}
