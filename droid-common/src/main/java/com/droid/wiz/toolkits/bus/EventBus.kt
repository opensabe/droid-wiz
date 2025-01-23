package com.droid.wiz.toolkits.bus

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

/**
 * 发送:
 *      emit("key", "hello"))
 *
 * 接受:
 *      observe("key", Observer<String> { value ->
 *          // do sth
 *         })
 *
 * onlyHandleNewValue参数: 只接受最新的值, 因为页面不可见时不会处理事件, 但是监听的key可能会收到多个事件, 当传true时只会接受最新的值 (默认是true)
 *
 */
class EventBus<T>(private val key: String) {

    @Suppress("UNCHECKED_CAST")
    companion object {
        private val map = mutableMapOf<String, EventBus<*>>()

        fun <T> with(key: String): EventBus<T> {
            if (map[key] == null) {
                map[key] = EventBus<T>(key)
            }
            return (map[key] as EventBus<T>?)!!
        }
    }

    private val observers = mutableMapOf<LifecycleOwner, ObserverWrapper<T>>()

    fun observe(owner: LifecycleOwner, observer: Observer<T>, onlyHandleNewValue: Boolean = true) {
        observers[owner] = ObserverWrapper(owner, observer, onlyHandleNewValue)
    }

    fun emit(value: T?) {
        observers.forEach { it.value.onChange(value) }
    }

    private inner class ObserverWrapper<T>(
        private val owner: LifecycleOwner,
        private val observer: Observer<T>,
        private val onlyHandleNewValue: Boolean
    ) : Observer<T> {

        private var waitEmitValues: MutableSet<T?>? = null

        init {
            owner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onResume(owner: LifecycleOwner) {
                    super.onResume(owner)
                    if (!waitEmitValues.isNullOrEmpty()) {
                        waitEmitValues!!.forEach { value ->
                            onChange(value)
                        }
                    }
                }

                override fun onDestroy(owner: LifecycleOwner) {
                    super.onDestroy(owner)
                    owner.lifecycle.removeObserver(this)
                    observers.remove(owner)
                    waitEmitValues = null
                    if (observers.isEmpty()) {
                        map.remove(key)
                    }
                }
            })
        }

        override fun onChange(value: T?) {
            if (!owner.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                if (waitEmitValues == null) {
                    waitEmitValues = mutableSetOf()
                } else {
                    if (onlyHandleNewValue) {
                        waitEmitValues!!.clear()
                    }
                }
                waitEmitValues!!.add(value)
                return
            }
            observer.onChange(value)
        }
    }
}

fun interface Observer<T> {
    fun onChange(value: T?)
}

fun <T> emit(key: String, value: T?) {
    EventBus.with<T>(key).emit(value)
}

fun <T> LifecycleOwner.observe(
    key: String, observer: Observer<T>, onlyHandleNewValue: Boolean = true
) {
    EventBus.with<T>(key).observe(this, observer, onlyHandleNewValue)
}

fun <T> LifecycleOwner.observe(
    key: String, observer: Observer<T>
) {
    EventBus.with<T>(key).observe(this, observer, true)
}