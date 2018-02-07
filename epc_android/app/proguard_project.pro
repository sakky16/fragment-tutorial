# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/trisys/Android/Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#progurrd for fabric
-keepattributes *Annotation*


#proguard for custom dialog
-keep class dmax.dialog.** {
    *;
}
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class org.junit.** { *; }
-dontwarn org.junit.**

-keep class junit.** { *; }
-dontwarn junit.**
-keep class com.trisysit.epc_android.SqliteDatabase.** { *;}
-keep class com.trisysit.epc_android.model.** { *;}
-keep class com.trisysit.epc_android.serverModel.** { *;}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
