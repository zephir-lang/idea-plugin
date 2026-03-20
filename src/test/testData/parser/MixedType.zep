namespace Test;

class MixedType
{
    public function process(mixed value) -> mixed
    {
        return value;
    }

    public function identity(mixed a, mixed b) -> mixed
    {
        return a;
    }
}
