# ClockJacked ProGuard Rules

# kotlinx.serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keep,includedescriptorclasses class com.clockjacked.app.**$$serializer { *; }
-keepclassmembers class com.clockjacked.app.** {
    *** Companion;
}
-keepclasseswithmembers class com.clockjacked.app.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Glance widget classes
-keep class com.clockjacked.app.widget.** { *; }
