# Kotlin扩展
[官方说明]{https://developer.android.google.cn/kotlin/ktx}
### core-ktx 包含下面扩展
```
androidx.core.animation
androidx.core.content
androidx.core.content.res
androidx.core.database
androidx.core.database.sqlite
androidx.core.graphics
androidx.core.graphics.drawable
androidx.core.location
androidx.core.net
androidx.core.os
androidx.core.text
androidx.core.transition
androidx.core.util
androidx.core.view
androidx.core.widget
```
### fragment-ktx
```
//简化 Fragment 事务
fragmentManager().commit {
   addToBackStack("...")
   setCustomAnimations(
           R.anim.enter_anim,
           R.anim.exit_anim)
   add(fragment, "...")
}
//属性委托
val viewModel by viewModels<MyViewModel>()

```
### lifecycle-runtime-ktx
```
viewLifecycleOwner.lifecycleScope.launch {
   //异步...     
}
```
### lifecycle-livedata-ktx
```
//loadUser() 是在其他地方声明的 suspend 函数。 您可以使用 liveData 构建器函数异步调用 loadUser()，
//然后使用 emit() 来发出结果
val user: LiveData<User> = liveData {
    val data = database.loadUser() // loadUser is a suspend function.
    emit(data)
}
```
### ViewModel KTX
```
//  viewModelScope() 函数会启动一个协程，用于在后台线程中发出网络请求。该库会处理所有设置和相应的范围清除
 private fun makeNetworkRequest() {
        // launch a coroutine in viewModelScope
        viewModelScope.launch  {
            remoteApi.slowFetch()
            ...
        }
    }
```