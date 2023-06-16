package com.zjut.study.dynamic.attach;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * 加载探针包 attach 目标服务
 */
public class AgentAttach {

    public static void main(String[] args) {
        //查找所有jvm进程，排除attach测试工程
        List<VirtualMachineDescriptor> attach = VirtualMachine.list()
                .stream()
                .filter(jvm -> {
                    return !jvm.displayName().contains("Attach");
                }).collect(Collectors.toList());
        for (int i = 0; i < attach.size(); i++) {
            System.out.println("[" + i + "] " + attach.get(i).displayName()+":"+attach.get(i).id());
        }

        // 根据提示选择目标进程
        System.out.println("请输入需要attach的pid编号");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        VirtualMachineDescriptor virtualMachineDescriptor = attach.get(new Integer(s));
        try {
            VirtualMachine virtualMachine = VirtualMachine.attach(virtualMachineDescriptor.id());
            virtualMachine.loadAgent("D:\\workspace\\public-project\\project-study\\study-agent\\agent-attach\\target\\agent-attach.jar", "param");
            virtualMachine.detach();
        } catch (AttachNotSupportedException e) {
            System.out.println("AttachNotSupportedException：" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException：" + e.getMessage());
        } catch (AgentLoadException e) {
            System.out.println("AgentLoadException：" + e.getMessage());
        } catch (AgentInitializationException e) {
            System.out.println("AgentInitializationException：" + e.getMessage());
        }
    }
}
