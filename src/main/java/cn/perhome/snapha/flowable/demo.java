//package cn.perhome.snapha.flowable;
//
//public class demo {
//}
//
//import org.flowable.engine.delegate.TaskListener;
//        import org.flowable.task.service.delegate.DelegateTask;
//
//public class ManagerTaskHandler implements TaskListener {
//
//    @Override
//    public void notify(DelegateTask delegateTask) {
//        delegateTask.setAssignee("经理");
//    }
//
//}
//
//     */
//@RequestMapping(value = "apply")
//@ResponseBody
//public String apply(String taskId) {
//        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
//        if (task == null) {
//        throw new RuntimeException("流程不存在");
//        }
//        //通过审核
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("outcome", "通过");
//        taskService.complete(taskId, map);
//        return "processed ok!";
//        }