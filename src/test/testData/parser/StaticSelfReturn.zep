namespace Test;

class StaticSelfReturn
{
    public function makeStatic() -> <static>
    {
        return null;
    }

    public function makeSelf() -> <self>
    {
        return null;
    }

    public function makeUnion() -> <Foo> | <static>
    {
        return null;
    }
}