namespace Test;

class ChainedCalls
{
    public function chains(a, m)
    {
        let r1 = a->b()->c();
        let r3 = (new X())->y();
        let r4 = foo()[0];
        let r5 = {m}::bar();
        let r6 = Foo::make(1)->run(2);
        let r7 = this->list()[0];
        let r8 = a->b->c(1);
        new X()->y();
        let n1 = new X()->y();
        foo()[0]->bar();
        let n2 = foo()[0]->bar();
        let n3 = new X()->y()->z();
        let n4 = foo()[0]->bar()[1];
        let r9 = outer(inner(1, two(2)), third(three(3)->four()));
        a->b()->c();
    }
}
