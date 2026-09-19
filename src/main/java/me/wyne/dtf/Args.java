package me.wyne.dtf;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class Args {

    public static final String COLON_DELIMITER = ":";
    public static final String SPACE_DELIMITER = " ";
    public static final String COLON_OR_SPACE_DELIMITER = "[: ]";

    private final List<String> args;

    public Args(String string) {
        var split = string.split(COLON_OR_SPACE_DELIMITER);
        args = List.of(split);
    }

    public Args(String string, String regex) {
        var split = string.split(regex);
        args = List.of(split);
    }

    private Args(List<String> args) {
        this.args = args;
    }

    public static Args splitOutsideBrackets(String string, char delimiter, UnaryOperator<String> mapper) {
        var split = new ArrayList<String>();
        var depth = 0;
        var start = 0;
        for (var i = 0; i < string.length(); i++) {
            var c = string.charAt(i);
            if (c == '{') depth++;
            else if (c == '}' && depth > 0) depth--;
            else if (c == delimiter && depth == 0) {
                split.add(mapper.apply(string.substring(start, i)));
                start = i + 1;
            }
        }
        split.add(mapper.apply(string.substring(start)));
        return new Args(List.copyOf(split));
    }

    @Nullable
    public String getNullable(int index) {
        return args.size() <= index ? null : args.get(index);
    }

    @NotNull
    public String get(int index) {
        return get(index, "");
    }

    @NotNull
    public String get(int index, String def) {
        if (index >= args.size())
            return def;
        return args.get(index).trim();
    }

    public Args skip(int i) {
        return new Args(args.subList(i, args.size()));
    }

    public int size() {
        return args.size();
    }

    public List<String> getArgs() {
        return args;
    }

}
