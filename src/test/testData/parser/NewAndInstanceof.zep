namespace Test;

class NewAndInstanceof
{
    public function creates(name)
    {
        let a = new static;
        let b = new static();
        let c = new static(1, 2);
        let d = new Foo;
        let e = new Foo();
        let f = new Foo(1);
        let g = new {name};
        let h = new {name}(1);
    }

    public function checks(a, b)
    {
        let r1 = a instanceof Foo;
        let r2 = a instanceof static;
        let r3 = a instanceof self;
        let r4 = a instanceof parent;
        let r5 = a instanceof static::class;
        let r6 = a instanceof \Ns\Foo;
        let r7 = !a instanceof static;
        let r8 = !a instanceof Foo;
        if a instanceof static {
            return true;
        }
        return a instanceof self;
    }
}
