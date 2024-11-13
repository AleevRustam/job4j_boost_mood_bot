package ru.job4j.servises;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.BeanNameAware;
import ru.job4j.content.Content;

@Service
public class BotCommandHandler implements BeanNameAware {
    private String beanName;

    void receive(Content content) {
        System.out.println(content);
    }

    @PostConstruct
    public void init() {
        System.out.println("Bean is going through @PostConstruct init.");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Bean will be destroyed via @PreDestroy.");
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
        System.out.println("Bean name from BeanNameAware: " + beanName);
    }
}
