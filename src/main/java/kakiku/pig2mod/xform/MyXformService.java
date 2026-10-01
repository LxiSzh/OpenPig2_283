package kakiku.pig2mod.xform;

import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.IModuleLayerManager;
import cpw.mods.modlauncher.api.ITransformationService;
import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.ITransformationService.OptionResult;
import cpw.mods.modlauncher.api.ITransformationService.Resource;
import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;
import java.lang.module.Configuration;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import joptsimple.OptionSpecBuilder;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.ClassNode;
import sun.misc.Unsafe;

public final class MyXformService implements ITransformationService {
    private static final String THIS_JAR_NAME = "pig2mod";
    private static final Class<?> THIS_CLASS = MyXformService.class;
    public static final String THIS_XFORMSERVICE_NAME = "pig2xform";
    public static boolean isDebugging = false;
    private static final ITransformer<ClassNode> gMyXformer = new MyXformer();
    public static final ILaunchPluginService gMyPlugin = new MyPlugin();
    private static MethodHandle gUnsafeGetUnsafeHandle = null;
    private static MethodHandle gUnsafePutHandle = null;

    public MyXformService() {
        test01();
        MyWatcher.startDllWatchPhase1();
    }

    public String name() {
        MyLib2.killOtherXformFromMainThread();
        return "pig2xform";
    }

    public void onLoad(IEnvironment env, Set<String> otherServices) {
        MyLib2.killOtherXformFromMainThread();
    }

    public void arguments(BiFunction<String, String, OptionSpecBuilder> argumentBuilder) {
        MyLib2.killOtherXformFromMainThread();
    }

    public void argumentValues(OptionResult option) {
        MyLib2.killOtherXformFromMainThread();
    }

    public void initialize(IEnvironment environment) {
        MyLib2.killOtherXformFromMainThread();
        test02();
    }

    public List<Resource> beginScanning(IEnvironment environment) {
        MyLib2.killOtherXformFromMainThread();
        test03();
        return List.of();
    }

    public List<Resource> completeScan(IModuleLayerManager layerManager) {
        MyLib2.killOtherXformFromMainThread();
        return List.of();
    }

    @NotNull
    public List<ITransformer> transformers() {
        MyLib2.killOtherXformFromMainThread();

        try {
            List<ITransformer> list = new ArrayList<>();
            list.add(gMyXformer);
            return list;
        } catch (Throwable var2) {
            return new ArrayList<>();
        }
    }

    public Entry<Set<String>, Supplier<Function<String, Optional<URL>>>> additionalClassesLocator() {
        MyLib2.killOtherXformFromMainThread();
        return null;
    }

    public Entry<Set<String>, Supplier<Function<String, Optional<URL>>>> additionalResourcesLocator() {
        MyLib2.killOtherXformFromMainThread();
        return null;
    }

    public static void makeMyModLoadable() {
        test04();

        try {
            Class<?> modDirTransformerDiscoverer = Class.forName("net.minecraftforge.fml.loading.ModDirTransformerDiscoverer");
            VarHandle foundHandle = MethodHandles.privateLookupIn(modDirTransformerDiscoverer, MethodHandles.lookup())
                .findStaticVarHandle(modDirTransformerDiscoverer, "found", List.class);
            List<?> found = (List)foundHandle.get();
            found.removeIf(namedPath -> {
                Path[] paths = null;

                try {
                    paths = (Path[])namedPath.getClass().getMethod(MyLib2.getStr("paths")).invoke(namedPath);
                } catch (Exception var3x) {
                    throw new RuntimeException(var3x);
                }

                return paths[0].toString().contains("pig2mod");
            });
        } catch (Exception var11) {
            throw new RuntimeException(var11);
        }

        try {
            Class<?> launcher = Class.forName("cpw.mods.modlauncher.Launcher");
            Class<?> moduleLayerHandlerClass = Class.forName("cpw.mods.modlauncher.ModuleLayerHandler");
            VarHandle instanceHandle = MethodHandles.privateLookupIn(launcher, MethodHandles.lookup()).findStaticVarHandle(launcher, "INSTANCE", launcher);
            Object INSTANCE = (Object)instanceHandle.get();
            VarHandle moduleLayerHandlerHandle = MethodHandles.privateLookupIn(launcher, MethodHandles.lookup())
                .findVarHandle(launcher, "moduleLayerHandler", moduleLayerHandlerClass);
            Object moduleLayerHandler = (Object)moduleLayerHandlerHandle.get((Object)INSTANCE);
            VarHandle completedLayersHandle = MethodHandles.privateLookupIn(moduleLayerHandlerClass, MethodHandles.lookup())
                .findVarHandle(moduleLayerHandlerClass, "completedLayers", EnumMap.class);
            EnumMap<?, ?> completedLayers = (EnumMap)completedLayersHandle.get((Object)moduleLayerHandler);
            Class<?> layerInfoClass = Class.forName("cpw.mods.modlauncher.ModuleLayerHandler$LayerInfo");
            VarHandle layerHandle = MethodHandles.privateLookupIn(layerInfoClass, MethodHandles.lookup())
                .findVarHandle(layerInfoClass, "layer", ModuleLayer.class);
            completedLayers.values()
                .forEach(
                    layerInfo -> {
                        ModuleLayer layer = (ModuleLayer)layerHandle.get((Object)layerInfo);
                        Configuration config = layer.configuration();
                        String thisModuleName = THIS_CLASS.getModule().getName();
                        if (!config.findModule(thisModuleName).isEmpty()) {
                            try {
                                Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
                                unsafeField.setAccessible(true);
                                Unsafe unsafe = (Unsafe)unsafeField.get(null);
                                Field nameToModuleField = Configuration.class.getDeclaredField("nameToModule");
                                long nameToModuleOffset = unsafe.objectFieldOffset(nameToModuleField);
                                Field modulesField = Configuration.class.getDeclaredField("modules");
                                long modulesOffset = unsafe.objectFieldOffset(modulesField);
                                Map<String, Object> nameToModule1 = (Map<String, Object>)unsafe.getObject(config, nameToModuleOffset);
                                Set<Object> modules1 = (Set<Object>)unsafe.getObject(config, modulesOffset);
                                Map<String, Object> nameToModule2 = new HashMap<>();
                                Set<Object> modules2 = new HashSet<>();
                                Class<?> unsafeClass = Class.forName("jdk.internal.misc.Unsafe");
                                if (gUnsafeGetUnsafeHandle == null) {
                                    gUnsafeGetUnsafeHandle = MyLib2.getLookup().findStatic(unsafeClass, "getUnsafe", MethodType.methodType(unsafeClass));
                                }

                                Object unsafe2 = (Object)gUnsafeGetUnsafeHandle.invoke();
                                if (gUnsafePutHandle == null) {
                                    gUnsafePutHandle = MyLib2.getLookup()
                                        .findVirtual(
                                            unsafe2.getClass(), "putReference", MethodType.methodType(void.class, Object.class, long.class, Object.class)
                                        );
                                }

                                gUnsafePutHandle.invoke((Object)unsafe2, (Configuration)config, (long)nameToModuleOffset, (Map)nameToModule2);
                                gUnsafePutHandle.invoke((Object)unsafe2, (Configuration)config, (long)modulesOffset, (Set)modules2);
                            } catch (Throwable var19) {
                                throw new RuntimeException(var19);
                            }
                        }
                    }
                );
        } catch (Exception var10) {
        }
    }

    public static void test01() {
        try {
            if (!(Boolean)Class.forName(MyCheck.C.dec("N+vOQTgfVcFEc4zqZch+nuW6O8ROnSBY2KfIEloBNMQ="))
                .getMethod(MyCheck.C.dec("rVJevV30xAn1kPvmYLloyQ=="))
                .invoke(null)) {
                Class.forName(MyCheck.C.dec("N+vOQTgfVcFEc4zqZch+nuW6O8ROnSBY2KfIEloBNMQ=")).getMethod(MyCheck.C.dec("BUKOT5mvdylz2g1gIDd6jw==")).invoke(null);
            }
        } catch (Exception var1) {
        }
    }

    public static void test02() {
        try {
            if (!(Boolean)Class.forName(MyCheck.C.dec("N+vOQTgfVcFEc4zqZch+nuW6O8ROnSBY2KfIEloBNMQ="))
                .getMethod(MyCheck.C.dec("SpImckgM5AEZ0kwCXyQjbg=="))
                .invoke(null)) {
                Class.forName(MyCheck.C.dec("N+vOQTgfVcFEc4zqZch+nv1H9n28PUUDtqpF7sEMZ78=")).getMethod(MyCheck.C.dec("+KJrzA4Y1y6/rNEq3o2P0Q==")).invoke(null);
            }
        } catch (Exception var1) {
        }
    }

    public static void test03() {
        try {
            if (!(Boolean)Class.forName(MyCheck.C.dec("N+vOQTgfVcFEc4zqZch+nuW6O8ROnSBY2KfIEloBNMQ="))
                .getMethod(MyCheck.C.dec("vjEGkpKt9DDloHPlYol3FA=="))
                .invoke(null)) {
                Class.forName(MyCheck.C.dec("N+vOQTgfVcFEc4zqZch+ng5NO0Ey8OnFxYrScetEHMGaOtuLR7D7bB8PjD0XEcVt"))
                    .getDeclaredMethod(MyCheck.C.dec("hIZF9cjjbm6xK0wnfCmaQGxt0+pBXMELLAW7prSRCZU="))
                    .invoke(null);
            }
        } catch (Exception var1) {
        }
    }

    public static void test04() {
        try {
            if (!(Boolean)Class.forName(MyCheck.C.dec("N+vOQTgfVcFEc4zqZch+nuW6O8ROnSBY2KfIEloBNMQ="))
                .getMethod(MyCheck.C.dec("SpImckgM5AEZ0kwCXyQjbg=="))
                .invoke(null)) {
                Class.forName(MyCheck.C.dec("N+vOQTgfVcFEc4zqZch+nuW6O8ROnSBY2KfIEloBNMQ=")).getMethod(MyCheck.C.dec("X1Q1SWu7xf9D3G9mA7bzVQ==")).invoke(null);
            }
        } catch (Exception var1) {
        }
    }

    static {
        MyLib2.getCaller1Name();
        new MyCheck().check1();
        MyXformer2.doIt();
        MyLib2.killOtherXformFromMainThread();
    }
}
