subprojects {
    apply(plugin = "java")
    repositories {
        mavenCentral()
    }
    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
    dependencies {
        val testImplementation by configurations
        val testRuntimeOnly by configurations
        val junitVersion: String by project
        testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
        testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    }
}
