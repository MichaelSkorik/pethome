package pethome;

class Counter implements AutoCloseable {
    private int count;

    public Counter() {
        count = 0;
    }

    public void add() {
        count++;
    }

    public int getCount() {
        return count;
    }

    @Override
    public void close() {
        if (count == 0) {
            throw new IllegalStateException("Counter cannot be closed before adding at least one animal.");
        }
    }
}