import com.github.gradle.node.yarn.task.YarnTask

plugins {
  id ("com.github.node-gradle.node")
}
// https://github.com/node-gradle/gradle-node-plugin/tree/master/examples/simple-node
// https://github.com/node-gradle/gradle-node-plugin/blob/master/src/test/resources/fixtures/kotlin/build.gradle.kts
node {
  val nodejsVersion: String by project
  version.set(nodejsVersion)
  npmVersion.set("")
  yarnVersion.set("")
  download.set(false)
}

val yarnInstallTask = tasks.register<YarnTask>("yarnInstall") {
  args.set(listOf("install", "--frozen-lockfile", "--ignore-scripts"))
  inputs.files("yarn.lock")
  outputs.dir("node_modules")
}

val lintTask = tasks.register<YarnTask>("lint") {
  args.set(listOf("lint"))
  dependsOn(yarnInstallTask, "lintDockerImage")
  inputs.dir("src")
  inputs.dir("node_modules")
  inputs.files("angular.json", ".browserslistrc", "tsconfig.json", "tsconfig.app.json", "tsconfig.spec.json",
    "tslint.json")
  outputs.upToDateWhen { true }
}

val testTask = tasks.register<YarnTask>("test") {
  args.set(listOf("test", "-c", "ci"))
  dependsOn(yarnInstallTask)
  inputs.dir("src")
  inputs.dir("node_modules")
  inputs.files("angular.json", ".browserslistrc", "tsconfig.json", "tsconfig.app.json", "tsconfig.spec.json",
    "tslint.json")
  outputs.upToDateWhen { true }
}

val buildTask = tasks.register<YarnTask>("build") {
  args.set(listOf("build", "--prod"))
  dependsOn(yarnInstallTask, lintTask, testTask)
  inputs.dir(project.fileTree("src").exclude("**/*.spec.ts"))
  inputs.dir("node_modules")
  inputs.files("yarn.lock", "tsconfig.json", "tsconfig.build.json")
  outputs.dir("www")
}

tasks.register("prepareDockerImage", Exec::class) {
  dependsOn(buildTask)
}

tasks.register<YarnTask>("start") {
  args.set(listOf("start:dev"))
  dependsOn(yarnInstallTask)
}
