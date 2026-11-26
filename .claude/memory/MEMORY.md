# Project Memory Index

## Domain

- [Zephir PSI Shapes](domain_psi_shapes.md) — method calls have two shapes (CallExpr chains and MethodCallExpr); attribute groups are siblings of their declaration; one MethodDefinition with hasBody(); names are nullable

## Rules

- [Licence Check Needs pcregrep](rules_ci_license_check.md) — .ci/check-license.sh prints "License OK!" and checks nothing when pcregrep is missing

## References

- [Running Gradle Without a JDK 21](reference_gradle_jbr.md) — use the JBR of the Gradle-cached IDE as JAVA_HOME and as -Porg.gradle.java.installations.paths
