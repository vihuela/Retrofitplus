RefreshLayout 是针对Retrofit的ReactX下 **一句话** 异常分发的一个库，**不入侵原有逻辑**

- 解决了异常回调判断繁杂（诸如：if(XX instancof xxException)）、不严谨、不容易扩展等
- 内部异常使用责任链分发，分发逻辑为：
	- 自定义异常->网络异常->服务器异常->内部程序异常->未知异常
	- 除了以上自定义异常之外，此库包含其它异常分发，默认适应场景为：Rx+Json
	- 自定义异常使用请调用，ExceptionParseMgr类的addCustomerParser方法添加业务异常

----------


内部使用provided，不入侵

----------
		provided "com.squareup.retrofit2:retrofit:$retrofit2"
    	provided "com.squareup.retrofit2:converter-gson:$retrofit2"
    	provided "io.reactivex.rxjava2:rxjava:$rxJava"

----------
用法



		//e is Throwable
		ExceptionParseMgr.Instance.parseException(e, new ExceptionParser.IHandler() {
                            @Override
                            public void onHandler(Error error, String message) {
                                //logic
                            }
                        });`

gradle
> compile 'com.ricky:retrofitExceptionDispatcher:0.5'