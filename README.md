# SOLVED

The problem on macOS w/ shared libraries was that the Cocoa framework needs to be setup properly. The main event loop needs to run on the main thread before calling into any JavaFX code.

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

macOS shared lib verbose info:

```
class javafx.application.Application
class us.hebi.samples.gui.MinimalFxApp
Prism pipeline init order: es2 sw 
Using Double Precision Marlin Rasterizer
Using dirty region optimizations
Not using texture mask for primitives
Not forcing power of 2 sizes for textures
Using hardware CLAMP_TO_ZERO mode
Opting in for HiDPI pixel scaling
Prism pipeline name = com.sun.prism.es2.ES2Pipeline
Loading ES2 native library ... prism_es2
WARNING: java.lang.UnsatisfiedLinkError: Invalid URL for class: resource://javafx.graphics/com/sun/glass/utils/NativeLibLoader.class
System.loadLibrary(prism_es2) succeeded
	succeeded.
GLFactory using com.sun.prism.es2.MacGLFactory
(X) Got class = class com.sun.prism.es2.ES2Pipeline
Initialized prism pipeline: com.sun.prism.es2.ES2Pipeline
JavaFX: using com.sun.javafx.tk.quantum.QuantumToolkit
WARNING: java.lang.UnsatisfiedLinkError: Invalid URL for class: resource://javafx.graphics/com/sun/glass/utils/NativeLibLoader.class
System.loadLibrary(glass) succeeded
```

macOS executable verbose info:

```
class javafx.application.Application
class us.hebi.samples.gui.MinimalFxApp
Prism pipeline init order: es2 sw 
Using Double Precision Marlin Rasterizer
Using dirty region optimizations
Not using texture mask for primitives
Not forcing power of 2 sizes for textures
Using hardware CLAMP_TO_ZERO mode
Opting in for HiDPI pixel scaling
Prism pipeline name = com.sun.prism.es2.ES2Pipeline
Loading ES2 native library ... prism_es2
WARNING: java.lang.UnsatisfiedLinkError: Invalid URL for class: resource://javafx.graphics/com/sun/glass/utils/NativeLibLoader.class
System.loadLibrary(prism_es2) succeeded
	succeeded.
GLFactory using com.sun.prism.es2.MacGLFactory
(X) Got class = class com.sun.prism.es2.ES2Pipeline
Initialized prism pipeline: com.sun.prism.es2.ES2Pipeline
JavaFX: using com.sun.javafx.tk.quantum.QuantumToolkit
WARNING: java.lang.UnsatisfiedLinkError: Invalid URL for class: resource://javafx.graphics/com/sun/glass/utils/NativeLibLoader.class
System.loadLibrary(glass) succeeded
Maximum supported texture size: 16384
Maximum texture size clamped to 4096
Non power of two texture support = true
Maximum number of vertex attributes = 16
Maximum number of uniform vertex components = 4096
Maximum number of uniform fragment components = 4096
Maximum number of varying components = 124
Maximum number of texture units usable in a vertex shader = 16
Maximum number of texture units usable in a fragment shader = 16
Graphics Vendor: Apple
       Renderer: Apple M1 Pro
        Version: 2.1 Metal - 89.3
 vsync: true vpipe: true
QT.pauseTimer#(262865161722416): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865170078458): Pause Timer : DROP : Tapn
WARNING: java.lang.UnsatisfiedLinkError: Invalid URL for class: resource://javafx.graphics/com/sun/glass/utils/NativeLibLoader.class
System.loadLibrary(javafx_font) succeeded
QT.pauseTimer#(262865178387708): Pause Timer : DROP : Tapn
QT.postPulse@(262865186771666): TaPN
QT.postPulse#(262865195092333): DROP : TaPN
PC.removeDirtyScene:  scene: 1082975835 @ (-1,-1)
PC.removeDirtyScene:  scene: 1082975835 @ (-1,-1)
PC.removeDirtyScene:  scene: 1082975835 @ (-1,-1)
PC.removeDirtyScene:  scene: 1082975835 @ (-1,-1)
PC.addDirtyScene: 262865196670916 scene: 1082975835 @ (-1,-1)
QT.postPulse#(262865203431416): DROP : TaPN
QT.postPulse#(262865211755000): DROP : TaPN
QT.postPulse#(262865220088416): DROP : TaPN
QT.postPulse#(262865228430583): DROP : TaPN
QT.postPulse#(262865236753583): DROP : TaPN
QT.postPulse#(262865245118666): DROP : TaPN
QT.postPulse#(262865253412208): DROP : TaPN
PC.addDirtyScene: 262865254072000 scene: 1082975835 @ (-1,-1)
PC.addDirtyScene: 262865255764458 scene: 1082975835 @ (500,500)
PC.addDirtyScene: 262865260031958 scene: 1082975835 @ (500,500)
QT.postPulse#(262865261290916): DROP : TaPN
PC.addDirtyScene: 262865263056083 scene: 1082975835 @ (500,500)
QT.postPulse#(262865270150625): DROP : TaPN
PC.addDirtyScene: 262865270667875 scene: 1082975835 @ (500,500)
PC.addDirtyScene: 262865270690083 scene: 1082975835 @ (500,500)
PC.addDirtyScene: 262865270713708 scene: 1082975835 @ (500,500)
QT.endPulse: 262865270724583
ES2ResourceFactory: Prism - createStockShader: FillPgram_Color.frag
new alphas with length = 12288
ES2ResourceFactory: Prism - createStockShader: Texture_Color.frag
ES2ResourceFactory: Prism - createStockShader: Texture_LinearGradient_PAD.frag
ES2ResourceFactory: Prism - createStockShader: Solid_TextureRGB.frag
QT.pauseTimer#(262865278406375): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865286321833): Pause Timer : DROP : Tapn
QT.postPulse@(262865295141000): TaPN
2025-03-03 14:48:40.075 native-executable[23419:15790494] +[IMKClient subclass]: chose IMKClient_Modern
2025-03-03 14:48:40.075 native-executable[23419:15790494] +[IMKInputSession subclass]: chose IMKInputSession_Modern
QT.postPulse#(262865303421625): DROP : TaPN
PC.addDirtyScene: 262865311375666 scene: 1082975835 @ (500,500)
QT.endPulse: 262865311411333
QT.postPulse@(262865311751125): TaPN
QT.endPulse: 262865315862916
QT.pauseTimer#(262865320092958): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865328460416): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865336758291): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865344170625): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865352502791): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865361841208): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865370077833): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865378140208): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865386785458): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865395080000): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865403414541): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865410837916): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865419158375): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865427495166): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865435827083): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865445170583): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865453407500): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865461753208): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865470085500): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865478418875): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865486750250): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865495084416): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865503416291): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865511752250): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865520087250): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865528291166): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865535834541): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865545156583): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865553407083): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865560875208): Pause Timer : DROP : Tapn
QT.pauseTimer#(262865570174000): Pausing Timer : Tapn
WARNING: java.lang.UnsatisfiedLinkError: Invalid URL for class: resource://javafx.graphics/com/sun/glass/utils/NativeLibLoader.class
System.loadLibrary(prism_es2) succeeded
JavaFX: using com.sun.javafx.tk.quantum.QuantumToolkit
WARNING: java.lang.UnsatisfiedLinkError: Invalid URL for class: resource://javafx.graphics/com/sun/glass/utils/NativeLibLoader.class
System.loadLibrary(glass) succeededQT.postPulse@(262868695195625): TaPN
PC.addDirtyScene: 262868695642125 scene: 1082975835 @ (500,500)
QT.endPulse: 262868695661083
QT.postPulse@(262868703404875): TaPN
QT.endPulse: 262868703508583
QT.pauseTimer#(262868711750041): Pause Timer : DROP : Tapn
QT.pauseTimer#(262868720066791): Pause Timer : DROP : Tapn
QT.pauseTimer#(262868728410208): Pause Timer : DROP : Tapn
QT.pauseTimer#(262868736731041): Pause Timer : DROP : Tapn
QT.pauseTimer#(262868745083791): Pause Timer : DROP : Tapn
QT.pauseTimer#(262868753410208): Pause Timer : DROP : Tapn
QT.pauseTimer#(262868761734250): Pause Timer : DROP : Tapn
QT.pauseTimer#(262868770077750): Pause Timer : DROP : Tapn
QT.pauseTimer#(262868778413666): Pause Timer : DROP : Tapn
QT.pauseTimer#(262868786730583): Pause Timer : DROP : Tapn
QT.pauseTimer#(262868795071500): Pause Timer : DROP : Tapn
QT.pauseTimer#(262868803397541): Pause Timer : DROP : Tapn
QT.pauseTimer#(262868811732625): Pause Timer : DROP : Tapn
QT.pauseTimer#(262868820064916): Pause Timer : DROP : Tapn
QT.pauseTimer#(262868828401916): Pause Timer : DROP : Tapn
QT.pauseTimer#(262868836730375): Pause Timer : DROP : Tapn
QT.pauseTimer#(262868845068041): Pause Timer : DROP : Tapn
QT.pauseTimer#(262868853394208): Pause Timer : DROP : Tapn
QT.pauseTimer#(262868861732291): Pause Timer : DROP : Tapn
QT.pauseTimer#(262868870061625): Pause Timer : DROP : Tapn
QT.pauseTimer#(262868878410708): Pause Timer : DROP : Tapn
QT.pauseTimer#(262868886793708): Pause Timer : DROP : Tapn
QT.pauseTimer#(262868895052208): Pause Timer : DROP : Tapn
QT.pauseTimer#(262868903395500): Pause Timer : DROP : Tapn
QT.pauseTimer#(262868911762625): Pause Timer : DROP : Tapn
QT.pauseTimer#(262868920055958): Pause Timer : DROP : Tapn
QT.pauseTimer#(262868928120958): Pause Timer : DROP : Tapn
QT.pauseTimer#(262868936762375): Pause Timer : DROP : Tapn
QT.pauseTimer#(262868945063125): Pause Timer : DROP : Tapn
QT.pauseTimer#(262868953398666): Pause Timer : DROP : Tapn
QT.pauseTimer#(262868961736416): Pausing Timer : Tapn
```

## Error 3: mvn error

```
Error: could not open `toolchains/bellsoft-liberica-vm-full-openjdk23-24.1.2/lib/jvm.cfg'
```


