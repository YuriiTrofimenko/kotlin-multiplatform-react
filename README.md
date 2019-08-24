# kotlin-multiplatform-react-demo

Kotlin-react multiplatform demo
IDE: Intellij Idea 2019.1.4 Ultimate
JDK: 1.8.0_201

### Useful Gradle tasks
`gradle run` runs webpack dev server and ktor application. You could find logs at `build/logs`.

`gradle stop` stops webpack dev server and ktor application.

`gradle bundle` to create static files bundle.

`gradle webpack-run` runs webpack dev server only.

`gradle webpack-stop` stops webpack dev server only.

`gradle ktor-run` runs ktor application only.

`gradle ktor-stop` stops ktor application only.

`gradle jvmTest` to run common and jvm tests with JUnit.

`gradle runKarmaTests` to run common and js tests with Mocha and Karma.

### Hosts
Webpack dev server responding at http://0.0.0.0:8080. Ktor application responding at http://0.0.0.0:8081
