package modules.analytics;

public class FenwickTree {

    int[] BIT;
    int n;

    public FenwickTree(int n) {

        this.n = n;
        BIT = new int[n + 1];
    }

    public void update(int index, int value) {

        while(index <= n) {

            BIT[index] += value;
            index += index & (-index);
        }
    }

    public int sum(int index) {

        int result = 0;

        while(index > 0) {

            result += BIT[index];
            index -= index & (-index);
        }

        return result;
    }

    public int rangeSum(int left, int right) {

        return sum(right) - sum(left - 1);
    }
}