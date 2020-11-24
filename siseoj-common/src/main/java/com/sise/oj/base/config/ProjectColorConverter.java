package com.sise.oj.base.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.CompositeConverter;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiElement;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.ansi.AnsiStyle;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author CiJee
 * @version 1.0
 */
public class ProjectColorConverter extends CompositeConverter<ILoggingEvent> {

    private static final Map<String, AnsiElement> ELEMENTS = new HashMap<>(7);
    private static final Map<Integer, AnsiElement> LEVELS = new HashMap<>(4);

    static {
        ELEMENTS.put("faint", AnsiStyle.FAINT);
        ELEMENTS.put("red", AnsiColor.RED);
        ELEMENTS.put("green", AnsiColor.GREEN);

        ELEMENTS.put("yellow", AnsiColor.YELLOW);
        ELEMENTS.put("blue", AnsiColor.BLUE);
        ELEMENTS.put("magenta", AnsiColor.MAGENTA);
        ELEMENTS.put("cyan", AnsiColor.CYAN);

        LEVELS.put(Level.ERROR_INTEGER, AnsiColor.RED);
        LEVELS.put(Level.INFO_INTEGER, AnsiColor.BLUE);
        LEVELS.put(Level.WARN_INTEGER, AnsiColor.BRIGHT_YELLOW);
        LEVELS.put(Level.DEBUG_INTEGER, AnsiColor.WHITE);
    }

    protected static AnsiElement getLevel(String firstOption, ILoggingEvent event) {
        AnsiElement element = ELEMENTS.get(firstOption);
        element = element == null ? LEVELS.get(event.getLevel().toInteger()) : element;
        element = (element != null) ? element : AnsiColor.GREEN;
        return element;
    }

    @Override
    protected String transform(ILoggingEvent event, String in) {
        return AnsiOutput.toString(getLevel(getFirstOption(), event), in);
    }
}