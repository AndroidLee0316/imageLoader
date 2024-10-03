# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-optimizationpasses 5
-useuniqueclassmembernames
-keepattributes SourceFile,LineNumberTable
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-dontoptimize
-verbose
-dontshrink
-ignorewarnings
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keep interface com.pasc.lib.imageloader.PascCallback{*;}
-keep interface com.pasc.lib.imageloader.Target{*;}
-keepclasseswithmembers public class com.pasc.lib.imageloader.PascImageLoader{*;}
-keep class com.pasc.lib.imageloader.PascImageLoader$*{*;}

# glide
-keep class com.bumptech.glide.**{*;}
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}



