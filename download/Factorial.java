final class Factorial {

    //階乗とその逆元
    private final long[] fact, inFact;
    private final long mod;

    /**
     * 1～Nの階乗とその逆元をmodで割ったあまりを事前に計算します。
     *
     * @param N 計算範囲
     * @param mod 法
     */
    public Factorial ( int N, long mod ) {
        fact = new long[N + 1];
        fact[0] = fact[1] = 1;
        for ( int i = 2; i <= N; i++ ) {
            fact[i] = fact[i - 1] * i % mod;
        }

        inFact = new long[N + 1];
        inFact[N] = MathFunction.modPow( fact[N], mod - 2, mod );
        for ( int i = N; i > 0; i-- ) {
            inFact[i - 1] = inFact[i] * i % mod;
        }
        inFact[0] = 1;

        this.mod = mod;
    }

    /**
     * num!をmodで割ったあまりを返します。
     *
     * @param num
     *
     * @return num!
     */
    public long getFact ( int num ) {
        return fact[num];
    }

    /**
     * aCbをmodで割ったあまりを返します。
     *
     * @param a
     * @param b
     *
     * @return aCb
     */
    public long getCombi ( int a, int b ) {
        if ( a < b || a <= 0 || b <= 0 ) {
            throw new IllegalArgumentException( "Factorial's index must be positive" );
        }
        return ( fact[a] * inFact[a - b] % mod ) * inFact[b] % mod;
    }
}
