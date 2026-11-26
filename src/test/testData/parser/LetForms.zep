namespace Test;

class LetForms
{
    public function destructure(list)
    {
        let [a, b] = list;
        let [a, , c] = list;
        let [, b] = list;
        let [a, b, ] = list;
        let [a, , , d] = list;
        let [a] = [1, 2];
        let { target } = list;
    }

    public function operators(a, b)
    {
        let a &= b;
        let a |= b;
        let a ^= b;
        let a <<= 2;
        let a >>= 2;
        let this->flags &= 1;
        let this->flags |= 2;
        let a += 1, b -= 1, a &= b;
    }

    public function requires(path)
    {
        require path;
        require_once path;
        require_once "vendor/autoload.php";
        return require_once path;
    }
}
