}
foo bar
namespace Test;

class Broken extends
{
    public function lost() { return 1; }
}

interface Valid
{
    public function run();
}

class Compared extends B < C
{
    public function kept() { return 1; }
}

trait Reusable
{
    public function reuse() { return 1; }
}

function helper(value)
{
    return value;
}

class Late
{
}

function brokenReturn() -> <$x>

class AfterReturn
{
    public function g() { return 1; }
}
