Assessment: Upgrade, Not Rewrite

The grammar, PSI structure, and feature implementations are all sound — the plugin is small (~23 Kotlin files) and the logic is worth keeping. The problem is entirely in the tooling layer. A rewrite would discard working grammar definitions needlessly.

---
Phase 1 — Modernize the Build (Blocker)

Nothing else can proceed until the build works. This is the most involved phase.

1.1 — Gradle Wrapper
- Update gradle-wrapper.properties from Gradle 6.5.1 → 8.x
- Gradle 8 is required by the modern IntelliJ plugin

1.2 — Plugin versions in build.gradle.kts

┌──────────────────────────┬─────────┬───────────────────────────────────────────┐
│          Plugin          │ Current │                  Target                   │
├──────────────────────────┼─────────┼───────────────────────────────────────────┤
│ org.jetbrains.intellij   │ 0.4.21  │ 2.x (new IntelliJ Platform Gradle Plugin) │
├──────────────────────────┼─────────┼───────────────────────────────────────────┤
│ org.jetbrains.grammarkit │ 2020.1  │ 2022.3.2                                  │
├──────────────────────────┼─────────┼───────────────────────────────────────────┤
│ kotlin("jvm")            │ 1.3.72  │ 2.1.x                                     │
├──────────────────────────┼─────────┼───────────────────────────────────────────┤
│ net.saliman.properties   │ 1.5.1   │ 1.5.2                                     │
└──────────────────────────┴─────────┴───────────────────────────────────────────┘

1.3 — Repository
- Replace jcenter() (shut down 2022) with mavenCentral()

1.4 — IDE target version
- Update ideVersion in gradle.properties from 2020.1.1 to a modern build (e.g. 2024.3)
- Modern IntelliJ requires Java 17 as minimum — update sourceCompatibility/targetCompatibility from 1.8 → 17

1.5 — Fix build.gradle.kts API changes
The plugin upgrades bring breaking API changes that need fixing:
- GenerateLexer / GenerateParser task classes renamed to GenerateLexerTask / GenerateParserTask with new property syntax (.set())
- intellij {} block moves to provider API (pluginName.set(...), version.set(...))
- PatchPluginXmlTask property API changes
- configure<JavaPluginConvention> removed → replace with java {} block
- Remove HasConvention extension (no longer needed with Kotlin 1.6+)
- Update patchPluginXml to use sinceBuild.set(...) / untilBuild.set(...)

1.6 — Regenerate parser and lexer
- Run ./gradlew generateLexer generateParser via Docker
- Commit the regenerated gen/ files

1.7 — Verify the build
- Run ./gradlew buildPlugin and ./gradlew test
- Fix any deprecated IntelliJ Platform API usages surfaced by the newer SDK

---
Phase 2 — Grammar & Type System

2.1 — mixed type (grammar changes already done, needs regeneration from Phase 1)

2.2 — Audit grammar against current Zephir language spec
- Compare Zephir.bnf against the current https://docs.zephir-lang.com
- Identify any constructs added to the language since 2020 that are missing
- Add test cases for each new construct

---
Phase 3 — Modernize Kotlin Source

Once the build is clean against the new SDK, the compiler will surface deprecated API usages. Common issues to expect:

- PsiElementVisitor / PSI API deprecations
- Icon loading API changes
- ActionUpdateThread — modern IntelliJ requires actions to declare getActionUpdateThread()
- ColorSettingsPage — getAdditionalHighlightingTagToDescriptorMap signature changed
- Completion API minor changes

---
Phase 4 — Feature Additions

With the foundation solid, new features can be added. Priority order based on developer value:

┌───────────────────────────────────────────────┬────────┬────────┐
│                    Feature                    │ Effort │ Value  │
├───────────────────────────────────────────────┼────────┼────────┤
│ Structure view (outline of classes/methods)   │ Low    │ High   │
├───────────────────────────────────────────────┼────────┼────────┤
│ Code folding (methods, blocks, comments)      │ Low    │ High   │
├───────────────────────────────────────────────┼────────┼────────┤
│ Go-to-definition / reference resolution       │ Medium │ High   │
├───────────────────────────────────────────────┼────────┼────────┤
│ Find usages                                   │ Medium │ High   │
├───────────────────────────────────────────────┼────────┼────────┤
│ Live templates (e.g. fn → method skeleton)    │ Low    │ Medium │
├───────────────────────────────────────────────┼────────┼────────┤
│ Rename refactoring                            │ Medium │ Medium │
├───────────────────────────────────────────────┼────────┼────────┤
│ Inspections / quick fixes (e.g. unknown type) │ High   │ Medium │
├───────────────────────────────────────────────┼────────┼────────┤
│ Documentation hover                           │ Medium │ Low    │
└───────────────────────────────────────────────┴────────┴────────┘
   
---
Suggested Order of Work

Phase 1.1–1.5  → Fix build tooling (no Docker needed, just file edits)
Phase 1.6      → Regenerate grammar via Docker
Phase 1.7      → Verify build passes
Phase 2        → Grammar audit and mixed type
Phase 3        → Fix deprecated APIs
Phase 4        → New features (as desired)

---                                                                                                                                Shall I start with Phase 1?

❯ We will do each phase in its own branch, which will be chained to the previous one. I am also wondering if we can just get away with a docker command as we did earlier in this discussion for the regeneration of files, or we would benefit from a docker-compose file to bring an environment up so as to run gradle

● Good questions — let me address both.

Branch Strategy

The current freshen-up branch has the GitHub Actions updates and the mixed type grammar/source changes. The chain would be:

master                                                                                                                                                           
└── freshen-up          (GH Actions + mixed type — already in progress)
└── phase-1       (build modernization + regeneration)
└── phase-2 (grammar audit)
└── phase-3 (deprecated API fixes)
└── phase-4 (new features)

Each branch is created from the tip of the previous one, so merging goes sequentially before the next begins.

---                            
Docker: One-Off Command vs. docker-compose

Use docker-compose. Here's why the one-off command falls apart for this project specifically:

The org.jetbrains.intellij plugin downloads the full IntelliJ IDE as a compile-time dependency during the build. That is several hundred MB to over 1 GB depending on the version. Without a persistent cache volume, every docker run re-downloads:

- The Gradle distribution itself
- All Gradle plugin dependencies (grammarkit, intellij plugin, kotlin plugin, etc.)
- The IntelliJ IDE build artifact
- All project dependencies

On a cold run that could be 10–20 minutes and hundreds of MB every single time. With a named volume for ~/.gradle, it downloads once and is cached forever.

A docker-compose.yml also:
- Declares the exact JDK/Gradle version needed (becomes part of the repo)
- Lets anyone on the project run any Gradle task with a consistent environment
- Is easier to remember than a long docker run command with multiple flags

The compose file would look roughly like this:

services:
gradle:
image: gradle:8.5-jdk17
working_dir: /project                                                                                                              volumes:
- .:/project
- gradle-cache:/home/gradle/.gradle
user: "${UID:-1000}:${GID:-1000}"

volumes:                       
gradle-cache:

Then any task becomes:

docker compose run --rm gradle ./gradlew generateLexer generateParser                                                                                            
docker compose run --rm gradle ./gradlew buildPlugin
docker compose run --rm gradle ./gradlew test

The gradle-cache volume survives between runs so the IntelliJ SDK and all dependencies are downloaded only once.
   
---                                                                                                                                                              
Recommendation: Add the docker-compose.yml as part of Phase 1 alongside the build modernization — it's the environment that makes Phase 1 testable. Shall I start Phase 1 now?

❯ Agreed, let us do the docker-compose in phase 1.1 so that we can test as we go along.

● Let me create the branch and get started.
● Bash(git checkout -b phase-1)                                                                                                    

Now create the docker-compose.yml and update the Gradle wrapper together — they're coupled since the compose image version should match the wrapper version.

The environment is working — Gradle 8.9 downloaded and ran correctly. The failure is the same one we saw before: build.gradle.kts still references the old broken plugins. The docker-compose setup is confirmed good. Now we move straight into Phase 1.2 — updating build.gradle.kts.

This is the most involved edit. The changes needed:

- org.jetbrains.intellij 0.4.21 → 2.x (new plugin, new API)
- org.jetbrains.grammarkit 2020.1 → 2022.3.2 (new task class names + property API)
- kotlin("jvm") 1.3.72 → 2.1.0
- jcenter() → mavenCentral()
- Fix all API call sites to match                                                                                                                                
