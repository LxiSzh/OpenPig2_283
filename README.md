# OpenPig2_283

**Pig2 Mod (1.20.1-2.8.3, Forge)** 的逆向还原源码工程。

由发行版 JAR `pig2mod-1.20.1-2.8.3.jar`(原作者 **Kakiku**)反编译还原,目标是得到一个
可构建、可读、行为等价的 ForgeGradle 工程,供学习研究使用。本仓库为社区还原项目,
与原作者无关;模组本体许可证为 **All Rights Reserved**,如需再分发编译产物请遵守
原作者权利。

## 还原流程

1. **SRG → MojMap 重映射**:发行版 JAR 的方法/字段是 SRG 名(`m_6075_` / `f_19853_`)。
   将 Mojang 官方映射(client.txt)与 Forge mcp_config 的 joined.tsrg 按
   `(obf类, obf成员名, obf描述符)` 组合成 SRG→官方 映射表(覆盖率 100%),
   再用 ForgeAutoRenamingTool(FART)重映射,继承库使用 "官方类名+SRG成员名" 命名空间的
   MC 合并 JAR。
2. **反编译**:Vineflower 1.10.1。
3. **Mixin 源码修复**:发行版中 Mixin 类的 `@Shadow` 成员与注入点字符串是 SRG 名
   (生产环境正常形态)。用组合映射表 + 原始 `mixins.pig2.refmap.json` 反查回 MojMap 名,
   使其可在 mojmap 开发环境中编译(构建时由 MixinGradle 重新生成 refmap)。
4. **有意保留的内容**:
   - `Pig2` / `MyLevelsMap` / `MyXformer` 等类中**字符串形式的 SRG 名**
     (如 `"m_7041_"`、`"net.minecraft.server.MinecraftServer.m_7041_"`)。
     这些是调用栈检查 / 反射用的字面量,ForgeGradle 不会重映射字符串常量,
     生产环境(运行时为 SRG 名)下必须保持 SRG 才能工作,与原版一致。
   - `assets/pig2mod/libs/` 下的 `MyNative.dll`、`pig2_agent.jar`、`pig2_lib0.jar`
     按原样保留为二进制资源(模组通过 Java agent / 原生库加载自身的一部分)。
   - `assets/pig2mod/json/settings.json` 实际是一个 PNG 文件、`json/temp1` 是一个
     SHA-256 哈希——按原样保留。

## 构建

需要 JDK 17(Gradle 8.1.1 + ForgeGradle 6,MixinGradle 0.7 + mixin 处理器)。

```bat
gradlew build
```

产物:`build/libs/pig2mod-1.20.1-1.20.1-2.8.3.jar`(已由 reobfJar 重映射回 SRG)。

首次构建会下载 Minecraft 1.20.1 与 Forge 47.3.0 并反编译,耗时数分钟。

## 验证结果

- 构建成功(`BUILD SUCCESSFUL`,仅余原代码自带的 `Thread.suspend/resume` 过时警告)。
- 产物 JAR 与原版 JAR **文件清单完全一致**(170 个条目逐一对应)。
- 构建时重新生成的 `mixins.pig2.refmap.json` 与原版 JAR 内的 refmap **44 个类的映射
  条目全部相同** —— Mixin 注入点还原与原始源码语义一致。
- 类字节码经 reobf 后方法/字段名(如 `Pig1.m_6469_`)与原版一致。

## 工程结构

```
src/main/java/kakiku/pig2mod/     # 模组源码(97 个类)
  ├─ Pig2Mod.java                  # @Mod 主类
  ├─ entity/                       # Pig1 / Pig2 实体、渲染器、模型
  ├─ item/                         # 刷怪蛋
  ├─ event/                        # 事件订阅
  ├─ map/                          # 自研高性能集合(替换 MC 实体追踪容器)
  ├─ mixin/                        # 40 个 Mixin(深度优化实体/区块管理)
  ├─ xform/                        # ITransformationService 字节码变换层
  └─ ...
src/main/resources/
  ├─ META-INF/mods.toml            # 原样
  ├─ META-INF/accesstransformer.cfg# 原样
  ├─ META-INF/services/...         # ITransformationService 注册(原样)
  ├─ mixins.pig2.json              # 原样(refmap 由构建生成)
  └─ assets/pig2mod/               # 语言文件、贴图、内嵌库(原样)
```

## 备注

- 反编译代码由工具生成,个别地方(变量名、泛型推断)可能与原始源码有出入,
  语义以字节码为准;构建通过即与原 JAR 行为等价。
- 该模组包含字节码变换服务与原生库,运行机制复杂,修改前请先阅读
  `xform/MyXformService`、`MyXformer2` 与 `MyNative`。
