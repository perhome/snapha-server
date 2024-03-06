package cn.perhome.snapha.flowable;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomEventListener implements FlowableEventListener {

    @Override
    public void onEvent(FlowableEvent event) {
        if (event.getType().equals(FlowableEngineEventType.JOB_EXECUTION_SUCCESS)) {
            log.info("一个任务执行成功");
        }
        else if (event.getType().equals(FlowableEngineEventType.JOB_EXECUTION_FAILURE)) {
            log.info("一个任务执行失败");
        }
        else  {
            log.info("一个任务发生了其它类型的事件 {}", event.getType());
        }
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }

    @Override
    public boolean isFireOnTransactionLifecycleEvent() {
        return false;
    }

    @Override
    public String getOnTransaction() {
        return null;
    }
}
