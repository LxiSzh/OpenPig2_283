package kakiku.pig2mod.xform;

import cpw.mods.modlauncher.api.NamedPath;
import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import cpw.mods.modlauncher.serviceapi.ILaunchPluginService.ITransformerLoader;
import cpw.mods.modlauncher.serviceapi.ILaunchPluginService.Phase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class MyPlugin implements ILaunchPluginService {
    private static String gDec = MyCheck.C.dec(MyCheck.C.dec("Ztr3rTHd8j+PB0fYX02zhw=="));
    private static final Map<String, List<String>> gMixinAccessorSetters = new HashMap<>();
    private static final Set<String> gClassNamesFromEntityToPig = Set.of(
        MyCheck.C.dec("ZkKsQ4hKeik0JcZH/Q/fmAIA4KeyVmakNv5S6fLuUSSC7q4RJ+ctIk1plQFyYlx9"),
        MyCheck.C.dec("ZkKsQ4hKeik0JcZH/Q/fmAOe+iOV0r2BT9Qf0XBTH8AACUoGjx29aVNRXfqb7sKu"),
        MyCheck.C.dec("ZkKsQ4hKeik0JcZH/Q/fmKDFEvtziLjnHf/XTcOsYWE="),
        MyCheck.C.dec("ZkKsQ4hKeik0JcZH/Q/fmKSCxO3J3CKpHXHYWic6pzAXJUIV+igf3LN2mtzanZG3"),
        MyCheck.C.dec("ZkKsQ4hKeik0JcZH/Q/fmOlwcLbfHyDnmQ2qkaNqgIPIzb3SR0GZeHxKMEqbzWGr"),
        MyCheck.C.dec("ZkKsQ4hKeik0JcZH/Q/fmKl6KJ5lO375wh4jk16H4G/WaX0reUnKVcV9paRfltRR"),
        MyCheck.C.dec("ZkKsQ4hKeik0JcZH/Q/fmKl6KJ5lO375wh4jk16H4G+I8Ga7rzXdcW8Jo9nHFg0o")
    );

    public void initializeLaunch(ITransformerLoader transformerLoader, NamedPath[] specialPaths) {
        MyLib2.killOtherXformFromMainThread();
        ILaunchPluginService.super.initializeLaunch(transformerLoader, specialPaths);
    }

    public EnumSet<Phase> handlesClass(Type type, boolean b) {
        if (MyLib2.isThisNameOtherBadMOD(type.getClassName())) {
            return EnumSet.of(Phase.AFTER);
        } else {
            return this.isThisVanillaTarget(type.getClassName()) ? EnumSet.of(Phase.AFTER) : EnumSet.noneOf(Phase.class);
        }
    }

    private boolean isThisVanillaTarget(String className) {
        className = className.replace('/', '.');
        return className.equals(MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScClvrq4gqnx7kkCOd/BqVtwkUI6FtvtbrXesaluQ7sGhlIX2rOfaE1YVSr8BDtJd3w=="));
    }

    public String name() {
        return "pig2xform";
    }

    public boolean processClass(Phase phase, ClassNode classNode, Type classType, String reason) {
        boolean bChanged = false;
        if (this.isThisVanillaTarget(classNode.name)) {
            if (reason.equals(MyCheck.C.dec("Lup17lzZ9lETPvlptaHJcg=="))) {
                bChanged = this.processVanillaClass(classNode);
            }
        } else if (reason.equals(MyCheck.C.dec("Lup17lzZ9lETPvlptaHJcg=="))) {
            bChanged = this.processClass(classNode);
        } else if (reason.equals(MyCheck.C.dec("jHg/yBz55kddGLJbg4Q2YA=="))) {
            bChanged = this.processClass(classNode);
        } else if (reason.equals(MyCheck.C.dec("N+hWuFWq6ed4b7fVKLt9fmba960x3fI/jwdH2F9Ns4c="))) {
        }

        if (bChanged) {
            MyXformer2.doNotResetThisClass(classType.getClassName());
        }

        return bChanged;
    }

    public boolean processClass(ClassNode classNode) {
        if (!MyLib2.isThisNameOtherBadMOD(classNode.name.replace('/', '.'))) {
            return false;
        } else if (classNode.name.contains(MyCheck.C.dec("f9Re8R9nLJkjZ0l5l9HR6w=="))) {
            return false;
        } else {
            boolean bChanged = false;
            bChanged |= this.transform_replacingClass(classNode);
            bChanged |= this.transform_replacingMapClass(classNode);
            bChanged |= this.transform_BadEventBus(classNode);
            bChanged |= this.transform_methodWithRunnableArg(classNode);
            bChanged |= this.transform_level_Entity(classNode);
            bChanged |= this.transform_BadThreads(classNode);
            bChanged |= this.transform_mFlashfur(classNode);
            bChanged |= this.transform_crashMethod(classNode);
            bChanged |= this.transform_BadEntityRemover(classNode);
            bChanged |= this.transform_disconnect(classNode);
            bChanged |= this.transform_BadDeleteFiles(classNode);
            bChanged |= this.transform_fakeEntityID(classNode);
            bChanged |= this.transform_BadRender(classNode);
            bChanged |= this.transform_mixinPlugin(classNode);
            bChanged |= this.transform_mixinMethods(classNode);
            bChanged |= this.transform_mixinAccessor(classNode);
            bChanged |= this.transform_playerJammer(classNode);
            bChanged |= this.transform_badDainyuu(classNode);
            bChanged |= this.transform_badDainyuuLimited(classNode);
            bChanged |= this.transform_badCall(classNode);
            return bChanged | this.transform_fixOtherModBug(classNode);
        }
    }

    private boolean processVanillaClass(ClassNode classNode) {
        boolean bChanged = false;
        return bChanged | this.tranVanilla_fixName2(classNode);
    }

    private boolean transform_tempXXXX(ClassNode classNode) {
        boolean bChanged = false;
        Map<String, List<String>> map = new HashMap<>();

        for (Entry<String, List<String>> entry : map.entrySet()) {
            String targetClass = entry.getKey();
            List<String> targetMethods = entry.getValue();
            if (classNode.name.contains(targetClass)) {
                for (String targetMethod : targetMethods) {
                    for (MethodNode method : classNode.methods) {
                        if (method.name.equals(targetMethod)) {
                        }
                    }
                }
            }
        }

        return bChanged;
    }

    private boolean transform_badDainyuuLimited(ClassNode classNode) {
        boolean bChanged = false;
        String ownerClassNameOfMixin = this.getOwnerClassNameFromMixinClass(classNode);

        for (MethodNode method : classNode.methods) {
            for (AbstractInsnNode insn : method.instructions) {
                if (insn.getOpcode() == 181 && insn instanceof FieldInsnNode) {
                    FieldInsnNode fieldInsn = (FieldInsnNode)insn;
                    String owner = fieldInsn.owner;
                    if (ownerClassNameOfMixin != null && owner.equals(classNode.name)) {
                        owner = ownerClassNameOfMixin.replace('.', '/');
                        if (!this.isThisMixinShadowField(classNode, fieldInsn)) {
                            continue;
                        }
                    }

                    if (this.isBadDainyuuLimited(owner, fieldInsn.name)) {
                        InsnList insnList = new InsnList();
                        Type fieldType = Type.getType(fieldInsn.desc);
                        int valueLocal = method.maxLocals;
                        int objectLocal = valueLocal + fieldType.getSize();
                        method.maxLocals = objectLocal + 1;
                        insnList.add(new VarInsnNode(fieldType.getOpcode(54), valueLocal));
                        insnList.add(new VarInsnNode(58, objectLocal));
                        LabelNode doPutField = new LabelNode();
                        insnList.add(new VarInsnNode(25, objectLocal));
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("yaZ/ufZKE2a0YhGjclYUImba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("0xntWvA5bLXMsOg/X56/9A=="),
                                MyCheck.C.dec("pZeveSeTCq6/krkWq2VvIxkLkuqDHl7wHeSbrTzejHs="),
                                false
                            )
                        );
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ=="),
                                MyCheck.C.dec("W4rNpPrFrNfslgF6TeHkeA=="),
                                MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                false
                            )
                        );
                        insnList.add(new LdcInsnNode(MyCheck.C.dec("79pneHF1w69th+nfmt8iQg==")));
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                false
                            )
                        );
                        insnList.add(new JumpInsnNode(153, doPutField));
                        LabelNode after = new LabelNode();
                        insnList.add(new JumpInsnNode(167, after));
                        insnList.add(doPutField);
                        insnList.add(new VarInsnNode(25, objectLocal));
                        insnList.add(new VarInsnNode(fieldType.getOpcode(21), valueLocal));
                        insnList.add(new FieldInsnNode(181, fieldInsn.owner, fieldInsn.name, fieldInsn.desc));
                        insnList.add(after);
                        method.instructions.insert(insn, insnList);
                        method.instructions.remove(insn);
                        bChanged = true;
                    }
                }
            }
        }

        return bChanged;
    }

    private boolean isBadDainyuuLimited(String className, String fieldName) {
        return className.equals(MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6SA3LHwefp60I0jBClhr4Spm/yix5lX2VuYYxn1mW0lQ"));
    }

    private boolean tranVanilla_fixName2(ClassNode classNode) {
        boolean bChanged = false;
        if (classNode.name.equals(MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6dD2x5iurz4doMSxhIZRMcf18EHWvOsfVCRicgV7wbvDlIX2rOfaE1YVSr8BDtJd3w=="))) {
            for (MethodNode method : classNode.methods) {
                if (method.name.equals(MyCheck.C.dec("q5cBSsRx3mArkySPuqixQg=="))) {
                    InsnList insnList = new InsnList();
                    insnList.add(new VarInsnNode(25, 2));
                    insnList.add(
                        new MethodInsnNode(
                            185,
                            MyCheck.C.dec("GdF/5C7+P57y9G/4ODWEo8iixkRCfefVzSAQVZWKhDxWgsV+IUI3Wi14mo4rSacL"),
                            MyCheck.C.dec("5S10eO+Unp2UJMKcGHDRQg=="),
                            MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                            true
                        )
                    );
                    insnList.add(new LdcInsnNode(MyCheck.C.dec("zgyz+gDaoKjb/G0vQLuC1w==")));
                    insnList.add(new LdcInsnNode(MyCheck.C.dec("Ztr3rTHd8j+PB0fYX02zhw==")));
                    insnList.add(
                        new MethodInsnNode(
                            182,
                            MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                            MyCheck.C.dec("Vih6oJ7YB3RKN3a146aR8w=="),
                            MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMDP1QKoaTPbOCCE7xabeIzMr0wPz5zz2uSeIcOueCT7tJisQ+LVKwWGONhYT1soxoQ=="),
                            false
                        )
                    );
                    insnList.add(
                        new MethodInsnNode(
                            184,
                            MyCheck.C.dec("GdF/5C7+P57y9G/4ODWEo8iixkRCfefVzSAQVZWKhDxWgsV+IUI3Wi14mo4rSacL"),
                            MyCheck.C.dec("8iXNda7CQ8UBW/N4BUku1g=="),
                            MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMG70pdiYEOKdsLqK6kPHqA9OVUc0MeEzUB9kALLq3GPk0TpoXMBBN4cy3ZQCErKPdw=="),
                            true
                        )
                    );
                    insnList.add(new VarInsnNode(58, 2));
                    method.instructions.insert(insnList);
                    bChanged = true;
                }
            }
        }

        return bChanged;
    }

    private boolean transform_replacingClass(ClassNode classNode) {
        boolean bChanged = false;

        for (String baseClassName : List.of(
            MyCheck.C.dec("KugE8x+gXfmTkPQhcaghTCjpa83eaqy1ggG3tR9I7feXdXT1GpfDMwtvjp57ok0h"),
            MyCheck.C.dec("KugE8x+gXfmTkPQhcaghTLwXCojpPQCDtMLE3taeHqmAHq5RoHTvGbtkoRfFXVxI"),
            MyCheck.C.dec("KugE8x+gXfmTkPQhcaghTCjpa83eaqy1ggG3tR9I7feym4PktN4htdC9jLAxjIIb"),
            MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bECVn8w00pP4YyFq+OVLpWlNZcCVFiGGRs3DBwdQDLXAJbciGfXeXKgABiREPqTUNeA=="),
            MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bECVn8w00pP4YyFq+OVLpWlPHSddKamKEGezaq3faA092"),
            MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bECVn8w00pP4YyFq+OVLpWlPAzWmFPmsbTkvXnPXNpnGGGBqq2r1if9muVeqOFjydUg=="),
            MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bECVn8w00pP4YyFq+OVLpWlOXb2vB2grCmB/Ett9dUg7e"),
            MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bECVn8w00pP4YyFq+OVLpWlPQZ/wxsedwMx+YV9pvn+N19XhAx0IxtAFG3ONU1pO9QQ=="),
            MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bECVn8w00pP4YyFq+OVLpWlObfUPlp/Q4NrpR7gi/Shvb"),
            MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6S92kF4V9hsHkIwP6+jzFbxYQ85ZjGoHVvV9zW9Ej5a9"),
            MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6S92kF4V9hsHkIwP6+jzFbyq4MZgNcY2R71aL5ARX21VCtqE2QPMAJMe4Hmj2h6/yA=="),
            MyCheck.C.dec("6DE3yYQuRmDQ3sQdnguZJdsxSZAE9UEVC0aYQ1slNRxcQGQNb+HB1DigWUoZUtIb"),
            MyCheck.C.dec("AcA+tzeBGEQbvzarVSzSo5idZqrgqIVepgZBRNlNwKUhvKiCzIft/dIfpWY9QwPV"),
            MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6V7EWpVvn/rtJpAWVtZ2qyMRPjiy82BiiL51vlJhGtnr"),
            MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6dD2x5iurz4doMSxhIZRMcf18EHWvOsfVCRicgV7wbvDMR1QOMpMsIwPUz4grbMN5Q=="),
            MyCheck.C.dec("FdfG+RVx95deBFUaqHeGFQ==")
        )) {
            if (classNode.superName != null && classNode.superName.startsWith(baseClassName) && !classNode.methods.isEmpty()) {
                for (MethodNode method : classNode.methods) {
                    if (!method.name.equals(MyCheck.C.dec("53vGmCW9xEO6aL8wiSGFuQ=="))
                        && !method.name.equals(MyCheck.C.dec("d+0KueTGDEG+vXcATNKFnQ=="))
                        && (method.access & 1280) == 0
                        && (method.access & 8) == 0
                        && MyLib2.isThisMethodOverride(classNode.superName, method.name, this.getClass())) {
                        method.instructions.clear();
                        method.tryCatchBlocks.clear();
                        method.localVariables = null;
                        InsnList insnList = new InsnList();
                        Type returnType = Type.getReturnType(method.desc);
                        insnList.add(new VarInsnNode(25, 0));
                        int index = 1;

                        for (Type arg : Type.getArgumentTypes(method.desc)) {
                            insnList.add(new VarInsnNode(arg.getOpcode(21), index));
                            index += arg.getSize();
                        }

                        insnList.add(new MethodInsnNode(183, classNode.superName, method.name, method.desc, false));
                        insnList.add(new InsnNode(returnType.getOpcode(172)));
                        method.instructions.insert(insnList);
                        bChanged = true;
                    }
                }
            }
        }

        return bChanged;
    }

    private boolean transform_replacingMapClass(ClassNode classNode) {
        boolean bChanged = false;
        Map<String, String> baseClassNames = Map.ofEntries(
            Map.entry(MyCheck.C.dec("GeQr9jfEf5L60EXr2i2TTgppHGTXzgwGIhLdmbCBm0I="), MyCheck.C.dec("AjFfJKeStg3js9GDknQ2JysdZAQsH6oNjd/hCbNzO0Q=")),
            Map.entry(
                MyCheck.C.dec("gDh2rFiFlmLAlLto/KGpE32Cw8iX+wuVeNcKoESC7nghhr5LNOy5AhOcoHimlEQcSJTcLbaf5oc8T+rOgnz2Ng=="),
                MyCheck.C.dec("AjFfJKeStg3js9GDknQ2J03JHhMEIUL9i/OjLlqSYcY+BWycZW530lOT59Y5gbY3")
            ),
            Map.entry(MyCheck.C.dec("qwbhasYwg7ck80jcYC2rYg3nw6zkQseT79lLu/Y8rF0="), MyCheck.C.dec("AjFfJKeStg3js9GDknQ2J02mMPaPl9DeG4KeITaYKWo=")),
            Map.entry(
                MyCheck.C.dec("gDh2rFiFlmLAlLto/KGpExYFPv0lNsCCf4AWgzP46rWzKP6o7/QFa9lDRxkkcZE0Q05Vjk6lZt8phr3MqgSwzw=="),
                MyCheck.C.dec("AjFfJKeStg3js9GDknQ2J/vFoqr6hOPEbpiGJm7ZrsvPj/Vc5KPFDQ6aRDLqJKj4Ztr3rTHd8j+PB0fYX02zhw==")
            ),
            Map.entry(
                MyCheck.C.dec("gDh2rFiFlmLAlLto/KGpExYFPv0lNsCCf4AWgzP46rVW1jFMT2AQqIktJe+D78gEZtr3rTHd8j+PB0fYX02zhw=="),
                MyCheck.C.dec("AjFfJKeStg3js9GDknQ2J+E9ST9hwgmQn7FCYXFeAstc6i4DTKHWcfz65T/NILLN")
            ),
            Map.entry(
                MyCheck.C.dec("gDh2rFiFlmLAlLto/KGpE32Cw8iX+wuVeNcKoESC7niQJCWCIus+BmPr4GELPH+qRmxvKE5jrhdx6geBcbzkLw=="),
                MyCheck.C.dec("AjFfJKeStg3js9GDknQ2J03JHhMEIUL9i/OjLlqSYcaoYjgsxoLUaUw2ZOydXmOIDefDrORCx5Pv2Uu79jysXQ==")
            ),
            Map.entry(
                MyCheck.C.dec("6DE3yYQuRmDQ3sQdnguZJSCtmoW8CBxgbHk2mGPFxwo0CfX6jJrzF90zpRi7f/Ap"),
                MyCheck.C.dec("AjFfJKeStg3js9GDknQ2JzRyAGDtMSl1nTVXCnpzciHojbqqrBaFdcq0ZLLSB9J7")
            )
        );

        for (Entry<String, String> entry : baseClassNames.entrySet()) {
            String baseClassName1 = entry.getKey();
            if (classNode.superName != null && classNode.superName.equals(baseClassName1)) {
                classNode.superName = entry.getValue();

                for (MethodNode method : classNode.methods) {
                    if (method.name.equals(MyCheck.C.dec("53vGmCW9xEO6aL8wiSGFuQ=="))) {
                        for (AbstractInsnNode insn : method.instructions) {
                            if (insn.getOpcode() == 183 && insn instanceof MethodInsnNode) {
                                MethodInsnNode mi = (MethodInsnNode)insn;
                                if (mi.name.equals(MyCheck.C.dec("53vGmCW9xEO6aL8wiSGFuQ==")) && mi.owner.equals(baseClassName1)) {
                                    mi.owner = classNode.superName;
                                    InsnList add = new InsnList();
                                    add.add(new VarInsnNode(25, 0));
                                    add.add(
                                        new MethodInsnNode(
                                            182,
                                            classNode.superName,
                                            MyCheck.C.dec("wmeJk2aCInkKPI2EqUgOrQ5KJxD/8bzEa/y3U3PoNZI="),
                                            MyCheck.C.dec("b3ELkPYCsvP73GEp8zSkeQ=="),
                                            false
                                        )
                                    );
                                    method.instructions.insert(insn, add);
                                }
                            }
                        }
                    } else if (!method.name.equals(MyCheck.C.dec("53vGmCW9xEO6aL8wiSGFuQ=="))
                        && !method.name.equals(MyCheck.C.dec("d+0KueTGDEG+vXcATNKFnQ=="))
                        && (method.access & 1280) == 0
                        && (method.access & 8) == 0
                        && MyLib2.isThisMethodOverride(classNode.superName, method.name, this.getClass())) {
                        InsnList insnList = new InsnList();
                        LabelNode labelContinue = new LabelNode();
                        insnList.add(new VarInsnNode(25, 0));
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                classNode.superName,
                                MyCheck.C.dec("RVZB4FFUaQOZuj1rk3KC8ql3oYUn5YB3I7Prnkr/51Q="),
                                MyCheck.C.dec("echQ/pNkYm3m0oxudd2+tw=="),
                                false
                            )
                        );
                        insnList.add(new JumpInsnNode(154, labelContinue));
                        insnList.add(new VarInsnNode(25, 0));
                        int index = 1;

                        for (Type arg : Type.getArgumentTypes(method.desc)) {
                            insnList.add(new VarInsnNode(arg.getOpcode(21), index));
                            index += arg.getSize();
                        }

                        insnList.add(new MethodInsnNode(183, classNode.superName, method.name, method.desc, false));
                        Type returnType = Type.getReturnType(method.desc);
                        insnList.add(new InsnNode(returnType.getOpcode(172)));
                        insnList.add(labelContinue);
                        method.instructions.insert(insnList);
                    }
                }

                bChanged = true;
            }
        }

        return bChanged;
    }

    private boolean transform_fixOtherModBug(ClassNode classNode) {
        boolean bChanged = false;
        if (classNode.name
            .equals(MyCheck.C.dec("ujRajTXFkCFD3Fx6TWY2vMWmoAuam4FwSPTKZE30EtXp9zNypQGui/xsPOsFjJ+I/I/VUb9h2Vu6AvXMwOsc5RwwQQcskbrk+MSvszS6ZIM="))) {
            for (MethodNode method : classNode.methods) {
                if (method.name.equals(MyCheck.C.dec("6R7e9lSTK+vr5b31EoDNDeBJF2RDtTPVVeret2PQg9g="))) {
                    LabelNode labelContinue = new LabelNode();
                    InsnList insnList = new InsnList();
                    insnList.add(
                        new FieldInsnNode(
                            178,
                            classNode.name,
                            MyCheck.C.dec("OD6qlysR9nDcDm3sCJXmrw=="),
                            MyCheck.C.dec("zGlic58t/VWN7FP5aIwp9YVJYwiA4s84p3JIzbuO/lgEeijjb7C6A5iFbktuIwWs")
                        )
                    );
                    insnList.add(new JumpInsnNode(199, labelContinue));
                    insnList.add(new InsnNode(177));
                    insnList.add(labelContinue);
                    method.instructions.insert(insnList);
                    bChanged = true;
                } else if (method.name.equals(MyCheck.C.dec("jHo+bBW0kT3R8gJQ1hzwaw=="))) {
                    this.makeMethodEmpty(classNode, method);
                    bChanged = true;
                }
            }
        }

        return bChanged;
    }

    private boolean transform_playerJammer(ClassNode classNode) {
        boolean bChanged = false;

        for (MethodNode method : classNode.methods) {
            if (!classNode.name.contains(MyCheck.C.dec("Vdl/WWAQZSvX3RxaGJ1/mE/V6oK/OK4q+Db/ApOFXLg="))
                || !method.name.equals(MyCheck.C.dec("PjmAmoNxOkbu+dIm8sh0Mg=="))) {
                boolean hasSubscribeAnnotation = false;
                if (method.visibleAnnotations != null) {
                    for (AnnotationNode aNode : method.visibleAnnotations) {
                        if (aNode.desc.equals(MyCheck.C.dec("zp0bb+qBEytuKv/7Z7yczqj0ets3hvgtaYuQ+QybQmyb97UzFBBE2ryKgcVlAYAbZtr3rTHd8j+PB0fYX02zhw=="))) {
                            hasSubscribeAnnotation = true;
                            break;
                        }
                    }

                    if (hasSubscribeAnnotation) {
                        if (!method.desc.equals(MyCheck.C.dec("eGrF+q48P0u9Hkt1/TEDXTQpFdKq/YxBmFxDTNGUq74DFg1581TNxeloBMw4B9ElFfYCzaveiDq+a+exDbaEMA=="))
                            && (
                                !method.desc.startsWith(MyCheck.C.dec("eGrF+q48P0u9Hkt1/TEDXU9yoVVfA/UNTUfLypR+4nUyG0SPebwq9YnGLhL8dnq4"))
                                    || !method.desc.endsWith(MyCheck.C.dec("j08rxPCRW2PthUhVmHjHkg=="))
                            )
                            && !method.desc.equals(MyCheck.C.dec("eGrF+q48P0u9Hkt1/TEDXU9yoVVfA/UNTUfLypR+4nUKqAJAWygTtLLVkIG1zPSNO6dl92YjGczt7zQkMlySvA=="))) {
                            if (method.desc.equals(MyCheck.C.dec("eGrF+q48P0u9Hkt1/TEDXePKolI8vteNyeuyqwu7ZcEMGSwFmKDL1aWetKGn2gP4pY/t7O05+hngKjXffZqdDA=="))
                                || method.desc
                                    .equals(
                                        MyCheck.C.dec(
                                            "eGrF+q48P0u9Hkt1/TEDXePKolI8vteNyeuyqwu7ZcFNAwISGgOj/6F9IOO5cX9wAle4+175E3tS2VgoWWbk8xX2As2r3og6vmvnsQ22hDA="
                                        )
                                    )) {
                                int eventVar = (method.access & 8) != 0 ? 0 : 1;
                                int entityVar = eventVar + 1;
                                InsnList insnList = new InsnList();
                                insnList.add(new VarInsnNode(25, eventVar));
                                insnList.add(
                                    new MethodInsnNode(
                                        182,
                                        MyCheck.C.dec("AcA+tzeBGEQbvzarVSzSozyJGKEqZvUirULaAys830It3uXVoOjAy6LNkEKmu1nY"),
                                        MyCheck.C.dec("QZc2lVnIHO8j45qEjiYZvw=="),
                                        MyCheck.C.dec("D0gW1KH/ofLB8YykJYi5u4tauYksTMFtroqIGnwJlSedKeXa2eeb8xtP1QYa1ugm"),
                                        false
                                    )
                                );
                                insnList.add(new VarInsnNode(58, entityVar));
                                LabelNode notPlayer = new LabelNode();
                                insnList.add(new VarInsnNode(25, entityVar));
                                insnList.add(new TypeInsnNode(193, MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEB8M30nZCvKHVR0OPL+DinF0cRQlcrV8UMLKrI5APyam")));
                                insnList.add(new JumpInsnNode(153, notPlayer));
                                insnList.add(new InsnNode(177));
                                insnList.add(notPlayer);
                                LabelNode notPig2 = new LabelNode();
                                insnList.add(new VarInsnNode(25, entityVar));
                                insnList.add(
                                    new MethodInsnNode(
                                        182,
                                        MyCheck.C.dec("yaZ/ufZKE2a0YhGjclYUImba960x3fI/jwdH2F9Ns4c="),
                                        MyCheck.C.dec("0xntWvA5bLXMsOg/X56/9A=="),
                                        MyCheck.C.dec("pZeveSeTCq6/krkWq2VvIxkLkuqDHl7wHeSbrTzejHs="),
                                        false
                                    )
                                );
                                insnList.add(
                                    new MethodInsnNode(
                                        182,
                                        MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ=="),
                                        MyCheck.C.dec("W4rNpPrFrNfslgF6TeHkeA=="),
                                        MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                        false
                                    )
                                );
                                insnList.add(new LdcInsnNode(MyCheck.C.dec("M+p7jYVxIjO1x2tNVB+zaelF2BcmS1auZtCxXc2UVlg=")));
                                insnList.add(
                                    new MethodInsnNode(
                                        182,
                                        MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                        MyCheck.C.dec("a/Z+ejwHPep5xcVmXvJQ4A=="),
                                        MyCheck.C.dec("MA8qM4FhZSz6lg4xdkbPlig27CQk2hTweSA7jLuWD2M="),
                                        false
                                    )
                                );
                                insnList.add(new JumpInsnNode(153, notPig2));
                                insnList.add(new InsnNode(177));
                                insnList.add(notPig2);
                                method.instructions.insert(insnList);
                                bChanged = true;
                            }
                        } else if (this.makeMethodEmpty(classNode, method)) {
                            bChanged = true;
                        }
                    }
                }
            }
        }

        return bChanged;
    }

    private boolean transform_mixinAccessor(ClassNode classNode) {
        boolean bChanged = false;
        String ownerClassNameOfMixin = this.getOwnerClassNameFromMixinClass(classNode);
        if (ownerClassNameOfMixin != null) {
            for (MethodNode method : classNode.methods) {
                if (this.isThisMixinAccessorSet(classNode, method)) {
                    String targetFieldName = null;
                    if (method.visibleAnnotations != null) {
                        for (AnnotationNode annotation : method.visibleAnnotations) {
                            if (annotation.values != null) {
                                for (int i = 0; i < annotation.values.size(); i += 2) {
                                    String key = (String)annotation.values.get(i);
                                    if (MyCheck.C.dec("+RwIPusS3oLyZb+A/PBIfg==").equals(key) && annotation.values.get(i + 1) instanceof String stringValue) {
                                        targetFieldName = stringValue;
                                    }
                                }
                            }
                        }
                    }

                    gMixinAccessorSetters.put(
                        classNode.name + MyCheck.C.dec("aEeEnSPhCaMmXiK+D4YzwA==") + method.name,
                        new ArrayList<>(Arrays.asList(ownerClassNameOfMixin, targetFieldName))
                    );
                }
            }
        }

        for (MethodNode methodx : classNode.methods) {
            for (AbstractInsnNode insn : methodx.instructions.toArray()) {
                if (insn instanceof MethodInsnNode) {
                    MethodInsnNode mInsn = (MethodInsnNode)insn;
                    List<String> target = gMixinAccessorSetters.get(mInsn.owner + MyCheck.C.dec("aEeEnSPhCaMmXiK+D4YzwA==") + mInsn.name);
                    if (target != null) {
                        String targetClassName = target.get(0);
                        String targetFieldName = target.get(1);
                        if (this.isBadDainyuu(targetClassName, targetFieldName)) {
                            this.makeMethodCallEmpty(methodx, mInsn);
                            bChanged = true;
                        }
                    }
                }
            }
        }

        return bChanged;
    }

    private boolean transform_mixinMethods(ClassNode classNode) {
        boolean bChanged = false;
        String ownerClassNameOfMixin = this.getOwnerClassNameFromMixinClass(classNode);
        if (ownerClassNameOfMixin != null) {
            if (this.isThisClassNameFromEntityToPig(ownerClassNameOfMixin)
                || ownerClassNameOfMixin.equals(MyCheck.C.dec("M+p7jYVxIjO1x2tNVB+zaelF2BcmS1auZtCxXc2UVlg="))) {
                for (MethodNode method : classNode.methods) {
                    if (this.isThisMixinAddedMethod(classNode, method)
                        && (
                            !method.name.startsWith(MyCheck.C.dec("lC9LiWfAi35+jLXEWKO3/A=="))
                                || !method.desc.startsWith(MyCheck.C.dec("vH1UMLN13vlFaDZ+i0s15w=="))
                        )
                        && MyXformer2.getInstance().canMakeMethodEmpty(method)
                        && (
                            (method.access & 8) == 0
                                || method.desc.startsWith(MyCheck.C.dec("Yhn3Q+lwbnEc7wJSR2E8BnBO4VOgDADBKwKpuCMkp1nM/4Pj10ValPf1dgmJJ4wC"))
                                || method.desc.startsWith(MyCheck.C.dec("Yhn3Q+lwbnEc7wJSR2E8Bnm4AKNaJllXupfYFSQgkaa5Xm/0X6Z1DcW35iCbIvr0"))
                        )) {
                        InsnList insnList = new InsnList();
                        LabelNode labelReturn = new LabelNode();
                        LabelNode labelContinue = new LabelNode();
                        insnList.add(new VarInsnNode(25, 0));
                        insnList.add(new JumpInsnNode(198, labelContinue));
                        insnList.add(new VarInsnNode(25, 0));
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("yaZ/ufZKE2a0YhGjclYUImba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("0xntWvA5bLXMsOg/X56/9A=="),
                                MyCheck.C.dec("pZeveSeTCq6/krkWq2VvIxkLkuqDHl7wHeSbrTzejHs="),
                                false
                            )
                        );
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ=="),
                                MyCheck.C.dec("W4rNpPrFrNfslgF6TeHkeA=="),
                                MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                false
                            )
                        );
                        insnList.add(new LdcInsnNode(MyCheck.C.dec("M+p7jYVxIjO1x2tNVB+zaelF2BcmS1auZtCxXc2UVlg=")));
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                false
                            )
                        );
                        insnList.add(new JumpInsnNode(154, labelReturn));
                        insnList.add(new JumpInsnNode(167, labelContinue));
                        insnList.add(labelReturn);
                        MyXformer2.getInstance().insnListAddReturn(method, insnList);
                        insnList.add(labelContinue);
                        method.instructions.insert(insnList);
                        bChanged = true;
                    }
                }
            }

            for (MethodNode methodx : classNode.methods) {
                String targetMethodName = this.getMixinTargetMethodName(classNode, methodx);
                if (targetMethodName.equals(MyCheck.C.dec("53vGmCW9xEO6aL8wiSGFuQ==")) || targetMethodName.equals(MyCheck.C.dec("d+0KueTGDEG+vXcATNKFnQ=="))) {
                    boolean hasThrow = false;

                    for (AbstractInsnNode insn : methodx.instructions) {
                        if (insn.getOpcode() == 191) {
                            hasThrow = true;
                            break;
                        }
                    }

                    if (hasThrow && this.makeMethodEmpty(classNode, methodx)) {
                        bChanged = true;
                    }
                }
            }
        }

        return bChanged;
    }

    private boolean transform_mixinPlugin(ClassNode classNode) {
        boolean bChanged = false;
        if (classNode.interfaces.contains(MyCheck.C.dec("ExWiij7t6WwKPPhm5yGmnAPxwMnyIdYxWNBbdXA3D0nlqs6GprIs3Zwd2kdL/w8IlY4APObT0lQKoYtYK1zAYg=="))) {
            for (MethodNode method : classNode.methods) {
                if ((
                        method.name.equals(MyCheck.C.dec("B7E1iG34cC04ISo2+XOdkg=="))
                            || method.name.equals(MyCheck.C.dec("Bw+WZoKvJCRBfIy+5D1aVg=="))
                            || method.name.equals(MyCheck.C.dec("onnwIJrzXXA42aETWByL+w=="))
                    )
                    && this.makeMethodEmpty(classNode, method)) {
                    bChanged = true;
                }
            }
        }

        for (MethodNode methodx : classNode.methods) {
            boolean needChange = false;

            for (AbstractInsnNode insn : methodx.instructions.toArray()) {
                if (insn instanceof MethodInsnNode mInsn) {
                    if (mInsn.owner.equals(MyCheck.C.dec("ExWiij7t6WwKPPhm5yGmnCOkypY59aBrwmj/CoUlx3lXUWbjkauyPrCqWEjFRq7O"))
                        && mInsn.name.equals(MyCheck.C.dec("cPLx7vweRzjHFNK4LQPvJ9KBezBTO1wGz2drcrIxeyk="))) {
                        needChange = true;
                        break;
                    }
                } else if (insn instanceof FieldInsnNode fInsn
                    && insn.getOpcode() == 178
                    && fInsn.owner.equals(MyCheck.C.dec("ExWiij7t6WwKPPhm5yGmnCOkypY59aBrwmj/CoUlx3lXUWbjkauyPrCqWEjFRq7O"))
                    && fInsn.name.equals(MyCheck.C.dec("tH6gyPNh4LHGCTUDdVB+eibqyU+RVdPYvvz3KBfTHlM="))) {
                    needChange = true;
                    break;
                }
            }

            if (needChange && this.makeMethodEmpty(classNode, methodx)) {
                bChanged = true;
            }
        }

        return bChanged;
    }

    private boolean transform_BadRender(ClassNode classNode) {
        boolean bChanged = false;

        for (MethodNode method : classNode.methods) {
            boolean usingSetOrtho = false;
            boolean usingRenderClear = false;
            boolean usingTranslateBig = false;
            boolean usingJNIinvoke = false;
            boolean usingBuilderAssign = false;
            boolean usingGLFWMakeContextCurrent = false;

            for (AbstractInsnNode insn : method.instructions.toArray()) {
                if (insn instanceof MethodInsnNode) {
                    MethodInsnNode mInsn = (MethodInsnNode)insn;
                    if (mInsn.owner.equals(MyCheck.C.dec("TCz+d0PbVnBTeDB/hPg0qXawao/T+DeCST1J01QKJYE="))
                        && mInsn.name.equals(MyCheck.C.dec("p5KnoAq7VuDAoG2o80OgUg=="))) {
                        usingSetOrtho = true;
                    }

                    if (mInsn.owner.equals(MyCheck.C.dec("MrQOMFtqK/i7EXxKr3WUwPgvrro9Vxd757DGw28RruX1faF9Rh2NPtR6eLjgOc8o"))
                        && mInsn.name.equals(MyCheck.C.dec("UA1VL9C5/9NrYIzma07KIA=="))) {
                        usingRenderClear = true;
                    }

                    if (mInsn.name.equals(MyCheck.C.dec("PtfwVZp6xvbvnBL3hZ0Q1A=="))
                        && (mInsn.desc.contains(MyCheck.C.dec("QGT+5xR7SZ2Pjhyi0wsbhw==")) || mInsn.desc.contains(MyCheck.C.dec("OVlFw7zoqTIl1urMDgF+zg==")))) {
                        AbstractInsnNode d = mInsn.getPrevious();
                        if (d instanceof LdcInsnNode) {
                            LdcInsnNode ldc = (LdcInsnNode)d;
                            Object var19 = ldc.cst;
                            if (var19 instanceof Float) {
                                Float f = (Float)var19;
                                if (Math.abs(f) >= 10000.0F) {
                                    usingTranslateBig = true;
                                }
                            }

                            var19 = ldc.cst;
                            if (var19 instanceof Double) {
                                Double dx = (Double)var19;
                                if (Math.abs(dx) >= 10000.0) {
                                    usingTranslateBig = true;
                                }
                            }
                        }
                    }

                    if (mInsn.owner.equals(MyCheck.C.dec("W3io3c1B2NR+uApcNZhiyDBZbhRlF7SiXI2XvS7k/qg="))
                        && mInsn.name.startsWith(MyCheck.C.dec("oobS1MQOVhDY3bJVjXV/0w=="))) {
                        usingJNIinvoke = true;
                    }

                    if (mInsn.owner.equals(MyCheck.C.dec("10F84bt/pgH4SuCtgJgAdxbVKF/vYqDQBdQaZrj3qrM="))
                        && mInsn.name.equals(MyCheck.C.dec("394iiu6ru2XOzjIJOC5Gn1cSDVpG/iVa/3M0N1pYfLw="))) {
                        usingGLFWMakeContextCurrent = true;
                    }
                } else if (insn instanceof FieldInsnNode) {
                    FieldInsnNode fInsn = (FieldInsnNode)insn;
                    if (fInsn.getOpcode() == 181
                        && fInsn.owner.equals(MyCheck.C.dec("MrQOMFtqK/i7EXxKr3WUwN8qyzJBESjA9WuHvL+dJMEbSxjKWk7Kfl2ljgq5OSKD"))
                        && fInsn.name.equals(MyCheck.C.dec("l7z23dyrvGp6dcxidtv0AQ=="))) {
                        usingBuilderAssign = true;
                    }
                }
            }

            if ((usingSetOrtho && usingRenderClear || usingTranslateBig || usingJNIinvoke || usingBuilderAssign || usingGLFWMakeContextCurrent)
                && this.makeMethodEmpty(classNode, method)) {
                bChanged = true;
            }
        }

        return bChanged;
    }

    private boolean transform_fakeEntityID(ClassNode classNode) {
        boolean bChanged = false;

        for (MethodNode method : classNode.methods) {
            if ((method.access & 1280) == 0) {
                if (method.name.equals(MyCheck.C.dec("UUkfH6daY7zDUkIp0xY/8Q=="))
                    && method.desc.equals(MyCheck.C.dec("Oxq7ejEjKV43HX9+tUTLBqX+LMJ4nOHwQXHBiRo0WgU="))) {
                    method.instructions.clear();
                    method.tryCatchBlocks.clear();
                    method.localVariables = null;
                    InsnList insnList = new InsnList();
                    insnList.add(new VarInsnNode(25, 0));
                    insnList.add(
                        new MethodInsnNode(
                            183,
                            MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEIqkwj9Kg+Cpyv8DyMthx9aC7q4RJ+ctIk1plQFyYlx9"),
                            MyCheck.C.dec("UUkfH6daY7zDUkIp0xY/8Q=="),
                            MyCheck.C.dec("Oxq7ejEjKV43HX9+tUTLBqX+LMJ4nOHwQXHBiRo0WgU="),
                            false
                        )
                    );
                    insnList.add(new InsnNode(176));
                    method.instructions.insert(insnList);
                    bChanged = true;
                } else if (method.name.equals(MyCheck.C.dec("kzTf+yZKiu9xPkLFS9yzdw==")) && method.desc.equals(MyCheck.C.dec("FqoyKdW9r1B6b5AUdCtpeQ=="))) {
                    method.instructions.clear();
                    method.tryCatchBlocks.clear();
                    method.localVariables = null;
                    InsnList insnList = new InsnList();
                    insnList.add(new VarInsnNode(25, 0));
                    insnList.add(
                        new MethodInsnNode(
                            183,
                            MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEIqkwj9Kg+Cpyv8DyMthx9aC7q4RJ+ctIk1plQFyYlx9"),
                            MyCheck.C.dec("kzTf+yZKiu9xPkLFS9yzdw=="),
                            MyCheck.C.dec("FqoyKdW9r1B6b5AUdCtpeQ=="),
                            false
                        )
                    );
                    insnList.add(new InsnNode(172));
                    method.instructions.insert(insnList);
                    bChanged = true;
                } else if (method.name.equals(MyCheck.C.dec("GNs7sEzI0DMTeXMyYOE72Q=="))
                    && method.desc.equals(MyCheck.C.dec("YW2zu1aR1PjEdLnVvF0azi4OkXDjAFxH/zyyTMcpmoc="))) {
                    method.instructions.clear();
                    method.tryCatchBlocks.clear();
                    method.localVariables = null;
                    InsnList insnList = new InsnList();
                    insnList.add(new VarInsnNode(25, 0));
                    insnList.add(new VarInsnNode(25, 1));
                    insnList.add(
                        new MethodInsnNode(
                            183,
                            MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEIqkwj9Kg+Cpyv8DyMthx9aC7q4RJ+ctIk1plQFyYlx9"),
                            MyCheck.C.dec("GNs7sEzI0DMTeXMyYOE72Q=="),
                            MyCheck.C.dec("YW2zu1aR1PjEdLnVvF0azi4OkXDjAFxH/zyyTMcpmoc="),
                            false
                        )
                    );
                    insnList.add(new InsnNode(177));
                    method.instructions.insert(insnList);
                    bChanged = true;
                } else if (method.name.equals(MyCheck.C.dec("KyPxevLPfirWLMbFuHx8eQ==")) && method.desc.equals(MyCheck.C.dec("d17+p1XFpyWATlRGYPMjsA=="))) {
                    method.instructions.clear();
                    method.tryCatchBlocks.clear();
                    method.localVariables = null;
                    InsnList insnList = new InsnList();
                    insnList.add(new VarInsnNode(25, 0));
                    insnList.add(new VarInsnNode(21, 1));
                    insnList.add(
                        new MethodInsnNode(
                            183,
                            MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEIqkwj9Kg+Cpyv8DyMthx9aC7q4RJ+ctIk1plQFyYlx9"),
                            MyCheck.C.dec("KyPxevLPfirWLMbFuHx8eQ=="),
                            MyCheck.C.dec("d17+p1XFpyWATlRGYPMjsA=="),
                            false
                        )
                    );
                    insnList.add(new InsnNode(177));
                    method.instructions.insert(insnList);
                    bChanged = true;
                }
            }
        }

        return bChanged;
    }

    private boolean transform_BadDeleteFiles(ClassNode classNode) {
        boolean bChanged = false;
        Set<MethodNode> fileDeleteMethods = new HashSet<>();
        Set<MethodNode> entitiesMethods = new HashSet<>();

        for (MethodNode method0 : classNode.methods) {
            boolean usesEntities = false;
            boolean callsDelete = false;

            for (AbstractInsnNode insn : method0.instructions) {
                if (insn instanceof LdcInsnNode ldc && MyCheck.C.dec("5Lbc+njEJkTFzzUh5ZUOWg==").equals(ldc.cst)) {
                    usesEntities = true;
                }

                if (insn instanceof MethodInsnNode minsn
                    && (
                        minsn.owner.equals(MyCheck.C.dec("xye8lv56sFgzqChPOdEvWw=="))
                                && minsn.name.equals(MyCheck.C.dec("7+HSjBniLIpdQpX05I3wzg=="))
                                && minsn.desc.equals(MyCheck.C.dec("echQ/pNkYm3m0oxudd2+tw=="))
                            || minsn.owner.equals(MyCheck.C.dec("xye8lv56sFgzqChPOdEvWw=="))
                                && minsn.name.equals(MyCheck.C.dec("5O18LLPN5Uhs9kOcDI5Qww=="))
                                && minsn.desc.equals(MyCheck.C.dec("b3ELkPYCsvP73GEp8zSkeQ=="))
                            || minsn.owner.equals(MyCheck.C.dec("k97CS18YwNdh65rW0gq2AhW/GGmIDcxQFzoMcpqdcnE="))
                                && minsn.name.equals(MyCheck.C.dec("7+HSjBniLIpdQpX05I3wzg=="))
                                && minsn.desc.equals(MyCheck.C.dec("4MT4NV3a+pbEjXcDWHMXxremuLLhvVp9qTVOyXdOCGU="))
                            || minsn.owner.equals(MyCheck.C.dec("k97CS18YwNdh65rW0gq2AhW/GGmIDcxQFzoMcpqdcnE="))
                                && minsn.name.equals(MyCheck.C.dec("5v4q54iOMJTQtbLVt8RW0Q=="))
                                && minsn.desc.equals(MyCheck.C.dec("4MT4NV3a+pbEjXcDWHMXxq7z2Zrg9Dix127luMYFLFM="))
                    )) {
                    callsDelete = true;
                }
            }

            if (usesEntities) {
                entitiesMethods.add(method0);
            }

            if (callsDelete) {
                fileDeleteMethods.add(method0);
            }
        }

        for (MethodNode method : fileDeleteMethods) {
            boolean isThisTargetMethod = entitiesMethods.contains(method);
            if (!isThisTargetMethod) {
                for (MethodNode caller : entitiesMethods) {
                    if (method.name.contains(caller.name)) {
                        isThisTargetMethod = true;
                        break;
                    }

                    for (AbstractInsnNode insn : caller.instructions) {
                        if (insn instanceof MethodInsnNode minsn && minsn.name.equals(method.name) && minsn.desc.equals(method.desc)) {
                            isThisTargetMethod = true;
                            break;
                        }
                    }

                    if (isThisTargetMethod) {
                        break;
                    }
                }
            }

            if (isThisTargetMethod && this.makeMethodEmpty(classNode, method)) {
                bChanged = true;
            }
        }

        return bChanged;
    }

    private boolean transform_disconnect(ClassNode classNode) {
        boolean bChanged = false;

        for (MethodNode method : classNode.methods) {
            for (AbstractInsnNode insn : method.instructions) {
                if (insn instanceof MethodInsnNode) {
                    MethodInsnNode methodInsn = (MethodInsnNode)insn;
                    if (insn.getOpcode() == 182
                        && methodInsn.name.equals(MyCheck.C.dec("9ICkYKLELvOOIvsyUUl8Ig=="))
                        && methodInsn.desc.equals(MyCheck.C.dec("Yhn3Q+lwbnEc7wJSR2E8Bt/JNrIBSExpnGIMgSHqCzKJ1ny4EodK1jV0HH4himLE"))) {
                        MethodInsnNode staticCall = new MethodInsnNode(
                            184,
                            MyCheck.C.dec("klPvsUiiCo161KCDM6cbU+qy+QepXPXnu9q9wGRZW8g="),
                            MyCheck.C.dec("LxwPWOFfMgAm48NXWfFSZw=="),
                            MyCheck.C.dec(
                                "Yhn3Q+lwbnEc7wJSR2E8Bj7hJxwi6hp29NwjiX5DdUAavgrgGFtdhlxDv9TzSqjUm1oMUFxJW3b6ZihUCqzxxKrwdlRSqzculjVJOZRN5DVRU4wGC1TYwcnEpPgnd3nv+iPQ8NoQs3xyAwg5PxFnyg=="
                            ),
                            false
                        );
                        method.instructions.set(methodInsn, staticCall);
                        bChanged = true;
                    }
                }
            }
        }

        return bChanged;
    }

    private boolean transform_badDainyuu(ClassNode classNode) {
        boolean bChanged = false;
        String ownerClassNameOfMixin = this.getOwnerClassNameFromMixinClass(classNode);

        for (MethodNode method : classNode.methods) {
            for (AbstractInsnNode insn : method.instructions) {
                if ((insn.getOpcode() == 181 || insn.getOpcode() == 179) && insn instanceof FieldInsnNode) {
                    FieldInsnNode fieldInsn = (FieldInsnNode)insn;
                    String owner = fieldInsn.owner;
                    if (ownerClassNameOfMixin != null && owner.equals(classNode.name)) {
                        owner = ownerClassNameOfMixin.replace('.', '/');
                        if (!this.isThisMixinShadowField(classNode, fieldInsn)) {
                            continue;
                        }
                    }

                    if (this.isBadDainyuu(owner, fieldInsn.name)) {
                        InsnList insnList = new InsnList();
                        if (!fieldInsn.desc.equals(MyCheck.C.dec("xS+VfCuwI37WekKwwptGDg=="))
                            && !fieldInsn.desc.equals(MyCheck.C.dec("MTVAokdbrdlZ43hBkqBjpw=="))) {
                            insnList.add(new InsnNode(87));
                        } else {
                            insnList.add(new InsnNode(88));
                        }

                        if (insn.getOpcode() == 181) {
                            insnList.add(new InsnNode(87));
                        }

                        method.instructions.insert(insn, insnList);
                        method.instructions.remove(insn);
                        bChanged = true;
                    }
                }
            }
        }

        return bChanged;
    }

    private boolean isBadDainyuu(String className, String fieldName) {
        if (!className.equals(MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6TfNhTyvzHvAri6iD2cx+FA="))
            && !className.equals(MyCheck.C.dec("AcA+tzeBGEQbvzarVSzSo8aFMZhM1QX/lNO0pKE0bjNWMc8lWrVyAW/22ucaAzqv"))
            && !className.equals(MyCheck.C.dec("KugE8x+gXfmTkPQhcaghTCjpa83eaqy1ggG3tR9I7feXdXT1GpfDMwtvjp57ok0h"))
            && !className.equals(MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bECVn8w00pP4YyFq+OVLpWlOXb2vB2grCmB/Ett9dUg7e"))
            && !className.equals(MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bECVn8w00pP4YyFq+OVLpWlNZcCVFiGGRs3DBwdQDLXAJbciGfXeXKgABiREPqTUNeA=="))
            && !className.equals(MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6S92kF4V9hsHkIwP6+jzFbxYQ85ZjGoHVvV9zW9Ej5a9"))
            && !className.equals(MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bECVn8w00pP4YyFq+OVLpWlPQZ/wxsedwMx+YV9pvn+N19XhAx0IxtAFG3ONU1pO9QQ=="))
            && !className.equals(MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bECVn8w00pP4YyFq+OVLpWlObfUPlp/Q4NrpR7gi/Shvb"))
            && !className.equals(MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6dD2x5iurz4doMSxhIZRMcf18EHWvOsfVCRicgV7wbvDMR1QOMpMsIwPUz4grbMN5Q=="))
            && !className.equals(MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEB8M30nZCvKHVR0OPL+DinF0cRQlcrV8UMLKrI5APyam"))
            && !className.equals(MyCheck.C.dec("MrQOMFtqK/i7EXxKr3WUwB8zh2FKY+LS8Cy4eNRt4i7r7eK56FbvWjK3Ebdot1et"))
            && !className.equals(MyCheck.C.dec("MrQOMFtqK/i7EXxKr3WUwPgvrro9Vxd757DGw28RruX1faF9Rh2NPtR6eLjgOc8o"))
            && !className.equals(MyCheck.C.dec("MrQOMFtqK/i7EXxKr3WUwN/tCwfRbamkLMw8/474RvC3dZbUBRYhjSH0xlck0iWl"))
            && !className.equals(MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6ZN8ZczNAZHLfh8TwXb+3U1LlyapM/qI7ytjpZ3WrjhA"))
            && !className.equals(MyCheck.C.dec("GdF/5C7+P57y9G/4ODWEoxYPmeLq4FMg9b9b5h12KoNm2vetMd3yP48HR9hfTbOH"))
            && !className.equals(MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6S92kF4V9hsHkIwP6+jzFbyq4MZgNcY2R71aL5ARX21VCtqE2QPMAJMe4Hmj2h6/yA=="))
            && !className.equals(MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6cMmKi77FUPszHxH+xFPzc/Mw/SaAP8Viou0rerD1o1d"))
            && !className.equals(MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6V7EWpVvn/rtJpAWVtZ2qyMRPjiy82BiiL51vlJhGtnr"))
            && !className.equals(MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6eu3j5sgTPf5UACgjuz3Ul8="))
            && !className.equals(MyCheck.C.dec("KugE8x+gXfmTkPQhcaghTP5ePu/YC5YMJm2g9f/Ln9b3ZdA0i/DeQAoq1PPJRxOC"))
            && !className.equals(MyCheck.C.dec("AcA+tzeBGEQbvzarVSzSo6cwAZDs6MULjnYdI18ijkDpayplS1LZvOutt74G4fFOJgecOZV0KHqJBBc/gJp/iw=="))
            && !className.equals(MyCheck.C.dec("/Q4Pz5wIbYVbV/u7qlrRJSacQPprqo1/hpj1UcwSZmg="))
            && !className.equals(MyCheck.C.dec("/Q4Pz5wIbYVbV/u7qlrRJQG/UfQds0PqIxV8T2YLw59VVaojqja38roninsweVM1"))
            && !className.equals(MyCheck.C.dec("/Q4Pz5wIbYVbV/u7qlrRJaktVnSdVDD4WlS9KG78gUjGbPmsbk3dSfPUptyENVSX"))
            && !className.equals(MyCheck.C.dec("6DE3yYQuRmDQ3sQdnguZJdsxSZAE9UEVC0aYQ1slNRxcQGQNb+HB1DigWUoZUtIb"))
            && !className.equals(MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bECVn8w00pP4YyFq+OVLpWlPAzWmFPmsbTkvXnPXNpnGGGBqq2r1if9muVeqOFjydUg=="))
            && !className.equals(MyCheck.C.dec("KugE8x+gXfmTkPQhcaghTCjpa83eaqy1ggG3tR9I7fcBVXrYnPA/fkpspq16l5Ig"))
            && !className.startsWith(MyCheck.C.dec("KugE8x+gXfmTkPQhcaghTLwXCojpPQCDtMLE3taeHqmAHq5RoHTvGbtkoRfFXVxI"))
            && !className.equals(MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bECVn8w00pP4YyFq+OVLpWlPHSddKamKEGezaq3faA092"))
            && !className.equals(MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bECVn8w00pP4YyFq+OVLpWlPV9q8KhT3U2GnpQAPpthbwUauUnJf38Uo5S2tyMZqWIw=="))
            && !className.equals(MyCheck.C.dec("KugE8x+gXfmTkPQhcaghTCjpa83eaqy1ggG3tR9I7feym4PktN4htdC9jLAxjIIb"))
            && !className.equals(MyCheck.C.dec("KugE8x+gXfmTkPQhcaghTB4e0rxT+KCyuLvf/eBfhXUHQGXd61JMIvVi0sCC7SuA"))
            && !className.equals(MyCheck.C.dec("KugE8x+gXfmTkPQhcaghTBxUX0YFLLUbxLdo4r35U0Q0CfX6jJrzF90zpRi7f/Ap"))
            && !className.equals(MyCheck.C.dec("KugE8x+gXfmTkPQhcaghTDzWFgzvFZTJppLsoh1hW48NTIjfN5G2ngJj5I8s8CQw"))
            && !className.equals(MyCheck.C.dec("KugE8x+gXfmTkPQhcaghTLwXCojpPQCDtMLE3taeHqnOyzFNqN4qyxHLYJ4/Zmr8"))
            && !className.equals(MyCheck.C.dec("GdF/5C7+P57y9G/4ODWEo0AsA55xn7E0fggj7McTJ2CgqwiaSAgkOPuWKTv3pDYE"))
            && !className.equals(MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEO4V3SMIYF2998CWJ7xRQoBlO4n2bttToWXNIApB+9fZ"))
            && !className.equals(MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEGcwIBrm6k2YkNgdEkZwd0yOv80sEPxuC6r2KrvG7iXY"))
            && !className.equals(MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEB8M30nZCvKHVR0OPL+DinHb5GCqbWCyHQfI5wbPD2zq"))
            && !className.equals(MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6YdpPRGjP/e7GEGbgveQwCAqYyvdwSL9E8v54li6kotx"))
            && !className.equals(MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6dD2x5iurz4doMSxhIZRMcfKQLtPobXOrdjb8gI02pK3Ztr3rTHd8j+PB0fYX02zhw=="))
            && !className.equals(MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6dD2x5iurz4doMSxhIZRMcfwQtcoJHGqDHXcCA2Qz825YijP3fsMTM7ACsIVLXQhXg=="))
            && !className.equals(MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6dD2x5iurz4doMSxhIZRMcf18EHWvOsfVCRicgV7wbvDlIX2rOfaE1YVSr8BDtJd3w=="))
            && !className.equals(MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6beAK+jHM24mgQJZUw92P3htaTPqiaKIn+Vf6G976sfz"))
            && !className.equals(MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6ZOuSs1y+eFx1D/O3+SoKR4RAu+/7z2i142a6NLgPXyS"))
            && !className.equals(MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6d5lXVr65qsRh4/PwQ0nBUQv1QA+Sb6WRjMnB8wzX3q8"))
            && !className.equals(MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6c8+ZV0uXfGwVN98PUdjvwq5R/us2v5IxRkeUkOEaq86"))
            && !className.equals(MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6ULF6KjEM1Acv0TjnLoxDhxm2vetMd3yP48HR9hfTbOH"))
            && !className.equals(MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6SA3LHwefp60I0jBClhr4SpxdLrtVopA2/bL3ahrxMeK"))
            && !className.startsWith(MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6T/pSCaGZtEyu84sba/5lvXV5RTnILqidf5EVEyADYCNbG3T6kFcwQssBbumtJEJlQ=="))
            && !className.startsWith(MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6XPpbds4yaUvEFJSQ0+kH0DVh8leW84CjGJfaCKRQ9sA"))
            && !className.startsWith(MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6bh9gq8AG5TMz1uVv1uoke/haemF7rB+5weEKfBVO9Wu"))
            && !className.startsWith(MyCheck.C.dec("MrQOMFtqK/i7EXxKr3WUwBNsaBQjozjSRZYCDk8hz7ngPlcLao0Sjf1WrKnflg8d"))
            && !className.equals(MyCheck.C.dec("MrQOMFtqK/i7EXxKr3WUwB8zh2FKY+LS8Cy4eNRt4i7Zdd3iLmEi8xSjLTo/GQd/"))
            && !className.startsWith(MyCheck.C.dec("AcA+tzeBGEQbvzarVSzSowBm0ElmjxQbo1swV28n8+QoS777HO8bttI+nJTQihTGNAn1+oya8xfdM6UYu3/wKQ=="))
            && !className.equals(MyCheck.C.dec("AcA+tzeBGEQbvzarVSzSozyJGKEqZvUirULaAys830KvbvT9mZqvhlk1vMNEsHHNj17HsoTUEVnKYnn73SS+8g=="))
            && !className.equals(MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6SA3LHwefp60I0jBClhr4SrWZCf+rzc0769Hx2WiDlxP"))
            && !className.equals(MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6SA3LHwefp60I0jBClhr4SoqgEDrs8u4JIPgIAqni4+h5VrCueytJ4i18YmLUiaUnQ=="))
            && !className.equals(MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6SA3LHwefp60I0jBClhr4SqbVY0Uhhbse4JFkIz5200geDOg2vp6+nxNSMEgtr/tqg=="))
            && (
                !className.startsWith(MyCheck.C.dec("FdfG+RVx95deBFUaqHeGFQ=="))
                    || className.equals(MyCheck.C.dec("klPvsUiiCo161KCDM6cbU8ittdbrIdU/phEOhQbiSTA="))
            )) {
            if (this.isThisClassNameFromEntityToPig(className)) {
                if (fieldName == null) {
                    return true;
                }

                if (fieldName.equals(MyCheck.C.dec("lGUzIRmkgMhoUatcl/k5Gw=="))
                    || fieldName.equals(MyCheck.C.dec("ajdfbU0Tc/4LTwSvB5h27A=="))
                    || fieldName.equals(MyCheck.C.dec("X6fIthl6SD74JeyG5q1OUw=="))
                    || fieldName.equals(MyCheck.C.dec("q7iMxC9PC12lBaYhrgt/QQ=="))
                    || fieldName.equals(MyCheck.C.dec("SGENqXmh8rD4kiddbjtsGA=="))
                    || fieldName.equals(MyCheck.C.dec("gbwXNz98UZDGHAhFPHdPDA=="))
                    || fieldName.equals(MyCheck.C.dec("Cwht5J16ouWgLmyk1QJ9tw=="))
                    || fieldName.equals(MyCheck.C.dec("iY2ugmTfOENmWF8DusQQFQ=="))
                    || fieldName.equals(MyCheck.C.dec("41VCNqUSYpWtCNdHV87k9w=="))
                    || fieldName.equals(MyCheck.C.dec("wClDqbo9za9O/ptymJXV5w=="))
                    || fieldName.equals(MyCheck.C.dec("JqtCLhOOifJnjV5Z81Skqg=="))
                    || fieldName.equals(MyCheck.C.dec("z5DHh1VZ4fXueYVUISsfBg=="))
                    || fieldName.equals(MyCheck.C.dec("rCvEbZVEpmhOvROpW5ff9Q=="))
                    || fieldName.equals(MyCheck.C.dec("/rp7Q4gwysKvxSftytUWAw=="))
                    || fieldName.equals(MyCheck.C.dec("ep0tJUltck0BiC7Ii84T4g=="))
                    || fieldName.equals(MyCheck.C.dec("0gjGaeoUKQLUBln/cMWPyA=="))
                    || fieldName.equals(MyCheck.C.dec("sronXEHeK+fdCvNw8CCo8A=="))
                    || fieldName.equals(MyCheck.C.dec("hTfofnEC8jwSUsDkTASKaw=="))
                    || fieldName.equals(MyCheck.C.dec("uE9KdPPVAGfwVMDLjMl/Zg=="))
                    || fieldName.equals(MyCheck.C.dec("riFwdDrVU5zVg/b1RNDwDg=="))
                    || fieldName.equals(MyCheck.C.dec("axvaVBnhV4io/b5JxGIjWw=="))
                    || fieldName.equals(MyCheck.C.dec("mtjXIFJpdN7LlhMfBh18uw=="))
                    || fieldName.equals(MyCheck.C.dec("1yGg0rcuZiENwhu34Uompw=="))
                    || fieldName.equals(MyCheck.C.dec("aPmX9mlteCgt8wEDxufRaQ=="))
                    || fieldName.equals(MyCheck.C.dec("Kx3ozKiTQ98ZXlnEqJaRyQ=="))
                    || fieldName.equals(MyCheck.C.dec("Vmp9mqN1crGmKrHycab7dw=="))) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }

    private boolean isThisClassNameFromEntityToPig(String pSlashName) {
        String name = pSlashName.replace('/', '.');
        return gClassNamesFromEntityToPig.contains(name);
    }

    private String getOwnerClassNameFromMixinClass(ClassNode classNode) {
        if (classNode.invisibleAnnotations != null) {
            for (AnnotationNode annotation : classNode.invisibleAnnotations) {
                if (annotation.desc.equals(MyCheck.C.dec("j1ywzOZq8fogUWeE0khI5GcwjIwfstwlIholG5/i7MZQOAaUtKzySt0IR9kFocYH"))) {
                    List<?> aValues = annotation.values;

                    for (int i = 0; i < aValues.size(); i += 2) {
                        Object targetNames = aValues.get(i);
                        if (targetNames instanceof String) {
                            String key = (String)targetNames;
                            if (key.equals(MyCheck.C.dec("+RwIPusS3oLyZb+A/PBIfg=="))) {
                                Object var15 = aValues.get(i + 1);
                                if (var15 instanceof List) {
                                    for (Object type0 : (List)var15) {
                                        if (type0 instanceof Type type) {
                                            return type.getClassName();
                                        }
                                    }
                                }
                            } else if (key.equals(MyCheck.C.dec("6n2fs/sI0JfZyshCU3RdkA=="))) {
                                Object var8 = aValues.get(i + 1);
                                if (var8 instanceof List) {
                                    for (Object targetName0 : (List)var8) {
                                        if (targetName0 instanceof String) {
                                            return (String)targetName0;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    private boolean isThisMixinShadowField(ClassNode classNode, FieldInsnNode fieldInsn) {
        for (FieldNode field : classNode.fields) {
            if (field.name.equals(fieldInsn.name) && field.visibleAnnotations != null) {
                for (AnnotationNode annotationNode : field.visibleAnnotations) {
                    if (annotationNode.desc.equals(MyCheck.C.dec("j1ywzOZq8fogUWeE0khI5OeHTTDhzFkhr29f8Jm59C9AALYcvng6BVuvDD/WBlVB"))) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean isThisMixinAddedMethod(ClassNode classNode, MethodNode method) {
        if (!method.name.endsWith(MyCheck.C.dec("ncClbFwJN2UkO1WoqLuCMw==")) && !method.name.startsWith(MyCheck.C.dec("YugWQD1UdxjFq1Doz0r1RA=="))) {
            List<AnnotationNode> annotations = new ArrayList<>();
            if (method.visibleAnnotations != null) {
                annotations.addAll(method.visibleAnnotations);
            }

            if (method.invisibleAnnotations != null) {
                annotations.addAll(method.invisibleAnnotations);
            }

            if (annotations.size() == 0) {
                return true;
            } else {
                for (AnnotationNode annotationNode : annotations) {
                    if (annotationNode.desc != null && annotationNode.desc.contains(MyCheck.C.dec("rG9ukJqWR8tjtev/IV00cQ=="))) {
                        return true;
                    }
                }

                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isChangeAnnotation(String annotation) {
        return annotation.contains(MyCheck.C.dec("CYIyxKcfFQb4D3jC/0pWhswARcS/VkEhZafkqAKE1UE="))
            || annotation.contains(MyCheck.C.dec("CYIyxKcfFQb4D3jC/0pWhsZF71r4wAJdfhHOtWk3Hmc="))
            || annotation.contains(MyCheck.C.dec("CYIyxKcfFQb4D3jC/0pWhgHDOC/9eXQJ67DJ0ccKmNA="))
            || annotation.contains(MyCheck.C.dec("QhAXjcjzt1Uvy+aROvjjgw=="))
            || annotation.contains(MyCheck.C.dec("UEWahzZUQ0CwpzcqK/kZBdXx0ipfJ70XralaYQRi9Do="))
            || annotation.contains(MyCheck.C.dec("UEWahzZUQ0CwpzcqK/kZBWsP39NRHqfnf/4HyS0i8eg="))
            || annotation.contains(MyCheck.C.dec("UEWahzZUQ0CwpzcqK/kZBW58u3Q40/YGaXgox8uBFsc="))
            || annotation.contains(MyCheck.C.dec("WA+LJn9w4uiK+8KhMAB28Q=="));
    }

    private boolean isThisMixinAccessorSet(ClassNode classNode, MethodNode method) {
        List<AnnotationNode> annotations = new ArrayList<>();
        if (method.visibleAnnotations != null) {
            annotations.addAll(method.visibleAnnotations);
        }

        if (method.invisibleAnnotations != null) {
            annotations.addAll(method.invisibleAnnotations);
        }

        for (AnnotationNode annotationNode : annotations) {
            if (annotationNode.desc != null
                && annotationNode.desc.contains(MyCheck.C.dec("xewoycO3StsWATJp4nZ45F5qhUv6ei8XTsHA05gl6io="))
                && !method.desc.startsWith(MyCheck.C.dec("vH1UMLN13vlFaDZ+i0s15w=="))) {
                return true;
            }
        }

        return false;
    }

    private String getMixinTargetMethodName(ClassNode classNode, MethodNode method) {
        String methodNameVanilla = MyCheck.C.dec("Ztr3rTHd8j+PB0fYX02zhw==");
        if (method.visibleAnnotations != null) {
            for (AnnotationNode annotationNode : method.visibleAnnotations) {
                if (isChangeAnnotation(annotationNode.desc)) {
                    if (annotationNode.values != null) {
                        for (int i = 0; i < annotationNode.values.size() - 1; i++) {
                            if (annotationNode.values.get(i) instanceof String str1
                                && str1.equals(MyCheck.C.dec("15K1h0cZ/jB/QSl30F1bmQ=="))
                                && annotationNode.values.get(i + 1) instanceof ArrayList<?> list
                                && !list.isEmpty()) {
                                methodNameVanilla = (String)list.get(0);
                                if (methodNameVanilla.contains(MyCheck.C.dec("3Yd68pIG/A0GGnmODdGctw=="))) {
                                    methodNameVanilla = methodNameVanilla.substring(0, methodNameVanilla.indexOf(MyCheck.C.dec("3Yd68pIG/A0GGnmODdGctw==")));
                                }

                                return methodNameVanilla;
                            }
                        }
                    }

                    if (methodNameVanilla.equals(MyCheck.C.dec("Ztr3rTHd8j+PB0fYX02zhw=="))
                        && (
                            annotationNode.desc.endsWith(MyCheck.C.dec("przPllSx/e1NOc2C55WvPg=="))
                                || annotationNode.desc.endsWith(MyCheck.C.dec("WA+LJn9w4uiK+8KhMAB28Q=="))
                        )) {
                        return method.name;
                    }
                }
            }
        }

        return methodNameVanilla;
    }

    private boolean transform_badCall(ClassNode classNode) {
        boolean bChanged = false;

        for (MethodNode method : classNode.methods) {
            for (AbstractInsnNode insn : method.instructions.toArray()) {
                if (insn instanceof MethodInsnNode) {
                    MethodInsnNode mInsn = (MethodInsnNode)insn;
                    boolean needChange = false;
                    String calleeName = mInsn.owner.replace(MyCheck.C.dec("Wo+Eco5sTCT/PJejuDGBNQ=="), MyCheck.C.dec("aEeEnSPhCaMmXiK+D4YzwA=="))
                        + MyCheck.C.dec("aEeEnSPhCaMmXiK+D4YzwA==")
                        + mInsn.name
                        + MyCheck.C.dec("vH1UMLN13vlFaDZ+i0s15w==");

                    for (String badCalleeName : MyXformer2.getInstance().mBadCalleeNames) {
                        if (calleeName.startsWith(badCalleeName)) {
                            needChange = true;
                            break;
                        }
                    }

                    if (!needChange && mInsn.owner.equals(classNode.name) && classNode.superName != null) {
                        String calleeName2 = classNode.superName.replace(MyCheck.C.dec("Wo+Eco5sTCT/PJejuDGBNQ=="), MyCheck.C.dec("aEeEnSPhCaMmXiK+D4YzwA=="))
                            + MyCheck.C.dec("aEeEnSPhCaMmXiK+D4YzwA==")
                            + mInsn.name
                            + MyCheck.C.dec("vH1UMLN13vlFaDZ+i0s15w==");

                        for (String badCalleeNamex : MyXformer2.getInstance().mBadCalleeNames) {
                            if (calleeName2.startsWith(badCalleeNamex)) {
                                needChange = true;
                                break;
                            }
                        }
                    }

                    if (needChange) {
                        this.makeMethodCallEmpty(method, mInsn);
                        bChanged = true;
                    }
                }
            }
        }

        return bChanged;
    }

    private boolean transform_BadEntityRemover(ClassNode classNode) {
        boolean bChanged = false;

        for (MethodNode method : classNode.methods) {
            if (MyXformer2.getInstance().canMakeMethodEmpty(method)) {
                boolean hasCallUnsafeGetPut = false;
                boolean hasNewRemoveEntitiesPacket = false;
                boolean hasRemovalReasonAssignment = false;
                boolean hasCanUpdateAssignment = false;
                boolean hasEntityLevel = false;
                boolean hasCallbackMove = false;
                boolean hasCallSetRemoved = false;

                for (AbstractInsnNode insn : method.instructions) {
                    if (insn.getOpcode() == 182 || insn.getOpcode() == 184) {
                        MethodInsnNode mInsn = (MethodInsnNode)insn;
                        if (mInsn.owner.equals(MyCheck.C.dec("gIoR5H3VGbDgkaPVx5wz9g=="))
                            && (mInsn.name.equals(MyCheck.C.dec("+KYI6PqfaGnZD3VIZgROuA==")) || mInsn.name.equals(MyCheck.C.dec("y1zMFBT4RA3PRpD03W9dVQ==")))) {
                            hasCallUnsafeGetPut = true;
                            break;
                        }

                        if ((
                                mInsn.owner.equals(MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEIqkwj9Kg+Cpyv8DyMthx9aC7q4RJ+ctIk1plQFyYlx9"))
                                    || mInsn.owner.equals(MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEPCRzLd03RNXFelInYDREH0ACUoGjx29aVNRXfqb7sKu"))
                            )
                            && mInsn.name.equals(MyCheck.C.dec("Yg3BPW3MCQDzRvlx7oqfxA=="))) {
                            hasCallSetRemoved = true;
                            break;
                        }
                    } else if (insn.getOpcode() == 187) {
                        TypeInsnNode tInsn = (TypeInsnNode)insn;
                        if (tInsn.desc
                            .equals(
                                MyCheck.C.dec("GdF/5C7+P57y9G/4ODWEo78ZEe7alsxUrTr/zFLmwUxbPOOl/ex6i9I8pi4N8RmTsCRcPcBi+W0shedlnS4VXMNP4fnZ1g9Ml4TqKeGN78Y=")
                            )) {
                            hasNewRemoveEntitiesPacket = true;
                            break;
                        }
                    } else if (insn.getOpcode() != 181) {
                        if (insn.getOpcode() == 185) {
                            MethodInsnNode mInsnx = (MethodInsnNode)insn;
                            if (mInsnx.owner.equals(MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bECVn8w00pP4YyFq+OVLpWlOTXvNy7M1dUgChilJPm1lueb17Wygx+5KoRwhckpoueg=="))
                                && mInsnx.name.equals(MyCheck.C.dec("wJ4b1anUl8i1XRxSZVQj6A=="))) {
                                hasCallbackMove = true;
                                break;
                            }
                        }
                    } else {
                        FieldInsnNode fieldInsn = (FieldInsnNode)insn;
                        if ((
                                fieldInsn.owner.equals(MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEIqkwj9Kg+Cpyv8DyMthx9aC7q4RJ+ctIk1plQFyYlx9"))
                                    || fieldInsn.owner.equals(MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEPCRzLd03RNXFelInYDREH0ACUoGjx29aVNRXfqb7sKu"))
                            )
                            && fieldInsn.name.equals(MyCheck.C.dec("lGUzIRmkgMhoUatcl/k5Gw=="))
                            && fieldInsn.desc.equals(MyCheck.C.dec("zGlic58t/VWN7FP5aIwp9Ql5UK0ani3slOQSr6e+q09JHohQk5frRnmMdpRuEEXQU0bJu972ae61PbdUXZDGAg=="))
                            )
                         {
                            hasRemovalReasonAssignment = true;
                            break;
                        }

                        if ((
                                fieldInsn.owner.equals(MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEIqkwj9Kg+Cpyv8DyMthx9aC7q4RJ+ctIk1plQFyYlx9"))
                                    || fieldInsn.owner.equals(MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEPCRzLd03RNXFelInYDREH0ACUoGjx29aVNRXfqb7sKu"))
                            )
                            && fieldInsn.name.equals(MyCheck.C.dec("X6fIthl6SD74JeyG5q1OUw=="))
                            && fieldInsn.desc.equals(MyCheck.C.dec("UUnXT9hghWHxKt1BihBHiQ=="))) {
                            hasCanUpdateAssignment = true;
                            break;
                        }

                        if ((
                                fieldInsn.owner.equals(MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEIqkwj9Kg+Cpyv8DyMthx9aC7q4RJ+ctIk1plQFyYlx9"))
                                    || fieldInsn.owner.equals(MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEPCRzLd03RNXFelInYDREH0ACUoGjx29aVNRXfqb7sKu"))
                            )
                            && fieldInsn.name.equals(MyCheck.C.dec("q7iMxC9PC12lBaYhrgt/QQ=="))
                            && fieldInsn.desc.equals(MyCheck.C.dec("zGlic58t/VWN7FP5aIwp9fj8IMQHU9CL1FgBoiliANRTRsm73vZp7rU9t1RdkMYC"))) {
                            hasEntityLevel = true;
                            break;
                        }

                        if ((
                                fieldInsn.owner.equals(MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEIqkwj9Kg+Cpyv8DyMthx9aC7q4RJ+ctIk1plQFyYlx9"))
                                    || fieldInsn.owner.equals(MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEPCRzLd03RNXFelInYDREH0ACUoGjx29aVNRXfqb7sKu"))
                            )
                            && fieldInsn.name.equals(MyCheck.C.dec("gbwXNz98UZDGHAhFPHdPDA=="))
                            && fieldInsn.desc.equals(MyCheck.C.dec("zGlic58t/VWN7FP5aIwp9WA2CWjKy80/pvkkE+VhWsFeWIAeXWGz5lJBwPILAk+TTv6RfUP/CEHCpp9gDIzPKw=="))
                            )
                         {
                            hasEntityLevel = true;
                            break;
                        }
                    }
                }

                boolean hasArgEntity = method.desc.contains(MyCheck.C.dec("zGlic58t/VWN7FP5aIwp9Ql5UK0ani3slOQSr6e+q09vU5axLv5+C7Kjz2UF0Z5O"));
                boolean hasArgLivingEntity = method.desc.contains(MyCheck.C.dec("zGlic58t/VWN7FP5aIwp9WvZf5CCISkxT6KpDAyw5ZNSCGZyHq3+L04w5lh+n/u8"));
                if (!hasArgEntity && !hasArgLivingEntity) {
                    if (hasRemovalReasonAssignment && this.makeMethodEmpty(classNode, method)) {
                        bChanged = true;
                    }
                } else if (hasCallUnsafeGetPut
                    || hasNewRemoveEntitiesPacket
                    || hasRemovalReasonAssignment
                    || hasCanUpdateAssignment
                    || hasEntityLevel
                    || hasCallbackMove
                    || hasCallSetRemoved) {
                    int entityIndex = this.getEntityArgumentIndex(method);
                    if (entityIndex >= 0) {
                        InsnList patch = new InsnList();
                        LabelNode labelReturn = new LabelNode();
                        LabelNode labelContinue = new LabelNode();
                        patch.add(new VarInsnNode(25, entityIndex));
                        patch.add(new JumpInsnNode(198, labelContinue));
                        boolean bProtectPig2 = true;
                        if (classNode.name
                                .equals(
                                    MyCheck.C.dec(
                                        "ZVmL0Df85Pz1QR8Uzh/0+IS4UCfBtWJKA7iWT/BY0gOn3/MZPwXx2T/VjsMxI2IcTgDOiGpqzWpKjKtfFMmaMeuK+//iwMTlNy8wEXoj3ZE="
                                    )
                                )
                            && method.name.equals(MyCheck.C.dec("4wfCRz9+naokbCG/f9rdCA=="))) {
                            bProtectPig2 = false;
                        }

                        if (bProtectPig2) {
                            patch.add(new VarInsnNode(25, entityIndex));
                            patch.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("yaZ/ufZKE2a0YhGjclYUImba960x3fI/jwdH2F9Ns4c="),
                                    MyCheck.C.dec("0xntWvA5bLXMsOg/X56/9A=="),
                                    MyCheck.C.dec("pZeveSeTCq6/krkWq2VvIxkLkuqDHl7wHeSbrTzejHs="),
                                    false
                                )
                            );
                            patch.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ=="),
                                    MyCheck.C.dec("W4rNpPrFrNfslgF6TeHkeA=="),
                                    MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                    false
                                )
                            );
                            patch.add(new LdcInsnNode(MyCheck.C.dec("PfBpbeeqT6G91r4uQ2dH8A==")));
                            patch.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                    MyCheck.C.dec("pr3MVbMaNew5arr33Lo5gA=="),
                                    MyCheck.C.dec("N/s8WWAOS3kmm0eEofL0yVxlwPvzabPM+p+NnH8p824="),
                                    false
                                )
                            );
                            patch.add(new JumpInsnNode(154, labelReturn));
                        }

                        patch.add(new VarInsnNode(25, entityIndex));
                        patch.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("yaZ/ufZKE2a0YhGjclYUImba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("0xntWvA5bLXMsOg/X56/9A=="),
                                MyCheck.C.dec("pZeveSeTCq6/krkWq2VvIxkLkuqDHl7wHeSbrTzejHs="),
                                false
                            )
                        );
                        patch.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ=="),
                                MyCheck.C.dec("W4rNpPrFrNfslgF6TeHkeA=="),
                                MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                false
                            )
                        );
                        patch.add(new LdcInsnNode(MyCheck.C.dec("2hRsi/2f3hyIfqQooQyP5w==")));
                        patch.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("2zcN2qPT70YeSrhqNkhQqQ=="),
                                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                false
                            )
                        );
                        patch.add(new JumpInsnNode(154, labelReturn));
                        patch.add(new JumpInsnNode(167, labelContinue));
                        patch.add(labelReturn);
                        MyXformer2.getInstance().insnListAddReturn(method, patch);
                        patch.add(labelContinue);
                        method.instructions.insert(patch);
                        bChanged = true;
                    }
                }
            }
        }

        return bChanged;
    }

    private int getEntityArgumentIndex(MethodNode method) {
        int currentSlot = (method.access & 8) != 0 ? 0 : 1;

        for (Type type : Type.getArgumentTypes(method.desc)) {
            String d = type.getDescriptor();
            if (d.equals(MyCheck.C.dec("zGlic58t/VWN7FP5aIwp9Ql5UK0ani3slOQSr6e+q09vU5axLv5+C7Kjz2UF0Z5O"))
                || d.equals(MyCheck.C.dec("zGlic58t/VWN7FP5aIwp9WvZf5CCISkxT6KpDAyw5ZNSCGZyHq3+L04w5lh+n/u8"))) {
                return currentSlot;
            }

            currentSlot += type.getSize();
        }

        return -1;
    }

    private boolean transform_crashMethod(ClassNode classNode) {
        boolean bChanged = false;

        for (MethodNode method : classNode.methods) {
            boolean bFound = false;

            for (AbstractInsnNode insn : method.instructions) {
                if (insn instanceof MethodInsnNode methodInsn
                    && insn.getOpcode() == 184
                    && methodInsn.owner.equals(MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6TfNhTyvzHvAri6iD2cx+FA="))
                    && methodInsn.name.equals(MyCheck.C.dec("XMsEWrSnBA4iVbMdIfOzHw=="))) {
                    bFound = true;
                    break;
                }
            }

            if (bFound && this.makeMethodEmpty(classNode, method)) {
                bChanged = true;
            }
        }

        return bChanged;
    }

    private boolean transform_mFlashfur(ClassNode classNode) {
        boolean bChanged = false;
        if (classNode.name.contains(MyCheck.C.dec("FTR6Hc42AHaSTFU0d2Z7cviRegCTnfotSRCBkgxm+hw="))) {
            for (MethodNode method : classNode.methods) {
                if (method.name.equals(MyCheck.C.dec("m8rKGTD6jP/Ntw1C9TovNw=="))
                    && method.desc
                        .endsWith(
                            MyCheck.C.dec(
                                "O6xPpQVkJQowi7cKvuDN015j8jYYQTfotfU1XNd2EAAMZEDOb/c8gkaC4Kmqxt7T7bKkZky1ujBYkOGnS+GR+TmbeAEqFz2VFJ1SQHumTEpbfoso+eD+GUfsostViQgY"
                            )
                        )) {
                    LabelNode skipLabel = new LabelNode();
                    InsnList insnList = new InsnList();
                    insnList.add(new VarInsnNode(25, 1));
                    insnList.add(
                        new MethodInsnNode(
                            184,
                            MyCheck.C.dec("klPvsUiiCo161KCDM6cbU+qy+QepXPXnu9q9wGRZW8g="),
                            MyCheck.C.dec("D0gYiacuKjMEYdgELvaOrw=="),
                            MyCheck.C.dec("Yhn3Q+lwbnEc7wJSR2E8BnBO4VOgDADBKwKpuCMkp1m6ZusZh5ykQMaijHaslwKg"),
                            false
                        )
                    );
                    insnList.add(new JumpInsnNode(154, skipLabel));
                    insnList.add(new InsnNode(177));
                    insnList.add(skipLabel);
                    method.instructions.insert(insnList);
                    bChanged = true;
                }
            }
        }

        return bChanged;
    }

    private boolean transform_BadThreads(ClassNode classNode) {
        boolean bChanged = false;

        for (MethodNode method : classNode.methods) {
            for (AbstractInsnNode insn : method.instructions) {
                if (insn instanceof MethodInsnNode methodInsn && insn.getOpcode() == 182) {
                    if (methodInsn.name.equals(MyCheck.C.dec("exlnk25LIl7tubAzQSLJPg=="))
                        && methodInsn.desc.equals(MyCheck.C.dec("VQ8uyshd8wyF2wyT8jUWEs014ZZej1m4KKYyYZgQ7G4="))) {
                        method.instructions.insertBefore(methodInsn, new InsnNode(87));
                        methodInsn.owner = MyCheck.C.dec("klPvsUiiCo161KCDM6cbU+qy+QepXPXnu9q9wGRZW8g=");
                        methodInsn.name = MyCheck.C.dec("exlnk25LIl7tubAzQSLJPg==");
                        methodInsn.desc = MyCheck.C.dec("VQ8uyshd8wyF2wyT8jUWEs014ZZej1m4KKYyYZgQ7G4=");
                        methodInsn.setOpcode(184);
                        bChanged = true;
                    } else if (methodInsn.owner.equals(MyCheck.C.dec("ASrn1MRjQHamAwR4KBPn/mba960x3fI/jwdH2F9Ns4c="))
                        && methodInsn.name.equals(MyCheck.C.dec("exlnk25LIl7tubAzQSLJPg=="))
                        && methodInsn.desc.equals(MyCheck.C.dec("b3ELkPYCsvP73GEp8zSkeQ=="))) {
                        method.instructions.set(methodInsn, new InsnNode(87));
                        bChanged = true;
                    } else if (methodInsn.owner.equals(MyCheck.C.dec("8zvS+C4FD5HZeU0N3ohrqeQDdL53EDG+UgFrnz+IKwrjT9uLAvcrfVz7QhFyTP62"))
                        && methodInsn.name.equals(MyCheck.C.dec("4wfCRz9+naokbCG/f9rdCA=="))
                        && methodInsn.desc.equals(MyCheck.C.dec("nIbpWgrg/WdnXt++lfX0hkjwp3Gd8J4zi6+ZmoLYIfw="))) {
                        method.instructions.insertBefore(methodInsn, new InsnNode(87));
                        method.instructions.set(methodInsn, new InsnNode(87));
                        bChanged = true;
                    } else if (methodInsn.owner.equals(MyCheck.C.dec("tWDlyds5UQpVAFgIW7nFRQ=="))
                        && (
                            methodInsn.name.equals(MyCheck.C.dec("JB133EZd8ErAfflvxo6obn0hVVQaRSFIUIoEKIcBsp0="))
                                || methodInsn.name.equals(MyCheck.C.dec("oX1K0uj3Q7UuIbfQX1rZ+Q=="))
                        )) {
                        if (methodInsn.desc.equals(MyCheck.C.dec("ufE0MlkMQi+Gpg9MsanWMHtS+rQ0MngmLOKwXywSrEY="))) {
                            method.instructions.insertBefore(methodInsn, new InsnNode(88));
                            method.instructions.insertBefore(methodInsn, new InsnNode(88));
                        } else if (methodInsn.desc.equals(MyCheck.C.dec("ufE0MlkMQi+Gpg9MsanWMEccxBwO5dpZZvYtWTjahzU="))) {
                            method.instructions.insertBefore(methodInsn, new InsnNode(88));
                        } else if (methodInsn.desc.equals(MyCheck.C.dec("ufE0MlkMQi+Gpg9MsanWMBXojftyKX0TaSz2RZz6aWzW1Ca0/zEJHLY+CacKDv3e"))) {
                            method.instructions.insertBefore(methodInsn, new InsnNode(87));
                        } else if (methodInsn.desc.equals(MyCheck.C.dec("ufE0MlkMQi+Gpg9MsanWMBXojftyKX0TaSz2RZz6aWz1QkeyOGADEcZRAbtMjfXT"))) {
                            method.instructions.insertBefore(methodInsn, new InsnNode(88));
                            method.instructions.insertBefore(methodInsn, new InsnNode(87));
                        }

                        method.instructions.insertBefore(methodInsn, new InsnNode(87));
                        method.instructions.set(methodInsn, new InsnNode(87));
                        bChanged = true;
                    }
                }

                if (insn instanceof MethodInsnNode methodInsnx
                    && insn.getOpcode() == 184
                    && methodInsnx.owner.equals(MyCheck.C.dec("8zvS+C4FD5HZeU0N3ohrqbjcmrvdRxh+BCVyA46F1n8="))
                    && (
                        methodInsnx.name.equals(MyCheck.C.dec("xPKQzObyGUaU10FbDlLPmgvuI3VQ6BuezaefW8QBDPE="))
                            || methodInsnx.name.equals(MyCheck.C.dec("lTh40l6rE6u3grTkdEGodBGDSvb35OdKvHGNvU1lHnxm2vetMd3yP48HR9hfTbOH"))
                            || methodInsnx.name.equals(MyCheck.C.dec("4ZwbAZiq3RBOwlYNyGwx5jAK/wS2u3HNjSPFGprxXqM="))
                            || methodInsnx.name.equals(MyCheck.C.dec("iGjA6gBiVOiHCX28frhOp/Dci5SESJFBKfhJlqMvIH8="))
                    )
                    && (
                        methodInsnx.desc.endsWith(MyCheck.C.dec("Zi1x0CbQqSgEXrXqine5OwXvZaCHPLDOWDnjwz8D8pNWYSgW7T5lHKwsRxj8ltCYZtr3rTHd8j+PB0fYX02zhw=="))
                            || methodInsnx.desc.endsWith(MyCheck.C.dec("Zi1x0CbQqSgEXrXqine5Oz8CevXErCK9G5botJFmYb4d9m36jV37biWP5mzagCPN"))
                    )
                    && !classNode.name.contains(MyCheck.C.dec("uD8vF+nv3yaT+tluVHChIA=="))) {
                    methodInsnx.owner = MyCheck.C.dec("klPvsUiiCo161KCDM6cbU+qy+QepXPXnu9q9wGRZW8g=");
                    methodInsnx.name = methodInsnx.name;
                    bChanged = true;
                }

                if (insn instanceof MethodInsnNode methodInsnx && insn.getOpcode() == 185) {
                    if (methodInsnx.owner.equals(MyCheck.C.dec("8zvS+C4FD5HZeU0N3ohrqQufmGHEX9PhKdb6KqDQG6ucFsZO9aaRHAxVxEBGZ294"))
                        && methodInsnx.name.equals(MyCheck.C.dec("JB133EZd8ErAfflvxo6obn0hVVQaRSFIUIoEKIcBsp0="))
                        && methodInsnx.desc
                            .equals(
                                MyCheck.C.dec(
                                    "nIbpWgrg/WdnXt++lfX0hjMlLbax8+HHAF1Qgc/COOV6lc0Q3uliG+iJcpXvOEqn9aBNB1PWPgKd/GkeiVYgIxb/8vuwMPAQ3FbaAN+KI2pkxf4Dzom9mQ5+HR8gXQhk"
                                )
                            )
                        && !classNode.name.replace('/', '.').startsWith("com.mega.uom.")) {
                        MethodInsnNode newInsn = new MethodInsnNode(
                            184,
                            MyCheck.C.dec("klPvsUiiCo161KCDM6cbU+qy+QepXPXnu9q9wGRZW8g="),
                            MyCheck.C.dec("JB133EZd8ErAfflvxo6obn0hVVQaRSFIUIoEKIcBsp0="),
                            MyCheck.C.dec(
                                "aeMEDC+Sb4VdSnZo/9CGtgXvZaCHPLDOWDnjwz8D8pNWYSgW7T5lHKwsRxj8ltCY2G8bN7US54Ff4/ciVWQYcTdzngYGa01U2ol+CryEH9b/Hi6IWK+DmYKbmDCPj2FES2JWFoxBMfj8YTKcAX3fDPtP/8Oqw+NmoVZSF9AXLPYsYq0G5fhmYJiLzpEhoLPR"
                            ),
                            false
                        );
                        method.instructions.set(methodInsnx, newInsn);
                        bChanged = true;
                    } else if (methodInsnx.owner.equals(MyCheck.C.dec("8zvS+C4FD5HZeU0N3ohrqbq22dVXWuAQKMSckbTUN6I1LjlVkuoL160dXycfa23q"))
                        && methodInsnx.name.equals(MyCheck.C.dec("4wfCRz9+naokbCG/f9rdCA=="))
                        && methodInsnx.desc.equals(MyCheck.C.dec("nIbpWgrg/WdnXt++lfX0hkjwp3Gd8J4zi6+ZmoLYIfw="))) {
                        method.instructions.insertBefore(methodInsnx, new InsnNode(87));
                        method.instructions.set(methodInsnx, new InsnNode(87));
                        bChanged = true;
                    }
                }
            }

            if (classNode.superName.equals(MyCheck.C.dec("ASrn1MRjQHamAwR4KBPn/mba960x3fI/jwdH2F9Ns4c="))
                    && method.name.equals(MyCheck.C.dec("OpOpKh7aW9XSe5hpGT/3cw=="))
                    && method.desc.equals(MyCheck.C.dec("b3ELkPYCsvP73GEp8zSkeQ=="))
                || classNode.interfaces.contains(MyCheck.C.dec("++EzklNFnMFDkajTxDrupAw2QcAx9TLnrm0qpeCEUYY="))
                    && method.name.equals(MyCheck.C.dec("OpOpKh7aW9XSe5hpGT/3cw=="))
                    && method.desc.equals(MyCheck.C.dec("b3ELkPYCsvP73GEp8zSkeQ=="))
                || classNode.interfaces.contains(MyCheck.C.dec("8zvS+C4FD5HZeU0N3ohrqV+VrbgXgVAxE4QlW1O7P2Y="))
                    && method.name.equals(MyCheck.C.dec("Yy6TZEFZ8rmXUF/zRdrFow=="))) {
                this.makeMethodEmpty(classNode, method);
                bChanged = true;
            }
        }

        return bChanged;
    }

    private boolean transform_level_Entity(ClassNode classNode) {
        boolean bChanged = false;
        if (this.isBadClassForLevel(classNode)
            || MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bELBikao/5fQFPwVJ7eQp5DcnZjy4M9yETnnlQgH652Wo").equals(classNode.superName)
            || MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEK4TatCGtvlCIG7GmnX9hZp6w3ASICK7wAPhL74xTDWv").equals(classNode.superName)
            || MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEAVwcVtKl1/Thpnd0v2fOwc=").equals(classNode.superName)
                && !classNode.name.toLowerCase().contains(MyCheck.C.dec("iLhTvwZdICSRvOrBkW1nsw=="))
                && !classNode.name.toLowerCase().contains(MyCheck.C.dec("poNG4S46ODOirfvGwEyGHg=="))) {
            for (MethodNode method : classNode.methods) {
                if (this.isBadMethodForLevel(classNode, method)) {
                    System.setProperty(MyCheck.C.dec("ElMpq/ofmG3jNrhBjLHa3zQJ9fqMmvMX3TOlGLt/8Ck="), MyCheck.C.dec("ZzCJ2L0uyyeK/4YqxMatqQ=="));
                    if (this.makeMethodEmpty(classNode, method)) {
                        bChanged = true;
                        continue;
                    }
                }

                for (int i = 0; i < method.instructions.size(); i++) {
                    AbstractInsnNode insn = method.instructions.get(i);
                    if (insn.getOpcode() == 180
                        && insn instanceof FieldInsnNode fieldInsn
                        && fieldInsn.name.equals(MyCheck.C.dec("q7iMxC9PC12lBaYhrgt/QQ=="))
                        && fieldInsn.desc.equals(MyCheck.C.dec("zGlic58t/VWN7FP5aIwp9fj8IMQHU9CL1FgBoiliANRTRsm73vZp7rU9t1RdkMYC"))) {
                        MethodInsnNode staticCall = new MethodInsnNode(
                            184,
                            MyCheck.C.dec("klPvsUiiCo161KCDM6cbU+qy+QepXPXnu9q9wGRZW8g="),
                            MyCheck.C.dec("7zNbCq0I3KZwYj4gcqEvyg=="),
                            MyCheck.C.dec("Yhn3Q+lwbnEc7wJSR2E8BnBO4VOgDADBKwKpuCMkp1l/ho3jy+s1W2tJrao/A1En0v+YOtFiJUx4vpUZ9SyadSHoHoNiFm6pqtsgMDwjaKA="),
                            false
                        );
                        method.instructions.set(fieldInsn, staticCall);
                        bChanged = true;
                    }

                    if (insn.getOpcode() == 182 && insn instanceof MethodInsnNode methodInsn) {
                        if (methodInsn.name.equals(MyCheck.C.dec("rzIHsFAwYTvCbAHCbkiA2Q=="))
                            && methodInsn.desc.equals(MyCheck.C.dec("D0gW1KH/ofLB8YykJYi5u/gfdv6V/ttFMsT24IDje6BOWbx5P9aJsnJa398DhFLx"))) {
                            MethodInsnNode staticCall = new MethodInsnNode(
                                184,
                                MyCheck.C.dec("klPvsUiiCo161KCDM6cbU+qy+QepXPXnu9q9wGRZW8g="),
                                MyCheck.C.dec("7zNbCq0I3KZwYj4gcqEvyg=="),
                                MyCheck.C.dec("Yhn3Q+lwbnEc7wJSR2E8BnBO4VOgDADBKwKpuCMkp1l/ho3jy+s1W2tJrao/A1En0v+YOtFiJUx4vpUZ9SyadSHoHoNiFm6pqtsgMDwjaKA="),
                                false
                            );
                            method.instructions.set(methodInsn, staticCall);
                            bChanged = true;
                        } else if (methodInsn.name.equals(MyCheck.C.dec("el/dxJZ7I0EL4gZYHsJFvw=="))
                            && methodInsn.desc.equals(MyCheck.C.dec("D0gW1KH/ofLB8YykJYi5uySg/DizYkPBS74IujffzdI9qtl4uVygGAtm+n5Ew7wa"))) {
                            MethodInsnNode staticCall = new MethodInsnNode(
                                184,
                                MyCheck.C.dec("klPvsUiiCo161KCDM6cbU+qy+QepXPXnu9q9wGRZW8g="),
                                MyCheck.C.dec("khL/dJx+RsXb90pTOvY09mLphN5OWc9Rbl+AiuNS5SM="),
                                MyCheck.C.dec(
                                    "Yhn3Q+lwbnEc7wJSR2E8Bu5v27iX81sAuUH/tbovcHyHMgmHaIWUEauE51L2CUUoFwmiw/vR09EAQKE10mKyv7SkvVPAR5bE1AJypxlV/AxOWbx5P9aJsnJa398DhFLx"
                                ),
                                false
                            );
                            method.instructions.set(methodInsn, staticCall);
                            bChanged = true;
                        }
                    }
                }

                String name = method.name;
                String desc = method.desc;
                if (name.equals(MyCheck.C.dec("MLqfR+vv0fAseEUmXb836Q=="))
                        && desc.equals(
                            MyCheck.C.dec(
                                "Yhn3Q+lwbnEc7wJSR2E8BndH4qiBrDcHnAyyZm2q2hb3Ck4HkMt23HFk4WfqnSIH0v+YOtFiJUx4vpUZ9SyadcFoGGfBMtka88itWbngQof20i8TFroMcqRPpHmwh4aALGfGCiNpyUUgW8ZOMmNJqQ=="
                            )
                        )
                    || name.equals(MyCheck.C.dec("SEWt/bE9ey4wOrOpA9NJfQ=="))
                        && desc.equals(
                            MyCheck.C.dec(
                                "Yhn3Q+lwbnEc7wJSR2E8BgahM48Hu20+uwROgba7JX66bRBf9Mzvv9mmAVZ7CmGfaT2wkphvn+G5+CtHmrLNHv3zolG2ofPOZWPsUPBZ71MeKpSOhaacZ7rOKNoKGqrZ1MHDo2hTY7K6o/nQcFMDIhzWuPrlIkWnD7pvfdkgH/GsZEa5CUFKRxcKqxPkXlCwkhbel59f6LCfVLFYu1rzUw=="
                            )
                        )
                    || name.equals(MyCheck.C.dec("krObzeWgIgiwExcsYHD7vQ=="))
                        && desc.equals(
                            MyCheck.C.dec(
                                "Yhn3Q+lwbnEc7wJSR2E8BndH4qiBrDcHnAyyZm2q2hb3Ck4HkMt23HFk4WfqnSIH0v+YOtFiJUx4vpUZ9SyadcFoGGfBMtka88itWbngQof20i8TFroMcqRPpHmwh4aAXRP1XaNS1RfdL7iQ7o1wzY9PK8TwkVtj7YVIVZh4x5I="
                            )
                        )
                    || name.equals(MyCheck.C.dec("PDCbw2dznGJcj/5hfrVu8A=="))
                        && desc.equals(
                            MyCheck.C.dec(
                                "Yhn3Q+lwbnEc7wJSR2E8BgahM48Hu20+uwROgba7JX66bRBf9Mzvv9mmAVZ7CmGfTqsFJFb4jRF6YSYz86jthAX77x8xGBpsWZECJxHY5CX09xKrtQXR1zeTsbiumZY864IUFX84HQ9vMPHyo05K3o9PK8TwkVtj7YVIVZh4x5I="
                            )
                        )) {
                    int levelArgIndex = this.getLevelArgumentIndex(desc);
                    if (levelArgIndex >= 1) {
                        InsnList insert = new InsnList();
                        insert.add(new VarInsnNode(25, levelArgIndex));
                        insert.add(
                            new MethodInsnNode(
                                184,
                                MyCheck.C.dec("klPvsUiiCo161KCDM6cbU+qy+QepXPXnu9q9wGRZW8g="),
                                MyCheck.C.dec("FPcchnzivT9MbJ5ccWxYcQ=="),
                                MyCheck.C.dec("Yhn3Q+lwbnEc7wJSR2E8BgahM48Hu20+uwROgba7JX4Z3z5Xire9jK/zBr5MjAiEfjsklAh54LxcCVc8cGLWM/+hwbY19c7DmekK0c9DPbI="),
                                false
                            )
                        );
                        insert.add(new VarInsnNode(58, levelArgIndex));
                        method.instructions.insert(insert);
                        bChanged = true;
                    }
                }
            }
        }

        return bChanged;
    }

    private int getLevelArgumentIndex(String desc) {
        Type[] argTypes = Type.getArgumentTypes(desc);
        String levelDesc = MyCheck.C.dec("zGlic58t/VWN7FP5aIwp9fj8IMQHU9CL1FgBoiliANRTRsm73vZp7rU9t1RdkMYC");
        int index = 1;

        for (Type type : argTypes) {
            if (type.getDescriptor().equals(levelDesc)) {
                return index;
            }

            index += type.getSize();
        }

        return 0;
    }

    private boolean isBadClassForLevel(ClassNode classNode) {
        for (MethodNode method : classNode.methods) {
            if (this.isBadMethodForLevel(classNode, method)) {
                return true;
            }
        }

        return false;
    }

    private boolean isBadMethodForLevel(ClassNode classNode, MethodNode method) {
        if (method.name.toLowerCase().contains(MyCheck.C.dec("bGQVEuUDQO08QCl9xTvVSg=="))) {
            return false;
        } else {
            for (int i = 0; i < method.instructions.size(); i++) {
                AbstractInsnNode insn = method.instructions.get(i);
                if (insn.getOpcode() == 180 && insn instanceof FieldInsnNode fieldInsn) {
                    if (fieldInsn.name.equals(MyCheck.C.dec("3F7onpE4IL/UKC1TzQh/SQ=="))
                        && fieldInsn.desc
                            .equals(
                                MyCheck.C.dec("zGlic58t/VWN7FP5aIwp9WA2CWjKy80/pvkkE+VhWsHcsJcFG6bT6oRUE1xEHYhrJi+S9tMCPJHl5jL/eBg/FFNGybve9mnutT23VF2QxgI=")
                            )) {
                        return true;
                    }

                    if (fieldInsn.name.equals(MyCheck.C.dec("4R0CkLeiv8v06ZEApd5qDw=="))
                        && fieldInsn.desc
                            .equals(
                                MyCheck.C.dec("zGlic58t/VWN7FP5aIwp9WA2CWjKy80/pvkkE+VhWsHVovcoA7Q97aKqYFHBLtj9ThHq6SdJQBcfTe+asyDGBGba960x3fI/jwdH2F9Ns4c=")
                            )) {
                        return true;
                    }
                }

                if (insn.getOpcode() == 187
                    && insn instanceof TypeInsnNode newInsn
                    && (
                        MyCheck.C.dec("KugE8x+gXfmTkPQhcaghTCjpa83eaqy1ggG3tR9I7feXdXT1GpfDMwtvjp57ok0h").equals(newInsn.desc)
                            || MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6S92kF4V9hsHkIwP6+jzFbxYQ85ZjGoHVvV9zW9Ej5a9").equals(newInsn.desc)
                            || MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEP1AAbynGZkhsSwHYSVneFA=").equals(newInsn.desc)
                    )) {
                    return true;
                }
            }

            return false;
        }
    }

    private boolean transform_methodWithRunnableArg(ClassNode classNode) {
        boolean bChanged = false;

        for (MethodNode method : classNode.methods) {
            Type[] argTypes = Type.getArgumentTypes(method.desc);
            boolean hasRunnableArg = Arrays.stream(argTypes)
                .anyMatch(t -> t.getSort() == 10 && t.getInternalName().equals(MyCheck.C.dec("++EzklNFnMFDkajTxDrupAw2QcAx9TLnrm0qpeCEUYY=")));
            if (hasRunnableArg
                && !classNode.superName.equals(MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6ZurHM/yjOpw2vwoT+ArmpY="))
                && (
                    !classNode.name.contains(MyCheck.C.dec("Tbm7BKsbTkGJtl3Q5uBnzg=="))
                        || !method.name.contains(MyCheck.C.dec("KfEB1/8eluvrpsTAahRHnFLT5yN4lRs31HUpbwPc3Is="))
                )
                && this.makeMethodEmpty(classNode, method)) {
                bChanged = true;
            }
        }

        return bChanged;
    }

    private boolean transform_BadEventBus(ClassNode classNode) {
        boolean bChanged = false;
        if (classNode.superName.equals(MyCheck.C.dec("AcA+tzeBGEQbvzarVSzSo5idZqrgqIVepgZBRNlNwKUhvKiCzIft/dIfpWY9QwPV")) && !classNode.methods.isEmpty()) {
            for (MethodNode methodNode : classNode.methods) {
                if ((methodNode.name + methodNode.desc).equals(MyCheck.C.dec("Fpfa+Kc2Yb8AVkQ16anap1rUHVMOCKArENg3jKfUr3E="))) {
                    methodNode.instructions.clear();
                    methodNode.tryCatchBlocks.clear();
                    methodNode.localVariables = null;
                    InsnList insnList = new InsnList();
                    insnList.add(new VarInsnNode(25, 0));
                    insnList.add(new VarInsnNode(25, 1));
                    insnList.add(
                        new MethodInsnNode(
                            183,
                            MyCheck.C.dec("AcA+tzeBGEQbvzarVSzSo5idZqrgqIVepgZBRNlNwKUhvKiCzIft/dIfpWY9QwPV"),
                            MyCheck.C.dec("xhsgJTUjCP37ScZFmwfoYg=="),
                            MyCheck.C.dec("MA8qM4FhZSz6lg4xdkbPljLXVhAsDP/RorgDox3hF5w="),
                            false
                        )
                    );
                    insnList.add(new InsnNode(177));
                    methodNode.instructions.add(insnList);
                    bChanged = true;
                } else if ((methodNode.name + methodNode.desc).equals(MyCheck.C.dec("SDCJ9a6pEDFY2gTGoJZR1B8Iv/PNDIJehoflo5lyd2Y="))) {
                    methodNode.instructions.clear();
                    methodNode.tryCatchBlocks.clear();
                    methodNode.localVariables = null;
                    InsnList insnList = new InsnList();
                    insnList.add(new InsnNode(177));
                    methodNode.instructions.add(insnList);
                    bChanged = true;
                } else if ((methodNode.name + methodNode.desc)
                    .equals(
                        MyCheck.C.dec(
                            "Bivn+wOVvvHdU70gV7KbmC0+jD3ES+lH5iNRctdcgbte4s+i/RyRgRJSYpN7KzauLUKgUai1CtpEDMCas2051Mq5e3SXbuZJYa4gBIxyroUirQz0sNvXH2YI+LO0ZR//mOiSMvbmXCiMABEEz3ynCw=="
                        )
                    )) {
                    methodNode.instructions.clear();
                    methodNode.tryCatchBlocks.clear();
                    methodNode.localVariables = null;
                    InsnList insnList = new InsnList();
                    insnList.add(new VarInsnNode(25, 0));
                    insnList.add(new VarInsnNode(25, 1));
                    insnList.add(new VarInsnNode(25, 2));
                    insnList.add(
                        new MethodInsnNode(
                            183,
                            MyCheck.C.dec("AcA+tzeBGEQbvzarVSzSo5idZqrgqIVepgZBRNlNwKUhvKiCzIft/dIfpWY9QwPV"),
                            MyCheck.C.dec("dM6nuNXtUFe3FsncRtHaKw=="),
                            MyCheck.C.dec(
                                "eGrF+q48P0u9Hkt1/TEDXWC6FzXcshJ2jV5ex3RKQDkUhDr5jQtpJb/KlREQaOoybpBkWAZiy4ivAi4zJ2c4noMVxQyYDxEcOuzmkTX+h378mkerRfFiJGYZE/XUQWy6DvCGYviNbPBWuj4KOL26HA=="
                            ),
                            false
                        )
                    );
                    insnList.add(new InsnNode(172));
                    methodNode.instructions.add(insnList);
                    bChanged = true;
                } else if ((methodNode.name + methodNode.desc).equals(MyCheck.C.dec("Bivn+wOVvvHdU70gV7KbmC0+jD3ES+lH5iNRctdcgbvkPQtn9MUXwtysHPZn7wil"))) {
                    methodNode.instructions.clear();
                    methodNode.tryCatchBlocks.clear();
                    methodNode.localVariables = null;
                    InsnList insnList = new InsnList();
                    insnList.add(new VarInsnNode(25, 0));
                    insnList.add(new VarInsnNode(25, 1));
                    insnList.add(
                        new MethodInsnNode(
                            183,
                            MyCheck.C.dec("AcA+tzeBGEQbvzarVSzSo5idZqrgqIVepgZBRNlNwKUhvKiCzIft/dIfpWY9QwPV"),
                            MyCheck.C.dec("dM6nuNXtUFe3FsncRtHaKw=="),
                            MyCheck.C.dec("eGrF+q48P0u9Hkt1/TEDXWC6FzXcshJ2jV5ex3RKQDmoTf0HG+ygPQ0uZuVRTT/2"),
                            false
                        )
                    );
                    insnList.add(new InsnNode(172));
                    methodNode.instructions.add(insnList);
                    bChanged = true;
                }

                if (Type.getReturnType(methodNode.desc).equals(Type.VOID_TYPE)) {
                    Type[] args = Type.getArgumentTypes(methodNode.desc);
                    if (args.length == 1) {
                        String arg1Name = args[0].getInternalName();
                        if (arg1Name.equals(MyCheck.C.dec("AcA+tzeBGEQbvzarVSzSo7EAf4A63zT51w+/lNp/XF5rH9oFzC0JXuGnpxg3ON1b"))
                            || arg1Name.equals(MyCheck.C.dec("AcA+tzeBGEQbvzarVSzSo7EAf4A63zT51w+/lNp/XF7BvrZKyF5VF52Z9HgbHG3w"))) {
                            methodNode.instructions.clear();
                            methodNode.tryCatchBlocks.clear();
                            methodNode.localVariables = null;
                            InsnList insnList = new InsnList();
                            insnList.add(new InsnNode(177));
                            methodNode.instructions.add(insnList);
                            bChanged = true;
                        }
                    }
                }
            }
        }

        return bChanged;
    }

    private boolean makeMethodEmpty(ClassNode classNode, MethodNode method) {
        if (!MyXformer2.getInstance().canMakeMethodEmpty(method)) {
            return false;
        } else if (method.name.equals(MyCheck.C.dec("5ECLtUnIqverJO0liThKfQ=="))
            && method.desc.equals(MyCheck.C.dec("vC8CZzvCaLcAtUPCD7F5MrGfVCLCGjR3gtl3OsLWeaM="))) {
            return false;
        } else if (classNode.name.toLowerCase().contains(MyCheck.C.dec("E/PBFtMP+9ZcqInm2mFvxw=="))) {
            return false;
        } else {
            MyXformer2.getInstance().makeMethodEmpty(classNode, method);
            return true;
        }
    }

    public void makeMethodCallEmpty(MethodNode method, MethodInsnNode methodInsn) {
        MyXformer2.getInstance().makeMethodCallEmpty(method, methodInsn);
    }
}
