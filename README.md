# resilience4j-SpringBoot2-demo
resilience4j version : 0.14.1

Spring Boot2 version : 2.1.3.RELEASE

## circuitBreaker demo
Spring AOP:
BackendAOPController > AopBusinessService > AopConnector

NOAOP(without Spring AOP)：
BackendNoAOPController > NoAopBusinessService > NoAopConnector
###event
pull task
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

push task

```
@PostConstruct
public void init() {
    circuitBreaker = circuitBreakerFactory.create(CRConstants.CIRCUITBREAKERNOAOP, 10, 5 * 1000, 5, 10, BusinessException.class);
    circuitBreaker.getEventPublisher().onEvent(event -> circuitBreakerEventsProcessor.processEvent(event));
}
```
两种区别在于：pull task需要应用主动拉取事件并进行消费，push task是CircuitBreaker触发事件是主动push并立刻消费
