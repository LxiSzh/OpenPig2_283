package kakiku.pig2mod.xform;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public final class MyWatcher {
    private static volatile Set<String> gBaselineDLLs = new HashSet<>();

    private static void setPhase2() {
        System.setProperty(MyCheck.C.dec("g9uEF4TPNCNs6iYP4GbTQ2KziEv+PO97nuUgZfaeNQU="), MyCheck.C.dec("ZzCJ2L0uyyeK/4YqxMatqQ=="));
    }

    private static boolean isPhase2() {
        return MyCheck.C.dec("ZzCJ2L0uyyeK/4YqxMatqQ==")
            .equals(System.getProperty(MyCheck.C.dec("g9uEF4TPNCNs6iYP4GbTQ2KziEv+PO97nuUgZfaeNQU="), MyCheck.C.dec("Ztr3rTHd8j+PB0fYX02zhw==")));
    }

    public static void startDllWatchPhase1() {
        if (MyNative.isReady()) {
            gBaselineDLLs.addAll(Arrays.asList(MyNative.getProcessModules()));
            Thread t = new Thread(() -> {
                while (true) {
                    Set<String> DLLsAdded = new HashSet<>(Arrays.asList(MyNative.getProcessModules()));
                    if (isPhase2()) {
                        return;
                    }

                    DLLsAdded.removeAll(gBaselineDLLs);
                    if (containsBadPhase1(DLLsAdded)) {
                        doSomething();
                    }

                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException var2) {
                    }
                }
            });
            t.setDaemon(true);
            t.start();
        }
    }

    private static boolean containsBadPhase1(Set<String> DLLsAdded) {
        for (String DLLAdded : DLLsAdded) {
            String name = normalizePath(DLLAdded);
            if (!name.isEmpty()) {
                if (name.endsWith(MyCheck.C.dec("TcmEf7JZ0Zn31yIVCvOM4OaVRrR9woOtgI/cAkuBf9E="))) {
                    return true;
                }

                if (!name.contains(MyCheck.C.dec("DK2qCgU3Ow6ySWHNNkpngw=="))
                    && !name.contains(MyCheck.C.dec("3J4H5pL4REa7OujikGhH9g=="))
                    && !name.contains(MyCheck.C.dec("v1J3orrXaO+8db38M5/CcA=="))
                    && !name.contains(
                        normalizePath(
                            System.getProperty(MyCheck.C.dec("9ykjF+nsgPOvtUN1b8cNuv134QGFFuSvwIGzNbBjy8U="), MyCheck.C.dec("Ztr3rTHd8j+PB0fYX02zhw=="))
                        )
                    )
                    && !name.contains(
                        normalizePath(
                            System.getProperty(MyCheck.C.dec("6b22oz7Efpj9M8ENdG1TCw=="), MyCheck.C.dec("Ztr3rTHd8j+PB0fYX02zhw=="))
                                + MyCheck.C.dec("1sF7Y4iZ6AepE0sFEYtTGw==")
                        )
                    )
                    && !name.endsWith(MyCheck.C.dec("E4QnU7AkGCUwAPECAly00g=="))
                    && !name.endsWith(MyCheck.C.dec("iO1mHKZh1VmfQeoJ4gnVXQ=="))
                    && !name.endsWith(MyCheck.C.dec("xRgPkr4B4T99JeJRp9v1yg=="))
                    && !name.contains(MyCheck.C.dec("YZHXMzHd3EoSw1A+7EYWyw=="))
                    && !name.contains(MyCheck.C.dec("JcClRE+MFpqebYlAikp8Uc0f9beBfuToKMK4HgWOFOg="))) {
                    if (name.contains(MyCheck.C.dec("/yObsJDiXoUXt1otKNmb6w=="))
                        || name.contains(MyCheck.C.dec("vTk6T7QkzUFsN/64GuxSFw=="))
                        || name.contains(MyCheck.C.dec("ZOosGVhUt2DiZmztxPrJWw=="))
                        || name.contains(MyCheck.C.dec("B1D/30KQhrtdsOvimkdeRQ=="))) {
                        return true;
                    }

                    MyLib2.SystemExitForDebug();
                }
            }
        }

        return false;
    }

    public static void startDllWatchPhase2() {
        setPhase2();
        if (MyNative.isReady()) {
            gBaselineDLLs.addAll(Arrays.asList(MyNative.getProcessModules()));
            Thread t = new Thread(() -> {
                while (true) {
                    Set<String> DLLsAdded = new HashSet<>(Arrays.asList(MyNative.getProcessModules()));
                    DLLsAdded.removeAll(gBaselineDLLs);
                    if (containsBadPhase2(DLLsAdded)) {
                        doSomething();
                    }

                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException var2) {
                    }
                }
            });
            t.setDaemon(true);
            t.start();
        }
    }

    private static boolean containsBadPhase2(Set<String> DLLsAdded) {
        for (String DLLAdded : DLLsAdded) {
            String name = normalizePath(DLLAdded);
            if (!name.isEmpty()) {
                if (name.endsWith(MyCheck.C.dec("TcmEf7JZ0Zn31yIVCvOM4OaVRrR9woOtgI/cAkuBf9E="))) {
                    return true;
                }

                if (!name.contains(MyCheck.C.dec("DK2qCgU3Ow6ySWHNNkpngw=="))
                    && !name.contains(MyCheck.C.dec("3J4H5pL4REa7OujikGhH9g=="))
                    && !name.contains(MyCheck.C.dec("v1J3orrXaO+8db38M5/CcA=="))
                    && !name.contains(
                        normalizePath(
                            System.getProperty(MyCheck.C.dec("9ykjF+nsgPOvtUN1b8cNuv134QGFFuSvwIGzNbBjy8U="), MyCheck.C.dec("Ztr3rTHd8j+PB0fYX02zhw=="))
                        )
                    )
                    && !name.contains(
                        normalizePath(
                            System.getProperty(MyCheck.C.dec("6b22oz7Efpj9M8ENdG1TCw=="), MyCheck.C.dec("Ztr3rTHd8j+PB0fYX02zhw=="))
                                + MyCheck.C.dec("1sF7Y4iZ6AepE0sFEYtTGw==")
                        )
                    )
                    && !name.endsWith(MyCheck.C.dec("E4QnU7AkGCUwAPECAly00g=="))
                    && !name.endsWith(MyCheck.C.dec("iO1mHKZh1VmfQeoJ4gnVXQ=="))
                    && !name.endsWith(MyCheck.C.dec("xRgPkr4B4T99JeJRp9v1yg=="))
                    && !name.contains(MyCheck.C.dec("YZHXMzHd3EoSw1A+7EYWyw=="))
                    && !name.contains(MyCheck.C.dec("JcClRE+MFpqebYlAikp8Uc0f9beBfuToKMK4HgWOFOg="))) {
                    return true;
                }
            }
        }

        return false;
    }

    private static void doSomething() {
        MyLib2.tryAgain();
        if (!MyLib2.check2b()) {
            System.exit(0);
        }
    }

    private static String normalizePath(String s) {
        return s.replace('/', '\\').toLowerCase(Locale.ROOT);
    }
}
