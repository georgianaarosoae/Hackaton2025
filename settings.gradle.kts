pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Hackaton2025"
include(":app")

file("feature").listFiles()?.filter { it.isDirectory }?.forEach { feature ->
    feature.listFiles()?.filter { it.isDirectory }?.forEach { submodule ->
        include(":feature:${feature.name}:${submodule.name}")
        project(":feature:${feature.name}:${submodule.name}").projectDir = submodule
    }
}

 