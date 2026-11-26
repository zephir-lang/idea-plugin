namespace Test;

class Broken
{
    public function run()
    {
        let f = #[A] function () {};
        return f;
    }
}
