namespace Test;

class Members
{
    public int x = ;
    public function afterProperty() { return 1; }

    const BROKEN = ;
    const FINE = 1;

    public function missingParen(a, b
    {
        return a;
    }

    public function afterMethod() { return 2; }

    public 5 junk;
    public function afterJunk() { return 3; }

    protected value = 1;
}

interface Contract
{
    public function broken(a ;
    public function fine();
    const OK = 1;
}

class Strays
{
    foo;
    public function afterWord() { return 1; }
    ;
    public after;
}

interface StrayContract
{
    foo;
    public function afterWord();
    ;
    const FINE = 1;
}

interface ReturnJunk
{
    public function a() -> <$x>
    public function b();
}

class HalfTyped
{
    public function a() -> <Foo
    public function b() { return 1; }
}
