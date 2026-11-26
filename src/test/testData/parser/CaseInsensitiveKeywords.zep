NAMESPACE Test;

USE Foo\Bar AS Baz;

FINAL CLASS MixedCase
{
    PUBLIC Name = NULL;

    Public Function KeepCase(String Value, INT Count) -> Bool
    {
        LET Result = TRUE;
        IF Result { RETURN FALSE; }
        Return Result;
    }
}

Interface Shape
{
    Public Function Area();
}

TRAIT Reusable
{
    PROTECTED Fixed = 1;
}
