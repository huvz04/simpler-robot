/*
 *  Copyright (c) 2021-2021 ForteScarlet <https://github.com/ForteScarlet>
 *
 *  根据 Apache License 2.0 获得许可；
 *  除非遵守许可，否则您不得使用此文件。
 *  您可以在以下网址获取许可证副本：
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *   有关许可证下的权限和限制的具体语言，请参见许可证。
 */

package love.forte.simbot.event

import love.forte.simbot.Interceptor

/**
 * 与事件有关的拦截器。
 * @see EventProcessingInterceptor
 * @see EventListenerInterceptor
 */
public sealed interface EventInterceptor<C : EventInterceptor.Context<R>, R> : Interceptor<C, R> {
    public sealed interface Context<R> : Interceptor.Context<R> {
        public val eventContext: EventProcessingContext
    }
}


/**
 * 一个事件处理过程的拦截器. 是一个最外层的拦截器。
 */
public interface EventProcessingInterceptor : EventInterceptor<EventProcessingInterceptor.Context, EventProcessingResult> {
    override suspend fun intercept(context: Context): EventProcessingResult

    /**
     * [EventProcessingInterceptor] 的传递上下文。
     */
    public interface Context : EventInterceptor.Context<EventProcessingResult> {
        override val eventContext: EventProcessingContext
        override suspend fun proceed(): EventProcessingResult
    }
}


/**
 * 事件监听函数拦截器，
 */
public interface EventListenerInterceptor : EventInterceptor<EventListenerInterceptor.Context, EventResult> {
    override suspend fun intercept(context: Context): EventResult

    /**
     * [EventListenerInterceptor] 的传递上下文。
     */
    public interface Context : EventInterceptor.Context<EventResult> {
        override val eventContext: EventProcessingContext
        override suspend fun proceed(): EventResult
    }
}


