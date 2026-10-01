package kakiku.pig2mod.xform;

import com.sun.tools.attach.VirtualMachine;
import cpw.mods.modlauncher.LaunchPluginHandler;
import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.TransformStore;
import cpw.mods.modlauncher.TransformationServiceDecorator;
import cpw.mods.modlauncher.TransformingClassLoader;
import cpw.mods.modlauncher.api.ITransformationService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.StackWalker.Option;
import java.lang.StackWalker.StackFrame;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import sun.misc.Unsafe;

public final class MyLib2 {
    public static final boolean MYLIB2_DEBUG_MODE = false;
    public static final String PIG2_CUSTOMNAME = " Pig2_2.8.3 ";
    private static ScheduledExecutorService gThreadScheduler = Executors.newScheduledThreadPool(1);
    private static final Set<Long> gStoppedThreadIDs = ConcurrentHashMap.newKeySet();
    public static final String SYSTEM_PROPERTY_KEY_SERVER_LEVEL_MAP = "Pig2.ServerLevelsMap";
    private static final Logger gLogger = LogManager.getLogger("MyLib2");
    private static final ConcurrentHashMap<Class<?>, Boolean> gIsThisOtherModCache = new ConcurrentHashMap<>();
    public static final String REIGAI_THREAD_CLASSNAME1 = "com.mega.uom.";
    static final StackWalker gStackWalker = StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE);
    private static TransformingClassLoader gClassLoaderBackup = null;
    private static Set<String> gClassNeedsTransformingBackup = null;
    private static Object gUnsafe = null;
    private static Object gLookup = null;
    private static Set<String> gModClassNamesFromJar = null;
    private static final Map<String, byte[]> gModClassDataFromJar = new HashMap<>();
    protected static final Set<String> gModPackageNamesFromJar = new HashSet<>();
    private static final Set<String> gJarNames = new HashSet<>();
    public static final Set<String> gBlackListNames = ConcurrentHashMap.newKeySet();
    private static Instrumentation gKeisou = null;
    private static Instrumentation gKeisouAgentMain = null;

    public static void MyLog2(String string) {
    }

    public static void showStackTrace() {
        showStackTrace(Thread.currentThread());
    }

    public static void showStackTrace(Thread thread) {
    }

    public static boolean isThisMinecraftVanilla(Object obj) {
        assert obj != null;

        String name = obj instanceof Class ? ((Class)obj).getName() : obj.getClass().getName();
        return isThisNameMinecraftVanilla(name);
    }

    public static boolean isThisNameMinecraftVanilla(String name) {
        for (String blackName : gBlackListNames) {
            if (name.startsWith(blackName)) {
                return false;
            }
        }

        if (name.startsWith("net.minecraft.")) {
            return true;
        } else if (name.startsWith("net.minecraftforge.")) {
            return true;
        } else if (name.startsWith("java.")) {
            return true;
        } else if (name.startsWith("cpw.mods.")) {
            return true;
        } else if (name.startsWith("jdk.")) {
            return true;
        } else if (name.startsWith("com.electronwill.")) {
            return true;
        } else if (name.startsWith("com.google.")) {
            return true;
        } else if (name.startsWith("com.mojang.")) {
            return true;
        } else if (name.startsWith("com.sun.")) {
            return true;
        } else if (name.startsWith("io.netty.")) {
            return true;
        } else if (name.startsWith("it.unimi.")) {
            return true;
        } else if (name.startsWith("javax.")) {
            return true;
        } else if (name.startsWith("joptsimple.")) {
            return true;
        } else if (name.startsWith("net.minecrell.")) {
            return true;
        } else if (name.startsWith("netscape.javascript.")) {
            return true;
        } else if (name.startsWith("org.antlr.")) {
            return true;
        } else if (name.startsWith("org.apache.logging.")) {
            return true;
        } else if (name.startsWith("org.apache.maven.")) {
            return true;
        } else if (name.startsWith("org.apache.commons.lang3.")) {
            return true;
        } else if (name.startsWith("org.apache.commons.io.")) {
            return true;
        } else if (name.startsWith("org.apache.commons.compress.")) {
            return true;
        } else if (name.startsWith("org.apache.commons.http.")) {
            return true;
        } else if (name.startsWith("org.jline.")) {
            return true;
        } else if (name.startsWith("org.joml.")) {
            return true;
        } else if (name.startsWith("org.lwjgl.")) {
            return true;
        } else if (name.startsWith("org.objectweb.")) {
            return true;
        } else if (name.startsWith("org.openjdk.")) {
            return true;
        } else if (name.startsWith("org.slf4j.")) {
            return true;
        } else if (name.startsWith("org.spongepowered.")) {
            return true;
        } else if (name.startsWith("org.w3c.")) {
            return true;
        } else if (name.startsWith("org.xml.")) {
            return true;
        } else if (name.startsWith("oshi.")) {
            return true;
        } else {
            return name.startsWith("sun.") ? true : name.startsWith("net.jodah.typetools.");
        }
    }

    public static boolean isThisNameWhiteListedMOD(String name) {
        for (String blackName : gBlackListNames) {
            if (name.startsWith(blackName)) {
                return false;
            }
        }

        for (Entry<String, String> entry : getWhiteListNameAndJarEntrySet()) {
            if (name.startsWith(entry.getKey())) {
                return true;
            }
        }

        return !isThisNameMyMOD(name) && gModPackageNamesFromJar.stream().noneMatch(name::startsWith);
    }

    private static Set<Entry<String, String>> getWhiteListNameAndJarEntrySet() {
        return Map.<String, String>ofEntries(
                Map.entry("com.github.alexthe666.citadel.", "citadel"),
                Map.entry("me.jellysquid.mods.sodium.", "embeddium"),
                Map.entry("org.embeddedt.modernfix", "modernfix"),
                Map.entry("org.embeddedt.embeddium", "embeddium"),
                Map.entry("com.supermartijn642.fusion.", "fusion"),
                Map.entry("com.supermartijn642.core.", "supermartijn642corelib"),
                Map.entry("committee.nova.mods.avaritia.", "Re-Avaritia"),
                Map.entry("dev.kosmx.playerAnim.", "player-animation-lib"),
                Map.entry("dev.tr7zw.entityculling.", "entityculling"),
                Map.entry("io.redspace.ironsspellbooks.", "irons_spellbooks"),
                Map.entry("malte0811.ferritecore.", "ferritecore"),
                Map.entry("mezz.jei.", "jei"),
                Map.entry("net.irisshaders.iris.", "oculus"),
                Map.entry("net.irisshaders.batchedentityrendering.", "oculus"),
                Map.entry("net.raphimc.immediatelyfast.", "ImmediatelyFast"),
                Map.entry("net.tslat.smartbrainlib", "SmartBrainLib"),
                Map.entry("software.bernie.geckolib.", "geckolib"),
                Map.entry("top.theillusivec4.caelus.", "caelus"),
                Map.entry("top.theillusivec4.curios.", "curios"),
                Map.entry("virtuoel.pehkui.", "Pehkui"),
                Map.entry("com.llamalad7.mixinextras.", "mixinextras"),
                Map.entry("net.fabricmc.", "fabric-"),
                Map.entry("org.sinytra.connector.", "Connector-"),
                Map.entry("kotlin", "kotlinforforge")
            )
            .entrySet();
    }

    public static boolean isThisMyMOD(Object obj) {
        if (obj == null) {
            return false;
        } else {
            String name = obj instanceof Class ? ((Class)obj).getName() : obj.getClass().getName();
            return isThisNameMyMOD(name);
        }
    }

    public static boolean isThisNameMyMOD(String name) {
        return name.startsWith("kakiku.pig2mod.MyAttacker") ? false : name.startsWith("kakiku.");
    }

    public static boolean isThisOtherMOD(Object obj) {
        if (obj == null) {
            return false;
        } else {
            Class<?> clazz = obj instanceof Class<?> c ? c : obj.getClass();
            return gIsThisOtherModCache.computeIfAbsent(clazz, MyLib2::isThisOtherMODSub);
        }
    }

    private static boolean isThisOtherMODSub(Class<?> clazz) {
        if (clazz.getClassLoader() == null) {
            return false;
        } else {
            CodeSource codeSource = clazz.getProtectionDomain().getCodeSource();
            if (codeSource != null) {
                String fileName = codeSource.getLocation().getFile();
                if (gJarNames.stream().anyMatch(fileName::contains)) {
                    if (clazz.getName().startsWith("kakiku.") && !clazz.getName().equals("kakiku.pig2mod.MyAttacker")) {
                        return false;
                    }

                    return true;
                }
            }

            return isThisNameOtherMOD(clazz.getName());
        }
    }

    public static boolean isThisNameOtherMOD(String name) {
        return name.startsWith("kakiku.pig2mod.MyAttacker") ? true : !isThisNameMinecraftVanilla(name) && !isThisNameMyMOD(name);
    }

    public static boolean isThisOtherBadMOD(Object obj) {
        if (obj == null) {
            return false;
        } else {
            String name = obj instanceof Class ? ((Class)obj).getName() : obj.getClass().getName();
            return isThisNameOtherBadMOD(name);
        }
    }

    public static boolean isThisNameOtherBadMOD(String name) {
        return isThisNameOtherMOD(name) && !isThisNameWhiteListedMOD(name);
    }

    public static boolean isClientThread() {
        return Thread.currentThread().getName().contains("Render");
    }

    public static boolean isServerThread() {
        return Thread.currentThread().getName().contains("Server");
    }

    public static boolean isThisBadThread() {
        if (isClientThread()) {
            return false;
        } else {
            return isServerThread() ? false : isThisBadThread(Thread.currentThread());
        }
    }

    public static boolean isThisBadThread(Thread thread) {
        if (isThisNameOtherBadMOD(thread.getClass().getName())) {
            return true;
        } else {
            StackTraceElement[] callers = thread.getStackTrace();
            if (callers.length == 0) {
                return false;
            } else {
                String pattern = "";

                for (StackTraceElement caller : callers) {
                    String className = caller.getClassName();
                    if (isThisNameOtherBadMOD(className)) {
                        if (className.startsWith("com.mega.uom.")) {
                            return false;
                        }

                        if (!pattern.endsWith("他")) {
                            pattern = pattern + "他";
                        }
                    } else if (className.startsWith("java.")) {
                        if (!pattern.endsWith("J")) {
                            pattern = pattern + "J";
                        }
                    } else if (className.startsWith("kakiku.")) {
                        if (!pattern.endsWith("自")) {
                            pattern = pattern + "自";
                        }

                        if (className.startsWith("kakiku.pig2mod.xform.") && caller.getMethodName().equals("transform")) {
                            return false;
                        }
                    } else if (!pattern.endsWith("*")) {
                        pattern = pattern + "*";
                    }
                }

                pattern = pattern + "終";
                return !pattern.equals("他J終") && !pattern.equals("J他J終") && (!pattern.contains("自") || !pattern.endsWith("他J終")) && !pattern.endsWith("他終")
                    ? false
                    : !callers[callers.length - 1].getClassName().startsWith("java.util.concurrent.ForkJoinWorkerThread");
            }
        }
    }

    public static void stopBadThreads() {
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            long tid = t.getId();
            if (isThisBadThread(t) && gStoppedThreadIDs.add(tid)) {
                try {
                    t.suspend();
                    if (!isThisNameOtherBadMOD(t.getClass().getName())) {
                        StackTraceElement[] stack = t.getStackTrace();
                        if (stack.length > 0 && stack[0].getClassName().equals("jdk.internal.misc.Unsafe") && stack[0].getMethodName().contains("park")
                            || stack[0].getClassName().startsWith("java.util.Collections$Synchronized")) {
                            t.resume();
                            gStoppedThreadIDs.remove(tid);
                            return;
                        }
                    }

                    if (!isThisNameOtherBadMOD(t.getClass().getName())) {
                        showStackTrace(t);
                    }
                } catch (Throwable var5) {
                }
            }
        }
    }

    public static void stopMyselfIfImBadThread(String debugMessage) {
        Thread t = Thread.currentThread();
        long tid = t.getId();
        if (isThisBadThread(t)) {
            if (gStoppedThreadIDs.add(tid)) {
                try {
                    Thread.sleep(6000000L);
                    t.suspend();
                } catch (Throwable var5) {
                }
            }
        }
    }

    public static boolean isCalledFromTheClass(String pClassName) {
        return gStackWalker.walk(s -> s.skip(2L).anyMatch(frame -> frame.getClassName().contains(pClassName)));
    }

    public static boolean isCalledFromTheMethod(String pMethodName) {
        return gStackWalker.walk(s -> s.skip(2L).anyMatch(frame -> frame.getMethodName().equals(pMethodName)));
    }

    public static boolean isCalledFromTheClassAndMethod(String pClassAndMethodName) {
        return gStackWalker.walk(
            s -> s.skip(2L).anyMatch(frame -> (frame.getDeclaringClass().getName() + "." + frame.getMethodName()).equals(pClassAndMethodName))
        );
    }

    public static boolean isCalledFromOtherModWithin1() {
        StackFrame frame = gStackWalker.walk(s -> s.skip(2L).findFirst()).orElse(null);
        return frame == null ? false : isThisOtherMOD(frame.getDeclaringClass());
    }

    public static boolean isCalledFromOtherModWithin5() {
        return gStackWalker.walk(s -> s.skip(2L).limit(5L).anyMatch(frame -> isThisOtherMOD(frame.getDeclaringClass())));
    }

    public static boolean isCalledFromOtherModWithin10() {
        return gStackWalker.walk(s -> s.skip(2L).limit(10L).anyMatch(frame -> isThisOtherMOD(frame.getDeclaringClass())));
    }

    public static boolean isCalledFromOtherModWithin1ExceptMixin() {
        StackFrame caller0 = gStackWalker.walk(s -> s.skip(1L).findFirst()).orElse(null);
        StackFrame caller1 = gStackWalker.walk(s -> s.skip(2L).findFirst()).orElse(null);
        StackFrame caller2 = gStackWalker.walk(s -> s.skip(3L).findFirst()).orElse(null);
        if (caller0 != null && caller1 != null && caller2 != null) {
            if (caller0.getMethodName().startsWith("handler$")) {
                caller1 = caller2;
            }

            return isThisOtherMOD(caller1.getDeclaringClass());
        } else {
            return false;
        }
    }

    public static StackFrame getCaller1() {
        return gStackWalker.walk(s -> s.skip(2L).findFirst()).orElse(null);
    }

    public static String getCaller1Name() {
        StackFrame caller1 = gStackWalker.walk(s -> s.skip(2L).findFirst()).orElse(null);
        return caller1 == null ? "" : caller1.getClassName() + "." + caller1.getMethodName() + "()";
    }

    public static StackFrame getCaller2() {
        return gStackWalker.walk(s -> s.skip(3L).findFirst()).orElse(null);
    }

    public static StackFrame getCallerN(int n) {
        try {
            return gStackWalker.walk(s -> s.skip((long)(1 + n)).findFirst()).orElseThrow();
        } catch (Throwable var3) {
            return gStackWalker.walk(s -> s.reduce((a, b) -> b)).orElse(null);
        }
    }

    public static String getCallerName_withoutVanilla(int n) {
        try {
            StackFrame frameFound = gStackWalker.walk(
                s -> s.skip((long)(1 + n)).filter(frame -> !isThisMinecraftVanilla(frame.getDeclaringClass())).findFirst().orElse(null)
            );
            return frameFound.getDeclaringClass().getName() + "." + frameFound.getMethodName() + "()";
        } catch (Throwable var2) {
            return "";
        }
    }

    public static Class<?> getCallerClass1() {
        StackFrame frame = gStackWalker.walk(s -> s.skip(2L).findFirst()).orElse(null);
        return frame != null ? frame.getDeclaringClass() : Object.class;
    }

    public static String getStr(String key) {
        return System.getProperty("Pig2." + key);
    }

    public static void setStr(String key, String value) {
        System.setProperty("Pig2." + key, value);
    }

    public static void keepKillingBeforeMod() {
        gThreadScheduler.scheduleWithFixedDelay(() -> {
            if ("true".equals(getStr("MyLib2.keepKillingAfterMod"))) {
                gThreadScheduler.shutdown();
            } else {
                Thread thread = new Thread(() -> {
                    stopBadThreads();
                    killOtherXformFromMyThread();
                });
                thread.setPriority(10);
                thread.start();
            }
        }, 0L, 100L, TimeUnit.MILLISECONDS);
    }

    public static void keepKillingAfterMod() {
        setStr("MyLib2.keepKillingAfterMod", "true");
        gThreadScheduler.scheduleWithFixedDelay(() -> {
            Thread thread = new Thread(() -> stopBadThreads());
            thread.setPriority(10);
            thread.start();
        }, 0L, 100L, TimeUnit.MILLISECONDS);
    }

    public static void killOtherXformFromMainThread() {
        killOtherXform();
        invalidOtherXformService();
    }

    public static void killOtherXformFromMyThread() {
        killOtherXform();
    }

    private static void killOtherXform() {
        try {
            Instrumentation keisou = getKeisou();
            if (keisou != null) {
                new ArrayList();
                Unsafe unsafe = getUnsafe();
                Class<?> instImplClass = Class.forName("sun.instrument.InstrumentationImpl");
                Field transformerField1 = instImplClass.getDeclaredField("mRetransfomableTransformerManager");
                long offset1 = unsafe.objectFieldOffset(transformerField1);
                Object manager = unsafe.getObject(keisou, offset1);
                if (manager != null) {
                    long listOffset = unsafe.objectFieldOffset(manager.getClass().getDeclaredField("mTransformerList"));
                    Object[] transformerList = (Object[])unsafe.getObject(manager, listOffset);
                    Class<?> transformerInfoClass = Class.forName("sun.instrument.TransformerManager$TransformerInfo");
                    Field tfField = transformerInfoClass.getDeclaredField("mTransformer");
                    long tfOffset = unsafe.objectFieldOffset(tfField);
                    if (transformerList.length > 0) {
                        for (Object info : transformerList) {
                            Object tfObj = unsafe.getObject(info, tfOffset);
                            if (tfObj instanceof ClassFileTransformer) {
                                ClassFileTransformer tf = (ClassFileTransformer)tfObj;
                                String XformerName = new String(tf.getClass().getName());
                                if (isThisNameOtherBadMOD(XformerName)) {
                                    keisou.removeTransformer(tf);
                                } else if (!XformerName.startsWith("kakiku.")) {
                                }
                            }
                        }
                    }
                }
            }
        } catch (Throwable var25) {
        }

        try {
            Field launchPluginsField = Launcher.class.getDeclaredField("launchPlugins");
            launchPluginsField.setAccessible(true);
            LaunchPluginHandler launchPlugins = (LaunchPluginHandler)launchPluginsField.get(Launcher.INSTANCE);
            if (launchPlugins.getClass() != MyLaunchPluginHandler.class) {
                if (launchPlugins.getClass() == LaunchPluginHandler.class) {
                    launchPluginsField.set(Launcher.INSTANCE, MyLaunchPluginHandler.myNew());
                } else {
                    launchPluginsField.set(Launcher.INSTANCE, MyLaunchPluginHandler.myNew());
                }
            } else {
                MyLaunchPluginsMap.setMyLaunchPluginsMap();
            }
        } catch (Throwable var24) {
        }

        try {
            Field classLoaderField = Launcher.class.getDeclaredField("classLoader");
            classLoaderField.setAccessible(true);
            TransformingClassLoader classLoader = (TransformingClassLoader)classLoaderField.get(Launcher.INSTANCE);
            if (isThisOtherMOD(classLoader)) {
                classLoaderField.set(Launcher.INSTANCE, gClassLoaderBackup);
            } else {
                gClassLoaderBackup = classLoader;
            }
        } catch (Throwable var23) {
        }

        try {
            Field transformationServicesHandlerField = Launcher.class.getDeclaredField("transformationServicesHandler");
            transformationServicesHandlerField.setAccessible(true);
            Object transformationServicesHandler = transformationServicesHandlerField.get(Launcher.INSTANCE);
            Field transformStoreField = transformationServicesHandler.getClass().getDeclaredField("transformStore");
            transformStoreField.setAccessible(true);
            TransformStore transformStore = (TransformStore)transformStoreField.get(transformationServicesHandler);
            Field classNeedsTransformingField = TransformStore.class.getDeclaredField("classNeedsTransforming");
            classNeedsTransformingField.setAccessible(true);
            Set<String> classNeedsTransforming = (Set<String>)classNeedsTransformingField.get(transformStore);
            if (isThisOtherMOD(classNeedsTransforming)) {
                if (gClassNeedsTransformingBackup == null) {
                    gClassNeedsTransformingBackup = new HashSet<>();
                }

                classNeedsTransformingField.set(transformStore, gClassNeedsTransformingBackup);
            } else {
                gClassNeedsTransformingBackup = classNeedsTransforming;
            }
        } catch (Throwable var22) {
        }
    }

    private static void invalidOtherXformService() {
        try {
            Field transformationServicesHandlerField = Launcher.class.getDeclaredField("transformationServicesHandler");
            transformationServicesHandlerField.setAccessible(true);
            Object transformationServicesHandler = transformationServicesHandlerField.get(Launcher.INSTANCE);
            Field serviceLookupField = transformationServicesHandler.getClass().getDeclaredField("serviceLookup");
            serviceLookupField.setAccessible(true);
            Map<String, TransformationServiceDecorator> serviceLookup = (Map<String, TransformationServiceDecorator>)serviceLookupField.get(
                transformationServicesHandler
            );
            if (serviceLookup != null && !serviceLookup.isEmpty()) {
                boolean bChanged = false;
                Map<String, TransformationServiceDecorator> serviceLookupNew = new HashMap<>();

                for (Entry<String, TransformationServiceDecorator> entryService : serviceLookup.entrySet()) {
                    TransformationServiceDecorator decorator = entryService.getValue();
                    Field serviceField = TransformationServiceDecorator.class.getDeclaredField("service");
                    serviceField.setAccessible(true);
                    ITransformationService service = (ITransformationService)serviceField.get(decorator);
                    if (isThisNameOtherBadMOD(service.getClass().getName())) {
                        bChanged = true;
                    } else {
                        serviceLookupNew.put(entryService.getKey(), entryService.getValue());
                    }
                }

                if (bChanged) {
                    serviceLookupField.set(transformationServicesHandler, serviceLookupNew);
                }
            }
        } catch (Throwable var11) {
        }
    }

    public static Unsafe getUnsafe() {
        if (gUnsafe != null) {
            return (Unsafe)gUnsafe;
        } else {
            try {
                Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
                unsafeField.setAccessible(true);
                gUnsafe = unsafeField.get(null);
            } catch (Throwable var1) {
                SystemExitForDebug();
            }

            return (Unsafe)gUnsafe;
        }
    }

    public static Lookup getLookup() {
        if (gLookup != null) {
            return (Lookup)gLookup;
        } else {
            try {
                Unsafe unsafe = getUnsafe();
                Field field = Lookup.class.getDeclaredField("IMPL_LOOKUP");
                Object base = unsafe.staticFieldBase(field);
                long offset = unsafe.staticFieldOffset(field);
                gLookup = unsafe.getObjectVolatile(base, offset);
            } catch (Throwable var5) {
                SystemExitForDebug();
            }

            return (Lookup)gLookup;
        }
    }

    private static Set<String> findModClassFromJar() {
        if (gModClassNamesFromJar != null) {
            return gModClassNamesFromJar;
        } else {
            gModClassNamesFromJar = new HashSet<>();
            Set<String> classNames = new HashSet<>();
            File modsFolder = new File("mods");
            if (modsFolder.exists() && modsFolder.isDirectory()) {
                File[] jarFiles = modsFolder.listFiles((dir, name) -> name.endsWith(".jar"));
                if (jarFiles == null) {
                    return gModClassNamesFromJar;
                } else {
                    for (File jarFile : jarFiles) {
                        try (JarFile jar = new JarFile(jarFile)) {
                            gJarNames.add(jarFile.getName());

                            try (InputStream is = new FileInputStream(jarFile)) {
                                processJarStream(is, jarFile.getName(), classNames);
                            }
                        } catch (IOException var15) {
                        }
                    }

                    gModClassNamesFromJar = classNames;
                    return gModClassNamesFromJar;
                }
            } else {
                return gModClassNamesFromJar;
            }
        }
    }

    private static void processJarStream(InputStream jarStream, String parentJarName, Set<String> classNames) {
        JarEntry entry;
        try (JarInputStream jis = new JarInputStream(jarStream)) {
            while ((entry = jis.getNextJarEntry()) != null) {
                String name = entry.getName();
                if (name.endsWith(".class")) {
                    String className = name.replace('/', '.').substring(0, name.length() - 6);
                    classNames.add(className);
                    byte[] classFile = jis.readAllBytes();
                    gModClassDataFromJar.put(className, classFile);
                    String packageName = name.substring(0, name.lastIndexOf(47) + 1).replace('/', '.');
                    gModPackageNamesFromJar.add(packageName);
                    boolean isFakeName = false;
                    if (className.startsWith("kakiku.") && !parentJarName.startsWith("pig2")) {
                        isFakeName = true;
                    } else if (isThisNameWhiteListedMOD(className)) {
                        isFakeName = getWhiteListNameAndJarEntrySet()
                            .stream()
                            .anyMatch(e -> className.startsWith(e.getKey()) && !parentJarName.startsWith(e.getValue()));
                    } else if (isThisNameMinecraftVanilla(className)) {
                        isFakeName = true;
                    }

                    if (isFakeName) {
                        gBlackListNames.add(className);
                    }
                } else if (name.endsWith(".jar")) {
                    int idx = name.lastIndexOf(47);
                    String jarName = idx >= 0 ? name.substring(idx + 1) : name;
                    if (!jarName.toLowerCase().startsWith("mixinextras")
                        && !jarName.startsWith("fabric-")
                        && !jarName.startsWith("Connector-")
                        && !jarName.startsWith("lazyyyyy")
                        && !jarName.startsWith("luaj-")) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        jis.transferTo(baos);
                        byte[] nestedJarBytes = baos.toByteArray();
                        processJarStream(new ByteArrayInputStream(nestedJarBytes), jarName, classNames);
                    }
                }
            }
        } catch (IOException var12) {
        }
    }

    public static Set<String> getModClassNamesFromJar() {
        return findModClassFromJar();
    }

    public static Map<String, byte[]> getModClassDataFromJar() {
        return gModClassDataFromJar;
    }

    public static boolean hasJar(String pJarName) {
        return gJarNames.stream().anyMatch(jarName -> jarName.contains(pJarName));
    }

    @Nullable
    public static Instrumentation getKeisou() {
        if (gKeisou != null) {
            return gKeisou;
        } else {
            gKeisou = getKeisouFromAgentPremain();
            if (gKeisou != null) {
                return gKeisou;
            } else {
                gKeisou = getKeisouFromAgentMain();
                if (gKeisou != null) {
                    return gKeisou;
                } else {
                    tryAgain();
                    return null;
                }
            }
        }
    }

    @Nullable
    private static Instrumentation getKeisouFromAgentPremain() {
        try {
            Class<?> clazz = Class.forName("kakiku.MyAgent", false, ClassLoader.getSystemClassLoader());
            Field keisouField = clazz.getField("gKeisou");
            Object var3 = keisouField.get(null);
            if (var3 instanceof Instrumentation) {
                return (Instrumentation)var3;
            }
        } catch (Throwable var4) {
            if (var4 instanceof ClassNotFoundException) {
            }
        }

        return null;
    }

    public static void tryAgain() {
        new MyCheck().check1b();
    }

    public static boolean check2b() {
        return new MyCheck().check2b();
    }

    @Nullable
    private static Instrumentation getKeisouFromAgentMain() {
        VirtualMachine vm = null;

        try {
            String pid = Long.toString(ProcessHandle.current().pid());
            Field field = Class.forName("sun.tools.attach.HotSpotVirtualMachine").getDeclaredField("ALLOW_ATTACH_SELF");
            Unsafe unsafe = getUnsafe();
            unsafe.putBoolean(unsafe.staticFieldBase(field), unsafe.staticFieldOffset(field), true);
            vm = VirtualMachine.attach(pid);
            String myAgentPath = extractAgentJar().toString();
            vm.loadAgent(myAgentPath, "");
        } catch (Throwable var13) {
        } finally {
            if (vm != null) {
                try {
                    vm.detach();
                } catch (Throwable var12) {
                }
            }
        }

        return gKeisouAgentMain;
    }

    public static void setKeisou(String args, Instrumentation inst) {
        gKeisouAgentMain = inst;
    }

    public static Path extractAgentJar() throws IOException {
        String agentFileName = "pig2_agent.jar";
        String resourcePath = "/assets/pig2mod/libs/" + agentFileName;
        Path outputPath = Paths.get(System.getProperty("java.io.tmpdir")).resolve(agentFileName);

        try (InputStream in = MyLib2.class.getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new FileNotFoundException("pig2_agent.jar が pig2mod 内に見つからない。");
            }

            try {
                Files.copy(in, outputPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (FileSystemException var7) {
            }
        }

        return outputPath.toAbsolutePath();
    }

    public static Path extractMyLib0Jar() throws IOException {
        String agentFileName = "pig2_lib0.jar";
        String resourcePath = "/assets/pig2mod/libs/" + agentFileName;
        Path outputPath = Paths.get(System.getProperty("java.io.tmpdir")).resolve(agentFileName);

        try (InputStream in = MyLib2.class.getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new FileNotFoundException(agentFileName + " が pig2mod 内に見つからない。");
            }

            try {
                Files.copy(in, outputPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (FileSystemException var7) {
            }
        }

        return outputPath.toAbsolutePath();
    }

    public static String shortName(String className, String methodName) {
        return shortClassName(className) + "." + shortMethodName(methodName) + "()";
    }

    public static String shortClassName(String className) {
        int idx = className.lastIndexOf(47);
        if (idx < 0) {
            idx = className.lastIndexOf(46);
        }

        return idx >= 0 ? className.substring(idx + 1) : className;
    }

    public static String shortMethodName(String methodName) {
        int idx = methodName.indexOf(40);
        return idx >= 0 ? methodName.substring(0, idx) : methodName;
    }

    public static void SystemExitForDebug() {
    }

    private static void checkForgeVersion() {
        String classPath = System.getProperty("java.class.path", "");
        String modulePath = System.getProperty("jdk.module.path", "");
        String allPath = classPath + ";" + modulePath;
        if (!allPath.contains("1.20.1-47.") || allPath.contains("neoforge")) {
            throw new RuntimeException("This Pig2 mod is only for Forge-1.20.1.");
        }
    }

    private static void addSystemOutIntoDebugLog() {
    }

    private static PrintStream createLoggerPrintStream(final String loggerName, final boolean isError) {
        OutputStream outputStream = new OutputStream() {
            private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

            @Override
            public synchronized void write(int b) throws IOException {
                this.baos.write(b);
                if (b == 10) {
                    this.flush();
                }
            }

            @Override
            public synchronized void flush() throws IOException {
                byte[] bytes = this.baos.toByteArray();
                if (bytes.length != 0) {
                    this.baos.reset();
                    String s = new String(bytes, Charset.defaultCharset());
                    s = s.replace("\r", "").replace("\n", "");
                    if (!s.isEmpty()) {
                        if (isError) {
                            LogManager.getLogger(loggerName).error(s);
                        } else {
                            LogManager.getLogger(loggerName).info(s);
                        }
                    }
                }
            }
        };
        return new PrintStream(outputStream, true, Charset.defaultCharset());
    }

    public static boolean isThisMethodOverride(String superClassName, String methodName, Class<?> callerClass) {
        boolean isForMixin = callerClass.getName().contains("MyMixin");
        if (isForMixin && methodName.startsWith("m_") && methodName.endsWith("_")) {
            return true;
        } else {
            ClassNode cn = null;

            try {
                label102: {
                    boolean var14;
                    try (InputStream is = getClassBytes(superClassName + ".class", callerClass)) {
                        if (is != null) {
                            ClassReader cr = new ClassReader(is);
                            cn = new ClassNode();
                            cr.accept(cn, 7);
                            break label102;
                        }

                        var14 = false;
                    }

                    return var14;
                }
            } catch (IOException var10) {
                cn = null;
            }

            if (cn != null && cn.methods != null) {
                for (MethodNode mn : cn.methods) {
                    if (mn.name.equals(methodName)) {
                        if (isForMixin) {
                            return true;
                        }

                        if ((mn.access & 8) == 0 && (mn.access & 1024) == 0) {
                            return true;
                        }
                    }
                }

                return cn.superName != null && !cn.superName.equals("java/lang/Object") ? isThisMethodOverride(cn.superName, methodName, callerClass) : false;
            } else {
                return false;
            }
        }
    }

    public static InputStream getClassBytes(String path, Class<?> callerClass) throws IOException {
        InputStream is = callerClass.getClassLoader().getResourceAsStream(path);
        if (is != null) {
            return is;
        } else {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            if (cl != null) {
                is = cl.getResourceAsStream(path);
                if (is != null) {
                    return is;
                }
            }

            return null;
        }
    }

    static {
        checkForgeVersion();
        MyKaizo.check();
        addSystemOutIntoDebugLog();
        findModClassFromJar();
    }
}
