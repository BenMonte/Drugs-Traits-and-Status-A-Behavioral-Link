package reporting;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Lightweight recursive descent JSON parser, no external libraries needed
public class JsonParser {

    private final String json;
    private int pos;

    private JsonParser(String json) {
        this.json = json;
        this.pos  = 0;
    }

    // Parses a full JSON string and returns the top level value
    public static Object parse(String json) {
        JsonParser parser = new JsonParser(json);
        Object result = parser.readValue();
        return result;
    }

    // Convenience casts for pulling typed values out of parsed JSON
    @SuppressWarnings("unchecked")
    public static Map<String, Object> asObject(Object o) {
        return (Map<String, Object>) o;
    }

    @SuppressWarnings("unchecked")
    public static List<Object> asArray(Object o) {
        return (List<Object>) o;
    }

    public static String asString(Object o) {
        return o == null ? "" : o.toString();
    }

    public static double asDouble(Object o) {
        if (o instanceof Number) return ((Number) o).doubleValue();
        try { return Double.parseDouble(o.toString()); }
        catch (NumberFormatException e) { return 0; }
    }

    public static int asInt(Object o) {
        return (int) asDouble(o);
    }

    // Dispatches to the correct reader based on the next character
    private Object readValue() {
        skipWhitespace();
        char c = peek();
        if (c == '{')  return readObject();
        if (c == '[')  return readArray();
        if (c == '"')  return readString();
        if (c == 't' || c == 'f') return readBoolean();
        if (c == 'n')  return readNull();
        return readNumber();
    }

    private Map<String, Object> readObject() {
        Map<String, Object> map = new LinkedHashMap<>();
        expect('{');
        skipWhitespace();
        if (peek() == '}') { advance(); return map; }

        while (true) {
            skipWhitespace();
            String key = readString();
            skipWhitespace();
            expect(':');
            Object value = readValue();
            map.put(key, value);
            skipWhitespace();
            if (peek() == ',') { advance(); continue; }
            break;
        }
        expect('}');
        return map;
    }

    private List<Object> readArray() {
        List<Object> list = new ArrayList<>();
        expect('[');
        skipWhitespace();
        if (peek() == ']') { advance(); return list; }

        while (true) {
            list.add(readValue());
            skipWhitespace();
            if (peek() == ',') { advance(); continue; }
            break;
        }
        expect(']');
        return list;
    }

    private String readString() {
        expect('"');
        StringBuilder sb = new StringBuilder();
        while (pos < json.length()) {
            char c = json.charAt(pos++);
            if (c == '"') return sb.toString();
            if (c == '\\') {
                char esc = json.charAt(pos++);
                switch (esc) {
                    case '"':  sb.append('"');  break;
                    case '\\': sb.append('\\'); break;
                    case '/':  sb.append('/');  break;
                    case 'n':  sb.append('\n'); break;
                    case 't':  sb.append('\t'); break;
                    case 'r':  sb.append('\r'); break;
                    default:   sb.append(esc);  break;
                }
            } else {
                sb.append(c);
            }
        }
        throw error("Unterminated string");
    }

    private double readNumber() {
        int start = pos;
        if (peek() == '-') advance();
        while (pos < json.length() && isDigit(peek())) advance();
        if (pos < json.length() && peek() == '.') {
            advance();
            while (pos < json.length() && isDigit(peek())) advance();
        }
        if (pos < json.length() && (peek() == 'e' || peek() == 'E')) {
            advance();
            if (pos < json.length() && (peek() == '+' || peek() == '-')) advance();
            while (pos < json.length() && isDigit(peek())) advance();
        }
        return Double.parseDouble(json.substring(start, pos));
    }

    private boolean readBoolean() {
        if (json.startsWith("true", pos))  { pos += 4; return true; }
        if (json.startsWith("false", pos)) { pos += 5; return false; }
        throw error("Expected boolean");
    }

    private Object readNull() {
        if (json.startsWith("null", pos)) { pos += 4; return null; }
        throw error("Expected null");
    }

    private char peek() {
        if (pos >= json.length()) throw error("Unexpected end of input");
        return json.charAt(pos);
    }

    private void advance() { pos++; }

    private void expect(char c) {
        skipWhitespace();
        if (pos >= json.length() || json.charAt(pos) != c) {
            throw error("Expected '" + c + "'");
        }
        pos++;
    }

    private void skipWhitespace() {
        while (pos < json.length() && Character.isWhitespace(json.charAt(pos))) {
            pos++;
        }
    }

    private boolean isDigit(char c) { return c >= '0' && c <= '9'; }

    private RuntimeException error(String msg) {
        return new RuntimeException("JSON parse error at position " + pos + ": " + msg);
    }
}
