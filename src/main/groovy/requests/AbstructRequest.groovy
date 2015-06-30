package requests

/**
 * Created by Denis on 28.06.2015.
 */
abstract class AbstructRequest implements Request {

    @Override
    public String toString() {
        return "AbstructRequest{" +
                "methodName='" + methodName + '\'' +
                ", parameters=" + parameters.toMapString() +
                '}';
    }
}
