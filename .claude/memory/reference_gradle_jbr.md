---
name: Running Gradle Without a JDK 21
description: Use the JetBrains Runtime from the Gradle-cached IDE as JAVA_HOME and as the toolchain path when no JDK 21 is installed
type: reference
---

The build needs JDK 21 (`jvmToolchain(21)`). On a machine without one, the IDE that Gradle downloads for the build
carries a JetBrains Runtime 21 under
`~/.gradle/caches/<gradle version>/transforms/<hash>/transformed/ideaIC-2024.3-<arch>/jbr/Contents/Home` (find it with
a glob). Pass it both ways, or toolchain resolution fails:

- `JAVA_HOME=<that path>`
- `-Porg.gradle.java.installations.paths=<that path>`

Both in one shell, since a new shell does not keep the variable:

```bash
JBR=$(ls -d ~/.gradle/caches/*/transforms/*/transformed/ideaIC-2024.3-*/jbr/Contents/Home | head -1); JAVA_HOME="$JBR" ./gradlew buildPlugin -Porg.gradle.java.installations.paths="$JBR"
```

Swap `buildPlugin` for `runIde` to start a sandbox IDE with the plugin installed.

The first build after a clean cache spends most of its time downloading the IDEs `verifyPlugin` checks against.
