package jp.ac.titech.cs.se.reqchecker.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class Chapter {
    @Getter
    private final List<Requirement> requirements = new ArrayList<>();

    @Getter
    private final String name;

    @Getter
    private final String number;

    public Chapter(final Requirement req) {
        requirements.add(req);
        name = req.getRawSentence();
        number = req.getNumber();
    }

    public Chapter(final List<Requirement> reqs) {
        requirements.addAll(reqs);
        name = "要求仕様";
        number = "";
    }

    public boolean add(final Requirement req) {
        return getRequirements().add(req);
    }

    public boolean isRequirements() {
        return name.contains("要求") || name.contains("仕様");
    }

    public List<Definition> extractDefinitions() {
        final List<Definition> result = new ArrayList<>();
        for (final Requirement req : requirements) {
            if (!req.getType().isStructural()) {
                result.addAll(req.getDefinitions());
            }
        }
        return result;
    }
}
