namespace Test;

class TypedProperties
{
    public int a;
    public string b = "x";
    protected array c = [];
    private var d;
    public <Foo> e;
    public <Ns\Deep\Foo> f = null;
    public ?int g;
    public ?string h = null;
    public ?<Foo> i;
    public int|string j;
    public int|string|null k = null;
    public <Foo>|null l;
    public <Foo>|<Bar> m;
    public array|false n = false;
    public readonly int o;
    public readonly ?<Foo> p;
    public readonly int|string q;
    public static int r = 3;
    public int s = 1 { get, set };
    public ?int t { get };
    public int|string|null u { set };
    public <Foo> v { toString };
}
