package zxing.image;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by root on 15-12-3.
 */
public class DataCenter {

    ////观察者通知队列
    private static ConcurrentLinkedQueue<DataObservable> mObservableList=new ConcurrentLinkedQueue<DataObservable>();
    /**
     * @param observer
     * 注册观察者对象
     */
    public static void registerObserver(DataObservable observer) {
        if (observer == null) {
            //throw new IllegalArgumentException("The observer is null.");
            return;
        }
        synchronized(mObservableList) {
            if (mObservableList.contains(observer)) {
                //throw new IllegalStateException("Observer " + observer + " is already registered.");
                return;
            }
            mObservableList.add(observer);
        }
    }
    /**
     * @param observer
     *取消观察者对象
     */
    public static void unregisterObserver(DataObservable observer) {
        if (observer == null) {
            //throw new IllegalArgumentException("The observer is null.");
            return;
        }
        synchronized(mObservableList) {

            if(mObservableList.contains(observer)){
                mObservableList.remove(observer);}
        }
    }
    /**
     * 事件通知
     * @paramInt1 通知类型
     * @paramInt2 保留字段
     * @paramObject 通知数据
     */
    public static void notifyData(int paramInt1,long paramInt2,Object paramObject){
        if(mObservableList==null){
            return;
        }
        synchronized (mObservableList){
            if(mObservableList.isEmpty()){
                return;
            }
            Iterator iterator=mObservableList.iterator();
            while (iterator.hasNext()){
                ((DataObservable)iterator.next()).onDataChange(paramInt1,paramInt2,paramObject);
            }
        }
    }
}
