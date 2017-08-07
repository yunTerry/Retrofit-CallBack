
## Retrofit的两种回调封装

在Android的世界里，可以说 [Retrofit](https://github.com/square/retrofit) 已经一统网络请求的江湖，Retrofit和Spring Cloud中的feign一样都是声明式REST请求客户端，都提供了大量注解和完善的json对象转换机制，同时不失灵活性。

通常服务端返回都是这种统一格式的对象：
```java
public class BaseModel<T> {

    public int code;
    public String message;
    public T data;

}
```

Android端拿到这个对象通常要判断code，然后做对象剥离、token有效性判断、网络故障处理等，这些如果封装起来统一处理，可以极大简化网络调用。

这里我根据自己实际开发经验，对使用了Rxjava的回调和普通回调分别作了封装，实现以上功能。

## 使用Rxjava的回调封装

Retrofit通过RxJavaCallAdapter可以直接将返回结果转换为可观察的对象，拿到Observable进行一系列链式处理就方便多了。
比如声明一个API调用方法：
```java
    @GET("/userinfo")
    Observable<BaseModel<User>> getRxUser();
```
我们通过一个实现Observer接口的抽象类对返回结果进行处理，实现对象剥离、token有效性判断、网络故障的统一处理，注意这里使用的是RxJava2

完整封装代码：
```java
public abstract class RxSubscribe<T> implements Observer<BaseModel<T>> {

    public RxSubscribe() {
    }

    protected abstract void onSuccess(T t);

    protected void onFailed(int code, String msg) {
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        // 显示加载中对话框
    }

    @Override
    public void onComplete() {
        // 隐藏加载中对话框
    }

    @Override
    public void onNext(BaseModel<T> baseModel) {
        if (baseModel.code == 200) {
            onSuccess(baseModel.data);
        } else if (baseModel.code == 303) {
            //比如 做token无效统一处理
            onFailed(baseModel.code, baseModel.message);
        } else {
            onFailed(baseModel.code, baseModel.message);
        }
    }

    @Override
    public void onError(Throwable t) {
        if (t instanceof ConnectException) {
            //网络连接失败
            onFailed(403, t.getMessage());
        } else {
            onFailed(405, t.getMessage());
        }
    }
}
```
+ 复写onSuccess抽象方法可以直接拿到剥离后目标对象，这里为使代码更简洁，不强制复写onSubscribe、onFailed等方法
+ 另外可以顺便在onSubscribe和onComplete方法中控制加载中对话框的显示与隐藏。

## 普通回调的封装

对于普通回调，返回的是Call类型，声明一个API调用方法：
```java
    @GET("/userinfo")
    Call<BaseModel<User>> getUser();
```
回调封装部分与上面类似，直接上代码：

```java
public abstract class BaseBack<T> implements Callback<BaseModel<T>> {

    public BaseBack() {
    }

    protected abstract void onSuccess(T t);

    protected void onFailed(int code, String msg) {
    }

    @Override
    public void onResponse(Call<BaseModel<T>> call, Response<BaseModel<T>> response) {
        BaseModel<T> baseModel = response.body();
        if (response.isSuccessful() && baseModel != null) {
            if (baseModel.code == 200) {
                onSuccess(baseModel.data);
            } else if (baseModel.code == 303) {
                //比如 做token无效统一处理
                onFailed(baseModel.code, baseModel.message);
            } else {
                onFailed(baseModel.code, baseModel.message);
            }
        } else {
            onFailed(response.code(), response.message());
        }
    }

    @Override
    public void onFailure(Call<BaseModel<T>> call, Throwable t) {
        if (t instanceof ConnectException) {
            //网络连接失败
            onFailed(403, t.getMessage());
        } else {
            onFailed(405, t.getMessage());
        }
    }
}
```
同样是复写onSuccess抽象方法直接拿到剥离后的目标对象。

### 项目使用的后端服务：[spring-cloud-netflix](https://github.com/yunTerry/spring-cloud-netflix)