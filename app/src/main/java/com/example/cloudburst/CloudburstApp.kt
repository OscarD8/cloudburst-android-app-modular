package com.example.cloudburst

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/*
 * Notes:
 * When the user launches the app, the Android OS creates an instance of this class.
 * Hilt Code Generation:
     * The @HiltAndroidApp annotation tells the Hilt compiler (during build process) to
     * generate the main dependency container (SingletonComponent) specifically for this
     * application.
 * Hilt Initialisation:
    * Hilt hooks into the application creation lifecycle. When the CloudburstApp instance is
    * created by the OS, Hilt automatically initialises its generated code and attaches the
    * SingletonComponent to the Application class
 */

/*
    * Application classes instantiate before Activities and are the base classes for an app.
    * By including Hilt dependencies and the annotation below, we create a subclass of Application
    * that brings Hilt into the foundation of the app. This is not directly related to activities (?)
    * but activities will now operate within the Hilt framework... the framework of the adapted Application.
    * The AndroidManifest is key in this - it tells the Android OS to use this custom Application class
    * instead of the default one, plugging in Hilt functionality to the foundation of the Application.

    * In short: the @HiltAndroidApp blueprint tells the shipyard to build a high-tech ship with an integrated power grid.
    * The @AndroidEntryPoint connects specific rooms to that grid. And @Inject lets devices in those rooms simply request resources,
    * which Hilt delivers via the grid.
 */

/**
 * The custom [Application] class for Cloudburst, annotated with `@HiltAndroidApp`
 * to enable Hilt dependency injection for the entire application.
 *
 * This class serves as the entry point for Hilt's code generation and the root
 * component (`SingletonComponent`) for managing application-scoped dependencies.
 * It must be registered in the `AndroidManifest.xml`.
 */
@HiltAndroidApp
class CloudburstApp : Application()