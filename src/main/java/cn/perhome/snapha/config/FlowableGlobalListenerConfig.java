package cn.perhome.snapha.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEventDispatcher;

import org.flowable.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import cn.perhome.snapha.flowable.TaskCreateListener;

/**
 * Flowable添加全局监听器
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class FlowableGlobalListenerConfig implements ApplicationListener<ContextRefreshedEvent> {

    private final SpringProcessEngineConfiguration configuration;

    private final TaskCreateListener taskCreateListener;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("全局消息 任务创建侦听器启动");
        FlowableEventDispatcher dispatcher = configuration.getEventDispatcher();
        // 任务创建全局监听-待办消息发送
        dispatcher.addEventListener(taskCreateListener, FlowableEngineEventType.TASK_CREATED);
    }

}