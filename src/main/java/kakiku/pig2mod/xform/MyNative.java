package kakiku.pig2mod.xform;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public final class MyNative {
    private static boolean gIsReady = false;

    public static boolean isReady() {
        return gIsReady;
    }

    public static native int getNumber();

    public static native String[] getProcessModules();

    public static native void test01();

    public static void test01_java() {
    }

    static {
        if (System.getProperty("os.name", "").toLowerCase().contains("win")) {
            try {
                InputStream in = MyNative.class.getResourceAsStream("/assets/pig2mod/libs/MyNative.dll");
                if (in == null) {
                    throw new IOException("MyNative.dll not found in jar");
                }

                File temp = File.createTempFile("MyNative", ".dll");
                temp.deleteOnExit();
                Files.copy(in, temp.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.load(temp.getAbsolutePath());
                gIsReady = true;
            } catch (Throwable var2) {
                MyLib2.SystemExitForDebug();
            }
        }
    }
}
