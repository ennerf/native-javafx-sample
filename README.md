# native-javafx-sample

Minimum sample for testing native compilation for JavaFX

Compile native-images via `mvn clean package -Pnative`. Note that GraalVM's native-image requires a local compiler being
setup.

---

### Windows 10

| GraalVM distribution                       | Native Executable  | Native Shared Library Launcher |
|--------------------------------------------|--------------------|--------------------------------|
| bellsoft-liberica-vm-full-openjdk21-23.1.0 | ok                 | ok                             |
| bellsoft-liberica-vm-full-openjdk22-24.0.2 | ok                 | ok                             |
| bellsoft-liberica-vm-full-openjdk23-24.1.2 | compiler error (1) | compiler error (1)             |

### macOS 15.3.1

| GraalVM distribution                       | Native Executable | Native Shared Library Launcher    |
|--------------------------------------------|-------------------|-----------------------------------|
| bellsoft-liberica-vm-full-openjdk17-23.0.7 | ok (x86?)         | stuck in init, no Stage shown (2) |
| bellsoft-liberica-vm-full-openjdk21-23.1.6 | ok (aarch64)      | stuck in init, no Stage shown (2) |
| bellsoft-liberica-vm-full-openjdk23-24.1.2 | ok (x86?)         | stuck in init, no Stage shown (2) |

### Ubuntu 20.04

| GraalVM distribution                       | Native Executable | Native Shared Library Launcher    |
|--------------------------------------------|-------------------|-----------------------------------|
| bellsoft-liberica-vm-full-openjdk17-23.0.7 | ok                | ok                                |
| bellsoft-liberica-vm-full-openjdk21-23.1.6 | ok                | ok                                |
| bellsoft-liberica-vm-full-openjdk23-24.1.2 | mvn error (3)     | mvn error (3) |


---

# Errors

## Error 1: Windows compiler error

```powershell
Error: java.util.concurrent.ExecutionException: com.oracle.svm.util.ReflectionUtil$ReflectionUtilError: java.lang.NoSuchFieldException: disableD3D9Ex
Caused by: java.util.concurrent.ExecutionException: com.oracle.svm.util.ReflectionUtil$ReflectionUtilError: java.lang.NoSuchFieldException: disableD3D9Ex
        at java.base/java.util.concurrent.FutureTask.report(FutureTask.java:122)
        at java.base/java.util.concurrent.FutureTask.get(FutureTask.java:191)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.util.AnalysisFuture.ensureDone(AnalysisFuture.java:70)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisElement.lambda$execute$2(AnalysisElement.java:220)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.util.CompletionExecutor.executeCommand(CompletionExecutor.java:166)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.util.CompletionExecutor.lambda$executeService$0(CompletionExecutor.java:152)
        at java.base/java.util.concurrent.ForkJoinTask$RunnableExecuteAction.compute(ForkJoinTask.java:1726)
        at java.base/java.util.concurrent.ForkJoinTask$RunnableExecuteAction.compute(ForkJoinTask.java:1717)
        at java.base/java.util.concurrent.ForkJoinTask$InterruptibleTask.exec(ForkJoinTask.java:1641)
        at java.base/java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:507)
        at java.base/java.util.concurrent.ForkJoinPool$WorkQueue.topLevelExec(ForkJoinPool.java:1460)
        at java.base/java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:2036)
        at java.base/java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:189)
Caused by: com.oracle.svm.util.ReflectionUtil$ReflectionUtilError: java.lang.NoSuchFieldException: disableD3D9Ex
        at org.graalvm.nativeimage.base/com.oracle.svm.util.ReflectionUtil.lookupField(ReflectionUtil.java:205)
        at org.graalvm.nativeimage.base/com.oracle.svm.util.ReflectionUtil.lookupField(ReflectionUtil.java:177)
        at org.graalvm.nativeimage.builder/com.oracle.svm.core.jdk.JNIRegistrationUtil.fields(JNIRegistrationUtil.java:103)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.javafx.JavaFXJNI.registerPrismD3D(JavaFXJNI.java:492)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.javafx.JavaFXFeature.enablePrismD3D(JavaFXFeature.java:217)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.javafx.JavaFXFeature.enableJavaFX(JavaFXFeature.java:167)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisElement$ElementNotification.lambda$notifyCallback$0(AnalysisElement.java:147)
        at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:317)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.util.AnalysisFuture.ensureDone(AnalysisFuture.java:69)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisField.lambda$beforeFieldValueAccess$0(AnalysisField.java:543)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisType.forAllSuperTypes(AnalysisType.java:730)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisType.forAllSuperTypes(AnalysisType.java:713)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisType.forAllSuperTypes(AnalysisType.java:709)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisField.beforeFieldValueAccess(AnalysisField.java:537)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.ameta.FieldValueInterceptionSupport.computeAndCacheFieldValueInterceptor(FieldValueInterceptionSupport.java:154)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.ameta.FieldValueInterceptionSupport.lookupFieldValueInterceptor(FieldValueInterceptionSupport.java:143)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.ameta.FieldValueInterceptionSupport.tryIntrinsifyFieldLoad(FieldValueInterceptionSupport.java:264)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.phases.InlineBeforeAnalysisGraphDecoderImpl.handleLoadFieldNode(InlineBeforeAnalysisGraphDecoderImpl.java:99)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.phases.InlineBeforeAnalysisGraphDecoderImpl.doCanonicalizeFixedNode(InlineBeforeAnalysisGraphDecoderImpl.java:60)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.phases.InlineBeforeAnalysisGraphDecoder.canonicalizeFixedNode(InlineBeforeAnalysisGraphDecoder.java:194)
        at jdk.graal.compiler/jdk.graal.compiler.nodes.SimplifyingGraphDecoder.handleFixedNode(SimplifyingGraphDecoder.java:193)
        at jdk.graal.compiler/jdk.graal.compiler.nodes.GraphDecoder.processNextNode(GraphDecoder.java:933)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.phases.InlineBeforeAnalysisGraphDecoder.processNextNode(InlineBeforeAnalysisGraphDecoder.java:269)
        at jdk.graal.compiler/jdk.graal.compiler.nodes.GraphDecoder.decode(GraphDecoder.java:654)
        at jdk.graal.compiler/jdk.graal.compiler.replacements.PEGraphDecoder.decode(PEGraphDecoder.java:895)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.phases.InlineBeforeAnalysis.decodeGraph(InlineBeforeAnalysis.java:73)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.MethodTypeFlowBuilder.parse(MethodTypeFlowBuilder.java:200)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.MethodTypeFlowBuilder.apply(MethodTypeFlowBuilder.java:652)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.MethodTypeFlow.createFlowsGraph(MethodTypeFlow.java:167)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.MethodTypeFlow.ensureFlowsGraphCreated(MethodTypeFlow.java:152)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.MethodTypeFlow.getOrCreateMethodFlowsGraphInfo(MethodTypeFlow.java:110)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.typestate.DefaultSpecialInvokeTypeFlow.lambda$onObservedUpdate$0(DefaultSpecialInvokeTypeFlow.java:88)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.util.LightImmutableCollection.forEach(LightImmutableCollection.java:90)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.typestate.DefaultSpecialInvokeTypeFlow.onObservedUpdate(DefaultSpecialInvokeTypeFlow.java:87)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.TypeFlow.update(TypeFlow.java:628)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.PointsToAnalysis$1.run(PointsToAnalysis.java:575)
        ... 9 more
Caused by: java.lang.NoSuchFieldException: disableD3D9Ex
        at java.base/java.lang.Class.getDeclaredField(Class.java:2841)
        at org.graalvm.nativeimage.base/com.oracle.svm.util.ReflectionUtil.lookupField(ReflectionUtil.java:184)
        ... 44 more
Caused by: com.oracle.svm.util.ReflectionUtil$ReflectionUtilError: java.lang.NoSuchFieldException: disableD3D9Ex
        at org.graalvm.nativeimage.base/com.oracle.svm.util.ReflectionUtil.lookupField(ReflectionUtil.java:205)
        at org.graalvm.nativeimage.base/com.oracle.svm.util.ReflectionUtil.lookupField(ReflectionUtil.java:177)
        at org.graalvm.nativeimage.builder/com.oracle.svm.core.jdk.JNIRegistrationUtil.fields(JNIRegistrationUtil.java:103)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.javafx.JavaFXJNI.registerPrismD3D(JavaFXJNI.java:492)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.javafx.JavaFXFeature.enablePrismD3D(JavaFXFeature.java:217)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.javafx.JavaFXFeature.enableJavaFX(JavaFXFeature.java:167)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisElement$ElementNotification.lambda$notifyCallback$0(AnalysisElement.java:147)
        at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:317)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.util.AnalysisFuture.ensureDone(AnalysisFuture.java:69)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisField.lambda$beforeFieldValueAccess$0(AnalysisField.java:543)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisType.forAllSuperTypes(AnalysisType.java:730)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisType.forAllSuperTypes(AnalysisType.java:713)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisType.forAllSuperTypes(AnalysisType.java:709)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisField.beforeFieldValueAccess(AnalysisField.java:537)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.ameta.FieldValueInterceptionSupport.computeAndCacheFieldValueInterceptor(FieldValueInterceptionSupport.java:154)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.ameta.FieldValueInterceptionSupport.lookupFieldValueInterceptor(FieldValueInterceptionSupport.java:143)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.ameta.FieldValueInterceptionSupport.tryIntrinsifyFieldLoad(FieldValueInterceptionSupport.java:264)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.phases.InlineBeforeAnalysisGraphDecoderImpl.handleLoadFieldNode(InlineBeforeAnalysisGraphDecoderImpl.java:99)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.phases.InlineBeforeAnalysisGraphDecoderImpl.doCanonicalizeFixedNode(InlineBeforeAnalysisGraphDecoderImpl.java:60)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.phases.InlineBeforeAnalysisGraphDecoder.canonicalizeFixedNode(InlineBeforeAnalysisGraphDecoder.java:194)
        at jdk.graal.compiler/jdk.graal.compiler.nodes.SimplifyingGraphDecoder.handleFixedNode(SimplifyingGraphDecoder.java:193)
        at jdk.graal.compiler/jdk.graal.compiler.nodes.GraphDecoder.processNextNode(GraphDecoder.java:933)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.phases.InlineBeforeAnalysisGraphDecoder.processNextNode(InlineBeforeAnalysisGraphDecoder.java:269)
        at jdk.graal.compiler/jdk.graal.compiler.nodes.GraphDecoder.decode(GraphDecoder.java:654)
        at jdk.graal.compiler/jdk.graal.compiler.replacements.PEGraphDecoder.decode(PEGraphDecoder.java:895)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.phases.InlineBeforeAnalysis.decodeGraph(InlineBeforeAnalysis.java:73)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.MethodTypeFlowBuilder.parse(MethodTypeFlowBuilder.java:200)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.MethodTypeFlowBuilder.apply(MethodTypeFlowBuilder.java:652)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.MethodTypeFlow.createFlowsGraph(MethodTypeFlow.java:167)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.MethodTypeFlow.ensureFlowsGraphCreated(MethodTypeFlow.java:152)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.MethodTypeFlow.getOrCreateMethodFlowsGraphInfo(MethodTypeFlow.java:110)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.typestate.DefaultSpecialInvokeTypeFlow.lambda$onObservedUpdate$0(DefaultSpecialInvokeTypeFlow.java:88)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.util.LightImmutableCollection.forEach(LightImmutableCollection.java:90)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.typestate.DefaultSpecialInvokeTypeFlow.onObservedUpdate(DefaultSpecialInvokeTypeFlow.java:87)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.TypeFlow.update(TypeFlow.java:628)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.PointsToAnalysis$1.run(PointsToAnalysis.java:575)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.util.CompletionExecutor.executeCommand(CompletionExecutor.java:166)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.util.CompletionExecutor.lambda$executeService$0(CompletionExecutor.java:152)
        at java.base/java.util.concurrent.ForkJoinTask$RunnableExecuteAction.compute(ForkJoinTask.java:1726)
        at java.base/java.util.concurrent.ForkJoinTask$RunnableExecuteAction.compute(ForkJoinTask.java:1717)
        at java.base/java.util.concurrent.ForkJoinTask$InterruptibleTask.exec(ForkJoinTask.java:1641)
        at java.base/java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:507)
        at java.base/java.util.concurrent.ForkJoinPool$WorkQueue.topLevelExec(ForkJoinPool.java:1460)
        at java.base/java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:2036)
        at java.base/java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:189)
Caused by: java.lang.NoSuchFieldException: disableD3D9Ex
        at java.base/java.lang.Class.getDeclaredField(Class.java:2841)
        at org.graalvm.nativeimage.base/com.oracle.svm.util.ReflectionUtil.lookupField(ReflectionUtil.java:184)
        ... 44 more
Caused by: java.lang.NoSuchFieldException: disableD3D9Ex
        at java.base/java.lang.Class.getDeclaredField(Class.java:2841)
        at org.graalvm.nativeimage.base/com.oracle.svm.util.ReflectionUtil.lookupField(ReflectionUtil.java:184)
        at org.graalvm.nativeimage.base/com.oracle.svm.util.ReflectionUtil.lookupField(ReflectionUtil.java:177)
        at org.graalvm.nativeimage.builder/com.oracle.svm.core.jdk.JNIRegistrationUtil.fields(JNIRegistrationUtil.java:103)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.javafx.JavaFXJNI.registerPrismD3D(JavaFXJNI.java:492)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.javafx.JavaFXFeature.enablePrismD3D(JavaFXFeature.java:217)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.javafx.JavaFXFeature.enableJavaFX(JavaFXFeature.java:167)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisElement$ElementNotification.lambda$notifyCallback$0(AnalysisElement.java:147)
        at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:317)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.util.AnalysisFuture.ensureDone(AnalysisFuture.java:69)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisField.lambda$beforeFieldValueAccess$0(AnalysisField.java:543)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisType.forAllSuperTypes(AnalysisType.java:730)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisType.forAllSuperTypes(AnalysisType.java:713)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisType.forAllSuperTypes(AnalysisType.java:709)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisField.beforeFieldValueAccess(AnalysisField.java:537)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.ameta.FieldValueInterceptionSupport.computeAndCacheFieldValueInterceptor(FieldValueInterceptionSupport.java:154)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.ameta.FieldValueInterceptionSupport.lookupFieldValueInterceptor(FieldValueInterceptionSupport.java:143)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.ameta.FieldValueInterceptionSupport.tryIntrinsifyFieldLoad(FieldValueInterceptionSupport.java:264)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.phases.InlineBeforeAnalysisGraphDecoderImpl.handleLoadFieldNode(InlineBeforeAnalysisGraphDecoderImpl.java:99)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.phases.InlineBeforeAnalysisGraphDecoderImpl.doCanonicalizeFixedNode(InlineBeforeAnalysisGraphDecoderImpl.java:60)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.phases.InlineBeforeAnalysisGraphDecoder.canonicalizeFixedNode(InlineBeforeAnalysisGraphDecoder.java:194)
        at jdk.graal.compiler/jdk.graal.compiler.nodes.SimplifyingGraphDecoder.handleFixedNode(SimplifyingGraphDecoder.java:193)
        at jdk.graal.compiler/jdk.graal.compiler.nodes.GraphDecoder.processNextNode(GraphDecoder.java:933)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.phases.InlineBeforeAnalysisGraphDecoder.processNextNode(InlineBeforeAnalysisGraphDecoder.java:269)
        at jdk.graal.compiler/jdk.graal.compiler.nodes.GraphDecoder.decode(GraphDecoder.java:654)
        at jdk.graal.compiler/jdk.graal.compiler.replacements.PEGraphDecoder.decode(PEGraphDecoder.java:895)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.phases.InlineBeforeAnalysis.decodeGraph(InlineBeforeAnalysis.java:73)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.MethodTypeFlowBuilder.parse(MethodTypeFlowBuilder.java:200)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.MethodTypeFlowBuilder.apply(MethodTypeFlowBuilder.java:652)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.MethodTypeFlow.createFlowsGraph(MethodTypeFlow.java:167)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.MethodTypeFlow.ensureFlowsGraphCreated(MethodTypeFlow.java:152)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.MethodTypeFlow.getOrCreateMethodFlowsGraphInfo(MethodTypeFlow.java:110)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.typestate.DefaultSpecialInvokeTypeFlow.lambda$onObservedUpdate$0(DefaultSpecialInvokeTypeFlow.java:88)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.util.LightImmutableCollection.forEach(LightImmutableCollection.java:90)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.typestate.DefaultSpecialInvokeTypeFlow.onObservedUpdate(DefaultSpecialInvokeTypeFlow.java:87)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.TypeFlow.update(TypeFlow.java:628)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.PointsToAnalysis$1.run(PointsToAnalysis.java:575)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.util.CompletionExecutor.executeCommand(CompletionExecutor.java:166)
        at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.util.CompletionExecutor.lambda$executeService$0(CompletionExecutor.java:152)
        at java.base/java.util.concurrent.ForkJoinTask$RunnableExecuteAction.compute(ForkJoinTask.java:1726)
        at java.base/java.util.concurrent.ForkJoinTask$RunnableExecuteAction.compute(ForkJoinTask.java:1717)
        at java.base/java.util.concurrent.ForkJoinTask$InterruptibleTask.exec(ForkJoinTask.java:1641)
        at java.base/java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:507)
        at java.base/java.util.concurrent.ForkJoinPool$WorkQueue.topLevelExec(ForkJoinPool.java:1460)
        at java.base/java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:2036)
        at java.base/java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:189)
```

## Error 2: macOS verbose info

macOS executable verbose info:

```
class javafx.application.Application
class us.hebi.samples.gui.MinimalFxApp
WARNING: java.lang.UnsatisfiedLinkError: Invalid URL for class: resource://javafx.graphics/com/sun/glass/utils/NativeLibLoader.class
System.loadLibrary(prism_es2) succeeded
JavaFX: using com.sun.javafx.tk.quantum.QuantumToolkit
WARNING: java.lang.UnsatisfiedLinkError: Invalid URL for class: resource://javafx.graphics/com/sun/glass/utils/NativeLibLoader.class
System.loadLibrary(glass) succeeded
WARNING: java.lang.UnsatisfiedLinkError: Invalid URL for class: resource://javafx.graphics/com/sun/glass/utils/NativeLibLoader.class
System.loadLibrary(javafx_font) succeeded
2025-03-03 09:14:17.188 native-executable[15061:15428417] +[IMKClient subclass]: chose IMKClient_Modern
2025-03-03 09:14:17.189 native-executable[15061:15428417] +[IMKInputSession subclass]: chose IMKInputSession_Modern
```

macOS shared lib verbose info:

```
class javafx.application.Application
class us.hebi.samples.gui.MinimalFxApp
WARNING: java.lang.UnsatisfiedLinkError: Invalid URL for class: resource://javafx.graphics/com/sun/glass/utils/NativeLibLoader.class
System.loadLibrary(prism_es2) succeeded
JavaFX: using com.sun.javafx.tk.quantum.QuantumToolkit
WARNING: java.lang.UnsatisfiedLinkError: Invalid URL for class: resource://javafx.graphics/com/sun/glass/utils/NativeLibLoader.class
System.loadLibrary(glass) succeeded
```

## Error 3: mvn error

```
Error: could not open `toolchains/bellsoft-liberica-vm-full-openjdk23-24.1.2/lib/jvm.cfg'
```


