namespace Zephir;

class ReturnTypeHints2
{
    public function test() -> int | <caret> {

    }
}
