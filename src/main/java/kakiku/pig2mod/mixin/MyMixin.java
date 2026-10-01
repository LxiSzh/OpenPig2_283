package kakiku.pig2mod.mixin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kakiku.pig2mod.xform.MyLib2;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public final class MyMixin implements IMixinConfigPlugin {
    public void onLoad(String mixinPackage) {
    }

    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
        this.myMain("acceptTargets()");
    }

    public List<String> getMixins() {
        this.myMain("getMixins()");
        return null;
    }

    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    public String getRefMapperConfig() {
        return null;
    }

    private void MyLogMixin(String string) {
    }

    private boolean isThisNameMinecraftVanillaMixin(String name) {
        for (String blackName : MyLib2.gBlackListNames) {
            if (name.replace('/', '.').startsWith(blackName)) {
                return false;
            }
        }

        if (name.startsWith("net/minecraft")) {
            return true;
        } else if (name.startsWith("java/")) {
            return true;
        } else if (name.startsWith("com/mojang/")) {
            return true;
        } else if (name.startsWith("org/lwjgl/")) {
            return true;
        } else if (name.startsWith("jdk/")) {
            return true;
        } else {
            return name.startsWith("cpw/mods/") ? true : name.startsWith("com/google/");
        }
    }

    private void myMain(String phase) {
        this.editClassNodeMethods(phase);
    }

    private void editClassNodeMethods(String phase) {
        List<MethodNode> returnMethods = null;
        ArrayList<Object> configs = this.getPendingConfigs();
        if (configs != null) {
            for (Object config : configs) {
                String packageName = this.getPackageNameFromMixinConfig(config);
                if (!packageName.startsWith(MyMixin.class.getPackageName()) && !MyLib2.isThisNameWhiteListedMOD(packageName)) {
                    ArrayList<Object> mixins = null;

                    try {
                        Field mixinsField = config.getClass().getDeclaredField("mixins");
                        mixinsField.setAccessible(true);

                        for (Object mixinInfo : (ArrayList)mixinsField.get(config)) {
                            String classNameVanilla = "";

                            try {
                                Field targetClassNamesField = mixinInfo.getClass().getDeclaredField("targetClassNames");
                                targetClassNamesField.setAccessible(true);
                                ArrayList<String> targetClassNames = (ArrayList<String>)targetClassNamesField.get(mixinInfo);
                                classNameVanilla = targetClassNames.get(0);
                            } catch (Throwable var18) {
                                classNameVanilla = "";
                            }

                            if (this.isThisNameMinecraftVanillaMixin(classNameVanilla) || MyLib2.isThisNameMyMOD(classNameVanilla.replace('/', '.'))) {
                                Object pendingState = null;

                                try {
                                    Field pendingStateField = mixinInfo.getClass().getDeclaredField("pendingState");
                                    pendingStateField.setAccessible(true);
                                    pendingState = pendingStateField.get(mixinInfo);
                                    if (pendingState != null) {
                                        ClassNode classNode = null;

                                        try {
                                            Field classNodeField = pendingState.getClass().getDeclaredField("classNode");
                                            classNodeField.setAccessible(true);
                                            classNode = (ClassNode)classNodeField.get(pendingState);
                                            this.editMethods(classNode, classNameVanilla);
                                        } catch (Throwable var16) {
                                            classNode = null;
                                        }
                                    } else if (!phase.equals("acceptTargets()") && !phase.equals("getMixins()")) {
                                        MyLib2.SystemExitForDebug();
                                    }
                                } catch (Throwable var17) {
                                    pendingState = null;
                                }
                            }
                        }
                    } catch (Throwable var19) {
                        mixins = null;
                    }
                }
            }
        }
    }

    private void editMethods(ClassNode classNode, String classNameVanilla) {
        try {
            Iterator<MethodNode> iterator = classNode.methods.iterator();

            while (iterator.hasNext()) {
                MethodNode method = iterator.next();
                List<AnnotationNode> visibleAnnotations = method.visibleAnnotations;
                String methodNameVanilla = "?";
                boolean needCheckForDebug = false;
                boolean needRemove = false;
                if (visibleAnnotations == null) {
                    methodNameVanilla = method.name;
                    if (this.isBadMixinMethod(classNameVanilla, methodNameVanilla, null)) {
                        needRemove = true;
                    }
                } else {
                    for (AnnotationNode annotationNode : visibleAnnotations) {
                        if (isChangeAnnotation(annotationNode.desc)) {
                            needCheckForDebug = true;
                            methodNameVanilla = "?";
                            if (annotationNode.values != null) {
                                for (int i = 0; i < annotationNode.values.size() - 1; i++) {
                                    if (annotationNode.values.get(i) instanceof String str1
                                        && str1.equals("method")
                                        && annotationNode.values.get(i + 1) instanceof ArrayList<?> list
                                        && !list.isEmpty()) {
                                        methodNameVanilla = (String)list.get(0);
                                        if (methodNameVanilla.contains("(")) {
                                            methodNameVanilla = methodNameVanilla.substring(0, methodNameVanilla.indexOf("("));
                                        }
                                        break;
                                    }
                                }
                            }

                            if (methodNameVanilla.equals("?") && (annotationNode.desc.endsWith("/Overwrite;") || annotationNode.desc.endsWith("/Override;"))) {
                                methodNameVanilla = method.name;
                            }

                            if (this.isBadMixinMethod(classNameVanilla, methodNameVanilla, annotationNode.desc)) {
                                needRemove = true;
                                break;
                            }
                        }
                    }
                }

                if (classNode.name.contains("canary/mixin/world/tick_scheduler/LevelChunkTicksMixin") && method.name.equals("reinit")) {
                    needRemove = true;
                }

                if (needRemove) {
                    iterator.remove();
                } else if (needCheckForDebug) {
                }
            }
        } catch (Exception var15) {
        }
    }

    private boolean isBadMixinMethod(String classNameVanilla, String methodNameVanilla, String annotation) {
        if (MyLib2.isThisNameMyMOD(classNameVanilla.replace('/', '.'))) {
            return true;
        } else if (methodNameVanilla.endsWith("init>")) {
            return false;
        } else if (methodNameVanilla.contains("defineSynchedData") || methodNameVanilla.equals("defineSynchedData")) {
            return false;
        } else if (classNameVanilla.endsWith("/EntityRenderDispatcher") && methodNameVanilla.equals("reload")) {
            return false;
        } else {
            return MyLib2.isThisMethodOverride(classNameVanilla, methodNameVanilla, this.getClass()) ? true : isChangeAnnotation(annotation);
        }
    }

    public static boolean isChangeAnnotation(String annotation) {
        return annotation == null
            ? false
            : annotation.contains("mixin/injection/Inject;")
                || annotation.contains("mixin/injection/Modify")
                || annotation.contains("mixin/injection/Redirect")
                || annotation.contains("mixin/Overwrite")
                || annotation.contains("mixinextras/injector/wrap")
                || annotation.contains("mixinextras/injector/v2/Wrap")
                || annotation.contains("mixinextras/injector/modify")
                || annotation.contains("/Override;");
    }

    private ArrayList<Object> getPendingConfigs() {
        Object pendingConfigs = null;

        try {
            MixinEnvironment environment = MixinEnvironment.getCurrentEnvironment();
            Field transformerField = environment.getClass().getDeclaredField("transformer");
            transformerField.setAccessible(true);
            Object transformer = transformerField.get(environment);
            Field processorField = transformer.getClass().getDeclaredField("processor");
            processorField.setAccessible(true);
            Object processor = processorField.get(transformer);
            Field pendingConfigsField = processor.getClass().getDeclaredField("pendingConfigs");
            pendingConfigsField.setAccessible(true);
            pendingConfigs = pendingConfigsField.get(processor);
        } catch (Exception var8) {
            pendingConfigs = null;
        }

        return (ArrayList<Object>)pendingConfigs;
    }

    private void setPendingConfigs(ArrayList<?> configs) {
        try {
            MixinEnvironment environment = MixinEnvironment.getCurrentEnvironment();
            Field transformerField = environment.getClass().getDeclaredField("transformer");
            transformerField.setAccessible(true);
            Object transformer = transformerField.get(environment);
            Field processorField = transformer.getClass().getDeclaredField("processor");
            processorField.setAccessible(true);
            Object processor = processorField.get(transformer);
            Field pendingConfigsField = processor.getClass().getDeclaredField("pendingConfigs");
            pendingConfigsField.setAccessible(true);
            pendingConfigsField.set(processor, configs);
        } catch (Exception var8) {
        }
    }

    private String getPackageNameFromMixinConfig(Object pMixinConfig) {
        Object packageName = null;

        try {
            Field packageNameField = pMixinConfig.getClass().getDeclaredField("mixinPackage");
            packageNameField.setAccessible(true);
            packageName = packageNameField.get(pMixinConfig);
        } catch (Exception var4) {
        }

        if (packageName == null) {
            packageName = "";
        }

        return (String)packageName;
    }
}
