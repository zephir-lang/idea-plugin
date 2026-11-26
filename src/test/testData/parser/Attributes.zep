use Foo\Bar;

/** A documented function */
#[Pure]
function helper(#[Sensitive] value) -> int
{
    return 1;
}

#[Pure]
function declared(string name) -> string;

#[Entity]
class Attributed
{
    #[Column]
    const A = 1;

    #[Column("name"), Index]
    #[Deprecated]
    public name;

    /** Documented before */
    #[Final]
    public function documentedBefore() { return 1; }

    #[Final]
    /** Documented after */
    public function documentedAfter() { return 1; }

    #[Route("/a", 1, [1, 2], Foo::BAR)]
    public function arguments(#[A] a, #[B] int b, #[C, D(1)] <Foo> ...rest) { return a; }

    #[Internal] #[Static] #[Default] #[Case] #[Empty] #[Void] #[Reverse] #[Inline] #[Readonly]
    public function keywordNames() { return 1; }

    #[Ns\Deep\Name]
    #[\Rooted\Name(1)]
    public function qualified() { return 1; }

    #[Marker]
    public function abstractAttributed();

    #[Uses]
    use SomeTrait;

    public function closure()
    {
        let handler = function (#[Injected] a, #[Other] ...rest) { return a; };
        return handler;
    }
}

#[Contract]
interface AttributedInterface
{
    #[Version(1)]
    const VERSION = 1;

    #[Operation]
    public function run();
}

#[Reusable]
trait AttributedTrait
{
    #[Prop]
    protected value;

    #[Mark]
    public function mark();

    #[Body]
    public function body() { return 1; }
}
