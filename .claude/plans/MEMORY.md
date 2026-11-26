# Plans Index

- [Constant Completion Without a Default](task_const_completion_default.md) — method-scope completion throws on a class constant declared without a value
- [Commenter Doc Comment Check](task_commenter_doc_comment.md) — ZephirCommenter never recognises a doc comment and can throw
- [Trait Member Completion in Using Classes](task_trait_member_completion.md) — offer the members of used traits in this-> and self:: completion inside a class
- [Namespace Separator and Dollar Tokens](task_namespace_separator_token.md) — `\` and `$` lex as bad characters that the grammar matches by text
- [Expression Precedence](task_expression_precedence.md) — several operators the plugin parses group differently from the Zephir parser's precedence table
- [Property and Array Access on Any Receiver](task_property_access_any_receiver.md) — property access and repeated array access fail after a call, a new expression or an array access
- [Class Name References](task_class_name_references.md) — contributed references never attach to qualified names, so rename and Find Usages miss every usage
- [Completion in Top-Level Function Bodies](task_function_body_completion.md) — a top-level function body gets no statement keywords, type hints or variable completion
- [Header Completion by Position](task_header_completion_position.md) — class header completion offers extends and implements wherever the caret is, including after implements
- [CONTRIBUTING Issue Link](task_contributing_issue_link.md) — CONTRIBUTING.md sends issues to the zephir-mode repository instead of this one
