package montecarlo;

public class Triple<L,M,R> {
    public L left;
    public M mid;
    public R right;

    public Triple() {
        left = null;
        mid = null;
        right = null;
    }

    public void clearAll() {
        left = null;
        mid = null;
        right = null;
    }

    public void setAll(L _left, M _mid, R _right) {
        left = _left;
        mid = _mid;
        right = _right;
    }

    public R getRight() {
        return right;
    }

    public L getLeft() {
        return left;
    }

    public M getMid() {
        return mid;
    }

    public void setRight(R right) {
        this.right = right;
    }

    public void setMid(M mid) {
        this.mid = mid;
    }

    public void setLeft(L left) {
        this.left = left;
    }
}
