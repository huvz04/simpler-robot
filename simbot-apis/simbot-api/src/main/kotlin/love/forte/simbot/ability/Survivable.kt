/*
 * Copyright (c) 2021-2023 ForteScarlet <ForteScarlet@163.com>
 *
 * 本文件是 simply-robot (或称 simple-robot 3.x 、simbot 3.x 、simbot3 等) 的一部分。
 * simply-robot 是自由软件：你可以再分发之和/或依照由自由软件基金会发布的 GNU 通用公共许可证修改之，无论是版本 3 许可证，还是（按你的决定）任何以后版都可以。
 * 发布 simply-robot 是希望它能有用，但是并无保障;甚至连可销售和符合某个特定的目的都不保证。请参看 GNU 通用公共许可证，了解详情。
 *
 * 你应该随程序获得一份 GNU 通用公共许可证的复本。如果没有，请看:
 * https://www.gnu.org/licenses
 * https://www.gnu.org/licenses/gpl-3.0-standalone.html
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */

package love.forte.simbot.ability

import kotlinx.coroutines.CompletionHandler
import love.forte.simbot.Api4J
import love.forte.simbot.JST
import love.forte.simbot.utils.runInNoScopeBlocking

/**
 * 可存活的。
 * 此接口提供 [join]、[invokeOnCompletion] 等函数来对生命周期提供一定操作。
 *
 * @author ForteScarlet
 */
public interface Survivable : Switchable {
    
    /**
     * 挂起, 直到当前实例被 [cancel] 或完成.
     */
    @JST(asyncBaseName = "asFuture", asyncSuffix = "")
    public suspend fun join()
    
    /**
     * 当完成（或被cancel）时执行一段处理。
     */
    public fun invokeOnCompletion(handler: CompletionHandler)
    
    /**
     * 阻塞当前线程并等待 [join] 的挂起结束。
     *
     * 等同于 `joinBlocking`。目前来看唯一的区别是 [waiting] 显示通过 [Throws] 指定了受检异常 [InterruptedException],
     * 而 joinBlocking 目前不会产生受检异常。
     */
    @Api4J
    @Throws(InterruptedException::class)
    public fun waiting() {
        runInNoScopeBlocking { join() }
    }
}

