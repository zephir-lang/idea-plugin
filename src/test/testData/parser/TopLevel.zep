use Foo\Bar;

function helper(value)
{
    return value;
}

function declared(string name) -> string;

%{
    /* raw C */
}%

class First
{
    public function run()
    {
        return 1;
    }
}

interface Second
{
    public function run();
}

fn shortHand()
{
    return 2;
}
