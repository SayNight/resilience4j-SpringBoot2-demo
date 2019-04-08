# resilience4j-SpringBoot2-demo
    resilience4j version : 0.14.1\<br>
    Spring Boot2 version : 2.1.3.RELEASE

## circuitBreaker demo
    Spring AOP:\<br>
BackendAOPController > AopBusinessService > AopConnector

    NOAOP(without Spring AOP)：\<br>
BackendNoAOPController > NoAopBusinessService > NoAopConnector\<br>
分为使用Spring aop和不使用aop两种方式，看demo中对应代码即可
### event
`pull task`
```
@Bean(name = CRConstants.CIRCUITBREAKERAOP)
public CircuitBreaker circuitBreaker() {
    CircuitBreaker circuitBreaker = circuitBreakerFactory.create(CRConstants.CIRCUITBREAKERAOP, 10, 1 * 1000, 10, 10, BusinessException.class);
    circuitBreaker.getEventPublisher().onEvent(eventConsumerRegistry.createEventConsumer(CRConstants.CIRCUITBREAKERAOP, 100));
    return circuitBreaker;
}
  
//CircularEventConsumer 该种方式需要去pull task
//@PostConstruct  //若注解打开，/actuator/circuitbreakerevents看不到事件，该监控作用是看近期未被消费的事件
public void init() {
    scheduled.scheduleAtFixedRate(() -> {
    CircularEventConsumer<CircuitBreakerEvent> ringBuffer = new CircularEventConsumer<>(10);
    circuitBreaker.getEventPublisher().onEvent(ringBuffer);
    List<CircuitBreakerEvent> bufferedEvents = (List<CircuitBreakerEvent>) ringBuffer.getBufferedEvents();
    bufferedEvents.forEach(event -> circuitBreakerEventsProcessor.processEvent(event));
    }, 1000, 1000, TimeUnit.MILLISECONDS); 
}
```

`push task`

```
@PostConstruct
public void init() {
    circuitBreaker = circuitBreakerFactory.create(CRConstants.CIRCUITBREAKERNOAOP, 10, 5 * 1000, 5, 10, BusinessException.class);
    circuitBreaker.getEventPublisher().onEvent(event -> circuitBreakerEventsProcessor.processEvent(event));
}
```
        两种区别在于：pull task需要应用主动拉取事件并进行消费，push task是CircuitBreaker触发事件是主动push并立刻消费

推荐采用官网yml配置方式(Spring AOP), 配置简单，上手快，维护成本低：
```
resilience4j.circuitbreaker:
    backends:
        backendA:
            registerHealthIndicator: true
            ringBufferSizeInClosedState: 5
            ringBufferSizeInHalfOpenState: 3
            waitInterval: 5000
            failureRateThreshold: 50
            eventConsumerBufferSize: 10
            ignoreExceptions:
                - io.github.robwin.exception.BusinessException
        backendB:
            registerHealthIndicator: true
            ringBufferSizeInClosedState: 10
            ringBufferSizeInHalfOpenState: 5
            waitInterval: 5000
            failureRateThreshold: 50
            eventConsumerBufferSize: 10
            recordFailurePredicate: io.github.robwin.exception.RecordFailurePredicate
```
暂时研究yml配置除了registerHealthIndicator（健康检查），其它配置均可通过circuitBreakerConfig自定义编码配置
