namespace Test;

class ParameterForms
{
    public function reference(&a, int &b, <Foo> &c)
    {
        return a;
    }

    public function strict(int! a, string! b = "x")
    {
        return a;
    }

    public function strictReference(int! &a)
    {
        return a;
    }

    public function strictReturn() -> int!
    {
        return 1;
    }

    public function strictUnionReturn() -> int!|null
    {
        return 1;
    }

    public function variadics(string ...names)
    {
        return names;
    }

    public function variadicTyped(int a, <Foo> ...items)
    {
        return items;
    }

    public function variadicUnion(int|string ...items)
    {
        return items;
    }

    public function variadicBare(...items)
    {
        return items;
    }

    public function constants(const a, const int b, const &c, const <Foo> d)
    {
        return a;
    }

    public function variadicReference(int ...&rest, ...&bare)
    {
        return rest;
    }

    public function defaults(a = 1, int b = 2, array c = [], d = null)
    {
        return a;
    }
}
