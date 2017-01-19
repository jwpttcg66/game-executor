# game-excutor
> 采用react模型，使用readycreate, readyfinish来进行模型缓存，然后将消息转化为
dispatchThread消息分配模型需要的create, update, finish进行单线程循环调度
中间使用了系统预置锁模型，来进行多线程唤醒机制，将所有的update循环检测接口进行多
线程调度，使用future-listener机制完成调度后，重新将消息转化为update, finish
事件进行循环处理。

## 异步使用例子

> 可参考test下的AsyncEventBusTest.

1. 生成eventbus，注册react事件模型。
2. 生成UpdateService更新服务eventbus, 注册react事件模型。
3. 生成异步线程服务UpdateExecutorService。
4. 生成异步分配线程LockSupportDisptachThread。
5. 生成更新服务UpdateService
6. 生成eventbus监听器. 注册监听器
7. 生成UpdateService更新服务eventbus监听器. 注册监听器
8. 生成事件，放入UpdateService更新服务eventbus
9. 进行循环处理

## 同步使用例子

> 可参考test下的SynsEventBusTest.

1. 生成eventbus，注册react事件模型。
2. 生成dispatchThread，生成分配线程.
3. 生成eventbus监听器. 注册监听器
4. 生成事件，放入eventbus
5. 进行循环处理

### 代码最后通过maven部署

- 作者qq 330258845
- QQ群310158485
