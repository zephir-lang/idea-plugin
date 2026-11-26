namespace Test;

class Closures
{
    public function forms(x)
    {
        let f1 = function () { return 1; };
        let f2 = function (a) { return a; };
        let f3 = function () use (x) { return x; };
        let f4 = function (a) use (x) { return a; };
        let f5 = function () -> int { return 1; };
        let f6 = function (a) -> int { return a; };
        let f7 = function () use (x) -> int { return x; };
        let f8 = function (a, b) use (x, y) -> int|null { return a; };
    }

    public function captures(x, y)
    {
        let by_ref = function () use (&x) { return x; };
        let by_const = function () use (const x) { return x; };
        let const_ref = function () use (const &x, &y) { return x; };
        let typed = function (int a, <Foo> b, ...rest) -> string { return "x"; };
    }

    public function arrows(list, a, b, c)
    {
        let doubled = n => n * 2;
        let pick = x => a ? b : c;
        let curried = p => q => p + q;
        let mapped = array_map(n => n + 1, list);
        let shorty = x => x ?: 0;
        let plain = a;
    }
}
