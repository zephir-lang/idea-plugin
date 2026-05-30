namespace Test;

class Variadic
{
    public function untyped(...args)
    {
        return args;
    }

    public function typed(string ...names)
    {
        return names;
    }

    public function classType(<Foo> ...items)
    {
        return items;
    }

    public function combined(a, ...rest)
    {
        return rest;
    }
}