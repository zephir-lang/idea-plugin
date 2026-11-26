namespace Test;

class Broken
{
    public int x =<error> </error>;

    <error>foo</error>;

    public<error> </error>5 junk;

    public function afterProperty() -> int|<error>void</error>
    {
        let a = 1;
        <error>) ;</error>
        return a;
    }

    public function valid()
    {
        return 1;
    }
}

interface Valid
{
    public function run();
}

function helper(value)
{
    return value;
}
