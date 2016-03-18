package zxing.image;

/**
 * Created by root on 15-12-3.
 * 观察者消息通知
 */
public interface DataObservable {

    public abstract void onDataChange(int action, long params, Object value);

}
