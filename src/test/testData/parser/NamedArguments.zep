namespace Test;

#[Route(path: "/a", name: "route", methods: ["GET"])]
class NamedArguments
{
    public function calls(a, b)
    {
        strlen(string: a);
        this->run(first: 1, second: a);
        self::make(value: 1, other: b);
        Foo::make(key: 1);
        let o = new Foo(name: "x", default: 1);
        let p = new Foo(1, extra: 2);
    }

    public function keywords(a)
    {
        keywords(default: 1);
        keywords(class: a);
        keywords(null: 1);
        keywords(string: a);
        keywords(array: [1]);
        keywords(int: 1, var: 2, public: 3, static: 4, new: 5, if: 6);
        keywords(let: 1, echo: 2, const: 3, require: 4, yield: 5, trait: 6);
    }

    public function combos(a, b, c)
    {
        combos(1, named: 2);
        combos(a, b, key: c);
        combos(a ? b : c, named: a ? b : c);
        combos(a ?: b);
        combos(x: [k: 1, j: 2]);
        combos(first: second => second * 2);
    }
}
