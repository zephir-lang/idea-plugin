namespace Test;

class ConstExpressions
{
    const A = 1;
    const B = 2 + 3 * 4;
    const C = A + 1;
    const D = self::A + 1;
    const E = "a" . "b";
    const F = 1 << 3;
    const G = 6 | 1;
    const H = 6 & 3;
    const I = 6 ^ 3;
    const J = ~0;
    const K = !false;
    const L = true ? 1 : 2;
    const M = [1, A, self::A + 1, "k": 2];
    const N = (1 + 2) * 3;
    const O = -1;
    const P = null;
    const Q = "x" . A . "y";

    public a = 1 + 2;
    public b = self::A + 1;
    public c = [1, 2, [3, 4]];
    public d = 1 << 2 | 3;
    public e = ~0;
    public f = !false;
    public g = true ? 1 : 2;
    public h = "a" . "b";
    public int i = A * 2;
    public ?int j = null;
    public k = [self::A, "x" . "y"];
}

interface ConstContract
{
    const A = 1;
    const B = A + 1;
    const C = ~0;
}
