package domain

/**
 * Created by Denis on 28.06.2015.
 */
class RequestResult<T> {
    boolean ok
    T result

    @Override
    public String toString() {
        return "RequestResult{" +
                "ok=" + ok +
                ", result=" + result +
                '}';
    }
}
