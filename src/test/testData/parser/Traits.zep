namespace Test;

trait Greets
{
    const GREETING = "hello";

    protected name = "world";

    public function greet()
    {
        return this->name;
    }

    abstract public function label();
}

trait Loud
{
    use Greets;
}

class Greeter
{
    use Greets;
    use Loud, Test\Deep\Shout;

    public function label()
    {
        return "greeter";
    }
}
