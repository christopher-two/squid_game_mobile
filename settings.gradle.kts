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

rootProject.name = "squid_game_mobile"
include(":app")
include(":feature")
include(":feature:start")
include(":feature:api")
include(":feature:login")
include(":feature:camera")
include(":feature:home")
include(":network:firebase")
include(":shared:utils")
include(":shared:ui")
include(":data:datastore")
