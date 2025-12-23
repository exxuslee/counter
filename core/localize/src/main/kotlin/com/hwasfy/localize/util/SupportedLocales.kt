package com.hwasfy.localize.util

import com.hwasfy.localize.R
import java.util.Locale

enum class SupportedLocales(val tag: String, val locale: Locale, val icon: Int) {

    EN_US("en-US", Locale.forLanguageTag("en-US"), R.drawable.icon_32_flag_usa),
    DE_DE("de-DE", Locale.forLanguageTag("de-DE"), R.drawable.icon_32_flag_germany),
    ES_ES("es-ES", Locale.forLanguageTag("es-ES"), R.drawable.icon_32_flag_spain),
    FA_IR("fa-IR", Locale.forLanguageTag("fa-IR"), R.drawable.icon_32_flag_iran),
    FR_FR("fr-FR", Locale.forLanguageTag("fr-FR"), R.drawable.icon_32_flag_france),
    HI_IN("hi-IN", Locale.forLanguageTag("hi-IN"), R.drawable.icon_32_flag_india),
    JA_JP("ja-JP", Locale.forLanguageTag("ja-JP"), R.drawable.icon_32_flag_japan),
    KO_KR("ko-KR", Locale.forLanguageTag("ko-KR"), R.drawable.icon_32_flag_korea),
    PT_BR("pt-BR", Locale.forLanguageTag("pt-BR"), R.drawable.icon_32_flag_brazil),
    RU_RU("ru-RU", Locale.forLanguageTag("ru-RU"), R.drawable.icon_32_flag_russia),
    TR_TR("tr-TR", Locale.forLanguageTag("tr-TR"), R.drawable.icon_32_flag_turkey),
    UK_UA("uk-UA", Locale.forLanguageTag("uk-UA"), R.drawable.icon_32_flag_ukraine),
    UR_PK("ur-PK", Locale.forLanguageTag("ur-PK"), R.drawable.icon_32_flag_india),
    ZH_CN("zh-CN", Locale.forLanguageTag("zh-CN"), R.drawable.icon_32_flag_china);

    companion object {
        fun fromTag(tag: String): SupportedLocales {
            val locale = Locale.forLanguageTag(tag)
            return entries.find { it.locale == locale }
                ?: entries.find { it.locale.language == locale.language }
                ?: EN_US
        }
    }
}

