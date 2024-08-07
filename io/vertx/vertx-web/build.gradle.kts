import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
    java
    application
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

version = "1.0.0-SNAPSHOT"

val vertxVersion = "4.5.7"
val junitJupiterVersion = "5.9.1"
val jacksonVersion = "2.11.3"

val mainVerticleName = "vertx.Application"
val launcherClassName = "io.vertx.core.Launcher"

val watchForChange = "src/**/*"
val doOnChange = "${projectDir}/gradlew classes"

application {
    mainClass.set(launcherClassName)
}

dependencies {
    implementation(platform("io.vertx:vertx-stack-depchain:$vertxVersion"))

    implementation("io.vertx:vertx-web")
    implementation("io.vertx:vertx-web-client")
    implementation("io.vertx:vertx-config")
    implementation("io.vertx:vertx-config-yaml")

    // ~ database
    implementation("io.vertx:vertx-pg-client")
    implementation("io.vertx:vertx-mysql-client")
    implementation("io.vertx:vertx-sql-client-templates")
    runtimeOnly("org.postgresql:postgresql:42.6.0")
    runtimeOnly("mysql:mysql-connector-java:8.0.33")
    implementation("org.flywaydb:flyway-core:9.19.0")

    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")

    // ~ testing
    testImplementation("io.vertx:vertx-junit5")
    testImplementation("org.junit.jupiter:junit-jupiter:$junitJupiterVersion")
}

tasks.withType<ShadowJar> {
    archiveClassifier.set("fat")
    manifest {
        attributes(mapOf("Main-Verticle" to mainVerticleName))
    }
    mergeServiceFiles()
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events = setOf(PASSED, SKIPPED, FAILED)
    }
    // disable test from this project during
    isEnabled = false
}

tasks.withType<JavaExec> {
    args = listOf(
        "run",
        mainVerticleName,
        "--redeploy=$watchForChange",
        "--launcher-class=$launcherClassName",
        "--on-redeploy=$doOnChange"
    )
}
