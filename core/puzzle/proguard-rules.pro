# Secp256k1 JNI
-keep class fr.acinq.secp256k1.** { *; }
-dontwarn fr.acinq.secp256k1.**

# Не удалять native методы
-keepclasseswithmembers class * {
    native <methods>;
}

-keepclassmembers class fr.acinq.secp256k1.Secp256k1 {
    *;
}

-keepclassmembers class fr.acinq.secp256k1.Secp256k1$Companion {
    *;
}


