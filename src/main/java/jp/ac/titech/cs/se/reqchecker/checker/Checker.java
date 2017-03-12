package jp.ac.titech.cs.se.reqchecker.checker;

import java.io.IOException;

import jp.ac.titech.cs.se.reqchecker.HTMLWriter;

public interface Checker {

    boolean check();

    void render(final HTMLWriter out) throws IOException;
}
