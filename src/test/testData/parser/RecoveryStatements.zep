namespace Test;

class Statements
{
    public function missingSemicolon()
    {
        let a = 1
        let b = 2;
        return b;
    }

    public function unclosedCall()
    {
        foo(1, 2;
        let c = 3;
        return c;
    }

    public function strayToken()
    {
        let a = 1;
        ) ;
        let b = 2;
        return b;
    }

    public function junkStatement()
    {
        abc def;
        this->run();
        return 1;
    }

    public function junkBlock()
    {
        ) { x; };
        this->run();
        return 1;
    }

    public function missingBrace()
    {
        if a {
            let x = 1;
        return x;
    }

    public function afterMissing()
    {
        return 1;
    }
}
