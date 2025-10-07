plugins {
    alias(libs.plugins.kotlin.jvm)
}



dependencies {
    compileOnly(libs.jakarta.inject.api) // only need the jakarta inject annotations to compile

    // Test dependencies for JUnit 5
    testImplementation(platform(libs.junit.jupiter.bom))
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testRuntimeOnly(libs.junit.platform.launcher)
}