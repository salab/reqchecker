package jp.ac.titech.cs.se.reqchecker.model;

import java.util.ArrayList;
import java.util.List;

import jp.ac.titech.cs.se.reqchecker.cabocha.CabochaParser;
import jp.ac.titech.cs.se.reqchecker.model.Requirement.Type;
import jp.ac.titech.cs.se.reqchecker.util.Utils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Document {
    private final String path;

    @Getter
    private final List<Chapter> chapters;

    @Getter
    private final List<Requirement> requirements;

    public Document(final String path, final CabochaParser parser) {
        this.path = path;
        requirements = buildRequirements(parser);
        log.trace("Loaded: " + path);
        chapters = buildChapters(requirements);
    }

    private List<Requirement> buildRequirements(final CabochaParser parser) {
        final List<Requirement> result = new ArrayList<>();
        for (final String line : Utils.readLines(path)) {
            result.add(new Requirement(line, parser));
        }
        return result;
    }

    private List<Chapter> buildChapters(final List<Requirement> requirements) {
        final List<Chapter> result = new ArrayList<>();
        for (final Requirement req : requirements) {
            if (req.getType() == Type.CHAPTER) {
                result.add(new Chapter(req));
            } else if (!result.isEmpty()) {
                Utils.last(result).add(req);
            }
        }
        if (result.isEmpty()) {
            result.add(new Chapter(requirements));
        }
        return result;
    }

    public List<Definition> extractDefinitions() {
        final List<Definition> result = new ArrayList<>();
        for (final Requirement req : requirements) {
            result.addAll(req.getDefinitions());
        }
        return result;
    }
}
