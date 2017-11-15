package onmap.co.il.remote.localization;

/**
 * Created by dev on 10.11.17.
 */

public interface Result<T> {
    void onSuccess(T result);

    void onError(Throwable throwable);
}
