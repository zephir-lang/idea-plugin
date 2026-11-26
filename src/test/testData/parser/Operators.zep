namespace Test;

class Operators
{
    public function ranges(a, b)
    {
        let r1 = a .. b;
        let r2 = a ... b;
        let r3 = 1 .. 10;
        let r4 = 1 ... 10;
        let r5 = a + 1 .. b;
        let r6 = a .. b * 2;
        for i in a .. b {
            echo i;
        }
        for i in a ... b {
            echo i;
        }
    }

    public function ternaries(a, b, c)
    {
        let t1 = a ?: b;
        let t2 = a ? b : c;
        let t3 = a ?: b ?: c;
        let t4 = a ? b : c ?: 1;
    }

    public function strings(name)
    {
        let s1 = ~"Hello";
        let s2 = ~"Hello {name}";
        let s3 = ~"";
        let s4 = ~"a" . name;
    }

    public function bitwise(a, b)
    {
        let n1 = ~a;
        let n2 = ~0;
        let n3 = ~a & b;
        let n4 = ~~a;
        let n5 = a | ~b;
        let n6 = ~(a + 1);
        let n7 = !a;
    }
}
