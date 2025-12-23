# sealed UiState (если sealed)
-keep class com.exxlexxlee.domain.model.UiState { *; }
-keep class com.exxlexxlee.domain.model.UiState$* { *; }

# Koin domain module (top-level Kotlin file)
-keep class com.exxlexxlee.domain.di.DomainModuleKt { *; }