---
name: Zephir PSI Shapes
description: Where the generated PSI puts method calls, attributes, abstract methods and nullable names
type: domain
---

- **Method calls** have two shapes. A chain on a variable (`a->b()->c()`) is a `CallExpr` built from a `Variable` with
  property accesses and call tails. A call on any other receiver (`new X()->y()`, `foo()[0]->bar()`) is a
  `MethodCallExpr`. Code reading method calls handles both.
- **Attribute groups** are siblings before the declaration or member they belong to, not children, because the rules
  wrapping them (`AttributedDeclaration`, `AttributedMember`) are private. A docblock lookup walks back over attribute
  groups, whitespace and comments.
- **Methods** in class and trait bodies are one `MethodDefinition`; one ending in `;` is abstract, asked through
  `hasBody()`. Interface methods are `InterfaceMethodDefinition`.
- **Names** (`Id`) of classes, interfaces, traits, functions, methods and properties are nullable, because each rule
  commits before its name; readers stay null-safe.
- **Trait use** inside a body is `TraitUseStatement` holding a `ComplexIdList`, separate from the top-level
  `ImportStatement`.
