namespace Test;

class Yields
{
    public function bare()
    {
        yield;
    }

    public function value()
    {
        yield 1;
        yield a + 1;
    }

    public function keyValue()
    {
        yield "key", 2;
        yield k, this->items[k];
    }

    public function inClosure()
    {
        let generator = function () {
            yield;
            yield 1;
            yield "a", "b";
        };
        return generator;
    }
}
