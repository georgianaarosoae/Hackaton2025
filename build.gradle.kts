// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}
tasks.register("createFeatureModule") {
    doLast {
        val featureName = project.findProperty("featureName")?.toString()
            ?: error("You have to specify -PfeatureName=numele_modului")

        val baseDir = file("D:/HackatonProject/Hackaton2025/feature/$featureName")
        val submodules = listOf("api", "data", "domain", "presentation")

        submodules.forEach { name ->
            val moduleDir = baseDir.resolve(name)
            val mainDir = moduleDir.resolve("src/main/java/com/example/$featureName/$name")
            val testDir = moduleDir.resolve("src/test/java/com/example/$featureName/$name")

            mainDir.mkdirs()
            testDir.mkdirs()

            val buildFile = moduleDir.resolve("build.gradle.kts")
            buildFile.writeText(
                """
                plugins {
                    id("com.android.library")
                    kotlin("android")
                }

                android {
                    namespace = "com.example.$featureName.$name"
                    compileSdk = 33
                    defaultConfig {
                        minSdk = 24
                        targetSdk = 33
                    }
                }

                dependencies {}
                """.trimIndent()
            )
        }

        println("✅ $featureName module has been created successfully!")
    }
}
