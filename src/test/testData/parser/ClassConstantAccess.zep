namespace Test;

class ClassConstantAccess extends Base
{
    const KEYS = [Foo::class, Bar::class];

    public names = [Foo::class, 1];
    public single = Foo::class;
    public selfName = self::class;

    public function names()
    {
        let a = Foo::class;
        let b = \Foo\Bar::class;
        let c = self::class;
        let d = parent::class;
        let e = static::class;
        let f = [Foo::class, 1];
        let g = static::VALUE;
        let h = self::VALUE;
        let i = parent::VALUE;
        let j = static::VALUE + 1;
        return a;
    }

    public function calls(m)
    {
        static::foo();
        static::foo(1, 2);
        static::{"name"}();
        static::{m}();
        Foo::{m}();
        Foo::{"m"}();
        FOO::{m}();
        {m}::bar();
        let x = static::make()->run();
        let y = Foo::bar(1);
        let z = self::bar();
    }

    public function defaults(a = Foo::class, b = [Foo::class, Bar::class])
    {
        return a;
    }
}
