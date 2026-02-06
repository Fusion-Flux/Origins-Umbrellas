import java.util.regex.Pattern

plugins {
    alias(libs.plugins.loom)
}

group = "com.fusionflux"
version = "1.6.1"

repositories {
    flatDir {
        dir("libs")
    }
}

var originsVersion: String? = null

dependencies {
    minecraft(libs.minecraft)
    mappings(variantOf(libs.yarn) {
        classifier("v2")
    })

    modImplementation(libs.bundles.fabric)

    file("libs").listFiles().forEach {
        val regex = Pattern.compile("(.+)-([0-9].*).jar")
        val matcher = regex.matcher(it.name)
        if (matcher.find()) {
            val module = matcher.group(1)
            val modVersion = matcher.group(2)
            modImplementation("ignored:$module:$modVersion")

            if (module == "Origins") {
                originsVersion = modVersion.substringBefore('+')
            }
        }
    }
}

tasks.processResources {
    val properties: Map<String, Any> = mapOf(
        "version" to version,
        "minecraft_version" to libs.versions.minecraft.get(),
        "loader_version" to libs.versions.loader.get(),
        "fapi_version" to libs.versions.fapi.get(),
        "origins_version" to originsVersion!!
    )

    inputs.properties(properties)

    filesMatching("fabric.mod.json") {
        expand(properties)
    }
}

loom.runs.configureEach {
    property("mixin.debug.export", "true")
}

java {
    withSourcesJar()
    toolchain.languageVersion = JavaLanguageVersion.of(17)
}
