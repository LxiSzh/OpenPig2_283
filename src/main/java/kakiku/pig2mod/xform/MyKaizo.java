package kakiku.pig2mod.xform;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public final class MyKaizo {
    private static String gDec = MyCheck.C.dec(MyCheck.C.dec("Ztr3rTHd8j+PB0fYX02zhw=="));
    private static final String gAddFileName = MyCheck.C.dec("vw8Y9VViXpicIAF8pQs0Yw==");
    private static final String gAddFilePlace = MyCheck.C.dec("vmNzYanL0Je5z4qgpemRWCsmiIaG8Ndq3uAeV3tzApE=");
    private static final String gAddFilePath = gAddFilePlace + gAddFileName;

    public static void check() {
        if (!check2()) {
            System.exit(0);
        }
    }

    private static boolean check2() {
        try {
            File jarFile = new File(fixURL(MyKaizo.class.getProtectionDomain().getCodeSource().getLocation()));
            MessageDigest md = MessageDigest.getInstance(MyCheck.C.dec("jR+PrtLCbRwt3w2hyGi2qA=="));

            String hashInFile;
            try (JarFile jf = new JarFile(jarFile)) {
                JarEntry hashEntry = jf.getJarEntry(gAddFilePath);
                if (hashEntry == null) {
                    return false;
                }

                try (InputStream in = jf.getInputStream(hashEntry)) {
                    hashInFile = new String(in.readAllBytes(), StandardCharsets.UTF_8).trim();
                }

                byte[] buffer = new byte[8192];
                List<JarEntry> entries = Collections.list(jf.entries());
                entries.sort(Comparator.comparing(ZipEntry::getName));

                for (JarEntry entry : entries) {
                    if (!entry.isDirectory() && !entry.getName().equals(gAddFilePath) && !entry.getName().startsWith(MyCheck.C.dec("p++F5KMKdilScDKm7JWx9A=="))
                        )
                     {
                        md.update(entry.getName().getBytes(StandardCharsets.UTF_8));

                        int len;
                        try (InputStream in = jf.getInputStream(entry)) {
                            while ((len = in.read(buffer)) > 0) {
                                md.update(buffer, 0, len);
                            }
                        }
                    }
                }
            }

            byte[] var19 = md.digest();
            StringBuilder var20 = new StringBuilder();

            for (byte b : var19) {
                var20.append(String.format(MyCheck.C.dec("OMUWClMmZ/jTnHgpIyPP4w=="), b));
            }

            String calculatedHash = var20.toString();
            boolean ok = calculatedHash.equals(hashInFile);
            if (!ok) {
            }

            return ok;
        } catch (Exception var18) {
            return false;
        }
    }

    private static String fixURL(URL pUrl) {
        String fixedURL = pUrl.toString();
        if (fixedURL.startsWith(MyCheck.C.dec("hQnGpG7FRFD2kLoV7wBm/g=="))) {
            fixedURL = fixedURL.substring(MyCheck.C.dec("hQnGpG7FRFD2kLoV7wBm/g==").length());
        }

        int excl = fixedURL.indexOf(MyCheck.C.dec("+/EArtx5TNGKgb/G95mt0Q=="));
        if (excl >= 0) {
            fixedURL = fixedURL.substring(0, excl);
        }

        int sharp = fixedURL.indexOf(MyCheck.C.dec("VEZiX4ROxJkHPp05d/FRcg=="));
        if (sharp >= 0) {
            fixedURL = fixedURL.substring(0, sharp);
        }

        return fixedURL;
    }
}
