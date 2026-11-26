namespace Test;

class Broken
{
    public function run(list)
    {
        let [a, [b, c]] = list;
    }
}
