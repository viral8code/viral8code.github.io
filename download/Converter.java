final class Converter {

    /**
     * Stringをintに変換します。
     *
     * @param str 変換対象
     *
     * @return 変換結果
     */
    public static int parseInt ( String str ) {
        char[] array = str.toCharArray();
        int ans = 0;
        boolean plus = true;
        if ( array[0] == '-' ) {
            plus = false;
            array[0] = '0';
        }
        for ( char num: array ) {
            ans = ans * 10 + num - '0';
        }
        return plus ? ans : -ans;
    }

    /**
     * Stringをlongに変換します。
     *
     * @param str 変換対象
     *
     * @return 変換結果
     */
    public static long parseLong ( String str ) {
        char[] array = str.toCharArray();
        long ans = 0;
        boolean plus = true;
        if ( array[0] == '-' ) {
            plus = false;
            array[0] = '0';
        }
        for ( char c: array ) {
            ans = ans * 10 + c - '0';
        }
        return plus ? ans : -ans;
    }
}
