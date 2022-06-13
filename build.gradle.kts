subprojects {
    apply(plugin = "java")
    repositories {
        mavenCentral()
    }
    dependencies {
        val testImplementation by configurations
        val junitVersion: String by project
        testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    }
}
