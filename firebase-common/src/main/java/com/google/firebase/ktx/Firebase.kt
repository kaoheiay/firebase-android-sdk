// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.google.firebase.ktx

import android.content.Context
import androidx.annotation.Keep
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.annotations.concurrent.Background
import com.google.firebase.annotations.concurrent.Blocking
import com.google.firebase.annotations.concurrent.Lightweight
import com.google.firebase.annotations.concurrent.UiThread
import com.google.firebase.components.Component
import com.google.firebase.components.ComponentRegistrar
import com.google.firebase.components.Dependency
import com.google.firebase.components.Qualified
import java.util.concurrent.Executor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher

/**
 * Single access point to all firebase SDKs from Kotlin.
 *
 * <p>Acts as a target for extension methods provided by sdks.
 */
@Deprecated(
  "com.google.firebase.ktx.Firebase has been deprecated. Use `com.google.firebase.Firebase` instead.",
  ReplaceWith(
    expression = "Firebase",
    imports = ["com.google.firebase.Firebase", "com.google.firebase.Firebase"]
  )
)
object Firebase

/** Returns the default firebase app instance. */
@Deprecated(
  "com.google.firebase.ktx.Firebase.app has been deprecated. Use `com.google.firebase.Firebase.app` instead.",
  ReplaceWith(
    expression = "Firebase.app",
    imports = ["com.google.firebase.Firebase", "com.google.firebase.app"]
  )
)
val Firebase.app: FirebaseApp
  get() = FirebaseApp.getInstance()

/** Returns a named firebase app instance. */
@Deprecated(
  "com.google.firebase.ktx.Firebase.app(name) has been deprecated. Use `com.google.firebase.Firebase.app(name)` instead.",
  ReplaceWith(
    expression = "Firebase.app(name)",
    imports = ["com.google.firebase.Firebase", "com.google.firebase.app"]
  )
)
fun Firebase.app(name: String): FirebaseApp = FirebaseApp.getInstance(name)

/** Initializes and returns a FirebaseApp. */
@Deprecated(
  "com.google.firebase.ktx.Firebase.initialize(context) has been deprecated. Use `com.google.firebase.Firebase.initialize(context)` instead.",
  ReplaceWith(
    expression = "Firebase.initialize(context)",
    imports = ["com.google.firebase.Firebase", "com.google.firebase.initialize"]
  )
)
fun Firebase.initialize(context: Context): FirebaseApp? = FirebaseApp.initializeApp(context)

/** Initializes and returns a FirebaseApp. */
@Deprecated(
  "com.google.firebase.ktx.Firebase.initialize(context, options) has been deprecated. Use `com.google.firebase.Firebase.initialize(context, options)` instead.",
  ReplaceWith(
    expression = "Firebase.initialize(context, options)",
    imports = ["com.google.firebase.Firebase", "com.google.firebase.initialize"]
  )
)
fun Firebase.initialize(context: Context, options: FirebaseOptions): FirebaseApp =
  FirebaseApp.initializeApp(context, options)

/** Initializes and returns a FirebaseApp. */
@Deprecated(
  "com.google.firebase.ktx.Firebase.initialize(context, options, name) has been deprecated. Use `com.google.firebase.Firebase.initialize(context, options, name)` instead.",
  ReplaceWith(
    expression = "Firebase.initialize(context, options, name)",
    imports = ["com.google.firebase.Firebase", "com.google.firebase.initialize"]
  )
)
fun Firebase.initialize(context: Context, options: FirebaseOptions, name: String): FirebaseApp =
  FirebaseApp.initializeApp(context, options, name)

/** Returns options of default FirebaseApp */
@Deprecated(
  "com.google.firebase.ktx.Firebase.options has been deprecated. Use `com.google.firebase.Firebase.options` instead.",
  ReplaceWith(
    expression = "Firebase.options",
    imports = ["com.google.firebase.Firebase", "com.google.firebase.options"]
  )
)
val Firebase.options: FirebaseOptions
  get() = Firebase.app.options

/** @suppress */
@Deprecated(
  "com.google.firebase.ktx.FirebaseCommonKtxRegistrar has been deprecated. Use `com.google.firebase.FirebaseCommonKtxRegistrar` instead.",
  ReplaceWith(
    expression = "FirebaseCommonKtxRegistrar",
    imports = ["com.google.firebase.Firebase", "com.google.firebase.FirebaseCommonKtxRegistrar"]
  )
)
@Keep
class FirebaseCommonKtxRegistrar : ComponentRegistrar {
  override fun getComponents(): List<Component<*>> {
    return listOf(
      coroutineDispatcher<Background>(),
      coroutineDispatcher<Lightweight>(),
      coroutineDispatcher<Blocking>(),
      coroutineDispatcher<UiThread>()
    )
  }
}

private inline fun <reified T : Annotation> coroutineDispatcher(): Component<CoroutineDispatcher> =
  Component.builder(Qualified.qualified(T::class.java, CoroutineDispatcher::class.java))
    .add(Dependency.required(Qualified.qualified(T::class.java, Executor::class.java)))
    .factory { c ->
      c.get(Qualified.qualified(T::class.java, Executor::class.java)).asCoroutineDispatcher()
    }
    .build()