package cn.allay.identifier;

import javax.annotation.Nullable;

import static cn.allay.identifier.Identifier.DEFAULT_NAMESPACE;
import static cn.allay.identifier.Identifier.NAMESPACE_SEPARATOR;
import static cn.allay.utils.StringUtils.fastTwoPartSplit;

/**
 * Author: daoge_cmd <br>
 * Date: 2023/3/4 <br>
 * Allay Project <br>
 * <p>
 * Some helper for {@link Identifier}
 */
public final class IdentifierUtils {
    public static Identifier splitOn(String id, String delimiter) {
        return new Identifier(fastTwoPartSplit(id, delimiter, DEFAULT_NAMESPACE));
    }

    @Nullable
    public static Identifier tryParse(String id) {
        try {
            return new Identifier(id);
        } catch (InvalidIdentifierException lv) {
            return null;
        }
    }

    @Nullable
    public static Identifier of(String namespace, String path) {
        try {
            return new Identifier(namespace, path);
        } catch (InvalidIdentifierException lv) {
            return null;
        }
    }

    public static boolean isValid(String id) {
        String[] strings = fastTwoPartSplit(id, NAMESPACE_SEPARATOR, DEFAULT_NAMESPACE);
        return isNamespaceValid(strings[0].isEmpty() ? DEFAULT_NAMESPACE : strings[0]) && isPathValid(strings[1]);
    }

    public static boolean isCharValid(char c) {
        return c >= '0' && c <= '9' || c >= 'a' && c <= 'z' || c == '_' || c == ':' || c == '/' || c == '.' || c == '-';
    }

    public static void assertValid(String id) {
        String[] strings = fastTwoPartSplit(id, NAMESPACE_SEPARATOR, DEFAULT_NAMESPACE);
        var namespace = strings[0].isEmpty() ? DEFAULT_NAMESPACE : strings[0];
        var path = strings[1];
        if (!isNamespaceValid(namespace)) {
            throw new InvalidIdentifierException("Non [a-z0-9_.-] character in namespace of location: " + namespace + NAMESPACE_SEPARATOR + path);
        }
        if (!isPathValid(path)) {
            throw new InvalidIdentifierException("Non [a-z0-9/._-] character in path of location: " + namespace + NAMESPACE_SEPARATOR + path);
        }
    }

    static boolean isPathValid(String path) {
        for (int i = 0; i < path.length(); ++i) {
            if (isPathCharacterValid(path.charAt(i))) continue;
            return false;
        }
        return true;
    }

    static boolean isPathCharacterValid(char character) {
        return character == '_' || character == '-' || character >= 'a' && character <= 'z' || character >= '0' && character <= '9' || character == '/' || character == '.';
    }

    static boolean isNamespaceValid(String namespace) {
        for (int i = 0; i < namespace.length(); ++i) {
            if (isNamespaceCharacterValid(namespace.charAt(i))) continue;
            return false;
        }
        return true;
    }

    static boolean isNamespaceCharacterValid(char character) {
        return character == '_' || character == '-' || character >= 'a' && character <= 'z' || character >= '0' && character <= '9' || character == '.';
    }
}