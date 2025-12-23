# Keep LocaleHelper class and its methods
-keep class com.hwasfy.localize.util.LocaleHelper { *; }
-keepclassmembers class com.hwasfy.localize.util.LocaleHelper { *; }

# Keep all classes in the localize package
-keep class com.hwasfy.localize.** { *; }

# Keep LanguageManager
-keep class com.hwasfy.localize.api.LanguageManager { *; }
-keepclassmembers class com.hwasfy.localize.api.LanguageManager { *; }

# Keep SupportedLocales enum
-keep class com.hwasfy.localize.util.SupportedLocales { *; }
-keepclassmembers enum com.hwasfy.localize.util.SupportedLocales { *; }

