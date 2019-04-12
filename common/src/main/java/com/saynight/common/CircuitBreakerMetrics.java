package com.saynight.common;

import java.util.concurrent.atomic.LongAdder;


class CircuitBreakerMetrics {

    private final int ringBufferSize;
    private final RingBitSet ringBitSet;
    private final LongAdder numberOfNotPermittedCalls;

    CircuitBreakerMetrics(int ringBufferSize) {
        this(ringBufferSize, null);
    }

    CircuitBreakerMetrics(int ringBufferSize, RingBitSet sourceSet) {
        this.ringBufferSize = ringBufferSize;
        if(sourceSet != null) {
            this.ringBitSet = new RingBitSet(this.ringBufferSize, sourceSet);
        }else{
            this.ringBitSet = new RingBitSet(this.ringBufferSize);
        }
        this.numberOfNotPermittedCalls = new LongAdder();
    }

    /**
     * Creates a new CircuitBreakerMetrics instance and copies the content of the current RingBitSet
     * into the new RingBitSet.
     *
     * @param targetRingBufferSize the ringBufferSize of the new CircuitBreakerMetrics instances
     * @return a CircuitBreakerMetrics
     */
    public CircuitBreakerMetrics copy(int targetRingBufferSize) {
        return new CircuitBreakerMetrics(targetRingBufferSize, this.ringBitSet);
    }

    /**
     * Records a failed call and returns the current failure rate in percentage.
     *
     * @return the current failure rate  in percentage.
     */
    float onError() {
        int currentNumberOfFailedCalls = ringBitSet.setNextBit(true);
        return getFailureRate(currentNumberOfFailedCalls);
    }

    /**
     * Records a successful call and returns the current failure rate in percentage.
     *
     * @return the current failure rate in percentage.
     */
    float onSuccess() {
        int currentNumberOfFailedCalls = ringBitSet.setNextBit(false);
        return getFailureRate(currentNumberOfFailedCalls);
    }

    /**
     * Records a call which was not permitted, because the CircuitBreaker state is OPEN.
     */
    void onCallNotPermitted() {
        numberOfNotPermittedCalls.increment();
    }

    /**
     * {@inheritDoc}
     */
    public float getFailureRate() {
        return getFailureRate(getNumberOfFailedCalls());
    }

    /**
     * {@inheritDoc}
     */
    
    public int getMaxNumberOfBufferedCalls() {
        return ringBufferSize;
    }

    /**
     * {@inheritDoc}
     */
    
    public int getNumberOfSuccessfulCalls() {
        return getNumberOfBufferedCalls() - getNumberOfFailedCalls();
    }

    /**
     * {@inheritDoc}
     */
    
    public int getNumberOfBufferedCalls() {
        return this.ringBitSet.length();
    }

    /**
     * {@inheritDoc}
     */
    
    public long getNumberOfNotPermittedCalls() {
        return this.numberOfNotPermittedCalls.sum();
    }

    /**
     * {@inheritDoc}
     */
    
    public int getNumberOfFailedCalls() {
        return this.ringBitSet.cardinality();
    }

    private float getFailureRate(int numberOfFailedCalls) {
        if (getNumberOfBufferedCalls() < ringBufferSize) {
            return -1.0f;
        }
        return numberOfFailedCalls * 100.0f / ringBufferSize;
    }
    public static void main(String[] args) {
		CircuitBreakerMetrics m = new CircuitBreakerMetrics(10);
		for (int i = 1; i <= m.ringBufferSize; i++) {
			long value;
			boolean flag = i % 2 == 0;//偶然代表成功调用，奇数代表失败调用
			if (flag ) {
				value =  m.ringBitSet.setNextBit(false);//成功的请求调用
			}else {
				value =  m.ringBitSet.setNextBit(true);//失败的请求调用

			}
			System.out.println(String.format("第[%s]次调用,flag[%s],value[%s]", i, flag , value));
		}
	}
}
