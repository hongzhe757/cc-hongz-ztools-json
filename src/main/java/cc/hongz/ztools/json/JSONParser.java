package cc.hongz.ztools.json;

import cc.hongz.ztools.core.lang.Filter;
import cc.hongz.ztools.core.lang.mutable.Mutable;
import cc.hongz.ztools.core.lang.mutable.MutablePair;

/**
 * JSON字符串解析器。
 *
 * @author looly
 * @since 5.8.0
 */
public class JSONParser {

    /**
     * 创建JSONParser。
     *
     * @param tokener {@link JSONTokener}
     * @return JSONParser
     */
    public static JSONParser of(final JSONTokener tokener) {
        return new JSONParser(tokener);
    }

    private final JSONTokener tokener;

    /**
     * 构造。
     *
     * @param tokener {@link JSONTokener}
     */
    public JSONParser(final JSONTokener tokener) {
        this.tokener = tokener;
    }

    // region parseTo

    /**
     * 解析{@link JSONTokener}中的字符到目标的{@link JSONObject}中。
     *
     * @param jsonObject {@link JSONObject}
     * @param filter     键值对过滤编辑器，可以通过实现此接口，完成解析前对键值对的过滤和修改操作，{@code null}表示不过滤
     */
    public void parseTo(final JSONObject jsonObject, final Filter<MutablePair<String, Object>> filter) {
        final JSONTokener x = this.tokener;

        if (x.nextClean() != '{') {
            throw x.syntaxError("A JSONObject text must begin with '{'");
        }

        char prev;
        char c;
        String key;
        while (true) {
            prev = x.getPrevious();
            c = x.nextClean();
            switch (c) {
                case 0:
                    throw x.syntaxError("A JSONObject text must end with '}'");
                case '}':
                    return;
                case '{':
                case '[':
                    if (prev == '{') {
                        throw x.syntaxError("A JSONObject can not directly nest another JSONObject or JSONArray.");
                    }
                default:
                    x.back();
                    key = x.nextStringValue();
            }

            // The key is followed by ':'.

            c = x.nextClean();
            if (c != ':') {
                throw x.syntaxError("Expected a ':' after a key");
            }

            jsonObject.set(key, x.nextValue(), filter, jsonObject.getConfig().isCheckDuplicate());

            // Pairs are separated by ','.

            switch (x.nextClean()) {
                case ';':
                case ',':
                    if (x.nextClean() == '}') {
                        // issue#2380
                        // 尾后逗号（Trailing Commas），JSON中虽然不支持，但是ECMAScript 2017支持，此处做兼容。
                        return;
                    }
                    x.back();
                    break;
                case '}':
                    return;
                default:
                    throw x.syntaxError("Expected a ',' or '}'");
            }
        }
    }

    /**
     * 解析JSON字符串到{@link JSONArray}中。
     *
     * @param jsonArray {@link JSONArray}
     * @param filter    键值对过滤编辑器，可以通过实现此接口，完成解析前对值的过滤和修改操作，{@code null} 表示不过滤
     */
    public void parseTo(final JSONArray jsonArray, final Filter<Mutable<Object>> filter) {
        final JSONTokener x = this.tokener;

        if (x.nextClean() != '[') {
            throw x.syntaxError("A JSONArray text must start with '['");
        }
        if (x.nextClean() != ']') {
            x.back();
            for (; ; ) {
                if (x.nextClean() == ',') {
                    x.back();
                    jsonArray.addRaw(JSONNull.NULL, filter);
                } else {
                    x.back();
                    jsonArray.addRaw(x.nextValue(), filter);
                }
                switch (x.nextClean()) {
                    case ',':
                        if (x.nextClean() == ']') {
                            return;
                        }
                        x.back();
                        break;
                    case ']':
                        return;
                    default:
                        throw x.syntaxError("Expected a ',' or ']'");
                }
            }
        }
    }
    // endregion
}
