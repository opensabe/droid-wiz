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

-keepclassmembers class **{ public static com.meituan.robust.ChangeQuickRedirect *;}

-keep class org.json.** {*;}

# 保留所需的信息，以便 Crashlytics 生成人能阅读的崩溃报告
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception

-keep public class * implements java.io.Serializable {*;}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

-dontwarn android.support.**
-keep class android.support.** { *; }
-keep interface android.support.** { *; }

# Keep file names/line numbers
-keepattributes SourceFile,LineNumberTable
# Keep custom exceptions (opt)
-keep public class * extends java.lang.Exception

-keep public class com.android.installreferrer.** { *; }
-keep public class com.miui.referrer.** {*;}
-keep class * implements androidx.viewbinding.ViewBinding {
    *;
}

-keep class org.litepal.** {*;}
-keep class * extends org.litepal.crud.LitePalSupport {*;}

-keep class androidx.navigation.** { *; }
