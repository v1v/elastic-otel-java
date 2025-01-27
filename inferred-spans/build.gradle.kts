plugins {
  id("elastic-otel.library-packaging-conventions")
  id("elastic-otel.sign-and-publish-conventions")
}

description = "Elastic OpenTelemetry Inferred Spans extension"

dependencies {
  annotationProcessor(libs.autoservice.processor)
  compileOnly(libs.autoservice.annotations)
  compileOnly("io.opentelemetry:opentelemetry-sdk")
  compileOnly("io.opentelemetry:opentelemetry-sdk-extension-autoconfigure-spi")
  compileOnly(libs.findbugs.jsr305)
  implementation(libs.lmax.disruptor)
  implementation(libs.jctools)
  implementation(project(":common"))

  testAnnotationProcessor(libs.autoservice.processor)
  testCompileOnly(libs.autoservice.annotations)
  testCompileOnly(libs.findbugs.jsr305)
  testImplementation(project(":testing-common"))
  testImplementation("io.opentelemetry:opentelemetry-sdk")
  testImplementation("io.opentelemetry:opentelemetry-sdk-extension-autoconfigure")
  testImplementation(libs.awaitility)
  testImplementation(libs.github.api)
  testImplementation(libs.apachecommons.compress)
  testImplementation(libs.asyncprofiler)
}

tasks.compileJava {
  options.encoding = "UTF-8"
}

tasks.javadoc {
  options.encoding = "UTF-8"
}

tasks.processResources {
  doLast {
    val resourcesDir = sourceSets.main.get().output.resourcesDir
    val packageDir = resourcesDir!!.resolve("co/elastic/otel/profiler");
    packageDir.mkdirs();
    packageDir.resolve("inferred-spans-version.txt").writeText(project.version.toString())
  }
}

tasks.withType<Test>().all {
  jvmArgs("-Djava.util.logging.config.file="+sourceSets.test.get().output.resourcesDir+"/logging.properties")
}
