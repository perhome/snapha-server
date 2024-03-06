package cn.perhome.snapha.flowable;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.common.engine.impl.event.FlowableEntityEventImpl;
import org.flowable.engine.*;

import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import cn.perhome.snapha.service.UserService;

/**
 * 全局监听-工作流待办消息提醒
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TaskCreateListener implements FlowableEventListener {
    public final RepositoryService repositoryService;
    public final RuntimeService runtimeService;
    private final TaskService taskService;
    private final UserService userService;

    public final RedisTemplate redisTemplate;

    @Autowired
    public TaskCreateListener(TaskService taskService,
                              RepositoryService repositoryService, RuntimeService runtimeService,
                              UserService userService, RedisTemplate redisTemplate) {
        this.taskService = taskService;
        this.runtimeService = runtimeService;
        this.repositoryService = repositoryService;
        this.userService = userService;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onEvent(FlowableEvent flowableEvent) {
        DelegateTask task = (DelegateTask) ((FlowableEntityEventImpl) flowableEvent).getEntity();
        String taskId = task.getId();

//        String processInstanceId = ((FlowableEntityEventImpl) flowableEvent).getProcessInstanceId();
//        ProcessInstance instance = this.runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
//        log.info(" 待办人 {} 任务ID {}/{} 类型 {} 流程 {}", taskEntity.getAssignee(), taskId,
//                taskEntity.getTaskDefinitionKey(), instance.getProcessDefinitionKey(), processInstanceId);
//        String processDefinitionKey = instance.getProcessDefinitionKey();
//        String assignee = taskEntity.getAssignee();
//        log.info("流程变量 getVariables => {}", taskEntity.getVariables());
//        if (StringUtils.isNotBlank(assignee)) {
//            Map<String, Object > vars = new HashMap<>();
//            switch (taskEntity.getTaskDefinitionKey()) {
//                // 创建档案时，指定后续审核人（排除自身）
//                case "LivestockDeathInput":
//                    List<String> taskAuditList = new ArrayList<>();
//                    List<UserEntity> userList = this.userService.getGroupMember("LivestockDeathAuth", this.sessionService.getUserId());
//                    userList.forEach(u->{
//                        taskAuditList.add(u.getUserCode());
//                    });
//                    vars.put("taskAuditList", taskAuditList);
//                    break;
//                case "TaskUserGoodsInput":
////                    taskEntity.setVariableLocal("formUrl", "TaskGoodsBox");
//                    break;
//            }
//            // 任务属于自己创建的流程，那么任务将flowableTaskService自动通过
//            if (assignee == this.sessionService.getUserCode()) {
//                log.info("流程 任务ID: {} 待办人: {} 自动通过处理", taskId, assignee);
//                vars.put("remark", "自动处理");
//                this.taskService.complete(taskId, vars);
//            }
//            else {
//                // 创建用户流程任务
//                log.info("taskEntity => {}", taskEntity);
//                this.flowableTaskService.create(instance, taskEntity);
//            }
//            Map<String, Object > messageMap = new HashMap();
//            messageMap.put("message", String.format("[待办]%s", taskEntity.getVariable("subject").toString()));
//            messageMap.put("toUserCode",  assignee);
//            messageMap.put("toUrl",  "/flowable/pending");
//            try {
//                this.redisTemplate.convertAndSend("USER_CHANNEL", JsonUtils.generate(messageMap));
//            } catch (JsonProcessingException e) {
//                log.error("流程通知出现了问题");
//            }
//        }
//        else {
//            log.info("等待指定任务待办者...");
//        }
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