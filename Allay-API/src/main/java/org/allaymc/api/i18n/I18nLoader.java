package org.allaymc.api.i18n;

import java.util.Map;

/**
 * @author daoge_cmd
 */
public interface I18nLoader {
    Map<String, String> getLangMap(LangCode langCode);
}
