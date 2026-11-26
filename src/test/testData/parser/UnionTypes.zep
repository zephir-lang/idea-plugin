namespace Test;

class UnionTypes
{
    public function params(int|string a, <Alpha>|<Beta> b, int|string|null c = null, array|false d = false)
    {
        return a;
    }

    public function returns() -> int|null
    {
        return 1;
    }

    public function returnsFalse() -> string|false
    {
        return false;
    }

    public function returnsClasses() -> <Alpha>|<Beta>|null
    {
        return null;
    }

    public function collection() -> <Item[]>
    {
        return [];
    }

    public function collectionUnion() -> <Ns\Item[]>|null
    {
        return [];
    }

    public function fluent() -> this
    {
        return this;
    }

    public function fluentUnion() -> this|null
    {
        return this;
    }

    public function nothing() -> void
    {
    }
}

interface UnionContract
{
    public function find(int|string id) -> <Entity>|null;
    public function all() -> <Entity[]>;
}
