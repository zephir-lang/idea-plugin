namespace Test;

abstract class Modifiers
{
    public static a = 1;
    static public b = 2;
    protected static c;
    private static d;
    internal e;
    internal static f;
    static internal fStatic;
    public internal fPublic;
    public readonly g;
    readonly protected h;
    public inline function inlined() { return 1; }
    inline public function inlinedFirst() { return 1; }
    public deprecated function old() { return 1; }
    deprecated protected function olderStill() { return 1; }
    public final function finished() { return 1; }
    final private function finishedFirst() { return 1; }
    internal function hidden() { return 1; }
    public internal function hiddenLate() { return 1; }
    static internal function hiddenStatic() { return 1; }
    public static function make() { return 1; }
    static public function makeFirst() { return 1; }
    abstract public function abstractFirst();
    public abstract function abstractSecond();
    protected static abstract function abstractLast(a, b);
    public function noBody();
}

interface Contract
{
    public function run();
    internal function hidden();
}
