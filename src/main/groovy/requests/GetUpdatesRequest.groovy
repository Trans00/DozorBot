package requests

/**
 * Created by Denis on 28.06.2015.
 */
class GetUpdatesRequest extends AbstructRequest {
    final String methodName = "getUpdates"
    Map<String,String> parameters

    GetUpdatesRequest() {
        parameters = [:]
    }

    GetUpdatesRequest(int offset, int limit=100, int timeout = 0) {
        parameters = [offset: offset, limit: limit, timeout: timeout]
    }
}
